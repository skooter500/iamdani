package infiniteforms;

import ie.tudublin.AudioGarden;
import processing.core.*;

public class Model
{    
  PShape s;
  PVector position;
  float smoothedBoxSize = 0;
  float angle = 0;
  float weight = 5;
  float size = 30;
  //myObject obj;
  float theta = 0;

  AudioGarden v;

  Model(String fileName, float x, float y, float z, AudioGarden v)
  {
    //obj = new myObject(fileName);
    s = v.loadShape(fileName);
    s.disableStyle();
    position = new PVector(x, y, z);
    this.v = v;
  }
  float c = 0;

  void render()
  {
    v.pushMatrix();
    //strokeWeight(2);    
    //s.setStroke(0, 0);
    //s.setStroke(v.color(200, 255, 255));
    //s.setFill(v.color(0, 0, 0, 0));
    
    int col1 = (int) v.map(v.getSmoothedAmplitude(), 0, 1, 0, 255);
    int col2 = (int) v.map(v.getSmoothedAmplitude(), 0, 1, 255, 0);
    
    v.fill(
      col1
      , 255, 255);
    v.stroke(
      col2
      , 255, 255);

      
    v.translate(position.x, position.y, position.z);       
    
    //strokeWeight(weight);
    float boxSize = size + (v.getAmplitude() * 30); 
    smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.2f);
    //scale(1);
    v.rotateX(-v.HALF_PI);
    v.rotateZ(v.PI);
    // v.rotateZ(0.2f + v.sin(theta) * 0.2f);
    theta += v.speed;
    c += v.speed * 100 * v.getAmplitude();
    v.scale(smoothedBoxSize * 2);
    v.shape(s);
    v.popMatrix();


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
