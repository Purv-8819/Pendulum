package dev.purv.pendulum.machinelearning.geneticalgorithm.geneticnueralnet;

import java.util.function.Supplier;

import dev.purv.pendulum.machinelearning.ai.nueralnetwork.NueralNetwork;

@FunctionalInterface
public interface NueralNetworkSupplier extends Supplier<NueralNetwork>{

}
