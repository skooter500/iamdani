package c22375341;

import ie.tudublin.*;
import ie.tudublin.IAMDANI;
import ie.tudublin.Poly;
import processing.core.PApplet;

public class SebsVisual extends Poly
{

    // MainVisual main;

    //decalreing variables
    float angle = 0;
    float angleX = 200;
    float angleY = 0;
    float speedX = 0.05f;
    float speedY = 0.05f;
    int targetSphereDetail = 0;
    int currentSphereDetail = 0;
    float lerpAmount = 0.1f;

    public SebsVisual(IAMDANI v) {
        super(v);
    }

    
    public void render()
    {
        v.calculateAverageAmplitude();
        v.colorMode(Visual.HSB);
    
        targetSphereDetail = (int) ((200 * v.getSmoothedAmplitude()));
    
        // Smoothly interpolate between current and target sphereDetail
        currentSphereDetail = (int) PApplet.lerp(currentSphereDetail, targetSphereDetail, lerpAmount);
        v.sphereDetail(currentSphereDetail); // Set sphere detail to the lerped amplitude value
        v.background(0);
        v.stroke(Visual.map(10*v.getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255); // Setting  stroke color to do cool things
        v.strokeWeight(1);
    
        // noStroke();
    
        // Set the light direction color based on the amplitude to shine on the top and bottom halves of the sphere
        v.directionalLight(v.getSmoothedAmplitude() * 10, 700, 206, 0, -1, 0); 
        v.directionalLight(v.getSmoothedAmplitude() * 1500, 500, 126, 0, 1, 0);
    
    
        // Generate random angles for spotlight positions
        float randomAngleX = v.random(Visual.TWO_PI);
        float randomAngleY = v.random(Visual.TWO_PI);
    
        // Update angles for animation
        randomAngleX += speedX;
        randomAngleY += speedY;
    
        // Calculate spotlight positions
        float spotlightX = v.width / 2 + 90 * PApplet.sin(randomAngleX);
        float spotlightY = v.height / 2 + 90 * PApplet.sin(randomAngleY);
    
    
        //spotlights in the sphere moving sporadically
        v.spotLight(220, 700, 500, spotlightY, spotlightY, 600, 0, 0, -1, Visual.PI/2, 600);
        v.spotLight(130, 700, 500, spotlightX, spotlightY, 600, 0, 0, -1, Visual.PI/2, 600);

    
        v.pushMatrix();
        
        v.camera(0, 0, 0, 0, 0, -1, 0, 1, 0); // setting camera position
    
    
        // rotating the sphere
        v.translate(0, 0, -200);
        v.rotateX(angle);
        v.rotateZ(angle);   
    
        // setting the sphere size to move with the ampltide to add a bit more movement 
        float sphereSize = 20 + (150 * v.getSmoothedAmplitude()); 
        v.sphere(sphereSize);
    
        v.popMatrix();
        angle += 0.01f;
    
    
        int gridSize = 10;
    
        // having a brackgournd grid with a smaller grid size so the spotlights can reflect off the grid 
        for (int x = gridSize; x <= v.width - gridSize; x += gridSize) {
            for (int y = gridSize; y <= v.height - gridSize; y += gridSize) {
                v.noStroke();
                v.fill(255);
                v.rect(x-1, y-1, 3, 3);
                v.stroke(255, 0);
                v.line(x, y, v.width/2, v.height/2);
            }
        }
    
        gridSize = 50;
        // grid with larger gridsize with lines pointing to every corner
        for (int x = gridSize; x <= v.width - gridSize; x += gridSize) {
            for (int y = gridSize; y <= v.height - gridSize; y += gridSize) {
                float c = Visual.map(v.getSmoothedAmplitude()*10*y, 0, v.getAudioBuffer().size(), 0, 255); // color mapping
                v.stroke(c, 4000*v.getSmoothedAmplitude(), 255); // setting stroke color
                v.rect(x-1, y-1, 1, 1); 
                v.line(x, y, v.width/2, v.height/2);
    
            }
        }
    }
}