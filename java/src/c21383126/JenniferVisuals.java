package c21383126;

import ie.tudublin.visual.AudioAnalysis;
import ie.tudublin.visual.VObject;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import jogamp.opengl.glu.nurbs.Backend;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import ddf.minim.AudioBuffer;
import ddf.minim.Minim;
import global.GlobalVisual;

public class JenniferVisuals extends VScene {
    Visual v;
    VObject speaker;
    Clock clock;
    VObject dots;
    VObject stars;
    VObject wf;
    VObject hex;
    GlobalVisual gv;
    
    public JenniferVisuals(Visual v) {
        super(v);
        this.v = v;
        speaker = new Speaker(v, new PVector(v.width / 4, v.height / 4));
        clock = new Clock(v, new PVector(v.width / 4, v.height / 4));
        dots = new Dots(v, new PVector(v.width / 4, v.height / 4));
        stars = new Stars(v, new PVector(v.width / 4, v.height / 4));
        wf = new WaveForm(v, new PVector(v.width / 4, v.height / 4));
        hex = new Hex(v, new PVector(v.width / 4, v.height / 4));
        gv = new GlobalVisual(v);

    }

    public void render(int elapsed) {

        // v.background(0);
        //wf.render();
        speaker.render();
        //hex.render();
        
        //dots.render(elapsed);
        //clock.render(elapsed);

        /*
        // 1:03 - 1:48 - Second verse & chorus
        if (elapsed > v.toMs(1, 3, 0) && elapsed < v.toMs(1, 14, 0)) {
            

        } else if (elapsed > v.toMs(1, 14, 0) && elapsed < v.toMs(1, 25, 0)) {
            v.background(0);
            gv.render(elapsed);
            hex.render();

        } else if (elapsed > v.toMs(1, 25, 0) && elapsed < v.toMs(1, 37, 0)) {
            v.background(0);
            dots.render(elapsed);
            clock.render(elapsed);

        } else if (elapsed > v.toMs(1, 37, 0) && elapsed < v.toMs(1, 48, 0)) {
            v.background(0);
            gv.render(elapsed);
            stars.render(elapsed);
        }
        */
    }

    class Stars extends VObject {

        PShape star;

        Stars(Visual v, PVector pos) {
            super(v, pos);
            // load star object
            star = v.loadShape("estrellica.obj");
        }

        int MAX = 150; // maximum distance between the stars
        float rot = 0; // rotation

        @Override
        public void render(int elapsed) {
            v.lights(); // set the default light
            float avg = v.getSmoothedAmplitude(); // average lerped amplitude
            v.translate(v.width / 2, v.height / 2, 0); // translate to centre
            // rotate big cube
            v.rotateX(rot * .01f);
            v.rotateY(rot * .01f);
            v.rotateZ(rot * .01f);
            for (int x = -MAX; x <= MAX; x += 50) // width
            {
                for (int y = -MAX; y <= MAX; y += 50)// height
                {
                    for (int z = -MAX; z <= MAX; z += 50) // depth
                    {
                        v.pushMatrix();
                        v.translate(x, y, z); // new centre for each star
                        // rotate each star
                        v.rotateX(rot * .02f);
                        v.rotateY(rot * .02f);
                        v.rotateZ(rot * .02f);

                        float c = v.noise(elapsed / (100f), x + y + z) * 360; // get colour
                        star.setFill(v.color(c, 100, 100)); // set colour
                        v.scale(avg * 50); // size of each star
                        v.shape(star);
                        v.popMatrix();
                    }
                }
            }
            rot++;
        }
    }

    class Dots extends VObject {
        Dots(Visual v, PVector pos) {
            super(v, pos);
        }

        @Override
        public void render() {

            float max_distance;
            v.noStroke();
            max_distance = PApplet.dist(0, 0, v.width, v.height);

            for (int i = 0; i <= v.width; i += 20) {
                for (int j = 0; j <= v.height; j += 20) {
                    float c = PApplet.map(i, 0, v.ab.size(), 0, 360); // rainbow coloured
                    v.fill(c, 100, 100);
                    float size = PApplet.dist(v.random(0, v.width), v.random(0, v.height), i, j); // random sized
                    size = size / max_distance * 66;
                    v.ellipse(i, j, size, size);
                }
            }
        }
    }

    class Clock extends VObject {

        Clock(Visual v, PVector pos) {
            super(v, pos);
        }

        public void render(int elapsed) {
            int cx, cy;
            float secondsRadius;
            float minutesRadius;
            float hoursRadius;
            float clockDiameter;

            v.fill(0);
            v.stroke(255);

            int radius = PApplet.min(v.width, v.height) / 3; // circle radius
            // smaller lines
            secondsRadius = (float) (radius * 0.72);
            minutesRadius = (float) (radius * 0.60);
            hoursRadius = (float) (radius * 0.50);
            clockDiameter = (float) (radius * 1.8);

            cx = v.width / 2;
            cy = v.height / 2;

            v.circle(cx, cy, clockDiameter + v.audioAnalysis().mix().lerpedAmplitude * 1000);

            // Angles for sin() and cos() start at 3 o'clock;
            // subtract HALF_PI to make them start at the top
            // Seconds hand ticks in time to the beat (96BPM) -> 96/60 = 1.6
            float s = PApplet.map((int) (elapsed / (1000 / 1.6f)), 0, 60, 0, PApplet.TWO_PI) - PApplet.HALF_PI;
            float m = PApplet.map(PApplet.minute() + PApplet.norm(PApplet.second(), 0, 60), 0, 60, 0, PApplet.TWO_PI)
                    - PApplet.HALF_PI;
            float h = PApplet.map(PApplet.hour() + PApplet.norm(PApplet.minute(), 0, 60), 0, 24, 0, PApplet.TWO_PI * 2)
                    - PApplet.HALF_PI;

            // Draw the hands of the clock
            v.stroke(255);
            v.strokeWeight(3);
            v.line(cx, cy, cx + PApplet.cos(s) * secondsRadius, cy + PApplet.sin(s) * secondsRadius);
            v.strokeWeight(4);
            v.line(cx, cy, cx + PApplet.cos(m) * minutesRadius, cy + PApplet.sin(m) * minutesRadius);
            v.strokeWeight(8);
            v.line(cx, cy, cx + PApplet.cos(h) * hoursRadius, cy + PApplet.sin(h) * hoursRadius);

            // Draw the minute ticks
            v.strokeWeight(5);
            v.beginShape(PApplet.POINTS);
            for (int a = 0; a < 360; a += 6) {
                float angle = PApplet.radians(a);
                float x = cx + PApplet.cos(angle) * secondsRadius;
                float y = cy + PApplet.sin(angle) * secondsRadius;
                v.vertex(x, y);
            }
            v.strokeWeight(2);
            v.endShape();

        }

    }

    class Speaker extends VObject {

        Speaker(Visual v, PVector pos) {
            super(v, pos);
        }

        @Override
        public void render() {
            
            // circles
            // (x1,y1) (x3,y3)
            // (x2,y2) (x4,y4)

            int x1 = v.width / 3;
            int y1 = v.height / 3;
            int x2 = v.width / 3;
            int y2 = v.height / 3 * 2;
            int x3 = v.width / 3 * 2;
            int y3 = v.height / 3;
            int x4 = v.width / 3 * 2;
            int y4 = v.height / 3 * 2;

            // int radius = 100;
            int border = 105;

            int length = ((y2 + border) - (y1 - border));
            int width = ((x2 + border) - (x1 - border));
            float col = v.random(0, 255); /// box colour
            v.fill(v.hueShift(col), 255, 255);

            // 3D boxes
            v.translate(x1, v.height / 2);
            v.rotateX((float) 1.5 * 0.2f);
            v.box(width, length, width);

            v.translate(-x1, -v.height / 2);
            v.translate(x3, v.height / 2);
            v.box(width, length, width);

            v.noStroke();
            // v.frameRate(1); //1 frame per second
            v.noFill();
            v.translate(-x3, -v.height / 2, width);
            v.rotateX((float) .02 * 0.2f);

            for (int i = 0; i < v.ab.size(); i++) {
                float c = PApplet.map(v.ab.get(i), -1, 1, 0, 360);
                v.stroke(v.hueShift(c), 100, 100);
                float radius = v.ab.get(i) * 1000 + 50; // radius size determined by the music
                v.circle(x1 + 30, y1 + 60, radius - 1);
                v.circle(x2 + 30, y2, radius - 1);
                v.circle(x3 - 30, y3 + 60, radius - 1);
                v.circle(x4 - 30, y4, radius - 1);
            }
        }
    }

    class WaveForm extends VObject {
        WaveForm(Visual v, PVector pos) {
            super(v, pos);
        }

        float y = v.height;

        @Override
        public void render() {
            // top and bottom of screen wave form
            for (int i = 0; i < v.ab.size(); i++) {
                float c = PApplet.map(i, 0, v.ab.size(), 0, 360); // rainbow coloured
                v.stroke(c, 100, 100);
                float f = v.ab.get(i) * v.height / 2;
                float x = PApplet.map(i, 0, v.ab.size(), 0, v.width); // x value determined by music
                v.line(x, y + f, x, y - f);
                v.line(x, f, x, -f);

            }
        }
    }

    class Hex extends VObject {
        Hex(Visual v, PVector pos) {
            super(v, pos);
        }

        @Override
        public void render() {
            v.noFill();
            v.translateCenter();
            v.beginShape();
            for (int i = 0; i < v.ab.size(); i++) {
                v.stroke(v.random(0, 360), 100, 100); // random coloured
                float radius = v.ab.get(i) * 300 + 50; // radius determined by music
                double x1 = (PApplet.cos(i) * (PApplet.PI / 180) * 100 * radius);
                double y1 = (PApplet.sin(i) * (PApplet.PI / 180) * 100 * radius);
                v.vertex((float) x1, (float) y1);
            }
            v.endShape();
        }
    }
}
