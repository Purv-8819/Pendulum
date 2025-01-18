package dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.recombiner;

import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;

@FunctionalInterface
public interface Recombiner<T extends Individual<T>> {
   /**
    * Fill up population after the selection step and recombine different parents to reach goal size
    * @param population the populatin to fill back up
    * @param goalSize the size of population to reach
    */
   void recombine(Population<T> population, int goalSize);
}
