package ie.tudublin;
import processing.core.PApplet;

public class BasakEllipse extends Poly {

    float value2;
    private float smoothedEllipseSize = 0;
    private float strokeWeightMapped;
    Ellipse ellipse;

    

    public BasakEllipse(Visual v) {
        super(v);
        ellipse=new Ellipse(260, 210, 0, 300);
        //TODO Auto-generated constructor stub
      
    }
    @Override
    public void render(int ellapsed)
    {       
        //translate to the center of the screen
        v.translate(v.width/2, v.height/2);
        ellipse.display();

        
        
    }

    class Ellipse{

        float x=80*2+100;
        float y=80*2+50;
        float angle;
        int value=300;
        

        Ellipse(float x, float y, float angle, int value) {
            this.x = x;
            this.y = y;
            this.angle = angle;
            this.value = value;
            //this.value2 = x * sin(radians(angle));
        }

        void display() {

            //v.translate(v.width/2,v.height/2);
            
    
        
            v.calculateAverageAmplitude();
            
    
            for (float rad = 0; rad < 360; rad += 5) {
                v.rotateX(v.pit);
                v.rotateY(v.yaw);
                v.pushMatrix();
                v.rotate(PApplet.radians(rad));
                v.lights();
                //v.stroke(map(rad, 0, 360, 0, 255), 255, 255);
                v.noFill();
                float EllipseSize = 75 + (v.getAmplitude() * 500);
                smoothedEllipseSize = PApplet.lerp(smoothedEllipseSize, EllipseSize, 0.2f);
             
    
                //v.pushMatrix();
                //v.stroke(PApplet.map(rad, 0, 255, 0, 360), 255, 255);
                v.stroke(v.hue,255,255,v.alp);
                //strokeWeightMapped = PApplet.map(v.getAmplitude(), 0, 1, 1, 5);
                //v.strokeWeight(strokeWeightMapped * 2);
                //v.popMatrix();
    
               
                v.line(x * PApplet.sin(PApplet.radians(angle)), 0, 0, y);
                v.ellipse(smoothedEllipseSize * PApplet.sin(PApplet.radians(angle)), 0, 5, 5);
                v.line(x * PApplet.sin(PApplet.radians(angle)), 0, 0, 5, 0, 0);
                //v.popMatrix();
    
    
                v.pushMatrix();
                v.rotate(-PApplet.radians(angle));
                v.ellipse(smoothedEllipseSize* PApplet.sin(PApplet.radians(angle)), y, 5, 5);
                //v.triangle(properties.x*sin(radians(properties.angle)), 0, 0, 5, 0, 0);
                v.popMatrix();
    
                v.pushMatrix();
                v.rotate(PApplet.radians(angle));
                v.ellipse(smoothedEllipseSize * PApplet.sin(PApplet.radians(angle)), y + 50, 10, 10);
                v.popMatrix();
                
    
                //v.stroke(hue, 255, 255);
                v.pushMatrix();
                v.rotate(-PApplet.radians(angle));
                v.ellipse(smoothedEllipseSize * PApplet.sin(PApplet.radians(angle)), y + 100, 20, 20);
                v.popMatrix();
    
                v.pushMatrix();
                
                v.rotate(PApplet.radians(angle));
                //v.ellipse(smoothedEllipseSize * sin(radians(properties.angle)), properties.y + 150, 30, 30);
               
                //v.stroke(32, 255, 255);
                v.stroke(PApplet.map(rad, 0, 360, 0, 255), 255, 255);
                //v.fill(60, 255, 255);
                
                float triangleBaseX = x + 48; 
                float triangleBaseY = y; 
                float triangleHeight = 20; 
                float triangleTopX = x + 10; 
                float triangleTopY = y - triangleHeight; 
                v.triangle(triangleBaseX+40, triangleBaseY+20, triangleTopX, triangleTopY, x +30, y-40);
                //v.stroke(32, 255, 255);
                v.popMatrix();
    
                v.popMatrix();
    
              
            }
    
          angle += v.spe;
            
        }
    }

}
    


    
