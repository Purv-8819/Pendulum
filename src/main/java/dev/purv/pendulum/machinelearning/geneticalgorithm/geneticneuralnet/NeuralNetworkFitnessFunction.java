package dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.NeuralNetwork;

@FunctionalInterface
public interface NeuralNetworkFitnessFunction {
   double calculateFitness(NeuralNetwork neuralNet);
}
