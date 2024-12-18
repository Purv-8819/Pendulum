"use client";

import { useEffect, useRef } from "react";
import p5 from "p5";

function sketch(p) {
  // p is a reference to the p5 instance this sketch is attached to
  p.setup = function () {
    p.createCanvas(400, 400);
  };

  p.draw = function () {
    p.background(220);
    var mX = p.mouseX;
    var mY = p.mouseY;
    for (var i = 0; i < 10; i++) {
      for (var j = 0; j < 10; j++) {
        if (
          mX > i * 40 &&
          mX < (i + 1) * 40 &&
          mY > j * 40 &&
          mY < (j + 1) * 40
        ) {
          var Xcol = p.map(p.mouseX, 0, p.width, 0, 255);
          var Ycol = p.map(p.mouseY, 0, p.height, 0, 255);
          var col = p.map(Xcol + Ycol, 0, 510, 0, 255);
          p.fill(0, 0, col);
        } else if (mX > i * 40 && mX < (i + 1) * 40) {
          let col = p.map(p.mouseX, 0, p.width, 0, 255);
          p.fill(p.mouseX, 0, 0);
        } else if (mY > j * 40 && mY < (j + 1) * 40) {
          let col = p.map(p.mouseY, 0, p.height, 0, 255);
          p.fill(0, col, 0);
        } else {
          if (p.mouseIsPressed) {
            continue;
          }
          p.fill(p.random(255), p.random(255), p.random(255), p.random(255));
        }
        p.rect(i * 40, j * 40, 40, 40);
        p.noFill();
      }
    }
  };
}

export default function App() {
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

  return <div className="App" ref={p5ContainerRef} />;
}
