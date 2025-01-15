package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.evolution.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;

public class RouletteWheelSelector <T extends Individual<T>> extends PercentageSelector<T>{

   //Attributes
   private final boolean ensureAddFirst;

   //Constructor
   public RouletteWheelSelector(double percent, boolean ensure){
      super(percent);
      this.ensureAddFirst = ensure;
   }

   //Methods
   @Override
   public void select(Population<T> popualtion){
      double totalFitness = calcTotalFitness(popualtion);
      List<Double> probabilityList = calcProbabilityList(popualtion, totalFitness);
      List<Individual<T>> repoulated = repopulate(popualtion, probabilityList);
      popualtion.replaceAllIndividuals(repoulated);
   }

   /**
    * Get the total fitness of every individual in the population
    * @param population population to get total fitness of
    * @return the total fitness
    */
   private double calcTotalFitness(Population<T> population){
      double sum = 0d;

      for(T indiv : population.getIndividuals()){
         sum += indiv.getFitness();
      }

      return sum;
   }

   /**
    * Create a cummulative probability list 
    * @param population population
    * @param totalFitness total fitness of the population
    * @return a cummulative probability list for the indiv in the population
    */
   private List<Double> calcProbabilityList(Population<T> population, double totalFitness){
      List<Double> result = new ArrayList<>();
      double cummulativeProb = 0d;
      List<T> indivs = population.getIndividuals();
      for(int i = 0; i<indivs.size(); i++){
         double indivFitness = indivs.get(i).getFitness();
         if(indivFitness == 0){
            continue;
         }
         cummulativeProb += (indivFitness/totalFitness);
         result.add(cummulativeProb);
      }
      return result;
   }

   /**
    * Repopulate the population to the goal size
    * @param population population to repopulate with
    * @param probList probability list to use to repopulate
    * @return list of new repopulated individuals
    */
   private List<Individual<T>> repopulate(Population<T> population, List<Double> probList){
      int goalSize = super.calcGoalSize(population.getSize());
      List<Individual<T>> repopulated = new ArrayList<>();
      if(ensureAddFirst){
         repopulated.add(population.getBesT());
      }

      //While not at goal get random by probability List
      while(repopulated.size() < goalSize){
         repopulated.add(getElementByProbabilityList(population, probList));
      }


      return repopulated;
   }

   /**
    * Get random element by the probability list
    * @param population population to get individual from
    * @param probList probability list to use to choose random
    * @return random individual from population
    */
   private Individual<T> getElementByProbabilityList(Population<T> population, List<Double> probList){
      //Get the proper index
      //If negative get index where it should be
      int index = Collections.binarySearch(probList, Math.random());
      if(index < 0){
         index = (-index)-1;
      }
      return population.getIndividuals().get(index);
   }

}
