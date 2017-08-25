/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: 21.11.2015
 * Author(s): Phillipp Mevenkamp <phillippmevenkamp@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.sim;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.math.IVector2;


/**
 * @author Phillipp Mevenkamp <phillippmevenkamp@gmail.com>
 */
public class SumatraBotPair
{
	private SumatraBot	botOne;
	private SumatraBot	botTwo;
	
	
	/**
	 * @param botOne
	 * @param botTwo
	 */
	public SumatraBotPair(final SumatraBot botOne, final SumatraBot botTwo)
	{
		this.botOne = botOne;
		this.botTwo = botTwo;
	}
	
	
	/**
	 * @return
	 */
	public List<SumatraBot> getBotPair()
	{
		List<SumatraBot> botPair = new ArrayList<SumatraBot>();
		
		botPair.add(botOne);
		botPair.add(botTwo);
		
		return botPair;
	}
	
	
	/**
	 * @return The vector between the two bots, seen from the first bot
	 */
	public IVector2 getVectorBetweenBots()
	{
		IVector2 returnVector = botOne.getPos().getXYVector();
		
		returnVector = returnVector.subtractNew(botTwo.getPos().getXYVector());
		
		return returnVector;
	}
	
}
