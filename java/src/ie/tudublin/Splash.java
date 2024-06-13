package ie.tudublin;

public class Splash extends Poly
{
    int t = 0;
    int duration = 5000;
    public Splash(Visual v) {
        super(v);
        //TODO Auto-generated constructor stub
    }

    public void render(int ellapsed)
    {
        render();
    }

    public void enter()
    {
        t = v.millis();
    }

    public void exit()
    {
    }

    public void render()
    {
        int d = v.millis() - t;
        if (d > duration)
        {
            v.change(0);
            return;
        }
        

        v.colorMode(v.RGB);
        v.background(v.bgColor, 255, 255);
        v.fill(255);
        v.ald = 0;
        v.textAlign(v.CENTER, v.CENTER);
        v.text("MSX system", v.width / 2, 500);
        v.text("version 1.0", v.width / 2, 530);
        v.text("Copyright 1983 by Microsoft", v.width / 2, 560);
        v.startPoly = null;
    }
}