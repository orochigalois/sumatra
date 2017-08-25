/*
 * *********************************************************
 * Copyright (c) 2009 - 2013, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: 23.10.2013
 * Author(s): AndreR
 * *********************************************************
 */
package edu.tigers.sumatra.botmanager.commands;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;
import com.github.g3force.instanceables.InstanceableParameter;

import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationACommand;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationAuth;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationConfigV2;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationEthStats;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationPing;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationWifiStats;
import edu.tigers.sumatra.botmanager.commands.tiger.TigerSystemPing;
import edu.tigers.sumatra.botmanager.commands.tiger.TigerSystemPong;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerBootloaderCheckForUpdates;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerBootloaderData;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerCtrlSetControllerType;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemAck;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemConsoleCommand;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemConsolePrint;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemMatchCtrl;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemMatchFeedback;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemQuery;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemQuery.EQueryType;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerBootloaderCrc;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerBootloaderRequestCrc;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerBootloaderRequestData;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerBootloaderRequestSize;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerBootloaderSize;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigFileStructure;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigItemDesc;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigQueryFileList;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigRead;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigWrite;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerStatusFeedbackPid;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerSystemBotSkill;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerSystemPerformance;


/**
 * List of all commands with their class type.
 * 
 * @author AndreR
 */
public enum ECommand implements IInstanceableEnum
{
	// ### 00 - SYSTEM ###
	/** */
	CMD_SYSTEM_PING(0x0006, new InstanceableClass(TigerSystemPing.class)),
	/** */
	CMD_SYSTEM_PONG(0x0007, new InstanceableClass(TigerSystemPong.class)),
	/** */
	CMD_SYSTEM_CONSOLE_PRINT(0x000A, new InstanceableClass(TigerSystemConsolePrint.class)),
	/** */
	CMD_SYSTEM_CONSOLE_COMMAND(0x000B, new InstanceableClass(TigerSystemConsoleCommand.class)),
	/** */
	CMD_SYSTEM_MATCH_CTRL(0x000D, new InstanceableClass(TigerSystemMatchCtrl.class)),
	/** */
	CMD_SYSTEM_MATCH_FEEDBACK(0x000E, new InstanceableClass(TigerSystemMatchFeedback.class)),
	/** */
	CMD_SYSTEM_PERFORMANCE(0x000F, new InstanceableClass(TigerSystemPerformance.class)),
	/** */
	CMD_SYSTEM_ACK(0x00F0, new InstanceableClass(TigerSystemAck.class)),
	/**  */
	CMD_SYSTEM_QUERY(0x0010, new InstanceableClass(TigerSystemQuery.class, new InstanceableParameter(
			EQueryType.class, "CTRL_PID", "EQueryType"))),
	/**  */
	CMD_SYSTEM_BOT_SKILL(0x0012, new InstanceableClass(TigerSystemBotSkill.class)),
	
	// ### 04 - KICKER ###
	
	// ### 07 - CTRL ###
	/** */
	CMD_CTRL_SET_CONTROLLER_TYPE(0x0705, new InstanceableClass(TigerCtrlSetControllerType.class)),
	
	// ### 08 - BASE_STATION ###
	/** */
	CMD_BASE_ACOMMAND(0x0800, new InstanceableClass(BaseStationACommand.class)),
	/** */
	CMD_BASE_PING(0x0801, new InstanceableClass(BaseStationPing.class)),
	/** */
	CMD_BASE_AUTH(0x0802, new InstanceableClass(BaseStationAuth.class)),
	/** */
	CMD_BASE_WIFI_STATS(0x0806, new InstanceableClass(BaseStationWifiStats.class)),
	/** */
	CMD_BASE_ETH_STATS(0x0807, new InstanceableClass(BaseStationEthStats.class)),
	/** */
	CMD_BASE_CONFIG_V2(0x0808, new InstanceableClass(BaseStationConfigV2.class)),
	
	// ### 09 - BOOTLOADER ###
	/** */
	CMD_BOOTLOADER_CHECK_FOR_UPDATES(0x0900, new InstanceableClass(TigerBootloaderCheckForUpdates.class)),
	/** */
	CMD_BOOTLOADER_REQUEST_SIZE(0x0901, new InstanceableClass(TigerBootloaderRequestSize.class)),
	/** */
	CMD_BOOTLOADER_REQUEST_CRC(0x0902, new InstanceableClass(TigerBootloaderRequestCrc.class)),
	/** */
	CMD_BOOTLOADER_REQUEST_DATA(0x0903, new InstanceableClass(TigerBootloaderRequestData.class)),
	/** */
	CMD_BOOTLOADER_SIZE(0x0904, new InstanceableClass(TigerBootloaderSize.class)),
	/** */
	CMD_BOOTLOADER_CRC(0x0905, new InstanceableClass(TigerBootloaderCrc.class)),
	/** */
	CMD_BOOTLOADER_DATA(0x0906, new InstanceableClass(TigerBootloaderData.class)),
	
	// ### 11 - CONFIG ###
	/** */
	CMD_CONFIG_QUERY_FILE_LIST(0x0B00, new InstanceableClass(TigerConfigQueryFileList.class)),
	/** */
	CMD_CONFIG_FILE_STRUCTURE(0x0B01, new InstanceableClass(TigerConfigFileStructure.class)),
	/** */
	CMD_CONFIG_ITEM_DESC(0x0B02, new InstanceableClass(TigerConfigItemDesc.class)),
	/** */
	CMD_CONFIG_READ(0x0B03, new InstanceableClass(TigerConfigRead.class)),
	/** */
	CMD_CONFIG_WRITE(0x0B04, new InstanceableClass(TigerConfigWrite.class)),
	
	// ### 12 - status feedback ###
	/**  */
	CMD_STATUS_FEEDBACK_PID(0x0C00, new InstanceableClass(TigerStatusFeedbackPid.class)), ;
	private final InstanceableClass	clazz;
	private final int						id;
	
	
	/**
	 */
	private ECommand(final int id, final InstanceableClass clazz)
	{
		this.clazz = clazz;
		this.id = id;
	}
	
	
	/**
	 * @return the paramImpls
	 */
	@Override
	public final InstanceableClass getInstanceableClass()
	{
		return clazz;
	}
	
	
	/**
	 * @return
	 */
	public Class<?> getClazz()
	{
		return clazz.getImpl();
	}
	
	
	/**
	 * @return
	 */
	public int getId()
	{
		return id;
	}
}
