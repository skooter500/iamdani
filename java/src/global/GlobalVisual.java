package global;

import ie.tudublin.IAMDANI;
import ie.tudublin.visual.AudioAnalysis;
import ie.tudublin.visual.EaseFunction;
import ie.tudublin.visual.VAnimation;
import ie.tudublin.visual.VObject;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import processing.core.PApplet;
import processing.core.PVector;

public class GlobalVisual extends VScene {

    WaveformFrame waveformFrame;

    int length = 50000;

    public GlobalVisual(IAMDANI v) {
        super(v);
        waveformFrame = new WaveformFrame(v);
    }

    @Override
    public void render(int elapsed) {
        waveformFrame.render(elapsed);
    }


    class WaveformFrame extends VObject {
        VAnimation effect;
        public WaveformFrame(IAMDANI v) {
            super(v);
            effect = new VAnimation(length);
            // Start
            // effect.addTransition(0, 500, 0, 255, EaseFunction.easeLinear);
            // Transition to solo at 1:48
            // effect.addTransition(v.toMs(1, 47, 750), 500, 255, 0, EaseFunction.easeLinear);
            // Transition to Verse at 2:30

            // Sarah Visuals
            effect.addTransition(v.toMs(0, 10, 800), 500, 0, 255, EaseFunction.easeLinear);
            // Fade out at 3:58
            effect.addTransition(v.toMs(1, 48, 0), 1000, 255, 0, EaseFunction.easeLinear);


            // AJ Visuals
            effect.addTransition(v.toMs(2, 30, 750), 500, 0, 255, EaseFunction.easeLinear);
            // Fade out at 3:58
            effect.addTransition(v.toMs(3, 54, 0), 1000, 255, 0, EaseFunction.easeLinear);
        }

        public void render(int elapsed) {
            float QUARTER_PI = Visual.QUARTER_PI;
            v.noFill();
            v.stroke(255, effect.getValue(elapsed));

            // Waveform line top left
            v.pushMatrix();
            v.rectMode(PApplet.CORNER);
            v.translate(0, 0, 0);
            v.rotate(QUARTER_PI);
            waveformLine(new PVector(0, 0, 0), new PVector(0, 0, -4800), v.audioAnalysis());
            v.popMatrix();

            // Waveform line top right
            v.pushMatrix();
            v.translate(v.width, 0, 0);
            v.rotate(QUARTER_PI * 3);
            waveformLine(new PVector(0, 0, 0), new PVector(0, 0, -4800), v.audioAnalysis());
            v.popMatrix();

            // Waveform line bottom left
            v.pushMatrix();
            v.translate(0, v.height, 0);
            v.rotate(QUARTER_PI * 7);
            waveformLine(new PVector(0, 0, 0), new PVector(0, 0, -4800), v.audioAnalysis());
            v.popMatrix();

            // Waveform line bottom right
            v.pushMatrix();
            v.translate(v.width, v.height, 0);
            v.rotate(QUARTER_PI * 5);
            waveformLine(new PVector(0, 0, 0), new PVector(0, 0, -4800), v.audioAnalysis());
            v.popMatrix();

        }

        private void waveformLine(PVector p1, PVector p2, AudioAnalysis aa) {
            v.beginShape();
            for (int i = 0; i < v.ab.size(); i++) {
                float x = Visual.lerp(p1.x, p2.x, i / (float) v.ab.size());
                float y = Visual.lerp(p1.y, p2.y, i / (float) v.ab.size());
                float z = Visual.lerp(p1.z, p2.z, i / (float) v.ab.size());

                y += v.ab.get(i) * 1000;

                v.vertex(x, y, z);
            }
            v.endShape();
        }
    }

}
