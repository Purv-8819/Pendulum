package dev.purv.pendulum.simmulator;

import lombok.Getter;
import lombok.Setter;

public class Cart {
    //Attributes

    @Getter
    private double position;
    private double velocity;
    @Getter
    private double acceleration;
    private double friction;
    private double size;
    @Setter
    private Move move = Move.NOTHING;
    private Pendulum attached;
    public enum Move {LEFT, RIGHT, BRAKE, NOTHING};

    //Constructor
    public Cart(){
        this.position = 0;
        this.velocity = 0;
        this.acceleration = 0;
        this.friction = 0;
        this.size = 5;
        this.move = Move.NOTHING;
    }

    //Method
    public void attach(Pendulum p){
        this.attached = p;
    }


    /**
     * Update the cart and attached pendulum
     */
    public void update(){
        //Check the move and update correspondingly
        switch (this.move){
            case LEFT -> {
                moveLeft();
            }
            case RIGHT -> {
                moveRight();
            }
            case BRAKE -> {
                brake();
            }
            case NOTHING -> {
                nothing();
            }
        }
    }

    /**
     * Update the pendulum in moving left
     */
    private void moveLeft(){
        this.acceleration -= 0.1;
        updateSystem();
    }

    /**
     * Update the pendulum in moving right
     */
    private void moveRight(){
        this.acceleration += 0.1;
        updateSystem();
    }

    /**
     * Update the pendulum in breaking
     */
    private void brake(){
        //Move the opposite way currently travelling
        this.acceleration += getSign(this.velocity) * -0.1;
        updateSystem();
    }

    private double getSign(double val){
        return val < 0 ? -1 : 1;
    }

    /**
     * Update the pendulum in doing nothing
     */
    private void nothing(){
        this.acceleration = 0;
        updateSystem();
    }

    /**
     * Update the whole system from the current state
     */
    private void updateSystem(){
        this.velocity += this.acceleration;
        this.position += this.velocity;
        this.attached.update();
    }
}
