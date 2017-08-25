/*
 * *********************************************************
 * Copyright (c) 2009 - 2016, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Jan 23, 2016
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.trajectory;

import com.sleepycat.persist.model.Persistent;


/**
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 * @param <RETURN_TYPE>
 */
@Persistent
public class TrajectoryWithTime<RETURN_TYPE> extends TrajWTimeImpl<RETURN_TYPE, ITrajectory<RETURN_TYPE>>
{
	@SuppressWarnings("unused")
	private TrajectoryWithTime()
	{
		super();
	}
	
	
	/**
	 * @param trajectory
	 * @param tStart
	 */
	public TrajectoryWithTime(final ITrajectory<RETURN_TYPE> trajectory, final long tStart)
	{
		super(trajectory, tStart);
	}
}
