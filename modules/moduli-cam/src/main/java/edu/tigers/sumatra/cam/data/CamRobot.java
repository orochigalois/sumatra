/*
 * *********************************************************
 * Copyright (c) 2009 - 2010, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: 21.07.2010
 * Author(s): Gero
 * *********************************************************
 */
package edu.tigers.sumatra.cam.data;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.AObjectID;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2f;


/**
 * Simple data holder; internal data structure for the
 * SSL_DetectionRobot from
 * protobuf-protocol, coming from
 * the SSL-Vision.
 * <p>
 * <i>(Being aware of EJ-SE Items 13, 14 and 55: members are public to reduce noise)</i>
 * </p>
 * 
 * @author Gero
 */
@Persistent
public class CamRobot extends ACamObject
{
	private final BotID		botId;
	private final IVector2	pos;
	private final double		orientation;
	private final double		height;
									
									
	/**
	 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
	 */
	public CamRobot()
	{
		super();
		botId = BotID.get();
		pos = AVector2.ZERO_VECTOR;
		orientation = 0;
		height = 0;
	}
	
	
	/**
	 * <p>
	 * <i>(Being aware of EJ-SE Item 2; but we prefer performance over readability - at least in this case. Objects are
	 * created at only one point in the system, but needs to be fast.</i>
	 * </p>
	 * 
	 * @param confidence
	 * @param pixelX
	 * @param pixelY
	 * @param tCapture
	 * @param tSent
	 * @param camId
	 * @param frameId
	 * @param x
	 * @param y
	 * @param orientation
	 * @param height
	 * @param botId
	 */
	public CamRobot(final double confidence,
			final double pixelX,
			final double pixelY,
			final long tCapture,
			final long tSent,
			final int camId,
			final long frameId,
			final double x,
			final double y,
			final double orientation,
			final double height,
			final BotID botId)
	{
		super(confidence, pixelX, pixelY, tCapture, tSent, camId, frameId);
		pos = new Vector2f(x, y);
		this.orientation = orientation;
		this.height = height;
		this.botId = botId;
	}
	
	
	/**
	 * @param list
	 * @return
	 */
	public static CamRobot fromNumberList(final List<? extends Number> list)
	{
		final long tCapture = list.get(0).longValue();
		final int camId = list.get(1).intValue();
		
		final int id = list.size() <= 2 ? AObjectID.UNINITIALIZED_ID : list.get(2).intValue();
		ETeamColor color = list.size() <= 3 ? ETeamColor.UNINITIALIZED : ETeamColor.fromNumberList(list.get(3));
		BotID botId = BotID.createBotId(id, color);
		
		final double x = list.get(4).doubleValue();
		final double y = list.get(5).doubleValue();
		final double orientation = list.get(6).doubleValue();
		final long frameId = list.size() <= 7 ? 0 : list.get(7).longValue();
		final double pixelX = list.size() <= 8 ? 0 : list.get(8).doubleValue();
		final double pixelY = list.size() <= 9 ? 0 : list.get(9).doubleValue();
		final double height = list.size() <= 10 ? 0 : list.get(10).intValue();
		final double confidence = list.size() <= 11 ? 0 : list.get(11).doubleValue();
		
		final long tSent = list.size() <= 12 ? 0 : list.get(12).longValue();
		
		return new CamRobot(confidence, pixelX, pixelY, tCapture, tSent, camId, frameId, x, y, orientation, height,
				botId);
	}
	
	
	@Override
	public ECamObjectType implementation()
	{
		return ECamObjectType.Robot;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SSLRobot [confidence=");
		builder.append(getConfidence());
		builder.append(", height=");
		builder.append(getHeight());
		builder.append(", orientation=");
		builder.append(getOrientation());
		builder.append(", pixelX=");
		builder.append(getPixelX());
		builder.append(", pixelY=");
		builder.append(getPixelY());
		builder.append(", robotID=");
		builder.append(getRobotID());
		builder.append(", x=");
		builder.append(getPos().x());
		builder.append(", y=");
		builder.append(getPos().y());
		builder.append("]");
		return builder.toString();
	}
	
	
	/**
	 * @return the robotID
	 */
	public int getRobotID()
	{
		return botId.getNumber();
	}
	
	
	/**
	 * @return the pos
	 */
	@Override
	public IVector2 getPos()
	{
		return pos;
	}
	
	
	/**
	 * @return the orientation
	 */
	public double getOrientation()
	{
		return orientation;
	}
	
	
	/**
	 * @return the height
	 */
	public double getHeight()
	{
		return height;
	}
	
	
	@Override
	public List<Number> getNumberList()
	{
		List<Number> numbers = new ArrayList<>();
		numbers.add(gettCapture());
		numbers.add(getCameraId());
		numbers.add(getRobotID());
		numbers.addAll(botId.getTeamColor().getNumberList());
		numbers.addAll(pos.getNumberList());
		numbers.add(getOrientation());
		numbers.add(getFrameId());
		numbers.add(getPixelX());
		numbers.add(getPixelY());
		numbers.add(getHeight());
		numbers.add(getConfidence());
		numbers.add(gettSent());
		return numbers;
	}
	
	
	/**
	 * @return the botId
	 */
	public BotID getBotId()
	{
		return botId;
	}
}
