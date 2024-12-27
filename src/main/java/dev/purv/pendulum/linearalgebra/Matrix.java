package dev.purv.pendulum.linearalgebra;

import java.util.function.DoubleUnaryOperator;

public class Matrix implements ApplyAble<Matrix> {
   // Member
   private double[][] data;

   // Constructors

   public Matrix(int rows, int cols) {
      this.data = new double[rows][cols];
   }

   public Matrix(double[][] data) {
      this.data = new double[data.length][data[0].length];
      for (int i = 0; i < data.length; i++) {
         for (int j = 0; j < data[0].length; j++) {
            this.data[i][j] = data[i][j];
         }
      }
   }

   // Methods

   /**
    * randomize the matrix
    * 
    * @param Randomizer, holds the range
    */
   public void randomize(Randomizer randomizer) {
      for (int i = 0; i < this.data.length; i++) {
         for (int j = 0; j < this.data[0].length; j++) {
            this.data[i][j] = randomizer.getInRange();
         }
      }
   }

   /**
    * apply a given function to all elements of the matrix
    * 
    * @param function to apply
    * 
    * @return the matrix with the function applied
    */
   @Override
   public Matrix apply(DoubleUnaryOperator function) {
      for (int i = 0; i < this.data.length; i++) {
         for (int j = 0; j < this.data[0].length; j++) {
            this.data[i][j] = function.applyAsDouble(this.data[i][j]);
         }
      }
      return this;
   }

   /**
    * Get the highest number from the matrix
    * 
    * @return the highest number
    */
   public double getHighestNum() {
      double highest = this.data[0][0];
      for (int i = 0; i < this.data.length; i++) {
         for (int j = 0; j < this.data[0].length; j++) {
            if (this.data[i][j] > highest) {
               highest = this.data[i][j];
            }
         }
      }
      return highest;
   }

   /**
    * get the value at a given row and col
    * 
    * @param row the row
    * 
    * @param col the column
    * 
    * @return the value at the given row and col
    */
   public double get(int row, int col) {
      return this.data[row][col];
   }

   /**
    * set the value at a given row and col
    * 
    * @param row the row
    * 
    * @param col the column
    * 
    * @param val the value to set
    */
   public void set(int row, int col, double val) {
      this.data[row][col] = val;
   }

   /**
    * get the number of rows
    * 
    * @return the number of rows
    */
   public int getNumRows() {
      return this.data.length;
   }

   /**
    * get the number of columns
    * 
    * @return the number of columns
    */
   public int getNumCols() {
      return this.data[0].length;
   }

   /**
    * Get all the data
    * 
    * @return the whole matrix
    */
   public double[][] getData(){
      return this.data;
   }

   /**
    * Create a copy of the matrix
    * 
    * @return the copy
    */
   public Matrix copy(){
      return new Matrix(this.data);
   }

   /**
    * Override the equals functionm
    * Check if the given object is teh same as the matrix
    * All the values and size needs to be the same
    */
   @Override
   public boolean equals(Object o){
      if(o == this){
         return true;
      }

      if(o == null || getClass() != o.getClass()){
         return false;
      }

      Matrix m = (Matrix) o;

      if(m.getNumCols() != this.getNumCols() || m.getNumRows() != this.getNumRows()){
         return false;
      }

      for(int i = 0; i< this.getNumRows(); i++){
         for(int j = 0; j<this.getNumCols(); j++){
            if(this.get(i, j) != m.get(i, j)){
               return false;
            }
         }
      }

      return true;
   }

}
