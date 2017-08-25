/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Jul 10, 2015
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.skillsystem;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;


/**
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class AsyncExecution
{
	@SuppressWarnings("unused")
	private static final Logger	log	= Logger.getLogger(AsyncExecution.class.getName());
	
	private final ExecutorService	threadPool;
	private Future<?>					future;
	
	
	/**
	 * @param threadPool
	 */
	public AsyncExecution(final ExecutorService threadPool)
	{
		this.threadPool = threadPool;
	}
	
	
	/**
	 * @param run
	 */
	public void executeAsynchronously(final Runnable run)
	{
		boolean done = (future != null) && future.isDone();
		
		if ((future != null) && done)
		{
			try
			{
				future.get();
			} catch (InterruptedException err)
			{
				log.error("Interrupted...", err);
			} catch (ExecutionException err)
			{
				log.error("Error during path calculation.", err);
			}
		}
		
		if ((future == null) || done)
		{
			// submit new task, if no other is running
			future = threadPool.submit(run);
		}
	}
}
