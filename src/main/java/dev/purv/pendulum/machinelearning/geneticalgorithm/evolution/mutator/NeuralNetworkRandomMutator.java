package dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.mutator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.NeuralNetwork;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;
import dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet.NeuralNetworkIndividual;
import dev.purv.pendulum.machinelearning.linearalgebra.Randomizer;

public class NeuralNetworkRandomMutator implements Mutator<NeuralNetworkIndividual>{
   //Attributes
   private final double outerMutationRate;
   private final DoubleUnaryOperator innerMutator;

   //Constructor
   /**
    * Constructor for a Mutator for nueral nets
    * @param outerMutRate mutation rate for whole nueral nets in percent of population
    * @param innerMutRate mutation rate of the inner componenets of the nueral net
    * @param mutationFactor The randomizer holding the min and max of the factor to mutate by
    * @param minMutatorAbsolute the absolute value of the min amount it shoudl be mutated
    */
   public NeuralNetworkRandomMutator(double outerMutRate, double innerMutRate, Randomizer mutationFactor, double minMutatorAbsolute){
      if(outerMutRate < 0 || outerMutRate > 1 || innerMutRate < 0 || innerMutRate > 1){
         throw new IllegalArgumentException("Mutation rate must be between 0 and 1 inclusive");
      }

      this.outerMutationRate = outerMutRate;
      this.innerMutator = d -> {
         if(ThreadLocalRandom.current().nextDouble() < innerMutRate){
            double mutatorVal = mutationFactor.getInRange();
            double sign = mutatorVal/Math.abs(mutatorVal);
            double factor = sign * Math.max(Math.abs(mutatorVal), minMutatorAbsolute);
            return factor + d;
         }else{
            return d;
         }
      };
   }

   //Methods
   
   @Override
   public void mutate(Population<NeuralNetworkIndividual> pop){
      Stream<NeuralNetworkIndividual> filtered = getFilteredIndividuals(pop);

      filtered.forEach(ind -> mutateNN(ind.getNueralNetwork()));
   }

   /**
    * Filter the individuals based on the outer mutation rate
    * @param pop the population to filer
    * @return a stream of the filterd individuals in the population
    */
   private Stream<NeuralNetworkIndividual> getFilteredIndividuals(Population<NeuralNetworkIndividual> pop){
      return pop.getIndividuals().stream().filter(ind -> ThreadLocalRandom.current().nextDouble() < outerMutationRate);
   }

   /**
    * Mutate the layers in the nueral network
    * @param nueralNet the nueral network to mutate
    */
   private void mutateNN(NeuralNetwork nueralNet){
      //For each layer in the nueral net, apply the inner mutator
      nueralNet.getLayers().stream().forEach(layer->
      {
         layer.getBias().apply(innerMutator);
         layer.getWeights().apply(innerMutator);
      });
   }


}
