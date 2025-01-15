package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.evolution.recombiner;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;

public class FillUpRecombiner<T extends Individual<T>> implements Recombiner<T>{

   @Override
   public void recombine(Population<T> pop, int goalSize){
      //Get the current individuals
      List<T> indivs = pop.getIndividuals();
      //While there are less than the goal size get a random index and add a copy of that random individual ot the individuals
      while (indivs.size() < goalSize) {
         int randIndex = ThreadLocalRandom.current().nextInt(indivs.size());
         indivs.add(indivs.get(randIndex).copy());
      }
      //Set the individuals to the recombined
      pop.setIndividuals(indivs);
   }
}
