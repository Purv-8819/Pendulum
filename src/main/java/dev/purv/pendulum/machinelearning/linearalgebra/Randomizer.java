package dev.purv.pendulum.machinelearning.linearalgebra;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
   private final double min, max;

   public Randomizer(double min, double max) {
      this.min = min;
      this.max = max;
   }

   public double getInRange() {
      return min + ThreadLocalRandom.current().nextDouble()* (max-min);
   }
}
