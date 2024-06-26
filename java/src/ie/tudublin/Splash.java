package ie.tudublin;

public class Splash extends Poly
{
    int t = 0;
    int duration = 1000;
    public Splash(Visual v) {
        super(v);
        //TODO Auto-generated constructor stub
    }

    public void enter()
    {
        v.showConsole = false;
        t = v.millis();
    }

    public void exit()
    {
        v.showConsole = true;
        v.startPoly = null;
    }

    public void render()
    {
        v.pushStyle();
        int d = v.millis() - t;
        if (d > duration)
        {
            v.showConsole = true;
            v.startPoly = null;
            v.change(0);
            return;
        }
        
        v.colorMode(v.RGB);
        v.background(v.bgColor);
        v.fill(255);
        v.ald = 0;
        v.textAlign(v.CENTER, v.CENTER);
        v.textSize(40);
        v.text("MSX system", v.width / 2, 470);
        v.text("version 1.0", v.width / 2, 530);
        v.text("Copyright 1983 by Microsoft", v.width / 2, 590);
        v.popStyle();
    }
}