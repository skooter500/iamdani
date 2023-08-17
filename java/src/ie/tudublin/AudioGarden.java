package ie.tudublin;

import ddf.minim.analysis.BeatDetect;

import themidibus.*; //Import the library

public class AudioGarden extends Visual implements MidiListener{
   
    Poly play;

    MidiBus myBus; // The MidiBus


    public void settings(){
        //fullScreen(P3D,SPAN);
        size(800, 600, P3D);
	}

    public void noteOn(int channel, int pitch, int velocity) {
        // Receive a noteOn
        println();
        println("Note On:");
        println("--------");
        println("Channel:"+channel);
        println("Pitch:"+pitch);
        println("Velocity:"+velocity);
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
    }

	public void setup(){
        colorMode(HSB,360,100,100);		        
        startMinim();
        rectMode(CENTER); 

        MidiBus.list();
        myBus = new MidiBus(this, 1, 4);
        myBus.addMidiListener(this);
        //startListening();

		//eye = loadShape("eyeball.obj");
        //grave = loadShape("gravestone.obj");
        //texture = loadImage("gravestone.mtl");
        noiseSeed(0l);

        //beat = new BeatDetect(ai.bufferSize(), ai.sampleRate());
        //beat.setSensitivity(10);
        //bl = new BeatListener(beat, getAudioPlayer());
        
        play = new Cubes(this);
		
        colorMode(HSB, 360, 100, 100);
	}
    

    
    public void keyPressed() {
    
        if (key == ' ') 
        {
            if(getAudioPlayer().isPlaying()){
                getAudioPlayer().pause(); //pauses the song
            }
            else{
                getAudioPlayer().loop(); //starts the song playing again from the point it left off
            }
        }
        
        if (key == '1')
        {
            play = new Bloom(this);
        }
        
        if (key == '2')
        {
            play = new Cubes(this);
        }

        if ( key == '3')
        {
            play = new kalidascope(this);
        }

        if (key =='4')
        {
            play = new Spiral(this);
        }

        if(key == '5'){
            play = new Cubesquared2(this);
        }

        if(key == '6'){
            play = new SinWaves(this);
        }

        

        if(key =='r' || key =='R'){ //allows for the song to be  started again from the beginning

            getAudioPlayer().cue(0);
            startListening();
        }
        
    }    


	public void draw(){

        stroke(255,255,255);
        background(0);
        // try
        // {
        //     // Call this if you want to use FFT data
        //     //calculateFFT(); 
        // }
        // catch(VisualException e)
        // {
        //     e.printStackTrace();
        // }
        // Call this is you want to use frequency bands
        //calculateFrequencyBands(); 

        // Call this is you want to get the average amplitude

        //calculateAverageAmplitude();        
       
      
        //will pulse an object with music volume
        // calculateAverageAmplitude();    

        // play.render(); //renders the currently loaded visual
        
    }   
    
    
}
    