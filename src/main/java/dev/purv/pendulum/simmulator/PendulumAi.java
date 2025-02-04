package dev.purv.pendulum.simmulator;

import dev.purv.pendulum.machinelearning.ai.nueralnetwork.NueralNetwork;
import dev.purv.pendulum.machinelearning.linearalgebra.Vector;

public class PendulumAi {
    private final Simulator simulator;
    private final NueralNetwork nn;

    public PendulumAi(NueralNetwork nn) {
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
            getDirectionFromNueralNet();
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
     * Input the current state of the system and get the move from the nueral network
     */
    private void getDirectionFromNueralNet(){
        Vector input = new Vector();
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


}
