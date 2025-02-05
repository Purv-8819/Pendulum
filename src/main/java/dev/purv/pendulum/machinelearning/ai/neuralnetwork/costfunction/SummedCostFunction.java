package dev.purv.pendulum.machinelearning.ai.neuralnetwork.costfunction;

import dev.purv.pendulum.machinelearning.linearalgebra.Vector;

public class SummedCostFunction implements CostFunction{

   public double costs(Vector expected, Vector actual){
      //Check if equal sizes
      validateSize(expected, actual);

      double cost = 0d;
      for(int i = 0; i<expected.size(); i++){
         cost += Math.pow(expected.get(i)-actual.get(i), 2);
      }

      return cost;
   }

   public double derivativeRespectToNueron(Vector expected, Vector actual, int indexOfNueron){
      return 2*(expected.get(indexOfNueron) - actual.get(indexOfNueron));
   }


   private void validateSize(Vector v1, Vector v2){
      if(v1.size() != v2.size()){
         throw new IllegalArgumentException("Vectors must have the same size");
      }
   }

}
