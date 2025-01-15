package dev.purv.pendulum.machinelearning.ai.geneticalgorithm.evolution.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.ai.geneticalgorithm.Population;

public class TournamentSelector <T extends Individual<T>> extends PercentageSelector<T>{

   //Attribute
   private final int tournamentSize;

   //Constructor
   public TournamentSelector(double percent, int tournamentSize){
      super(percent);
      if(tournamentSize < 1){
         throw new IllegalArgumentException("Tournament size must be at least 1");
      }
      this.tournamentSize = tournamentSize;
   }

   //Methods

   public void select(Population<T> population){
      int goalSize = super.calcGoalSize(population.getSize());
      if(tournamentSize > population.getSize()){
         throw new IllegalArgumentException("Population is too small for this tournament size");
      }

      if(population.getSize() != goalSize){
         List<Individual<T>> repopulted = repopulate(population, goalSize);
         population.replaceAllIndividuals(repopulted);
      }
   }

   /**
    * Repopulate the individuals from the population using touranment method
    * @param population population used to repopulate
    * @param goalSize size to repopulate to
    * @return List of repopulated individuals
    */
   private List<Individual<T>> repopulate(Population<T> population, int goalSize){
      List<Individual<T>> repopulated = new ArrayList<>();

      //While less than goal size add the winner of tournament
      while(repopulated.size() < goalSize){
         repopulated.add(playTournament(population));
      }

      return repopulated;
   }

   /**
    * Select tournament size random individuals from population and get best
    * @param population population to select individuals from
    * @return the best individual from the population
    */
   private Individual<T> playTournament(Population<T> population){
      Random rand = new Random();
      List<Individual<T>> participants = new ArrayList<>();

      //Fill the tournament
      for(int i = 0; i<tournamentSize; i++){
         Individual<T> newIndiv = population.getIndividuals().get(rand.nextInt(population.getSize()));

         //If alreadu in the tournament select different
         if(participants.contains(newIndiv)){
            i--;
         }else{
            participants.add(newIndiv);
         }
      }
      
      //Get the participant with the highest fitness
      return Collections.max(participants);
   }

}
