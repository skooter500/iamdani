package C22396101;
import ie.tudublin.*;
import processing.core.PApplet;
//import processing.core.PShape;
//import processing.core.PVector;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
// import java.awt.*;
// import javax.swing.*;
// import jn ava.awt.geom.GeneralPath;

public class EllaVisual extends Poly {
    //WaveForm wf;
    //AudioBandsVisual abv;
    int mode = 0;
    float angle = 0;
    
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    public EllaVisual(IAMDANI v) 
    {
        super(v);
    }

    float ry;
    // float c = map(getAmplitude(), 0, 500, 0, 255);

    float maxAmplitude; // Declare maxAmplitude variable

    //code to smooth amplitude 
    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;

    public void settings() {
        //size(2048, 1000, P3D);

        // Use this to make fullscreen
        //fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        v.fullScreen(PApplet.P3D, PApplet.SPAN);
    }

    int numColors = 360; // Number of colors in the rainbow
    int[] colors = new int[numColors]; // Array to hold rainbow colors

    public void setup()
     {
        v.frameRate(30);
        v.startMinim();

        minim = new Minim(this);
        // Uncomment this to use the microphone
        // ai = minim.getLineIn(Minim.MONO, width, 44100, 16);
        // ab = ai.mix; 
        //ap = minim.loadFile("data\\Post Malone, Swae Lee - Sunflower (Spider-Man_ Into the Spider-Verse) (256 kbps).mp3", 1024);
        ap.play();
        ab = ap.mix;

        // Call loadAudio to load an audio file to process
        //loadAudio("data\\Post Malone, Swae Lee - Sunflower (Spider-Man_ Into the Spider-Verse) (256 kbps).mp3");

        // Call this instead to read audio from the microphone
         // startListening();

        //wf = new WaveForm(this);
        //abv = new AudioBandsVisual(this);
        // hexagonTunnel = new EndlessHexagonTunnel(this);

        y = v.height / 2;
        smoothedY = y;

        lerpedBuffer = new float[v.width];

    // Generate rainbow colors (not used for drawing)
    for (int i = 0; i < numColors; i++) 
    {
       colors[i] = v.color(i, 100, 100); // Hue ranges from 0 to 360
    }

    }

    float off = 0;

    public void render() {
    v.background(0);

    // Call this is you want to use frequency bands
    //calculateFrequencyBands();

    // Call this is you want to get the average amplitude
    v.calculateAverageAmplitude();
    //wf.render();
    //abv.render();

    //float c = map(amplitude, 0, 500, 0, 255);
    // fill(c, 0, 0);
    float amplitude = v.getAmplitude();

         //Ella
            // hexagonTunnel.repaint()
            v.colorMode(PApplet.HSB); // Set color mode to HSB

            drawHexagons(v.width / 2.0f, v.height / 2.0f, 400.0f + v.getAmplitude(), 20, v.getSmoothedBands());
            float c = PApplet.map(amplitude, 0, 500, 0, 255);
            //fill(c, 0, 0);
            v.fill(c, 255, 255);
    
}

    //hexagon, stars, shooting stars, confetti and lines all added for Ella's hexagon visual
    void drawHexagons(float x, float y, float outerRadius, int numHexagons, float[] ab) 
    {
        if (ab == null) {
            return; // Exit the method early if ab is null
        }
        float angleStep = PApplet.TWO_PI / 6;
        float maxAmplitude = PApplet.max(ab);
        float gap = 10; // Gap between hexagons
        float maxOuterRadius = 400.0f + maxAmplitude;//calculate max outer radius dynamically based on max amplitude

        
        for (int i = 0; i < numHexagons; i++) 
        {
            float innerRadius = maxOuterRadius * (numHexagons - i) / numHexagons;//ensures that innerRadius decreases as i increases,
            // resulting in smaller inner radii towards the center of the hexagon arrangement.
            float brightness = PApplet.map(ab[i % ab.length], 0, maxAmplitude, 50, 100); // Reactivity to music
            float newSize = PApplet.map(ab[i % ab.length], 0, maxAmplitude, 50, 400); // Adjust size based on frequency


            if (i % 2 == 0) {
                // Set blue color (RGB values)
                v.fill(0, 0, 255, brightness); // Blue color
            } else {
                // Set orange color (RGB values)
                v.fill(255, 165, 0, brightness); // Orange color
            }

             // Draw the hexagon with the adjusted size
            drawHexagon(x, y, newSize);

            // Set the fill color for confetti (random colors)
            float confettiHue = v.random(360);
            float confettiSaturation = v.random(90, 255); // Random saturation between 90 and 255
            float confettiBrightness = v.random(90, 255); // Random brightness between 90 and 255
            v.fill(confettiHue, confettiSaturation, confettiBrightness);

            v.beginShape();
            for (int j = 0; j < 6; j++) {
                float angle = j * angleStep;
                float hx = x + PApplet.cos(angle) * innerRadius;
                float hy = y + PApplet.sin(angle) * innerRadius;
                v.vertex(hx, hy);
            }
            v.endShape(PApplet.CLOSE);

            // Draw stars, circles, rectangles, and shooting stars in the gap
            drawStars(x, y, maxOuterRadius, innerRadius, ab[i % ab.length]);
            //drawShootingStars(x, y, outerRadius, innerRadius, ab[i % ab.length]);
            drawConfetti(x, y, innerRadius, maxOuterRadius, ab[i % ab.length]);


            maxOuterRadius -= gap; // Adjust the outer radius for the next hexagon
        }
    }

    void drawHexagon(float x, float y, float size) {
        float angleStep = PApplet.TWO_PI / 6;
        v.beginShape();
        for (int j = 0; j < 6; j++) {
            float angle = j * angleStep;
            float hx = x + PApplet.cos(angle) * size;
            float hy = y + PApplet.sin(angle) * size;
            v.vertex(hx, hy);
        }
        v.endShape(PApplet.CLOSE);
    }
    
    // void drawLines(float x1, float y1, float x2, float y2) {
    //     v.stroke(255); // Set the color of the lines to white
    //     v.line(x1, y1, x2, y2); // Draw a line between the given points
    // }
    
    void drawStars(float x, float y, float outerRadius, float innerRadius, float amplitude) {
        float gap = 10; // Gap between hexagons
        int numStars = PApplet.floor((outerRadius - innerRadius) / gap); // Number of stars based on the gap size
        float minStarSize = 1;
        float maxStarSize = 3;

        //float previousStarX = x;
        //float previousStarY = y;

        // Set purple color (RGB values)
        v.fill(128, 0, 128); // Purple color

        for (int i = 0; i < numStars; i++) {
            float randomAngle = v.random(PApplet.TWO_PI);
            float randomDistance = v.random(innerRadius, outerRadius);
            float starX = x + PApplet.cos(randomAngle) * randomDistance;
            float starY = y + PApplet.sin(randomAngle) * randomDistance;
            float starSize = v.random(minStarSize, maxStarSize);
            v.ellipse(starX, starY, starSize, starSize);

            // Call the drawLines function to draw lines between stars
            //drawLines(starX, starY, previousStarX, previousStarY);
        }
    }
    
    
    // void drawShootingStars(float x, float y, float outerRadius, float innerRadius, float amplitude) {
    //     // Draw shooting stars between hexagons with a different color
    //     float gap = 10; // Gap between hexagons
    //     int numStars = PApplet.floor((outerRadius - innerRadius) / gap); // Number of shooting stars based on the gap size
    //     float minStarSize = 1;
    //     float maxStarSize = 3;

    //     // Set shooting star color (RGB values)
    //     v.fill(255, 255, 0); // Yellow color

    //     for (int i = 0; i < numStars; i++) {
    //         float randomAngle = v.random(PApplet.TWO_PI);
    //         float randomDistance = v.random(innerRadius, outerRadius);
    //         float starX = x + PApplet.cos(randomAngle) * randomDistance;
    //         float starY = y + PApplet.sin(randomAngle) * randomDistance;
    //         float starSize = v.random(minStarSize, maxStarSize);
    //         v.ellipse(starX, starY, starSize, starSize);
    //     }
    // }
    
    void drawConfetti(float x, float y, float innerRadius, float outerRadius, float amplitude) {
         int numConfetti = 50; // Number of confetti particles
         float minConfettiSize = 2;
         float maxConfettiSize = 5;

        for (int i = 0; i < numConfetti; i++) {
             // Random position within the hexagon
            float randomAngle = v.random(PApplet.TWO_PI);
             float randomDistance = v.random(innerRadius, outerRadius);
             float confettiX = x + PApplet.cos(randomAngle) * randomDistance;
             float confettiY = y + PApplet.sin(randomAngle) * randomDistance;

             // Random confetti size
             float confettiSize = v.random(minConfettiSize, maxConfettiSize);

             // Draw confetti particle
             v.ellipse(confettiX, confettiY, confettiSize, confettiSize);
         }
    }
  
    public static void main(String[] args) {
        PApplet.main("C22396101.EllaVisual");
    }
}
