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

    float scale_factor = 1.0f;

    public GrainneHead(IAMDANI v, String filename)
    {
        super(v);
        this.filename = filename;
        spider_head = v.loadShape(filename);
        angle = 0;
        radius = 100; 
        radiusX = v.width / 4;
        radiusY = v.height / 4;
        speed = 0.005f;
        centerX = v.width / 2;
        centerY = v.height / 2;
        //spider_head.rotateX(PApplet.PI);
        //spider_head.rotateY(PApplet.PI - 30);
    }

    float size = 50;

    float smoothedBoxSize = 10000;

        @Override
        public void render(int ellapsed)
        {       
            v.lights();

            float c = 90;                
            float x = centerX + radiusX * PApplet.sin(angle);
            float y = centerY + radiusY * PApplet.cos(angle);

            angle += speed * v.spe; 

            v.translate(x, y, -250);
            v.rotateX(-v.HALF_PI);
            v.rotateX(v.pit);
            v.rotateZ( v.yaw);
            v.rotateY(v.rol);
            float boxSize = size + (v.getAmplitude() * 200); 
            smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.1f);                 
            v.scale(smoothedBoxSize);
            
            //spider_head.setFill(v.color(v.hueShift(c), 255, 255, v.alp));
            spider_head.noFill();
            v.shape(spider_head);

            radiusX = v.width / 4 + v.width / 8 * PApplet.sin(angle * 2);
            radiusY = v.height / 4 + v.height / 8 * PApplet.cos(angle * 2);
            count += 10;
        }

    }

