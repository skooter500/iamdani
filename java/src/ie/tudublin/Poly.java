package ie.tudublin;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public abstract class Poly {
    public AudioGarden v;


    public Poly(Visual v){
        this.v = (AudioGarden) v;
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


