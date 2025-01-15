package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.evolution.selector;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;

public interface Selector <T extends Individual<T>>{
   /**
    * Select the best from the population
    * Discard the worst
    * @param population the population to select from
    */
   void select(Population<T> population);
}
