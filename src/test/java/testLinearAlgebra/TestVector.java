package testLinearAlgebra;

import org.junit.jupiter.api.Test;

import dev.purv.pendulum.machinelearning.linearalgebra.LinearAlgebra;
import dev.purv.pendulum.machinelearning.linearalgebra.Matrix;
import dev.purv.pendulum.machinelearning.linearalgebra.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVector {
   Vector v1 = new Vector(1, 2, 3);
   Vector v2 = new Vector(4, 5, 6);

   @Test
   public void testAdd() {

      Vector v3 = LinearAlgebra.add(v1, v2);
      Vector Expected = new Vector(5, 7, 9);
      assertEquals(v3, Expected);
   }

   @Test
   public void testSub() {

      Vector v3 = LinearAlgebra.sub(v2, v1);
      Vector Expected = new Vector(3, 3, 3);
      assertEquals(v3, Expected);
   }

   @Test
   public void testUnitZero() {

      Vector v3 = LinearAlgebra.zeroVector(3);
      Vector v4 = LinearAlgebra.unitVector(3);
      Vector zero = new Vector(0d, 0d, 0d);
      Vector unit = new Vector(1d, 1d, 1d);

      assertEquals(v3, zero);
      assertEquals(v4, unit);
   }

   @Test
   public void testApply() {
      Vector v4 = v1.apply(d -> d * 2);
      Vector Expected = new Vector(2, 4, 6);
      assertEquals(v4, Expected);
   }

   @Test
   public void testMul(){
      Matrix m = new Matrix(new double[][] {{1, 2, 3}, {4, 5, 6}});
      Vector prod1 = LinearAlgebra.multiply(m, v1);
      Vector prod2 = LinearAlgebra.multiply(m, v2);
      Vector expected1 = new Vector(14, 32);
      Vector expected2 = new Vector(32, 77);

      assertEquals(prod1, expected1);
      assertEquals(prod2, expected2);
   }

}
