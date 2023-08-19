  class Bands extends Vision
{
  PVector[] positions;
  
  float radius;
  
  Bands(float radius, float x, float y, float z)
  {
    this.radius = radius;
    
    positions = new PVector[smoothedBands.length];
    for(int i = 0 ; i < smoothedBands.length ; i ++)
    {
      float theta = map(i, 0, smoothedBands.length, 0, TWO_PI);
      positions[i] = new PVector(x + sin(theta) * radius, y - cos(theta) * radius, z); 
    }
    
  }
  
  float smoothedBoxSize = 0;
  float rot = 0;
  
  void render()
  {
    background(0);
    noFill();
    lights();    
    pushMatrix();
    camera(0, 450, 450, 0, 0, 0, 0, 1, 0);
    rotateZ(rot);
    for(int i = 0 ; i < positions.length ; i ++)
    {
      pushMatrix();
      stroke(map(i, 0, positions.length, 0, 255), 255, 255);
      noFill();
      strokeWeight(2);
      float base = 40;
      float boxSize = base + (smoothedBands[i] * 2);       
      translate(positions[i].x, positions[i].y, positions[i].z + (boxSize * 0.5f));
      rotateZ(map(i, 0, positions.length, 0, TWO_PI));
      box(base, base, boxSize);
      popMatrix();
    }
    rot += speed * amplitude;
    popMatrix();
    camera(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0), width/2.0, height/2.0, 0, 0, 1, 0);          
    
  }
}
