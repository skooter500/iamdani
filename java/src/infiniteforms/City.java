package infiniteforms;


import ie.tudublin.AudioGarden;
import ie.tudublin.Poly;
import processing.core.PVector;

public class City extends Poly
{
    int rows = 100;
    float gap = 100;
    //Street[] streets;
    PVector pos;
    
    public City(AudioGarden v)
    {
        this(v, 0, 0, -250, 20, 100);
    }

    City(AudioGarden v, float x, float y, float z, int rows, float gap)
    {
        super(v);
        pos = new PVector(x, y, z);
        this.rows = rows;
        this.gap = gap;                
        cutoff = pos.z + (gap * toCut);
    }
    
    // Ugh Java
    PVector rowPos = new PVector(); 
        
    float smoothedBoxSize = 0;
    float camY = -100;
    
    float cutoff;
    int toCut = 5;
    
    public void render()
    {
        v.noFill();
        v.lights();    
        v.camera(0, camY, 0, 0, 0, -1000, 0, 1, 0);
        float fov = v.PI/3.0f;
        float cameraZ = (v.height/2.0f) / v.tan(fov/2.0f);
        v.perspective(fov, v.width/(float)v.height, 
            cameraZ/10.0f, cameraZ*100000.0f);
        v.pushMatrix();

        //println(camY);
        
        for(int row = 0 ; row < rows ; row ++)
        {
            rowPos.z = pos.z -(gap * row);
            //Street s = streets[row];
            int j = 0;
            float[] smoothedBands = v.getSmoothedBands();
            for(int i = smoothedBands.length - 1 ; i >=0 ; i --)
            {
                float base = 40;
                float boxSize = base + (smoothedBands[i] * 2); 
                v.strokeWeight(2);
                float c = v.hueShift(v.map(j, 0, smoothedBands.length, 255, 0));
                v.stroke(c, 255, 255);                
                v.pushMatrix();
                v.translate(rowPos.x + (j+1) * gap, rowPos.y - (boxSize * 0.5f), rowPos.z);
                v.box(base, boxSize, base);
                v.popMatrix();      

                v.pushMatrix();
                v.translate(rowPos.x - (j+1) * gap, rowPos.y  - (boxSize * 0.5f), rowPos.z);
                v.box(base, boxSize, base);
                v.popMatrix();      
                j ++;
            }
        }        
        v.popMatrix();
        pos.z += 10 * v.getAmplitude() * v.spe;
        if (pos.z >= cutoff)
        {
          pos.z = cutoff - gap;
        }
        v.camera(v.width/2.0f, v.height/2.0f, (v.height/2.0f) / v.tan(v.PI*30.0f / 180.0f), v.width/2.0f, v.height/2.0f, 0f, 0f, 1f, 0f);          
    }
}
