package dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.selector;

import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;

public interface Selector <T extends Individual<T>>{
   /**
    * Select the best from the population
    * Discard the worst
    * @param population the population to select from
    */
   void select(Population<T> population);
}
