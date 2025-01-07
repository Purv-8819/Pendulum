package dev.purv.pendulum.ai.nueralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import dev.purv.pendulum.ai.nueralnetwork.activationfunction.ActivationFunction;
import dev.purv.pendulum.ai.nueralnetwork.activationfunction.Sigmoid;
import dev.purv.pendulum.linearalgebra.Randomizer;
import dev.purv.pendulum.linearalgebra.Vector;

public class NueralNetwork {
   //Member Variables
   int inputSize;
   protected final List<Layer> layers;

   //Constructor
   private NueralNetwork(int inputSize){
      this.inputSize = inputSize;
      this.layers = new ArrayList<Layer>();
   }

   private NueralNetwork(int inputSize, List<Layer> layers){
      this.inputSize = inputSize;
      this.layers = layers;
   }

   //Methods
   /**
    * 
    * @param input input to the nueral network
    * @return the final result vector from the nueral network
    */
   public Vector calcOutput(Vector input){
      return calcAllLayers(input).get(layers.size()-1);
   }

   /**
    * 
    * @param inputVector input to the nueral network
    * @return List of all the layers and their results
    */
   public List<Vector> calcAllLayers(Vector inputVector){
      validateInputVector(inputVector);

      return doCalcLayers(inputVector);
   }

   protected void validateInputVector(Vector inputVector){
      if(inputVector.size() != inputSize){
         throw new IllegalArgumentException("Input vector must be same size as Nueral network first layer");
      }
   }

   /**
    * Calculates the input through all the layers
    * 
    * @param inputVector input to the nueral network
    * @return List of all the layers
    */
   private List<Vector> doCalcLayers(Vector inputVector){
      List<Vector> result = new ArrayList<>(layers.size());
      //Add input to beginning
      result.add(inputVector);

      //Go throuhg all the layers and calc the activation based on the pervious layer
      for(int i = 0; i<layers.size(); i++){
         result.add(layers.get(i).calcActivation(result.get(i)));
      }
      //Remove the input from the result
      result.remove(0);
      return result;
   }

   /**
    * Randomize all the weights and biases in this nueral network
    * @param weightRandomizer
    * @param biasRandomizer
    * @return the randomized nueral network
    */
   public NueralNetwork randomize(Randomizer weightRandomizer, Randomizer biasRandomizer){
      for(int i = 0; i< layers.size(); i++){
         layers.get(i).randomize(weightRandomizer, biasRandomizer);
      }
      return this;
   }

   /**
    * 
    * @return the layers in this nueral network
    */
   public List<Layer> getLayers(){
      return this.layers;
   } 

   /**
    * @return a copy of this nueral network
    */
   public NueralNetwork copy(){
      //Create a copy of each layer
      List<Layer> result = new ArrayList<>();
      layers.forEach(l -> result.add(l.copy()));

      return new NueralNetwork(this.inputSize, result);
   }

   public static class Builder {
      
      //Member Variables
      private final int inputSize;
      private final int outputSize;
      private final List<Integer> layerSize = new ArrayList<>();
      private ActivationFunction aFunction;
      private Randomizer weightRandomizer = new Randomizer(-1, 1);
      private Randomizer biasRandomizer = new Randomizer(-1, 1);
      private final AtomicBoolean isBuilt = new AtomicBoolean(false);

      //Constructor
      public Builder(int inputSize, int outputSize){
         this.inputSize = inputSize;
         this.outputSize = outputSize;
         this.aFunction = new Sigmoid();
      }
      
      //Methods

      public Builder withActivationFunction(ActivationFunction func){
         this.aFunction = func;

         return this;
      }

      public Builder withWeightRandomizer(Randomizer wRandomizer){
         this.weightRandomizer = wRandomizer;
         return this;
      }

      public Builder withBiasRandomizer(Randomizer bRandomizer){
         this.biasRandomizer = bRandomizer;
         return this;
      }

      /**
       * Adds a layer to the nueral network
       * @param sizeOfLayer size of layer to be added
       * @return builder with layer added
       */
      public Builder addLayer(int sizeOfLayer){
         this.layerSize.add(sizeOfLayer);

         return this;
      }

      /**
       * 
       * @param amount the number of layers to be added
       * @param size the size of each layer
       * @return Builder with newly configured settings
       */
      public Builder addLayers(int amount, int size){
         for(int i = 0; i<amount; i++){
            this.layerSize.add(size);
         }
         return this;
      }

      /**
       * 
       * @param size the sizes of the layers to be added
       * @return the builder
       */
      public Builder addLayers(int... size){
         for(int i = 0; i<size.length; i++){
            this.layerSize.add(size[i]);
         }

         return this;
      }

      public NueralNetwork build(){
         if(this.isBuilt.getAndSet(true)){
            throw new IllegalStateException( "this builder has already been used for building" );
         } 

         layerSize.add(outputSize);

         NueralNetwork nn = new NueralNetwork(inputSize);
         //Add first layer based on input size
         nn.layers.add(new Layer(this.layerSize.get(0), inputSize, aFunction));

         //Add all the following layers
         for(int i = 1; i<this.layerSize.size(); i++){
            nn.layers.add(new Layer(this.layerSize.get(i), this.layerSize.get(i-1), aFunction));
         }

         return nn.randomize(weightRandomizer, biasRandomizer);

      }

   }



}
