package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.evolution.mutator;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;

public interface Mutator <T extends Individual<T>> {
   void mutate(Population<T> pop);
}
