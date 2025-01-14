package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.populationsupplier;

import java.util.function.Supplier;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;

@FunctionalInterface
public interface PopulationSupplier<T extends Individual<T>> extends Supplier<Population<T>> {
   
}
