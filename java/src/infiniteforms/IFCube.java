package infiniteforms;

import ie.tudublin.IAMDANI;
import processing.core.PShape;
import processing.core.PVector;

public class IFCube
{
  PVector position;
  
  float smoothedBoxSize = 0;
  float angle = 0;
  float weight = 1;
  float size = 50;
  
  boolean useAmplitude = true;
  IAMDANI v;
  PShape s;
  String filename;
  
  public IFCube(IAMDANI v, float x, float y, float z, String filename)
  {
    this.v = v;
    position = new PVector(x, y, z);
    this.filename = filename;
    s = Model.loadModel(filename, v);
  }

  float colorOffset = 0;
  
  void render()
  {
    v.pushMatrix();
    v.strokeWeight(2);    
    float col = v.hueShift((colorOffset + v.getAmplitude() * 255));        
    v.stroke(col, 255, 255, v.alp);
    v.translate(position.x, position.y, position.z);       
    
    v.rotateY(angle);
    v.rotateX(angle);
    v.rotateZ(v.pit);
    v.rotateY(v.yaw);

    v.strokeWeight(weight);
    if (useAmplitude)
    {
      float amp = v.getAmplitude();
      float boxSize = size + (amp * 100); 
      smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.1f);      
      v.rotateX(-v.HALF_PI);
      v.rotateZ(v.PI);
      v.scale(smoothedBoxSize);
      v.shape(s);          
      //v.box(smoothedBoxSize);
    }
    else
    {
        v.box(size);
    }
    angle+=0.001f * v.spe;
    v.popMatrix();
  }
  
}