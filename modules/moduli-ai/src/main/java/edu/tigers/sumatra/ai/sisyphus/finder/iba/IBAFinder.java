/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Jan 4, 2015
 * Author(s): Daniel Andres <andreslopez.daniel@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.ai.sisyphus.finder.iba;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.tigers.sumatra.ai.sisyphus.PathFinderInput;
import edu.tigers.sumatra.ai.sisyphus.data.Path;
import edu.tigers.sumatra.ai.sisyphus.finder.AFinder;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.path.IPath;


/**
 * Intelligent Bug Algorithm based on http://arxiv.org/pdf/1312.4552.pdf. <br />
 * Ticket #1304
 * 
 * @author Daniel Andres <andreslopez.daniel@gmail.com>
 */
public class IBAFinder extends AFinder
{
	
	private static final double	DEGREE90	= AngleMath.PI / 2.0;
	private static final double	DEGREE45	= AngleMath.PI / 4.0;
														
	/**
	 * use to switch the direction for stepAlongCircle. Randomly 1 or -1. Note: switching for each new call of calcPath
	 * and letting a PathFilter decide which is the best path, has shown, that the pathes are also switched and the bot
	 * does not know where to drive, therefore with this approach there can't be a shortest path. It is random based.
	 */
	private double						leftRightSwitch;
											
											
	/**
	 */
	public IBAFinder()
	{
		super();
		leftRightSwitch = Math.signum(Math.random() - 0.5);
		if (leftRightSwitch == 0.0)
		{
			leftRightSwitch = 1;
		}
	}
	
	
	@Override
	protected IPath doCalculation(final PathFinderInput pathFinderInput, final IVector2 start, final IVector2 target,
			final boolean isIntermediate, final boolean isSecondTry)
	{
		List<IVector2> pathPointList = new LinkedList<IVector2>();
		
		IVector2 current = start;
		IVector2 goal = pathFinderInput.getFieldInfo().getPreprocessedTarget();
		pathPointList.add(current);
		
		for (int i = 0; (i < getAdjustableParams().getMaxIterations()); i++)
		{
			// check direct line to goal and if true, break iteration. goal is added to path outside.
			if (pathFinderInput.getFieldInfo().isWayOK(current, goal))
			{
				// add current point if it wasn't saved yet
				if (!pathPointList.get(pathPointList.size() - 1).equals(current))
				{
					pathPointList.add(current);
				}
				break;
			}
			// Try to go on a direct line towards the goal
			IVector2 nextPoint = GeoMath.stepAlongLine(current, goal, getAdjustableParams().getStepSize());
			
			// if nextPoint is not possible, because there would be an obstacle, calculate another point
			if (!pathFinderInput.getFieldInfo().isWayOK(current, nextPoint))
			{
				// save current position as the path would work to here.
				pathPointList.add(nextPoint);
				
				// Get next point in 90° left or right from current point
				nextPoint = GeoMath.stepAlongLine(current, GeoMath.stepAlongCircle(nextPoint, current,
						(-leftRightSwitch * DEGREE90)), getAdjustableParams().getStepSize());
						
				// if nextPoint is not possible, because there would be an obstacle, go on
				if (!pathFinderInput.getFieldInfo().isWayOK(current, nextPoint))
				{
					// Get next point in 45° left or right from current point
					nextPoint = GeoMath.stepAlongLine(current, GeoMath.stepAlongCircle(nextPoint, current,
							(-leftRightSwitch * DEGREE45)), getAdjustableParams().getStepSize());
					// if nextPoint is not possible, because there would be an obstacle, go on and set rambo
//					if (!pathFinderInput.getFieldInfo().isWayOK(current, nextPoint))
//					{
//					}
					pathPointList.add(nextPoint);
					current = nextPoint;
					continue;
				}
				pathPointList.add(nextPoint);
				current = nextPoint;
				continue;
			}
			current = nextPoint;
		}
		
		pathPointList.add(goal);
		List<IVector2> unsmoothedPath = new ArrayList<IVector2>(pathPointList);
		smoothPath(pathPointList, pathFinderInput);
		
		addPathPointsFromAdjustments(pathPointList, pathFinderInput, target);
		
		Path newPath = new Path(pathPointList, pathFinderInput.getDstOrient());
		newPath.setUnsmoothedPathPoints(unsmoothedPath);
		
		return newPath;
	}
}