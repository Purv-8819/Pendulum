package dev.purv.pendulum.simmulator;

public class Cart {
    //Attributes
    private double position;
    private double velocity;
    private double acceleration;
    private double friction;
    private double size;
    private Move move;
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

    public void setMove(Move m){
        this.move = m;
    }

    public void update(){

    }
}
