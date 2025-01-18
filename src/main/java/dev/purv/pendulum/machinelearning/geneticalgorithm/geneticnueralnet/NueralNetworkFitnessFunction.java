package dev.purv.pendulum.machinelearning.geneticalgorithm.geneticnueralnet;

import dev.purv.pendulum.machinelearning.ai.nueralnetwork.NueralNetwork;

@FunctionalInterface
public interface NueralNetworkFitnessFunction {
   double calculateFitness(NueralNetwork nueralNet);
}
