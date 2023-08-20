// java program that draws a left heart that beats by detecting beats from a music file
package ie.tudublin;
 

import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
 
public class LeftHeart extends Poly
{
    BeatDetect beat;
 
    public LeftHeart(AudioGarden v)
    {
        super(v);
        this.beat = v.beat;
    }
 
    public void render()
    {
        draw();
    }
 
    public void draw()
    {
        //v.background(0); // set background to black
        analyzeMusic(); // call method to detect beats in the audio
        leftHeart(); // display fullHeart
    }
 
    int heartColor;
    float beatSize = 10;
    float beatMaxSize = 100;
    float beatDecay = 0.9f;
 
    // analyse music to detect beats
    public void analyzeMusic()
    {
        beat.detect(v.getAudioBuffer()); // detect beats and updates
        if (beat.isOnset()) // if statement to check if a beat has been detected
        {
            heartColor = v.color(v.random(255), v.random(255), v.random(255)); // set heart colour to random of 255
            beatSize = beatMaxSize; // sets the size of the heart beat to the maximum size
        }
        beatSize *= beatDecay; // update size of heart by multiplying beatDecay to reduce size
    }
 
    float beatSpeed = 0.5f;
    float threshold = 50;
    private float beatMinSize;
 
    // method that draws left heart
    public void leftHeart()
    {
        float heartSize = MyVisual.map(v.ab.level(), 0, 1, 50, 200) * 8; // calculate heart size based on left level channel of audio, from 0-1 to range of 50-200, multiply by 8
 
        v.strokeWeight(4);
        v.stroke(255, 0, 0);
        v.fill(255, 0, 0);
 
        float bezierOffset = heartSize/6; // the spacing between the bezier curves
       
        v.beginShape(); // start drawing
        v.vertex(v.width/2, v.height/2 + heartSize/2);
        v.bezierVertex(v.width/4 - heartSize/2, v.height/2 - bezierOffset - heartSize/80, v.width/2 - heartSize/80, v.height/2 - heartSize/2 + bezierOffset, v.width/2, v.height/2 - heartSize/80); // left heart
        v.endShape(); // end drawing
        v.noStroke();
        v.fill(255, 255, 255);
        beatSize -= beatSpeed; // beat size is decresed by the beat speed so it becomes smaller over time
        // ensure that the circles dont become too small
        if (beatSize < beatMinSize) // if heart beat size is less than min. size
        {
            beatSize = beatMinSize; // sets to the min.
        }
    }
 
    // method that stops the program
    public void stop()
    {
        v.stop(); // stop process in the superclass's stop()
    }
}