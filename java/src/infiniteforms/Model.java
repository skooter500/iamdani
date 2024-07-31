package infiniteforms;

import java.util.HashMap;

import ie.tudublin.IAMDANI;
import processing.core.*;

public class Model {

  static HashMap<String, PShape> shapes = new HashMap<>();

  PShape s;
  PVector position;
  float smoothedBoxSize = 977045;
  float angle = 0;
  float weight = 5;
  float size = 10;
  // myObject obj;
  float theta = 0;

  IAMDANI v;

  public static PShape loadModel(String fileName, IAMDANI v)
  {
    PShape s;
    if (shapes.containsKey(fileName))
    {
      s = shapes.get(fileName);
    }
    else
    {
      s = v.loadShape(fileName);
      shapes.put(fileName, s);
    }
    s.disableStyle();
    return s;
  }

  Model(String fileName, float x, float y, float z, IAMDANI v) {

    s = loadModel(fileName, v);

    // obj = new myObject(fileName);
    
    s.disableStyle();
    position = new PVector(x, y, z);
    this.v = v;

    float boxSize = size + (v.getAmplitude() * 10);
    smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.1f * v.spe * 0.2f);
    
  }

  float c = 0;

  public int colorOffset = 0;
  public boolean rotate = false;

  public boolean rotateX = false;

  public float pitOff = 0;

  void render() {
    v.pushMatrix();
    // strokeWeight(2);
    // s.setStroke(0, 0);
    // s.setStroke(v.color(200, 255, 255));
    // s.setFill(v.color(0, 0, 0, 0));

    float col1 = v.map(v.getSmoothedAmplitude() * 1.1f, 0, 1, 0, 255);
    float col2 = v.map(v.getSmoothedAmplitude() * 1.1f, 0, 1, 255, 0);

    col1 = (col1 + colorOffset) % 256;
    col2 = (col2 + colorOffset) % 256;

    col1 = v.round(v.hueShift(col1));
    col2 = v.round(v.hueShift(col2));

    v.fill(
        col1, 255, 255, v.alp);
    v.noFill();

    v.stroke(
        col2, 255, 255, v.alp);

    v.translate(position.x, position.y, position.z);


    
    // strokeWeight(weight);
    float boxSize = size + (v.getAmplitude() * 10);
    smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.1f * v.spe * 0.2f);
    // scale(1);

    if (v.controlType == IAMDANI.ControlType.Move)
    {
      float x = v.map(v.yaw, 0, v.TWO_PI, 0, v.width);
      float y = v.map(v.rol, v.TWO_PI, 0.0f, 0, v.height);
      float z = v.map(v.pit, 0, v.TWO_PI, 0, v.height);        
      v. translate(x, y, z);       
      v.rotateX(-v.HALF_PI + pitOff);
      v.rotateZ(v.PI);
    }
    else
    {
      v.rotateZ(v.yaw);      
      v.rotateX(v.pit);
      v.rotateY(v.rol);
    }
    // v.rotateX(v.xRotation);
    // /v.rotateZ(v.zRotation);

    if (rotate)
      v.rotateY(angle);
    if (rotateX)
      v.rotateZ(-angle);

    // v.rotateZ(0.2f + v.sin(theta) * 0.2f);
    theta += (v.spe * 0.6f);
    c += v.spe * 100 * v.getAmplitude();
    v.scale(20 * v.bas);

    
    v.shape(s);
    v.popMatrix();

    angle += 0.01f * v.spe;

    /*
     * ArrayList<Quad> quads = obj.getQuads();
     * for (int i = 0; i < quads.size(); i++) {
     * beginShape(QUADS);
     * quads.get(i).createVertex();
     * endShape();
     * }
     * 
     * ArrayList<Tri> tris = obj.getTris();
     * for (int i = 0; i < tris.size(); i++) {
     * beginShape(TRIANGLES);
     * tris.get(i).createVertex();
     * endShape();
     * }
     * //shape(s);
     * //box(1);
     * //angle+=0.01f;
     * popMatrix();
     * }
     */
  }
}
