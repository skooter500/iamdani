package ie.tudublin;

import processing.core.PApplet;

public class SinWaves extends Poly{

    public SinWaves(AudioGarden v) {
        super(v);
       
    }

    float angle = 0.0f;


    @Override
    public void render() {
        

        // v.background(0); 
        float boxSize;

    
        
        v.translate(v.width / 2, v.height / 2, 0);//centre the display
        //v.rotateX(v.radians(v.frameCount));
        //v.rotateY(v.radians(v.frameCount));
        //v.rotateZ(v.radians(v.frameCount));

        for (int x = -10; x < 10; x++) {
            for (int y = -10; y < 10; y++) {
            for (int z = -10; z < 10; z++) {//creates a 20X20X20 cube 
                float offsetX = x * 20 *(v.getSmoothedAmplitude()*12);//value increasing/decreasing distance between cubes on x axis
                float offsetY = y * 20 *(v.getSmoothedAmplitude()*12);//value increasing/decreasing distance between cubes on y axis
                float offsetZ = z * 20 *(v.getSmoothedAmplitude()*12);//value increasing/decreasing distance between cubes on z axis
                //float offsetX = x * 40;
                //float offsetY = y * 40;
                //float offsetZ = z * 40;
                float distance = PApplet.dist(0, 0, offsetX, offsetY);//calculates distance between the origin and the x and y offsets.
                //boxSize=20;
                boxSize = (PApplet.sin(PApplet.radians(angle + distance))+1) * 20;//edits box size along a sin wave continously to appear like its fading 
                v.pushMatrix();
                v.translate(offsetX, offsetY, offsetZ-400);
                v.fill((offsetX + v.frameCount) % 255, (offsetY + v.frameCount) % 255, (offsetZ + v.frameCount) % 255);
                v.noStroke();
                v.box(boxSize);
                v.popMatrix();
            }
            }
        }
        angle += v.getSmoothedAmplitude()*20;//syncs the sin wave change for box size with the amplitude of song.
        //angle+=2;
    }
    
}
