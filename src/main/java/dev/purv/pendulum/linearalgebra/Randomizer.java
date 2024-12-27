package dev.purv.pendulum.linearalgebra;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
   private final double min, max;

   public Randomizer(double min, double max) {
      this.min = min;
      this.max = max;
   }

   public double getInRange() {
      return ThreadLocalRandom.current().nextDouble(min, max);
   }
}
