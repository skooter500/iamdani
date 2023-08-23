package infiniteforms;

import ie.tudublin.AudioGarden;
import ie.tudublin.Poly;

public class IFCubes extends Poly {
    IFCube[] cubes;
    float z = 0;

    public int colorOffset = 0;
  
  public void enter()
  {
    float offs = v.random(0, 255);
    for(IFCube c:cubes)
    {
      c.smoothedBoxSize = 0;
      c.colorOffset = offs;
    }
  }
  
  public IFCubes(AudioGarden v, int numCubes, float radius, float z)
  {
    super(v);
    cubes = new IFCube[numCubes];
    
    for(int i = 0 ; i < numCubes ; i ++)
    {
      float theta = v.map(i, 0, numCubes, v.HALF_PI, v.TWO_PI + v.HALF_PI);
      float x = v.sin(theta) * radius;
      float y = - v.cos(theta) * radius;
      cubes[i] = new IFCube(v, x + (v.width / 2), y + (v.height / 2), 0.0f);        
    }
    this.z = z;
  }
  
  float theta = 0;
    
  public void render()
  {
    v.noFill();
    v.lights();
    float col = v.map(v.getSmoothedAmplitude(), 0, 1, 0, 255);
    col = v.hueShift(col); 
    v.stroke(col, 255, 255);
    v.pushMatrix();    
    //v.camera(0, 0, -z, 0, 0, -1, 0, 1, 0);
    //rotateY(theta);   
    //rotateX(theta);    
    for(IFCube c:cubes)
    {
      c.render();       
    }    
    v.camera(v.width/2.0f, v.height/2.0f, -1000, v.width/2.0f, v.height/2.0f, 0f, 0f, 0.001f, 0f);
    v.popMatrix();
    theta+=0.01f * v.spe;
    
  }
}
