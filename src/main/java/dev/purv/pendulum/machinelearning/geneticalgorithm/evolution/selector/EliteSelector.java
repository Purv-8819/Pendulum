package dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.selector;

import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;

public class EliteSelector <T extends Individual<T>> extends PercentageSelector<T>{
   //Constructor
   public EliteSelector(double percent){
      //Call parent
      super(percent);
   }

   //Methods
   @Override
   public void select(Population<T> population){
      population.sortPopulationByFitness();
      repopulate(population);
   }

   private void repopulate(Population<T> population){
      int goalSize = super.calcGoalSize(population.getSize());
      //While larger than goal size remove element at goal size index
      while(population.getSize() > goalSize){
         population.getIndividuals().remove(goalSize);
      }
   }
}
