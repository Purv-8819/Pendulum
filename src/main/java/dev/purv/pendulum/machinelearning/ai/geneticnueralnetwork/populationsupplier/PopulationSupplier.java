package dev.purv.pendulum.machinelearning.ai.geneticnueralnetwork.populationsupplier;

import java.util.function.Supplier;

import dev.purv.pendulum.machinelearning.ai.geneticnueralnetwork.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticnueralnetwork.Population;

@FunctionalInterface
public interface PopulationSupplier<T extends Individual<T>> extends Supplier<Population<T>> {
   
}
