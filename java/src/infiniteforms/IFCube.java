package infiniteforms;

import ie.tudublin.AudioGarden;
import processing.core.PVector;

public class IFCube
{
  PVector position;
  
  float smoothedBoxSize = 0;
  float angle = 0;
  float weight = 5;
  float size = 50;
  
  boolean useAmplitude = true;
  AudioGarden v;
  
  public IFCube(AudioGarden v, float x, float y, float z)
  {
    this.v = v;
    position = new PVector(x, y, z);
  }

  float colorOffset = 0;
  
  void render()
  {
    v.pushMatrix();
    v.strokeWeight(2);    
    float c = v.map(v.getSmoothedAmplitude(), 0, 1, 0, 255);
    c = v.hueShift(c + colorOffset);
    v.stroke(c, 255, 255, v.alp);
    v.translate(position.x, position.y, position.z);       
    
    v.rotateY(angle);
    v.rotateX(angle);
    v.strokeWeight(weight);
    if (useAmplitude)
    {
      float amp = v.getAmplitude();
      float boxSize = size + (amp * 1000); 
      smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.1f);                 
      v.box(smoothedBoxSize);
    }
    else
    {
        v.box(size);
    }
    angle+=0.01f * v.spe;
    v.popMatrix();
  }
  
}