"use client";

import { useEffect, useRef } from "react";
import p5 from "p5";
import Pendulum from "./p5js/pendulum";
import Trolley from "./p5js/trolley";

const sketch = (p) => {
  // p is a reference to the p5 instance this sketch is attached to
  let pendulumTest;
  let trolleyTest;
  p.setup = function () {
    let canvas = p.createCanvas(p.windowWidth * 0.45, p.windowWidth * 0.45);
    canvas.style("margin: 0px");
    pendulumTest = new Pendulum(p, p.width / 2, p.height / 2, 100, 10);
    trolleyTest = new Trolley(
      p,
      p.width / 2,
      p.height / 2,
      100,
      10,
      pendulumTest
    );
    p.rectMode(p.CENTER);
  };

  //Key controls for trolley
  p.keyReleased = function () {
    trolleyTest.resetAccel();
  };

  p.keyPressed = function () {
    if (p.keyCode == p.LEFT_ARROW) {
      trolleyTest.moveLeft();
    }
    if (p.keyCode == p.RIGHT_ARROW) {
      trolleyTest.moveRight();
    }
    if (p.keyCode == p.DOWN_ARROW) {
      trolleyTest.resetSpeed();
    }
  };

  p.draw = function () {
    p.background(120);

    //Middle Line
    p.fill(255, 0, 0);
    p.rect(p.width / 2, p.height / 2, p.width, 3);

    //Test pendulum and trolley update and draw
    // pendulumTest.update();
    // pendulumTest.display();
    trolleyTest.update();
    trolleyTest.display();
  };
};

export default function Simmulator() {
  // create a reference to the container in which the p5 instance should place the canvas
  const p5ContainerRef = useRef();

  useEffect(() => {
    // On component creation, instantiate a p5 object with the sketch and container reference
    const p5Instance = new p5(sketch, p5ContainerRef.current);

    // On component destruction, delete the p5 instance
    return () => {
      p5Instance.remove();
    };
  }, []);

  return <div ref={p5ContainerRef} />;
}
