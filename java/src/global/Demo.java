package global;

import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import ie.tudublin.IAMDANI;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import processing.core.PApplet;

public class Demo extends VScene {
    float height4 = v.height / 4;
    float height8 = v.height / 8;
    float width4 = v.width / 4;

    public Demo(IAMDANI v) {
        super(v);
    }

    @Override
    public void render() {
        // 4 sections
        v.background(0);
        for (int i = 0; i < 4; i++) {
            v.noStroke();
            v.fill(i * 50, 100, 50);
            v.pushMatrix();
            v.translate(0, i * v.height / 4);
            v.rect(0, 0, v.width, v.height / 4);
            v.popMatrix();
        }
        v.rectMode(PApplet.CORNER);
        waveform();
        spectrum();
        spectrumLog();
        bands();
        beat();
    }

    public void waveform() {
        float[] ab = v.audioAnalysis().mix().waveform;
        v.pushMatrix();
        v.translate(0, height4 * 4 - height8);
        v.beginShape();
        for (int i = 0; i < ab.length; i++) {
            float x = PApplet.map(i, 0, ab.length, 0, v.width);
            float y = PApplet.map(ab[i], -1, 1, height8, -height8);
            v.stroke(255);
            v.vertex(x, y);
        }
        v.endShape();
        v.popMatrix();
    }

    public void spectrum() {
        float[] spec = v.audioAnalysis().mix().lerpedSpectrum;
        for (int i = 0; i < spec.length; i++) {
            // float x = PApplet.map(i, 0, fft.specSize(), 0, v.width);
            float x = PApplet.map(i, 0, spec.length, 0, v.width);
            float h = PApplet.map(spec[i], 0, 100, 0, -height4);

            v.stroke(255);
            v.pushMatrix();
            v.translate(0, height4 * 3);
            v.line(x, 0, x, h);
            v.popMatrix();
        }
    }

    public void spectrumLog() {
        float[] spec = v.audioAnalysis().mix().lerpedSpectrum;
        for (int i = 0; i < spec.length; i++) {
            // float x = PApplet.map(i, 0, fft.specSize(), 0, v.width);
            float x = PApplet.map(PApplet.log(i), 0, PApplet.log(spec.length), 0, v.width);
            float h = PApplet.map(spec[i], 0, 100, 0, height4);

            v.stroke(255);
            v.pushMatrix();
            v.translate(0, height4 * 2);
            v.line(x, 0, x, h);
            v.popMatrix();
        }
    }

    public void bands() {
        FFT fft = v.fft();
        // v.fill(255);
        v.pushMatrix();
        v.translate(0, height4 * 2);
        for (int i = 0; i < fft.avgSize(); i++) {
            float centerFrequency = fft.getAverageCenterFrequency(i);
            // how wide is this average in Hz?
            float averageWidth = fft.getAverageBandWidth(i);

            // we calculate the lowest and highest frequencies
            // contained in this average using the center frequency
            // and bandwidth of this average.
            float lowFreq = centerFrequency - averageWidth / 4;
            float highFreq = centerFrequency + averageWidth / 4;

            // freqToIndex converts a frequency in Hz to a spectrum band index
            // that can be passed to getBand. in this case, we simply use the
            // index as coordinates for the rectangle we draw to represent
            // the average.
            int xl = (int) fft.freqToIndex(lowFreq) * 1;
            int xr = (int) fft.freqToIndex(highFreq) * 1;
            v.rect(xl, 0, xr, -fft.getAvg(i) * 4);

        }
        v.popMatrix();
    }

    public void bandsLog() {

    }

    public void beat() {
        BeatDetect beat = v.beat();
        float hat, kick, snare;
        hat = kick = snare = 0;
        // 4 rects int a row, one for isHat, isKick, isSnare
        if (beat.isHat())
            hat = 255;
        if (beat.isKick())
            kick = 255;
        if (beat.isSnare())
            snare = 255;
        if (beat.isRange(0, 0, 0))

            // hat = Visual.lerp(hat, 0, .9f);
            // kick = Visual.lerp(kick, 0, .9f);
            // snare = Visual.lerp(snare, 0, .9f);

            v.pushMatrix();
        {
            v.noStroke();
            v.fill(255);
            v.text("Hat", 0, 0);
            v.text("Kick", width4, 0);
            v.text("Snare", width4 * 2, 0);

            v.fill(255, 0);
            v.translate(0, 0);
            v.textAlign(PApplet.LEFT, PApplet.TOP);
            v.fill(255, hat);
            v.rect(0, 0, width4, height4);
            v.fill(255, kick);
            v.rect(width4, 0, width4, height4);
            v.fill(255, snare);
            v.rect(width4 * 2, 0, width4, height4);
        }
        v.popMatrix();

    }

}
