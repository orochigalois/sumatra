/*
 * *********************************************************
 * Copyright (c) 2009 - 2014, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: May 30, 2014
 * Author(s): Mark Geiger <Mark.Geiger@dlr.de>
 * *********************************************************
 */
package edu.tigers.sumatra.offensive.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import edu.tigers.sumatra.views.ISumatraView;
import net.miginfocom.swing.MigLayout;


/**
 * Main Panel for OffensiveStrategy from tactical Field.
 * 
 * @author Mark Geiger <Mark.Geiger@dlr.de>
 */
public class OffensiveStrategyPanel extends JPanel implements ISumatraView
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	/**  */
	private static final long				serialVersionUID	= -314343167523031597L;
	
	private JRadioButton						yellowRadioButton	= null;
	private JRadioButton						blueRadioButton	= null;
	
	private TeamOffensiveStrategyPanel	yellowPanel			= null;
	private TeamOffensiveStrategyPanel	bluePanel			= null;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	/**
	 */
	public OffensiveStrategyPanel()
	{
		setLayout(new MigLayout());
		
		yellowRadioButton = new JRadioButton("Yellow Team");
		blueRadioButton = new JRadioButton("Blue Team");
		
		yellowPanel = new TeamOffensiveStrategyPanel();
		bluePanel = new TeamOffensiveStrategyPanel();
		
		yellowRadioButton.setSelected(true);
		yellowRadioButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				if (blueRadioButton.isSelected())
				{
					blueRadioButton.setSelected(false);
					remove(2);
					add(yellowPanel, "span");
					revalidate();
					repaint();
				}
				yellowRadioButton.setSelected(true);
			}
		});
		blueRadioButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				if (yellowRadioButton.isSelected())
				{
					yellowRadioButton.setSelected(false);
					remove(2);
					add(bluePanel, "span");
					revalidate();
					repaint();
				}
				blueRadioButton.setSelected(true);
			}
		});
		
		add(yellowRadioButton);
		add(blueRadioButton, "wrap");
		add(yellowPanel, "span");
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	/**
	 * @return TeamOffensiveStrategyPanel of the yellow team
	 */
	public TeamOffensiveStrategyPanel getYellowStrategyPanel()
	{
		return yellowPanel;
	}
	
	
	/**
	 * @return TeamOffensiveStrategyPanel of the blue tem
	 */
	public TeamOffensiveStrategyPanel getBlueStrategyPanel()
	{
		return bluePanel;
	}
	
	
	@Override
	public List<JMenu> getCustomMenus()
	{
		final List<JMenu> menus = new ArrayList<JMenu>();
		return menus;
	}
	
	
	@Override
	public void onShown()
	{
	}
	
	
	@Override
	public void onHidden()
	{
	}
	
	
	@Override
	public void onFocused()
	{
	}
	
	
	@Override
	public void onFocusLost()
	{
	}
	
	
}
