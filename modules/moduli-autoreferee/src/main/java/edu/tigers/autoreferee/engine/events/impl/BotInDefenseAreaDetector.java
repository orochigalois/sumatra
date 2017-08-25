/*
 * *********************************************************
 * Copyright (c) 2009 - 2016, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Feb 16, 2016
 * Author(s): Lukas Magel
 * *********************************************************
 */
package edu.tigers.autoreferee.engine.events.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.github.g3force.configurable.Configurable;

import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.AutoRefMath;
import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.FollowUpAction.EActionType;
import edu.tigers.autoreferee.engine.NGeometry;
import edu.tigers.autoreferee.engine.calc.BotPosition;
import edu.tigers.autoreferee.engine.events.CardPenalty;
import edu.tigers.autoreferee.engine.events.DistanceViolation;
import edu.tigers.autoreferee.engine.events.EGameEvent;
import edu.tigers.autoreferee.engine.events.GameEvent;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest.CardInfo.CardType;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.referee.TeamConfig;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.PenaltyArea;


/**
 * This rule detects attackers/defenders that touch the ball while inside the defense area of the defending/their own
 * team.
 * 
 * @author Lukas Magel
 */
public class BotInDefenseAreaDetector extends APreparingGameEventDetector
{
	private static final int			priority					= 1;
	
	@Configurable(comment = "[ms] The cooldown time before registering a ball touch with the same bot again in ms")
	private static int					COOLDOWN_TIME_MS		= 3_000;
	
	@Configurable(comment = "[mm] Distance from the defense line that is considered a partial violation")
	private static double				partialTouchMargin	= 20;
	
	private long							entryTime				= 0;
	private Map<BotID, BotPosition>	lastViolators			= new HashMap<>();
	
	static
	{
		AGameEventDetector.registerClass(BotInDefenseAreaDetector.class);
	}
	
	
	/**
	 * 
	 */
	public BotInDefenseAreaDetector()
	{
		super(EGameStateNeutral.RUNNING);
	}
	
	
	@Override
	public int getPriority()
	{
		return priority;
	}
	
	
	@Override
	protected void prepare(final IAutoRefFrame frame)
	{
		entryTime = frame.getTimestamp();
	}
	
	
	@Override
	public Optional<IGameEvent> doUpdate(final IAutoRefFrame frame, final List<IGameEvent> violations)
	{
		BotPosition curKicker = frame.getBotLastTouchedBall();
		
		if (curKicker.getTs() < entryTime)
		{
			/*
			 * The ball was last touched before the game state changed to RUNNING
			 */
			return Optional.empty();
		}
		if ((curKicker == null) || curKicker.getId().isUninitializedID())
		{
			return Optional.empty();
		}
		
		BotID curKickerId = curKicker.getId();
		BotPosition lastViolationOfCurKicker = lastViolators.get(curKickerId);
		
		if (lastViolationOfCurKicker != null)
		{
			if (curKicker.getTs() == lastViolationOfCurKicker.getTs())
			{
				// The offense has already been reported
				return Optional.empty();
			}
			
			if (curKicker.getId().equals(lastViolationOfCurKicker.getId()))
			{
				// Wait a certain amount of time before reporting the offense again for the same bot
				long timeDiff = curKicker.getTs() - lastViolationOfCurKicker.getTs();
				if (timeDiff < TimeUnit.MILLISECONDS.toNanos(COOLDOWN_TIME_MS))
				{
					return Optional.empty();
				}
			}
		}
		
		Set<BotID> keeper = TeamConfig.getKeeperIDs();
		if (keeper.contains(curKickerId))
		{
			return Optional.empty();
		}
		
		IVector2 curKickerPos = curKicker.getPos();
		ETeamColor curKickerColor = curKicker.getId().getTeamColor();
		
		PenaltyArea opponentPenArea = NGeometry.getPenaltyArea(curKickerColor.opposite());
		PenaltyArea ownPenArea = NGeometry.getPenaltyArea(curKickerColor);
		
		if (opponentPenArea.isPointInShape(curKickerPos, getPartialTouchMargin()))
		{
			/*
			 * Attacker touched the ball while being located partially/fully inside the opponent's penalty area
			 */
			lastViolators.put(curKickerId, curKicker);
			
			double distance = AutoRefMath.distanceToNearestPointOutside(opponentPenArea, Geometry.getBotRadius(),
					curKickerPos);
			FollowUpAction followUp = new FollowUpAction(EActionType.INDIRECT_FREE, curKickerColor.opposite(),
					AutoRefMath.getClosestFreekickPos(curKickerPos, curKickerColor.opposite()));
			
			GameEvent violation = new DistanceViolation(EGameEvent.ATTACKER_IN_DEFENSE_AREA, frame.getTimestamp(),
					curKickerId, followUp, distance);
			
			return Optional.of(violation);
		} else if (ownPenArea.isPointInShape(curKickerPos, -Geometry.getBotRadius()))
		{
			/*
			 * Multiple Defender:
			 * Defender touched the ball while being located entirely inside the own defense area
			 */
			lastViolators.put(curKickerId, curKicker);
			
			double distance = AutoRefMath
					.distanceToNearestPointOutside(ownPenArea, Geometry.getBotRadius(), curKickerPos);
			FollowUpAction followUp = new FollowUpAction(EActionType.PENALTY, curKickerColor.opposite(),
					NGeometry.getPenaltyMark(curKickerColor));
			GameEvent violation = new DistanceViolation(EGameEvent.MULTIPLE_DEFENDER, frame.getTimestamp(),
					curKickerId, followUp, distance);
			
			return Optional.of(violation);
		} else if (ownPenArea.isPointInShape(curKickerPos, getPartialTouchMargin()))
		{
			/*
			 * Multiple Defender:
			 * Defender touched the ball while being located partially inside his own defense area
			 */
			lastViolators.put(curKickerId, curKicker);
			
			double distance = AutoRefMath.distanceToNearestPointOutside(ownPenArea, Geometry.getBotRadius(), curKickerPos);
			IVector2 freekickPos = AutoRefMath.getClosestFreekickPos(curKickerPos, curKickerColor.opposite());
			FollowUpAction followUp = new FollowUpAction(EActionType.INDIRECT_FREE, curKickerColor.opposite(), freekickPos);
			CardPenalty cardPenalty = new CardPenalty(CardType.CARD_YELLOW, curKickerColor);
			GameEvent violation = new DistanceViolation(EGameEvent.MULTIPLE_DEFENDER_PARTIALLY, frame.getTimestamp(),
					curKickerId, followUp, cardPenalty, distance);
			return Optional.of(violation);
		}
		return Optional.empty();
		
	}
	
	
	private double getPartialTouchMargin()
	{
		return Geometry.getBotRadius() - partialTouchMargin;
	}
	
	
	@Override
	public void doReset()
	{
		lastViolators.clear();
	}
	
}
