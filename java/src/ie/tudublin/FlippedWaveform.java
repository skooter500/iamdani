package ie.tudublin;

public class FlippedWaveform extends Art {

    public FlippedWaveform(Visual v) {
        super(v);
        //TODO Auto-generated constructor stub
    }

    
    public void render(int ellapsed)
    { 
        v.rotateX(v.pit);
        v.rotateY(v.yaw);
        v.rotateZ(v.rol);
        
        v.strokeWeight(2);
        float halfH = v.height / 2;
        float halfWidth, halfDrawable;
        halfWidth = halfDrawable = v.width / 2;
        float bs = v.ab.size()/10;
        for (int i = 0; i < bs; i ++)
        {
        
        v.stroke(v.hueShift(v.map(i, 0, bs, 0, 255)), 255, 255, v.alp);
        //line(i, halfH + sample, i, halfH - sample); 

        //v.smoothedBuffer[i] = v.lerp(v.smoothedBuffer[i], v.ab.get(i), 0.02f);

        float sample = v.smoothedBuffer[i * 4] * v.width * v.getSmoothedAmplitude() * 10.0f;    
        v.stroke(v.hueShift(v.map(i, 0, bs, 0, 255)), 255, 255, v.alp);
        float x = (int) v.map(i, 0, bs, halfWidth - halfDrawable, halfWidth + halfDrawable);

        v.rotateX(v.pit * 0.5f);
        v.rotateY(v.yaw * 0.5f);
        v.rotateZ(v.rol * 0.5f);
        

        v.line(x, v.height / 2 - sample, x, v.height/2 + sample); 
        float y = v.height / 2 + sample;
        float r = 6;
        v.circle(x, v.height / 2 + sample + (r / 2), r);
        v.circle(x, v.height / 2 - (sample + (r / 2)), r);
        }
    }
    
    public void enter()
    {
        
    }

    public void exit()
    {
    }

}
