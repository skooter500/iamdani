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

    public void enter()
    {
        y = 1000;
    }

    // varaible for rocket
    int y = 1100;

    public void render()
    // start of the render
    {
        //calulate average
        float avg = v.getAmplitude();
       
        float smoothedavg = 0;
        smoothedavg = v.lerp(smoothedavg, avg, 0.1f);

        // varaible for the stars
        int w = 2000;
        int h = 900;

        //represent the stars
        v.fill(v.hueShift(27), 255, 255);
        v.noStroke();
        v.ellipse(v.random(w), v.random(h), 5, 5);

        float color = v.hueShift(217);
    
        // v.fill(color, 255, 255);
        // v.noStroke();

        // // create the moon and matching the beat of the moon
        // v.ellipse(v.width + 200, v.height + 100 , 300 + (smoothedavg*600) , 300 + (smoothedavg*600));

        // //draw a house
        // v.fill(v.hueShift(173), 255, 255);
        // v.rect(100, 600, 300, 300);

        // //draw the roof
        // v.fill(v.hueShift(144), 255, 255);
        // v.triangle(100, 600, 250, 400, 400, 600);


        // // draw the window 
        // v.fill(v.hueShift(6), 255, 255);
        // v.rect(150, 656, 40, 50); // making the first window
        // v.rect(300, 656, 40, 50); // making the second window

        // v.fill(v.hueShift(87), 255, 255);
        // v.rect(200, 800, 100, 200);

        // // draw the door 
        // v.fill(v.hueShift(13), 255, 255);
        // v.rect(200, 800, 30, 100);


        // // draw the grass
        // v.fill(v.hueShift(333), 255, 255);
        // v.noStroke(); // remove the stroke
        // v.rect(0, 900, 2000 ,120);     

         // draw a rocket
         v.translate(v.CENTER, v.CENTER);
         v.fill(v.hueShift(1737), 255, 255);
         v.triangle(950, y+100, 1000, y-10, 1050, y+100); // bottom of the rocket
        //an oval shape of the rocket and moving the rocket up
         v.ellipse(w/2, y,100,200);
         v.fill(v.hueShift(1773), 255, 255);
        // two circle windows on the rocket and moving the rocket up
        // the two cicrle also represent the beat of the music
         v.ellipse(w/2, y+10, 50+(smoothedavg*300), 50);
         v.ellipse(w/2, y-50, 40+(smoothedavg*200),40);
         v.fill(v.hueShift(66), 255, 255);
         v.ellipse(w/2, y+10, 40,40);
         v.ellipse(w/2, y-50, 30,30);
         v.ellipse(0, 80, 40, 20); // Draw the engine of the rocket
        
        // move the rocket
        y-= v.spe;

        if(y < 0){
            y = 1100;
        }

        // //draw a street lights near the house
        // v.translate(200, 595); // move the street light the bottom of the screen
        // v.fill(v.hueShift(17763), 255, 255);
        // v.rect(350, 100, 20, 200);
        // v.ellipse(360, 90, 40+ (smoothedavg*200), 40); // make the circle of the light which will match the beat of the music
        
        // v.fill(v.hueShift(1723), 255, 255);
        // v.ellipse(360, 90, 20+ (smoothedavg*300), 20); // make the circle of the light which will match the beat of the music
        // v.strokeWeight(3);
        // v.fill(v.hueShift(3333), 255, 255);
        // v.line(345, 90, 375, 90);

        // v.fill(v.hueShift(13), 255, 255);
        // v.noStroke(); 
  
        // // Draw the pond
        // v.ellipse(1000, 400, 450+ (smoothedavg*600), 200); // draw the pond of the water

        // v.fill(v.hueShift(172223), 255, 255);
        // v.ellipse(750, 350, 30+(smoothedavg*600) , 30); // draw a small rock
        // v.ellipse(1250, 350, 40+ (smoothedavg*600), 40); // draw a larger rock
        // v.ellipse(1400, 380, 60+ (smoothedavg*600), 60); // draw a medium rock
        
        // // drawing a bigger version of the street light
        // v.fill(v.hueShift(87), 255, 255);
        // v.rect(1500, 100, 20, 200); // draw the pole of the street light
        
        // // drawing the head of the street light
        // v.fill(v.hueShift(1223), 255, 255);
        // v.ellipse(1500, 80, 80+(smoothedavg*200), 80); // draw the head of the street light
        
        // // Draw the lamp light
        // v.fill(v.hueShift(123), 255, 255);
        // v.ellipse(1500, 80, 50+(smoothedavg*200), 50);
        // v.stroke(255, 255, 204); // set the color stroke to yellow

 
    } // end of the render





} //end main