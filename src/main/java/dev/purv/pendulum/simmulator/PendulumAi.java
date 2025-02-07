package dev.purv.pendulum.simmulator;

import dev.purv.pendulum.machinelearning.ai.neuralnetwork.NeuralNetwork;
import dev.purv.pendulum.machinelearning.linearalgebra.Vector;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PendulumAi {
    private final Simulator simulator;
    @Getter
    private final NeuralNetwork nn;

    public PendulumAi(NeuralNetwork nn) {
        this.simulator = new Simulator();
        this.nn = nn;
    }

    /**
     * Start the simulation for maxTime
     * @param maxTime max number of ticks to run simulation for
     * @return score of the system
     */
    public double startPlaying(int maxTime){
        while(simulator.getTickCounter() < maxTime){
            getDirectionFromNeuralNet();
            simulator.tick();
        }
        return calcFitness();
    }

    private double calcFitness(){
        //Score is the number of ticks above 0
        double result = 0d;

        //Linear contribution from both
        result += this.simulator.getScore();
        result -= this.simulator.getCumulativeDistance();

        return result;

    }

    /**
     * Input the current state of the system and get the move from the neural network
     */
    private void getDirectionFromNeuralNet(){
        Vector input = simulator.getState();
        Vector result = nn.calcOutput(input);
        switch (result.getBiggestIndex()){
            case(0):
            {
                simulator.getCart().setMove(Cart.Move.LEFT);
            }
            case(1):{
                simulator.getCart().setMove(Cart.Move.RIGHT);
            }
            case(2):{
                simulator.getCart().setMove(Cart.Move.BRAKE);
            }
            default :{
                simulator.getCart().setMove(Cart.Move.NOTHING);
            }

        }
    }

    public List<Cart.Move> getMoveList(int maxTime){
        List<Cart.Move> result = new ArrayList<>();
        while(simulator.getTickCounter() < maxTime){
            getDirectionFromNeuralNet();
            result.add(simulator.getCart().getMove());
            simulator.tick();
        }

        return  result;
    }
}
