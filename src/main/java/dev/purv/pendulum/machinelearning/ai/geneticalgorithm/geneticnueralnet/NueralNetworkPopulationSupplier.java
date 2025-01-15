package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.geneticnueralnet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.populationsupplier.PopulationSupplier;

public class NueralNetworkPopulationSupplier implements PopulationSupplier<NueralNetworkIndividual> {
   //Attributes
   private final NueralNetworkSupplier nueralNetSupplier;
   private final int populationSize;
   private final NueralNetworkFitnessFunction fitnessFunc;

   //Constructor
   public NueralNetworkPopulationSupplier(NueralNetworkSupplier nns, int populationSize, NueralNetworkFitnessFunction nnff){
      this.nueralNetSupplier = nns;
      this.populationSize = populationSize;
      this.fitnessFunc = nnff;
   }

   //Methods
   @Override
   public Population<NueralNetworkIndividual> get(){
      List<NueralNetworkIndividual> indivs = IntStream.range(0, populationSize).mapToObj(i -> new NueralNetworkIndividual(nueralNetSupplier.get(), this.fitnessFunc)).collect(Collectors.toList());

      return new Population<NueralNetworkIndividual>(indivs);
   }
}
