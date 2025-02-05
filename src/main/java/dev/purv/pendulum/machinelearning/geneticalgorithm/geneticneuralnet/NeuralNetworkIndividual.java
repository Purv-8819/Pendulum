package dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.NeuralNetwork;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Individual;
import dev.purv.pendulum.machinelearning.linearalgebra.Vector;

public class NeuralNetworkIndividual implements Individual<NeuralNetworkIndividual>{
   //Attributes
   private NeuralNetwork nueralNet;
   private NeuralNetworkFitnessFunction fitnessFunc;
   private double fitness;

   //Constructor
   public NeuralNetworkIndividual(NeuralNetwork nn, NeuralNetworkFitnessFunction ff){
      this.nueralNet = nn;
      this.fitnessFunc = ff;
   }

   //Methods

   /**
    * Get the nueral net of this indiviidual
    * @return the nueral network
    */
   public NeuralNetwork getNueralNetwork(){
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
   public NeuralNetworkIndividual copy(){
      NeuralNetworkIndividual newIndiv = new NeuralNetworkIndividual(nueralNet.copy(), fitnessFunc);
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
   public NeuralNetworkFitnessFunction getFitnessFunction(){
      return this.fitnessFunc;
   }
}
