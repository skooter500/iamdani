package ie.tudublin;

public abstract class Poly {
    AudioGarden v;

    public Poly(AudioGarden v){
        this.v = v;
    }

    public abstract void render();
}
