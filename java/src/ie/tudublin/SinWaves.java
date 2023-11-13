package ie.tudublin;

import processing.core.PApplet;

public class SinWaves extends Poly{

    public SinWaves(AudioGarden v) {
        super(v);
       
    }

    float angle = 0.0f;


    @Override
    public void render(int ellapsed) {
        

        //v.background(0); 
        float boxSize;

    
        
        v.translate(v.width / 2, v.height / 2, 0);//centre the display

        v.rotateX(v.pit);
        v.rotateZ(v.yaw);

        float amp = v.getSmoothedAmplitude() * 0.8f;

        for (int x = -10; x < 10; x++) {
            for (int y = -10; y < 10; y++) {
            for (int z = -10; z < 10; z++) {//creates a 20X20X20 cube 
                float offsetX = x * 10 *(amp*11);//value increasing/decreasing distance between cubes on x axis
                float offsetY = y * 10 *(amp*11);//value increasing/decreasing distance between cubes on y axis
                float offsetZ = z * 10 *(amp*11);//value increasing/decreasing distance between cubes on z axis
                //float offsetX = x * 40;
                //float offsetY = y * 40;
                //float offsetZ = z * 40;
                float distance = PApplet.dist(0, 0, offsetX, offsetY);//calculates distance between the origin and the x and y offsets.
                //boxSize=20;
                boxSize = (PApplet.sin(PApplet.radians(angle + distance))+1) * 20;//edits box size along a sin wave continously to appear like its fading 
                v.pushMatrix();
                v.translate(offsetX, offsetY, offsetZ-400);
                float c = v.hueShift((offsetX + offsetY + offsetZ + v.frameCount) % 255);
                //p.fill(c, 255, 255);
                v.fill(c, 255, 255, v.alp);
                //v.stroke(v.hueShift(c + 128), 255, 255, v.alp);
                v.box(boxSize);
                v.popMatrix();
            }
            }
        }
        angle += amp*5 * v.spe;//syncs the sin wave change for box size with the amplitude of song.
        //angle+=2;
        // v.camera(v.width/2.0f, v.height/2.0f, (v.height/2.0f) / v.tan(v.PI*30.0f / 180.0f), v.width/2.0f, v.height/2.0f, 0f, 0f, 0.1f, 0f);  
    }

    
}
