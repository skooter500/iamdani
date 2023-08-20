// java program that draws a full heart, left heart and right heart that beats by detecting beats from a music file
package ie.tudublin;
 
import ddf.minim.analysis.BeatDetect;
 
public class JiaHeart extends Poly
{
    Visual p;
    BeatDetect beat;
 
    public JiaHeart(AudioGarden v)
    {
        super(v);
        p = v;
        this.beat = v.beat;
    }
 
    public void render()
    {
        draw();
    }
 
    public void draw()
    {
        v.colorMode(v.RGB);
        analyzeMusic(); // call method to detect beats in the audio
        fullHeart(); // display fullHeart
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
            heartColor = p.color(p.random(255), p.random(255), p.random(255)); // set heart colour to random of 255
            beatSize = beatMaxSize; // sets the size of the heart beat to the maximum size
        }
        beatSize *= beatDecay; // update size of heart by multiplying beatDecay to reduce size
    }
 
    float beatSpeed = 0.5f;
    float threshold = 50;
    private float beatMinSize;
 
    // method that draws full heart
    public void fullHeart()
    {
        float heartSize = v.map(v.getAmplitude(), 0, 1, 50, 200) * 8; // calculate the size of the heart based on left level channel of audio, from 0-1 to range of 50-200, multiply by 8
 
        p.strokeWeight(4);
        p.stroke(255, 0, 0);
        p.fill(255, 0, 0);
 
        float bezierOffset = heartSize/6; // the spacing between the bezier curves
 
        p.beginShape(); // begin drawing shape
        p.vertex(p.width/2, p.height/2 + heartSize/2);
        p.bezierVertex(p.width/4 - heartSize/2, p.height/2 - bezierOffset - heartSize/80, p.width/2 - heartSize/80, p.height/2 - heartSize/2 + bezierOffset, p.width/2, p.height/2 - heartSize/80); // left heart
        p.bezierVertex(p.width/2 + heartSize/80, p.height/2 - heartSize/2 + bezierOffset, p.width/1.35f + heartSize/2, p.height/2 - bezierOffset - heartSize/80, p.width/2, p.height/2 + heartSize/2); // right heart
        p.endShape(); // end of drawing shape
        p.noStroke();
        p.fill(255, 255, 255);
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
        p.stop(); // stop process in the superclass's stop()
    }
}