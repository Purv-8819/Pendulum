package dev.purv.pendulum.machinelearning.ai.neuralnetwork.activationfunction;

import java.util.function.DoubleUnaryOperator;

public interface ActivationFunction extends DoubleUnaryOperator{

   public double applyActivatoin(double x);

   @Override
   default double applyAsDouble(double x){
      return applyActivatoin(x);
   }

   default double derivative(double x){
      throw new UnsupportedOperationException("This ActivationFunction doesn't provide an implementation for its derivative");
   }

}
