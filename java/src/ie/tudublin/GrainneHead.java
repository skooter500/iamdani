package ie.tudublin;
import processing.core.PApplet;
import processing.core.PShape;

public class GrainneHead extends Poly
{

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
    float count = 0;

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
        spider_head.rotateX(PApplet.PI);
        spider_head.rotateY(PApplet.PI - 30);


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

        }


        @Override
        public void render(int ellapsed)
        {       
            v.background(0);
            v.lights();

            float c = PApplet.map(count, 0, v.getAudioBuffer().size() , 100, 400);
            float x = centerX + radiusX * PApplet.sin(angle);
            float y = centerY + radiusY * PApplet.cos(angle);

            angle += speed * v.spe; 

            v.translate(x, y, -250);
            v.rotateX(v.pit);
            v.rotateY(v.yaw);
            v.rotateZ(v.rol); 
            v.scale(ellapsed / 990f * v.bas );

            spider_head.setFill(v.color(v.hueShift(c), v.random(255), v.random(255)));
            v.shape(spider_head);

            radiusX = v.width / 4 + v.width / 8 * PApplet.sin(angle * 2);
            radiusY = v.height / 4 + v.height / 8 * PApplet.cos(angle * 2);
            count += 10;
        }

    }

