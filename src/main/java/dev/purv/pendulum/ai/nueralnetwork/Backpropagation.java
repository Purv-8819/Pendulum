package dev.purv.pendulum.ai.nueralnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import dev.purv.pendulum.ai.nueralnetwork.costfunction.CostFunction;
import dev.purv.pendulum.linearalgebra.ApplyAble;
import dev.purv.pendulum.linearalgebra.Matrix;
import dev.purv.pendulum.linearalgebra.Vector;

public class Backpropagation {

   private final NueralNetwork nn;

   public Backpropagation(NueralNetwork n){
      this.nn = n;
   }

   /**
    * Train the batch
    * @param input input values given
    * @param expectedOutput expected result to be produced
    * @param costFunction cost function for evaluation
    * @param learningRate how fast the nueral network can change
    * @return Return BatchTrainingResult object 
    */
   public BatchTrainingResult trainBatch(List<Vector> input, List<Vector> expectedOutput, CostFunction costFunction, double learningRate){
      //Validate the input of this function
      if(input.size() != expectedOutput.size()){
         throw new IllegalArgumentException("Number of inputs must match number of outputs");
      }
      if(learningRate <= 0 || learningRate > 1){
         throw new IllegalArgumentException("Learning rate must be between 0 and 1");
      }

      return doTrainBatch(input, expectedOutput, costFunction, learningRate);
   }

   /**
    * Helper function to do the actual training
    * @param input input values given
    * @param expectedOutput expected result to be produced
    * @param costFunction cost function for evaluation
    * @param learningRate how fast the nueral network can change
    * @return Return BatchTrainingResult object 
    */
   private BatchTrainingResult doTrainBatch(List<Vector> input, List<Vector> expectedOutput, CostFunction costFunction, double learningRate){
      BatchTrainingResult result = new BatchTrainingResult();

      updateLayers(learningRate, getAverageGradient(createGradientFromBatch(input, expectedOutput, costFunction, result)));

      return result;
   }

   private List<Map<Integer, GradientObject>> createGradientFromBatch(List<Vector> input, List<Vector> expectedOutput, CostFunction costFunction, BatchTrainingResult batchTrainingResult){
      List<Map<Integer, GradientObject>> gradientList = new ArrayList<>();

      for(int i = 0; i<input.size(); i++){
         gradientList.add(calcGradient(input.get(i), expectedOutput.get(i), costFunction, batchTrainingResult));
      }

      return gradientList;
   }

   /**
    * Update all the layers based on the gradient
    * @param learningRate learning rate to change the nueral net
    * @param resultGradients gradient used to change it
    */
   private void updateLayers(double learningRate, Map<Integer, GradientObject> resultGradients){
      for(int i = 0; i<nn.layers.size(); i++){
         updateLayer(learningRate, resultGradients.get(i), i);
      }
   }

   /**
    * Helper function to update all layers. Updates a single layer
    * @param learningRate learning rate to change the nueral net
    * @param gradient gradient object holding gradient data
    * @param index index of the layer to be updated
    */
   private void updateLayer(double learningRate, GradientObject gradient, int index){
      updateLayerBiases(this.nn.layers.get(index).getBias(), learningRate, gradient.biasGradient);
      updateLayerWeights(this.nn.layers.get(index).getWeights(), learningRate, gradient.weightGradient);
   }

   /**
    * Helper functino to update a Layer's Biases
    * @param curr current bias values in layer
    * @param learningRate learning rate to change with
    * @param gradient gradient to change by
    */
   private void updateLayerBiases(Vector curr, double learningRate, Vector gradient){
      for(int i = 0; i<curr.size(); i++){
         curr.set(i, curr.get(i) + (learningRate * gradient.get(i)));
      }

   }

   /**
    * Helper function to update a Layer's weights
    * @param curr current weights of the layer
    * @param learningRate learning rate to change with
    * @param gradient gradient to change by
    */
   private void updateLayerWeights(Matrix curr, double learningRate, Matrix gradient){
      for(int i = 0; i<curr.getNumRows(); i++){
         for(int j = 0; j<curr.getNumCols(); j++){
            curr.set(i, j, curr.get(i, j) - (learningRate * gradient.get(i, j)));
         }
      }
   }

   /**
    * Get teh average gradient values from a list of all gradient values
    * @param gradientList List of all gradients from different inputs/outputs
    * @return the average of the gradients from all the trainings
    */
   private Map<Integer, GradientObject> getAverageGradient(List<Map<Integer, GradientObject>> gradientList){
      Map<Integer, GradientObject> result = createEmptyResultGradientMap(gradientList);

      sumUpGradients(gradientList, result);
      averageOutGradients(gradientList, result);

      return result;
   }

   /**
    * Add all the gradients from list to the result
    * @param gradientList list of gradients taht need to be added
    * @param resultGradient destication that needs to be added to
    */
   private void sumUpGradients(List<Map<Integer, GradientObject>> gradientList, Map<Integer, GradientObject> resultGradient){
      for(Map<Integer, GradientObject> gradient : gradientList){
         addGradientToResult(resultGradient, gradient);
      }
   }

   /**
    * Add a certain gradient to the the result
    * @param resultGradient result to be added to
    * @param source source that needs to be added
    */
   private void addGradientToResult(Map<Integer, GradientObject> resultGradient, Map<Integer, GradientObject> source){
      for(Map.Entry<Integer, GradientObject> entry: source.entrySet()){
         addWeightGradientToResult(resultGradient, entry);
         addBiasGradientToResult(resultGradient, entry);
      }
   }

   /**
    * Helper function to add the weightrs of a particular entry to the result
    * @param resultGradient result to be added to
    * @param entry entry that needs to be added
    */
   private void addWeightGradientToResult(Map<Integer, GradientObject> resultGradient, Map.Entry<Integer, GradientObject> entry){
      Matrix result = resultGradient.get(entry.getKey()).weightGradient;
      addEachGradientToResultMatrix(entry.getValue().weightGradient, result);
   }

   /**
    * Helper function to add the bias a particular entry to the result
    * @param resultGradient result to be added to
    * @param entry entry that needs to be added
    */
   private void addBiasGradientToResult(Map<Integer, GradientObject> resultGradient, Map.Entry<Integer, GradientObject> entry){
      Vector result = resultGradient.get(entry.getKey()).biasGradient;
      addEachGradientToResultVector(entry.getValue().biasGradient, result);
   }

   /**
    * Helper func to add the given bias from gradient to result vector
    * @param bias given bias vector
    * @param result result to add to
    */
   private void addEachGradientToResultVector(Vector bias, Vector result){
      for(int i = 0; i<result.size(); i++){
         result.set(i, result.get(i) + bias.get(i));
      }
   }

   /**
    * Helper func to add the given weights from gradient to result matrix
    * @param weight given weight matrix
    * @param result result to add to
    */
   private void addEachGradientToResultMatrix(Matrix weight, Matrix result){
      for(int row = 0; row<result.getNumRows(); row++){
         for(int col = 0; col<result.getNumCols(); col++){
            result.set(row, col, result.get(row, col) + weight.get(row, col));
         }
      }
   }

   /**
    * Average out all the summed values from the list
    * @param gradientList list which was summed
    * @param resultGradient holds the result
    */
   private void averageOutGradients(List<Map<Integer, GradientObject>> gradientList, Map<Integer, GradientObject> resultGradient){
      //Get The collectoin of values
      //Change that collection into a stream
      //Flat map the stream into a stream of <Applyable> of weight adn bias gadient
      //for each on that stream apply dividing by size of list
      resultGradient.values().stream().flatMap(e -> Stream.<ApplyAble<?>>of(e.weightGradient, e.biasGradient)).forEach(a -> a.apply(v -> v/gradientList.size()));
   }

   /**
    * Create a map with the same size as the ones in the gradient list
    * @param gradientList List of all the gradients 
    * @return Empty gradient map
    */
   private Map<Integer, GradientObject> createEmptyResultGradientMap(List<Map<Integer, GradientObject>> gradientList){
      Map<Integer, GradientObject> result = new HashMap<>();
      for(int i = 0; i<gradientList.get(0).size(); i++){
         GradientObject here = gradientList.get(0).get(i);
         Matrix weights = new Matrix(here.weightGradient.getNumRows(), here.weightGradient.getNumCols());
         Vector bias = new Vector(here.biasGradient.size());
         result.put(i, new GradientObject(weights, bias));
      }

      return result;
   }




   private static class GradientObject{
      final Matrix weightGradient;
      final Vector biasGradient;

      public GradientObject(Matrix w, Vector v){
         this.weightGradient = w;
         this.biasGradient = v;
      }
   }

   public static class BatchTrainingResult{

      private final List<Double> costValues;

      public BatchTrainingResult(List<Double> d){
         this.costValues = d;
      }

      public BatchTrainingResult(){
         this.costValues = new ArrayList<Double>();
      }
      
      public void addCost(double d){
         this.costValues.add(d);
      }

      public double average(){
         return this.costValues.stream().mapToDouble(x -> x).average().orElse(0d);
      }
   }

}
