class Cubes3 extends Vision
{
  Cube[] cubes;
  float z = 0;
  float range = 2000;
  int numCubes;
  
  void restart()
  {
    for(Cube c:cubes)
    {
      c.smoothedBoxSize = 0;
    }
  }
  
  Cubes3(int numCubes, float z)
  {
    cubes = new Cube[numCubes];
    
    for(int i = 0 ; i < numCubes ; i ++)
    {
      float x = random(-range, range);
      float y = random(-range, range);
      cubes[i] = new Cube(x, y, random(-z, 0));        
      cubes[i].useAmplitude = false;
      cubes[i].weight = 3;
      cubes[i].size = 300;
    }
    this.z = z;
    this.numCubes = numCubes;
  }
  
  float theta = 0;
    
  void render()
  {
    background(0);
    noFill();
    lights();
    stroke(map(smoothedAmplitude, 0, 1, 0, 255), 255, 255);
    pushMatrix();    
    camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
    for(Cube c:cubes)
    {
      c.render();       
      c.position.z += amplitude * 300;
      if (c.position.z > 0)
      {
        c.position.x = random(-range, range);
        c.position.y = random(-range, range);
        c.position.z = -z;       
      }
    }    
    popMatrix();
    camera(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0), width/2.0, height/2.0, 0, 0, 1, 0);
  }
}
