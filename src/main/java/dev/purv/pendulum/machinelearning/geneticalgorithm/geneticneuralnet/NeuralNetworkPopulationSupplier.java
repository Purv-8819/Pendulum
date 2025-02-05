package dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;
import dev.purv.pendulum.machinelearning.geneticalgorithm.populationsupplier.PopulationSupplier;

public class NeuralNetworkPopulationSupplier implements PopulationSupplier<NeuralNetworkIndividual> {
   //Attributes
   private final NeuralNetworkSupplier nueralNetSupplier;
   private final int populationSize;
   private final NeuralNetworkFitnessFunction fitnessFunc;

   //Constructor
   public NeuralNetworkPopulationSupplier(NeuralNetworkSupplier nns, int populationSize, NeuralNetworkFitnessFunction nnff){
      this.nueralNetSupplier = nns;
      this.populationSize = populationSize;
      this.fitnessFunc = nnff;
   }

   //Methods
   @Override
   public Population<NeuralNetworkIndividual> get(){
      List<NeuralNetworkIndividual> indivs = IntStream.range(0, populationSize).mapToObj(i -> new NeuralNetworkIndividual(nueralNetSupplier.get(), this.fitnessFunc)).collect(Collectors.toList());

      return new Population<NeuralNetworkIndividual>(indivs);
   }
}
