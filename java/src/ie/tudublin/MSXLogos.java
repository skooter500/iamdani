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
        AudioGarden v;

        MSXModel(String fileName, float x, float y, float z, float h, AudioGarden v) {
            pos = new PVector(x, y, z);
            this.h = h;
            this.v = v;
            sh = v.loadShape(fileName);
            sh.disableStyle();
            theta = v.random(v.TWO_PI);
        }

        float lerpedS = 0;

        void render() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateX(-v.HALF_PI);
            v.rotateY(-v.yaw);
            v.rotateZ(v.pit);
            float s = 1.0f + v.noise(theta * 3) * 400;
            lerpedS = v.lerp(lerpedS, s, 0.01f);
            v.scale(s);
            v.stroke(v.hueShift(h), 255, 255, v.alp);
            v.noFill();
            v.shape(sh);
            v.popMatrix();
            theta += v.spe * 0.03f * v.getSmoothedAmplitude();
            pos.z += v.spe * v.getSmoothedAmplitude() * 40.0f;

            if (pos.z > 2000) {
                pos.z = -1000 ;
                lerpedS = 0;
                h = 127;
            }        
        }
    }

public class MSXLogos extends Poly{

    ArrayList<MSXModel> models = new ArrayList<MSXModel>();

    int numLogos = 20;

    public MSXLogos(AudioGarden v) {
        super(v);

        
        
        //
    }

    @Override
    public void render() {
        float halfW = v.width / 2;
        float halfH = v.height / 2;
        if (models.size() < numLogos && v.frameCount % 20 == 0 )
        {
            MSXModel msxModel = new MSXModel("msx.obj", v.random(-halfW, halfW), v.random(-halfH, halfH), 127, 100, v);
            models.add(msxModel);
        }

        v.lights();
        v.strokeWeight(2);
        v.translate(v.width / 2, v.height / 2, -500);
        for (MSXModel model:models)
        {
            model.render();
        }
    }
    
}
