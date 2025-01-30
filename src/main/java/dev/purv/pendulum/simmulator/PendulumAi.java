package dev.purv.pendulum.simmulator;

import dev.purv.pendulum.machinelearning.ai.nueralnetwork.NueralNetwork;

public class PendulumAi {
    private System system;
    private NueralNetwork nn;

    public PendulumAi(NueralNetwork nn) {
        this.system = new System();
        this.nn = nn;
    }


}
