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
import infiniteforms.IFCubes;
import infiniteforms.Life;
import infiniteforms.Models1;
import infiniteforms.Nematode;
import oopBaddies.Airish;
import oopBaddies.Anne;
import oopBaddies.Mena;
import oopBaddies.paris;
import processing.core.PFont;
import themidibus.*; //Import the library

public class AudioGarden extends ie.tudublin.visual.Visual implements MidiListener {

    ArrayList<Poly> visions = new ArrayList<Poly>();

    // Poly play;
    int previousVisual = 0;
    int whichVisual = 0;

    MidiBus myBus; // The MidiBus

    float ald = 20;

    public void settings() {
        //fullScreen(P3D, 0);
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

        println("I aM DANI");
        
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
        visions.add(new Cubes(this));
        visions.add(new Bloom(this));
        visions.add(new SinWaves(this));  
        visions.add(new Spiral(this));
        visions.add(new IFCubes(this, 2, 150, -600)); 
        visions.add(new ManarBrain(this));  
        visions.add(new Models1(this, "eye.obj", true));
        visions.add(new Models1(this, "eden.obj", false));
        visions.add(new Models1(this, "tudub.obj", false));
        visions.add(new DANI(this, "captainb.txt"));
        visions.add(new Models1(this, "audio garden 2.obj", false));
        visions.add(new Models1(this, "msx.obj", false));
         
        visions.add(new IFCubes(this,7, 250, -600));  
        visions.add(new IFCubes(this,30, 150, -400));
        visions.add(new Airish(this));
        visions.add(new Mena(this));
        visions.add(new Bands(this, 200, 0, 0, 0));        
        visions.add(new paris(this));        
        visions.add(new Spiral(this));
        visions.add(new Cubesquared2(this));
        
        visions.add(new SarahVisual(this));
        visions.add(new JenniferVisuals(this));
        visions.add(new SinWaves(this));        
        visions.add(new LauraSun(this));
        visions.add(new Nematode(this));
        visions.add(new Bloom(this));
        

        
        //visuals.add(new Models1(this, "thc molecule.obj"));
 

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
                  .setLineHeight(30)
                  .setText(console.toString())
                  .setVisible(true);
                  ;
  
    }

    float consoleSize = 0;
    float targetSize = 400;
    
    ControlP5 cp5;
    Textarea myTextarea;
    float consoleColor = 128;

    void resetDefaults()
    {
        ald = 10;
        sensitivity = 1.0f;
        base = 0.3f;
        speed = 1.0f;
        hueShift = 0;
        alp = 255;
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

        if (pitch == 49)
        {
            change(whichVisual + 1);
            return;
        }

        if (pitch == 41)
        {
            change(whichVisual - 1);
            return;
        }

        if (pitch == 51)
        {
            showConsole = !showConsole;
            println("CON:" + showConsole);   
            consoleSize = 0;
            if (!showConsole)
            {
                myTextarea.setVisible(showConsole);
            }
            return;
        }

        if (pitch == 42)
        {
            println("STA");
            visions.get(whichVisual).enter();
            return;
        }

        if (pitch == 50)
        {
            println("RST");
            resetDefaults();
            return;
        }

        int newVisual = pitch % visions.size();
        change(newVisual);
    }

    public void noteOff(int channel, int pitch, int velocity) {
        // Receive a noteOff
        println("N-: " + " CH: " + channel, " PI: " + pitch + " VE: " + velocity);  
    }

    public void change(int into) 
    {    
        if (into < 0)
        {
            into = visions.size() + into;
        }
        into = into % visions.size();
        if (whichVisual >= 0 && whichVisual < visions.size()) {
            visions.get(whichVisual).exit();
        }            
        whichVisual = into;
        visions.get(whichVisual).enter();
        println("ACT " + whichVisual + ": " + visions.get(whichVisual).getClass().getName());         
    }

    
    public void controllerChange(int channel, int number, int value) {
        // Receive a controllerChange
        println("CC: " + " CH: " + channel + " NUM: " + number + " VA: " + value);  

        boolean clockWise = (value < 100);

        if (number == 7) {
            speed = min(max(clockWise ? speed + 0.1f : speed - 0.1f, 0.1f), 2);
            println("SPE: " + speed);
        }

        if (number == 10) {
            base = max(clockWise ? base + 0.01f : base - 0.01f, 0.01f);
            println("BAS: " + base);
        }

        if (number == 114) {
            sensitivity = max(clockWise ? sensitivity + 0.1f : sensitivity - 0.1f, 0);
            println("MUL: " + sensitivity);
        }
        if (number == 74) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            hueShift = clockWise ? hueShift + 1f : hueShift - 1f;
            println("HUE: " + hueShift);
        }

        if (number == 18) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            hueShift = clockWise ? hueShift + 5f : hueShift - 5f;
            println("HUE: " + hueShift);
        }

        if (number == 19) {
            ald = min(max(clockWise ? ald + 1f : ald - 1f, 0), 50);
            println("ALD: " + ald);
        }

        if (number == 71) {
            alp = min(max(clockWise ? alp + 1f : alp - 1f, 0), 255);
            println("APL: " + alp);
        }
        if (number == 76) {
            xRotation = clockWise ? xRotation + 0.01f : xRotation - 0.01f;
            println("X: " + xRotation);
        }
        if (number == 16) {
            zRotation = clockWise ? zRotation + 0.01f : zRotation - 0.01f;
            println("APL: " + zRotation);
        }
        // int newVisual = whichVisual;
        //     if (clockWise)
        //         newVisual = (newVisual + 1) % visions.size();
        //     else {
        //         newVisual--;
        //         if (newVisual < 0) {
        //             newVisual = visions.size() - 1;
        //         }
        //     }
        //     change(newVisual);        
    }

    public float xRotation = 0;
    public float zRotation = 0;


    public void keyPressed() {

        if (key >= '0' && key <= '9') 
        {
            int newVisual = keyCode - '0';
            change(newVisual);
        }

        if (keyCode == UP)
        {
            change(whichVisual - 1);
            return;
        }
        if (keyCode == DOWN)
        {
            change(whichVisual + 1);
            return;
        }
        if (key == ' ')
        {

        }


    }

    boolean showConsole = true;

    public void draw() {

        
        if (ald == 50)
        {        
            background(0);
        }
        else
        {
            colorMode(RGB);
            blendMode(SUBTRACT);
            fill(255, ald);
            rect(0, 0, width * 4, height * 4);
            blendMode(BLEND);
        }
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

        //speed = map(getAmplitude(), 0, 1, 0, 0.1f);

        pushMatrix();
        pushStyle();
        visions.get(whichVisual).render(frameCount); // renders the currently loaded visual
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
