package ie.tudublin;

import processing.*;
import processing.data.IntList;

public class C_Head extends Art {
    public C_Head(Visual v) {
        super(v);
        initialiseTriangleOrder();
        initializeArrays();
    }

    private int triangleNum = 383;
    private int headRandomSpawn = 1;
    private float headScale = 0.7f;
    private float nodDistance = 0.5f;
    private float headWireframe = -1.5f;
    private float wireSize = 1;
    private boolean headFill = true;
    private int headHue = 180;

    private float[][] TRI;
    private float[][] PT;

    private IntList triangleOrder;
    private int triangleCount = 0;
    private boolean isHeadComplete = false;
    private float eyeZoom = 0;
    private boolean zoomIn = false;
    private int zoomPoint = 1080;

    private float dY = 0.1f;
    private float[] Xvel;
    private float[] Yvel;
    private float[] PXvel;

    private void initializeArrays() {
        // Initialize TRI and PT arrays here
        // You'll need to copy the data from the original sketch
        // This is just a placeholder initialization
        TRI = new float[5][1149];
        PT = new float[3][1149];
        
        Xvel = new float[TRI[0].length];
        Yvel = new float[TRI[0].length];
        PXvel = new float[PT[0].length];

        for (int i = 0; i < Xvel.length; i++) {
            Xvel[i] = v.random(-3, 3);
            Yvel[i] = -2;
        }
        for (int i = 0; i < PXvel.length; i++) {
            PXvel[i] = v.random(-0.8f, 0.8f);
            if (PT[0][i] < 0) {
                PT[0][i] = 9999;
                PT[1][i] = 9999;
            }
        }
    }

    public void render(int el) {
        boolean zoom = false;
        boolean rando = false;
        float elapsed = el / 60.0f;
        drawTriangles(450, 50, 0);
        if (isHeadComplete) {
            playMusic();
        }

        if (rando) {
            //colourRandomiser = true;
          } // starts randomizing colour
          if (zoom) {
            if(!zoomIn)
            eyeZoom = -0.001f;
            zoomIn = true;
          } // zooms in at given time
    }

    private void drawTriangles(float headX, float headY, float Nod) {
        // if (v.isBeat && v.colourRandomiser) {
        //     headHue = (int) v.random(0, 255);
        // }

        v.strokeWeight(wireSize);
        if (headRandomSpawn == 0) {
            triangleCount = TRI[0].length;
        }

        for (int I = 0; I < triangleCount; I++) {
            int tO = triangleOrder.get(I);

            v.fill(headHue, TRI[3][tO] * 2.5f, TRI[4][tO] * 2.5f);

            if (!headFill) {
                v.noFill();
            }
            v.stroke(headHue, TRI[3][tO] * 2.5f, TRI[4][tO] * (1 + (1.5f * (1 - headWireframe))));

            float aY = (PT[1][v.round(TRI[0][tO])] - (12 - PT[2][v.round(TRI[0][tO])]) * (Nod * nodDistance)) * headScale + headY;
            float bY = (PT[1][v.round(TRI[1][tO])] - (12 - PT[2][v.round(TRI[1][tO])]) * (Nod * nodDistance)) * headScale + headY;
            float cY = (PT[1][v.round(TRI[2][tO])] - (12 - PT[2][v.round(TRI[2][tO])]) * (Nod * nodDistance)) * headScale + headY;

            float aX = PT[0][v.round(TRI[0][tO])] * headScale + headX;
            float bX = PT[0][v.round(TRI[1][tO])] * headScale + headX;
            float cX = PT[0][v.round(TRI[2][tO])] * headScale + headX;

            if (zoomIn) {
                aX = v.lerp(aX, PT[0][zoomPoint] * headScale + headX, eyeZoom);
                bX = v.lerp(bX, PT[0][zoomPoint] * headScale + headX, eyeZoom);
                cX = v.lerp(cX, PT[0][zoomPoint] * headScale + headX, eyeZoom);

                aY = v.lerp(aY, (PT[1][zoomPoint] - (12 - PT[2][zoomPoint]) * (Nod * nodDistance)) * headScale + headY, eyeZoom);
                bY = v.lerp(bY, (PT[1][zoomPoint] - (12 - PT[2][zoomPoint]) * (Nod * nodDistance)) * headScale + headY, eyeZoom);
                cY = v.lerp(cY, (PT[1][zoomPoint] - (12 - PT[2][zoomPoint]) * (Nod * nodDistance)) * headScale + headY, eyeZoom);
            }

            if (aX < v.width && aX > 0 && aY < v.height && aY > 0 &&
                bX < v.width && bX > 0 && bY < v.height && bY > 0 &&
                cX < v.width && cX > 0 && cY < v.height && cY > 0) {
                v.triangle(aX, aY, bX, bY, cX, cY);
            }
        }

        if (zoomIn) {
            eyeZoom *= 1.05f;
        }

        if (triangleCount < triangleNum) {
            triangleCount += 3;
            triangleCount = v.constrain(triangleCount, 0, triangleNum);
        }

        if (triangleCount == triangleNum) {
            isHeadComplete = true;
        }
    }

    private void initialiseTriangleOrder() {
        triangleOrder = new IntList();
        for (int i = 0; i < triangleNum; i++) {
            triangleOrder.append(i);
        }
        triangleOrder.shuffle();
    }

    public int Shatter() {
        float headY = 50;
        v.translate(500, 0);
        boolean fin = true;
        for (int i = 0; i < TRI[0].length; i++) {
            PT[0][v.round(TRI[0][i])] = v.lerp(PT[0][v.round(TRI[0][i])], PT[0][v.round(TRI[0][i])] + Xvel[i], 0.8f);
            PT[0][v.round(TRI[1][i])] = v.lerp(PT[0][v.round(TRI[1][i])], PT[0][v.round(TRI[1][i])] + Xvel[i], 0.8f);
            PT[0][v.round(TRI[2][i])] = v.lerp(PT[0][v.round(TRI[2][i])], PT[0][v.round(TRI[2][i])] + Xvel[i], 0.8f);
            PT[1][v.round(TRI[0][i])] = v.lerp(PT[1][v.round(TRI[0][i])], PT[1][v.round(TRI[0][i])] + Yvel[i], 0.8f);
            PT[1][v.round(TRI[1][i])] = v.lerp(PT[1][v.round(TRI[1][i])], PT[1][v.round(TRI[1][i])] + Yvel[i], 0.8f);
            PT[1][v.round(TRI[2][i])] = v.lerp(PT[1][v.round(TRI[2][i])], PT[1][v.round(TRI[2][i])] + Yvel[i], 0.8f);
            Yvel[i] += dY;
            if (PT[1][v.round(TRI[0][i])] * headScale + headY <= v.height || 
                PT[1][v.round(TRI[1][i])] * headScale + headY <= v.height || 
                PT[1][v.round(TRI[2][i])] * headScale + headY <= v.height) {
                fin = false;
            }
        }
        for (int i = 0; i < PXvel.length; i++) {
            PT[0][i] += PXvel[i];
        }
        return fin ? 1 : 0;
    }

    public void enter() {
        // Implementation for enter method
    }

    public void exit() {
        // Implementation for exit method
    }

    private void playMusic() {
        // Implementation for playMusic method
    }
}