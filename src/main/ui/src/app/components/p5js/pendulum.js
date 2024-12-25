class Pendulum {
  //Add a parent controller/trolley to the pendulum
  constructor(p, x, y, length, trolley = null) {
    //P5 instance
    this.p = p;
    //Origin of pendulum
    this.origin = this.p.createVector(x, y);
    //Position of pendulum end
    this.position = this.p.createVector();
    // length of pendulum
    this.length = length;
    //Angle of pendulum
    this.angle = 0;
    //Angular Velocity
    this.aVelocity = 0.0;
    //Angular Acceleration
    this.aAcceleration = 0.0;
    //Gravity
    this.gravity = 0.4; // Arbitrary constant
    //Trolley Instance
    this.trolley = trolley;
  }

  //Set Trolley
  //Set refrence to trolley that its attached to
  setTrolley(trolley) {
    this.trolley = trolley;
    return this;
  }

  //Update the pendulum based on the acceleration of the trolley
  update(accel) {
    //Calculate the components of the acceleration based on the acceleration of the trolley
    var linAccelComponent = accel * (1 / this.length) * this.p.cos(this.angle);
    //Calculate the componenet of accelaraion based on gravity
    var gravityComponent =
      (this.gravity * this.p.sin(this.angle)) / this.length;

    this.aAcceleration = -linAccelComponent - gravityComponent;
    this.aVelocity += this.aAcceleration;
    //limit the velocity
    if (this.aVelocity > 7.5) {
      this.aVelocity = 7.5;
    }
    this.angle += this.aVelocity;
  }

  //Angle is based on 0 degrees being straight down
  //Rotate the angle by 90 degrees to make 0 degrees straight horizontal
  angle() {
    return this.angle + this.p.PI / 2;
  }

  //Reset the pendulum to its starting position
  reset() {
    this.angle = 0;
    this.aVelocity = 0;
    this.aAcceleration = 0;
  }

  //Show the pendulum
  display() {
    //Set position of end of pendulum based on angle
    this.position.set(
      this.length * this.p.sin(this.angle),
      this.length * this.p.cos(this.angle)
    );
    //Translate to trolley position
    this.position.add(this.trolley.pos);

    this.p.stroke(0);
    this.p.strokeWeight(2);
    this.p.fill(127);
    this.p.line(
      this.trolley.pos.x,
      this.trolley.pos.y,
      this.position.x,
      this.position.y
    );
    this.p.ellipse(this.position.x, this.position.y, 16, 16);
  }
}

export default Pendulum;
