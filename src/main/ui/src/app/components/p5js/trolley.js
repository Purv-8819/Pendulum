class Trolley {
  constructor(p, x, y, width, height) {
    this.p = p;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.speed = 0;
    this.accel = 0;
    this.friction = 0.95;
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

  update() {
    this.speed += this.accel;
    this.x += this.speed * this.friction;
  }

  display() {
    this.p.fill(150);
    this.p.rect(this.x, this.y, this.width, this.height);
  }
}

export default Trolley;
