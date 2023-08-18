package ie.tudublin;

import java.util.ArrayList;

import c21348423.AdriansVisual;
import ddf.minim.analysis.BeatDetect;

import themidibus.*; //Import the library

public class AudioGarden extends ie.tudublin.visual.Visual implements MidiListener{
   
    ArrayList<Poly> visuals = new ArrayList<Poly>();

    //Poly play;
    int previousVisual = 0;
    int whichVisual = 0;

    MidiBus myBus; // The MidiBus

    int trails = 20;

    public AudioGarden()
    {
        super(1024, 44100, 0.5f);
    }

    public void settings(){
        // fullScreen(P3D, 0);
        size(1000, 1000, P3D);
	}

    public void noteOn(int channel, int pitch, int velocity) {
        // Receive a noteOn
        println();
        println("Note On:");
        println("--------");
        println("Channel:"+channel);
        println("Pitch:"+pitch);
        println("Velocity:"+velocity);

        whichVisual = pitch % visuals.size();

    }
        
    public void noteOff(int channel, int pitch, int velocity) {
    // Receive a noteOff
    println();
    println("Note Off:");
    println("--------");
    println("Channel:"+channel);
    println("Pitch:"+pitch);
    println("Velocity:"+velocity);
    }

    public void controllerChange(int channel, int number, int value) {
        // Receive a controllerChange
        println();
        println("Controller Change:");
        println("--------");
        println("Channel:"+channel);
        println("Number:"+number);
        println("Value:"+value);
        
        println(value);

        boolean clockWise = (value < 127);
        if (number == 7)
        {
            sensitivity = clockWise ? sensitivity + 0.1f : sensitivity - 0.1f; 
            println("sensitivity : " + sensitivity);
        }
        if (number == 10)
        {
            base = clockWise ? base + 0.1f : base - 0.1f;
            println("base : " + base);
        }
        if (number == 74)
        {
            trails = clockWise ? trails + 1 : trails -1; 
            println("trails : " + trails);
        }
    }

	public void setup(){
        colorMode(HSB,360,100,100);		        
        startMinim();
        rectMode(CENTER); 

        MidiBus.list();
        myBus = new MidiBus(this, 1, 4);
        //myBus.addMidiListener(this);
        startListening();

		//eye = loadShape("eyeball.obj");
        //grave = loadShape("gravestone.obj");
        //texture = loadImage("gravestone.mtl");
        noiseSeed(0l);

        beat = new BeatDetect(ai.bufferSize(), ai.sampleRate());
        beat.setSensitivity(10);
        //bl = new BeatListener(beat, getAudioPlayer());
        		
        colorMode(HSB, 360, 100, 100);
        visuals.add(new AdriansVisual(null));
        visuals.add(new kalidascope(this));
        visuals.add(new Cubes(this));
        visuals.add(new Bloom(this));
        
        visuals.add(new Spiral(this));
        visuals.add(new Cubesquared2(this));
        visuals.add(new SinWaves(this));
        visuals.add(new SinWaves(this));
 
        background(0);
	}

	public void draw(){

        // background(0);
        colorMode(RGB);
        blendMode(SUBTRACT);
        fill(255, trails);
        rect(0, 0, width * 4, height * 4);
        blendMode(BLEND);
        colorMode(HSB);
        

        stroke(255,255,255);
        //background(0);
        try
        {
             // Call this if you want to use FFT data
             calculateFFT(); 
         }
        catch(VisualException e)
        {
            e.printStackTrace();
        }
        // Call this is you want to use frequency bands
        calculateFrequencyBands(); 
        // will pulse an object with music volume
        calculateAverageAmplitude();    

        visuals.get(whichVisual).render(); //renders the currently loaded visual
        
    }   
    
    
}
    