package c21415904;

import ie.tudublin.visual.AudioAnalysis;
import ie.tudublin.visual.VObject;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import processing.core.PApplet;
import processing.core.PVector;

import ddf.minim.AudioBuffer;
import global.GlobalVisual;

public class SarahVisual extends VScene {
    Visual v;
    VObject wf;
    Spiral1 sp1;
    Spiral2 sp2;
    VObject cs;
    VObject sw;
    VObject mb;

    GlobalVisual gv;
    AudioBuffer ab;
    AudioAnalysis aa;

    public SarahVisual(Visual v) {
        super(v);
        this.v = v;

        ab = v.audioPlayer().mix;
        aa = v.audioAnalysis();

        sp1 = new Spiral1(v, new PVector(0, 0, 0));
        sp2 = new Spiral2(v, new PVector(0, 0, 0));
        sw = new SoundWaves(v, new PVector(0, 0, 0));
        cs = new Circles(v, new PVector(0, 0, 0));
        wf = new WaveForm(v, new PVector(0, 0, 0));
        mb = new metaBalls(v, new PVector(0, 0, 0));
        gv = new GlobalVisual(v);
    }

    public void render(int elapsed) {
        // 0:00 - 1:02 - Intro, V1, C1

        //intro
        if (elapsed > v.toMs(0, 0, 0) && elapsed < v.toMs(0, 10, 800)) {
            v.background(0);
            sp1.render();
        }
        if (elapsed > v.toMs(0, 10, 800) && elapsed < v.toMs(0, 21, 0)) {
            v.background(0);
            gv.render(elapsed);
            sp2.render();
        }
        if (elapsed > v.toMs(0, 21, 0) && elapsed < v.toMs(0, 40, 0)) {
            v.background(0);
            sw.render();
        }
        if (elapsed > v.toMs(0, 40, 0) && elapsed < v.toMs(0, 50, 0)) {
            v.background(0);
            v.strokeWeight(2);
            gv.render(elapsed);
            cs.render();
        }
        if (elapsed > v.toMs(0, 50, 0) && elapsed < v.toMs(1, 3, 0)) {
            v.background(0);
            mb.render();
            wf.render();
        }

        System.out.println(elapsed);
    }

    class Spiral1 extends VObject {

        Spiral1(Visual v, PVector pos) {
            super(v, pos);
            v.background(0);
        }

        float cx = v.width / 2;
        float cy = v.height / 2;
        float theta = 0.0f;

        public void render() {

            v.noStroke();
            v.translate(cx, cy);

            for (int i = 0; i < ab.size(); i++) {
                float c = PApplet.map(i, 0, ab.size(), 0, 360);
                v.fill(c, 100, 100);
                v.scale((float) 0.99);
                v.rotate(PApplet.radians(theta));
                v.ellipse(cx, cy, 50 + (aa.mix().lerpedAmplitude * 750), 50 + (aa.mix().lerpedAmplitude * 750));

            }
            theta += 0.08;
        }

    }

    class Spiral2 extends VObject {

        Spiral2(Visual v, PVector pos) {
            super(v, pos);
            v.background(0);
        }

        float cx, cy;
        float rot = 0;

        @Override
        public void render() {

            // make center of screen (0,0)
            v.translateCenter(PApplet.CENTER, PApplet.CENTER);
            float radius = 1f;

            v.pushMatrix();
            cx = 0;
            cy = 0;
            for (int i = 0; i < ab.size(); i++) {

                // colour calculation
                float c = PApplet.map(i, 0, ab.size(), 0, 240);
                v.strokeWeight(2);
                v.stroke(c, c, c);

                // calculate angle, TWO_PI/3 -> each segment has three points, shape moves up
                // and down with amplitude
                float theta = i * (PApplet.TWO_PI / 3 + aa.mix().lerpedAmplitude * 5);

                // find points on
                v.pushMatrix();
                float x = PApplet.sin(theta) * radius;
                float y = -PApplet.cos(theta) * radius;

                // increase radius to create spiral effect, add lerped amplitude so shape
                // expands and contracts with music
                radius += 0.2f + aa.mix().lerpedAmplitude;

                v.line(cx, cy, x, y);

                // draw line out from last line to build up
                cx = x;
                cy = y;

                v.popMatrix();

            } // end for

            v.popMatrix();

        } // end render

    } // end Spiral

    class SoundWaves extends VObject {

        SoundWaves(Visual v, PVector pos) {
            super(v, pos);
        }

        float cx, cy;
        float theta = 0;
        float radius = 100;
        float rot = 0;

        @Override
        public void render() {
            // inner waveform
            v.translateCenter(PApplet.CENTER, PApplet.CENTER);
            v.noFill();
            v.strokeWeight(2);

            v.pushMatrix();
            for (int i = 0; i < ab.size(); i++) {

                float c = PApplet.map(i, 0, ab.size(), 0, 360);
                v.stroke(c, 100, 100);

                // calculate starting points on circle which each line will be drawn out of
                float x1 = PApplet.sin(theta) * radius;
                float y1 = -PApplet.cos(theta) * radius;

                // get frequency at current position in Audio Buffer and make it visible
                float f = ab.get(i) * 200 + 20;

                // calculate endpoint of each line, line will expand and contract with frequency
                float x2 = (x1 + (PApplet.sin(i) * (PApplet.PI / 180) * radius * f));
                float y2 = (y1 + (-PApplet.cos(i) * (PApplet.PI / 180) * radius * f));

                v.line(x1, y1, x2, y2);

                // increment angle to form circle
                theta += 0.1f;
            }
            v.popMatrix();

            // outer waveform
            v.beginShape();
            for (int i = 0; i < ab.size(); i++) {
                float c = PApplet.map(i, 0, ab.size(), 0, 360);
                v.stroke(c, 100, 100);

                // map the position in the audio buffer on TWO_PI
                float angle = PApplet.map(i, 0, ab.size(), 0, PApplet.TWO_PI);
                float radius = ab.get(i) * 200 + 300;

                // get point on circle
                float x = PApplet.sin(angle) * radius;
                float y = -PApplet.cos(angle) * radius;
                v.vertex(x, y);
            }
            v.endShape();

            // Waveforms on edge of screen
            v.beginShape();
            for (int i = 0; i < ab.size(); i++) {
                float c = PApplet.map(i, 0, ab.size(), 0, 360);
                v.stroke(c, 100, 100);
                v.strokeWeight(4);

                float f = ab.get(i) * v.height / 2;
                float x = PApplet.map(i, 0, aa.mix().lerpedAmplitude * 10000, -v.width, v.width);

                v.line(x, v.height / 2 + f, x, v.height / 2 - f); // bottom
                v.line(x, -v.height / 2 + f, x, -v.height / 2 - f); // top
                v.line(-v.width / 2, x - v.height / 2, -v.width / 2 + f, x - v.height / 2); // left
                v.line(v.width / 2, x - v.height / 2, v.width / 2 + f, x - v.height / 2); // right

            }
            v.endShape();

        }
    }

    class Circles extends VObject {

        Circles(Visual v, PVector pos) {
            super(v, pos);
        }

        float rot = 0;

        @Override
        public void render() {

            v.noFill();
            v.translateCenter(PApplet.CENTER, PApplet.CENTER);

            v.rotate(PApplet.radians(rot));

            // store array of smoothed frequency bands
            float bands[] = aa.mix().lerpedBands;
            v.strokeWeight(2);

            for (int i = 0; i < bands.length; i++) {

                float c = PApplet.map(i, 0, bands.length, 0, 360);
                v.stroke(c, 100, 100);
                v.strokeWeight(4);

                // radius corresponds to frequency
                float r = bands[i] * 10 + 100;

                // circles
                v.beginShape();
                v.ellipseMode(PApplet.CENTER);
                v.circle(36, 36, r);
                v.circle(-36, -36, r);
                v.circle(36, -36, r);
                v.circle(-36, 36, r);

                v.circle(50, 0, r);
                v.circle(-50, 0, r);
                v.circle(0, 50, r);
                v.circle(0, -50, r);
                v.endShape();

                // sphere
                v.pushMatrix();
                v.stroke(v.random(0, 360), 50, 50);
                v.strokeWeight(0.5f);
                v.rotateX(PApplet.radians(rot));
                v.rotateY(PApplet.radians(rot));
                v.rotateZ(PApplet.radians(rot));
                v.sphere(2000 * aa.mix().lerpedAmplitude + 200);
                v.popMatrix();
            }
            rot += 1;
        }

    }

    class WaveForm extends VObject {

        WaveForm(Visual v, PVector pos) {
            super(v, pos);
        }

        @Override
        public void render() {
            v.beginShape();
            for (int i = 0; i < ab.size(); i++) {
                float c = PApplet.map(i, 0, ab.size(), 0, 360);
                v.stroke(c, 50, 100);
                v.strokeWeight(4);
                float f = ab.get(i) * v.height / 2;
                float x = PApplet.map(i, 0, aa.mix().lerpedAmplitude * 10000, 0, v.width);
                v.line(x, v.height / 2 + f, x, v.height / 2 - f);
            }
            v.endShape();
        }

    }

    class metaBalls extends VObject {

        class Blob {
            PVector pos;
            PVector vel;
            float r;

            Blob(float x, float y) {
                pos = new PVector(x, y); // position of blob
                vel = PVector.random2D(); // velocity of blob
                vel.mult(v.random(2, 5)); // randomise velocity
                r = 40;
            }

            public void update() {
                // keep blobs moving
                pos.add(vel);

                // stop blobs from leaving screen
                if (pos.x > v.width || pos.x < 0) {
                    vel.x *= -1;
                }
                if (pos.y > v.height || pos.y < 0) {
                    vel.y *= -1;
                }
            }

        }

        // array of blobs
        Blob[] blobs = new Blob[20];

        metaBalls(Visual v, PVector pos) {
            super(v, pos);

            // create blobs
            for (int i = 0; i < blobs.length; i++) {
                blobs[i] = new Blob(v.random(v.width), v.random(v.height));
            }
        }

        @Override
        public synchronized void render() {
            v.background(0, 0, 50);

            v.background(0,0,50);
            v.beginShape();

            v.loadPixels();
            // show every fourth pixel so processor can handle image
            for (int x = 0; x < v.width; x += 4) {
                for (int y = 0; y < v.height; y += 4) {
                    int index = x + y * v.width;
                    float sum = 0;

                    for (Blob b : blobs) {
                        float d = PApplet.dist(x, y, b.pos.x, b.pos.y);
                        sum += 100 * b.r / d * (aa.mix().lerpedAmplitude * 50);
                    }

                    v.pixels[index] = v.color(sum % 360, 100, 100); // modulus operator makes ball inside of ball

                }
            }
            v.updatePixels();

            for (Blob b : blobs) {
                b.update();
            }

            v.endShape();

        }
    }
}