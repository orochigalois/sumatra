/*
 * *********************************************************
 * Copyright (c) 2009 - 2014, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Jan 30, 2014
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.ai.metis.general;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.ACalculator;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.wp.data.Geometry;


/**
 * Save the moment, the ball left the field
 * 
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class BallLeftFieldCalc extends ACalculator
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger	log				= Logger.getLogger(BallLeftFieldCalc.class.getName());
	private static final int		BUFFER_SIZE		= 5;
	private final List<IVector2>	lastBallPoss	= new ArrayList<IVector2>(BUFFER_SIZE);
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public void doCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		newTacticalField.setBallLeftFieldPos(baseAiFrame.getPrevFrame().getTacticalField().getBallLeftFieldPos());
		
		IVector2 ballPos = baseAiFrame.getWorldFrame().getBall().getPos();
		if (Geometry.getField().isPointInShape(ballPos))
		{
			if (lastBallPoss.size() == BUFFER_SIZE)
			{
				lastBallPoss.remove(BUFFER_SIZE - 1);
			}
			lastBallPoss.add(0, ballPos);
			newTacticalField.setBallLeftFieldPos(null);
		} else if ((baseAiFrame.getPrevFrame().getTacticalField().getBallLeftFieldPos() == null)
				&& !lastBallPoss.isEmpty())
		{
			ILine line = Line.newLine(lastBallPoss.get(0), ballPos);
			List<IVector2> ballIntersectionPoints = Geometry.getField().lineIntersections(line);
			if (ballIntersectionPoints.isEmpty())
			{
				log.warn("Ball left field, but there was no intersection with field borders?!");
			} else
			{
				newTacticalField.setBallLeftFieldPos(ballIntersectionPoints.get(0));
			}
		}
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
