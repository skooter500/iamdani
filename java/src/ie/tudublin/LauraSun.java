package ie.tudublin;
 
import ddf.minim.analysis.BeatDetect;
import example.MyVisual;
 
public class LauraSun extends Art
{
    IAMDANI p;
 
    //int mode = 0;
    int numDrops = 100; // set the number of drops to 25
    float[] dropX = new float[numDrops];
    float[] dropY = new float[numDrops];
    float lightningProbability = 0.05f; // set the probability of lightning occurring
 
    public LauraSun(IAMDANI v)
    {
        super(v);
        p = v;
        this.beat = v.beat;
    }

    public void enter() {
        v.cqz = 255;
        v.targetCqz = 255;
      }
 
    BeatDetect beat;
   
    public void render()
    {
        draw();
    }
 

    public void draw()
    {

        //v.background(0);
        //v.colorMode(v.RGB);
        drawSun();
        drawSeawaves();
        drawLightning();
        drawRaindrops();
    }
 
    // Draw sun with surrounding shapes
    public void drawSun()
    {
        p.lights();//lighting for the spheres
        p.ambientLight(50, 50, 50);// illumination
        p.specular(255, 255, 255);//highlights for spheres
 
        // Define variables to control the shape of the sun and surrounding
        float sunSize = 200;
        int sunDetailLevel = 13;
        float surroundingSize = 400;
        int surroundingDetailLevel = 6;
 
        p.pushMatrix(); // drawSun push
        p.translate(p.width/2, p.height/2, -400); // move ball to center of canvas
        p.rotateY((float)(p.frameCount * 0.009)); // rotation speed
        p.rotateX(p.pit);
        p.rotateZ(p.yaw);
        // Draw surrounding shapes
        p.noStroke();
        for (int i = 0; i < 20; i++)
        {
            float angle = MyVisual.map(i, 0, 20, 0, MyVisual.TWO_PI);//calculates rotation
            p.pushMatrix();// saves the current position
            p.rotateY(angle + p.frameCount * 0.01f);// rotates surrounding spheres
            p.translate(surroundingSize, 0, 0);
            float c = v.hueShift(69);
            p.fill(c, 255, 255, v.alp);
            p.sphereDetail(surroundingDetailLevel);
            p.sphere(sunSize / 4);
            p.popMatrix();// used to restore the previous position
        }
       
        // Draw sun
        for (int i = 0; i < 50; i++) //itrerates 50 times
        {
            p.pushMatrix();
            float offset = p.random(10, 20);
            p.translate(offset, 0, 0);
            // Set color of each sphere
            float c = v.hueShift(200);
            p.fill(c, 255, 255, v.alp);            
            p.sphereDetail(sunDetailLevel);
            p.sphere(sunSize); // size of the sphere

            p.stroke(v.hueShift(22), 255, 255, v.alp);
            p.popMatrix(); // restore overall transformation
        }
        p.popMatrix(); // drawSun pop
    }
 
    public void drawStars()
    {
        // Draw background stars
        for (int i = 0; i < 200; i++)
        {
            float x = p.random(-p.width, p.width);//genegrates random x,y, z coordinates of each star
            float y = p.random(-p.height, p.height);
            float z = p.random(-2000, 0);
            p.pushMatrix();
            p.translate(x, y, z);// translates the drawing
            p.noStroke();
            p.fill(v.hueShift(37), 255, 255, v.alp);
            p.sphere(2);
            p.popMatrix();
        }
    }
 
    // drawing sea waves at the bottom of the screen
    public void drawSeawaves()
    {
        // Draw sea waves
        for (int j = 0; j < 3; j++) // add 3 waves
        {
            float waveOffset = j * 40; // adjust the offset of each wave
       
            // Set the fill and stroke colors for the wave
            p.fill(v.hueShift(37), 255, 255, v.alp); // blue
            
            p.stroke(v.hueShift(77), 255, 255, v.alp); // blue
       
            p.strokeWeight(2);
       
            p.beginShape();
            p.curveVertex(0, p.height);
            p.curveVertex(0, p.height);
            for (int i = 0; i <= p.width; i += 20)
            {
                p.curveVertex(i, p.height - 50 * MyVisual.sin(i * 0.01f + p.frameCount * 0.05f + waveOffset)); // adjust wave height, wave offset, frame count
            }
            p.curveVertex(p.width, p.height); // ending point of curve and the height
            p.curveVertex(p.width, p.height);
            p.endShape();
 
            // Draw foam outline
            p.noFill();
            p.stroke(255, 255, 255, 150); // white
            p.strokeWeight(4);
       
            p.beginShape();
            p.curveVertex(0, p.height);// first control point of curve
            p.curveVertex(0, p.height);
            for (int i = 0; i <= p.width; i += 10)
            {
                p.curveVertex(i, p.height - 50 *  MyVisual.sin(i * 0.01f + p.frameCount * 0.05f + waveOffset) - 10 * MyVisual.cos(i * 0.02f + p.frameCount * 0.07f + waveOffset)); // adjust foam height and width using sin and cos
            }
            p.curveVertex(p.width, p.height);
            p.curveVertex(p.width, p.height);
            p.endShape();
        }
    }
 
    // drawing lightning flashing on the screen
    public void drawLightning()
    {
        //Lightning  
        if (p.random(1) < lightningProbability)
        {
            for (int i = 0; i < 5; i++)
            {
                // set random start and end positions for the lightning bolt
                float startX = p.random(p.width);
                float endX = p.random(p.width);
                float startY = 0;
                float endY = p.height - 50; // made lightining strike longer
               
                // draw the lightning bolt
                p.noFill();
                p.beginShape();
                p.vertex(startX, startY);
                for (int j = 0; j < 50; j++)
                {
                    float x = MyVisual.lerp(startX, endX, (float) (j / 30.0)); // calculate x and y coordinates
                    float y = MyVisual.lerp(startY, endY, (float) (j / 30.0)) + p.random(-50, 50);// y coorrdinate
 
                    float thickness = 10 * MyVisual.pow(MyVisual.sin((float) (j / 30.0) * MyVisual.PI), 2); // vary lightning bolt thickness
 
                    int c = p.color(MyVisual.map(j, 0, 30, 255, 0), MyVisual.map(j, 0, 30, 0, 255), MyVisual.map(j, 0, 30, 128, 255)); // rainbow colours
                    p.stroke(c, 255 - j * 5); // fade out stroke
                    p.vertex(x, y);
                }
                p.vertex(endX, endY);
                p.endShape();
            }
        }
    }
 
    private int c1;
 
    // drawing rain drops
    public void drawRaindrops()
    {
        // define colors
        int c3 = p.color(150, 200, 255, v.alp); // light blue
        int c4 = p.color(200, 230, 255, v.alp); // lighter blue
 
        // draw rain drops
        for (int i = 0; i < dropX.length; i++)
        {
            float dropLength = p.random(10, 100); // generate a random length for each rain drop
            float yPos = dropY[i] + dropLength; // calculate the y position of the bottom of the rain drop
            float percentY = yPos / p.height; // calculate the percentage of the screen height that the rain drop is at
       
            // interpolate between colors based on the rain drop's position
            if (percentY <= 0.5)
            {  
                p.stroke(p.lerpColor(c1, c3, percentY * 2));
            }
            else
            {
                p.stroke(p.lerpColor(c3, c4, (float) ((percentY - 0.5) * 2)));
            }
       
            p.strokeWeight(p.random(2, 4)); // set a random stroke weight for each rain drop
            p.line(dropX[i], dropY[i], dropX[i], yPos); // draw the rain drop as a line
           
            dropY[i] += 10; // move the rain drop downwards
           
            // if the rain drop is out of the screen, reset its position
            if (yPos > p.height)
            {
                dropX[i] = p.random(p.width);
                dropY[i] = p.random(-100, 0) - dropLength; // subtract the drop length to prevent overlapping
            }
        }
    }
 
    public void stop()
    {
        p.stop();
    }
}