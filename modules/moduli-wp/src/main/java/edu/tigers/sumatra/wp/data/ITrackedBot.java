/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Nov 4, 2015
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.wp.data;

import edu.tigers.sumatra.bot.IBot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;


/**
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public interface ITrackedBot extends ITrackedObject
{
	
	/**
	 * @return
	 */
	ITrackedBot mirrorNew();
	
	
	/**
	 * @param t
	 * @return
	 */
	IVector2 getPosByTime(double t);
	
	
	/**
	 * @param t
	 * @return
	 */
	IVector2 getVelByTime(double t);
	
	
	/**
	 * @param t
	 * @return
	 */
	double getAngleByTime(double t);
	
	
	/**
	 * @return the ballContact
	 */
	boolean hasBallContact();
	
	
	/**
	 * @return the visible
	 */
	boolean isVisible();
	
	
	/**
	 * @return
	 */
	double getCenter2DribblerDist();
	
	
	/**
	 * Calculates the position of the dribbler/kicker of the given bot.
	 * Use this position for ball receivers, etc.
	 * 
	 * @return
	 */
	IVector2 getBotKickerPos();
	
	
	/**
	 * @return
	 */
	@Override
	BotID getBotId();
	
	
	/**
	 * @return
	 */
	ETeamColor getTeamColor();
	
	
	/**
	 * @return the angle
	 */
	double getAngle();
	
	
	/**
	 * @return the aVel
	 */
	double getaVel();
	
	
	/**
	 * @return
	 */
	double getaAcc();
	
	
	/**
	 * @return
	 */
	boolean isAvailableToAi();
	
	
	/**
	 * @return
	 */
	IBot getBot();
	
	
	/**
	 * @param t
	 * @return
	 */
	IVector2 getBotKickerPosByTime(double t);
	
}