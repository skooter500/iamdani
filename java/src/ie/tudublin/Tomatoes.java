package ie.tudublin;

import processing.core.*;

public class Tomatoes extends Poly {

    // Reference to the main IAMDANI object
    PShape tomato; // Field to hold the tomato shape

    PVector[] tomatoPositions;
    int numTomatoes = 10; // Adjust this value to change the number of tomatoes
    float[] tomatoSpeeds; // Array to store the speed of each tomato

    // Tube properties
    float tubeRadius = 300; // Adjust this value to change the radius of the tube
    float tubeLength = 2000; // Initial length of the tube
    int numSegments = 25; // Adjust this value to change the number of segments in the tube
    float count = 0;

    float baseColour = 0.5f;

    // Constructor that accepts an IAMDANI object
    public Tomatoes(IAMDANI v) {
        super(v);
        loadTomato(); // Load the tomato shape
        tomato.rotateX(+260); // Rotate the model
        initialiseTomatoes();
    }

    // Method to load the tomato shape
    void loadTomato() {
        tomato = v.loadShape("tomato.obj");
    }

    // Method to render the tomato shape
    public void tomato(IAMDANI v) {
        v.pushMatrix();
        v.shape(tomato); // Render the tomato shape
        v.popMatrix();
    }

    void initialiseTomatoes() {
        tomatoPositions = new PVector[numTomatoes];
        tomatoSpeeds = new float[numTomatoes];
        float buffer = tubeRadius * 0.1f; // Define a buffer size as half of the tube radius
        float minX = -tubeRadius + buffer;
        float maxX = tubeRadius - buffer;
        float minY = -tubeRadius + buffer;
        float maxY = tubeRadius - buffer;

        for (int i = 0; i < numTomatoes; i++) {
            float x = v.random(minX, maxX);
            float y = v.random(minY, maxY);
            float z = v.random(-tubeLength / 2, tubeLength / 2);
            tomatoPositions[i] = new PVector(x, y, z);
            tomatoSpeeds[i] = v.random(1, 8); // Random speed between 1 and 8
        }
    }

    void updateFloatingTomatoes() {
        for (int i = 0; i < numTomatoes; i++) {
            tomatoPositions[i].z += tomatoSpeeds[i];
            if (tomatoPositions[i].z > tubeLength / 2) {
                tomatoPositions[i].z = -tubeLength / 2;
            }

            // Apply repulsive force between tomatoes
            for (int j = 0; j < numTomatoes; j++) {
                if (i != j) { // Avoid self-collision check
                    float distance = tomatoPositions[i].dist(tomatoPositions[j]);
                    float minDistance = 50; // Adjust this value as needed
                    if (distance < minDistance) {
                        // Calculate repulsive force
                        PVector direction = PVector.sub(tomatoPositions[i], tomatoPositions[j]).normalize();
                        float repulsionMagnitude = 0.1f; // Adjust this value to control the repulsion strength
                        float repulsionFactor = 2.0f; // Adjust this value to control the effect of repulsion
                        PVector repulsionForce = direction.mult(repulsionMagnitude).mult(repulsionFactor);

                        // Apply repulsive force to both tomatoes
                        tomatoPositions[i].add(repulsionForce);
                        tomatoPositions[j].sub(repulsionForce);

                        // Reverse velocities upon collision
                        float velocityDiff = tomatoSpeeds[j] - tomatoSpeeds[i];
                        tomatoSpeeds[i] += velocityDiff;
                        tomatoSpeeds[j] -= velocityDiff;
                    }
                }
            }

            // Ensure tomatoes stay within the cylinder's sides
            if (!isInsideCylinder(tomatoPositions[i])) {
                // Calculate correction vector to move tomato back inside the cylinder
                PVector correction = PVector.sub(new PVector(0, 0, tomatoPositions[i].z), tomatoPositions[i])
                        .normalize().mult(tubeRadius - 50);
                tomatoPositions[i].add(correction);
            }
        }
        generateNewTomatoes();
    }

    void generateNewTomatoes() {
        for (int i = 0; i < numTomatoes; i++) {
            if (tomatoPositions[i].z < -tubeLength / 2) {
                float x = v.random(-tubeRadius, tubeRadius);
                float y = v.random(-tubeRadius, tubeRadius);
                float z = -tubeLength / 2 + v.random(0, tubeLength / 4);
                tomatoPositions[i] = new PVector(x, y, z);
            }
        }
    }

    void drawFloatingTomatoes() {
        float amplitude = v.getSmoothedAmplitude();
        float tomatoSize = PApplet.map(amplitude, 0, 1, 20, 1000) * 75; // Map amplitude to larger tomato size range
        float rotationSpeed = 0.02f; // Adjust the rotation speed

        for (int i = 0; i < numTomatoes; i++) {
            if (isInsideCylinder(tomatoPositions[i])) {
                v.pushMatrix();
                v.translate(tomatoPositions[i].x, tomatoPositions[i].y, tomatoPositions[i].z);
                v.scale(tomatoSize / 75.0f); // Scale the tomato size based on amplitude
                float tomatoRotationY = v.frameCount * rotationSpeed + i * 0.1f;
                float tomatoRotationZ = v.frameCount * rotationSpeed + i * 0.2f;
                v.rotateY(tomatoRotationY);
                v.rotateZ(tomatoRotationZ);
                v.rotateY(v.yaw);
                v.rotateZ(v.rol);
                tomato(v);
                v.popMatrix();
            }
        }
    }

    boolean isInsideCylinder(PVector position) {
        float d = PApplet.dist(0, 0, position.x, position.y);
        return d <= tubeRadius && position.z >= -tubeLength / 2 && position.z <= tubeLength / 2;
    }

    void drawCylinder() {

        float baseStrokeWeight = 3;
        float maxStrokeWeight = 8;
        float angleIncrement = PConstants.TWO_PI / numSegments;
        float rotationAngle = v.frameCount * 0.001f;
        float colour = PApplet.map(count, 0, v.getAudioBuffer().size(), 100, 400);

        v.noFill();

        v.beginShape(PConstants.LINES);
        float zStart = -tubeLength / 2;
        float zEnd = tubeLength / 2;

        for (float z = zStart; z < zEnd; z += tubeLength / numSegments) {
            for (int i = 0; i <= numSegments; i++) {
                float angle1 = angleIncrement * i + rotationAngle;
                float angle2 = angleIncrement * (i + 1) + rotationAngle;
                angle1 = angle1 % PConstants.TWO_PI;
                angle2 = angle2 % PConstants.TWO_PI;
                float x1 = PApplet.cos(angle1) * tubeRadius;
                float y1 = PApplet.sin(angle1) * tubeRadius;
                float x2 = PApplet.cos(angle2) * tubeRadius;
                float y2 = PApplet.sin(angle2) * tubeRadius;
                int index = i % numSegments;
                float amp1 = v.getFFT().getBand(index) * 50;
                float amp2 = v.getFFT().getBand((index + 1) % numSegments) * 50;
                v.stroke((v.hueShift(colour) + baseColour + amp1) % 255, 255, 255, v.alp);
                baseColour += 0.0003;
                float mappedStrokeWeight = PApplet.map(amp2, 0, 255, baseStrokeWeight, maxStrokeWeight);
                v.strokeWeight(mappedStrokeWeight);
                v.vertex(x1, y1, z);
                v.vertex(x2, y2, z);
            }
        }
        v.endShape();
    }

    // Method to render the Chorus visual
    public void render() {
        v.camera(v.width / 2, v.height / 2, (v.height / 2) / PApplet.tan(PApplet.PI / 6), v.width / 2,
                v.height / 2, 0, 0, 1, 0);

        v.ambientLight(128, 128, 128);
        v.directionalLight(192, 192, 192, -1, -1, -1);

        v.lights();
        v.translate(v.width / 2, v.height / 2);
        drawCylinder();
        updateFloatingTomatoes();
        drawFloatingTomatoes();
    }
}
