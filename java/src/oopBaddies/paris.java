package oopBaddies;

import processing.core.PApplet;
import ie.tudublin.IAMDANI;
import ie.tudublin.Poly;
import ie.tudublin.Visual;

public class paris extends Poly {


  public paris(IAMDANI v) {

    super(v);
  }

  // declare the variable

  int cols, rows;
  int scl = 20;
  int w = 6000;
  int h = 1900;

  float wave = 0;
  float[][] ocean;

  // declare the plane variable

  int planeX = 0; 
  int planeY = v.height / 5; 

  // this is the start of the draw render

  public void render() {

    // calulate average

    float avg = v.getAmplitude();
       
    float smoothedavg = 0;
    smoothedavg = v.lerp(smoothedavg, avg, 0.1f);


    // variable for the ocean wave

    cols = w / scl;
    rows = h / scl;
    ocean = new float[cols][rows];

    // wave movement setting

    wave -= 0.04;
    float yoff = wave;
    for (int y = 0; y < rows; y++) {
      float xoff = 0;
      for (int x = 0; x < cols; x++) {
        ocean[x][y] = v.map(v.noise(xoff, yoff), 0, 1, -300, 20);
        xoff += 0.06;
      }
      yoff += 0.06;
    }

    // colouring of the background

    v.stroke(v.hueShift(255), 255, 255, v.alp);
    v.noFill();

    // this is to add the rainbow pixel effect

    v.fill(v.hueShift(v.random(0, 256)), 255, 255, v.alp);
    v.noStroke();
    v.rect(v.random(w), v.random(h), 25, 25);

    // adding shading to the ocean wave

    v.pushMatrix();
    v.translate(v.width / 2, v.height / 2 + 2);
    v.rotateX(v.PI / 3);

    // colouring of the blue ocean

    v.noStroke();
    v.fill(v.hueShift(180), 255, 255, v.alp);
    //v.frameRate(15);

    // wave movement setting

    v.translate(-w / 2, +300);
    for (int y = 0; y < rows - 1; y++) {
      v.beginShape(PApplet.TRIANGLE_STRIP);
      for (int x = 0; x < cols; x++) {
        v.vertex(x * scl, y * scl, ocean[x][y]);
        v.vertex(x * scl, (y + 1) * scl, ocean[x][y + 1]);
      }
      v.endShape();
    }

    v.popMatrix();

    // drawing of the moon

    v.pushMatrix();
    v.translate(+1000, +130);
    v.fill(v.hueShift(55), 255, 255, v.alp);
    v.noStroke();
    v.ellipse(v.width - 100, v.height - 25, 400 + (smoothedavg * 600), 400 + (smoothedavg * 600));
    v.popMatrix();

    // drawing of the left rainbow heart

    v.pushMatrix();
    v.translate(+500, +160);
    v.fill(v.hueShift(v.random(0, 256)), 255, 255, v.alp);
    v.noStroke();
    v.beginShape();
    v.vertex(0, -50, 0);
    v.bezierVertex(0, -80, -30, -100, -60, -100);
    v.bezierVertex(-90, -100, -90, -50, -90, -50);
    v.bezierVertex(-90, -20, -70, 0, 0, 30);
    v.bezierVertex(70, 0, 90, -20, 90, -50);
    v.bezierVertex(90, -50, 90, -100, 60, -100);
    v.bezierVertex(30, -100, 0, -80, 0, -50);
    v.endShape(v.CLOSE);
    v.popMatrix();

    // drawing of the right rainbow heart

    v.pushMatrix();
    v.translate(1500, +160);
    v.fill(v.hueShift(v.random(0, 256)), 255, 255, v.alp);
    v.noStroke();
    v.beginShape();
    v.vertex(0, -50, 0);
    v.bezierVertex(0, -80, -30, -100, -60, -100);
    v.bezierVertex(-90, -100, -90, -50, -90, -50);
    v.bezierVertex(-90, -20, -70, 0, 0, 30);
    v.bezierVertex(70, 0, 90, -20, 90, -50);
    v.bezierVertex(90, -50, 90, -100, 60, -100);
    v.bezierVertex(30, -100, 0, -80, 0, -50);
    v.endShape(v.CLOSE);
    v.popMatrix();

    // drawing plane that fly across the screen

    v.pushMatrix();
    v.translate(0, 160);
    v.fill(200);
    v.stroke(v.hueShift(90), 255, 255, v.alp);
    v.strokeWeight(2);
    v.beginShape();
    v.vertex(planeX, planeY);
    v.vertex(planeX + 40, planeY - 20);
    v.vertex(planeX + 120, planeY);
    v.vertex(planeX + 40, planeY + 20);
    v.endShape(v.CLOSE);
    v.fill(v.hueShift(120), 255, 255, v.alp);
    v.stroke(v.hueShift(30), 255, 255, v.alp);
    v.ellipse(planeX + 50, planeY - 5, 55, 25);
    v.popMatrix();

    // update plane's position
    
    planeX += 3;
    if (planeX > v.width + 1800) { // reset plane's position when it goes off screen
      planeX = -1000;

    }

  }
}