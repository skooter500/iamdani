package infiniteforms;

import ie.tudublin.IAMDANI;
import processing.core.*;

public class Model
{    
  PShape s;
  PVector position;
  float smoothedBoxSize = 977045;
  float angle = 0;
  float weight = 5;
  float size = 10;
  //myObject obj;
  float theta = 0;

  IAMDANI v;

  

  Model(String fileName, float x, float y, float z, IAMDANI v)
  {
    //obj = new myObject(fileName);
    s = v.loadShape(fileName);
    s.disableStyle();
    position = new PVector(x, y, z);
    this.v = v;
  }
  float c = 0;

  public int colorOffset = 0;
  public boolean rotate = false;

  public boolean rotateX = false;

  void render()
  {
    v.pushMatrix();
    //strokeWeight(2);    
    //s.setStroke(0, 0);
    //s.setStroke(v.color(200, 255, 255));
    //s.setFill(v.color(0, 0, 0, 0));
    
    float col1 = v.map(v.getSmoothedAmplitude() * 1.1f, 0, 1, 0, 255);
    float col2 = v.map(v.getSmoothedAmplitude() * 1.1f, 0, 1, 255, 0);

    col1 = (col1 + colorOffset) % 256;
    col2 = (col2 + colorOffset) % 256;
    
    col1 = v.round(v.hueShift(col1));
    col2 = v.round(v.hueShift(col2));

    v.fill(
      col1
      , 255, 255, v.alp);
      v.noFill();
    
    v.stroke(
      col2
      , 255, 255, v.alp);

      
    v.translate(position.x, position.y, position.z);       
    
    //strokeWeight(weight);
    float boxSize = size + (v.getAmplitude() * 10); 
    smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.1f * v.spe * 0.2f);
    //scale(1);

    
    v.rotateX(-v.HALF_PI + v.pit);
    v.rotateZ(v.PI + v.yaw);
    //v.rotateX(v.xRotation);
    // /v.rotateZ(v.zRotation);
    
    if (rotate)
      v.rotateY(angle);
    if (rotateX)
      v.rotateZ(-angle);

      // v.rotateZ(0.2f + v.sin(theta) * 0.2f);
    theta += (v.spe * 0.2f) ;
    c += v.spe * 100 * v.getAmplitude();
    v.scale(smoothedBoxSize * 4);
    v.shape(s);
    v.popMatrix();

    angle+=0.05f * v.spe;


    /*
    ArrayList<Quad> quads = obj.getQuads();  
     for (int i = 0; i < quads.size(); i++) {
     beginShape(QUADS);
     quads.get(i).createVertex();
     endShape();
     }
     
     ArrayList<Tri> tris = obj.getTris();  
     for (int i = 0; i < tris.size(); i++) {
     beginShape(TRIANGLES);
     tris.get(i).createVertex();
     endShape();
     }
     //shape(s);
     //box(1);
     //angle+=0.01f;
     popMatrix();
     }
     */
  }
}
