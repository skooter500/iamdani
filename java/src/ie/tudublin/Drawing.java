package ie.tudublin;

public class Drawing extends Art {

    public Drawing(Visual v) {
        super(v);
        //TODO Auto-generated constructor stub
    }

    public void render(int ellapsed)
    { 
        v.rotateX(v.pit);
        v.rotateY(v.yaw);
        v.rotateZ(v.rol);
        
        v.strokeWeight(2);
        float x = v.map(v.yaw, 0, v.TWO_PI, 0, v.width);
        float y = v.map(v.yaw, 0, v.TWO_PI, 0, v.width);
        float z = v.map(v.yaw, 0, v.TWO_PI, 0, v.width);

        float c = (x + y + z);
        c = v.map(c, 0.0f, v.TWO_PI * 3.0f, 0.0f, 255.0f);
    }
    
    public void enter()
    {
    }

    public void exit()
    {
    }

}
