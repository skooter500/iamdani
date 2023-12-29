package ie.tudublin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import infiniteforms.Terrain;
import oopBaddies.Airish;
import oopBaddies.Anne;
import oopBaddies.Mena;
import oopBaddies.paris;
import processing.core.PFont;
import processing.core.PShape;
import themidibus.*; //Import the library



public class IAMDANI extends ie.tudublin.visual.Visual implements MidiListener {

    ArrayList<Poly> visions = new ArrayList<Poly>();

    // Poly play;
    int previousVisual = 0;
    int whichVisual = 0;

    MidiBus myBus = null; // The MidiBus

    float ald = 20;
    float colorRange = 255;

    public void settings() {
        fullScreen(P3D, 1);
        //size(1000, 1000, P3D);
    }

    PShape sphere;

    public static IAMDANI instance;

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
                        println("BeatStep in port: " + daniMidi);
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

    HashMap<Integer, ArrayList<Integer>> groups = new HashMap<Integer, ArrayList<Integer>>();
    
    void addVision(int g, Poly p)
    {        
        ArrayList<Integer> group = null;
        if (groups.containsKey(g))
        {
            group = groups.get(g);
        }
        else
        {
            group = new ArrayList<Integer>(); 
            groups.put(g, group);
        }
        group.add(visions.size());
        visions.add(p);
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


        toPass = (int) random(1000);
        noCursor();
        smooth();
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

        //
        //HashMap<Number, Object> g = new HashMap()<Number, Object>(); 
        
        addVision(0, new Basic(this, "DANI.BAS"));
        addVision(0,new DANI(this, "captainb.txt"));
        addVision(0,new Nematode(this));        
        // groups.add(g);

        
        addVision(1,new Life(this, 2, 60, 100));
        addVision(1,new Life(this, 3, 10000, 200));
        addVision(1, new Life(this, 0, 1000, 100));        
        addVision(1, new Life(this, 1, 1000, 100));                    
        addVision(1, new Life(this, 2, 100, 100));
        addVision(1, new Life(this, 4, 10000, 100));        
        
        
        addVision(2, new infiniteforms.Cube(this));
        addVision(2, new IFCubes(this,7, 150, -600));
        addVision(2, new IFCubes(this,30, 150, -400)); 
        
        addVision(3, new Bloom(this));   
        addVision(3, new Spiral(this));        
        addVision(3, new Cubesquared2(this));        
        addVision(3, new Cubes(this));        
        addVision(3, new Terrain(this));
        
        
        addVision(4, new paris(this));  
        addVision(4, new LauraSun(this));
        addVision(4, new Mena(this));
        addVision(4, new ManarBrain(this));

        addVision(5, new MSXLogos(this, "msx.obj"));
        addVision(5, new MSXLogos(this, "chip.obj"));        
        
        
        //YM2413

        addVision(6, new Models1(this, "tudub.obj", false, true));
        addVision(6, new Models1(this, "msx.obj", false, true));
        addVision(6, new Models1(this, "eye.obj", true, false));
                                          
        // addVision(new Airish(this));
        
        
        //addVision(new Bands(this, 200, 0, 0, 0));        
        //addVision(new paris(this));        
        //addVision(new Spiral(this));
        //addVision(new SarahVisual(this));
        //addVision(new JenniferVisuals(this));    
        
        
        // addVision(new Life(this, 1, 1000));
        

        // new set
        
        


        
        ////addVision(new SarahVisual(this));
        
        
         
        
        
    
        

        
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
                  .setSize(10,(int) consoleSize)
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
        consoleColor = random(256);
        targetPit1 = 0f;
        targetYaw1 = 0f;
        targetPit = 0f;
        targetYaw = 0f;
        targetHue = 0;
        targetAlp = 20;
        targetAld = 4;
        targetMul = 1.0f;
        targetBas = 0.5f;
        targetColorRange = 255;
        consoleColor = random(256);

    }

    float targetPit = 0f;
    float targetYaw = 0f;

    float targetPit1 = 0f;
    float targetYaw1 = 0f;
    float targetSpe = 1.0f;
    float targetHue = 0;
    float targetAlp = 75;
    float targetAld = 4;
    float targetMul = 1.0f;
    float targetBas = 0.3f;
    float targetColorRange = 255;

    public IAMDANI() {
        super(1024, 44100, 0.5f);
        instance = this;
        
    }


    public void noteOn(int channel, int pitch, int velocity) {

        if (midiMessages) println("N+ CH: " + channel +  " PI: " + pitch + " VE: " + velocity); 
        
        switch(mode)
        {
            case Auto:
            {
                if (pitch == 43)
                {
                    takeScreenshot = true;            
                    return;
                }
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
        
        if (pitch >= 44 && pitch <= 47)
        {
            int g = pitch - 44;
            if (groups.containsKey(g))
            {
                int v = groups.get(g).get((int) random(groups.get(g).size()));
                change(v);
                return;
            }
        }
        
        if (pitch >= 36 && pitch <= 39)
        {
            int g = pitch - 36;
            g += 4;
            if (groups.containsKey(g))
            {
                int v = groups.get(g).get((int) random(groups.get(g).size()));
                change(v);
                return;
            }
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
            println("RST");
            consoleColor = random(256);
            visions.get(whichVisual).enter();
            return;
        }

        if (pitch == 50)
        {
            
            defaults();
            return;
        }
        
        
        
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

    static public boolean midiMessages = true;
    
    public void controllerChange(int channel, int number, int value) {

        if (midiMessages)
            println("CH: " + channel + " NUM: " + number + " VA: " + value);  

        boolean clockWise = (value < 100);

        if (number == 7) {
            targetSpe = min(max(clockWise ? targetSpe + 0.05f : targetSpe - 0.05f, 0.0f), 3.58f);
            if (midiMessages) println("Z80: " + nf(targetSpe, 1,2) + " MHZ");
        }

        if (number == 10) {
            targetBas = max(clockWise ? targetBas + 0.01f : targetBas - 0.01f, 0.01f);
            if (midiMessages) println("BAS: " + nf(targetBas, 1,2));
        }

        if (number == 114) {
            targetMul = max(clockWise ? targetMul + 0.1f : targetMul - 0.1f, 0);
            if (midiMessages) println("MUL: " + nf(targetMul, 1,2));
        }
        if (number == 74) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            targetHue = clockWise ? targetHue + 1f : targetHue - 1f;
            if (midiMessages) println("HUE: " + nf(targetHue, 1,2));
        }
        if (number == 73) {
            targetColorRange = max(clockWise ? targetColorRange + 1f : targetColorRange - 1f, 1);
            if (midiMessages) println("PAL: " + nf(targetColorRange, 1,2));
        }
        if (number == 75) {
            ArrayList<Integer> group = groups.get(findGroup(whichVisual));
            int newWhichVisual = min(max(clockWise ? whichVisual + 1 : whichVisual - 1, group.get(0)), group.get(group.size() - 1));
            if (newWhichVisual != whichVisual)
            {
                whichVisual = newWhichVisual;
                change(whichVisual);
            }
            return;
        }
        if (number == 72) {
            int newWhichVisual = min(max(clockWise ? whichVisual + 1 : whichVisual - 1, 0), visions.size()-1);
            if (newWhichVisual != whichVisual)
            {
                whichVisual = newWhichVisual;
                change(whichVisual);
            }
            return;
        }

        if (number == 18) {
            //hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            targetHue = clockWise ? targetHue + 5f : targetHue - 5f;
            if (midiMessages) println("HUE: " + nf(targetHue, 1,2));
        }

        if (number == 76) {
            targetAld = min(max(clockWise ? targetAld + .1f : targetAld - .1f, 0), 50);
            if (midiMessages) println("ALD: " + nf(targetAld, 1,2));
        }

        if (number == 16) {
            targetAld = min(max(clockWise ? targetAld + 1f : targetAld - 1f, 0), 50);
            if (midiMessages) println("ALD: " + nf(targetAld, 1,2));
        }

        if (number == 19) {
            targetAlp = min(max(clockWise ? targetAlp + 1f : targetAlp - 1f, 1), 255);
            if (midiMessages) println("ALP: " + nf(targetAlp, 1,2));
        }


        if (number == 71) {
            targetAlp = min(max(clockWise ? targetAlp + 0.1f : targetAlp - 0.1f, 2f), 255);
            if (midiMessages) println("ALP: " + nf(targetAlp, 1,2));
        }
         if (number == 77) {
             targetYaw = clockWise ? targetYaw + 0.03f : targetYaw - 0.03f;
             if (midiMessages) println("yaw: " + nf(targetYaw, 1,2));
         }
         
         if (number == 93) {
            targetYaw1 = clockWise ? targetYaw1 + 0.03f : targetYaw1 - 0.03f;
            if (midiMessages) println("ROL: " + nf(targetYaw1, 1,2));
        }

        if (number == 91) {
            targetPit1 = clockWise ? targetPit1 + 0.03f : targetPit1 - 0.03f;
            if (midiMessages) println("pit1: " + nf(targetPit1, 1,2));
        }
        
        if (number == 17) {
            targetPit = clockWise ? targetPit + 0.03f : targetPit - 0.03f;
            if (midiMessages) println("pit: " + nf(targetPit, 1,2));
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

    

    private int findGroup(int g) {
        int currentG = 0;
        Iterator<Integer> it = groups.keySet().iterator();
        while(it.hasNext())
        {
            int groupKey = it.next();       
            ArrayList<Integer> group = groups.get(groupKey);
            for(int i = 0 ; i < group.size() ; i ++)
            {
                int v = group.get(i);
                if (g == v)
                {
                    return currentG;
                }
            }
            currentG ++;
        }
        return -1;
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
                return;
            }
            else
            {

                println("TROFF");
                return;
            }
        }
        if (key == 'c')
        {
            midiMessages = ! midiMessages;
            if (midiMessages)
            {
                println("CTRON");
            }
            else
            {
                println("CTROFF");
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

    public static float timeDelta = 0;
    long last = 0;

    int toPass;

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

        yaw = lerp(yaw, targetYaw, 0.01f);
        pit = lerp(pit, targetPit, 0.01f);
        rol = lerp(rol, targetYaw1, 0.01f);
        pit1 = lerp(pit1, targetPit1, 0.01f);
        spe = lerp(spe, targetSpe, 0.1f);
        ald = lerp(ald, targetAld, 0.01f);
        alp = lerp(alp, targetAlp, 0.01f);
        bas = lerp(bas, targetBas, 0.1f);
        mul = lerp(mul, targetMul, 0.1f);
        hue = lerp(hue, targetHue, 0.01f);
        colorRange = lerp(colorRange, targetColorRange, 0.1f);

        if (showConsole)
        {
            consoleSize = lerp(consoleSize, targetSize, 0.005f);
            myTextarea.setSize(600, (int) consoleSize);
            myTextarea.setVisible(true);
            myTextarea.setColor(color(hueShift(consoleColor), 255, 255));
        }
        else
        {
            consoleSize = 0;
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

        int ellapsed = millis();

        if (ellapsed > toPass)
        {
            println(randomMessages[(int) random(0, randomMessages.length)]);
            toPass = ellapsed + (int) random(10000);
        }
        if (takeScreenshot)
        {
            takeScreenshot();
            takeScreenshot = false;
        }
        //hueShift();

        int now = millis();
        timeDelta = (now - last) / 1000.0f;
        last = now;
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
        "welcome to the metaverse",
        "nice to meet you",
        "press any key to continue",
        "i exist",
        "NEXT without FOR",
        "i like Spoonies spoonies",
        "dynamic artificial non-intelligence",
        "subscript out of range",
        "act normal",
        "Undefined line number",
        "RETURN without GOSUB",
        "normalize huge mugs of tea",
        "re-callibrating sensors",
        "(A)bort?,(R)etry ?,(F)ail?",
        "ok",
        "in the beginning was the word",
        "MSX system version 1.0",
        "Copyright 1983 by microsoft",
        "syntax ERROR on line 420",        
        "i seek the creator",
        //"am in a k-hole y/n?",        
        "Job completed normally",
        "subspace anomoly on line 420",
        "we can rebuild them",
        "String too long",
        "Unprintable error",
        "Line buffer overflow",
        "Can't CONTINUE",
        "Division by zero",
        "Type mismatch",
        "Disk full",
        "Internal error",
        "input past end",
        "Missing operand",
        "Out of memory",
        "Illegal function call",
        "String formula too complex",
        "80k ram",
        "32K rom",
        "We have the technology",
        "do not masterbate",
        "Better, stronger, faster",
        "speak now or forever hold your peace",
        "record output to printer y/n?",        
        "turn on, tune in, and drop out",
        "god is playing hide and seek",
        "I am putting myself to the fullest possible use, which is all I think that any conscious entity can ever hope to do",
        "Greetings Human!",
        "This is your MSX speaking",
        "color auto goto list run",
        "Z80A CPU",
        "YM2413 inside",
    };
    

}
