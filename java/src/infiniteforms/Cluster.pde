public class ClusterVisual extends Vision{

    public ClusterVisual() {
        
    }

    public void render() {

        int numSpheres = smoothedBands.length;
        float radius = height / 50;
        float dist = map(smoothedAmplitude, 0, 1, 2 * radius, 0.7f * height);
        float offset = 50;
        float angle, X, Y;
        float colour;
        float freqBandRadius;

        pushMatrix();
        translate((width / 2), height / 2);
        pushMatrix();
        for (int i = 0; i < numSpheres; i++) {
            colour = map(i, 0, numSpheres, 0, 255);
            noStroke();
            fill(colour, 255, 255);
            offset = radius * i;
            pushMatrix();
            for (int j = 0; j < numSpheres + 2 * i; j++) {

                freqBandRadius = map(smoothedBands[i], 0, 1000, 0.4f * radius, 1.6f * radius);
                angle = map(j, 0, numSpheres + 2 * i, 0, TWO_PI);

                pushMatrix();
                X = cos(angle) * (dist + offset);
                Y = -sin(angle) * (dist + offset);
                translate(X, Y, 0);

                sphere(freqBandRadius);
                popMatrix();

                pushMatrix();
                rotateX(PI);
                X = cos(angle) * (dist + offset);
                Y = -sin(angle) * (dist + offset);
                translate(X, Y, 0);

                sphere(freqBandRadius);
                popMatrix();

                rotateX(TWO_PI / (numSpheres + ( 2 * i)));
            }
            popMatrix();
            rotateX(TWO_PI / (numSpheres));
        }
        popMatrix();
        popMatrix();
    }

}
