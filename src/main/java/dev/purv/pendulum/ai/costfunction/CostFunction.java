package dev.purv.pendulum.ai.costfunction;

import dev.purv.pendulum.linearalgebra.Vector;

public interface CostFunction {
   double costs(Vector expected, Vector actual);

   double derivativeRespectToNueron(Vector expected, Vector actual, int indexOfNueron);
}
