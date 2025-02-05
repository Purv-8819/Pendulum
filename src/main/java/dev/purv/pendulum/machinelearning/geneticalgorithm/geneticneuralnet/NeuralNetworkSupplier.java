package dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet;

import java.util.function.Supplier;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.NeuralNetwork;

@FunctionalInterface
public interface NeuralNetworkSupplier extends Supplier<NeuralNetwork>{

}
