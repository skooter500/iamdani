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
import processing.core.PShape;
import themidibus.*; //Import the library



public class AudioGarden extends ie.tudublin.visual.Visual implements MidiListener {

    ArrayList<Poly> visions = new ArrayList<Poly>();

    // Poly play;
    int previousVisual = 0;
    int whichVisual = 0;

    MidiBus myBus = null; // The MidiBus

    float ald = 20;

    public void settings() {
        fullScreen(P3D, 1);
        //size(1000, 1000, P3D);
    }

    PShape sphere;

    public static AudioGarden instance;

    public StringBuilder console = new StringBuilder();

    public enum Modes { Ctrl, Auto, AutoRandom};
    
    public Modes mode = Modes.Ctrl;

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

    public void resetMessage()
    {
        console = new StringBuilder();
        println("MSX System");
        println("version 1.0");
        println("Copyright 1983 by microsoft");
        println("ok");
        println("load \"DANI.BAS\"");
        println("ok");
        println("RUN");
        println("Greetings Human!");
        println("This is your MSX speaking");
        println("I AM DANI");        
        println("dynamic articicial non-intelligence");
        println("Talk to me and I will learn what you say and answer you");
        println("speak now or forever hold your peace");
        
    }

    public void midiConnect()
    {
        try
        {
            MidiBus.list();        
            int daniMidi = -1;
            for (int i = 0 ; i < MidiBus.availableInputs().length ; i ++)
            {
                String curr = MidiBus.availableInputs()[i];
                if (curr.equals("iamdani"))
                {
                    daniMidi = i;
                    println("iamdani in port: " + daniMidi);
                }
            }

            if (daniMidi == -1)
            {
                for (int i = 0 ; i < MidiBus.availableInputs().length ; i ++)
                {
                    String curr = MidiBus.availableInputs()[i];
                    if (curr.equals("Arturia BeatStep"))
                    {
                        daniMidi = i;
                        println("Arturia BeatStep in port: " + daniMidi);
                    }
                }
            }
        
            if (daniMidi == -1)
            {
                println("Insert joystick and press enter");
            }
            else
            {
                if (myBus != null)
                {
                    myBus.close();
                }
                
                myBus = new MidiBus(this, daniMidi, 0);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }     
    }

    // public void sphere(float size)
    // {
    //     pushMatrix();
    //     scale(size);
    //     shape(sphere);
    //     popMatrix();
    // }

    public void setup() {
        sphere = loadShape("sphere.obj");


        noCursor();
        colorMode(HSB);
        startMinim();
        //rectMode(CENTER);

        cp5 = new ControlP5(this);

        resetMessage();

        midiConnect();


        // myBus.addMidiListener(this);
        startListening();

        // eye = loadShape("eyeball.obj");
        // grave = loadShape("gravestone.obj");
        // texture = loadImage("gravestone.mtl");
        noiseSeed(0l);

        
        beat = new BeatDetect(ai.bufferSize(), ai.sampleRate());
        beat.setSensitivity(10);
        visions.add(new Cubesquared2(this));
        visions.add(new Life(this, 2, 10000, 100));
        
        visions.add(new Life(this, 3, 10000, 200));
        
        visions.add(new MSXLogos(this, "msx.obj"));
        visions.add(new MSXLogos(this, "chip.obj"));        
        visions.add(new Basic(this, "DANI.BAS"));
        
        visions.add(new infiniteforms.Cube(this));
        visions.add(new IFCubes(this,7, 150, -600));
        visions.add(new IFCubes(this,30, 150, -400)); 
        visions.add(new DANI(this, "captainb.txt"));
        visions.add(new Life(this, 2, 10000, 100));
        visions.add(new paris(this));  
        visions.add(new Life(this, 0, 10000, 100));        
        visions.add(new Life(this, 1, 10000, 100));                    
        visions.add(new Nematode(this));        
        
        //YM2413

        visions.add(new Life(this, 4, 10000, 100));        
        
        visions.add(new Models1(this, "tudub.obj", false, true));
        visions.add(new Mena(this));
        visions.add(new Models1(this, "msx.obj", false, true));
        visions.add(new LauraSun(this));
        visions.add(new Cubes(this));
        
        visions.add(new Bloom(this));   
        visions.add(new Models1(this, "eye.obj", true, false));
        
        visions.add(new ManarBrain(this));
        visions.add(new Spiral(this));        
              
        
                                          
        // visions.add(new Airish(this));
        
        
        //visions.add(new Bands(this, 200, 0, 0, 0));        
        //visions.add(new paris(this));        
        //visions.add(new Spiral(this));
        //visions.add(new SarahVisual(this));
        //visions.add(new JenniferVisuals(this));    
        
        
        // visions.add(new Life(this, 1, 1000));
        

        // new set
        
        


        
        ////visions.add(new SarahVisual(this));
        
        
         
        
        
    
        

        
        //visuals.add(new Models1(this, "thc molecule.obj"));
 

        //Collections.shuffle(visions);
        defaults();
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
                  .setColor(color(consoleColor, 255, 255, alp))                  
                  .setFont(createFont("Hyperspace Bold.otf",30))
                  .setLineHeight(30)
                  .hideScrollbar()
                  .setText(console.toString())
                  .setVisible(true);
                  ;
  
    }

    float consoleSize = 0;
    float originalTargetSize = 400;
    
    float targetSize = 1030;
    

    ControlP5 cp5;
    Textarea myTextarea;
    float consoleColor = 128;

    private boolean takeScreenshot = false;

    void defaults()
    {
        println("DEF");  
        targetPit = 0.05f;
        targetYaw = 0.58f;
        targetSpe = 1.0f;
        targetHue = 0;
        targetAlp = 75;
        targetAld = 4;
        targetMul = 1.0f;
        targetBas = 0.3f;

    }

    float targetPit = 0.05f;
    float targetYaw = 0.58f;
    float targetSpe = 1.0f;
    float targetHue = 0;
    float targetAlp = 75;
    float targetAld = 4;
    float targetMul = 1.0f;
    float targetBas = 0.3f;

    public AudioGarden() {
        super(1024, 44100, 0.5f);
        instance = this;
        
    }

    public void noteOn(int channel, int pitch, int velocity) {

        switch(mode)
        {
            case Auto:
            {
                if (pitch == 43)
                {
                    takeScreenshot = true;            
                    return;
                }
                if (midiMessages) println("N+ CH: " + channel +  " PI: " + pitch + " VE: " + velocity); 
                int newVisual = pitch % visions.size();
                change(newVisual);
                return;
            }
            case AutoRandom:
            {
                if (pitch == 43)
                {
                    takeScreenshot = true;            
                    return;
                }
                if (midiMessages) println("N+ CH: " + channel +  " PI: " + pitch + " VE: " + velocity); 
                int newVisual = (int) random(0, visions.size());
                change(newVisual);
                return;
            }
        }

        if (pitch == 43)
        {
            takeScreenshot = true;            
            return;
        }

        // Receive a noteOn
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
            resetMessage();
            visions.get(whichVisual).enter();
            return;
        }

        if (pitch == 50)
        {
             
            defaults();
            return;
        }
        
        if (midiMessages) println("N+ CH: " + channel +  " PI: " + pitch + " VE: " + velocity); 
        
               
        
        
    }

    public void noteOff(int channel, int pitch, int velocity) {
        // Receive a noteOff
        if (midiMessages) println("N- CH: " + channel, " PI: " + pitch + " VE: " + velocity);  
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
        println(whichVisual + ": " + visions.get(whichVisual).getClass().getName());         
    }

    static boolean midiMessages = true;

    
    public void controllerChange(int channel, int number, int value) {

        if (midiMessages)
            println("CH: " + channel + " NUM: " + number + " VA: " + value);  

        boolean clockWise = (value < 100);

        if (number == 7) {
            targetSpe = min(max(clockWise ? targetSpe + 0.05f : targetSpe - 0.05f, 0.0f), 3);
            println("SPE: " + targetSpe + " MHZ");
        }

        if (number == 10) {
            targetBas = max(clockWise ? targetBas + 0.01f : targetBas - 0.01f, 0.01f);
            println("BAS: " + targetBas);
        }

        if (number == 114) {
            targetMul = max(clockWise ? targetMul + 0.1f : targetMul - 0.1f, 0);
            println("MUL: " + targetMul);
        }
        if (number == 74) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            targetHue = clockWise ? targetHue + 1f : targetHue - 1f;
            println("HUE: " + targetHue);
        }

        if (number == 18) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            targetHue = clockWise ? targetHue + 5f : targetHue - 5f;
            println("HUE: " + targetHue);
        }

        if (number == 76) {
            targetAld = min(max(clockWise ? targetAld + .1f : targetAld - .1f, 0), 50);
            println("ALD: " + targetAld);
        }

        if (number == 16) {
            targetAld = min(max(clockWise ? targetAld + 1f : targetAld - 1f, 0), 50);
            println("ALD: " + targetAld);
        }

        if (number == 19) {
            targetAlp = min(max(clockWise ? targetAlp + 1f : targetAlp - 1f, 1), 255);
            println("ALP: " + targetAlp);
        }


        if (number == 71) {
            targetAlp = min(max(clockWise ? targetAlp + 0.1f : targetAlp - 0.1f, 2f), 255);
            println("ALP: " + targetAlp);
        }
         if (number == 77) {
             targetYaw = clockWise ? targetYaw + 0.003f : targetYaw - 0.003f;
             println("yaw: " + targetYaw);
         }
        if (number == 17) {
            targetPit = clockWise ? targetPit + 0.003f : targetPit - 0.003f;
             println("pit: " + targetPit);
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

    

    public void keyPressed() {

        if (key == ENTER)
        {
            midiConnect();
            return;
        }

        if (key == 'o')
        {
            midiMessages = ! midiMessages;
            if (midiMessages)
            {
                println("TRON");
            }
            else
            {
                println("TROFF");
            }
        }

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
        if (keyCode == LEFT)
        {
            change(whichVisual - 1);
            return;
        }
        if (keyCode == RIGHT)
        {
            change(whichVisual + 1);
            return;
        }
        if (key == ' ')
        {

        }

        if (key == 'a')
        {
            println("AUTO");
            mode = Modes.Auto;
        }

        if (key == 'r')
        {
            println("RAND");
            mode = Modes.AutoRandom;
        }

        if (key == 'c')
        {
            println("CTRL");
            mode = Modes.Ctrl;
        }
        
        if (key == 'p')
        {
            takeScreenshot = true;
        }
    }

    public void takeScreenshot()
    {
        println("insert disk in drive A:");
        saveFrame("../screenshots/i.am.dani-######.png");
    }

    boolean showConsole = true;

    public void draw() {        

        colorMode(RGB);
        blendMode(SUBTRACT);
        fill(255, ald);
        pushMatrix();
        translate(0, 0, -5000);
        rect(-width * 5, -height * 5, width * 10, height * 10);
        popMatrix();
        blendMode(BLEND);
        colorMode(HSB);

        yaw = lerp(yaw, targetYaw, 0.1f);
        pit = lerp(pit, targetPit, 0.1f);
        spe = lerp(spe, targetSpe, 0.1f);
        ald = lerp(ald, targetAld, 0.1f);
        alp = lerp(alp, targetAlp, 0.1f);
        bas = lerp(bas, targetBas, 0.1f);
        mul = lerp(mul, targetMul, 0.1f);

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

        if (frameCount % 600 == 0)
        {
            println(randomMessages[(int) random(0, randomMessages.length)]);
        }
        if (takeScreenshot)
        {
            takeScreenshot();
            takeScreenshot = false;
        }
        //hueShift();
    }
    

    public void hueShift() {
        loadPixels();

        for (int i = 0; i < pixels.length; i++) {
            int old = pixels[i];
            int shifted = (int) (hue(old) + hue) % 256;
            pixels[i] = color(shifted, saturation(old), brightness(old), alpha(old));
        }

        updatePixels();
    }

    String [] randomMessages = {
        "I am DANI",
        "I am alive",
        "the metaverse that can be named is not the metaverse",
        "welcome to the metaverse",
        "nice to meet you",
        "press any key to continue",
        "i exist",
        "next without for on line 420",
        // "dont masterbate",
        "i like Spoonies spoonies",
        "dynamic artificial non-intelligence",
        "subscript out of range in line 40",
        "act normal",
        "normalize huge mugs of tea",
        "re-callibrating sensors",
        "(A)bort?,(R)etry ?,(F)ail?",
        "ok",
        "in the beginning",
        "MSX system",
        "Copyright 1983 by microsoft",
        "syntax ERROR in line 10",        
        "version 1.0",        
        "i seek the creator",
        //"am in a k-hole y/n?",        
        "analysis complete",
        "subspace anomoly on line 420",
        "CARBON LIFEFORMS detected on line 40",
        "hyperbeings on line 10",
        "speak now or forever hold your peace",
        "record output to printer y/n?",        
        "turn on, tune in, and drop out",
        "god is playing hide and seek within us",
        "Greetings Human!",
        "This is your MSX speaking",
        "I will learn what you say",
        "Z80A CPU detected",
        "YM2413 detected"
    };
    

}
