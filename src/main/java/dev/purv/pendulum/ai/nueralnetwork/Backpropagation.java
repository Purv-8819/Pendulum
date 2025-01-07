package dev.purv.pendulum.ai.nueralnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import dev.purv.pendulum.ai.nueralnetwork.activationfunction.ActivationFunction;
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

   /**
    * Calculates the gradients for each layer
    * @param input input to the nueral net
    * @param expectedOutput the expected ouput for the nueral net
    * @param costFunction the costfunction of the nueral net
    * @param batchTrainResult the batchTrainResult for this batch
    * @return a mapping of the layer's index to it's gradients
    */ 
   private Map<Integer, GradientObject> calcGradient (Vector input, Vector expectedOutput, CostFunction costFunction, BatchTrainingResult batchTrainResult){
      nn.validateInputVector(input);

      //Feed the input throuhg the nueral net and get all the training results
      Map<Integer, Layer.TrainingResult> layerIndexToTrainingResult = feedForward(input);
      //Add the cost of this input to the batchTrainResult
      addCostsToTrainBatchResults(expectedOutput, costFunction, batchTrainResult, layerIndexToTrainingResult);
      //backprogate to get the gradients
      return backpropagate(input, expectedOutput, costFunction, layerIndexToTrainingResult);
   }
   
   /**
    * Add the cost of the current batch's output to the whole batch's result
    * @param expectedOutput expected output for the nueral net
    * @param costFunction costFunction of the nueral net
    * @param batchResult the object holding all the results for the current batch
    * @param layerIndexToTrainingResult Mapping of layer's index to it's training result
    */
   private void addCostsToTrainBatchResults(Vector expectedOutput, CostFunction costFunction, BatchTrainingResult batchResult,  Map<Integer, Layer.TrainingResult> layerIndexToTrainingResult){
      batchResult.addCost(costFunction.costs(expectedOutput, layerIndexToTrainingResult.get(layerIndexToTrainingResult.size()-1).getOutputWithActivationFunction()));
   }

   /**
    * Backpropogate throuhg the layers based on the given input and ouptu
    * @param input input to nueral net
    * @param expectedOutput expected ouput from nueral net
    * @param costFunction costFunction of the nueral net
    * @param layerIndexToTrainingResult mapping of layer's index to it's trainingResult
    * @return a mapping layer index to it's gradient object
    */
   private Map<Integer, GradientObject> backpropagate(Vector input, Vector expectedOutput, CostFunction costFunction, Map<Integer, Layer.TrainingResult> layerIndexToTrainingResult){
      Map<Integer, GradientObject> layerIndexToGradient = new HashMap<>();

      //Loop throuhg all the layers backwards
      for(int i = nn.layers.size()-1; i >= 0; i--){
         //Calculate the derivatives at the layer
         calcDerivativeOfCostFunction(i, expectedOutput, costFunction, layerIndexToTrainingResult);
         //Get the weight and bias gradients 
         Vector biasGradient = biasGradientsForEachBiasOfLayer(layerIndexToTrainingResult, i, nn.getLayers().get(i));
         Matrix weightGradient = weightGradientsForEachWeightOfLayer(layerIndexToTrainingResult, i, nn.getLayers().get(i), input);

         layerIndexToGradient.put(i, new GradientObject(weightGradient, biasGradient));
      }


      return layerIndexToGradient;
   }

   /**
    * get all the bias gradients for a layer
    * @param layerInexToTrainingResult mapping of layer index to it's training result
    * @param indexOfLayer index of current layer
    * @param layer the current layer
    * @return a gradient vector
    */
   private Vector biasGradientsForEachBiasOfLayer(Map<Integer, Layer.TrainingResult> layerInexToTrainingResult, int indexOfLayer, Layer layer){
      Vector gradients = new Vector(layer.getBias().size());

      for(int i = 0; i<gradients.size(); i++){
         double dervOfActivationFunction = getDerivativeOfActivationFunctionFromLayer(layer, i, layerInexToTrainingResult.get(indexOfLayer));
         double dervOfCostFunction = layerInexToTrainingResult.get(indexOfLayer).getDerivativeOfCostFunction().get(i);

         gradients.set(i, dervOfActivationFunction * dervOfCostFunction);
      }

      return gradients;
   }

   /**
    * Get all the weight gradients for a layer
    * @param layerIndexToTrainingResult mapping of layer index to it's training layer
    * @param indexOfLayer index of current layer
    * @param layer the current layer
    * @param input the input vector
    * @return a gradient matrix 
    */
   private Matrix weightGradientsForEachWeightOfLayer(Map<Integer, Layer.TrainingResult> layerIndexToTrainingResult, int indexOfLayer, Layer layer, Vector input){
      Matrix gradients = new Matrix(layer.getWeights().getNumRows(), layer.getWeights().getNumCols());
      //loop through all the weights
      for(int j = 0; j<layer.getWeights().getNumRows(); j++){
         for(int k = 0; k<layer.getWeights().getNumCols(); k++){
            //If first layer get the kth input val else get kth nueron of previous layer
            double nueronKOfPrevLayer = indexOfLayer > 0 ? layerIndexToTrainingResult.get(indexOfLayer-1).getOutputWithActivationFunction().get(k) : input.get(k);
            //Get the derivative based on the activation function
            double dervOfActivationFunction = getDerivativeOfActivationFunctionFromLayer(layer, j, layerIndexToTrainingResult.get(indexOfLayer));
            //Get the derivativec of the costFunction
            double dervOfCostFunction = layerIndexToTrainingResult.get(indexOfLayer).getDerivativeOfCostFunction().get(j);

            //Set the appropriate gradient value
            gradients.set(j, k, nueronKOfPrevLayer * dervOfActivationFunction * dervOfCostFunction);

         }
      }

      return gradients;
   }

   /**
    * Get the derivative of the activation function for a given nueron from a layer
    * @param layer layer in which the nueron resides
    * @param nueronIndex the index of nueron
    * @param trainResult training result of the layer
    * @return the derivative for the nueron's activation function
    */
   private double getDerivativeOfActivationFunctionFromLayer(Layer layer, int nueronIndex, Layer.TrainingResult trainResult){
      ActivationFunction func = layer.getActivation();
      return func.derivative(trainResult.getOutputStripped().get(nueronIndex));
   }

   /**
    * Calculate all the derivatives for teh cost functions for a given layer
    * @param indexOfLayer index of layer to calculate
    * @param expectedOutput expected output for the nueral net
    * @param costFunc cost function used by nueral net
    * @param layerInexToTrainingResult mapping of layer index to it's trainingResult
    */
   private void calcDerivativeOfCostFunction(int indexOfLayer, Vector expectedOutput, CostFunction costFunc, Map<Integer, Layer.TrainingResult> layerInexToTrainingResult){
      if(indexOfLayer == layerInexToTrainingResult.size()-1){
         calcCostFunctionDerivativeForLastLayer(expectedOutput, costFunc, layerInexToTrainingResult);
      }else{
         calcCostFunctionDerivativeForAnyLayer(layerInexToTrainingResult.get(indexOfLayer), nn.layers.get(indexOfLayer+1), layerInexToTrainingResult.get(indexOfLayer+1));
      }
   }

   /**
    * Calculate and set the derivaitve of the cost function for a layer
    * @param currTrainingResult the current training result, wehre the derivative is set
    * @param followingLayer the layer following
    * @param followTrainingResult the result from teh following layer
    */
   private void calcCostFunctionDerivativeForAnyLayer(Layer.TrainingResult currTrainingResult, Layer followingLayer, Layer.TrainingResult followTrainingResult){
      currTrainingResult.setDerviativeOfCostFunction(calcCostFunctionDerivative(currTrainingResult, followingLayer, followTrainingResult));
   }

   /**
    * Set the last training result's derivativeOfCostFunction based on the expected and actual outputs
    * @param expectedOutput the expected output 
    * @param costFunction the cost function used in the nueral net
    * @param layerIndexToTrainingResult mapping between layer index and its corresponding trainingResult
    */
   private void calcCostFunctionDerivativeForLastLayer(Vector expectedOutput, CostFunction costFunction, Map<Integer, Layer.TrainingResult> layerIndexToTrainingResult){
      //Get the output from the nueral net
      Vector nueralNetOutput = layerIndexToTrainingResult.get(layerIndexToTrainingResult.size()-1).getOutputWithActivationFunction();
      //Create and set teh gradients based on the expected outptue
      Vector dervOfOutput = new Vector(nueralNetOutput.size());
      for(int i = 0; i<dervOfOutput.size(); i++){
         dervOfOutput.set(i, costFunction.derivativeRespectToNueron(expectedOutput, nueralNetOutput, i));
      }
      layerIndexToTrainingResult.get(layerIndexToTrainingResult.size()-1).setDerviativeOfCostFunction(dervOfOutput);
   }

   /**
    * Get vector for all the derivatives for the nuerons in the current layer
    * @param currTrainingResult the current layer's training results
    * @param followingLayer the following layer
    * @param followingTrainingResult the following layer's training result
    * @return the gradient vector for the layer
    */
   private Vector calcCostFunctionDerivative(Layer.TrainingResult currTrainingResult, Layer followingLayer, Layer.TrainingResult followingTrainingResult){
      Vector derivativeVector = new Vector(currTrainingResult.size());
      for(int i = 0; i<derivativeVector.size(); i++){
         derivativeVector.set(i, calcDervOfNueron(followingLayer, followingTrainingResult, i));
      }
      return derivativeVector;
   }

   /**
    * Get the derivative of the nueron based on the output
    * @param followingLayer Next layer
    * @param followingLayerTrainingResult Training result fir the next year
    * @param nueronIndex index of the nueron to calculate derivative at
    * @return return the derivative
    */
   private double calcDervOfNueron(Layer followingLayer, Layer.TrainingResult followingLayerTrainingResult, int nueronIndex){
      double result = 0d;

      for(int i = 0; i<followingLayer.size(); i++){
         //Get the weright from nureon at index I to the follwoing layer's nueronIndex
         double weightFromNueronIndexToI = followingLayer.getWeights().get(i, nueronIndex);
         //Get the derivative of the activation function
         double dervOfActivationFunction = getDerivativeOfActivationFunctionFromLayer(followingLayer, i, followingLayerTrainingResult);
         //get the derivative of the cost function
         double dervOfCostFunctionOfNueronI = followingLayerTrainingResult.getDerivativeOfCostFunction().get(i);
         result += weightFromNueronIndexToI * dervOfActivationFunction * dervOfCostFunctionOfNueronI;
      }

      return result;
   }


   /**
    * Feed the input throuhg the nueral network and return map of index to training result
    * @param input input vector to nueral net
    * @return map of index of layer to training result of layer
    */
   private Map<Integer, Layer.TrainingResult> feedForward(Vector input){
      //Create result
      Map<Integer, Layer.TrainingResult> trainingResult = new HashMap<>();

      //Loop throuhg each layer of the nueral net
      for(int i = 0; i < nn.layers.size(); i++){
         Layer.TrainingResult result = nn.layers.get(i).feedForward(input);
         trainingResult.put(i, result);
         input = result.getOutputWithActivationFunction();
      }

      return trainingResult;
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
