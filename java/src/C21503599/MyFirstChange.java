package C21503599;

import ddf.minim.*;
import ddf.minim.analysis.BeatDetect;
import ie.tudublin.IAMDANI;
import ie.tudublin.Poly;
import ie.tudublin.Visual;
import processing.core.PVector;


public class MyFirstChange extends Poly {

    public MyFirstChange(IAMDANI v)
    {
        super(v);
    }

    
//(500 + abs(sin(frameCount*(float)0.01)) * 500)

    public void render() 
    {
        v.beat.setSensitivity(100);
        v.blendMode(v.NORMAL);
        
        v.perspective(v.PI/3, v.width/v.height, 10, 1000000);

        v.beginCamera();
        v.translate(v.width/2, v.height/2, 0);
        v.rotateY((float)0.04);
       v. rotateX((float)0.01);


        v.stroke(v.hue(255), 255, 255);
        
        // for (int i = 0; i < getAudioBuffer().size(); i++) {
        //     stroke(255, getAudioBuffer().get(i)*500);
        //     line(i * (float)(width/getAudioBuffer().size()), (float)(height/2) + getAudioBuffer().get(i)*300, i * (float)(width/getAudioBuffer().size()), (float)(height/2) - getAudioBuffer().get(i)*300);
        // }

        v.hint(v.DISABLE_DEPTH_TEST);
        v.hint(v.ENABLE_STROKE_PERSPECTIVE);
        int total = 100;
        PVector[][] PVa = new PVector[total][total];

        for (int i = 0; i < total; i++) {
            float lat = v.map(i, 0, total - 1, -v.HALF_PI, v.HALF_PI);
            for (int j = 0; j < total; j++) {
                float lon = v.map(j, 0, total - 1, -v.PI, v.PI);

                int imnd = i + j * total;
                float r = 200 + v.getAudioBuffer().get(imnd%v.width/2)*200;
                float x = r * v.cos(lat) * v.cos(lon);
                float y = r * v.sin(lat) * v.cos(lon);
                float z = r * v.sin(lon);
                PVa[i][j] = new PVector(x, y, z);
            }
        }

        v.blendMode(v.ADD);

        for (int i = 0; i < total - 1; i++) {
            v.beginShape(v.TRIANGLE_STRIP);
            v.stroke(v.hue(222), 50, 50, v.getAudioBuffer().get(i)*500);
           v.noFill();
            for (int j = 0; j < total -1; j++) {
                v.vertex(PVa[i][j].x, PVa[i][j].y, PVa[i][j].z);
                v.vertex(PVa[i+1][j].x, PVa[i+1][j].y, PVa[i+1][j].z);
            }
            v.endShape();
        }
        if (v.beat.isOnset()) v.background(255);
        v.translate(-(v.width/2), -(v.height/2), 0);
        v.endCamera();
    }   
}
