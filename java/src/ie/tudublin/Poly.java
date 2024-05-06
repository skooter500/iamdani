package ie.tudublin;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public abstract class Poly {
    public IAMDANI v;


    public Poly(Visual v){
        this.v = (IAMDANI) v;
    } 

    

    public void render(int ellapsed)
    {
        render();
    }

    public void enter()
    {
    }

    public void exit()
    {
    }

    public void render()
    {
    }
}


