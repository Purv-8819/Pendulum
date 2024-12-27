package dev.purv.pendulum.ai.nueralnetwork;


import dev.purv.pendulum.ai.nueralnetwork.activationfunction.ActivationFunction;
import dev.purv.pendulum.linearalgebra.LinearAlgebra;
import dev.purv.pendulum.linearalgebra.Matrix;
import dev.purv.pendulum.linearalgebra.Randomizer;
import dev.purv.pendulum.linearalgebra.Vector;

public class Layer {

   //Member Variables
   private Matrix weights;
   private Vector bias;
   private ActivationFunction fActivation;

   //Constructors
   public Layer(int size, int sizeOfLayerBefore, ActivationFunction func){

      //Check for proper values
      if(size <= 0 || sizeOfLayerBefore <=0){
         throw new IllegalArgumentException("Size must be positive");
      }

      this.bias = new Vector(size);
      this.weights = new Matrix(size, sizeOfLayerBefore);
      this.fActivation = func;
   }

   //Copy Constructor
   public Layer(Layer other){
      this.weights = other.weights.copy();
      this.bias = other.bias.copy();
      this.fActivation = other.fActivation;
   }

   //Methods
   
   /**
    * Calculates the activation of this layer
    * @param activationOfPreviousLayer results from the previous layer
    * @return The vector resulting of the activation performed on this layer 
    */
   public Vector calcActivation(Vector activationOfPreviousLayer){
      validateActivationOfPreviousLayer(activationOfPreviousLayer);

      return calcOutput(activationOfPreviousLayer).apply(fActivation);
   }

   /**
    * Helper function for calculating with activation
    * Performs raw math
    * @param activationOfPreviousLayer results from the previous layer
    * @return The product of weights and previous with the bias
    */
   private Vector calcOutput(Vector activationOfPreviousLayer){
      return LinearAlgebra.multiply(weights, activationOfPreviousLayer).sub(bias);
   }

   /**
    * 
    * @param activationOfPreviousLayer results from the previous layer
    * @return A training result object with the stripped and activated vectors
    */
   protected TrainingResult feedForward(Vector activationOfPreviousLayer){
      validateActivationOfPreviousLayer(activationOfPreviousLayer);

      Vector stripped = calcOutput(activationOfPreviousLayer);
      Vector activated = calcActivation(activationOfPreviousLayer);

      return new TrainingResult(stripped, activated);
   }

   /**
    * Randomize the weights and bias for the layer
    * @param weightRand
    * @param biasRand
    */
   public void randomize(Randomizer weightRand, Randomizer biasRand){
      this.weights.randomize(weightRand);
      this.bias.randomize(biasRand);
   } 

   /**
    * Get the number of nodes in this layer
    * @return The size of the layer
    */
   public int size(){
      return this.bias.size();
   }

   /**
    * 
    * @return the weights matrix
    */
   public Matrix getWeights(){
      return this.weights;
   }

   /**
    * @return the bias vector
    */
   public Vector getBias(){
      return this.bias;
   }

   /**
    * @return The activation function of this layer
    */
   public ActivationFunction getActivation(){
      return this.fActivation;
   }

   /**
    * @return a copy of the layer
    */
   public Layer copy(){
      return new Layer(this);
   }


   /**
    * Validate if the sizing is proper
    * Throws an Error if inproper sizing
    * @param activationOfPreviousLayer
    */
   private void validateActivationOfPreviousLayer(Vector activationOfPreviousLayer){
      if(activationOfPreviousLayer.size() != weights.getNumCols()){
         throw new IllegalArgumentException( "size of activationsOfLayerBefore must fit with weights columns" );
      }
   }



   /**
    * Class holding OutputStriped, 
    * outputWithActivationFunction, 
    * and derivativeOfCostFunction vectors
    */
   public static class TrainingResult {
      
      //Member Variables
      private Vector outputStripped;
      private Vector outputWithActivationFunction;
      private Vector derivativeOfCostFunction;

      //Constructor
      public TrainingResult(Vector stripped, Vector activation){
         this.outputStripped = stripped;
         this.outputWithActivationFunction = activation;
      }

      //Methods
      
      //Getter Functions
      /**
       * 
       * @return the output Stripped Vector
       */
      public Vector getOutputStripped(){
         return this.outputStripped;
      }

      /**
       * 
       * @return the output with Activation Vector
       */
      public Vector getOutputWithActivationFunction(){
         return this.outputWithActivationFunction;
      }

      /**
       * 
       * @return the derivative of cost Function Vector
       */
      public Vector getDerivativeOfCostFunction(){
         return this.derivativeOfCostFunction;
      }

      /**
       * Set the derivative of Cost function vector
       * @param dervOfCostFunction the vector to set to
       */
      public void setDerviativeOfCostFunction(Vector dervOfCostFunction){
         this.derivativeOfCostFunction = dervOfCostFunction;
      }

      /**
       * Get the size of the vector
       *  @return Size of output vector
       * */
      public int size(){
         return this.outputStripped.size();
      }
      
   }
}
