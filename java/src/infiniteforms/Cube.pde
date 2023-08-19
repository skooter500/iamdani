class Cube
{
  PVector position;
  
  float smoothedBoxSize = 0;
  float angle = 0;
  float weight = 5;
  float size = 50;
  
  boolean useAmplitude = true;
  
  
  Cube(float x, float y, float z)
  {
    position = new PVector(x, y, z);
  }
  
  void render()
  {
    pushMatrix();
    strokeWeight(2);        
    stroke((map(smoothedAmplitude, 0, 1, 0, 255) + colorOffset) % 255, 255, 255);
    translate(position.x, position.y, position.z);       
    
    rotateY(angle);
    rotateX(angle);
    strokeWeight(weight);
    if (useAmplitude)
    {
      float boxSize = size + (amplitude * 300); 
      smoothedBoxSize = lerp(smoothedBoxSize, boxSize, 0.1f);                 
      box(smoothedBoxSize);
    }
    else
    {
      box(size);
    }
    angle+=0.01f;
    popMatrix();
  }
  
}
