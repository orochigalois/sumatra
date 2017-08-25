/*
 * *********************************************************
 * Copyright (c) 2009 - 2014, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Jan 29, 2014
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.ai.pandora.roles.test;

import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.skillsystem.skills.AMoveToSkill;
import edu.tigers.sumatra.statemachine.IRoleState;


/**
 * Test role for testing fast changing destinations
 * 
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class DestChangedTestRole extends ARole
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final IVector2	diffDest;
	private final IVector2	diffLookAt;
	private final int			freq;
	private long				lastTimestamp	= 0;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	/**
	 * @param diffDest
	 * @param diffLookAt
	 * @param freq
	 */
	public DestChangedTestRole(final IVector2 diffDest, final IVector2 diffLookAt, final int freq)
	{
		super(ERole.DEST_CHANGED);
		this.diffDest = diffDest;
		this.diffLookAt = diffLookAt;
		this.freq = freq * 1000_000;
		setInitialState(new MainState());
	}
	
	
	// --------------------------------------------------------------------------
	// --- states ---------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	private enum EStateId
	{
		MAIN
	}
	
	
	private class MainState implements IRoleState
	{
		private AMoveToSkill	skill	= null;
		
		
		@Override
		public void doEntryActions()
		{
			skill = AMoveToSkill.createMoveToSkill();
			skill.getMoveCon().updateDestination(getPos());
			skill.getMoveCon().updateLookAtTarget(AVector2.ZERO_VECTOR);
			setNewSkill(skill);
		}
		
		
		@Override
		public void doUpdate()
		{
			if ((getWFrame().getTimestamp() - lastTimestamp) > freq)
			{
				IVector2 random = new Vector2((Math.random() * diffDest.x()), (Math.random() * diffDest.y()));
				skill.getMoveCon().updateDestination(random);
				IVector2 lookAt = new Vector2(-200 + (Math.random() * diffLookAt.x()), -1500
						+ (Math.random() * diffLookAt.y()));
				skill.getMoveCon().updateLookAtTarget(lookAt);
				lastTimestamp = getWFrame().getTimestamp();
			}
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.MAIN;
		}
	}
}
