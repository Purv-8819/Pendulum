package dev.purv.pendulum.machinelearning.ai.neuralnetwork.costfunction;

import dev.purv.pendulum.machinelearning.linearalgebra.Vector;

public interface CostFunction {
   double costs(Vector expected, Vector actual);

   double derivativeRespectToNueron(Vector expected, Vector actual, int indexOfNueron);
}
