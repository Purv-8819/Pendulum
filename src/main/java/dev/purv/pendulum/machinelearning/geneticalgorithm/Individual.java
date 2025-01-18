package dev.purv.pendulum.machinelearning.geneticalgorithm;

public interface Individual <T extends Individual<T>> extends Comparable<Individual<T>>{
   /**
    * Calculate the fitness of this individual
    */
   void calcFitness();
   /**
    * Get the Fitness of the individual
    */
   double getFitness();
   
   /**
    * @return a copy of the type the individual holds
    */
   T copy();

   //Default Methods
   /**
    * Get the current type that is implement Individual
    * @return instance of class
    */
   @SuppressWarnings("unchecked")
   default T getThis(){
      try {
         return (T) this;
      } catch (ClassCastException e) {
         throw new RuntimeException("Could not get an instance of the individual");
      }
   }

   /**
    * Compare to other indiviudals 
    */
   default int compareTo(Individual<T> o) {
      double dif = this.getFitness()-o.getFitness();
      if(dif == 0){
         return 0;
      } else if(dif > 0){
         return 1;
      }else{
         return 0;
      }
   }


}
