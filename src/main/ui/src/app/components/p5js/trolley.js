class Trolley {
  constructor(p, x, y, width, height, pendulum) {
    this.p = p;
    this.pos = this.p.createVector(x, y);
    this.width = width;
    this.height = height;
    this.speed = 0;
    this.accel = 0;
    this.friction = 0.95;
    this.pendulum = pendulum.setTrolley(this);
  }

  moveRight() {
    this.accel += 1;
    console.log(this.accel);
  }

  moveLeft() {
    this.accel -= 1;
  }

  resetAccel() {
    this.accel = 0;
  }

  resetSpeed() {
    this.accel = 0;
    this.speed = 0;
  }

  update() {
    this.speed += this.accel;
    this.pos.x += this.speed * this.friction;
    this.pendulum.update();
  }

  display() {
    this.p.fill(150);
    this.p.rect(this.pos.x, this.pos.y, this.width, this.height);
    this.pendulum.display();
  }
}

export default Trolley;
