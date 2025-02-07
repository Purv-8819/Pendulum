package dev.purv.pendulum.machinelearning.geneticalgorithm;

import java.util.*;
import java.util.stream.Collectors;

public class Population <T extends Individual<T>> {
   
   //Attributes
   private List<T> individuals;
   private int generation;

   //Constructor
   public Population(){
      individuals = new ArrayList<>();
   }
   
   public Population(List<T> individuals){
      this.individuals = new ArrayList<>(individuals);
   }

   //Methods

   /**
    * Calculate the fitness for all the individuals in the population
    */
   protected void calcFitnesses(){
      this.individuals.forEach(Individual::calcFitness);
   }

   /**
    * Sort the individuals in the population descending by fitness
    */
   public void sortPopulationByFitness(){
      individuals.sort(Comparator.reverseOrder());
   }

   /**
    * Gets the Individual with the best fitness
    * @return Individual with the best fitness
    */
   public T getBestT(){
      return Collections.max(individuals);
   }

   /**
    * Get the average fitness of the population
    * @return the average fitness of the population
    */
   public double getAverageFitness(){
      //Convert list to stream, map each individual to their fitness value, get the optional double and get actual double val
      return individuals.stream().mapToDouble(Individual::getFitness).average().getAsDouble();
   }

   /**
    * Get the generation of this population
    * @return the generation of the population
    */
   public int getGeneration(){
      return this.generation;
   }

   /**
    * Increment the generation of this population
    */
   public void incrementGeneration(){
      this.generation++;
   }

   /**
    * Get the amount of individuals in this population
    * @return Number of individuals in the population
    */
   public int getSize(){
      return this.individuals.size();
   }

   /**
    * Replace all the current individuals of this population to the given collection
    * @param collection individuals to replace current with
    */
   public void replaceAllIndividuals(Collection<Individual<T>> collection){
      //Clear current
      this.individuals.clear();
      Map<Integer, Long> refrenceMap = collection.stream().collect(Collectors.groupingBy(x-> System.identityHashCode(x), Collectors.counting()));

      //For each indiv in collection, map indiv to its reference or a copy if multiple and then for each add to population
      collection.stream().map(indiv -> refrenceMap.get(System.identityHashCode(indiv)) > 1 ? indiv.copy() : indiv).forEach(x -> this.individuals.add(x.getThis()));
   }

   /**
    * Get the individuals in this population
    * @return the individuals in this population
    */
   public List<T> getIndividuals(){
      return this.individuals;
   }

   /**
    * Set the individuals of this population
    * @param indivs the individuals to set to
    */
   public void setIndividuals(List<T> indivs){
      if(indivs == null){
         throw new IllegalArgumentException("individuals to set can not be null");
      }
      this.individuals = indivs;
   }
}

