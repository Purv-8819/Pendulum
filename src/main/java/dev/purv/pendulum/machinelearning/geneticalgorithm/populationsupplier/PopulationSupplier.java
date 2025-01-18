package dev.purv.pendulum.machinelearning.geneticalgorithm.populationsupplier;

import java.util.function.Supplier;

import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;

@FunctionalInterface
public interface PopulationSupplier<T extends Individual<T>> extends Supplier<Population<T>> {
   
}
