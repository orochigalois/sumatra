/*
 * *********************************************************
 * Copyright (c) 2009 - 2014, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Feb 8, 2014
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.ai.athena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.data.PlayStrategy.Builder;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.lachesis.RoleFinderInfo;
import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.plays.others.GuiTestPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ids.BotID;


/**
 * Test mode
 * 
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class TestModeAthenaAdapter extends AAthenaAdapter
{
	private static final Logger	log	= Logger.getLogger(TestModeAthenaAdapter.class.getName());
	
	
	@Override
	public void doProcess(final MetisAiFrame metisAiFrame, final Builder playStrategyBuilder, final AIControl aiControl)
	{
		Map<EPlay, RoleFinderInfo> infosCopy = new HashMap<EPlay, RoleFinderInfo>(metisAiFrame.getTacticalField()
				.getRoleFinderInfos());
		metisAiFrame.getTacticalField().getRoleFinderInfos().clear();
		for (Map.Entry<EPlay, RoleFinderInfo> entry : infosCopy.entrySet())
		{
			Boolean override = getAiControl().getRoleFinderOverrides().get(entry.getKey());
			if ((override != null) && !override)
			{
				metisAiFrame.getTacticalField().getRoleFinderInfos().put(entry.getKey(), entry.getValue());
			}
		}
		for (Map.Entry<EPlay, RoleFinderInfo> entry : getAiControl().getRoleFinderInfos().entrySet())
		{
			// only put plays that are not present
			metisAiFrame.getTacticalField().getRoleFinderInfos().putIfAbsent(entry.getKey(), entry.getValue());
		}
		
		updatePlays(metisAiFrame.getTacticalField().getRoleFinderInfos(), playStrategyBuilder.getActivePlays());
		
		GuiTestPlay guiPlay = getGuiTestPlay(playStrategyBuilder.getActivePlays());
		if (guiPlay != null)
		{
			for (ARole role : new ArrayList<ARole>(guiPlay.getRoles()))
			{
				if (role.isCompleted())
				{
					guiPlay.setRoleToBeRemoved(role);
					guiPlay.removeRoles(1, metisAiFrame);
				}
			}
		}
		// process legacy
		if (aiControl.hasChanged())
		{
			// process roles
			if (!aiControl.getAddRoles().isEmpty())
			{
				if (guiPlay == null)
				{
					guiPlay = new GuiTestPlay();
					playStrategyBuilder.getActivePlays().add(guiPlay);
				}
				for (ARole role : aiControl.getAddRoles())
				{
					guiPlay.setRoleToBeAdded(role);
					guiPlay.addRoles(1, metisAiFrame);
				}
			}
			if (!aiControl.getRemoveRoles().isEmpty())
			{
				if (guiPlay == null)
				{
					log.warn("Tried to remove at least one role, but there is no guiPlay active.");
				} else
				{
					for (ARole role : aiControl.getRemoveRoles())
					{
						guiPlay.setRoleToBeRemoved(role);
						guiPlay.removeRoles(1, metisAiFrame);
					}
				}
			}
			outer: for (Map.Entry<BotID, ARole> entry : aiControl.getAssignRoles().entrySet())
			{
				BotID botId = BotID.createBotId(entry.getKey().getNumber(), metisAiFrame.getTeamColor());
				for (APlay play : playStrategyBuilder.getActivePlays())
				{
					for (ARole r : play.getRoles())
					{
						if (r.getBotID().equals(botId))
						{
							log.error("Bot id " + botId + " already assigned");
							continue outer;
						}
					}
				}
				entry.getValue().assignBotID(botId, metisAiFrame);
			}
			
			for (APlay play : aiControl.getAddPlays())
			{
				playStrategyBuilder.getActivePlays().add(play);
			}
			
			for (APlay play : aiControl.getRemovePlays())
			{
				play.changeToFinished();
				if (!playStrategyBuilder.getActivePlays().remove(play))
				{
					log.warn("Play " + play.getType() + " could not be removed, because it does not exist.");
				}
			}
			
			int roleSum = 0;
			for (APlay play : playStrategyBuilder.getActivePlays())
			{
				roleSum += play.getRoles().size();
			}
			
			int addedRoles = 0;
			for (Map.Entry<APlay, Integer> entry : aiControl.getAddRoles2Play().entrySet())
			{
				if ((roleSum + addedRoles) < metisAiFrame.getWorldFrame().tigerBotsAvailable.size())
				{
					entry.getKey().addRoles(entry.getValue(), metisAiFrame);
					addedRoles++;
				}
			}
			
			for (Map.Entry<APlay, Integer> entry : aiControl.getRemoveRolesFromPlay().entrySet())
			{
				if (!entry.getKey().getRoles().isEmpty())
				{
					entry.getKey().removeRoles(entry.getValue(), metisAiFrame);
				}
			}
			
			List<APlay> tobeRemoved = new LinkedList<APlay>();
			for (APlay play : playStrategyBuilder.getActivePlays())
			{
				if (roleSum <= metisAiFrame.getWorldFrame().tigerBotsAvailable.size())
				{
					break;
				}
				tobeRemoved.add(play);
				roleSum -= play.getRoles().size();
			}
			
			for (APlay play : tobeRemoved)
			{
				play.changeToFinished();
				playStrategyBuilder.getActivePlays().remove(play);
				log.trace("Play removed: " + play);
			}
		}
	}
	
	
	private GuiTestPlay getGuiTestPlay(final List<APlay> activePlays)
	{
		GuiTestPlay guiPlay = null;
		for (APlay aPlay : activePlays)
		{
			if (aPlay.getType() == EPlay.GUI_TEST)
			{
				guiPlay = (GuiTestPlay) aPlay;
				break;
			}
		}
		return guiPlay;
	}
}
