package ie.tudublin;

public class FlippedWaveform1 extends Art {

    public FlippedWaveform1(Visual v) {

        super(v);
        //TODO Auto-generated constructor stub
    }

    public void render(int ellapsed)
    { 
        float off = 10;
        float bs = v.ab.size()/10;
        float offset = 0;
        v.strokeWeight(2);
        for (int i = 0; i < bs; i ++)
        {
        float sample = v.smoothedBuffer[i * 4] * v.halfH * v.getSmoothedAmplitude() * 5.0f;
        v.stroke(v.map(i, 0, bs, 0, 255), 255, 255, v.alp);
        //line(i, halfH + sample, i, halfH - sample); 

        //v.smoothedBuffer[i] = v.lerp(v.smoothedBuffer[i], v.smoothedBuffer[i], 0.01f);

        sample = v.smoothedBuffer[i * (int) off] * v.width * v.mul *.03f;    
        if (i < v.smoothedBuffer.length/4)
        {
            float c = v.map((i+offset) % bs, 0, bs / 2, 0, 127) % 255; 
            v.stroke(v.hueShift(c), 255, 255, v.alp);
        }
        else
        {
            float c = v.map((v.abs(i-offset)) % bs, bs, v.smoothedBuffer.length/2, 255, 128);         
            v.stroke(v.hueShift(c), 255, 255, v.alp);
        }
        //offset += lerpedAverage * 0.01f;
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
