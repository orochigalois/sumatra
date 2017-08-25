/*
 * *********************************************************
 * Copyright (c) 2009 - 2013, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Oct 11, 2013
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.ai.data.frames;

import edu.tigers.sumatra.ai.data.ITacticalField;


/**
 * This frame extends the {@link BaseAiFrame} with information gathered from Metis module.
 * 
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class MetisAiFrame extends BaseAiFrame
{
	/** stores all tactical information added by metis' calculators. */
	private final ITacticalField	tacticalField;
	
	
	/**
	 * @param baseAiFrame
	 * @param tacticalInfo
	 */
	public MetisAiFrame(final BaseAiFrame baseAiFrame, final ITacticalField tacticalInfo)
	{
		super(baseAiFrame);
		tacticalField = tacticalInfo;
	}
	
	
	/**
	 * @return the tacticalInfo
	 */
	public ITacticalField getTacticalField()
	{
		return tacticalField;
	}
}
