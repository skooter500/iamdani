package ie.tudublin;

import java.util.ArrayList;
import java.util.Collections;

import c21348423.AdriansVisual;
import c21383126.JenniferVisuals;
import c21415904.SarahVisual;
import ddf.minim.analysis.BeatDetect;
import infiniteforms.Life;
import infiniteforms.Models1;
import infiniteforms.Tadpole;
import themidibus.*; //Import the library

public class AudioGarden extends ie.tudublin.visual.Visual implements MidiListener {

    ArrayList<Poly> visuals = new ArrayList<Poly>();

    // Poly play;
    int previousVisual = 0;
    int whichVisual = 0;

    MidiBus myBus; // The MidiBus

    float trails = 10;

    public float speed = 1;

    void resetDefaults()
    {
        trails = 10;
        sensitivity = 1.0f;
        base = 0.3f;
        speed = 1;
        hueShift = 0;
    }

    public AudioGarden() {
        super(1024, 44100, 0.5f);

        resetDefaults();
        
    }

    public void settings() {
        fullScreen(P3D, 0);
        //size(1000, 1000, P3D);
    }

    public void noteOn(int channel, int pitch, int velocity) {
        // Receive a noteOn
        println();
        println("Note On:");
        println("--------");
        println("Channel:" + channel);
        println("Pitch:" + pitch);
        println("Velocity:" + velocity);

        int newVisual = pitch % visuals.size();
        change(newVisual);
    }

    public void noteOff(int channel, int pitch, int velocity) {
        // Receive a noteOff
        println();
        println("Note Off:");
        println("--------");
        println("Channel:" + channel);
        println("Pitch:" + pitch);
        println("Velocity:" + velocity);
    }

    public void change(int into) {
        println("Changing to: " + into);
        if (whichVisual >= 0 && whichVisual < visuals.size()) {
            visuals.get(whichVisual).exit();
        }
        whichVisual = into;
        if (into >= 0 && into < visuals.size()) {
            visuals.get(whichVisual).exit();
            whichVisual = into;
            visuals.get(whichVisual).enter();
        }
    }

    public void controllerChange(int channel, int number, int value) {
        // Receive a controllerChange
        println();
        println("Controller Change:");
        println("--------");
        println("Channel:" + channel);
        println("Number:" + number);
        println("Value:" + value);

        println(value);

        boolean clockWise = (value < 100);

        if (number == 7) {
            int newVisual = whichVisual;
            if (clockWise)
                newVisual = (newVisual + 1) % visuals.size();
            else {
                newVisual--;
                if (newVisual < 0) {
                    newVisual = visuals.size() - 1;
                }
            }
            change(newVisual);
        }

        if (number == 10) {
            base = max(clockWise ? base + 0.01f : base - 0.1f, 0.01f);
            println("base : " + base);
        }

        if (number == 114) {
            sensitivity = max(clockWise ? sensitivity + 0.01f : sensitivity - 0.01f, 0);
            println("sensitivity : " + sensitivity);
        }
        if (number == 74) {
            trails = min(max(clockWise ? trails + 0.2f : trails - 0.2f, 0), 20);
            println("trails : " + trails);
        }

        if (number == 71) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            hueShift = clockWise ? hueShift + 5 : hueShift - 5;
            println("hue shift : " + hueShift);
        }

        if (number == 19) {

        }

        if (number == 75)
        {
            resetDefaults();
        }
    }

    public void keyPressed() {
        int newVisual = (int) pingpongmap(keyCode - '0', 0, 1024, 0, visuals.size());
        change(newVisual);
    }

    public void setup() {
        colorMode(HSB, 360, 100, 100);
        startMinim();
        rectMode(CENTER);

        MidiBus.list();
        myBus = new MidiBus(this, 1, 4);
        // myBus.addMidiListener(this);
        startListening();

        // eye = loadShape("eyeball.obj");
        // grave = loadShape("gravestone.obj");
        // texture = loadImage("gravestone.mtl");
        noiseSeed(0l);

        beat = new BeatDetect(ai.bufferSize(), ai.sampleRate());
        beat.setSensitivity(10);
        // bl = new BeatListener(beat, getAudioPlayer());
        visuals.add(new Spiral(this));
        
         visuals.add(new SinWaves(this));
       
        visuals.add(new Cubes(this));
        
        visuals.add(new kalidascope(this));
        
        visuals.add(new Bloom(this));
        visuals.add(new JenniferVisuals(this));
        
        
        visuals.add(new Tadpole(this));        
        visuals.add(new Life(this, 2, 1000));
        visuals.add(new Life(this, 1, 1000));
        visuals.add(new Life(this, 0, 1000));

        visuals.add(new Models1(this, "msx.obj"));

        visuals.add(new Models1(this, "infiniteForms.obj"));
        colorMode(HSB, 360, 100, 100);
        // visuals.add(new AdriansVisual(this));
        visuals.add(new Cubesquared2(this));

        visuals.add(new SarahVisual(this));

        //Collections.shuffle(visuals);

        background(0);
        change(0);
    }

    public void draw() {

        // background(0);
        colorMode(RGB);
        blendMode(SUBTRACT);
        fill(255, trails);
        rect(0, 0, width * 4, height * 4);
        blendMode(BLEND);
        colorMode(HSB);

        stroke(255, 255, 255);
        // background(0);
        try {
            // Call this if you want to use FFT data
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        // Call this is you want to use frequency bands
        calculateFrequencyBands();
        // will pulse an object with music volume
        calculateAverageAmplitude();

        speed = map(getAmplitude(), 0, 1, 0, 0.1f);

        visuals.get(whichVisual).render(frameCount); // renders the currently loaded visual

        //hueShift();
    }

    

    public void hueShift() {
        loadPixels();

        for (int i = 0; i < pixels.length; i++) {
            int old = pixels[i];
            int shifted = (int) (hue(old) + hueShift) % 256;
            pixels[i] = color(shifted, saturation(old), brightness(old), alpha(old));
        }

        updatePixels();
    }

    

}
