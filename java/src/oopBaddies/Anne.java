package oopBaddies;

import ie.tudublin.IAMDANI;
import ie.tudublin.Poly;
import ie.tudublin.Visual;

//Start of the main 
public class Anne extends Poly
// start of the main
{
 
    int flowerX;
    int flowerY;

    public Anne(IAMDANI anne)
    // start of public Anne
    {
        super(anne);

        // used to make the flower add more circles for the flower petals
        //frameCount = 0;

        // End of the public anne
    }

    // using this for the flower instead of a frameRate
    // public float getAmplitude() {
    //     float[] flowers = getAudioBuffer().toArray();
    //     float sum = 0;

    //     for (int i = 0; i < flowers.length; i++) {
    //         sum += flowers[i] * flowers[i];

    //     }

    //     float rms = (float) Math.sqrt(sum / (float) flowers.length);
    //     return rms;
    // }

    // function to draw the clouds
    public void cloud(int a, int b, int c, float d) {
        v.noStroke();
        v.fill(255);
        v.ellipse(a + 530, b + 295, c + 60, d + 30);
        v.ellipse(a + 510, b + 305, c + 40, d + 30);
        v.ellipse(a + 460, b + 295, c + 40, d + 30);
        v.ellipse(a + 480, b + 305, c + 40, d + 30);
        v.ellipse(a + 470, b + 285, c + 40, d + 30);
        v.ellipse(a + 510, b + 285, c + 40, d + 30);
        v.ellipse(a + 490, b + 280, c + 40, d + 30);

    }// End of fucntion to draw the clouds

    // function to draw the ground
    // rect(x-co, y-co, width, height)
    public void ground() {
        v.stroke(114, 180, 58);
        v.fill(65, 101, 34);
        v.rect(0, 850, 2000, 500);

    }// End of funtion to draw ground

    // funtion to draw the sun
    // ellipse(x-co, y-co, width, height)
    public void sun() {
        v.pushMatrix();
        v.smooth();
        v.noSmooth();

        v.fill(245, 187, 87);
        v.ellipse(224, 184, 220, 220);

        v.popMatrix();

    }// end of the function to draw the sun

    // Funtion to draw the flower
    public void flowers() {

        // Using this instead of the FrameRate()
        float amplitude = v.getSmoothedAmplitude();

        // flower 1
        // makes the flower rotate without affecting the background
        v.pushMatrix();
        v.smooth();
        v.noStroke();

        v.translate(v.width / 2, v.height / 2);
        v.translate(-100, 100);

        // makes the flower rotate maybe this time???
        v.rotate(v.radians(amplitude * 180));

        // draws the flower petals
        v.fill(191, 155, 48);
        for (int i = 0; i < 5; i++) {
            // ellipse(x-co, y-co, width, height)
            v.ellipse(0, -40, 50, 50);
            v.rotate(v.radians(72));
        }

        // stem 1
        // so the line doesnt rotate with the flower
        v.pushMatrix();
        v.rotate(-v.radians(amplitude * 180));
        v.stroke(41, 63, 22);
        v.strokeWeight(3);
        v.line(0, 44, 0, 255);
        // end of stem popMatrix()
        v.popMatrix();

        // the center of the flower
        v.stroke(141, 85, 36);
        v.fill(141, 85, 36);
        v.ellipse(0, 0, 50, 50);

        // end of the pushMatrix
        v.popMatrix();

        // flower 2
        // makes the flower rotate without affecting the background
        v.pushMatrix();
        v.smooth();
        v.noStroke();

        v.translate(v.width / 2, v.height / 2);
        v.translate(-200, -100);

        // makes the flower rotate using the audio instead of a framRate
        v.rotate(v.radians(amplitude * 360));

        // draws the flower petals
        v.fill(141, 158, 199);
        for (int i = 0; i < 5; i++) {
            // ellipse(x-co, y-co, width, height)
            v.ellipse(0, -40, 50, 50);
            v.rotate(v.radians(72));
        }

        // stem 2
        // so the line doesnt rotate with the flower
        v.pushMatrix();
        // - amplitude so it the line doesnt spin with the music
        v.rotate(-v.radians(amplitude * 360));
        v.stroke(41, 63, 22);
        v.strokeWeight(3);
        v.line(0, 40, 0, 450);
        // end of stem popMatrix()
        v.popMatrix();

        // the center of the flower
        v.stroke(195, 227, 220);
        v.fill(195, 227, 220);
        v.ellipse(0, 0, 50, 50);

        // end of the pushMatrix flower 2
        v.popMatrix();

        // flower 3
        // makes the flower rotate without affecting the background
        v.pushMatrix();
        v.smooth();
        v.noStroke();

        v.translate(v.width / 2, v.height / 2);
        v.translate(200, 200);

        // makes the flower rotate using the audio instead of a framRate
        v.rotate(v.radians(amplitude * 60));

        // draws the flower petals
        v.fill(246, 234, 219);
        for (int i = 0; i < 5; i++) {
            // ellipse(x-co, y-co, width, height)
            v.ellipse(0, -40, 50, 50);
            v.rotate(v.radians(72));
        }

        // stem 2
        // so the line doesnt rotate with the flower
        v.pushMatrix();
        // - amplitude so it the line doesnt spin with the music
        v.rotate(-v.radians(amplitude * 60));
        v.stroke(41, 63, 22);
        v.strokeWeight(3);
        v.line(0, 40, 0, 150);
        // end of stem popMatrix()
        v.popMatrix();

        // the center of the flower
        v.stroke(255, 200, 109);
        v.fill(255, 200, 109);
        v.ellipse(0, 0, 50, 50);

        // end of the pushMatrix flower 3
        v.popMatrix();

        // flower 4
        // makes the flower rotate without affecting the background
        v.pushMatrix();
        v.smooth();
        v.noStroke();

        v.translate(v.width / 2, v.height / 2);
        v.translate(-400, 50);

        // makes the flower rotate using the audio instead of a framRate
        v.rotate(v.radians(amplitude * 250));

        // draws the flower petals
        v.fill(149, 125, 173);
        for (int i = 0; i < 5; i++) {
            // ellipse(x-co, y-co, width, height)
            v.ellipse(0, -40, 50, 50);
            v.rotate(v.radians(72));
        }

        // stem 4
        // so the line doesnt rotate with the flower
        v.pushMatrix();
        // -amplitude so it the line doesnt spin with the music
        v.rotate(-v.radians(amplitude * 250));
        v.stroke(41, 63, 22);
        v.strokeWeight(3);
        v.line(0, 40, 0, 300);
        // end of stem popMatrix()
        v.popMatrix();

        // the center of the flower
        v.stroke(255, 223, 211);
        v.fill(255, 223, 211);
        v.ellipse(0, 0, 50, 50);

        // end of the pushMatrix flower 4
        v.popMatrix();

    } // End of the funtion to draw the flower

    // Start of the draw render
    public void render() {
        // Calculating the average amplitude

        v.colorMode(v.RGB);

        // renders the sun
        sun();

        // renders the flower maybe??
        flowers();

        // render the ground
        ground();

        // render for the clouds
        v.fill(0, 10);
        v.fill(255);
        v.noStroke();

        // making the circle bigger representing the height anf width
        if (v.frameCount % 30 == 00) {
            v.ellipse((v.width), (v.height), v.getSmoothedAmplitude(), v.getSmoothedAmplitude());
        }

        v.translate(v.width / 2, v.height / 2, 0);

        float smoothedavg = v.getSmoothedAmplitude();
        // renders the clouds
        cloud(-600, -544, 10, 30 * smoothedavg * 200);
        cloud(-800, -644, 10, 30 * smoothedavg * 200);
        cloud(-1000, -644, 10, 30 * smoothedavg * 200);
        cloud(-100, -544, 10, 30 * smoothedavg * 200);
        cloud(-300, -600, 10, 30 * smoothedavg * 200);
        cloud(0, -644, 10, 30 * smoothedavg * 200);

    }// End of the void render

}// End of the main