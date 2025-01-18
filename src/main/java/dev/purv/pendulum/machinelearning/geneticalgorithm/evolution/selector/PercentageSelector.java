package dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.selector;

import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;

public abstract class PercentageSelector <T extends Individual<T>> implements Selector<T>{
   //Attributes
   private double percentage;

   //Constructor
   public PercentageSelector(double percentage){
      if(percentage < 0 || percentage > 1){
         throw new IllegalArgumentException("Percentage for selector must be between 0 and 1 inclusive");
      }
      this.percentage = percentage;
   }

   //Methods
   /**
    * Get the size to selct to
    * @param populationSize total size
    * @return the goal size
    */
   protected int calcGoalSize(int populationSize){
      return (int) Math.ceil(populationSize * percentage);
   }

   /**
    * @return The percent of the population to be selected
    */
   public double getPercent(){
      return this.percentage;
   }

   /**
    * Set the percent for the selector
    * @param percent percent to set to
    * @throws if percent is less than 0 or greater than 1
    */
   public void setPercent(double percent){
      if(percent < 0 || percent > 1){
         throw new IllegalArgumentException("Percentage for selector must be between 0 and 1 inclusive");
      }
      this.percentage = percent;
   }
   
}
