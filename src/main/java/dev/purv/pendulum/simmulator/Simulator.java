package dev.purv.pendulum.simmulator;

import dev.purv.pendulum.machinelearning.linearalgebra.Vector;
import lombok.Getter;

public class Simulator {
    //Attributes
    @Getter
    private final Cart cart;
    private final Pendulum pendulum;
    @Getter
    private int tickCounter;
    @Getter
    private double score;
    @Getter
    private double cumulativeDistance;
    @Getter
    private double cumulativePendulumSpeed;

    public Simulator() {
        this.cart = new Cart();
        this.pendulum = new Pendulum(this.cart);
        this.tickCounter = 0;
        this.score = 0;
    }

    public void tick(){
        this.cart.update();
        this.tickCounter++;
        //The score is the sum of the -cos of the angle
        score -= Math.cos(this.pendulum.getAngle());

        this.cumulativeDistance += Math.abs(this.cart.getPosition());
        this.cumulativePendulumSpeed += Math.abs(this.pendulum.getAngularVelocity());
    }

    /**
     * The state consists of the cart's position, velocity, and acceleration, as well as,
     * the pendulum's angle, angularVelocity, angularAcceleration
     * @return vector containing the state of the simulation
     */
    public Vector getState(){
        return new Vector(cart.getPosition(), cart.getVelocity(), cart.getAcceleration(), pendulum.getAngle(), pendulum.getAngularVelocity(), pendulum.getAngularAcceleration());
    }

}
