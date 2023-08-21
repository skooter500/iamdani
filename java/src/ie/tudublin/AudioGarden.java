package ie.tudublin;

import java.util.ArrayList;
import java.util.Collections;

import C21503599.MyFirstChange;
import c21348423.AdriansVisual;
import c21383126.JenniferVisuals;
import c21415904.SarahVisual;
import controlP5.ControlP5;
import controlP5.Textarea;
import ddf.minim.analysis.BeatDetect;
import infiniteforms.City;
import infiniteforms.Life;
import infiniteforms.Models1;
import infiniteforms.Tadpole;
import oopBaddies.Airish;
import oopBaddies.Anne;
import oopBaddies.Mena;
import oopBaddies.paris;
import processing.core.PFont;
import themidibus.*; //Import the library

public class AudioGarden extends ie.tudublin.visual.Visual implements MidiListener {

    ArrayList<Poly> visuals = new ArrayList<Poly>();

    // Poly play;
    int previousVisual = 0;
    int whichVisual = 0;

    MidiBus myBus; // The MidiBus

    float trails = 10;

    public float speed = 1;

    public void settings() {
        // fullScreen(P3D, 0);
        size(1000, 1000, P3D);
    }

    public static AudioGarden instance;

    public StringBuilder console = new StringBuilder();

    public static void println(String o)
    {
        instance.console.append(o + "\n");
        int len = instance.console.length(); 
        if (len > 2000)
        {
            instance.console = new StringBuilder(instance.console.subSequence(len - 2000, len));
        }   
        if (instance.myTextarea != null)
        {
            instance.myTextarea.setText(instance.console.toString());
            instance.myTextarea.scroll(1.0f);
        }            
        System.out.println(o);
    }

    public void setup() {

        println("Hello");

        colorMode(HSB);
        startMinim();
        rectMode(CENTER);

        cp5 = new ControlP5(this);

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


        visuals.add(new Bloom(this));
        visuals.add(new Models1(this, "audio garden 1.obj"));
        visuals.add(new Models1(this, "eden.obj"));
        visuals.add(new Models1(this, "tudub.obj"));
        visuals.add(new Models1(this, "infiniteForms.obj"));
        visuals.add(new Models1(this, "audio garden 2.obj"));
        // visuals.add(new Models1(this, "thc molecule.obj"));

        
        visuals.add(new Tadpole(this));
        visuals.add(new City(this));
        visuals.add(new Life(this, 2, 1000));
        visuals.add(new Life(this, 1, 1000));
        visuals.add(new Life(this, 0, 1000));

        
        visuals.add(new ManarBrain(this));  

        visuals.add(new LauraSun(this));
        
        // visuals.add(new MyFirstChange(this));
        // visuals.add(new Tadpole(this));
        // visuals.add(new City(this));

        ;
        
        visuals.add(new Models1(this, "eden.obj"));
        visuals.add(new ManarBrain(this));                

        // visuals.add(new JiaHeart(this));
            
        // visuals.add(new LauraSun(this));
        // visuals.add(new paris(this));
        // visuals.add(new Mena(this));
        // visuals.add(new Airish(this));
        
        // visuals.add(new Spiral(this));
        
        // visuals.add(new SinWaves(this));
       
        // visuals.add(new Cubes(this));
        
        // visuals.add(new kalidascope(this));
        
        // 
        // visuals.add(new JenniferVisuals(this));
        
        // visuals.add(new Life(this, 2, 1000));
        // visuals.add(new Life(this, 1, 1000));
        // visuals.add(new Life(this, 0, 1000));
        
        // visuals.add(new Models1(this, "msx.obj"));
        
        // 
        // colorMode(HSB, 360, 100, 100);
        // // visuals.add(new AdriansVisual(this));
        // visuals.add(new Cubesquared2(this));

        // visuals.add(new SarahVisual(this));

        //Collections.shuffle(visuals);

        background(0);
        change(0);

        // String[] fonts = PFont.list();

        // for(String s:fonts)
        // {
        //     println(s);
        // }

        myTextarea = cp5.addTextarea("txt")
                  .setPosition(50,50)
                  .setSize(1000,(int) consoleSize)
                  .setColor(color(consoleColor, 255, 255))                  
                  .setFont(createFont("Hyperspace Bold.otf",30))
                  .setLineHeight(24)
                  ;
  
    }

    float consoleSize = 0;
    float targetSize = 400;
    
    ControlP5 cp5;
    Textarea myTextarea;
    float consoleColor = 128;

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
        instance = this;
        
        resetDefaults();
        
    }

    

    public void noteOn(int channel, int pitch, int velocity) {
        // Receive a noteOn
        println("N+: " + " CH: " + channel +  " PI: " + pitch + " VE: " + velocity);        
        // SPecial codes
        if (pitch == 51)
        {
            showConsole = !showConsole;
            println("CON:" + showConsole);   
            consoleSize = 0;
            myTextarea.setVisible(showConsole);
            return;
        }

        if (pitch == 43)
        {
            resetDefaults();
            return;
        }

        int newVisual = pitch % visuals.size();
        change(newVisual);
    }

    public void noteOff(int channel, int pitch, int velocity) {
        // Receive a noteOff
        println("N-: " + " CH: " + channel, " PI: " + pitch + " VE: " + velocity);  
    }

    public void change(int into) {        
        if (into >= 0 && into < visuals.size()) {
            if (whichVisual >= 0 && whichVisual < visuals.size()) {
                visuals.get(whichVisual).exit();
            }            
            whichVisual = into;
            visuals.get(whichVisual).enter();
            println("ACT: " + whichVisual + ": " + visuals.get(whichVisual).getClass().getName());
        }            
    }

    public void controllerChange(int channel, int number, int value) {
        // Receive a controllerChange
        println("CC: " + " CH: " + channel + " NUM: " + number + " VA: " + value);  

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
            println("BAS: " + base);
        }

        if (number == 114) {
            sensitivity = max(clockWise ? sensitivity + 0.01f : sensitivity - 0.01f, 0);
            println("MULT : " + sensitivity);
        }
        if (number == 74) {
            trails = min(max(clockWise ? trails + 1f : trails - 1f, 0), 40);
            println("BLA: " + trails);
        }

        if (number == 18) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            hueShift = clockWise ? hueShift + 5 : hueShift - 5;
            println("HUE_SHIFT: " + hueShift);
        }

        if (number == 71) {
            speed = min(max(clockWise ? speed + 0.2f : speed - -0.2f, 0), 5);
            println("HUE_SHIFT: " + hueShift);
        }
    }

    public void keyPressed() {
        int newVisual = keyCode - '0';
        change(newVisual);
    }

    boolean showConsole = true;

    public void draw() {

        
        // background(0);
        colorMode(RGB);
        blendMode(SUBTRACT);
        fill(255, trails);
        rect(0, 0, width * 4, height * 4);
        blendMode(BLEND);
        colorMode(HSB);

        if (showConsole)
        {
            consoleSize = lerp(consoleSize, targetSize, 0.1f);
            myTextarea.setSize(800, (int) consoleSize);
            myTextarea.setVisible(true);
            myTextarea.setColor(color(hueShift(consoleColor), 255, 255));
        }

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

        pushMatrix();
        pushStyle();
        visuals.get(whichVisual).render(frameCount); // renders the currently loaded visual
        popStyle();        
        popMatrix();

        

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
