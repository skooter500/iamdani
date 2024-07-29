package c21415952;

import ddf.minim.AudioBuffer;
import global.GlobalVisual;
import ie.tudublin.IAMDANI;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import processing.core.PApplet;

public class AjVisual extends VScene {
    Visual v;
    AudioBuffer ab;
    private VScene gv;

    public AjVisual(IAMDANI v) {
        super(v);
        this.v = v;

        ab = v.audioPlayer().mix;
        gv = new GlobalVisual(v);
    }

    public void render(int elapsed) {
        // 1:48 - 2:30 - Instrumental
        if (elapsed > v.toMs(2, 31, 0) && elapsed < v.toMs(3, 58, 0)) {
            v.background(0);

            // Set up spiral parameters
            float spiralSize = 100; // Size of spiral
            float spiralSpacing = 0.2f; // Distance between spiral points
            float spiralSpeed = 0.5f; // Rotation speed of spiral

            // Map the amplitude of the music to the size of the spiral
            float spiralAmplitude = PApplet.map(ab.get(0), -1, 1, 0, 1) * 200 + 100;

            // Draw rainbow spiral that reacts to music
            v.pushMatrix();
            v.stroke(10);
            v.translate(v.width / 2, v.height / 2); // Set origin to center of screen
            for (float i = 0; i < PApplet.TWO_PI * 5; i += spiralSpacing) {
                float r = i / PApplet.TWO_PI * spiralSize;
                float x = r * PApplet.sin(i + spiralSpeed * elapsed);
                float y = r * PApplet.cos(i + spiralSpeed * elapsed);

                // Set color based on position along spiral
                v.stroke(PApplet.map(i, 0, PApplet.TWO_PI * 5, 0, 255), 255, 255);

                // Draw spiral point
                v.point(x, y, spiralAmplitude);
            }
            v.stroke(1);
            v.popMatrix();

            // Draw expanding circle that reacts to music
            float circleSize = PApplet.map(ab.get(0), -1, 1, 0, v.width / 2);
            v.fill(60);
            v.strokeWeight(2);
            v.stroke(255);
            v.ellipse(v.width / 2, v.height / 2, circleSize, circleSize);
            gv.render(elapsed);
        }
    }

}