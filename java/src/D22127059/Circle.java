//To create a circular wave form in the shape of a drum and surrounded by drum objects
//Kim McGrath
package D22127059;

import example.MyVisual;
import processing.core.PApplet;
import processing.core.PShape;


public class Circle
{

    int mode = 0;
    MyVisual mv;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
    PShape snare;
    PShape bass;

    
    float off = 0;

    float lerpedBuffer[];
    public Circle(MyVisual m){
        this.mv = m;
        smoothedY = y; 
        snare = mv.loadShape("snare.obj");
        bass = mv.loadShape("bass.obj");
        snare.scale(5);
        bass.scale(3);
        lerpedBuffer = new float[1024];
    }


    public void draw() {
        float halfH = mv.height / 2;
        float average = 0;
        float sum = 0;
        off += 1;
    
        for(int i = 0 ; i < mv.getAudioBuffer().size() ; i ++)
        {
            sum += PApplet.abs(mv.getAudioBuffer().get(i));
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], mv.getAudioBuffer().get(i), 0.1f);
        }

        average = sum / (float) mv.getAudioBuffer().size(); //calc average of audio buffer
        smoothedAmplitude = PApplet.lerp(smoothedAmplitude, average, 0.1f); //audio wave intensity
    
        float cx = mv.width / 2;
        float cy = mv.height / 2;
    
        float t = PApplet.TWO_PI / mv.getAudioBuffer().size();

        //inner circle waveform
        mv.beginShape();
        for(int i = 0 ; i < mv.getAudioBuffer().size() ; i ++)
        {
            float amplitude = lerpedBuffer[i] * halfH * 1.0f;
            float x = cx + PApplet.cos(t * i) * (halfH * 0.5f - amplitude);
            float y = cy + PApplet.sin(t * i) * (halfH * 0.5f - amplitude);
            mv.stroke(255);
            mv.strokeWeight(5);
            mv.vertex(x, y);
        }
        mv.endShape(PApplet.CLOSE);
    
    //outer grey ring
    mv.stroke(74); 
    mv.strokeWeight(8);
    float r2 = halfH * 0.5f;
    
    mv.beginShape();
    for(int i = 0 ; i < mv.getAudioBuffer().size() ; i ++)
    {
        float amplitude = lerpedBuffer[i] * halfH * .0f;
        float x = cx + PApplet.cos(t * i) * (r2 - amplitude);
        float y = cy + PApplet.sin(t * i) * (r2 - amplitude);
        mv.vertex(x, y);
    }
    mv.endShape(PApplet.CLOSE);  


    mv.lights(); //detailing on objects
        
    //top right snare
    mv.pushMatrix();
    mv.translate(mv.width/2 + 350, mv.height/2 - 350);
    mv.rotateX(smoothedAmplitude * 5); //Rotate on beat around point
    mv.shape(snare);
    mv.popMatrix();

    //top left bass
    mv.pushMatrix();
    mv.translate(mv.width/2 - 350, mv.height/2 - 350);
    mv.rotateX(smoothedAmplitude * 1); //Add a sort of punch in X axis
    mv.rotateZ(smoothedAmplitude * 20); //Spin on beat
    mv.shape(bass);
    mv.popMatrix();

    //bottom right bass
    mv.pushMatrix();
    mv.translate(mv.width/2 + 350, mv.height/2 + 350);
    mv.rotateX(smoothedAmplitude * 1);
    mv.rotateZ(smoothedAmplitude * 20);
    mv.shape(bass);
    mv.popMatrix();

    //bottom left snare
    mv.pushMatrix();
    mv.translate(mv.width/2 - 350, mv.height/2 + 350);
    mv.rotateX(smoothedAmplitude * 5);
    mv.shape(snare);
    mv.popMatrix();
    }
}

