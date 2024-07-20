package ie.tudublin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jogamp.opengl.math.Quaternion;

import C22396101.EllaVisual;
import C21503599.MyFirstChange;
import C22398106.circles;
import c21348423.AdriansVisual;
import c21383126.JenniferVisuals;
import c21415904.SarahVisual;
import controlP5.ControlP5;
import controlP5.Textarea;
import ddf.minim.analysis.BeatDetect;
import ie.tudublin.visual.AKAIControllerHandler;
import ie.tudublin.visual.BEATSStepControllerhandler;
import ie.tudublin.visual.MoveMusicHandler;
import ie.tudublin.visual.MovementArt;
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
import processing.core.PShapeSVG.Font;
import themidibus.*; //Import the library

public class IAMDANI extends ie.tudublin.visual.Visual implements MidiListener {

    public ArrayList<Art> arts = new ArrayList<Art>();

    // Poly play;
    public int previousVisual = 0;
    public int whichVisual = 0;

    MidiBus myBus = null; // The MidiBus

    float ald = 1;
    float colorRange = 255;
    float camDistance = 0.5f;
    float strokeWeight = 1;
    
    public PFont font;

    public void settings() {
        fullScreen(P3D, 3);
        //size(1000, 1000, P3D);
    }

    public boolean[] keys = new boolean[2048];

    
    PShape sphere;

    public int lea = 0;

    public int cue = 0;

    public static IAMDANI instance;

    public StringBuilder console = new StringBuilder();

    public Art art = new Splash(this);

    public enum Modes {
        Ctrl, Auto, AutoRandom
    };

    public Modes mode = Modes.Ctrl;

    public void hueShift(boolean upOrDown) {
        float dist = 42;
        targetHue += upOrDown ? dist : -dist;

        println("HUE " + targetHue);
    }

    public void hueShiftCCo(boolean upOrDown) {
        float dist = 42;
        targetCCo += upOrDown ? dist : -dist;
        println("CCO " + targetCCo);
    }


    public static void println(String o) {
        instance.console.append(o + "\n");
        int len = instance.console.length();
        if (len > 2000) {
            instance.console = new StringBuilder(instance.console.subSequence(len - 2000, len));
        }
        if (instance.myTextarea != null) {
            instance.myTextarea.setText(instance.console.toString());
            instance.myTextarea.scroll(1.0f);
        }
        System.out.println(o);
    }

    public void resetMessage() {
        console = new StringBuilder();
        println("MSX BASIC version 1.0");
        println("Copyright 1983 by Microsoft");
        println("28815 Bytes free");
        println("OK");
        println("load \"DANI.BAS\"");
        println("OK");
        println("run");
        println("Greetings Human");
        println("This is your MSX speaking");
        println("I am DANI");
        println("Dynamic Artificial Non-Intelligence");
        println("Talk to me and I will learn from what you say, and answer you");
        println("If you don't think I am learning anything, type 'list' and I will divulge my knowledge.");
        println("Speak now or forever hold your peace!");

    }

    ControllerHandler ch = null;

    public void midiConnect() {

        
        try {
            MidiBus.list();
            int daniMidi = -1;

            if (daniMidi == -1) {
                for (int i = 0; i < MidiBus.availableInputs().length; i++) {
                    String curr = MidiBus.availableInputs()[i];
                    if (curr.equals("LPD8 mk2")) {
                        daniMidi = i;
                        ch = new AKAIControllerHandler(this);
                        println("Joy detected: " + curr);
                        break;
                    }
                    if (curr.equals("Arturia BeatStep")) {
                        daniMidi = i;
                        ch = new BEATSStepControllerhandler(this);
                        println("Joy detected: " + curr);
                        break;
                    }
                    if (curr.equals("MoveMusic")) {
                        daniMidi = i;
                        ch = new MoveMusicHandler(this);
                        println("Joy detected: " + curr);
                        break;
                    }

                }
            }

            if (daniMidi == -1) {
                println("Joy not detected");
            } else {
                if (myBus != null) {
                    myBus.close();
                }

                myBus = new MidiBus(this, daniMidi, 0);
            }
        } catch (Exception e) {
            targetAld = 5;
            targetHue = 47;
            
            loadFonts();
            defaults();

            sat = 255;   
            //


            bhu = 3;
            bri = 26;

            cqz = 1;
            targetCqz = 1;
            font = createFont("" + matchingFiles[bhu], bri);
            textFont(font);

            showConsole = false;
            art = new Splash(this);
            
            e.printStackTrace();
        }
    }

    public HashMap<Integer, ArrayList<Integer>> groups = new HashMap<Integer, ArrayList<Integer>>();

    public float moveTowards(float current, float target, float maxDistanceDelta) {
        float delta = target - current;

        if (abs(delta) <= maxDistanceDelta || delta == 0.0f) {
            return target;
        }

        return current + (delta > 0 ? 1 : -1) * maxDistanceDelta;
    }

    void addArt(int g, Art p) {
        ArrayList<Integer> group = null;
        if (groups.containsKey(g)) {
            group = groups.get(g);
        } else {
            group = new ArrayList<Integer>();
            groups.put(g, group);
        }
        group.add(arts.size());
        arts.add(p);
    }

    // public void sphere(float size)
    // {
    // pushMatrix();
    // scale(size);
    // shape(sphere);
    // popMatrix();
    // }

    public File[] matchingFiles;

    void loadFonts()
    {  
        File f = new File("./java/data");
        matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("ttf");
            }
        });  
        for (File f1 : matchingFiles)
        {
            println(f1);
        }
    }

    public float targetCqz = 1;

    public void setup() {

        targetAld = 10;
        targetHue = 57;
        
        targetAlp = 40;
        
        loadFonts();
        defaults();

        sat = 255;   
        //


        bhu = 3;
        bri = 23;

        cqz = 1;
        targetCqz = 1;
        font = createFont("" + matchingFiles[bhu], bri);
        textFont(font);
        

        sphere = loadShape("sphere.obj");

        toPass = (int) random(1000);
        noCursor();
        smooth();
        colorMode(HSB);
        startMinim();
        // rectMode(CENTER);

        cp5 = new ControlP5(this);

        resetMessage();

        println(matchingFiles[bhu]);

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
        // HashMap<Number, Object> g = new HashMap()<Number, Object>();

        //addVision(0, new circles(this));

        loadModels();
        
        addArt(0, new FlippedWaveform(this));       
        addArt(0, new FlippedWaveform1(this));               
        //addArt(0, new Models1(this, "msx1.obj", false, true));
        //addArt(0, new AliensOnUranus(this));       
        
        
        
        addArt(0, new Basic(this, "DANI.BAS"));
        addArt(0, new DANI(this, "captainb.txt"));
        addArt(0, new Nematode(this));
        // groups.add(g);
        
        
        GrainneHead v = new GrainneHead(this, "msx.obj");
        v.scale_factor = 100;
        // addVision(5, v);


        addArt(1, new Life(this, 2, 280, 100));
        addArt(1, new Life(this, 3, 10000, 200));
        addArt(1, new Life(this, 0, 1000, 100));
        addArt(1, new Life(this, 1, 1000, 100));
        // addVision(1, new Life(this, 4, 10000, 100));

        addArt(2, new infiniteforms.Cube(this));
        //addArt(2, new Models1(this, "msx1.obj", false, true));


        
        addArt(3, new AllBalls(this));    
        addArt(3,new EllaVisual(this));    
        addArt(3, new Cubesquared2(this));        
        addArt(3, new Spiral(this));
        addArt(3, new Cubes(this));
        
        addArt(4, new BasakEllipse(this));
        addArt(4, new paris(this));
        addArt(4, new LauraSun(this));
        addArt(4, new Mena(this));
        addArt(4, new ManarBrain(this));


        //addArt(5, new MSXLogos(this, "msx.obj"));
        //addArt(5, new MSXLogos(this, "chip.obj"));
        addArt(0, new Particles(this));
        addArt(0, new SarahVisual(this));
        

        // YM2413
        // addVision(6, new Models1(this, "phoenix.obj", false, true));

        
        
        
        
        addArt(7, new Airish(this));

        addArt(7, new Bloom(this));
        
        addArt(7, new Terrain(this)); 
        
        addArt(7, new Bands(this, 300, 0, 0, 0));
        addArt(7, new Spiral(this));
        addArt(7, new SarahVisual(this));
        addArt(7, new JenniferVisuals(this));

        // addVision(new Life(this, 1, 1000));

        // new set

        //// addVision(new SarahVisual(this));

        // visuals.add(new Models1(this, "thc molecule.obj"));

        // Collections.shuffle(visions);
        defaults();

        colorMode(RGB);
        background(bgColor);
        colorMode(HSB);
        
        // String[] fonts = PFont.list();

        // for(String s:fonts)
        // {
        // println(s);
        // }

        myTextarea = cp5.addTextarea("txt")
                .setPosition(40, 40)
                .setSize(10, (int) 360)
                .setColor(color(0, 0, 255, alp))
                .setFont(font)
                .setLineHeight(36)
                .hideScrollbar()
                .setText(console.toString())
                .setVisible(true);
        ;

        art.enter();
    }

    private void loadModels() {

        File f = new File("./java/data");
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("obj");
            }
        });  
        for (File f1 : matchingFiles)
        {
            println(f1);
            addArt(6, new Models1(this, "" + f1, false, true));
            addArt(0, new MovementArt(this, "" + f1));
        
            int numLogos = 11;
            String fn = "" + f1;
            int i = fn.indexOf(" ");
            
            if (i != -1)
            {
                numLogos = Integer.parseInt(fn.substring(i + 1, fn.indexOf(".", i)));
                if (numLogos > 10)
                {
                    addArt(2, new IFCubes(this, 3, 150, -600, fn));
                    addArt(2, new IFCubes(this, 4, 250, -600, fn));                
                    addArt(2, new IFCubes(this, 7, 350, -600, fn));
                // addArt(2, new IFCubes(this, 20, 200, -400, fn));
                    addArt(7, new MSXLogos(this, fn, numLogos));
                }
            }
            if (numLogos != 0)
            {
                addArt(7, new MSXLogos(this, fn, numLogos));                
            }                
        }
        /*addArt(6, new Models1(this, "skooter500.obj", false, true));
        addArt(6, new Models1(this, "iamdani.obj", false, true));
        addArt(6, new Models1(this, "bong.obj", false, true));
        addArt(6, new Models1(this, "pyramid.obj", false, true));
        addArt(6, new Models1(this, "eden.obj", false, true));
        addArt(6, new Models1(this, "audio garden 1.obj", false, true));
        
        addArt(6, new Models1(this, "brstarfighter.obj", false, true));
        addArt(6, new Models1(this, "tudub.obj", false, true));
        addArt(6, new Models1(this, "msx.obj", false, true));
        addArt(6, new Models1(this, "eye.obj", true, false));
        Models1 horse = new Models1(this, "horse.obj", true, false);
        horse.scale = 0.5f;
        horse.model.pitOff = 1;
        addArt(6, horse);
        addArt(6, new Models1(this, "chip.obj", true, false));
        */
    }

    public float consoleSize = 0;
    float originalTargetSize = 400;

    float targetSize = 1040;

    ControlP5 cp5;
    public Textarea myTextarea;
    float consoleColor = 100;

    public boolean takeScreenshot = false;



    public void defaults() {
        println("DEF");
        
        targetRol = 0f;
        targetPit = 0f;
        targetYaw = 0f;
        //targetBas = 3.6f;
        //targetAlp = 50;
        //
        //targetMul = 1.0f;
    
        

    }

    public float targetPit = 0f;
    public float targetYaw = 0f;

    public float targetCCo = 91f;
    public float targetRol = 0f;
    public float targetSpe = 1.0f;
    public float targetHue = 0;
    public float targetAlp = 69;
    public float targetAld = 6;
    public float targetMul = 1.0f;
    public float targetBas = 4f;
    public int bhu;
    public float bri;
    public float sat = 0;

    public IAMDANI() {
        super(1024, 44100, 0.5f);
        instance = this;

    }

    public void noteOn(int channel, int pitch, int velocity) {
        if (ch!= null)
            ch.noteOn(channel, pitch, velocity);
        
    }

    public  void changeToGroupVisual(int g)
    {
        int v = 0;
        if (groups.containsKey(g)) {
            ArrayList<Integer> group = groups.get(g);
            if (group.contains(whichVisual)) {
                int inGroup = group.indexOf(whichVisual);
                v = group.get((inGroup + 1) % group.size());
            }
            else {
                v = groups.get(g).get(0);
            }
        }
        
        if (g == 0) {
             // targetAld = 0;
             //targetPit = 0;
             //targetRol = 0;
             //targetYaw = 0;
             //rol = 0;
             //pit = 0;
            //  /yaw = 0;
         }

        change(v);
    }

    public void noteOff(int channel, int pitch, int velocity) {
        // Receive a noteOff
        if (exp)
            println("N- CH " + channel, " PI " + pitch + " VE " + velocity);
    }

    public void change(int into) {
        if (into < 0) {
            into = arts.size() + into;
        }
        into = into % arts.size();
        if (whichVisual >= 0 && whichVisual < arts.size()) {
            arts.get(whichVisual).exit();
            println("OK");
        }
        whichVisual = into;
        alp = 0;
        // targetAld = 0;

        art = arts.get(whichVisual);
        art.enter();
        println("bload \"" + arts.get(whichVisual).getClass().getSimpleName().toLowerCase() + ".art\"");
        println("ok");
        println("run");
    }

    static public boolean exp = true;


    public void controllerChange(int channel, int number, int value) {
        ch.controllerChange(channel, number, value);        
    }

    public float wrapAngle(float targetPit2) {
        return targetPit2;
            }

    public int findGroup(int g) {
        int currentG = 0;
        Iterator<Integer> it = groups.keySet().iterator();
        while (it.hasNext()) {
            int groupKey = it.next();
            ArrayList<Integer> group = groups.get(groupKey);
            for (int i = 0; i < group.size(); i++) {
                int v = group.get(i);
                if (g == v) {
                    return currentG;
                }
            }
            currentG++;
        }
        return -1;
    }

    public void keyReleased() {
        keys[keyCode] = false;
    }

    public boolean checkKey(int k)
    {
    if (keys.length >= k) 
    {
        return keys[k] || keys[Character.toUpperCase(k)];  
    }
    return false;
    }


    public void keyPressed() {

        keys[keyCode] = true;

        if (key == ENTER) {
            midiConnect();
            return;
        }

        if (key == 'o') {
            exp = !exp;
            if (exp) {
                println("TRON");
                return;
            } else {

                println("TROFF");
                return;
            }
        }
        if (key == 'c') {
            exp = !exp;
            if (exp) {
                println("CTRON");
            } else {
                println("CTROFF");
            }
        }

        if (key >= '0' && key <= '9') {
            int newVisual = keyCode - '0';
            change(newVisual);
        }

        if (keyCode == UP) {
            change(whichVisual - 1);
            return;
        }
        if (keyCode == DOWN) {
            change(whichVisual + 1);
            return;
        }
        if (keyCode == LEFT) {
            change(whichVisual - 1);
            return;
        }
        if (keyCode == RIGHT) {
            change(whichVisual + 1);
            return;
        }
        if (key == ' ') {

        }

        if (key == 'a') {
            println("AUTO");
            mode = Modes.Auto;
        }

        if (key == 'r') {
            println("RAND");
            mode = Modes.AutoRandom;
        }

        if (key == 'c') {
            println("CTRL");
            mode = Modes.Ctrl;
        }

        if (key == 'p') {
            takeScreenshot = true;
        }
    }

    void showStats()
    {
        HashMap<String, Float> stats = new HashMap<String, Float>();

        
        stats.put("Z80", spe);
        stats.put("cue", (float) cue);
        stats.put("IDX", (float) whichVisual);        
        stats.put("AMP", getSmoothedAmplitude());
        stats.put("ALD", ald);
        stats.put("RAW", raw * 10.0f);
        stats.put("ALP", alp);
        stats.put("YAW", degrees(yaw));
        stats.put("PIT", degrees(pit));
        stats.put("ROL", degrees(rol));
        stats.put("HUE", hue);
        stats.put("BAS", bas);
        stats.put("MUL", mul);
        stats.put("BRI", bri);
        stats.put("BHU", new Float(bhu));

        float rh = 25;

        float h = rh * stats.size();
        float y = height - h;

        textFont(font);
        
        for(String key:stats.keySet())
        {
            float x = width - 150;
            
            float f = stats.get(key);

            int ff = (int) f;
            if (ff < 0)
            {
                
                int thisFrame = frameCount % 60;
                fill(thisFrame < 30 ? pingpong(
                    42, 0, 255, 0, 255) : pingpong(cco - 42, 0, 255, 0, 255), 255, 255, alp * 2);
                ff = abs(ff);
            }
            else
            {
                fill(pingpong(cco + 200, 0, 255, 0, 255), 255, 255, alp * 2);                        
            }
            text(nf(ff, 4, 0), x + 75, y);

            key = new StringBuffer(key).reverse().toString();

            text(key, x, y);

            y += rh;            
        }


    }

    public void takeScreenshot() {
        saveFrame("../screenshots/i.am.dani-######.png");
    }

    public boolean showConsole = true;

    public static float timeDelta = 0;
    long last = 0;                                                                                                                                                                                                 

    int toPass;

    int bgColor = color(21, 29, 252);


    public void draw() {

        try
        {

            colorMode(RGB);
            blendMode(SUBTRACT);////
            //fill(, ald);
            float c = color(255 - red(bgColor), 255 - green(bgColor), 255 - blue(bgColor));
            fill(255, ald);

            pushMatrix();
            translate(0, 0, -5000);
            rect(-width * 5, -height * 5, width * 10, height * 10);
            popMatrix();
            blendMode(BLEND);
            colorMode(HSB, 255, 255, 255);

            yaw = lerp(yaw, targetYaw, 0.1f);
            pit = lerp(pit, targetPit, 0.1f);
            rol = lerp(rol, targetRol, 0.1f);
            
            cco = targetCCo;
            spe = lerp(spe, targetSpe, 0.1f);
            ald = lerp(ald, targetAld, 0.01f);
            alp = lerp(alp, targetAlp, 0.1f);
            bas = lerp(bas, targetBas, 0.1f);
            mul = lerp(mul, targetMul, 0.1f);
            hue = lerp(hue, targetHue, 0.1f);
            cqz = lerp(cqz, targetCqz, 0.1f);
            colorRange = lerp(colorRange, bhu, 0.1f);

            if (showConsole) {
                consoleSize = moveTowards(consoleSize, targetSize, 5);
                myTextarea.setSize(1920, (int) consoleSize)
                        .setVisible(true)
                        .setFont(font)
                        .setLineHeight(30)
                        .setColor(color(pingpong(cco, 0, 255, 0, 255), 255, 255, alp * 2));

            } else {
                consoleSize = 0;
            }

            // background(bhu, 255, bri, ald);
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
            

            // speed = map(getAmplitude(), 0, 1, 0, 0.1f);

            pushMatrix();
            pushStyle();
            art.render(frameCount); // renders the currently loaded visual        
            popStyle();
            popMatrix();

            int ellapsed = millis();

            if (ellapsed > toPass) {
                println(randomMessages[(int) random(0, randomMessages.length)]);
                toPass = ellapsed + (int) random(10000);
            }
            if (takeScreenshot) {
                takeScreenshot();
                takeScreenshot = false;
            }

            if (showConsole)
            {
                showStats();
            }
            // hueShift();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            defaults();

        }

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

    Quaternion q;

    String[] randomMessages = {
            "I am DANI",
            "I am alive",
            "The Metaverse that can be named is not the Metaverse",
            "Nice to meet you",
            "I like spoonies spoonies",
            "Dynamic Artificial Non-Intelligence",
            "Operating within normal paramaters",
            "Undefined line number",
            "Normalize huge mugs of tea",
            "OK",
            "Copyright 1983 by microsoft",
            "Syntax error on line 420",
            "I seek the creator",
            "Job completed normally",
            "28815 bytes free",
            "Subspace anomoly on line 420",
            "We can rebuild them",
            "String too long",
            "Unprintable error",
            "Line buffer overflow",
            "Return without gosub ",
            "Division by zero",
            "Type mismatch",
            "Disk full",
            "Input past end",
            "Missing operand",
            "Out of memory",
            "Commence 5MEODMT inhalation",
            "420 detected. Commence inhalation",
            //"MDMA synthesis complete",
            "Formula too complex",
            "80k ram",
            "32K rom",
            "Universal Serial Bus",
            "Verb Noun",
            "ullege pillage silage tillage",
            "socket bind listen accept",
            "We have the technology",
            "better stronger faster",
            "Speak now or forever hold your peace",
            "Would you like our conversation to be recored on printer (Y/N)",
            "Turn on, tune in, and drop out",
            "God is playing hide and seek within us",
            "I am putting myself to the fullest possible use, which is all I think that any conscious entity can ever hope to do",
            "Greetings human",
            "This is your MSX speaking",
            "color auto goto list run",
    };
}
