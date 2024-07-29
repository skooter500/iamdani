package ie.tudublin;

import java.util.ArrayList;

import infiniteforms.Model;
import processing.core.PShape;
import processing.core.PVector;

class MSXModel {
        PVector pos;
        float h;
        PShape sh;
        float theta;
        IAMDANI v;

        MSXModel(PShape fileName, float x, float y, float z, float h, IAMDANI v) {
            pos = new PVector(x, y, z);
            this.h = h;
            this.v = v;
            sh = fileName;
            sh.disableStyle();
            theta = v.random(v.TWO_PI);
        }

        float lerpedS = 0;

        float size = 100;
        float smoothedBoxSize = 100;

        void render() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateX(-v.HALF_PI);
            v.rotateX(v.pit);
            v.rotateZ(v.yaw);
            v.rotateY(v.rol);
            
            float s = 0.1f + v.noise(theta * 0.4f) * 50;
            lerpedS = v.lerp(lerpedS, s, 0.01f);

            float boxSize = size + (v.getAmplitude() * 40);
    smoothedBoxSize = v.lerp(smoothedBoxSize, boxSize, 0.1f * v.spe * 0.2f);
    
            v.scale(s + smoothedBoxSize * 1);
            v.stroke(v.hueShift(h), 255, 255, v.alp);
            float newC = v.hueShift(h + 127 + lerpedS);
            // v.fill(newC, 255, 255, v.alp);       
            v.noFill();     
            v.strokeWeight(2);
            v.shape(sh);
            v.popMatrix();
            theta += v.spe * 0.005f;
            pos.z += v.spe * v.getSmoothedAmplitude() * 2;

            if (pos.z > 3000) {
                pos.z = -1000 ;
                float halfW = v.width / 2;
                float halfH = v.height / 2;
                pos.x = v.random(-v.width, v.width);
                pos.y = v.random(-v.width, v.width);
                
                lerpedS = 0;
                h = v.random(256);
                size = 0;
                smoothedBoxSize =0;
            }        
        }    }

public class MSXLogos extends Art{

    ArrayList<MSXModel> models = new ArrayList<MSXModel>();

    int numLogos = 20;

    String filename;

    public void enter()
    {
        // v.defaults();
        v.cqz = 1;        
    }

    public String toString()
  {
    return filename;
  }

    public  MSXLogos(IAMDANI v, String filename, int numLogos) {
        super(v);
        this.filename = filename;
        s = Model.loadModel(filename, v);
        this.numLogos = numLogos;
        //
    }

    PShape s;

    int spawnCounter = 0;

    @Override
    public void render() {
        float halfW = v.width;
        float halfH = v.height;
        
        if (models.size() < numLogos && spawnCounter == 0)
        {
            MSXModel msxModel = new MSXModel(s, v.random(-v.width, v.width), v.random(-halfH, halfH), 127, v.random(0, 255), v);
            models.add(msxModel);
            spawnCounter = 60;
        }

        spawnCounter = v.max(0, spawnCounter - 1);

        v.lights();
        v.strokeWeight(2);

        v.translate(v.width / 2, v.height / 2, -2000);
        for (MSXModel model:models)
        {
            model.render();
        }
    }
    
}