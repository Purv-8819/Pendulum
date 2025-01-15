package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.evolution.mutator;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;

public interface Mutator <T extends Individual<T>> {
   /**
    * Mutatate the individuals in a population
    * @param population the population to mutate
    */
   void mutate(Population<T> population);
}
