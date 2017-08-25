/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Aug 7, 2015
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.dhbw.mannheim.tigers.sumatra.presenter.botcenter;

import java.awt.EventQueue;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.BotCenterPanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.basestation.BaseStationControlPanel.IBaseStationControlPanelObserver;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.basestation.BaseStationPanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.TigerBotV2Summary;
import edu.tigers.moduli.IModuliStateObserver;
import edu.tigers.moduli.listenerVariables.ModulesState;
import edu.tigers.sumatra.bot.EFeature;
import edu.tigers.sumatra.botmanager.basestation.IBaseStation;
import edu.tigers.sumatra.botmanager.basestation.IBaseStationObserver;
import edu.tigers.sumatra.botmanager.bots.communication.ENetworkState;
import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationEthStats;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationWifiStats;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemMatchFeedback;
import edu.tigers.sumatra.ids.BotID;


/**
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class BaseStationPresenter implements IBaseStationControlPanelObserver, IBaseStationObserver,
		IModuliStateObserver
{
	private final IBaseStation					baseStation;
	private final FirmwareUpdatePresenter	firmwareUpdatePresenter;
	private final BotCenterPanel				botCenter;
	private final BaseStationPanel			bsPanel;
	
	
	/**
	 * @param baseStation
	 * @param botCenter
	 */
	public BaseStationPresenter(final IBaseStation baseStation, final BotCenterPanel botCenter)
	{
		this.baseStation = baseStation;
		this.botCenter = botCenter;
		bsPanel = new BaseStationPanel(baseStation.getName());
		firmwareUpdatePresenter = new FirmwareUpdatePresenter(bsPanel.getFirmwareUpdatePanel(), baseStation);
		
		
	}
	
	
	@Override
	public void onConnectionChange(final boolean connect)
	{
		if (connect)
		{
			baseStation.connect();
		} else
		{
			baseStation.disconnect();
		}
	}
	
	
	@Override
	public void onIncommingBotCommand(final BotID id, final ACommand command)
	{
		switch (command.getType())
		{
			case CMD_SYSTEM_MATCH_FEEDBACK:
				final TigerSystemMatchFeedback feedback = (TigerSystemMatchFeedback) command;
				EventQueue.invokeLater(() -> {
					TigerBotV2Summary summ = (TigerBotV2Summary) botCenter.getOverviewPanel().getBotPanel(id);
					if (summ == null)
					{
						return;
					}
					summ.setBatteryLevel(feedback.getBatteryLevel());
					summ.setCap(feedback.getKickerLevel());
				});
				
				for (EFeature feature : EFeature.values())
				{
					boolean working = feedback.isFeatureWorking(feature);
					botCenter.getFeaturePanel().setFeatureState(id, feedback.getHardwareId(), feature, working);
				}
				botCenter.getFeaturePanel().setHWIdSet(id, feedback.getHardwareId() != 255);
				botCenter.getFeaturePanel().update();
				break;
			default:
				break;
		}
	}
	
	
	@Override
	public void onNewBaseStationWifiStats(final BaseStationWifiStats stats)
	{
		EventQueue.invokeLater(() -> {
			bsPanel.getWifiStatsPanel().setStats(stats);
		});
	}
	
	
	@Override
	public void onNewBaseStationEthStats(final BaseStationEthStats stats)
	{
		EventQueue.invokeLater(() -> {
			bsPanel.getEthStatsPanel().setStats(stats);
			bsPanel.getNtpStatsPanel().setStats(stats);
		});
	}
	
	
	@Override
	public void onNetworkStateChanged(final ENetworkState netState)
	{
		EventQueue.invokeLater(() -> {
			bsPanel.getControlPanel().setConnectionState(netState);
		});
	}
	
	
	@Override
	public void onNewPingDelay(final double delay)
	{
		EventQueue.invokeLater(() -> {
			bsPanel.getControlPanel().setPingDelay(delay);
		});
	}
	
	
	@Override
	public void onStartPing(final int numPings, final int payload)
	{
		baseStation.startPing(numPings, payload);
	}
	
	
	@Override
	public void onStopPing()
	{
		baseStation.stopPing();
	}
	
	
	@Override
	public void onModuliStateChanged(final ModulesState state)
	{
		switch (state)
		{
			case ACTIVE:
			{
				EventQueue.invokeLater(() -> {
					bsPanel.getControlPanel().setConnectionState(baseStation.getNetState());
				});
				baseStation.addObserver(this);
				bsPanel.getControlPanel().addObserver(this);
				break;
			}
			case NOT_LOADED:
				break;
			case RESOLVED:
			{
				baseStation.removeObserver(this);
				bsPanel.getControlPanel().removeObserver(this);
				
				EventQueue.invokeLater(() -> {
					bsPanel.getControlPanel().setConnectionState(ENetworkState.OFFLINE);
				});
				break;
			}
			default:
				break;
		}
		firmwareUpdatePresenter.onModuliStateChanged(state);
	}
	
	
	/**
	 * @return the bsPanel
	 */
	public final BaseStationPanel getBsPanel()
	{
		return bsPanel;
	}
}
