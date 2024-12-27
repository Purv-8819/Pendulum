package dev.purv.pendulum.ai.activationfunction;

public class Tanh implements ActivationFunction{
      /**
    * perform the tanh activation on the value
    * 
    * @param x input value
    * @return the result
    */
   @Override
   public double applyActivatoin(double x){
      //Translate tanh up by 1, then divide by 2 to get range from 0, 1
      //Divide x by 2 to stretch tanh
      return Math.tanh(x);
   }

   /**
    * Get the value of x from the derivative of the tanh
    * 
    * @param x input value
    * @return the result
    */
   @Override
   public double derivative(double x){
      return 1-(applyActivatoin(x) * applyActivatoin(x));
   }

}
