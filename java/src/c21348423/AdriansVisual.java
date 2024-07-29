package c21348423;

import ie.tudublin.IAMDANI;
import ie.tudublin.visual.AudioAnalysis;
import ie.tudublin.visual.EaseFunction;
import ie.tudublin.visual.VAnimation;
import ie.tudublin.visual.VObject;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

/**
 * AdriansVisual
 * 1:48 - 2:30 - Instrumental
 * Adrian's visuals present a dynamic and engaging Music Visualizer scene
 * featuring a horse called Pony Hopps, a rainbow stage, and a starry background
 * of superellipses.
 *
 * Pony Hopps is a 3D object imported into the scene, illuminated with
 * purple/red lights. The horse dynamically moves and hops in sync with the
 * music while the camera perspective shifts around it. The character was
 * inspired by Maxwell the Cat, who spins happily to a cheerful song.
 *
 * The stage is a visually reactive disk composed of rainbow-colored arcs, which
 * respond to the music by changing their length and color, creating a vibrant
 * and mesmerizing experience.
 *
 * The background consists of a grid of superellipses that adapt their size and
 * color according to the music, enhancing the scene's atmosphere and
 * complementing Pony Hopps and the stage.
 *
 * Together, Adrian's visuals provide a captivating and immersive environment
 * that beautifully showcases the integration of 3D elements, color palettes,
 * animations, and audio for a unique Music Visualizer.
 */
public class AdriansVisual extends VScene {

    public final int BPM = 96;
    public final float BEATMS = 1 / (1000 / (BPM / 60.0f)); // Multiply with elapsed to get beat
    private final float HALF_PI = PApplet.HALF_PI;
    private float PI = PApplet.PI;
    private float TWO_PI = PApplet.TWO_PI;

    private PVector rotationOff;
    private VAnimation sceneVisibility;
    private Visual v;
    private SquigglyArcs squigglyArcs;
    private VAnimation horseBounceAnimation;
    private HappyHorse horse;
    private SuperStars superStars;

    private int background;

    public AdriansVisual(IAMDANI v) {
        super(v);
        this.v = v;
        background = v.color(0, 0, 100);
        rotationOff = new PVector(0, 0, 0);

        // Visual objects
        horse = new HappyHorse(v, new PVector(0, 0, 0));
        squigglyArcs = new SquigglyArcs(v, new PVector(0, 0, 0));
        superStars = new SuperStars(v, new PVector(0, 0, 0));

        setSceneAnimation();
    }

    int length = 500000;

    /**
     * Set up scene animation
     */
    public void setSceneAnimation() {
        // Scene visibility
        sceneVisibility = new VAnimation(length);
        sceneVisibility.addTransition(v.toMs(1, 48, 0), 0, 0, 100, EaseFunction.easeLinear);
        sceneVisibility.addTransition(v.toMs(2, 31, 0), 0, 100, 0, EaseFunction.easeLinear);
        sceneVisibility.addTransition(length, 0, 0, 0, EaseFunction.easeLinear);

        // Horse bounce animation
        horseBounceAnimation = new VAnimation(length);
        horseBounceAnimation.addTransition(v.toMs(1, 48, 0), 1000, -500, 0, EaseFunction.easeOutBounce);
        horseBounceAnimation.addTransition(length, 0, 0, 0, EaseFunction.easeLinear);
    }

    public void render(int elapsed) {

        // Background
        v.blendMode(PApplet.BLEND);
        v.fill(background, 30);

        v.translateCenter();

        v.pushMatrix();
        v.translate(0, 0, -2000);
        v.rectMode(PApplet.CENTER);
        v.rect(0, 0, v.width * 4, v.height * 4);
        v.popMatrix();

        // Star background
        v.pushMatrix();
        v.rotateX(Visual.sin(-elapsed * BEATMS * HALF_PI * 0.1f) * HALF_PI / 6 - HALF_PI / 6);
        superStars.render(elapsed);
        v.popMatrix();

        // End background

        // Light up the horse
        v.ambientLight(300, 100, 100);
        v.pointLight(0, 100, 100, 100, -v.height, 1000);

        // Rotate the stage
        rotation.x = Visual.sin(-elapsed * BEATMS * HALF_PI * 0.1f + PI) * HALF_PI / 2 - HALF_PI / 2;
        rotation.y = Visual.sin(elapsed * BEATMS * TWO_PI) * 0.3f;
        rotation.y = elapsed * BEATMS * 0.3f;
        rotation.add(rotationOff);
        applyTransforms();

        // The Squiggly Arcs stage
        squigglyArcs.render(elapsed);


        // Pony Hopps the Happy Horse
        v.blendMode(PApplet.BLEND);
        v.translate(horseBounceAnimation.getValue(elapsed), horseBounceAnimation.getValue(elapsed), 0);
        horse.render(elapsed);

        v.popMatrix();
        rotateControls();
    }

    /**
     * Stage rotation controls using mouse down and drag
     */
    int targetX = 0;
    int targetY = 0;

    private void rotateControls() {
        if (v.mousePressed) {
            targetX -= v.mouseY - v.pmouseY;
            targetY += v.mouseX - v.pmouseX;
        } else {
            targetX *= 0.9f;
            targetY *= 0.9f;
        }
        rotationOff.x = PApplet.lerp(rotationOff.x, targetX / 100f, 0.9f); // adjust rate as needed
        rotationOff.y = PApplet.lerp(rotationOff.y, targetY / 100f, 0.9f); // adjust rate as needed
    }

    /**
     * Pony Hopps the Happy Horse
     */
    class HappyHorse extends VObject {
        PShape horse;

        HappyHorse(IAMDANI v, PVector pos) {
            super(v, pos);
            horse = v.loadShape("horse.obj");
        }

        @Override
        public void render(int elapsed) {
            applyTransforms();
            v.scale(40);

            // v.rotateZ(Visual.sin(elapsed / 1000f * TWO_PI) + PI);
            v.rotateZ(Visual.sin(elapsed * BEATMS * HALF_PI) / 2 + PI);
            v.translate(0, Visual.sin(elapsed * BEATMS * TWO_PI) * 2 + 2);
            // v.translate(0,200);
            v.shape(horse, 0, 2);
            v.popMatrix();
        }
    }

    /**
     * SquigglyArcs
     */
    public class SquigglyArcs extends VObject {
        private AudioAnalysis aa;

        public SquigglyArcs(IAMDANI v, PVector position) {
            super(v, position);
            aa = v.audioAnalysis();
        }

        @Override
        public void render(int elapsed) {
            v.colorMode(PApplet.HSB);
            float lerpedAmplitude = aa.mix().lerpedAmplitude;
            float[] lerpedSpectrum = aa.mix().lerpedSpectrum;
            // 1000 / 60
            // float off = 16.6666666667f / elapsed;
            float off = 1 / elapsed;

            v.pushMatrix();
            v.rectMode(PApplet.CENTER);

            v.translate(0, 200, 0);

            v.rotateX(HALF_PI);
            v.scale(2);
            v.noFill();

            float sinMap = PApplet.map(PApplet.sin(off * 1.00001f / (60 * 3)), -1, 1, 2.090f, 2.110f);

            // How many arcs to render
            int count = (int) PApplet.map(lerpedAmplitude * 4, 0, 0.2f, 0, lerpedSpectrum.length - 1);
            count += PApplet.constrain((int) PApplet.map(lerpedAmplitude, 0.01f, 0, 0, lerpedSpectrum.length - 1), 0,
                    lerpedSpectrum.length);

            int color;
            float f, r;

            for (int i = 100; i < PApplet.constrain(count, 100, lerpedSpectrum.length) - 1; i += 2) {
                color = v.color(
                        (PApplet.map(i, 100, lerpedSpectrum.length, 0, 360) + 360 * 2 - 120) % 360,
                        100,
                        100,
                        PApplet.map(i, 0, lerpedSpectrum.length, 100, 50) + (lerpedAmplitude * 2 * 100));
                v.stroke(color);

                f = lerpedSpectrum[i] * HALF_PI; //
                f += 0.1f;

                r = PApplet.sin(i * sinMap) / 1 * PI / 2; // Sine wave rotation
                r += PApplet.sin(off / (120)) / 1 * PI / 2; // Sine wave rotation
                r += v.millis() / 1000f; // 1 rotation per second

                // Two arcs
                v.arc(0, 0, i, i, r - f, r + f);
                r += PI;
                v.arc(0, 0, i, i, r - f, r + f);

            }
            v.popMatrix();
        }

    }

    /**
     * Constillation of superellipses
     */
    private class SuperStars extends VObject {

        public SuperStars(IAMDANI v, PVector position) {
            super(v, position);
        }

        @Override
        public void render(int elapsed) {
            v.pushMatrix();
            float lerpedAmplitude = v.audioAnalysis().mix().lerpedAmplitude * 10;

            v.blendMode(PApplet.SUBTRACT);
            v.noFill();

            float hue = (-120 + PApplet.round(3.5f + (lerpedAmplitude) * 8) * 120 + 360) % 360;
            float sat = 100;
            float val = 100;
            if (lerpedAmplitude > 0.6f) {
                sat = 0;
            }
            v.stroke(hue, sat, val);

            // Render grid of superellipses
            v.translate((-elapsed / 10) % 200, 0, -100);
            for (int x = -200 - v.width; x < v.width + 200; x += 200) {
                for (int y = -200 - v.height; y < v.height + 200; y += 200) {
                    superellipse(x, y, 0.5f * lerpedAmplitude, 1.0f * lerpedAmplitude, 150 * lerpedAmplitude + 10,
                            150 * lerpedAmplitude + 10);
                }
            }

            v.popMatrix();
        }

        /**
         * Superellipse
         *
         * @param x
         * @param y
         * @param a
         * @param b
         * @param n
         * @param x
         * @return
         */
        public void superellipse(float posX, float posY, float m, float n, float a, float b) {
            v.beginShape();
            for (int i = 0; i < 360; i++) {
                float t = i * TWO_PI / 360;
                float x = PApplet.pow(PApplet.abs(PApplet.cos(t)), 2 / m) * a * Math.signum(PApplet.cos(t));
                float y = PApplet.pow(PApplet.abs(PApplet.sin(t)), 2 / n) * b * Math.signum(PApplet.sin(t));
                v.vertex(posX + x, posY + y);
            }
            v.endShape();
        }

    }
}
