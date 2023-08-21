package ie.tudublin;

import processing.core.PApplet;

public class Spiral2 extends Poly{

    float xoff;
    float theta;
    float radius;

    public Spiral2(AudioGarden v){
        super(v);
    }

    public void render(int ellapsed){
        for(int i = 0; i < v.getAudioBuffer().size();i++){

            v.pushMatrix();
            v.noFill();
            float c = PApplet.map(i, 0, v.getAudioBuffer().size() , 0, 255);
            float n = v.noise(xoff) * v.width;
            xoff = v.getSmoothedAmplitude() *3;
            v.stroke(v.hueShift(c),255,255);
            theta = PApplet.map(i, 0, v.getAudioBuffer().size(),0 , PApplet.TWO_PI*15);
            radius = PApplet.map(i, 0, v.getAudioBuffer().size(), 100, 1000);
            float x = PApplet.sin(theta) * radius; // calculates the x co-ordinate of the next circle within the spiral
            float y = PApplet.cos(theta) * radius; // calculates the y co-ordinate of the next circle within the spiral
            v.circle(x + v.width/2, y + v.height / 2, n);
            //v.translate(x + v.width/2, y + v.height/2);
            //v.rotate(75);
            //v.box(n);
            v.popMatrix();

        }  
         

    }
    
}
