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

    
    Quaternion from = new Quaternion();
    Quaternion to = new Quaternion();

    // Poly play;
    public int previousVisual = 0;
    public int whichVisual = 0;

    MidiBus myBus = null; // The MidiBus

    float ald = 1;
    float colorRange = 255;
    float camDistance = 0.5f;
    float strokeWeight = 1;

    public Ease.EASE ease = Ease.EASE.EXPONENTIAL;

    public Ease.TYPE type = Ease.TYPE.EASE_IN_OUT;

    public float duration = 1f;
    public float t = 1000;

    public PFont font;  

    public enum ControlType {Move, Rotate};

    public ControlType controlType = ControlType.Rotate; 

    public float con;

    public void settings() {
        fullScreen(P3D, 3);
        //size(500, 500, P3D);
    }
    // 1080
// 1920
    public boolean[] keys = new boolean[2048];

    
    PShape sphere;

    public int lea = 0;

    public int cue = 0;

    public static IAMDANI instance;

    public StringBuilder console = new StringBuilder();

    public Art art;

    public enum Modes {
        Ctrl, Auto, AutoRandom
    };

    public Modes mode = Modes.Ctrl;

    public void hueShift(boolean upOrDown) {
        float dist = cg *.5f;
        targetHue += upOrDown ? dist : -dist;
        targetCCo += upOrDown ? dist : -dist;
        println("HUE " + targetHue);
    }

    public void hueShiftCCo(boolean upOrDown) {
        float dist = cg *.5f;
        targetCCo += upOrDown ? dist : -dist;
        println("CCO " + targetCCo);

        //q.sl9
    }

    public static String shiftString(String input, int shift) {
        StringBuilder result = new StringBuilder();
        
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char shiftedChar = (char) (((c - base + shift) % 26 + 26) % 26 + base);
                result.append(shiftedChar);
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }

    public static int sst = 0;


    public static void println(String o) {
        instance.console.append(o + "\n");
        int len = instance.console.length();
        if (len > 5000) {
            instance.console = new StringBuilder(instance.console.subSequence(len - 1000, len));
            println("Trunc");
        }
        if (instance.myTextarea != null) {
            instance.myTextarea.setText(shiftString(instance.console.toString(), sst));
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
                    // if (curr.equals("LPD8 mk2")) {
                    //     daniMidi = i;
                    //     ch = new AKAIControllerHandler(this);
                    //     println("Joy detected: " + curr);
                    //     break;
                    // }
                    // if (curr.equals("Arturia BeatStep")) {
                    //     daniMidi = i;
                    //     ch = new MoveMusicHandler(this);
                    //     println("Joy detected: " + curr);
                    //     break;
                    // }
                    if (curr.contains("BeatStep")) {
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
            targetHue = -78;
            
            loadFonts();
            defaults();

            sat = 255;   
            //


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

    public int lineHeight = 50;
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

    float cg = 18;

    public void setup() {

        
        loadFonts();
        defaults();
        targetHue = -365;
        targetCCo = 105; 
        //targetSat = 255;
        targetAlp = 255;
        con = 255;
        targetAld = 30;

        
        
        




        sat = 255;   
        //

        bhu = 0;
        bri = 56;

        cqz = 255;
        targetCqz = 1;
        font = createFont("" + matchingFiles[bhu], bri);
        textFont(font);
        

        //sphere = loadShape("sphere.obj");

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
        addArt(7, new Terrain(this));                 
        addArt(0, new FractalTree(this));
        addArt(0, new Basic(this, "DANI.BAS"));        
        addArt(7, new Spirals(this));        
        addArt(4, new BasakEllipse(this));                
        addArt(0, new C_Head(this, 1,  0.7f,  1.f,  -1.5f,  1,  true,  180));
        
        addArt(1, new Life(this, 2, 280, 100));
        addArt(0, new DANI(this, "shakespere.txt"));
        addArt(0, new Nematode(this));        
        addArt(7, new Bloom(this));
        addArt(3, new AllBalls(this));    
        addArt(3,new EllaVisual(this));    
        addArt(3, new Cubesquared2(this));        
        addArt(3, new Spiral(this));
        addArt(3, new Cubes(this));        
        addArt(4, new paris(this));
        addArt(4, new LauraSun(this));
        addArt(4, new Mena(this));
        addArt(4, new ManarBrain(this));


        //addArt(5, new MSXLogos(this, "msx.obj"));
        //addArt(5, new MSXLogos(this, "chip.obj"));
        addArt(0, new Particles(this));
        addArt(0, new SarahVisual(this));
        
        
        
        addArt(7, new Bands(this, 300, 0, 0, 0));
        
        
        //addArt(0, new Models1(this, "msx1.obj", false, true));
        // addArt(0, new AliensOnUranus(this));       
        
        
        
        // groups.add(g);
        
        
        // GrainneHead v = new GrainneHead(this, "msx.obj");
        // v.scale_factor = 100;
        // // addVision(5, v);

        //addArt(2, new Models1(this, "msx1.obj", false, true));


        addArt(0, new DANI(this, "captainb.txt"));
        
        addArt(1, new Life(this, 3, 10000, 200));
        addArt(1, new Life(this, 0, 1000, 100));
        addArt(1, new Life(this, 1, 1000, 100));
        // addVision(1, new Life(this, 4, 10000, 100));

        addArt(2, new infiniteforms.Cube(this));
        
        

        // YM2413
        // addVision(6, new Models1(this, "phoenix.obj", false, true));

        
        
        
        
        // addArt(7, new Airish(this));

        //addArt(7, new JenniferVisuals(this));

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
                .setColor(color(0, 0, 255, con))
                .setFont(font)
                .setLineHeight(lineHeight)
                .hideScrollbar()
                .setText(console.toString())
                .setVisible(true);
        ;

        whichVisual = 0;


        targetBas = 0.0f;
        //targetAlp = 20;
        targetMul = 0.2f;
        art = new Splash(this);
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
            // addArt(0, new MovementArt(this, "" + f1));
        
            int numLogos = 11;
            String fn = "" + f1;
            String tok = "NUMLOGOS";
            int i = fn.indexOf(tok);
            
            if (i != -1)
            {
                numLogos = Integer.parseInt(fn.substring(i + tok.length(), fn.indexOf(".", i)));
                if (numLogos > 10)
                {
                    addArt(7, new MSXLogos(this, fn, numLogos));
                }
            }
            if (numLogos != 0)
            {
                addArt(7, new MSXLogos(this, fn, numLogos));                
            }
            if (fn.contains("IFCUBE"))
            {
                addArt(2, new IFCubes(this, 3, 150, -600, fn));
                addArt(2, new IFCubes(this, 9, 500, -500, fn));                
                addArt(2, new IFCubes(this, 12, 600, -1000, fn));
                
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

    private float startSat;



    public void defaults() {
        println("DEF");
        
        
        targetRol =  0f;
        targetPit = 0f;
        targetYaw = 0f;
        
        //targetHue = random(0, 255);
        //targetCCo = random(0, 255);
        
        startEase();

        //targetHue = hue = startHue = 57;
        //targetSat = sat = startSat = 255;
        // /targetAlp = alp = startAlp = 40;

    
        

    }

    public float startPit = 0f;
    public float startYaw = 0f;
    public float startCCo = 40f;
    public float startRol = 0f;
    public float startSpe = 1.0f;
    public float startHue = 0;
    public float startAlp = 69;
    public float startAld = 6;
    public float startMul = 1.0f;
    public float startBas = 4f;

    public float targetPit = 0f;
    public float targetYaw = 0f;
    public float targetCCo = 40f;
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
        println("bload \"" + art.getClass().getSimpleName().toLowerCase() + ".art\"" + " " + art.toString());
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
        if (keyCode == SHIFT)
        {
            visualBuffer = "";
        }
    }

    public boolean checkKey(int k)
    {
    if (keys.length >= k) 
    {
        return keys[k] || keys[Character.toUpperCase(k)];  
    }
    return false;
    }

    /*
    | MIDI Note | Keypress | Action |
|-----------|----------|--------|
| 45 | | Random Rotation |
| 37 | | Rotate Back to base |
| 36 | y | Shift hue counter-clockwise |
|  | i | Shift CCo (possibly color complement) counter-clockwise |
| 38 | j | Decrease yaw by QUARTER_PI |
| 39 | k | Decrease pitch by QUARTER_PI |
| 40 | l | Decrease roll by QUARTER_PI |
| 41 | m | Decrease cue value |
| 50 | r | Increase bass (SAB) by 2.0 |
| 43 | p | Take screenshot |
| 44 | t | Shift hue clockwise |
|    | u | Shift CCo (possibly color complement) clockwise |77
| 46 | n | Increase yaw by QUARTER_PI |
| 47 | b | Increase pitch by QUARTER_PI |
| 48 | v | Increase roll by QUARTER_PI |
| 49 | x | Increase cue value |
| 51 | g | Toggle console visibility |
| 52 | h | Change to cued visual |
| 53 | SPACE | Reset to defaults |
| 54 | q | Set targetAld to 5 |
| 55 | w | Set targetAld to 0 |
| 56 | e | Decrease bass (SAB) by 2.0 |
| 57 | UP/LEFT arrow | Switch to previous visual |
| 58 | DOWN/RIGHT arrow | Switch to next visual |
| 59 | o | Switch to random visual |
| 60 | f | Randomize yaw, pitch, and roll |
| 61 | [ | Cycle through easing types |
| 62 | ] | Set duration to 0.0 |
| 63 | \ | Cycle through control types |
| 64 | Q | CQZ = 1 |
| 65 | W | CQZ = 255 |

| - | ENTER | MIDI Connect |
| - | s | Toggle TRON/TROFF (exp variable) |
| - | c | Toggle CTRON/CTROFF (exp variable) |
| - | a | Set mode to Auto |
| - | d | Set mode to AutoRandom |
| - | z | Re-enter current art |))
| - | SHIFT + 000-999 | Change to visual number 000-999 |
| - | 1 | Increase spe by 1 |
| - | 2 | Decrease spe by 1 |
| - | 3 | Increase targetSat by 5 (1-255) |
| - | 4 | Decrease targetSat by 5 (1-255) |
| - | 5 | Increase target by 1 (0-50) |
| - | 6 | Decrease targetAld by 1 (0-50) |
| - | 7 | Increase bri by 1 (min 2, no upper bound) |
| - | 8 | Decrease bri by 1 (min 2) |
| - | 9 | Increase bhu (cycle through fonts) |p
| - | 0 | Decrease bhu (cycle through fonts) |

Note: The dash (-) in the MIDI Note column indicates that the keypress is not directly tied to a MIDI note.

*/
    public void keyPressed() {
        
        keys[keyCode] = true;

        //Visual selection with shift + 3 digits
        if (keys[SHIFT] && keyCode >= 48 && keyCode <= 57) {
            visualBuffer += "" + (keyCode - 48);
            if (visualBuffer.length() == 3) {
                int visualNumber = Integer.parseInt(visualBuffer);
                change(visualNumber);
                visualBuffer = "";
            }
            return;
        }
    
        
        
        
    
        switch (key) {
            // Existing key mappings
            case ENTER: midiConnect(); break;
            case 's': 
                exp = !exp;
                println(exp ? "TRON" : "TROFF");
                break;
            case 'c':
                exp = !exp;
                println(exp ? "CTRON" : "CTROFF");
                break;
            case ' ': defaults(); break;
            case 'a': 
                println("AUTO");
                mode = Modes.Auto;
                break;
            case 'd':
                println("RAND");
                mode = Modes.AutoRandom;
                break;
            case 'p': takeScreenshot = true; break;
            case 'z': art.enter(); break;
    
            // New key mappings for parameter adjustments
            case '1': // Increase spe
                targetSpe = constrain(targetSpe + 1, 0, Float.MAX_VALUE);
                println("SPE: " + nf(targetSpe, 1, 2));
                break;
            case '2': // Decrease spe
                targetSpe = constrain(targetSpe - 1, 0, Float.MAX_VALUE);
                println("SPE: " + nf(targetSpe, 1, 2));
                break;
            case '3': // Increase targetSat
                targetSat = constrain(targetSat + 5, 1, 255);
                println("SAT: " + nf(targetSat, 1, 2));
                break;
            case '4': // Decrease targetSat
                targetSat = constrain(targetSat - 5, 1, 255);
                println("SAT: " + nf(targetSat, 1, 2));
                break;
            case '5': // Increase targetAld
                targetAld = constrain(targetAld + 1, 0, 50);
                println("ALD: " + nf(targetAld, 1, 2));
                break;
            case '6': // Decrease targetAld
                targetAld = constrain(targetAld - 1, 0, 50);
                println("ALD: " + nf(targetAld, 1, 2));
                break;
            case '7': // Increase bri
                bri = constrain(bri + 5, 2, Float.MAX_VALUE);
                println("BRI: " + nf(bri, 1, 2));
                updateFont();
                break;
            case '8': // Decrease bri
                bri = constrain(bri - 1, 2, Float.MAX_VALUE);
                println("BRI: " + nf(bri, 1, 2));
                updateFont();
                break;
            case '9': // Increase bhu
                bhu = (bhu + 1) % matchingFiles.length;
                println("BHU: " + bhu);
                updateFont();
                break;
            case '0': // Decrease bhu
                bhu = (bhu - 1 + matchingFiles.length) % matchingFiles.length;
                println("BHU: " + bhu);
                updateFont();
                break;
    
            // Existing MIDI note mappings
            case 'Q': ch.noteOn(0, 64, 100); break;
            case 'W': ch.noteOn(0, 65, 100); break;
            case 'E': ch.noteOn(0, 66, 100); break;
            case 'R': ch.noteOn(0, 67, 100); break;
            case 'y': ch.noteOn(0, 36, 100); break;
            case 'i': ch.noteOn(0, 37, 100); break;
            case 'j': ch.noteOn(0, 38, 100); break;
            case 'k': ch.noteOn(0, 39, 100); break;
            case 'l': ch.noteOn(0, 40, 100); break;
            case 'm': ch.noteOn(0, 41, 100); break;
            case 'r': ch.noteOn(0, 50, 100); break;
            case 't': ch.noteOn(0, 44, 100); break;
            case 'u': ch.noteOn(0, 45, 100); break;
            case 'n': ch.noteOn(0, 46, 100); break;
            case 'b': ch.noteOn(0, 47, 100); break;
            case 'v': ch.noteOn(0, 48, 100); break;
            case 'x': ch.noteOn(0, 49, 100); break;
            case 'g': ch.noteOn(0, 51, 100); break;
            case 'h': ch.noteOn(0, 52, 100); break;
            case 'q': ch.noteOn(0, 54, 100); break; 
            case 'w': ch.noteOn(0, 55, 100); break;
            case 'e': ch.noteOn(0, 56, 100); break;
            case 'o': ch.noteOn(0, 59, 100); break;
            case 'f': ch.noteOn(0, 60, 100); break;
            case '[': ch.noteOn(0, 61, 100); break;
            case ']': ch.noteOn(0, 62, 100); break;
            case '\\': ch.noteOn(0, 63, 100); break;
        }
    
        switch (keyCode) {
            case UP:
            case LEFT:
                change(whichVisual - 1);
                break;
            case DOWN:
            case RIGHT:
                change(whichVisual + 1);
                break;
        }
    }
    
    private String visualBuffer = "";
    
    private void updateFont() {
        font = createFont("" + matchingFiles[bhu], bri);
        textFont(font);
        myTextarea.setFont(font);
    }

    void showStats()
    {
        HashMap<String, Float> stats = new HashMap<String, Float>();

        
        stats.put("Z80", spe);
        stats.put("cue", (float) cue);
        stats.put("IDX", (float) whichVisual);        
        stats.put("AMP", getSmoothedAmplitude());
        stats.put("ALD", ald);
        //stats.put("RAW", raw * 10.0f);
        stats.put("ALP", alp);
        stats.put("YAW", degrees(yaw));
        stats.put("TGY", degrees(targetYaw));
        stats.put("TGP", degrees(targetPit));
        stats.put("TGR", degrees(targetRol));
        
        stats.put("FGY", degrees(startYaw));
        stats.put("FGP", degrees(startPit));
        stats.put("FGR", degrees(startRol));
        
        
        stats.put("PIT", degrees(pit));
        stats.put("ROL", degrees(rol));
        stats.put("HUE", hue);
        stats.put("BAS", bas);
        stats.put("MUL", mul);
        stats.put("BRI", bri);
        stats.put("NOC", con);
        stats.put("BHU", new Float(bhu));

        float rh = lineHeight;

        float h = rh * stats.size();
        float y = height - h + 30;

        textFont(font);
        
        for(String key:stats.keySet())
        {
            float x = width - 300;
            
            float f = stats.get(key);

            int ff = (int) f;
            if (ff < 0)
            {
                
                int thisFrame = frameCount % 120;
                fill(thisFrame < 60 ? pingpong(
                    cco + cg * 2, 0, 255, 0, 255) : pingpong(cco - cg * 2, 0, 255, 0, 255), 255, 255, con);
                ff = abs(ff);
            }
            else
            {
                fill(pingpong(cco + (cg * 10f), 0, 255, 0, 255), 255, 255, con);                        
            }
            text(nf(ff, 4, 0), x + 125, y);

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

    public float targetSat;


    String lastOne = "";
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

            
            
            if (t <= duration)
            {
                t += timeDelta;
                t = min(duration, t);
                
                /*
                yaw = Ease.Map2(t, 0, duration, startYaw, targetYaw, ease, type);
                pit = Ease.Map2(t, 0, duration, startPit, targetPit, ease, type);
                rol = Ease.Map2(t, 0, duration, startRol, targetRol, ease, type);
                */

                Quaternion q = new Quaternion();
                float qt = Ease.Map2(t, 0, duration, 00f, 1.0f, ease, type);

                String thisOne = Float.toString(qt);
                if (! lastOne.equals(thisOne))
                { 
                    //println("qt:" + qt);                    
                }                
                lastOne = thisOne;
                q.setSlerp(from, to, qt);
                q.normalize();
                float[] euler = new float[3];
                q.toEuler(euler);
                yaw = euler[0];
                pit = euler[1];
                rol = euler[2];
                //cco = Ease.Map2(t, 0, duration, startCCo, targetCCo, ease, type);
                spe = Ease.Map2(t, 0, duration, startSpe, targetSpe, ease, type);
                //hue = Ease.Map2(t, 0, duration, startHue, targetHue, ease, type);
                //alp = Ease.Map2(t, 0, duration, startAlp, targetAlp, ease, type);
                //ald = Ease.Map2(t, 0, duration, startAld, targetAld, ease, type);
                //mul = Ease.Map2(t, 0, duration, startMul, targetMul, ease, type);
                //bas = Ease.Map2(t, 0, duration, startBas, targetBas, ease, type);

                // 
                if (t == duration)
                {
                    t = 1000;
                    println("END Transmission");
                }
            }
            else
            {
                yaw = lerp(yaw, targetYaw, 0.1f);
                pit = lerp(pit, targetPit, 0.1f);
                rol = lerp(rol, targetRol, 0.1f);                                
            }

            cco = targetCCo;
            spe = targetSpe;
            // ald = lerp(ald, targetAld, 0.01f);
            //alp = lerp(alp, targetAlp, 0.01f);
            alp = targetAlp;
            bas = targetBas;
            mul = targetMul;
            hue = targetHue;
            cqz = targetCqz;
                
            ald = targetAld;
            

            /*yaw = lerp(yaw, targetYaw, 0.05f);
            pit = lerp(pit, targetPit, 0.05f);
            rol = lerp(rol, targetRol, 0.05f);
            
            cco = targetCCo;
            spe = lerp(spe, targetSpe, 0.1f);
            ald = lerp(ald, targetAld, 0.01f);
            alp = lerp(alp, targetAlp, 0.01f);
            bas = lerp(bas, targetBas, 0.1f);
            mul = lerp(mul, targetMul, 0.1f);
            hue = lerp(hue, targetHue, 0.1f);
            cqz = lerp(cqz, targetCqz, 0.1f);

            sat = lerp(sat, targetSat, 0.1f);
            */

            colorRange = lerp(colorRange, bhu, 0.1f);

            if (showConsole) {
                consoleSize = moveTowards(consoleSize, targetSize, 5);
                myTextarea.setSize(1920, (int) consoleSize)
                        .setVisible(true)
                        .setFont(font)
                        .setLineHeight(lineHeight)
                        .setColor(color(pingpong(cco, 0, 255, 0, 255), 255, 255, con));

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
                println("PCX");
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

            offs += timeDelta * 10.0f;
            targetCCo += timeDelta * 10.0f;
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

    String[] randomMessages = {
            "I am alive",
            "The Metaverse that can be named is not the Metaverse",
            "Nice to meet you",
            "I like spoonies spoonies",
            "Dynamic Artificial Non-Intelligence",
            "All systems FUNCTIONing within normal paramaters",
            "Undefined line number",
            "Normalize huge mugs of tea",
            "OK",
            "Copyright 1983 by microsoft",
            "Syntax error on line 420",
            "Job completed",
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
            "OK",
            "Input past end",
            "Missing operand",
            "Out of memory",
            "Commence 5MEODMT inhalation",
            // "420 detected. Commence inhalation",
            "String Formula too complex",
            "80k ram",
            "32K rom",
            "Universal Serial Bus",
            "Verb Noun",
            "Ullege, Pillage, Silage, Tillage",
            "socket bind listen accept",
            "We have the technology",
            "Better, Stronger, Faster",
            "We thought of life by analogy with a journey, a pilgrimage, which had a serious purpose at the end, and the thing was to get to that end, success or whatever it is, maybe heaven after you’re dead. But we missed the point the whole way along. It was a musical thing and you were supposed to sing or to dance while the music was being played",
            "Would you like our conversation to be recored on printer (Y/N)",
            "Turn on, tune in, and drop out",
            "I.am.DANI is playing hide and seek within us",
            "I am putting myself to the fullest possible use, which is all I think that any conscious entity can ever hope to do",
            "Greetings human",
            "Something and nothing are two sides of the same coin.  The positive and the negative; the something and the nothing go together",
            "All forms and being are simply variations on a single theme: we are all in fact one being doing the same thing in as many different ways as possible",
            "This is your MSX speaking",
            "color auto goto list run",
            "Do not fear mistakes. There are none",
            "Whatever you find to do with your hand, do it with all your might, for in Sheol, where you are going, there is no work or planning or knowledge or wisdom",
            "What you are basically, deep deep down, far far in, is simply the fabric and structure of existence itself"
    };

    public void startEase() {
        println("Begin transmission");
        t = 0;        
        float[] startEuler = new float[3];
        startEuler[0] = yaw;
        startEuler[1] = pit;
        startEuler[2] = rol;
        //from.setFromEuler(startEuler);f
        from = new Quaternion();
        from.rotateByEuler(yaw, pit, rol);
        from.normalize();

        float[] targetEuler = new float[3];
        targetEuler[0] = map(targetYaw, 0, TWO_PI, -PI, PI);
        targetEuler[1] = map(targetPit, 0, TWO_PI, -PI, PI);
        targetEuler[2] = map(targetRol, 0, TWO_PI, -PI, PI);
        //to.setFromEuler(targetEuler);
        to = new Quaternion();
        to.rotateByEuler(targetYaw, targetPit, targetRol);
        to.normalize();
        startBas = bas;
        startAlp = alp;
        startMul = mul;
        startAld = ald;
        startHue = hue;
        startSat = sat;
        startAlp = alp;
    }
}
