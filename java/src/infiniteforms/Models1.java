package infiniteforms;

import ie.tudublin.AudioGarden;
import ie.tudublin.Poly;

public class Models1 extends Poly
{
  Model model;
  
  public Models1(AudioGarden v, String fileName)
  {
    super(v);
    model = new Model(fileName, 0, 0, 0, v);    
  }
  
  void restart()
  {
    model.smoothedBoxSize = 0;
  }
  
  public void render()
  {

    v.noFill();
    v.lights();
    v.strokeWeight(2);
    v.stroke(255, 255, 255, 240);
    v.noFill();           
    v.pushMatrix();    
    v.camera(0, 150, -300, 20, 20, 0, 0, 1, 0);
    model.render();
    v.popMatrix();
    v.camera(v.width/2.0f, v.height/2.0f, (v.height/2.0f) / v.tan(v.PI*30.0f / 180.0f), v.width/2.0f, v.height/2.0f, 0, 0, 1, 0);

  }
  
}
