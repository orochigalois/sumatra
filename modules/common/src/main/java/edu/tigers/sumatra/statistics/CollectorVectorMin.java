/*
 * *********************************************************
 * Copyright (c) 2009 - 2015, DHBW Mannheim - Tigers Mannheim
 * Project: TIGERS - Sumatra
 * Date: Sep 10, 2015
 * Author(s): Nicolai Ommer <nicolai.ommer@gmail.com>
 * *********************************************************
 */
package edu.tigers.sumatra.statistics;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import edu.tigers.sumatra.math.IVector;
import edu.tigers.sumatra.math.VectorN;
import edu.tigers.sumatra.statistics.CollectorVectorMin.Accumulator;


/**
 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
 */
public class CollectorVectorMin implements Collector<IVector, Accumulator, VectorN>
{
	private final int	dim;
	
	
	/**
	 * @author Nicolai Ommer <nicolai.ommer@gmail.com>
	 * @param dim
	 */
	public CollectorVectorMin(final int dim)
	{
		this.dim = dim;
	}
	
	
	@Override
	public Supplier<Accumulator> supplier()
	{
		return Accumulator::new;
	}
	
	
	@Override
	public BiConsumer<Accumulator, IVector> accumulator()
	{
		return (acc, vec) -> {
			for (int i = 0; i < vec.getNumDimensions(); i++)
			{
				if (acc.vector.get(i) > vec.get(i))
				{
					acc.vector.set(i, vec.get(i));
				}
			}
		};
	}
	
	
	@Override
	public BinaryOperator<Accumulator> combiner()
	{
		return (v1, v2) -> {
			Accumulator acc = new Accumulator();
			for (int i = 0; i < Math.max(v1.vector.getNumDimensions(), v2.vector.getNumDimensions()); i++)
			{
				acc.vector.set(i, Math.min(v1.vector.get(i), v2.vector.get(i)));
			}
			return acc;
		};
	}
	
	
	@Override
	public Function<Accumulator, VectorN> finisher()
	{
		return acc -> new VectorN(acc.vector);
	}
	
	
	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics()
	{
		return EnumSet.of(Characteristics.UNORDERED);
	}
	
	protected class Accumulator
	{
		VectorN	vector	= new VectorN(dim).apply(f -> Double.MAX_VALUE);
	}
}
