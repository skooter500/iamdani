package oopBaddies;

import ie.tudublin.AudioGarden;
import ie.tudublin.Poly;
import ie.tudublin.Visual;

// start of the main
public class Mena extends Poly {

    //constructor
    public Mena(AudioGarden v)
    {
        super(v);

    }

    // varaible for rocket
    int y = 700;

    public void render()
    // start of the render
    {
        //calulate average
        float avg = v.getAmplitude();
       
        float smoothedavg = 0;
        smoothedavg = v.lerp(smoothedavg, avg, 0.1f);

        v.colorMode(v.RGB);

        // varaible for the stars
        int w = 2000;
        int h = 900;

        v.fill(0, 10);
        v.rect(0, 0 ,w , h);

        //represent the stars
        v.fill(255);
        v.noStroke();
        v.ellipse(v.random(w), v.random(h), 5, 5);

        int color = v.color (255); 
    
        v.fill(color);
        v.noStroke();

        // create the moon and matching the beat of the moon
        v.ellipse(v.width + 200, v.height + 100 , 300 + (smoothedavg*600) , 300 + (smoothedavg*600));

        //draw a house
        v.fill(173, 216, 230);
        v.rect(100, 600, 300, 300);

        //draw the roof
        v.fill(139, 69, 19); // color grey
        v.triangle(100, 600, 250, 400, 400, 600);


        // draw the window 
        v.fill(0, 0, 0); // color black
        v.rect(150, 656, 40, 50); // making the first window
        v.rect(300, 656, 40, 50); // making the second window

        v.fill(0, 0, 0); //
        v.rect(200, 800, 100, 200);

        // draw the door 
        v.fill(200, 200, 200);
        v.rect(200, 800, 30, 100);


        // draw the grass
        v.fill(80, 200, 120); // set the fill color to green
        v.noStroke(); // remove the stroke
        v.rect(0, 900, 2000 ,120);     

         // draw a rocket
         v.translate(v.CENTER, v.CENTER);
         v.fill(255);
         v.triangle(950, y+100, 1000, y-10, 1050, y+100); // bottom of the rocket
        //an oval shape of the rocket and moving the rocket up
         v.ellipse(w/2, y,100,200);
         v.fill(175, 100, 220);
        // two circle windows on the rocket and moving the rocket up
        // the two cicrle also represent the beat of the music
         v.ellipse(w/2, y+10, 50+(smoothedavg*300), 50);
         v.ellipse(w/2, y-50, 40+(smoothedavg*200),40);
         v.fill(0);
         v.ellipse(w/2, y+10, 40,40);
         v.ellipse(w/2, y-50, 30,30);
         v.ellipse(0, 80, 40, 20); // Draw the engine of the rocket
        
        // move the rocket
        y--;

        if(y < 0){
            y = 700;
        }

        //draw a street lights near the house
        v.translate(200, 595); // move the street light the bottom of the screen
        v.fill(200);
        v.rect(350, 100, 20, 200);
        v.ellipse(360, 90, 40+ (smoothedavg*200), 40); // make the circle of the light which will match the beat of the music
        
        v.fill(255, 240, 200);
        v.ellipse(360, 90, 20+ (smoothedavg*300), 20); // make the circle of the light which will match the beat of the music
        v.strokeWeight(3);
        v.stroke(255, 240, 200);
        v.line(345, 90, 375, 90);

        v.fill(0, 204, 204); // set the color the blue
        v.noStroke(); 
  
        // Draw the pond
        v.ellipse(1000, 400, 450+ (smoothedavg*600), 200); // draw the pond of the water

        v.fill(102, 102, 102); // Settting the  color to gray
        v.ellipse(750, 350, 30+(smoothedavg*600) , 30); // draw a small rock
        v.ellipse(1250, 350, 40+ (smoothedavg*600), 40); // draw a larger rock
        v.ellipse(1400, 380, 60+ (smoothedavg*600), 60); // draw a medium rock
        
        // drawing a bigger version of the street light
        v.fill(200); // setting the color to grey
        v.rect(1500, 100, 20, 200); // draw the pole of the street light
        
        // drawing the head of the street light
        v.fill(255, 255, 204); // set the color to yellow
        v.ellipse(1500, 80, 80+(smoothedavg*200), 80); // draw the head of the street light
        
        // Draw the lamp light
        v.fill(255, 255, 204, 200); // set the semi-circle to the color yellow
        v.ellipse(1500, 80, 50+(smoothedavg*200), 50);
        v.stroke(255, 255, 204); // set the color stroke to yellow

 
    } // end of the render





} //end main