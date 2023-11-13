package ie.tudublin;

import processing.core.PVector;

public class Bands extends Poly {

        //
  PVector[] positions;
  
  float radius;
  
  Bands(AudioGarden v, float radius, float x, float y, float z)
  {
    super(v);
    this.radius = radius;
    float[] smoothedBands = v.getSmoothedBands();
    positions = new PVector[v.getSmoothedBands().length];
    for(int i = 0 ; i < smoothedBands.length ; i ++)
    {
      float theta = v.map(i, 0, smoothedBands.length, 0, v.TWO_PI);
      positions[i] = new PVector(x + v.sin(theta) * radius, y - v.cos(theta) * radius, z); 
    }
    
  }
  
  float smoothedBoxSize = 0;
  float rot = 0;
  
  public void render()
  {
    v.noFill();
    v.lights();    
    v.pushMatrix();
    v.camera(0, 450, 450, 0, 0, 0, 0, 1, 0);
    v.rotateZ(rot);
    for(int i = 0 ; i < positions.length ; i ++)
    {
      v.pushMatrix();
      float c = v.map(i, 0, positions.length, 0, 255); 
      v.stroke(v.hueShift(c), 255, 255);
      v.noFill();
      v.strokeWeight(2);
      float base = 40;
      float boxSize = base + (v.smoothedBands[i] * 2);       
      v.translate(positions[i].x, positions[i].y, positions[i].z + (boxSize * 0.5f));
      v.rotateZ(v.map(i, 0, positions.length, 0, v.TWO_PI));
      v.box(base, base, boxSize);
      v.popMatrix();
    }
    rot += v.spe * v.getAmplitude() * 0.01f;
    v.popMatrix();
    v.camera(v.width/2.0f, v.height/2.0f, (v.height/2.0f) / v.tan(v.PI*30.0f / 180.0f), v.width/2.0f, v.height/2.0f, 0f, 0f, 0.01f, 0f);          
    
  }
}