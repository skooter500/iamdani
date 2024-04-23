package ie.tudublin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jogamp.opengl.math.Quaternion;

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
import processing.core.PShapeSVG.Font;
import themidibus.*; //Import the library

public class IAMDANI extends ie.tudublin.visual.Visual implements MidiListener {

    ArrayList<Poly> visions = new ArrayList<Poly>();

    // Poly play;
    int previousVisual = 0;
    int whichVisual = 0;

    MidiBus myBus = null; // The MidiBus

    float ald = 1;
    float colorRange = 255;
    float camDistance = 0.5f;
    float strokeWeight = 1;
    
    PFont f;

    public void settings() {
        fullScreen(P3D, 2);
        // size(1000, 1000, P3D);
    }

    PShape sphere;

    public int lea = 0;

    public static IAMDANI instance;

    public StringBuilder console = new StringBuilder();

    public enum Modes {
        Ctrl, Auto, AutoRandom
    };

    public Modes mode = Modes.Ctrl;

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
        println("MSX System");
        println("version 1.0");
        println("Copyright 1983 by microsoft");
        println("ok");
        println("load \"DANI.BAS\"");
        println("ok");
        println("RUN");
        println("Greetings Human");
        println("This is your MSX speaking");
        println("I AM DANI");
        println("dynamic articicial non-intelligence");
        println("Talk to me and I will learn what you say and answer you");
        println("speak now or forever hold your peace");

    }

    public void midiConnect() {
        try {
            MidiBus.list();
            int daniMidi = -1;

            if (daniMidi == -1) {
                for (int i = 0; i < MidiBus.availableInputs().length; i++) {
                    String curr = MidiBus.availableInputs()[i];
                    if (curr.equals("Arturia BeatStep")) {
                        daniMidi = i;
                        println("JOYSTICK ATTACHED: " + daniMidi);
                    }
                }
            }

            if (daniMidi == -1) {
                println("Insert joystick");
            } else {
                if (myBus != null) {
                    myBus.close();
                }

                myBus = new MidiBus(this, daniMidi, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HashMap<Integer, ArrayList<Integer>> groups = new HashMap<Integer, ArrayList<Integer>>();

    public float moveTowards(float current, float target, float maxDistanceDelta) {
        float delta = target - current;

        if (abs(delta) <= maxDistanceDelta || delta == 0.0f) {
            return target;
        }

        return current + (delta > 0 ? 1 : -1) * maxDistanceDelta;
    }

    void addVision(int g, Poly p) {
        ArrayList<Integer> group = null;
        if (groups.containsKey(g)) {
            group = groups.get(g);
        } else {
            group = new ArrayList<Integer>();
            groups.put(g, group);
        }
        group.add(visions.size());
        visions.add(p);
    }

    // public void sphere(float size)
    // {
    // pushMatrix();
    // scale(size);
    // shape(sphere);
    // popMatrix();
    // }

    public void setup() {

        f = createFont("Hyperspace Bold.otf", 24);

        textFont(f);

        sphere = loadShape("sphere.obj");

        toPass = (int) random(1000);
        noCursor();
        smooth();
        colorMode(HSB);
        startMinim();
        // rectMode(CENTER);

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
        // HashMap<Number, Object> g = new HashMap()<Number, Object>();

        addVision(0, new Basic(this, "DANI.BAS"));
        addVision(0, new DANI(this, "captainb.txt"));
        addVision(0, new Nematode(this));
        // groups.add(g);
        addVision(1, new Life(this, 2, 280, 100));
        addVision(1, new Life(this, 3, 10000, 200));
        addVision(1, new Life(this, 0, 1000, 100));
        addVision(1, new Life(this, 1, 1000, 100));
        // addVision(1, new Life(this, 4, 10000, 100));

        addVision(2, new infiniteforms.Cube(this));
        addVision(2, new IFCubes(this, 7, 150, -600));
        addVision(2, new IFCubes(this, 30, 150, -400));

        addVision(3, new Spiral(this));
        addVision(3, new Cubesquared2(this));
        addVision(3, new Cubes(this));

        addVision(4, new AllBalls(this));
        addVision(4, new paris(this));
        addVision(4, new LauraSun(this));
        addVision(4, new Mena(this));
        addVision(4, new ManarBrain(this));

        addVision(5, new MSXLogos(this, "msx.obj"));
        addVision(5, new MSXLogos(this, "chip.obj"));

        // YM2413

        addVision(6, new Models1(this, "tudub.obj", false, true));
        addVision(6, new Models1(this, "msx.obj", false, true));
        addVision(6, new Models1(this, "eye.obj", true, false));
        Models1 horse = new Models1(this, "horse.obj", true, false);
        horse.scale = 0.5f;
        horse.model.pitOff = 1;
        addVision(6, horse);
        addVision(6, new Models1(this, "chip.obj", true, false));

        addVision(7, new Bloom(this));
        addVision(7, new Terrain(this));

        // addVision(new Airish(this));

        // addVision(new Bands(this, 200, 0, 0, 0));
        // addVision(new paris(this));
        // addVision(new Spiral(this));
        // addVision(new SarahVisual(this));
        // addVision(new JenniferVisuals(this));

        // addVision(new Life(this, 1, 1000));

        // new set

        //// addVision(new SarahVisual(this));

        // visuals.add(new Models1(this, "thc molecule.obj"));

        // Collections.shuffle(visions);
        defaults();
        background(0);
        change(0);

        // String[] fonts = PFont.list();

        // for(String s:fonts)
        // {
        // println(s);
        // }

        myTextarea = cp5.addTextarea("txt")
                .setPosition(40, 40)
                .setSize(10, (int) 360)
                .setColor(color(consoleColor, 255, 255, alp))
                .setFont(f)
                .setLineHeight(36)
                .hideScrollbar()
                .setText(console.toString())
                .setVisible(true);
        ;

    }

    float consoleSize = 0;
    float originalTargetSize = 400;

    float targetSize = 1040;

    ControlP5 cp5;
    Textarea myTextarea;
    float consoleColor = 100;

    private boolean takeScreenshot = false;

    void defaults() {
        println("DEF");
        targetCCo = cco = 76f;
        targetRol = 0f;
        targetPit = 0f;
        targetYaw = 0f;
        targetBas = 3.6f;
        targetAlp = 75;
        targetAld = 1;
        targetMul = 1.0f;
    
        bhu = 255;
        bri = 255;
        sat = 255;        
        ;
    }

    float targetPit = 0f;
    float targetYaw = 0f;

    float targetCCo = 0f;
    float targetRol = 0f;
    float targetSpe = 1.0f;
    float targetHue = 0;
    float targetAlp = 75;
    float targetAld = 4;
    float targetMul = 1.0f;
    float targetBas = 0.3f;
    public float bhu = 0;
    public float bri = 0;
    public float sat = 0;

    public IAMDANI() {
        super(1024, 44100, 0.5f);
        instance = this;

    }

    public void noteOn(int channel, int pitch, int velocity) {

        // 43 is print screen button
        if (exp && pitch != 43)
            println("N+ CH " + channel + " PI " + pitch + " VE " + velocity);

        switch (mode) {
            case Auto: {
                if (pitch == 43) {
                    takeScreenshot = true;
                    return;
                }
                int newVisual = pitch % visions.size();
                change(newVisual);
                return;
            }
            case AutoRandom: {
                if (pitch == 43) {
                    takeScreenshot = true;
                    return;
                }
                int newVisual = (int) random(0, visions.size());
                change(newVisual);
                return;
            }
        }

        if (pitch == 43) {
            takeScreenshot = true;
            return;
        }

        // Receive a noteOn
        // SPecial codes

        if (pitch >= 44 && pitch <= 47) {
            int g = pitch - 44;
            changeToGroupVisual(g);
            return;
        }

        if (pitch == 48)
        {
            targetYaw = random(targetYaw - HALF_PI, targetYaw + HALF_PI);
            targetPit = random(targetPit - HALF_PI, targetPit + HALF_PI);
            targetRol = random(targetRol - HALF_PI, targetRol + HALF_PI);
            // targetHue = random(0, 255);
            // targetAlp = random(10, 255);
            // targetAld = random(0, 50);
            if (exp) println("RND");
        }

        if (pitch >= 36 && pitch <= 39) {
            int g = pitch - 36;
            g += 4;
            changeToGroupVisual(g);            
        }

        if (pitch == 40) {
            exp = !exp;
            if (exp) {
                println("TRON");
                return;
            } else {

                println("TROFF");
                return;
            }
        }

        if (pitch == 51) {
            showConsole = !showConsole;
            println("CON:" + showConsole);
            consoleSize = 0;
            if (!showConsole) {
                myTextarea.setVisible(showConsole);
            }
            return;
        }
        if (pitch == 49) {
            boolean clockWise = true;
            ArrayList<Integer> group = groups.get(findGroup(whichVisual));
            int newWhichVisual = min(max(clockWise ? whichVisual + 1 : whichVisual - 1, group.get(0)),
                    group.get(group.size() - 1));
            if (newWhichVisual != whichVisual) {
                whichVisual = newWhichVisual;
                change(whichVisual);
            }
            return;
        }

        if (pitch == 41) {
            boolean clockWise = false;
            ArrayList<Integer> group = groups.get(findGroup(whichVisual));
            int newWhichVisual = min(max(clockWise ? whichVisual + 1 : whichVisual - 1, group.get(0)),
                    group.get(group.size() - 1));
            if (newWhichVisual != whichVisual) {
                whichVisual = newWhichVisual;
                change(whichVisual);
            }
            return;
        }

        if (pitch == 42) {
            if (exp) println("RST");
            alp = 0;
            ald = 0;
            visions.get(whichVisual).enter();
            return;
        }

        if (pitch == 50) {

            defaults();
            return;
        }

    }

    private void changeToGroupVisual(int g)
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
             targetAld = 0;
             targetPit = 0;
             targetRol = 0;
             targetYaw = 0;
             rol = 0;
             pit = 0;
             yaw = 0;
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
            into = visions.size() + into;
        }
        into = into % visions.size();
        if (whichVisual >= 0 && whichVisual < visions.size()) {
            visions.get(whichVisual).exit();
        }
        whichVisual = into;
        alp = 0;
        // targetAld = 0;
        visions.get(whichVisual).enter();
        println(whichVisual + " " + visions.get(whichVisual).getClass().getSimpleName());
    }

    static public boolean exp = true;

    public void controllerChange(int channel, int number, int value) {

        if (exp)
            println("CH " + channel + " NUM " + number + " VA " + value);

        boolean clockWise = (value < 100);

        if (number == 7) {
            targetSpe = min(max(clockWise ? targetSpe + 0.05f : targetSpe - 0.05f, 0.0f), 3.58f);
            if (exp)
                println("Z80 " + nf(targetSpe, 1, 2) + " MHZ");
        }

        if (number == 10) {
            targetBas = max(clockWise ? targetBas + 0.01f : targetBas - 0.01f, 0.01f);
            if (exp)
                println("BAS " + nf(targetBas, 3, 2));
        }

        if (number == 114) {
            targetMul = max(clockWise ? targetMul + 0.1f : targetMul - 0.1f, 0);
            if (exp)
                println("MUL " + nf(targetMul, 3, 2));
        }
        if (number == 74) {
            // hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            targetHue = clockWise ? targetHue + 1f : targetHue - 1f;
            if (exp)
                println("HUE " + nf(targetHue, 3, 2));
        }
        if (number == 73) {
            bhu = (clockWise ? bhu + 1f : bhu - 1f);
            if (exp)
                println("BHU " + nf(bhu, 3, 2));
        }
        if (number == 79) {
            bri = (clockWise ? bri + 1f : bhu - 1f);
            if (exp)
                println("bri " + nf(bri, 3, 2));
        }
        if (number == 75) {
            ArrayList<Integer> group = groups.get(findGroup(whichVisual));
            int newWhichVisual = min(max(clockWise ? whichVisual + 1 : whichVisual - 1, group.get(0)),
                    group.get(group.size() - 1));
            if (newWhichVisual != whichVisual) {
                whichVisual = newWhichVisual;
                change(whichVisual);
            }
            return;
        }
        if (number == 72) {
            int newWhichVisual = min(max(clockWise ? whichVisual + 1 : whichVisual - 1, 0), visions.size() - 1);
            if (newWhichVisual != whichVisual) {
                whichVisual = newWhichVisual;
                change(whichVisual);
            }
            return;
        }

        if (number == 18) {
            // hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
            targetHue = clockWise ? targetHue + 5f : targetHue - 5f;
            if (exp)
                println("HUE " + nf(targetHue, 3, 2));
        }

        if (number == 76) {
            targetAld = min(max(clockWise ? targetAld + .1f : targetAld - .1f, 0), 50);
            
            if (exp)
                println("ALD " + nf(targetAld, 3, 2));
        }

        // if (number == 16) {
        //     targetAld = min(max(clockWise ? targetAld + 1f : targetAld - 1f, 10), 50);
        //     if (exp)
        //         println("ALD " + nf(targetAld, 3, 2));
        // }

        if (number == 19) {
            targetAlp = min(max(clockWise ? targetAlp + 1f : targetAlp - 1f, 10), 255);
            if (exp)
                println("ALP " + nf(targetAlp, 3, 2));
        }

        float rotSpeed = 0.01f;

        if (number == 71) {
            targetAlp = min(max(clockWise ? targetAlp + 0.1f : targetAlp - 0.1f, 1f), 255);
            if (exp)
                println("ALP " + nf(targetAlp, 3, 2));
        }
        if (number == 77) {
            targetYaw = clockWise ? targetYaw + rotSpeed : targetYaw - rotSpeed;
            targetYaw = wrapAngle(targetYaw);
            if (exp)
                println("yaw " + nf(targetYaw, 3, 2));
        }

        if (number == 93) {
            targetRol = clockWise ? targetRol + rotSpeed : targetRol - rotSpeed;
            targetRol = wrapAngle(targetRol);
            if (exp)
                println("ROL " + nf(targetRol, 3, 2));
        }

        if (number == 91) {
            targetCCo = clockWise ? targetCCo + 1f : targetCCo - 1f;
            if (exp)
                println("CCO " + nf(targetCCo, 3, 2));

        }

        if (number == 17) {
            targetPit = clockWise ? targetPit + rotSpeed : targetPit - rotSpeed;
            targetPit = wrapAngle(targetPit);
            if (exp)
                println("pit " + nf(targetPit, 3, 2));
        }
        // int newVisual = whichVisual;
        // if (clockWise)
        // newVisual = (newVisual + 1) % visions.size();
        // else {
        // newVisual--;
        // if (newVisual < 0) {
        // newVisual = visions.size() - 1;
        // }
        // }
        // change(newVisual);
    }

    private float wrapAngle(float targetPit2) {
        return targetPit2;
            }

    private int findGroup(int g) {
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

    public void keyPressed() {

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

        float rh = 30;

        float h = rh * stats.size();
        float y = height - h;
        
        for(String key:stats.keySet())
        {
            fill(0, 255, 255);
            float x = width - 140;
            
            float f = stats.get(key);

            int ff = (int) f;
            if (ff < 0)
            {
                fill(87, 255, 255);
                ff = abs(ff);
            }
            text(nf(ff, 3, 0), x + 65, y);
            text(key, x, y);

            y += rh;            
        }


    }

    public void takeScreenshot() {
        saveFrame("../screenshots/i.am.dani-######.png");
    }

    boolean showConsole = true;

    public static float timeDelta = 0;
    long last = 0;                                                                                                                                                                                                 

    int toPass;

    public void draw() {
        colorMode(RGB);
        blendMode(SUBTRACT);////
        fill(255, ald);

        pushMatrix();
        translate(0, 0, -5000);
        rect(-width * 5, -height * 5, width * 10, height * 10);
        popMatrix();
        blendMode(BLEND);
        colorMode(HSB);

        yaw = lerp(yaw, targetYaw, 0.01f);
        pit = lerp(pit, targetPit, 0.01f);
        rol = lerp(rol, targetRol, 0.01f);
        cco = targetCCo;
        spe = lerp(spe, targetSpe, 0.1f);
        ald = lerp(ald, targetAld, 0.1f);
        alp = lerp(alp, targetAlp, 0.005f);
        bas = lerp(bas, targetBas, 0.1f);
        mul = lerp(mul, targetMul, 0.1f);
        hue = lerp(hue, targetHue, 0.1f);
        colorRange = lerp(colorRange, bhu, 0.1f);

        if (showConsole) {
            consoleSize = moveTowards(consoleSize, targetSize, 5);
            myTextarea.setSize(1920, (int) consoleSize)
                    .setVisible(true)
                    .setColor(color(cco, 255, 255));

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
        visions.get(whichVisual).render(frameCount); // renders the currently loaded visual
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
            "welcome to the metaverse",
            "nice to meet you",
            "strike any key",
            "i exist",
            "NEXT without FOR",
            "Spoonies spoonies?",
            "dynamic artificial non-intelligence",
            "subscript out of range",
            "act normal",
            "Undefined line number",
            "RETURN without GOSUB",
            "a nice mug of tea",
            "ok",
            "MSX system version 1.0",
            "Copyright 1983 by microsoft",
            "syntax ERROR on line 420",
            "i seek the creator",
            // "© Microcabin",
            // "am in a k-hole y/n?",
            "Job completed",
            "28815 bytes free",
            "subspace anomoly on line 420",
            "we can rebuild him",
            "String too long",
            "Unprintable error",
            "Line buffer overflow",
            "CONTINUE",
            "Division by zero",
            "Type mismatch",
            "Disk full",
            "input past end",
            "Missing operand",
            "Out of memory",
            "commence 5meodmt inhalation",
            "420 DETECTED",
            "MDMA synthesis complete",
            "Illegal function call",
            "String formula too complex",
            "80k ram",
            "32K rom",
            "We have the technology",
            "do not masterbate",
            "Better, stronger, faster",
            "speak now or forever hold your peace",
            "record output to printer",
            "turn on, tune in, and drop out",
            "God is playing hide and seek within you",
            "I am putting myself to the fullest possible use, which is all I think that any conscious entity can ever hope to do",
            "Greetings Human",
            "This is your MSX speaking",
            "color auto goto list run",
            "Z80A CPU",
    };

}










