/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Apr 1, 2015
 * Author(s): FelixB <bayer.fel@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.ai.metis.defense.algorithms.interfaces;

import java.util.List;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.defense.data.FoeBotData;


/**
 * TODO FelixB <bayer.fel@gmail.com>, add comment!
 * - What should this type do (in one sentence)?
 * - If not intuitive: A simple example how to use this class
 * 
 * @author FelixB <bayer.fel@gmail.com>
 */
public interface IFoeBotCalc
{
	/**
	 * This method shall return a list of FoeBotDatas describing the enemy bots, sorted by danger.
	 * 
	 * @param newTacticalField
	 * @param baseAiFrame
	 * @return
	 */
	public List<FoeBotData> getFoeBotData(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame);
}
