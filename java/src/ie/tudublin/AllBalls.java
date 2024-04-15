package ie.tudublin;

import processing.core.PApplet;

public class AllBalls extends Poly {
    
    Balls[] balls;
    int numBalls = 150;
    

    public AllBalls(IAMDANI v)
    {
        super(v);
        initialiseBalls();
    }

    public void initialiseBalls() {

        balls = new Balls[numBalls];
        for (int i = 0; i < numBalls; i++) {
            balls[i] = new Balls(i);
        }

    }

    @Override
    public void render(int ellapsed)
    {       
        
        v.translate(v.width/2, v.height/2);
        
        for(int i = 0; i < numBalls; i++) {
            balls[i].display(); 
            balls[i].update(); 
        }

    }

    class Balls
    {   
        float angle;
        float angleSpeed;
        float radius;
        float count = 0;

        Balls(int n) {
            this.angle = 0;
            this.angleSpeed = 0.001f + n * 0.0005f;
            this.radius = 30 + n * 6;
            this.count = 0;
        }

        void update() {
            angle += angleSpeed;
        }

        void display() {
            //v.translate(v.width / 2, v.height / 2);

            v.lights();
            v.calculateAverageAmplitude();
            //v.stroke(PApplet.map(v.getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);

            
            float c = PApplet.map(count, 0, v.getAudioBuffer().size() , 100, 400);
            float x = radius * PApplet.cos(angle);
            float y = radius * PApplet.sin(angle);

            

            v.noStroke();
            //v.fill(255);
            v.fill(v.hueShift(c), 255, 255, v.alp);
            
            v.translate(x, y);
            v.rotateX(v.pit);
            v.rotateZ(v.yaw);
            float sphereSize = 25 + (v.getSmoothedAmplitude()); 
            v.sphere(sphereSize);

            count+=5;
        }
    }

}
