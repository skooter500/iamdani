package ie.tudublin;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;


class GrainneHeadModel {
    PVector pos;
    float h;
    PShape sh;
    float theta;
    IAMDANI v;

    GrainneHeadModel(PShape fileName, float x, float y, float z, float h, IAMDANI v) 
    {
        pos = new PVector(x, y, z);
        this.h = h;
        this.v = v;
        sh = fileName;
        sh.disableStyle();
        theta = v.random(v.TWO_PI);
    }
}

public class GrainneHead extends Poly
{
    // Minim minim;
    // AudioBuffer ab;

    PShape spider_head;
    float ry;

    String filename;

    float angle;
    float radius;
    float speed;
    float centerX;
    float centerY;
    float radiusX;
    float radiusY;

    public GrainneHead(IAMDANI v, String filename)
    {
        super(v);
        this.filename = filename;
        spider_head = v.loadShape(filename);
        angle = 0;
        radius = 100; 
        radiusX = v.width / 4;
        radiusY = v.height / 4;
        speed = 0.05f;
        centerX = v.width / 2;
        centerY = v.height / 2;
        spider_head.rotateY(PApplet.PI-30);

    }


        public void setup()
        {
            v.frameRate(30);
            v.startMinim();

            try 
            {
                spider_head = v.loadShape("data\\spiderman1.obj");
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                System.err.println("Error loading shape file: " + e.getMessage());
            }

        
            spider_head.setFill(v.color(v.random(120,255), v.random(255), v.random(255)));

        }


        @Override
        public void render(int ellapsed)
        {       
            v.background(0);
            v.lights();

            float x = centerX + radiusX * PApplet.sin(angle);
            float y = centerY + radiusY * PApplet.cos(angle);

            angle += speed; 

            v.translate(x, y, -250);
            v.rotateZ(PApplet.PI);
            // v.rotateY(ry);
            v.fill(v.random(120,255), v.random(255), v.random(255));
            v.shape(spider_head);

            radiusX = v.width / 4 + v.width / 8 * PApplet.sin(angle * 2);
            radiusY = v.height / 4 + v.height / 8 * PApplet.cos(angle * 2);

        }

    }

