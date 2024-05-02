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

        void render() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateX(-v.HALF_PI);
            v.rotateX(v.pit);
            v.rotateZ(v.yaw);
            v.rotateY(v.rol);
            
            float s = 0.5f + v.noise(theta * 0.2f) * 1500;
            lerpedS = v.lerp(lerpedS, s, 0.01f);
            v.scale(s);
            v.stroke(v.hueShift(h), 255, 255, 10);
            float newC = v.hueShift(h + 127 + lerpedS);
            // v.fill(newC, 255, 255, v.alp);       
            v.noFill();     
            v.strokeWeight(2);
            v.shape(sh);
            v.popMatrix();
            theta += v.spe * 0.005f;
            pos.z += v.spe * v.getSmoothedAmplitude();

            if (pos.z > 2000) {
                pos.z = -1000 ;
                float halfW = v.width / 2;
                float halfH = v.height / 2;
                pos.x = v.random(-halfW, halfW);
                pos.y = v.random(-halfH, halfH);
                
                lerpedS = 0;
                h = 127;
            }        
        }
    }

public class MSXLogos extends Poly{

    ArrayList<MSXModel> models = new ArrayList<MSXModel>();

    int numLogos = 8;

    String filename;

    public MSXLogos(IAMDANI v, String filename) {
        super(v);
        this.filename = filename;
        s = v.loadShape(filename);
        
        //
    }

    PShape s;

    int spawnCounter = 0;

    @Override
    public void render() {
        float halfW = v.width / 2;
        float halfH = v.height / 2;
        
        if (models.size() < numLogos && spawnCounter == 0)
        {
            MSXModel msxModel = new MSXModel(s, v.random(-halfW, halfW), v.random(-halfH, halfH), 127, 100, v);
            models.add(msxModel);
            spawnCounter = 420;
        }

        spawnCounter = v.max(0, spawnCounter - 1);

        v.lights();
        v.strokeWeight(5);

        v.translate(v.width / 2, v.height / 2, -1000);
        for (MSXModel model:models)
        {
            model.render();
        }
    }
    
}