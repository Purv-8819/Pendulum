package dev.purv.pendulum.machinelearning.geneticalgorithm.evolution.recombiner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.Layer;
import dev.purv.pendulum.machinelearning.geneticalgorithm.Population;
import dev.purv.pendulum.machinelearning.geneticalgorithm.geneticneuralnet.NeuralNetworkIndividual;

public class NeuralNetworkUniformCrossoverRecombiner implements Recombiner<NeuralNetworkIndividual>{

   //Attribute
   private int numParetnsPerChild;

   //Constructor
   public NeuralNetworkUniformCrossoverRecombiner(int parentsPerChild){
      this.numParetnsPerChild = parentsPerChild;
   }

   //Methods

   @Override
   public void recombine(Population<NeuralNetworkIndividual> pop, int goalSize){
      if(numParetnsPerChild < 1 || numParetnsPerChild > pop.getSize()){
         throw new IllegalArgumentException("Number of parents per child must be between 1 and size of population");
      }

      List<NeuralNetworkIndividual> indivs = pop.getIndividuals();
      while(indivs.size() < goalSize){
         List<NeuralNetworkIndividual> parents = new ArrayList<>();
         //Create parents list
         for(int i = 0; i<numParetnsPerChild; i++){
            int randomIndex = ThreadLocalRandom.current().nextInt(indivs.size());
            parents.add(indivs.get(randomIndex));
         }
         //Create child from parents and add to to indivs
         indivs.add(makeChild(parents));
      }
   }

   private NeuralNetworkIndividual makeChild(List<NeuralNetworkIndividual> parents){
      NeuralNetworkIndividual child = parents.get(0).copy();

      //Loop throuhg all layers of the child's nueral net
      for(int i = 0; i<child.getNueralNetwork().getLayers().size(); i++){
         //Combine the weights and biases of the parents
         combineWeights(parents, child, i);
         combineBias(parents, child, i);
      }

      return child;
   }

   /**
    * Helper function to get the new weights of a child based on the parents
    * @param parents parents of child
    * @param child child
    * @param layerIndex index of layer currently on
    */
   private void combineWeights(List<NeuralNetworkIndividual> parents, NeuralNetworkIndividual child, int layerIndex){
      Layer here = child.getNueralNetwork().getLayers().get(layerIndex);

      //Loop throuhg all the weights at that layer
      for(int row = 0; row < here.getWeights().getNumRows(); row ++){
         for(int col = 0; col < here.getWeights().getNumCols(); col ++){
            //Select random parents
            int parInd = ThreadLocalRandom.current().nextInt(parents.size());
            //Get the corresponding value of parent
            double parentVal = parents.get(parInd).getNueralNetwork().getLayers().get(layerIndex).getWeights().get(row, col);
            //Set that value to child
            here.getWeights().set(row, col, parentVal);
         }
      }
   }

   /**
    * Helper function to set teh new biases of a child based on the parents
    * @param parents parents of child
    * @param child child 
    * @param layerIndex index of layer to set
    */
   private void combineBias(List<NeuralNetworkIndividual> parents, NeuralNetworkIndividual child, int layerIndex){
      //Current layer of child
      Layer here = child.getNueralNetwork().getLayers().get(layerIndex);

      //Loop throuhg all the biases
      for(int i = 0; i<here.getBias().size(); i++){
         //Get random parent
         int parentIndex = ThreadLocalRandom.current().nextInt(here.getBias().size());
         double parentVal = parents.get(parentIndex).getNueralNetwork().getLayers().get(layerIndex).getBias().get(i);
         //Set child's corresponding value
         here.getBias().set(i, parentVal);
      }
   }
}
