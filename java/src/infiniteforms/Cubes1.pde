class Cubes1 extends Vision
{
  Cube cube = new Cube(0, 0, -250);
  
  void restart()
  {
    cube.smoothedBoxSize = 0;
  }
  
  void render()
  {
    background(0);
    noFill();
    lights();
    stroke(map(smoothedAmplitude, 0, 1, 0, 255), 255, 255);
    camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
    cube.render();    
    camera(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0), width/2.0, height/2.0, 0, 0, 1, 0);
  }
}
