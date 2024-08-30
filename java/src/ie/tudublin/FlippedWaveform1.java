package ie.tudublin;

public class FlippedWaveform1 extends Art {

    public FlippedWaveform1(Visual v) {

        super(v);
        //TODO Auto-generated constructor stub
    }

    public void render(int ellapsed)
    { 
        float bs = v.ab.size()/10;
        float offset = 0;
        v.strokeWeight(2);
        for (int i = 0; i < bs; i ++)
        {
        //line(i, halfH + sample, i, halfH - sample); 

        //v.smoothedBuffer[i] = v.lerp(v.smoothedBuffer[i], v.smoothedBuffer[i], 0.01f);

        float sample = v.smoothedBuffer[i * 4] * v.width * v.getSmoothedAmplitude() * 10.0f;    
        
        v.stroke(v.hueShift(v.map(i, 0, bs, 0, 255)), 255, 255, v.alp);
        
        float x = (int) v.map(i , 0, bs, v.halfWidth - v.halfDrawable, v.halfWidth + v.halfDrawable);
        float y = (int) v.map(i, 0, bs, 0, v.height);
        v.rotateX(v.pit * 0.5f);
        v.rotateY(v.yaw * 0.5f);
        v.rotateZ(v.rol * 0.5f);
        
        v.line(x, v.height / 2 - sample, v.width/2 + sample, y); 

        // float y1 = v.height / 2 + sample;
        float r = 6;
        v.circle(x, v.height / 2 - sample + (r / 2), r);
        v.circle(v.width / 2 + (sample + (r / 2)), y, r);
        
        }
        

    }

    
    public void enter()
    {
        v.cqz = 1;
        v.targetCqz = 1;
    }

    public void exit()
    {
    }

}
