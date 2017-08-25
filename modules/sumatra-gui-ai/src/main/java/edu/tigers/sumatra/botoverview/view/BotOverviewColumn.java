/*
 * *********************************************************
 * Copyright (c) 2009 - 2014, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Jan 12, 2014
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.botoverview.view;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.data.BotAiInformation;
import edu.tigers.sumatra.botoverview.BotOverviewTableModel;


/**
 * Dataholder for a bot overview column in {@link BotOverviewTableModel}
 * 
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class BotOverviewColumn
{
	/**  */
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	public static final int		ROWS	= 15;
	
	private final List<Object>	data	= new ArrayList<Object>(ROWS);
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	/**
	 * @param botAiInformation
	 */
	public BotOverviewColumn(final BotAiInformation botAiInformation)
	{
		addData(String.format("%.0f%% Bat.", botAiInformation.getBattery() * 100));
		addData(String.format("%.1fV Cond.", botAiInformation.getKickerCharge()));
		addData(String.format("%.0f/%.0f Pos.", botAiInformation.getPos().x(), botAiInformation.getPos().y()));
		addData(String.format("%.1fm/s Vel.", botAiInformation.getVel().getLength2()));
		addData(String.format("%.1fm/s Max", botAiInformation.getMaxVel()));
		addData(botAiInformation.getLimits());
		addData(botAiInformation.isBallContact() ? "ballcont." : "no ballcont.");
		addData(String.format("%.0frpm/s", botAiInformation.getDribbleSpeed()));
		addData(String.format("%.1f %s", botAiInformation.getKickSpeed(), botAiInformation.getDevice()));
		addData(botAiInformation.getBrokenFeatures());
		addData(botAiInformation.getPlay());
		addData(botAiInformation.getRole());
		addData(botAiInformation.getRoleState());
		addData(botAiInformation.getSkill());
		addData(botAiInformation.getSkillState());
		addData(botAiInformation.getSkillDriver());
		int size = data.size();
		for (int i = 0; i < (ROWS - size); i++)
		{
			addData(null);
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private void addData(final Object obj)
	{
		if (obj == null)
		{
			data.add("");
		} else
		{
			data.add(obj);
		}
	}
	
	
	/**
	 * @return
	 */
	public List<Object> getData()
	{
		return data;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((data == null) ? 0 : data.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		BotOverviewColumn other = (BotOverviewColumn) obj;
		if (data == null)
		{
			if (other.data != null)
			{
				return false;
			}
		} else if (!data.equals(other.data))
		{
			return false;
		}
		return true;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
