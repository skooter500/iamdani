package global;

import ie.tudublin.visual.EaseFunction;
import ie.tudublin.visual.VAnimation;
import ie.tudublin.visual.VObject;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;

public class AnimationDemo extends VScene {
    WooCircle circle;

    public AnimationDemo(Visual v) {
        super(v);
        circle = new WooCircle(v);
    }

    @Override
    public void render(int elapsed) {
        elapsed = elapsed % 5000;
        v.background(0);

        v.pushMatrix();
        v.translateCenter();
        v.text(v.getLyrics(0), 0, 0);
        circle.render(elapsed);
        circle.debugPath();
        v.popMatrix();
    }

    class WooCircle extends VObject {
        VAnimation animX;
        VAnimation animY;

        public WooCircle(Visual v) {
            super(v);
            animX = new VAnimation(5000);
            animY = new VAnimation(5000);

            animX.addTransition(0, 5000, -v.width / 2, v.width / 2, EaseFunction.easeLinear);

            // Static section automatically added from start to first transition
            // Go down linearly
            animY.addTransition(1000, 1000, 0, 500, EaseFunction.easeLinear);
            // Straight up then bounce down
            animY.addTransition(2000, 500, 0, 500, EaseFunction.easeOutBounce);
            // Section added automatically between end of last transition and start of next
            animY.addTransition(3000, 999, 540, 0, EaseFunction.easeSmoothstep);
            // Go up
            animY.addTransition(4000, 500, 0, -500, EaseFunction.easeOutQuad);
            // Back to middle
            animY.addTransition(4500, 1000, -500, 0, EaseFunction.easeLinear);

        }

        public void render(int elapsed) {
            v.fill(255);
            v.stroke(255);
            v.circle(animX.getValue(elapsed), animY.getValue(elapsed),
                    v.audioAnalysis().mix().lerpedAmplitude * 400 + 10);
            v.fill(255);
        }

        public void debugPath() {
            v.beginShape();
            v.noFill();
            v.stroke(255);
            for (int i = 0; i < 4999; i++) {
                v.vertex(animX.getValue(i), animY.getValue(i));
            }
            v.endShape();
        }

    }

}
