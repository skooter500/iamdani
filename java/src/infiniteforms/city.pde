class City extends Vision
{
    int rows = 100;
    float gap = 100;
    //Street[] streets;
    PVector pos;
    
    City()
    {
      this(0, 0, -250, 20, 100);
    }

    City(float x, float y, float z, int rows, float gap)
    {
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
    
    void render()
    {
        background(0);
        noFill();
        lights();    
        camera(0, camY, 0, 0, 0, -1000, 0, 1, 0);
        float fov = PI/3.0;
        float cameraZ = (height/2.0) / tan(fov/2.0);
        perspective(fov, float(width)/float(height), 
            cameraZ/10.0, cameraZ*100000.0);
            pushMatrix();
        if (checkKey('i'))
        {
          camY -= 5;
        }
        //println(camY);
        
        for(int row = 0 ; row < rows ; row ++)
        {
            rowPos.z = pos.z -(gap * row);
            //Street s = streets[row];
            int j = 0;
            for(int i = smoothedBands.length - 1 ; i >=0 ; i --)
            {
                float base = 40;
                float boxSize = base + (smoothedBands[i] * 2); 
                strokeWeight(2);
                stroke(map(j, 0, smoothedBands.length, 255, 0), 255, 255);                
                pushMatrix();
                translate(rowPos.x + (j+1) * gap, rowPos.y - (boxSize * 0.5f), rowPos.z);
                box(base, boxSize, base);
                popMatrix();      

                pushMatrix();
                translate(rowPos.x - (j+1) * gap, rowPos.y  - (boxSize * 0.5f), rowPos.z);
                box(base, boxSize, base);
                popMatrix();      
                j ++;
            }
        }        
        popMatrix();
        pos.z += 10 * amplitude;
        if (pos.z >= cutoff)
        {
          pos.z = cutoff - gap;
        }
        camera(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0), width/2.0, height/2.0, 0, 0, 1, 0);          
    }
}
