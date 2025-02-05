package dev.purv.pendulum.machinelearning.ai.neuralnetwork;

import java.util.List;
import java.util.function.Supplier;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.Backpropagation.BatchTrainingResult;
import dev.purv.pendulum.machinelearning.ai.neuralnetwork.costfunction.CostFunction;
import dev.purv.pendulum.machinelearning.ai.neuralnetwork.costfunction.SummedCostFunction;
import dev.purv.pendulum.machinelearning.linearalgebra.Vector;

public class Trainer {

   private final NeuralNetwork nueralNetwork;
   private final Supplier<Batch> batchSupplier;
   private final double learningRate;
   private final int epochs;
   private final CostFunction costFunction;
   private final boolean logging;

   //Constructor
   private Trainer(NeuralNetwork nueralNetwork, Supplier<Batch> batchSupplier, double learningRate, int epochs, CostFunction costFunction, boolean logging){
      this.nueralNetwork = nueralNetwork;
      this.batchSupplier = batchSupplier;
      this.learningRate = learningRate;
      this.epochs = epochs;
      this.costFunction = costFunction;
      this.logging = logging;
   }

   /**
    * Train the nueral network
    */
   public void train(){
      for(int i = 0; i<epochs; i++){
         doEpoch(i, batchSupplier.get());
      }
   }

   /**
    * Get the result from a singular epoch
    * @param i
    * @param batch
    */
   private void doEpoch(int i, Batch batch){
      Backpropagation.BatchTrainingResult result = new  Backpropagation(nueralNetwork).trainBatch(batch.getInputs(), batch.getOutputs(), costFunction, learningRate);
      if(logging){
         logIfPresent(i, result);
      }
   }

   /**
    * Helper function to log the epoch and average cost
    * @param epoch epoch number
    * @param trainingResult batchTrainingResult for that epoch
    */
   private void logIfPresent(int epoch, BatchTrainingResult trainingResult){
      System.out.println("EPOCH " + epoch + ": " + trainingResult.average());
   }  

   public static class Builder{
      private final NeuralNetwork nueralNetwork;
      private final Supplier<Batch> batchSupplier;
      private final double learningRate;
      private final int epochs;
      private CostFunction costFunction = new SummedCostFunction();
      private boolean logging = false;

      public Builder(NeuralNetwork nn, Supplier<Batch> batchSupplier, double learningRate, int epochs){
         this.nueralNetwork = nn;
         this.batchSupplier = batchSupplier;
         this.learningRate = learningRate;
         this.epochs = epochs;
      }

      public Builder withCostFunction(CostFunction costFunction){
         this.costFunction = costFunction;
         return this;
      }

      public Builder withLogger(){
         this.logging = true;
         return this;
      }

      /**\
       * Create and return a Trainer object based on builder's attributes
       */
      public Trainer build(){
         return new Trainer(nueralNetwork, batchSupplier, learningRate, epochs, costFunction, logging);
      }
   }

   public static class Batch{
      private final List<Vector> inputs;
      private final List<Vector> expectedOutputs;

      public Batch(List<Vector> input, List<Vector> output){
         this.inputs = input;
         this.expectedOutputs = output;
      }

      public List<Vector> getInputs(){
         return this.inputs;
      }

      public List<Vector> getOutputs(){
         return this.expectedOutputs;
      }

   }
}
