package dev.purv.pendulum.ai.nueralnetwork.activationfunction;

public class Sigmoid implements ActivationFunction{
   
   /**
    * perform the sigmoid activation on the value
    * 
    * @param x input value
    * @return the result
    */
   @Override
   public double applyActivatoin(double x){
      //Translate tanh up by 1, then divide by 2 to get range from 0, 1
      //Divide x by 2 to stretch tanh
      return (Math.tanh(x/2)+1)/2;
   }

   /**
    * Get the value of x from the derivative of the sigma
    * 
    * @param x input value
    * @return the result
    */
   @Override
   public double derivative(double x){
      return applyActivatoin(x) * (1-applyActivatoin(x));
   }
}
