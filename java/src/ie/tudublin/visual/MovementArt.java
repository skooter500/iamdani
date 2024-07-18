package ie.tudublin.visual;

import ie.tudublin.Art;

public class MovementArt extends Art {

    public MovementArt(Visual v) {

        super(v);
        //TODO Auto-generated constructor stub
    }

    public void render(int ellapsed)
    { 
        float x = v.map(v.yaw, 0, v.TWO_PI, 0, v.width);
        float y = v.map(v.rol, v.TWO_PI, 0.0f, 0, v.height);
        float z = v.map(v.pit, 0, v.TWO_PI, 0, v.height);        
        v. translate(x, y, z);
        // v.translate(v.width / 2, v.height / 2);
        v.strokeWeight(2);
        float c = v.map(x + y + z, 0, v.TWO_PI * 3.0f, 0, 255);
        c = v.hueShift(c);
        v.stroke(c, 255, 255, v.alp);
        
        v.noFill();
        v.rotateY(v.spe * .05f);
        v.sphere(50 * v.getSmoothedAmplitude());
        
    }
    
    public void enter()
    {
    }

    public void exit()
    {
    }

}
