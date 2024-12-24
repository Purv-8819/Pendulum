class Pendulum {
  //Add a parent controller/trolley to the pendulum
  constructor(p, x, y, length, trolley = null) {
    this.p = p;
    this.origin = this.p.createVector(x, y);
    this.position = this.p.createVector();
    this.length = length;
    this.angle = p.PI / 4;
    this.aVelocity = 0.0;
    this.aAcceleration = 0.0;
    this.gravity = 0.4; // Arbitrary constant
    this.trolley = trolley;
  }

  //Set Trolley
  //Set refrence to trolley that its attached to
  setTrolley(trolley) {
    this.trolley = trolley;
    return this;
  }

  update() {
    this.aAcceleration =
      ((-1 * this.gravity) / this.length) * this.p.sin(this.angle);

    // Update velocity and angle
    this.aVelocity += this.aAcceleration;
    this.angle += this.aVelocity;
  }

  display() {
    this.position.set(
      this.length * this.p.sin(this.angle),
      this.length * this.p.cos(this.angle)
    );
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
