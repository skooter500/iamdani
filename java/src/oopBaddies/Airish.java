package oopBaddies;

import ie.tudublin.IAMDANI;
import ie.tudublin.Poly;
import ie.tudublin.Visual;
//import processing.core.PVector;
//import processing.core.PVector;

//main class
public class Airish extends Poly {

    // constructor

    public Airish(IAMDANI v) {
        super(v);
    }

    // declaring variables
    int i = 1;
    int j = 1;
    int n = 300;
    int a = 1;

    public void render() {

        
        float avg = v.getAmplitude();
        
        // make colours to music
        float c = v.map(avg, -1, 1, 0, 255);

       
        v.stroke(v.hueShift(c + 128), 255, 255, v.alp);
        // fill the middle circles in white
        v.fill(v.hueShift(66), 255, 255);
        // if a is 1 meaning this will only appear if user presses key that allows to
        // display this drwaing
        if (a == 1) {
            // circle must be first as it will interrupt with drawing other shapes
            // draws the centre pink circle of the shape
            v.fill(v.hueShift(9), 255, 255, v.alp);
            v.ellipse(250, 250, 100, 100);

            v.fill(v.hueShift(173), 255, 255, v.alp);
            v.ellipse(175, 175, 50, 50);
            v.triangle(175, 200, 250, 250, 200, 175);

            v.ellipse(250, 150, 50, 50);
            v.triangle(255, 150, 250, 250, 275, 150);

            v.ellipse(325, 175, 50, 50);
            v.triangle(300, 175, 250, 250, 325, 200);

            v.ellipse(150, 250, 50, 50);
            v.triangle(150, 255, 250, 250, 150, 275);

            v.ellipse(350, 250, 50, 50);
            v.triangle(350, 225, 250, 250, 350, 275);

            v.ellipse(175, 325, 50, 50);
            v.triangle(175, 300, 250, 250, 200, 325);

            v.ellipse(250, 350, 50, 50);
            v.triangle(225, 350, 250, 250, 275, 350);

            v.ellipse(325, 325, 50, 50);
            v.triangle(300, 325, 250, 250, 325, 300);

        } // end if

        if (a >= 3) {
            for (int i = -250; i < 1600; i += 250) {
                for (j = -250; j < 900; j += 250) {
                    v.fill(v.hueShift(193), 255, 255, v.alp);
                    v.ellipse(250 + i, 250 + j, 100, 100);
                    v.fill(v.hueShift(c), 255, 255, v.alp);
                    v.ellipse(175 + i, 175 + j, 50, 50);
                    v.triangle(175 + i, 200 + j, 250 + i, 250 + j, 200 + i, 175 + j);
                    v.ellipse(250 + i, 150 + j, 50, 50);
                    v.triangle(225 + i, 150 + j, 250 + i, 250 + j, 275 + i, 150 + j);

                    v.ellipse(325 + i, 175 + j, 50, 50);
                    v.triangle(300 + i, 175 + j, 250 + i, 250 + j, 325 + i, 200 + j);

                    v.ellipse(150 + i, 250 + j, 50, 50);
                    v.triangle(150 + i, 225 + j, 250 + i, 250 + j, 150 + i, 275 + j);

                    v.ellipse(350 + i, 250 + j, 50, 50);
                    v.triangle(350 + i, 225 + j, 250 + i, 250 + j, 350 + i, 275 + j);

                    v.ellipse(175 + i, 325 + j, 50, 50);
                    v.triangle(175 + i, 300 + j, 250 + i, 250 + j, 200 + i, 325 + j);

                    v.ellipse(250 + i, 350 + j, 50, 50);
                    v.triangle(225 + i, 350 + j, 250 + i, 250 + j, 275 + i, 350 + j);

                    v.ellipse(325 + i, 325 + j, 50, 50);
                    v.triangle(300 + i, 325 + j, 250 + i, 250 + j, 325 + i, 300 + j);

                    v.fill(0);
                    v.ellipse(250 + i, 250 + j, n, n);

                } // end for

            } // end for

        } // end if

        if (a >= 2) {
            for (i = -125; i < 1700; i += 250) {
                for (j = -125; j < 900; j += 250) {
                    v.fill(v.hueShift(93), 255, 255);
                    v.ellipse(250 + i, 250 + j, 100, 100);
                    v.fill(v.hueShift(13), 255, 255);

                    v.ellipse(175 + i, 175 + j, 50, 50);
                    v.triangle(175 + i, 200 + j, 250 + i, 250 + j, 200 + i, 175 + j);

                    v.ellipse(250 + i, 150 + j, 50, 50);
                    v.triangle(225 + i, 150 + j, 250 + i, 250 + j, 275 + i, 150 + j);

                    v.ellipse(325 + i, 175 + j, 50, 50);
                    v.triangle(300 + i, 175 + j, 250 + i, 250 + j, 325 + i, 200 + j);

                    v.ellipse(150 + i, 250 + j, 50, 50);
                    v.triangle(150 + i, 225 + j, 250 + 1, 250 + j, 150 + 1, 275 + j);

                    v.ellipse(350 + i, 250 + j, 50, 50);
                    v.triangle(350 + i, 225 + j, 250 + i, 250 + j, 350 + i, 275 + j);

                    v.ellipse(175 + i, 325 + j, 50, 50);
                    v.triangle(175 + i, 300 + j, 250 + i, 250 + j, 200 + i, 325 + j);

                    v.ellipse(250 + i, 350 + j, 50, 50);
                    v.triangle(225 + i, 350 + j, 250 + i, 250 + j, 275 + i, 350 + j);

                    v.ellipse(325 + i, 325 + j, 50, 50);
                    v.triangle(300 + i, 325 + j, 250 + i, 250 + j, 325 + i, 300 + j);

                } // end for

            } // end for

        } // end if

        // draw butterfly
        v.stroke(v.hueShift(6), 255, 255);
        v.strokeWeight(2);

        
        v.strokeWeight(3);
        v.fill(v.hueShift(c), 255, 255);
        v.quad(v.mouseX - 60, v.mouseY - 70, v.mouseX - 10, v.mouseY - 50, v.mouseX,
                v.mouseY, v.mouseX - 40, v.mouseY - 10);
        v.quad(v.mouseX + 60, v.mouseY - 70, v.mouseX + 10, v.mouseY - 50, v.mouseX,
                v.mouseY, v.mouseX + 40, v.mouseY - 10);
        v.fill(v.hueShift(c), 255, 255);
        v.quad(v.mouseX - 60, v.mouseY + 40, v.mouseX - 40, v.mouseY, v.mouseX,
                v.mouseY, v.mouseX - 18, v.mouseY + 30);
        v.quad(v.mouseX + 60, v.mouseY + 40, v.mouseX + 40, v.mouseY, v.mouseX,
                v.mouseY, v.mouseX + 10, v.mouseY + 30);

        // moving background
        n -= 0.1f;
        if (n == -0.5f) {
            n = 1;
        }

        //
        a++;
    }// end render()

}// end main class


//Attempt of a recursive factoral tree
//TREE attempt, unfortunately couldn’t get it to run
//public void branches(double l, int depth)
    //{
        //if(l < 2 || depth > 10)
        //{
            //return;
       // }
        //v.pushMatrix();
          //v.rotate((float) angle*random(0.8,1.2));
          //v.strokeWeight((float) (l * 0.2));
          //v.line(0,0,(float) l,0);
           //v.translate((float) l,0);
           //branches(l*reduction*random(0.8,1.2),depth + 1);
          //v.popMatrix();
          //v.pushMatrix();
          //v.rotate((float)-angle*random(0.8,1.2));
          //v.strokeWeight((float) (l * 0.2));
          //v.line(0,0,(float) l,0);
          //v.translate((float) l,0);
          //branches(l*reduction*random(0.8,1.2), depth + 1);
        //v.popMatrix();
          

    //}


    //private float random(double d, double e) {
           //return 0;
       //}
   
       //public void trunk()
       //{
           //v.strokeWeight((float)(len*0.2));
           //v.stroke(0);
           //v.line(0,0,len,0);
           //v.pushMatrix();
              //v.translate(len,0);
              //branches(len*reduction, 0);
           //v.popMatrix();
       //}
   
       //public void display(float x, float y)
       //{
           //v.pushMatrix();
             //v.translate(x, y);
             //v.rotate((float)(-0.5*PI));
             //trunk();
           //v.popMatrix();
   
   
      // }
   
    
      // void render()
       //{
   
           //calulating average
           //float avg = 0;
          // for (int i = 0; i < ab.size(); i++)
           //{
               //avg += abs(ab.get(i));
           //}
           //avg = avg / ab.size();
           //float smoothedavg = 0;
           //smoothedavg = lerp(smoothedavg, avg, 0.1f);
   
           //make colours to music
           //float c = map(avg, -1, 1, 0, 255);
   
           //v.background(0);//black background
   
           //v.size(640, 360);
           //v.frameRate(30);
           
   
          //draw butterfly
          //v.stroke (255) ;
          //v.strokeWeight(2);
       
          //v.stroke (255) ;
          //v.strokeWeight(3);
          //v.fill(c, 255, 255);
          //v.quad (v.mouseX-60, v.mouseY-70, v.mouseX-10, v.mouseY-50,v.mouseX,v.mouseY,v.mouseX-40,v.mouseY-10);
          //v.quad (v.mouseX+60, v.mouseY-70, v.mouseX+10, v.mouseY-50, v.mouseX, v.mouseY, v.mouseX+40, v.mouseY-10);
          //v.fill (c, 255, 255);
          //v.quad (v.mouseX-60, v.mouseY+40, v.mouseX-40,v.mouseY, v.mouseX, v.mouseY, v.mouseX-18, v.mouseY+30);
          //v.quad (v.mouseX+60, v.mouseY+40, v.mouseX+40,v.mouseY, v.mouseX, v.mouseY, v.mouseX+10,v.mouseY+30);
   
           //branches
           //branches(angle, a);
          
           //trunk of tree
           //trunk();
   
           //display
           //display(c, c);
   
   
   
          
           
       
           //moving the background 
           //n-=0.1f;
           //if(n==-0.5f)
           //{
               //n = 1;
   
           //}//end if
           
           //
           //a++;
   
           //drawing tree
           //calls the branch() to keep drawing it again
           //branch(120);
   
   
           //call branch function
           //branch(120);
       
           
       //}//end render()
       
   
   
   
   
           
   