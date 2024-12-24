"use client";

import { useEffect, useRef } from "react";
import p5 from "p5";
import Pendulum from "./p5js/pendulum";

const sketch = (p) => {
  // p is a reference to the p5 instance this sketch is attached to
  let pendulumTest;
  p.setup = function () {
    let canvas = p.createCanvas(p.windowWidth * 0.45, p.windowWidth * 0.45);
    canvas.style("margin: 0px");
    pendulumTest = new Pendulum(p, p.width / 2, p.height / 2, 100, 10);
  };

  p.draw = function () {
    p.background(120);

    //Middle Line
    p.fill(255, 0, 0);
    p.rect(0, p.height / 2, p.width, 2);

    // //Carrying box
    // p.fill(0, 0, 255);
    // //X pos
    // var xPos = 0;
    // if (p.mouseX < 0) {
    //   xPos = 0;
    // } else if (p.mouseX > p.width - 60) {
    //   xPos = p.width - 60;
    // } else {
    //   xPos = p.mouseX - 30;
    // }
    // p.rect(xPos, p.height / 2 - 20, 60, 40);
    pendulumTest.update();
    pendulumTest.display();
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
