package C21733731;

import C21503599.MyFirstChange;
import example.MyVisual;
import ie.tudublin.Visual;
import processing.core.PApplet;
import processing.core.PImage;


public class Assignment{
    MyVisual mv;
    PImage unicorn; // Declare a PImage variable called unicorn to hold an image
    Explode glitter[]; // Declare an array in Explode class called glitter
    int ga=8; // glitter amount = 8

    public Assignment(MyVisual mv)
    {
        this.mv = mv;
        unicorn = mv.loadImage("unicorn.jpg");
        mv.colorMode(PApplet.HSB);

        glitter = new Explode[ga];

        // Loop through the glitter array
        for(int i=0; i<ga; i++){ 
            glitter[i] = new Explode((PApplet)this.mv, 13500+i*2000);
        }


    }

    float smoothedBoxSize = 0;

    public void draw()
    {
        mv.background(0);
        mv.image(unicorn, 0, 0, mv.displayWidth, mv.displayHeight);
        mv.noFill();
        mv.lights();
        mv.stroke(PApplet.map(mv.getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255); // Set the stroke color based on the smoothed amplitude of the audio
        //translate(0, 0, -250);
        mv.translate(mv.width/2, mv.height/2, 500);
               
        float boxSize = 100 + (mv.getAmplitude() * 300); // Calculate the size of the box based on the amplitude of the audio
        smoothedBoxSize = PApplet.lerp(smoothedBoxSize, boxSize, 0.45f); // Smoothly interpolate the box size over time

        
        
        //box(300);

        
        mv.rotateX(angle); 
        mv.rotateY(angle);

        mv.stroke(140, 255, 255);         
        mv.strokeWeight(10);
        mv.point(0, 0);
        mv.stroke(130, 255, 255);         
        mv.strokeWeight(5);
        mv.fill(140, 255, 255, 60);
        mv.box(smoothedBoxSize);

        mv.rotateY(-2*angle);
        mv.rotateX(-2*angle);
        mv.strokeWeight(2);
        mv.fill(140, 255, 255, 70);
        for(int i=0; i<ga; i++){
            glitter[i].render();
        }


        
    
        angle += 0.01f;
    

    }
    float angle = 0;
}

