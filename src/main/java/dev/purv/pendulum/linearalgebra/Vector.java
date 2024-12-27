package dev.purv.pendulum.linearalgebra;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class Vector implements ApplyAble<Vector> {
   private double[] data;

   // Constructors

   /**
    * Creates a new vector with the given size.
    * 
    * @param size The size of the vector
    */
   public Vector(int size) {
      this.data = new double[size];
   }

   /**
    * Copys the given vector
    * 
    * @param vector The vector to copy
    */
   public Vector(double... data) {
      if (data.length == 0) {
         throw new IllegalArgumentException("Vector must have at least one element");
      }

      this.data = new double[data.length];
      for (int i = 0; i < data.length; i++) {
         this.data[i] = data[i];
      }
   }

   // Methods

   /**
    * Randomizes the vector
    * 
    * @param Randomizer, holds the range
    */
   public void randomize(Randomizer randomizer) {
      for (int i = 0; i < this.data.length; i++) {
         this.data[i] = randomizer.getInRange();
      }
   }

   /**
    * adds the given vector to this vector
    * 
    * @param vector The vector to add
    * 
    * @return The sum of the two vectors
    */
   public Vector add(Vector other) {

      applyOperator(other, Double::sum);
      return this;
   }

   /**
    * subtracts the given vector from this vector
    * 
    * @param vector The vector to add
    * 
    * @return The difference of the two vectors
    */
   public Vector sub(Vector other) {

      applyOperator(other, (d1, d2) -> d1 - d2);
      return this;
   }

   /**
    * Helper functon to apply a given operator between two vectors
    * 
    * @param vector The second vector
    * 
    * @return The result of the function on the two vectors
    */
   public void applyOperator(Vector other, DoubleBinaryOperator func) {
      if (this.size() != other.size()) {
         throw new IllegalArgumentException("Vectors must have the same size");
      }

      for (int i = 0; i < this.size(); i++) {
         this.data[i] = func.applyAsDouble(this.data[i], other.data[i]);
      }
   }

   /**
    * applies the double unary operator to each element of the vector
    * 
    * @param function The function to apply
    * 
    * @return The vector with the function applied
    */
   @Override
   public Vector apply(DoubleUnaryOperator func) {
      for (int i = 0; i < this.size(); i++) {
         this.data[i] = func.applyAsDouble(this.data[i]);
      }

      return this;
   }

   /**
    * applies double unary operator to each element of the vector
    * applies the funciton on a copy of the vector
    * 
    * @param function The function to apply
    * 
    * @return The copy of vector with the function applied
    */
   public Vector applyAsCopy(DoubleUnaryOperator func) {
      return copy().apply(func);
   }

   /**
    * Gets the index of the biggest value in the vector
    * 
    * @return The index of the biggest value
    */
   public int getBiggestIndex() {
      double max = Double.MIN_VALUE;
      int maxIndex = -1;
      for (int i = 0; i < this.size(); i++) {
         if (this.data[i] > max) {
            max = this.data[i];
            maxIndex = i;
         }
      }

      return maxIndex;
   }

   /**
    * Gets the data of the vector
    * 
    * @return The data of the vector
    */
   public double[] getData() {
      return data;
   }

   /**
    * Get the value at the given index
    * 
    * @param index The index of the value
    * 
    * @return The value at the given index
    */
   public double get(int index) {
      return this.data[index];
   }

   /**
    * Sets the value at the given index
    * 
    * @param index The index of the value
    * 
    * @param value The value to set
    */
   public void set(int index, double value) {
      this.data[index] = value;
   }

   /**
    * Returns the size of the vector
    * 
    * @return The size of the vector
    */
   public int size() {
      return this.data.length;
   }

   /**
    * Creates a copy of the vector
    * 
    * @return The copy of the vector
    */
   public Vector copy() {
      return new Vector(this.data);
   }

   /**
    * Print out the contents of the vector
    */
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < this.size(); i++) {
         sb.append("|").append(i).append(": ").append(this.data[i]).append("| ");
      }
      return sb.toString();
   }

   /**
    * Checks if the given object is equal to this vector
    * 
    * @param obj The object to compare
    * 
    * @return True if the object is equal to this vector, false otherwise
    */
   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if (obj == null || getClass() != obj.getClass()) {
         return false;
      }

      Vector vector = (Vector) obj;

      if (this.size() != vector.size()) {
         return false;
      }

      for (int i = 0; i < this.size(); i++) {
         if (this.data[i] != vector.data[i]) {
            return false;
         }
      }

      return true;
   }
}
