package ie.tudublin;

public class Spirals extends Art {

    float cx, cy, x, y, r, px, py, theta;
    float c = 0;

    float rInc, thetaInc;
    float dir = 1;


    public Spirals(Visual v) {
        super(v);

    }

    public void render(int frameCount)
    { 
        //v.rotateX(v.pit);
        //v.rotateY(v.yaw);
        //v.rotateZ(v.rol);
        v.strokeWeight(2);
        x = cx + v.sin(theta) * r * v.getSmoothedAmplitude() * 0.5f;
        y = cy + (v.cos(theta) * dir) * r * v.getSmoothedAmplitude() * 0.5f;
        v.stroke(v.hueShift(c), v.sat, 255);
        v.rotateX(v.pit * 0.5f);
        v.rotateY(v.yaw * 0.5f);
        v.rotateZ(v.rol * 0.5f);
        v.line(px, py, x, y);
        if (frameCount % 2 == 0)
        {
            r += rInc;
            theta += thetaInc;
            c += 1f;
            px = x;
            py = y;
        }
        
        if (r >= 700)
        {
            enter();
        }
        

        
        //noFill();
        //circle(x, y, 5);
        
        // v.fill(0);
        // v.noStroke();
        // v.rect(5, 5, 600, 500);
        // v.fill(255);
        // v.textSize(50);
    }
    
    public void enter()
    {
        cx = v.width / 2;
        cy = v.height / 2;
        px = cx;
        py = cy;
        r = 5;
        rInc = v.random(2, 10);
        thetaInc = v.random(1, 5);
        dir = ((int) v.random(0, 2)) == 0 ? 1 : -1;
    }

    public void exit()
    {
    }

}
