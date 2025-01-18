package dev.purv.pendulum.machinelearning.geneticalgorithm.geneticnueralnet;

import dev.purv.pendulum.machinelearning.ai.nueralnetwork.NueralNetwork;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.linearalgebra.Vector;

public class NueralNetworkIndividual implements Individual<NueralNetworkIndividual>{
   //Attributes
   private NueralNetwork nueralNet;
   private NueralNetworkFitnessFunction fitnessFunc;
   private double fitness;

   //Constructor
   public NueralNetworkIndividual(NueralNetwork nn, NueralNetworkFitnessFunction ff){
      this.nueralNet = nn;
      this.fitnessFunc = ff;
   }

   //Methods

   /**
    * Get the nueral net of this indiviidual
    * @return the nueral network
    */
   public NueralNetwork getNueralNetwork(){
      return this.nueralNet;
   }

   @Override
   public void calcFitness(){
      this.fitness = fitnessFunc.calculateFitness(nueralNet);
   }

   @Override
   public double getFitness(){
      return this.fitness;
   }

   @Override
   public NueralNetworkIndividual copy(){
      NueralNetworkIndividual newIndiv = new NueralNetworkIndividual(nueralNet.copy(), fitnessFunc);
      //Set Same refrence
      newIndiv.fitnessFunc = this.fitnessFunc;
      return newIndiv;
   }

   /**
    * Calculate the ouput of the nueral net
    * @param input the input to the nueral net
    * @return the nueral net oupt
    */
   public Vector calcOutput(Vector input){
      return nueralNet.calcOutput(input);
   }

   /**
    * Get the fitness function
    * @return the fitness function
    */
   public NueralNetworkFitnessFunction getFitnessFunction(){
      return this.fitnessFunc;
   }
}
