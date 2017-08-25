/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: 24.06.2015
 * Author(s): AndreR
 * *********************************************************
 */
package edu.tigers.sumatra.botmanager.commands.botskills;

import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;


/**
 * Command direct wheel speeds to the robot.
 * No further conversion on the robot is done.
 * 
 * @author AndreR
 */
public class BotSkillWheelVelocity extends ABotSkill
{
	@SerialData(type = ESerialDataType.INT16)
	private final int	vel[]	= new int[4];
	
	
	/** */
	public BotSkillWheelVelocity()
	{
		super(EBotSkill.WHEEL_VELOCITY);
	}
	
	
	/**
	 * Set wheel velocities in [rad/s].
	 * Min/Max: +-163.48rad/s
	 * Resolution: 0.005rad/s
	 * 
	 * @param fr Front Right
	 * @param fl Front Left
	 * @param rl Rear Left
	 * @param rr Rear Right
	 */
	public BotSkillWheelVelocity(final double fr, final double fl, final double rl, final double rr)
	{
		super(EBotSkill.WHEEL_VELOCITY);
		
		vel[0] = (int) (fr * 200.0);
		vel[1] = (int) (fl * 200.0);
		vel[2] = (int) (rl * 200.0);
		vel[3] = (int) (rr * 200.0);
	}
	
	
	/**
	 * Set all wheel velocities.
	 * Min/Max: +-163.48rad/s
	 * Resolution: 0.005rad/s
	 * 
	 * @param in Wheel velocities in [rad/s]
	 */
	public BotSkillWheelVelocity(final double[] in)
	{
		super(EBotSkill.WHEEL_VELOCITY);
		
		if (in.length < 4)
		{
			return;
		}
		
		for (int i = 0; i < 4; i++)
		{
			vel[i] = (int) (in[i] * 200.0);
		}
	}
	
	
	/**
	 * @param i Wheel Index
	 * @return
	 */
	public double getWheelVelocity(final int i)
	{
		if ((i > 3) || (i < 0))
		{
			return 0;
		}
		
		return vel[i] * 0.005;
	}
	
	
	/**
	 * @return
	 */
	public double[] getVelocities()
	{
		double[] result = new double[4];
		
		for (int i = 0; i < 4; i++)
		{
			result[i] = vel[i] * 0.005;
		}
		
		return result;
	}
}
