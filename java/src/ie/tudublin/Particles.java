//Student: Loredana Bura C22370523

package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;

public class Particles extends Poly{
    

    int num = 200; //amount of particles
    Particle[] p = new Particle[num]; //array of particles

    public Particles(IAMDANI v){
        super(v);
        newParticles();
    }


    public void newParticles() {
        //loop through the array to create the particles
        for (int i = 0; i < num; i++) {
            p[i] = new Particle(new PVector(v.random(v.width), v.random(v.height)), 200);
        }
        v.stroke(255);
    }

    @Override
    public void render(int frameCount) {
        // Render particles

        // 
        v.translate(v.width/2, v.height/2);        

        v.rotateY(v.yaw);
        v.rotateX(v.pit);    
        v.rotateZ(v.rol);
        v.translate(-v.width/2, -v.height/2);        

        for (int i = 0; i < num; i++) {
            p[i].update(p, i);
        }
        
        // v.translate(v.width/2, v.height/2);
    }

    class Particle {

        PVector pos;
        PVector vel;

        float r;

        float spd = 5.2f;
        float max = 6;
        float chooseColour = v.random(2);

        Particle(PVector pos, float r) {
            this.pos = pos;
            this.r = r;
            
            vel = new PVector(v.random(-1, 1), v.random(-1, 1));
        }

        void update(Particle[] p, int i) {
            pos.add(PVector.mult(vel, v.spe * 0.01f + v.getSmoothedAmplitude() * 0.1f));
            v.strokeWeight(5);
            spd = v.spe;
            //if the position of x moves too far left, it will appear from the right side of the screen
            if (pos.x < -10){
                pos.x = v.width;
            } 

            //if the position of x moves too far right, it will appear from the left side of the screen
            if (pos.x > v.width + 10){
                pos.x = 0;
            }
            
            //if the position of x moves too far up the screen, it will appear from the bottom of the screen
            if (pos.y < -10) {
                pos.y = v.height;
            }

            //if the position of x moves too far down the screen, it will appear from the top of the screen
            if (pos.y > v.height + 10){
                pos.y = 0;
            } 

            //Particle moves in differnt directions without going off screen by using constrain()
            vel.x = v.constrain(vel.x + v.random(-spd, spd), -max, max);
            vel.y = v.constrain(vel.y + v.random(-spd, spd), -max, max);

            for (int j = i + 1; j < p.length; j++) {

                //finding the angle and distance of each particle from each other
                float ang = PApplet.atan2(pos.y - p[j].pos.y, pos.x - p[j].pos.x);
                float dist = pos.dist(p[j].pos);

                //if the distance is less than the radius specified before
                if (dist < r) {
                    if (chooseColour < 0.8) {
                        //setting blue colour
                        float blueHue = v.map(chooseColour, 0.5f, 1.0f, 50, 90);
                        v.stroke(v.hueShift(blueHue), 255, 255, v.alp);
                    } 
                    else {
                        //setting red colour
                        float redHue = v.map(chooseColour, 0, 0.5f, 200, 255);
                        v.stroke(v.hueShift(redHue), 255, 255, v.alp);  
                    }

                    //drawing the lines between the particles
                    //particles that are far away have thin lines
                    //paticles that are close have thicker lines
                    v.strokeWeight(v.map(dist, 0, r, 3, 0));
                    v.line(pos.x, pos.y, p[j].pos.x, p[j].pos.y);

                    //particles repelling each other based on the distance between them
                    //as particles get closer, there is more force pushing them apart
                    float force = v.map(dist, 0, r, 4, 0);
                    vel.x += force * PApplet.cos(ang);
                    vel.y += force * PApplet.sin(ang);
                }
            }

            v.noStroke();
            
            if (chooseColour < 0.8) {
                //setting blue colour
                float blueHue = v.map(chooseColour, 0.5f, 1.0f, 170, 175); 
                v.fill(v.hueShift(blueHue), 255, 255, v.alp);
                
            } 
            else {
                //setting red colour
                float redHue = v.map(chooseColour, 0, 0.5f, 0, 2); 
                v.fill(v.hueShift(redHue), 255, 255, v.alp);
            }
            
            v.ellipse(pos.x, pos.y, 5, 5);
        }

    }

    
}
