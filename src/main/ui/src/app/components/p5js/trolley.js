class Trolley {
  constructor(p, x, y, width, height, pendulum) {
    //P5 instance
    this.p = p;
    //Position of trolley
    this.pos = this.p.createVector(x, y);
    //Width and Height of trolley
    this.width = width;
    this.height = height;
    //Speed of trolley
    this.speed = 0;
    //Acceleration of trolley
    this.accel = 0;
    //Friction of trolley
    this.friction = 0.95;
    //Refrence to Pendulum
    this.pendulum = pendulum.setTrolley(this);
  }

  //Push the trolley to the right
  pushRight() {
    this.accel += 0.1;
  }

  //Push the trolley to the left
  pushLeft() {
    this.accel -= 0.1;
  }

  //Reset the acceleration of the trolley
  resetAccel() {
    this.accel = 0;
  }

  //Reset the trolley to its starting position and speed
  resetTrolley() {
    this.pos.x = this.p.width / 2;
    this.accel = 0;
    this.speed = 0;
  }

  //Reset the trolley and pendulum to their starting positions
  resetSystem() {
    this.resetTrolley();
    this.pendulum.reset();
  }

  //Update the trolley and connected pendulum
  update() {
    this.speed += this.accel;
    this.pos.x += this.speed * this.friction;
    this.pendulum.update(this.accel);
  }

  //Display the trolley and connected pendulum
  display() {
    this.p.fill(150);
    this.p.rect(this.pos.x, this.pos.y, this.width, this.height);
    this.pendulum.display();
  }
}

export default Trolley;
