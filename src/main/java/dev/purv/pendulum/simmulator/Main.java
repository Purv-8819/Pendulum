package dev.purv.pendulum.simmulator;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.NeuralNetwork;
import dev.purv.pendulum.machinelearning.geneticalgorithm.GeneticAlgorithm;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.mutator.Mutator;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.mutator.NeuralNetworkRandomMutator;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.recombiner.NeuralNetworkUniformCrossoverRecombiner;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.recombiner.Recombiner;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.selector.EliteSelector;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.selector.RouletteWheelSelector;
import dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.selector.Selector;
import dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet.NeuralNetworkFitnessFunction;
import dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet.NeuralNetworkIndividual;
import dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet.NeuralNetworkPopulationSupplier;
import dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet.NeuralNetworkSupplier;
import dev.purv.pendulum.machinelearning.geneticalgorithm.populationsupplier.PopulationSupplier;
import dev.purv.pendulum.machinelearning.linearalgebra.Randomizer;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.System;
import java.util.List;

public class Main {

   public static final int MAX_GENS = 1000;
   public static final Selector<NeuralNetworkIndividual> SELECTOR = new EliteSelector<>(0.2);
   public static final Recombiner<NeuralNetworkIndividual> RECOMBINER = new NeuralNetworkUniformCrossoverRecombiner(2);
   public static final Mutator<NeuralNetworkIndividual> MUTATOR = new NeuralNetworkRandomMutator(0.75, 0.5, new Randomizer(-0.3, 0.3), 0.1);

   public static void main(String[] args) {
      NeuralNetworkSupplier nn = () -> new NeuralNetwork.Builder(6, 4)
              .addLayers(5, 5)
              .build();
      NeuralNetworkFitnessFunction fitnessFunction = (neuralNet) -> new PendulumAi(neuralNet).startPlaying(500);
      PopulationSupplier<NeuralNetworkIndividual> populationSupplier = new NeuralNetworkPopulationSupplier(nn, 1000, fitnessFunction);
      GeneticAlgorithm<NeuralNetworkIndividual> geneticAlgorithm = new GeneticAlgorithm.Builder<>(MAX_GENS, populationSupplier, SELECTOR).
              withMutator(MUTATOR)
              .withRecombiner(RECOMBINER)
              .build();

      NeuralNetwork best = geneticAlgorithm.solve().getNueralNetwork();

      List<Cart.Move> list = new PendulumAi(best).getMoveList(500);
      try {
         FileWriter writer = new FileWriter("output.txt");
         for(int i = 0; i<list.size(); i++){
            switch (list.get(i)){
               case NOTHING -> writer.write("NOTHING");
               case LEFT -> writer.write("LEFT");
               case RIGHT -> writer.write("RIGHT");
               case BRAKE -> writer.write("BRAKE");
            }
         }
         writer.close();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
   }

}
