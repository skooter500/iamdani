package C21733731;

import processing.core.PApplet;
import processing.core.PVector;

public class cubes {
    PApplet p; //stores a reference to the main applet
    PVector position; //store the position of the cubes
    PVector velocity; //store the velocity of the cubes
    float size; //size of the cubes

    public cubes(PApplet p, PVector position, float size){
        this.p = p;
        this.position = position;
        this.size = size;

        velocity = new PVector(0, 0, 0);

    }
    public void render(){
        p.pushMatrix(); // Store the current transformation matrix
        p.translate(position.x, position.y, position.z);
        p.box(size); // Draw a box with the specified size
        p.popMatrix(); // Restore the previous transformation matrix
    }

    public void update(){
        position.add(velocity); // Add the velocity vector to the position vector to update the position of the cubes
    }

    public void antigravity(PVector gravPoint, float min, float max){
        PVector temp;

        temp = PVector.sub(position, gravPoint);
        temp.mult(0.06f); //speed
        velocity.set(temp);


    }

    public PVector getPosition(){
        return position;
    }


}
