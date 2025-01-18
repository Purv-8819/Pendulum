package dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.mutator;

import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;

public interface Mutator <T extends Individual<T>> {
   /**
    * Mutatate the individuals in a population
    * @param population the population to mutate
    */
   void mutate(Population<T> population);
}
