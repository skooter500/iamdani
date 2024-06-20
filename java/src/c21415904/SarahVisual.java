package c21415904;

import ie.tudublin.IAMDANI;
import ie.tudublin.visual.AudioAnalysis;
import ie.tudublin.visual.VObject;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import processing.core.PApplet;
import processing.core.PVector;

import ddf.minim.AudioBuffer;
import global.GlobalVisual;

public class SarahVisual extends VScene {
    IAMDANI v;
    VObject wf;
    Spiral1 sp1;
    Spiral2 sp2;
    VObject cs;
    VObject sw;
    VObject mb;



    GlobalVisual gv;

    public void enter() {
        v.cqz = 255;
    }

    public SarahVisual(Visual v) {
        super(v);
        this.v = (IAMDANI) v;

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
        //sp1.render();
        // gv.render(elapsed);
        // sp2.render();

        // sw.render();
        // v.strokeWeight(2);
        // gv.render(elapsed);
        // cs.render();
        //v.translate(v.width/2, v.height/2);        

        //v.translate(-v.width/2, -v.height/2);        

        mb.render();
        // wf.render();        
    }

    class Spiral1 extends VObject {

        Spiral1(Visual v, PVector pos) {
            super(v, pos);
        }

        float cx = v.width / 2;
        float cy = v.height / 2;
        float theta = 0.0f;

        public void render() {

            
            
            for (int i = 0; i < v.ab.size(); i++) {
                float c = PApplet.map(i, 0, v.ab.size(), 0, 360);
                v.fill(c, 255, 255);
                v.scale((float) 0.99);
                v.rotate(PApplet.radians(theta));
                v.ellipse(cx, cy, 20 + (v.getSmoothedAmplitude() * 10), 20 + (v.getSmoothedAmplitude() * 10));

            }
            theta += 0.08;
        }

    }

    class Spiral2 extends VObject {

        Spiral2(Visual v, PVector pos) {
            super(v, pos);
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
            for (int i = 0; i < v.ab.size(); i++) {

                // colour calculation
                float c = PApplet.map(i, 0, v.ab.size(), 0, 240);
                v.strokeWeight(2);
                v.stroke(v.hueShift(c), 255, 255);

                // calculate angle, TWO_PI/3 -> each segment has three points, shape moves up
                // and down with amplitude
                float theta = i * (PApplet.TWO_PI / 3 + v.getSmoothedAmplitude() * 5);

                // find points on
                v.pushMatrix();
                float x = PApplet.sin(theta) * radius;
                float y = -PApplet.cos(theta) * radius;

                // increase radius to create spiral effect, add lerped amplitude so shape
                // expands and contracts with music
                radius += 0.2f + v.getSmoothedAmplitude();

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
            for (int i = 0; i <  v.ab.size(); i++) {

                float c = PApplet.map(i, 0,  v.ab.size(), 0, 360);
                v.stroke(c, 100, 100);

                // calculate starting points on circle which each line will be drawn out of
                float x1 = PApplet.sin(theta) * radius;
                float y1 = -PApplet.cos(theta) * radius;

                // get frequency at current position in Audio Buffer and make it visible
                float f =  v.ab.get(i) * 200 + 20;

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
            for (int i = 0; i <  v.ab.size(); i++) {
                float c = PApplet.map(i, 0,  v.ab.size(), 0, 360);
                v.stroke(c, 100, 100);

                // map the position in the audio buffer on TWO_PI
                float angle = PApplet.map(i, 0,  v.ab.size(), 0, PApplet.TWO_PI);
                float radius =  v.ab.get(i) * 200 + 300;

                // get point on circle
                float x = PApplet.sin(angle) * radius;
                float y = -PApplet.cos(angle) * radius;
                v.vertex(x, y);
            }
            v.endShape();

            // Waveforms on edge of screen
            v.beginShape();
            for (int i = 0; i <  v.ab.size(); i++) {
                float c = PApplet.map(i, 0,  v.ab.size(), 0, 360);
                v.stroke(c, 100, 100);
                v.strokeWeight(4);

                float f =  v.ab.get(i) * v.height / 2;
                float x = PApplet.map(i, 0, v.getSmoothedAmplitude() * 10000, -v.width, v.width);

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
            float bands[] = v.getSmoothedBands();
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
                v.sphere(2000 * v.getSmoothedAmplitude() + 200);
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
            for (int i = 0; i <  v.ab.size(); i++) {
                float c = PApplet.map(i, 0,  v.ab.size(), 0, 360);
                v.stroke(c, 50, 100);
                v.strokeWeight(4);
                float f =  v.ab.get(i) * v.height / 2;
                float x = PApplet.map(i, 0, v.getSmoothedAmplitude() * 10000, 0, v.width);
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
                PVector vn = PVector.mult(vel, v.spe);
                pos.add(vn);

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

        
            
            v.beginShape();
            

            v.loadPixels();
            float vsize = 20;
            // show every fourth pixel so processor can handle image
            for (int x = 0; x < v.width; x += vsize) {
                for (int y = 0; y < v.height; y += vsize) {
                    int index = x + y * v.width;
                    float sum = 0;

                    for (Blob b : blobs) {
                        float d = PApplet.dist(x, y, b.pos.x, b.pos.y);
                        sum += 100 * b.r / d * (v.getSmoothedAmplitude());
                    }

                    float c = v.hueShift(sum % 360);
                    for(int row = 0 ; row < vsize ; row ++)
                    {
                        for(int col = 0 ; col < vsize ; col ++)
                        {
                            int i = (x + col) + (y + row) * v.width;
                            v.pixels[i] = v.color(c, 255, 255, v.alp);
                        }                    
                    }
                     // modulus operator makes ball inside of ball

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