/*
 * *********************************************************
 * Copyright (c) 2009 - 2011, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: 10.11.2011
 * Author(s): osteinbrecher
 * *********************************************************
 */
package edu.tigers.sumatra.ids;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * This is a unit test for {@link AObjectID} and sub classes.
 * 
 * @author Oliver Steinbrecher
 */
public class ObjectIDTester
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	/**
	 */
	@Test
	public void testID()
	{
		final BotID botId = BotID.createBotId(1, ETeamColor.YELLOW);
		
		BotID botId2 = botId;
		assertTrue(botId.equals(botId2));
		
		botId2 = BotID.createBotId(1, ETeamColor.YELLOW);
		assertTrue(botId.equals(botId2));
		
		botId2 = BotID.createBotId(2, ETeamColor.YELLOW);
		assertFalse(botId.equals(botId2));
		
		assertFalse(botId.equals(null));
		assertFalse(botId.equals(new BallID()));
		assertFalse(botId.equals(this));
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
