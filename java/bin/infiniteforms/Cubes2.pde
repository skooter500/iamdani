class Cubes2 extends Vision
{
  Cube[] cubes;
  float z = 0;
  
  void restart()
  {
    for(Cube c:cubes)
    {
      c.smoothedBoxSize = 0;
    }
  }
  
  Cubes2(int numCubes, float radius, float z)
  {
    cubes = new Cube[numCubes];
    
    for(int i = 0 ; i < numCubes ; i ++)
    {
      float theta = map(i, 0, numCubes, HALF_PI, TWO_PI + HALF_PI);
      float x = sin(theta) * radius;
      float y = - cos(theta) * radius;
      cubes[i] = new Cube(x, y, 0);        
    }
    this.z = z;
  }
  
  float theta = 0;
    
  void render()
  {
    background(0);
    noFill();
    lights();
    stroke(map(smoothedAmplitude, 0, 1, 0, 255), 255, 255);
    pushMatrix();    
    camera(0, 0, -z, 0, 0, -1, 0, 1, 0);
    //rotateY(theta);   
    //rotateX(theta);    
    for(Cube c:cubes)
    {
      c.render();       
    }    
    popMatrix();
    theta+=0.01f;
    camera(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0), width/2.0, height/2.0, 0, 0, 1, 0);
  }
}
