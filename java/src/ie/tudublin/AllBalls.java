// Author: Tommy Burke
// Student Number: C22737179
// Date: 19th April 2024

// Description: This is the AllBalls class that extends the Poly class. It creates
// an array of Balls and displays them on the screen. The balls rotate around the center
// of the screen and change colour based on the amplitude of the audio. The balls can also  
// be rotated using the pitch, yaw and roll variables controlled by a midi controller. The
// number of balls can also be changed using a midi controller. The size of the balls can also
// be changed.


package ie.tudublin;

import processing.core.PApplet;

public class AllBalls extends Poly {
    
    Balls[] balls; //This is an array of Balls
    int numBalls = 150; //This is the number of Balls
    

    public AllBalls(IAMDANI v)
    {
        super(v); //This is calling the constructor of the parent class
        initialiseBalls(); //This is calling the initialiseBalls method
    }
    
    //This is the initialiseBalls method
    public void initialiseBalls() {

        balls = new Balls[numBalls];
        for (int i = 0; i < numBalls; i++) {
            balls[i] = new Balls(i);
        }

    }


    @Override
    public void render(int ellapsed)
    {       
        //translate to the center of the screen
        v.translate(v.width/2, v.height/2);
        
        //This is a for loop that goes through the balls array
        //you could replace numBalls with a midi controlled variable here to change the number of balls
        for(int i = 0; i < numBalls; i++) {
            balls[i].display(); 
            balls[i].update(); 
        }

    }

    class Balls
    {   
        float angle;        //This is the angle of the ball
        float angleSpeed;   //This is the speed the angle changes
        float radius;       //This is the radius of orbit(How far the ball is from the center of the screen)
        float count = 0;    //This is used to change the colour of the ball in the hueShift method

        Balls(int n) {
            //This is the constructor of the Balls class
            //initialises the variables
            this.angle = 0;
            this.angleSpeed = 0.001f + (n-1) * 0.00005f;
            this.radius = 30 + n * 6;
            this.count = 0;
        }

        
        void update() {
            //updates the angle of the ball for each frame
            angle += (angleSpeed * v.spe * 0.5f);
        }

        //This is the display method that gets called from the render method
        void display() {

            v.lights();
            v.calculateAverageAmplitude();

            //This variable changes the colour of the balls
            float c = PApplet.map(count, 0, v.getAudioBuffer().size() , 100, 400);
            //This is the x and y position of the ball, based on its angle of rotation and distance from the center
            float x = radius * PApplet.cos(angle);
            float y = radius * PApplet.sin(angle);

            v.noStroke();
            //This is the colour of the ball, it changes based on the c variable
            v.fill(v.hueShift(c), 255, 255, v.alp);

            // Translate and rotate to the ball's position and orientation
            v.translate(x, y);

            //This is the rotation of the entire visual using the pitch, yaw and roll variables controlled by a midi controller
            v.rotateX(v.pit);
            v.rotateY(v.yaw);
            v.rotateZ(v.rol);
            //This is the size of the ball, it changes based on the amplitude of the audio
            float sphereSize = (v.getSmoothedAmplitude()*20);
            //This is the sphere that gets drawn
            v.sphere(sphereSize);
            //this constantly changes the count variable so the colour of the ball changes but can also be changed using the midi controller
            count+=5;
        }
    }

}
