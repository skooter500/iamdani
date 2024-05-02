package C22398106;

import ie.tudublin.Poly;
import ie.tudublin.Visual;
import processing.core.PApplet;

public class circles extends Poly
{
    public circles(Visual v)
    { 
        super(v);
    }

    @Override
    public void render(){

        float[] lerpedBuffer;
        lerpedBuffer = new float[v.width];

        float sum = 0;
        
        for(int i = 0 ; i < v.ab.size() ; i ++)
        {
            sum += PApplet.abs(v.ab.get(i));
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], v.ab.get(i), 0.05f);
        }
        float average= sum / (float) v.ab.size();
        float smooth = 0;
        smooth = PApplet.lerp(smooth, average, 0.1f);

        v.strokeWeight(1);
        v.colorMode(PApplet.HSB);
        v.background(150);
        v.stroke(255);
        for (int i = 0; i < v.width + 25; i += 25) {
            for (int j = 0; j < v.height + 25; j += 25)
            {
                float hue = PApplet.map(i, 0, v.ab.size() + (v.width/5), 0, 256);
                v.fill(hue, 255, 255);
                v.stroke(15);
                v.circle(i, j, (v.width / 2) * smooth);
            }
        }
    }
}