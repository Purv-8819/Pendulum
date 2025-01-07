package dev.purv.pendulum.machinelearning.linearalgebra;

import java.util.function.DoubleUnaryOperator;

public class LinearAlgebra {

   /**
    * Returns a unit vector of the given size
    * 
    * @param size the size of the vector
    * 
    * @return a unit vector of the given size
    */
   public static Vector unitVector(int size) {
      return new Vector(size).apply(d -> 1d);
   }

   /**
    * Returns a zero vector of the given size
    * 
    * @param size the size of the vector
    * 
    * @return a zero vector of the given size
    */
   public static Vector zeroVector(int size) {
      return new Vector(size).apply(d -> 0d);
   }

   /**
    * Create a vector of a given size with a given value
    * 
    * @param size the size of the vector
    * 
    * @param value the value of the vector
    * 
    * @return a vector of a given size with a given value
    */
   public static Vector vectorWithValues(int size, double value) {
      return new Vector(size).apply(d -> value);
   }

   /**
    * Sum of two vectors
    * 
    * @param v1 the first vector
    * 
    * @param v2 the second vector
    * 
    * @return the sum of the two vectors
    */
   public static Vector add(Vector v1, Vector v2) {
      Vector result = v1.copy();
      return result.add(v2);
   }

   /**
    * Difference of two vectors
    * 
    * @param v1 the first vector
    * 
    * @param v2 the second vector
    * 
    * @return the difference of the two vectors
    */
   public static Vector sub(Vector v1, Vector v2) {
      Vector result = v1.copy();
      return result.sub(v2);
   }

   /**
    * Apply a given operator to each element of the vector
    * 
    * @param v the vector
    * 
    * @param func the operator
    * 
    * @return the vector with the operator applied
    */
   public static Vector apply(Vector v, DoubleUnaryOperator func) {
      Vector result = v.copy();
      return result.apply(func);
   }


   /**
    * Multiply a vector and the matrix
    * 
    * @param m the matrix
    * @param v the vector
    * 
    * @return The resulting vector from the multiplication
    */
   public static Vector multiply(Matrix m, Vector v){
      if(v.size() != m.getNumCols()){
         throw new IllegalArgumentException("Vectors must have the same size as the matrix columns");
      }

      Vector result = new Vector(m.getNumRows());

      //Go throuhg each column
      //The result is the sum of the product of all the values in the column
      for(int i = 0; i<m.getNumRows(); i++){
         double sum = 0;
         for(int j = 0; j<m.getNumCols(); j++){
            sum += m.get(i, j) * v.get(j);
         }
         result.set(i, sum);
      }

      return result;
   }
}
