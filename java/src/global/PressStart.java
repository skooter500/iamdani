package global;

import ie.tudublin.HoldTheLine;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import processing.core.PApplet;
import processing.core.PShape;

public class PressStart extends VScene {
    PShape text;
    HoldTheLine htl;
    float effect;
    boolean active;

    public PressStart(Visual v) {
        super(v);
        htl = (HoldTheLine) v;
        text = v.loadShape("Start Here.obj");
        text.setFill(v.color(0, 0, 800));
        effect = 1;
        active = true;
    }

    float scale = 1;

    @Override
    public void render() {
        v.background(0);
        v.pushMatrix();
        v.translateCenter();
        // v.ambientLight(0, 0, 100);
        v.lightFalloff(0.5f, 0.001f, 0);
        v.spotLight(0, 0, 200 * effect, // Hue, saturation, brightness
                200, -400, 200, // position
                -1, 1, 0, // direction
                PApplet.HALF_PI * 1.5f, 1); // angle, concentration
        v.spotLight(240, 80, 50 * effect, // Hue, saturation, brightness
                -200, -400, 200, // position
                1, 1, 0, // direction
                PApplet.HALF_PI * 1.5f, 1); // angle, concentration
        v.spotLight(300, 80, 50 * effect, // Hue, saturation, brightness
                0, 400, 200, // position
                0, -1, 0, // direction
                PApplet.HALF_PI * 1.5f, 1); // angle, concentration

        v.rotateX(PApplet.PI);
        // v.emissive(120, 100, 100);
        // If mouse is hovering over the text make it bigger
        if (v.mouseX > v.width / 2 - 200 && v.mouseX < v.width / 2 + 200 && v.mouseY > v.height / 2 - 200
                && v.mouseY < v.height / 2 + 200) {
            scale = PApplet.lerp(scale, 1.2f, 0.1f);
            if (v.mousePressed) {
                active = false;
            }
        } else {
            scale = PApplet.lerp(scale, 1, 0.1f);
        }
        if (!active) {
            effect = PApplet.lerp(effect, 0, 0.05f);
        }
        if (effect <= 0.01f && active == false) {
            htl.startScreen = false;
            v.play();
        }
        v.pushMatrix();
        v.scale(scale);
        v.shape(text);
        v.popMatrix();
        // v.translate(0, -v.height / 4 + v.height / 16);
        v.translate(0, -200, 0);
        v.rotateX(-PApplet.HALF_PI);
        v.fill(0, 0, 80);
        v.rectMode(PApplet.CENTER);
        v.circle(0, 0, v.height * 2);
        v.popMatrix();
    }

}
