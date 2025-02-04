package dev.purv.pendulum.simmulator;

import lombok.Getter;

public class System {
    //Attributes
    @Getter
    private final Cart cart;
    private final Pendulum pendulum;
    @Getter
    private int tickCounter;
    @Getter
    private int score;
    @Getter
    private double cumulativeDistance;

    public System() {
        this.cart = new Cart();
        this.pendulum = new Pendulum(this.cart);
        this.tickCounter = 0;
        this.score = 0;
    }

    public void tick(){
        this.cart.update();
        this.tickCounter++;
        //Check if above the cart/ in the upright position
        if(Math.cos(this.pendulum.getAngle()) < 0){
            score ++;
        }
        this.cumulativeDistance += Math.abs(this.cart.getPosition());
    }

}
