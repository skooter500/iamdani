package ie.tudublin;

import java.util.ArrayList;

import infiniteforms.Model;
import processing.core.PShape;
import processing.core.PVector;

class MSXModel {
        PVector pos;
        float s;
        float h;
        PShape sh;
        float theta;
        AudioGarden v;

        MSXModel(String fileName, float x, float y, float z, float s, float h, AudioGarden v) {
            pos = new PVector(x, y, z);
            this.s = s;
            this.h = h;
            this.v = v;
            sh = v.loadShape(fileName);
            sh.disableStyle();
            theta = v.random(v.TWO_PI);
        }

        void render() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateX(-v.HALF_PI);
            v.rotateY(v.yaw);
            v.rotateZ(v.pit);
            v.scale(1.0f + v.noise(theta * 2) * 100);
            v.stroke(v.hueShift(h), 255, 255, v.alp);
            v.noFill();
            v.shape(sh);
            v.popMatrix();
            theta += v.spe * 0.003f * v.getSmoothedAmplitude();
            pos.z += v.spe * v.getSmoothedAmplitude() * 2.0f;

            if (pos.z > 1000) {
                pos.z = 0 ;
            }        
        }
    }

public class MSXLogos extends Poly{

    ArrayList<MSXModel> models = new ArrayList<MSXModel>();

    int numLogos = 50;

    public MSXLogos(AudioGarden v) {
        super(v);

        
        
        //
    }

    @Override
    public void render() {
        float halfW = v.width / 2;
        float halfH = v.height / 2;
        if (models.size() < numLogos)
        {
            MSXModel msxModel = new MSXModel("msx.obj", v.random(-halfW, halfW), v.random(-halfH, halfH), v.random(0, 1000), v.random(5, 20), v.random(256), v);
            models.add(msxModel);
        }

        v.lights();
        v.strokeWeight(2);
        v.translate(v.width / 2, v.height / 2, 100);
        for (MSXModel model:models)
        {
            model.render();
        }
    }
    
}
