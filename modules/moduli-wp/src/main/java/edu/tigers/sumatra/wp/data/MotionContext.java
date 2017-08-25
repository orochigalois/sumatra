/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Sep 17, 2015
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.wp.data;

import java.util.HashMap;
import java.util.Map;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;


/**
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class MotionContext
{
	private final Map<BotID, BotInfo> bots = new HashMap<>(12);
	
	
	/**
	 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
	 */
	public static class BotInfo
	{
		private final BotID		botId;
		private final IVector3	pos;
		private double				kickSpeed				= 0;
		private boolean			chip						= false;
		private double				dribbleRpm				= 0;
		private double				center2DribblerDist	= Geometry.getCenter2DribblerDistDefault();
		private IVector3			vel						= Vector3.ZERO_VECTOR;
		private boolean			ballContact				= false;
		
		
		/**
		 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
		 * @param botId
		 * @param pos
		 */
		public BotInfo(final BotID botId, final IVector3 pos)
		{
			this.botId = botId;
			this.pos = pos;
		}
		
		
		/**
		 * @return the kickSpeed
		 */
		public final double getKickSpeed()
		{
			return kickSpeed;
		}
		
		
		/**
		 * @param kickSpeed the kickSpeed to set
		 */
		public final void setKickSpeed(final double kickSpeed)
		{
			this.kickSpeed = kickSpeed;
		}
		
		
		/**
		 * @return the pos
		 */
		public final IVector3 getPos()
		{
			return pos;
		}
		
		
		/**
		 * @return the botId
		 */
		public final BotID getBotId()
		{
			return botId;
		}
		
		
		/**
		 * @return the center2DribblerDist
		 */
		public double getCenter2DribblerDist()
		{
			return center2DribblerDist;
		}
		
		
		/**
		 * @param center2DribblerDist the center2DribblerDist to set
		 */
		public void setCenter2DribblerDist(final double center2DribblerDist)
		{
			this.center2DribblerDist = center2DribblerDist;
		}
		
		
		/**
		 * @return the chip
		 */
		public boolean isChip()
		{
			return chip;
		}
		
		
		/**
		 * @param chip the chip to set
		 */
		public void setChip(final boolean chip)
		{
			this.chip = chip;
		}
		
		
		/**
		 * @return the dribbleRpm
		 */
		public double getDribbleRpm()
		{
			return dribbleRpm;
		}
		
		
		/**
		 * @param dribbleRpm the dribbleRpm to set
		 */
		public void setDribbleRpm(final double dribbleRpm)
		{
			this.dribbleRpm = dribbleRpm;
		}
		
		
		/**
		 * @return the vel
		 */
		public IVector3 getVel()
		{
			return vel;
		}
		
		
		/**
		 * @param vel the vel to set
		 */
		public void setVel(final IVector3 vel)
		{
			this.vel = vel;
		}
		
		
		/**
		 * @return the ballContact
		 */
		public boolean isBallContact()
		{
			return ballContact;
		}
		
		
		/**
		 * @param ballContact the ballContact to set
		 */
		public void setBallContact(final boolean ballContact)
		{
			this.ballContact = ballContact;
		}
	}
	
	
	/**
	 * @return the bots
	 */
	public final Map<BotID, BotInfo> getBots()
	{
		return bots;
	}
}
