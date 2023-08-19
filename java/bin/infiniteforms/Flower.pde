class Flower extends Vision
{    
    public Flower() {
    }

    public void render() {
        pushMatrix();
        int numPetals = 8;
        int numRows = smoothedBands.length; 
        float pOffset = PConstants.HALF_PI * 0.3f;
        float rowAngleOffset = 0;

        // size of petal
        float pX = 50;
        float pY = 100;

        // center of bottom of rose
        float rX = 0;
        float rY = pY / 2;

        float rColor = 0;
        translate(width / 2, height / 2);
        // rotate rose to see side on
        rotateX(PI * 0.3f) ;

        noStroke();

        for (int i = 0; i < numRows; i++) {

            fill(rColor, 255, 255);
            pY = map(smoothedBands[i], 0, 1000, height / 12, height / 2);

            for (int j = 0; j < numPetals; j++) {
                rowAngleOffset = map(i, 0, numPetals, HALF_PI, 0);
                pushMatrix();
                rotateX(rowAngleOffset);
                ellipse(rX, rY + (pY / 2), pX, pY);
                
                popMatrix();
                rotate(TWO_PI / numPetals);
            }
            
            // offset petals for next row
            rotate(pOffset);

            // reduce petal size
            pY = pY * 0.9f;
            rColor += (255 / numRows);
        }

        popMatrix();

    }
}
