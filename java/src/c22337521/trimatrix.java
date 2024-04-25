package c22337521;

import ie.tudublin.*;
import processing.core.PApplet;

public class trimatrix extends Poly {
    float angle;

    public trimatrix(IAMDANI v) {
        super(v);
    }

    public void render() {
        v.background(0);
        v.lights();
        v.background(v.alp);

        float triangleSize = 250;
        float angle = PApplet.radians(v.frameCount % 360) + v.spe * PApplet.TWO_PI;

        float x1 = v.width / 2 + PApplet.cos(angle) * triangleSize;
        float y1 = v.height / 2 + PApplet.sin(angle) * triangleSize;

        float x2 = v.width / 2 + PApplet.cos(angle + PApplet.TWO_PI / 3) * triangleSize;
        float y2 = v.height / 2 + PApplet.sin(angle + PApplet.TWO_PI / 3) * triangleSize;

        float x3 = v.width / 2 + PApplet.cos(angle + 2 * PApplet.TWO_PI / 3) * triangleSize;
        float y3 = v.height / 2 + PApplet.sin(angle + 2 * PApplet.TWO_PI / 3) * triangleSize;

        float hue1 = v.frameCount % 255;
        v.stroke(hue1, 255, 255);
        v.fill(hue1, 255, 255);

        v.stroke(0);
        v.strokeWeight(2);
        v.triangle(x1, y1, x2, y2, x3, y3);
    }
}
