package testNueralNetwork;

import dev.purv.pendulum.ai.nueralnetwork.Layer;
import dev.purv.pendulum.ai.nueralnetwork.NueralNetwork;
import dev.purv.pendulum.linearalgebra.Matrix;
import dev.purv.pendulum.linearalgebra.Randomizer;
import dev.purv.pendulum.linearalgebra.Vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class TestNueralNetwork {

   //Return a randomizer with a set value
   private Randomizer setn (double n){
      return new Randomizer(n, n);
   }

   @Test
   public void testBasic(){
      Randomizer set1 = setn(1);
      //1 node input to 1 node output
      NueralNetwork oneNode = new NueralNetwork.Builder(1, 1).withBiasRandomizer(set1).withWeightRandomizer(set1).build();

      List<Layer> list = oneNode.getLayers();
      assertEquals(1, list.size());
      
      Layer l = list.get(0);
      Matrix expectedMatrix = new Matrix(new double[][] {{1}});
      Vector expectedVector = new Vector(1d);
      
      assertEquals(expectedMatrix, l.getWeights());
      assertEquals(expectedVector, l.getBias());
      
   }

   @Test
   public void testMedium(){
      Randomizer set1 = setn(1);
      //2-4-3
      //2 input, 4 middle hidden, 3 output
      NueralNetwork twoLayer = new NueralNetwork.Builder(2, 3).withBiasRandomizer(set1).withWeightRandomizer(set1).addLayer(4).build();

      List<Layer> list = twoLayer.getLayers();
      assertEquals(2, list.size());
      
      //First Layer
      Layer firstLayer = list.get(0);
      assertEquals(4, firstLayer.size());
      Matrix expectedFirstMatrix = new Matrix(new double[][] {{1, 1}, {1, 1,},{1, 1}, {1, 1,}});
      Vector expectedFirstVector = new Vector(1, 1, 1, 1 );

      
      assertEquals(expectedFirstMatrix, firstLayer.getWeights());
      assertEquals(expectedFirstVector, firstLayer.getBias());

      //Second Layer
      Layer secondLayer = list.get(1);
      assertEquals(3, secondLayer.size());

      Matrix expectedSecondMatrix = new Matrix(new double[][] {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}});
      Vector expectedSecondVector = new Vector(1, 1, 1);
      
      assertEquals(expectedSecondMatrix, secondLayer.getWeights());
      assertEquals(expectedSecondVector, secondLayer.getBias());
   }

   @Test
   public void testWeight(){
      Randomizer set2 = setn(2);
      Randomizer set0 = setn(0);
      //1 node input to 1 node output
      NueralNetwork oneNode = new NueralNetwork.Builder(1, 1).withBiasRandomizer(set0).withWeightRandomizer(set2).withActivationFunction(x->x).build();

      Vector output = oneNode.calcOutput(new Vector(1d));
      assertEquals(1, output.size());

      assertEquals(2, output.get(0));


      NueralNetwork medium = new NueralNetwork.Builder(2, 2).withBiasRandomizer(set0).withWeightRandomizer(set2).withActivationFunction(x->x).addLayer(3).build();

      List<Vector> mediumOutputList = medium.calcAllLayers(new Vector(1, 1));
      assertEquals(2, mediumOutputList.size());

      //First Layer
      Vector first = mediumOutputList.get(0);
      Vector expectFirst = new Vector(4, 4, 4);
      assertEquals(expectFirst, first);

      //Second Layer
      Vector second = mediumOutputList.get(1);
      Vector expectSecond = new Vector(24, 24);
      assertEquals(expectSecond, second);
   }
   
   @Test
   public void testBias(){
      Randomizer set2 = setn(2);
      Randomizer set1 = setn(1);

      //1 node input to 1 node output
      NueralNetwork oneNode = new NueralNetwork.Builder(1, 1).withBiasRandomizer(set2).withWeightRandomizer(set1).withActivationFunction(x->x).build();

      Vector output = oneNode.calcOutput(new Vector(0d));
      assertEquals(1, output.size());

      assertEquals(2, output.get(0));

      NueralNetwork medium = new NueralNetwork.Builder(2, 2).withBiasRandomizer(set2).withWeightRandomizer(set1).withActivationFunction(x->x).addLayer(3).build();

      List<Vector> outputList = medium.calcAllLayers(new Vector(0d, 0d));
      assertEquals(2, outputList.size());
      
      //First Layer
      Vector first = outputList.get(0);
      Vector expectedFirst = new Vector(2d, 2d, 2d);
      assertEquals(3, first.size());
      assertEquals(expectedFirst, first);

      Vector second = outputList.get(1);
      Vector expectedSecond = new Vector(8d, 8d);
      assertEquals(2, second.size());
      assertEquals(expectedSecond, second);
   }

}
