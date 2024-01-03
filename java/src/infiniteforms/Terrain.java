package infiniteforms;

import ie.tudublin.IAMDANI;
import ie.tudublin.Poly;

public class Terrain extends Poly {
    int cols, rows;
    int scl = 50;
    int w = 2000;
    int h = 1600;

    float flying = 0;
    float[][] terrain;

    public Terrain(IAMDANI dani) {
        super(dani);

        cols = w / scl;
        rows = h / scl;
        terrain = new float[cols][rows];
    }

    float offs = 0;

    public void render() {

        flying -= v.spe * 0.005f;

        float yoff = flying;
        //float a = (v.getSmoothedAmplitude() * (v.map(v.mul, 0, 100, 0, 1)  + v.bas)) * 200;
        float a = 200 * v.getSmoothedAmplitude();
        for (int y = 0; y < rows; y++) {
            float xoff = 0;
            for (int x = 0; x < cols; x++) {
                terrain[x][y] = v.map(v.noise(xoff, yoff), 0, 1, -a, a);
                xoff += 0.2;
            }
            yoff += 0.2;
        }

        v.noFill();

        v.translate(v.width / 2, v.height / 2 - 200, -500);
        v.rotateX(v.pit);
        v.rotateY(-v.yaw);
        v.rotateZ(v.rol);

        v.rotateX(v.PI / 3);
        v.translate(-w / 2, -h / 2);
        for (int y = 0; y < rows - 1; y++) {
            v.beginShape(v.TRIANGLE_STRIP);
            for (int x = 0; x < cols; x++) {
                float c3 = v.map(x + y + offs, 0, rows + cols, 255, 0);
                c3 = v.hueShift(c3); 
                v.stroke(c3, 255, 255, v.alp);
                v.vertex(x * scl, y * scl, terrain[x][y]);
                v.vertex(x * scl, (y + 1) * scl, terrain[x][y + 1]);
                // rect(x*scl, y*scl, scl, scl);
            }
            v.endShape();
        }
    }
}