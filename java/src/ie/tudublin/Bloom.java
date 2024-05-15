package ie.tudublin;

import processing.core.PApplet;

public class Bloom extends Poly{

    float angle;

    public Bloom(IAMDANI v){
        super(v);
    }

    @Override
    public void render(int frameCount) {
        //v.background(0);
        //v.strokeWeight(15); //different thicknesses of rect produce different resulting flower-esque patterns
        float cRange;//colour range
        v.noStroke();
        v.translate(v.width/2, v.height/2); //draws at the centre of the screen
        v.rotateX(v.pit);
        v.rotateY(v.yaw);
        v.rotateZ(v.rol);
        v.pushMatrix();
        for(int i = 0; i < 400; i++){ //400 is number of squares on screen at any moment
            // if(v.beat.isKick()){// changes the range of colours being mapped based on if there is a kick in the song
            //     cRange = 3000;
            //   }
            //   else{
            //     cRange = 5000;
            //   }
            //v.fill(v.getAudioBuffer().get(i)*i*i,10,255);
            //v.fill(v.getAudioBuffer().get(i)*i*i,50,100);
            //v.fill(v.getAudioBuffer().get(i)*i*i,v.getAudioBuffer().get(i)*i*i*i,v.getAudioBuffer().get(i)*i*i);
            
            cRange = 3000;
            float c2 = v.getAudioBuffer().get(i)*i*i;//allows for synced color change at centre of bloom creating a pistil-esque pattern
            float c = PApplet.map(i,0, v.getAudioBuffer().size() , 0, cRange);

            c = v.hueShift(c);
            c2 = v.hueShift(c2);

            v.fill(c,255,255, v.alp);//allows for darker edges of the flower creating an almost 3d shape
            v.scale(0.95f);//scales the shape by the percentage given
            v.rotate(PApplet.radians(angle));
        
            v.rect(0,0,v.getSmoothedAmplitude()*700,v.getSmoothedAmplitude()*700);
            v.fill(c2,255,255, v.alp);//allows for darker edges of the flower creating an almost 3d shape            
            v.rect(0,0,v.getSmoothedAmplitude()*500,v.getSmoothedAmplitude()*500);
           
            
            
        }
        v.popMatrix();
        
        angle+=(PApplet.map(v.getAmplitude(), 0, 1.0f, 0, 1f) * (v.spe / 100.0f));
    }
    
}
