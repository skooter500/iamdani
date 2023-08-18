package ie.tudublin;

public abstract class Poly {
    Visual v;

    public Poly(Visual v){
        this.v = v;
    }

    public abstract void render();
}
