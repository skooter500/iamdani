package ie.tudublin;
 
import ddf.minim.Minim;
 
public class ManarBrain extends Poly
{
    Minim m;
    Visual p;
 
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
 
    public ManarBrain(Visual p)
    {
        super(p);
        this.p = p;
    }
 
    public void render()
    {
        draw();
    }
   
    public void draw()
    {
        p = v;
        p.pushMatrix();//seperating drawBrain function from other functions
        drawBrain();
        p.popMatrix();
        drawCircles();//function to draw the circles
    }

    int brainHeight = 550;
    float rotationSpeed = 0.02f;
   
    float z = 0;
    public void drawBrain()
    {
        p.strokeWeight(8);
        p.stroke(p.hueShift(30), 255, 255, v.alp);//color + weight of the line
        p.translate(p.width/2 , brainHeight, 0);//position of the brain on the screen
        v.rotateX(v.pit);
        v.rotateY(v.yaw);
        v.rotateZ(v.rol);
        p.beginShape();
 
        p.rotateY(p.frameCount * rotationSpeed);//makes the brain drawing rotate on the Y axis
 
 
        // /p.translate(0, 0, z);
        //drawing the brain
        //centre
        p.line(-165, -80, -200, 0); //1
 
        p.line(-90, -120, -165, -80); //2
 
        p.line(10, -130, -90, -120); //3
 
        p.line(60, -125, 10, -130); //4
 
        p.line(110, -110, 60, -125); //5
 
        p.line(200, -40, 110, -110); //6
       
        p.line(230, 40, 200, -40); //7
 
        p.line(215, 70, 230, 40); //8
 
        p.line(190, 100, 215, 70); //9
 
        p.line(100, 90, 190, 100); //10
 
        p.line(110, 190, 100, 90); //11
 
        p.line(60, 95, 110, 190); //12
 
        p.line(-30, 115, 60, 95); //13
 
        p.line(-120, 110, -30, 115); //14
 
        p.line(-110, 65, -120, 110); //15
 
        p.line(-180, 70, -110, 65); //16
 
        p.line(-200, 0, -180,70);//17
 
        //2 second lines from the centre
        p.line(-145, -60, 40, -180, 0, 40); //1 right
        p.line(-145, -60, -40, -180, 0, -40); //1 left
        p.line(-70, -100, 40, -145, -60, 40); //2 right
        p.line(-70, -100, -40, -145, -60, -40); //2 left
        p.line(30, -110, 40, -70, -100, 40 ); //3 right
        p.line(30, -110, -40, -70, -100, -40 ); //3 left
        p.line(80, -105, 40, 30,-110, 40); //4 right
        p.line(80, -105, -40, 30,-110, -40); //4 left
        p.line(90, -90, 40, 80, -105, 40);//5 right
        p.line(90, -90, -40, 80, -105, -40);//5 left
        p.line(180, -20, 40, 90, -90, 40);// 6 right
        p.line(180, -20, -40, 90, -90, -40);// 6 left
        p.line(210, 60, 40, 180, -20, 40);//7 right
        p.line(210, 60, -40, 180, -20, -40);//7 left
        p.line(180, 70, 40, 210, 60, 40);//8 right
        p.line(180, 70, -40, 210, 60, -40);//8 right
        p.line(100,50,40, 180, 70, 40);//10 right
        p.line(100,50,-40, 180, 70, -40);//10 left
        p.line(90,110,40,100,50,40);//11 right
        p.line(90,110,-40,100,50,-40);//11 left
        p.line(40,70,40,100,50,40);//12 right
        p.line(40,70,-40,100,50,-40);//12 right
        p.line(-80,85,40,40,70,40);
        p.line(-80,85,-40,40,70,-40);
        p.line(-70,30,40,-80,85,40);
        p.line(-70,30,-40,-80,85,-40);
        p.line(-160,40,40,-70,30,40);
        p.line(-160,40,-40,-70,30,-40);
        p.line(-180,0,40,-160,40,40);
        p.line(-180,0,-40,-160,40,-40);
 
        p.line(-90,-50,80,-120, 0,80);//1 right
        p.line(-90,-50,-80,-120, 0,-80);//1 left
 
        p.line(-30,-70,80,-90,-50,80);
        p.line(-30,-70,-80,-90,-50,-80);
 
        p.line(30,-80,80,-30,-70,80);
        p.line(30,-80,-80,-30,-70,-80);
 
        p.line(160,0,80,30,-80,80);
        p.line(160,0,-80,30,-80,-80);
 
        p.line(175,40, 80,160,0,80);
        p.line(175,40, -80,160,0,-80);
 
        p.line(80,25,80,175,40,80);
        p.line(80,25,-80,175,40,-80);
 
        p.line(70,45,80,80,25,80);
        p.line(70,45,-80,80,25,-80);
 
        p.line(-40,55,80,70,45,80);
        p.line(-40,55,-80,70,45,-80);
 
        p.line(-30,15,80,-40,55,80);
        p.line(-30,15,-80,-40,55,-80);
 
        p.line(-120,25,80,-30,15,80);
        p.line(-120,25,-80,-30,15,-80);
 
        p.line(-120, 0,80,-120,25,80);
        p.line(-120, 0,-80,-120,25,-80);
 
        //4 (connecting the lines together)
        p.line(-180,0,40,-165,-80,0);//1
        p.line(-180,0,-40,-165,-80,0);//1
 
        p.line(-180,0,40,-120, 0,80);
        p.line(-180,0,-40,-120, 0,-80);
        p.line(160,0,80, -120,0,80);
        p.line(160,0,-80, -120,0,-80);
 
        p.line(-200,0,0,-180,0,40);
        p.line(-200,0,0,-180,0,-40);
 
        p.line(-180,0,40,-180,70,0);
        p.line(-180,0,-40,-180,70,0);
 
        p.line(-180,70,0,-160,40,40);
        p.line(-180,70,0,-160,40,-40);
 
        p.line(-160,40,40,-110, 65,0);
        p.line(-160,40,-40,-110, 65,0);
 
        p.line(-70,30,40,-110,65,0);
        p.line(-70,30,-40,-110,65,0);
 
        p.line(200, -40,0,180, -20, 40);
        p.line(200, -40,0,180, -20, -40);
 
        p.line(215, 70,0,180, 70, 40);
        p.line(215, 70,0,180, 70, -40);
 
        p.line(230, 40,0,210, 60, 40);
        p.line(230, 40,0,210, 60, -40);//8
 
        p.line(190, 100,0,100,50,40);
        p.line(190, 100,0,100,50,-40);//9
 
        p. line(-180, 70,0,-120,25,80);
        p. line(-180, 70,0,-120,25,-80);
       
        p. line(180, 70, 40,70,45,80);
        p. line(180, 70, 40,70,45,-80);
       
        p. line(-145, -60, 40,-90,-50,80);
        p. line(-145, -60, -40,-90,-50,-80);
       
        p. line(-30,-70,80,-70, -100, 40);
        p. line(-30,-70,-80,-70, -100, -40);
       
        p. line(90, -90, 40,160,0,80);
        p. line(90, -90, -40,160,0,-80);
       
        p. line(90,-90,40,30,-80,80);
        p. line(90,-90,-40,30,-80,-80);
       
        p. line(-145,-60,40,-90,-120,0);//2
        p. line(-145,-60,-40,-90,-120,0);//2
       
        p. line(10,-130,0,30, -110,40);//3
        p. line(10,-130,0,30, -110,-40);//3
       
        p. line(60, -125,0,80, -105, 40);//4
        p. line(60, -125,0,80, -105, -40);//4
       
        p. line(110, -110,0,90, -90, 40);//5
        p. line(110, -110,0,90, -90, -40);//5
       
        p. line(110,190,0,90,110,40);//11
        p. line(110,190,0,90,110,-40);//11
       
        p. line(-120, 110,0,-70,30,40);
        p. line(-120, 110,0,-70,30,-40);
       
        p. line(230, 40,0,175,40,80);
        p.line(230, 40,0,175,40,-80);
        p.endShape();
    }//end drawBrain
 
    float angle = 0;
    float radius = 300;//size of the larger circle
 
    public void drawCircles()
    {
        // circle
        p.noStroke();
        p.noFill();
        p.ellipse(p.width/2, p.height/2, radius*2, radius*2);//size of the larger circle
   
   
        // rotating circles
        for (int i = 0; i < 10; i++)
        {
            float x = p.cos(angle + i * p.TWO_PI / 10) * radius + p.width/2;
            float y = p.sin(angle + i * p.TWO_PI / 10) * radius + p.height/2f;
            p.stroke(p.hueShift(93), 255, 255, v.alp);
            p.fill(p.hueShift(93), 255, 255, v.alp);

            p.ellipse(x, y, 20, 20);
        }
        angle += 0.01 * v.spe;//speed of the smaller rotating circles
    }//end function drawCircles
 
    // method that stops the program
    public void stop()
    {
        p.stop(); // stop process in the superclass's stop()
    }
}