//Made by Lee Cox inspiration from https://www.youtube.com/watch?v=fO1uW-xhwtA

//I made a mobius strip and then stopped it from looking like one at all by changing it with the amplitude
//of the music drastically. Screen flashes and is very bright and the animation is very bright and all over the place


package C21503599;
import java.util.Random;

import ddf.minim.*;
import ddf.minim.analysis.BeatDetect;



import ie.tudublin.Visual;
import processing.core.PVector;


public class Lee extends Visual {

    Minim minim;
    AudioInput in;
    BeatDetect beat;
    AudioBuffer ab;
    AudioPlayer ai;
    int redN = 60;
    int gb = 220;
    int siz = 20;
    int swap = 0;
    int hold;
    float rot = (float)0.06;

    public void settings() {
        fullScreen(P3D, SPAN);
        minim = new Minim(this);
        beat = new BeatDetect();
        beat.setSensitivity(100);
    }

    public void setup() {
        startMinim();
        loadAudio("chucky.mp3"); 
        getAudioPlayer().play();
        lights();
    }
    
    public void keyPressed() {
        //change strip width
        if (key == ' ') {
            swap++;
            siz = swap % 2;
        }
        //reset strip width
        if (key == 'r') {
            siz = 20;
            textSize(50);
        }
        //randomise colours of strip
        if (key == 'c') {
            redN = (int)random(0, 255);
            gb = (int)random(0,255);
        }

        if (key == 'p') {
            if (rot == (float)0.06) {
                rot = (float)0.00;
            } else {
                rot = (float)0.06;
            }
        }
    }
    //swap from range -1 to 1 - 0 to 0 also known as a cirlce
    public void swap() {
        swap++;
        siz = swap % 2;
    }

    //to swap colours
    public void colourSwap() {
        hold = redN;
        redN = gb;
        gb = hold;
    }

    public void draw() {
        beat.detect(getAudioPlayer().mix);
        blendMode(NORMAL);
        background(0);
        
        beginCamera();
        //centers visual
        translate(width/2, height/2, 0);

        //spin strip
        rotateY(rot);
        rotateX(rot);
        calculateAverageAmplitude();

        //stopped audio issues
        hint(DISABLE_DEPTH_TEST);
        //make stroke appear more 3D
        hint(ENABLE_STROKE_PERSPECTIVE);
        int total = 40;
        
        //2d arr to store the coords of strip
        PVector[][] PVa = new PVector[total][total];


        //calculate co-ordinates for mobius strip
        for (int i = 0; i < total; i++) {
            //u is the angle of rotation of plane around central axis
            float u = map(i, 0, total - 1, 0, PI*2);
            for (int j = 0; j < total; j++) {
                int ind = i + j * total;
                
                //v is the point along the line of the strip
                float v = map(j, abs(getAudioBuffer().get(ind%512)*100), total -1, -siz, siz);
                float x =  ((1 + v/2*(cos(u/2)))*cos(u));
                float y =  ((1 + v/2*(cos(u/2)))*sin(u));
                float z = (v/2*(sin(u/2)));

                PVa[i][j] = new PVector(x * 50, y * 50, z * 50);
            }
        }

        //overlapping colour adds towards white
        blendMode(ADD);

        //Creates mobius strip from co-ordinates using triangles
        //Changes alpha based on amplitude each frame with a with a cap on 255
        //The red, green and blue get changed by the beat detect to switch to a dark red
        for (int i = 0; i < total - 1; i++) {
            beginShape(TRIANGLE_STRIP);
            stroke(redN, gb, gb,  min(getAudioBuffer().get(i)*500, 255));
            noFill();
            for (int j = 0; j < total -1; j++) {
                vertex(PVa[i][j].x, PVa[i][j].y, PVa[i][j].z);
                vertex(PVa[i+1][j].x, PVa[i+1][j].y, PVa[i+1][j].z);
            }
            endShape();
        }
        

        //flicker backround and change strip colours on beat
        if (beat.isOnset()) {
            background(255);
            colourSwap();
        } 
        //cancels out translation so it doesn't go flying off screen
        translate(-(width/2), -(height/2), 0);
        endCamera();
    }   
}
