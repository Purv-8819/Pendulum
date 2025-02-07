package dev.purv.pendulum.simmulator;

import lombok.Getter;

public class Pendulum {
    private final double length;
    @Getter
    private double angle;
    @Getter
    private double angularVelocity;
    @Getter
    private double angularAcceleration;
    private static final double gravity = 9.8;
    private final Cart cart;


    public Pendulum(Cart c){
        this.length = 5;
        this.angle = 0;
        this.angularVelocity = 0;
        this.angularAcceleration = 0;
        this.cart = c;
        this.cart.attach(this);
    }


    public void update(){
        //Calculate the angular acceleration component from the linear acceleration of the cart
        double linearComponent = this.cart.getAcceleration() * (1/this.length) * Math.cos(this.angle);
        //Calculate the angular acceleration component from the acceleration of gravity
        double gravityComponent = Pendulum.gravity * Math.sin(this.angle) / this.length;

        //Combine the two components
        this.angularAcceleration = linearComponent + gravityComponent;
        //Multiply by negative 1 so bottom is down
        this.angularAcceleration *= -1;

        //Add the acceleration to the velocity
        this.angularVelocity += this.angularAcceleration;
//        this.angularVelocity = Math.max(this.angularVelocity, 2*Math.PI - .5);

        //Add the velocity to angle
        this.angle += this.angularVelocity;
    }

}
