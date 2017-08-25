/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: May 21, 2015
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.dhbw.mannheim.tigers.sumatra.view.ball;

import java.util.List;

import edu.tigers.sumatra.bot.EBotType;


/**
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public interface IBallAnalyserPanelObserver
{
	/**
	 * @param filename
	 */
	void onSave(String filename);
	
	
	/**
	 * @param record
	 * @param stopAutomatically
	 */
	void onRecord(boolean record, boolean stopAutomatically);
	
	
	/**
	 * @param selectedFiles
	 */
	void onDelete(List<String> selectedFiles);
	
	
	/**
	 * @param selectedFiles
	 */
	void onPlot(List<String> selectedFiles);
	
	
	/**
	 * @param selectedFiles
	 */
	void onCreateBallModel(List<String> selectedFiles);
	
	
	/**
	 * @param selectedFiles
	 * @param eBotType
	 */
	void onCreateKickModel(List<String> selectedFiles, EBotType eBotType);
	
	
	/**
	 * @param selectedFiles
	 */
	void onNewSelectedFile(List<String> selectedFiles);
	
	
	/**
	 * @param selectedFiles
	 */
	void onBallCorrector(List<String> selectedFiles);
	
	
	/**
	 * @param selectedFiles
	 */
	void onCopy(List<String> selectedFiles);
	
	
	/**
	 * @param selectedFiles
	 */
	void onKalman(List<String> selectedFiles);
	
}
