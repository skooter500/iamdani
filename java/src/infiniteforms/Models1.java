package infiniteforms;

import ie.tudublin.IAMDANI;
import ie.tudublin.Poly;

public class Models1 extends Poly
{
  public Model model;

  int colorOffset = 0;
  public float scale = 1;

  public float pitOff = 0;
  
  public Models1(IAMDANI v, String fileName, boolean rotate, boolean rotateX)
  {
    super(v);
    model = new Model(fileName, 0, 0, 0, v);    
    model.rotate = rotate;
    model.pitOff = pitOff;
    model.rotateX = rotateX;
  }
  
  public void enter()
  {
    model.smoothedBoxSize = 20;
    model.colorOffset = (int) v.random(0, 256);
  }

  float smoothedBoxSize = 0;

  float size = 0;
  
  public void render()
  {

    v.noFill();
    //v.lights();
    v.strokeWeight(2);
    v.stroke(255, 255, 255, 240);
    v.noFill();      
    v.rotateZ( v.yaw);
    v.rotateX(v.pit);    
    v.rotateY(v.rol);
         
    v.pushMatrix();    
    v.camera(200, 0, -400, 20, 20, 0, 0, 1, 0);
    
    v.scale(scale);
    
    model.render();
    v.popMatrix();
    v.camera(v.width/2.0f, (v.height/2.0f) - 5000, (v.height/2.0f) / v.tan(v.PI*30.0f / 180.0f), v.width/2.0f, v.height/2.0f, 0, 0, 1, 0);

  }
  
}
