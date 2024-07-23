package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PShape;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

public class AliensOnUranus extends Art {
    private ArrayList<Star> stars;
    private ArrayList<UFO_Model> ships;
    private int lastFrame;

    // Crowd setup
    private PImage img1, img2, img3, img4, img5, img6;
    private float crowd_x, crowd_y1, crowd_y2;
    private int crowd_y_switch;

    // DJ desk setup
    private float angle1, angle2, radius, speed;
    private float table_x, table_y;
    private int direction;

    // DJ animation setup
    private int fps, cycle;
    private boolean wf;
    private float r, g, b, a;

    // DJ animation frames
    private DJ_Model pR, p1f1, p1f2, p1f3, p1f4, p2f1, p2f2, p2f3, p2f4;

    // MSX logo setup
    private MSX_Model msx_left, msx_right;

    public AliensOnUranus(Visual v) {
        super(v);
        //setup();
    }

    public void enter()
    {
        stars = new ArrayList<>();
        ships = new ArrayList<>();
        lastFrame = 0;

        // Crowd setup
        setupCrowd();

        // DJ desk setup
        setupDJDesk();

        // DJ animation setup
        setupDJAnimation();

        // MSX logo setup
        setupMSXLogo();

        // UFO setup
        setupUFOs();

        v.cqz = 255;
    }


        

    private void setupCrowd() {
        img1 = v.loadImage("alien_crowd/alien1.png");
        img2 = v.loadImage("alien_crowd/alien2.png");
        img3 = v.loadImage("alien_crowd/alien3.png");
        img4 = v.loadImage("alien_crowd/alien4.png");
        img5 = v.loadImage("alien_crowd/alien5.png");
        img6 = v.loadImage("alien_crowd/alien6.png");

        crowd_x = 500;
        crowd_y1 = 500;
        crowd_y2 = 525;
        crowd_y_switch = 0;
    }

    private void setupDJDesk() {
        angle1 = 0;
        angle2 = 40;
        radius = 100;
        speed = 2;

        table_x = 770;
        table_y = 730;
        direction = 1;
    }

    private void setupDJAnimation() {
        fps = 0;
        cycle = 0;
        wf = false;

        r = v.random(0, 256);
        g = v.random(0, 256);
        b = v.random(0, 256);
        a = 255;

        assignPose();
    }

    private void assignPose() {
        pR = new DJ_Model("dj_anims/dj_anim_rest.obj", 0, 500, 500, 5);
        
        p1f1 = new DJ_Model("dj_anims/p1/dj_anim_p1_f1.obj", 0, 700, 500, 5);
        p1f2 = new DJ_Model("dj_anims/p1/dj_anim_p1_f2.obj", 0, 700, 500, 5);
        p1f3 = new DJ_Model("dj_anims/p1/dj_anim_p1_f3.obj", 0, 700, 500, 5);
        p1f4 = new DJ_Model("dj_anims/p1/dj_anim_p1_f4.obj", 0, 700, 500, 5);
        
        p2f1 = new DJ_Model("dj_anims/p2/dj_anim_p2_f1.obj", 0, 500, 500, 5);
        p2f2 = new DJ_Model("dj_anims/p2/dj_anim_p2_f2.obj", 0, 500, 500, 5);
        p2f3 = new DJ_Model("dj_anims/p2/dj_anim_p2_f3.obj", 0, 500, 500, 5);
        p2f4 = new DJ_Model("dj_anims/p2/dj_anim_p2_f4.obj", 0, 500, 500, 5);
    }

    private void setupMSXLogo() {
        msx_left = new MSX_Model("msx.obj", v.width * 0.25f, v.height * 0.75f, 300, 35, 200);
        msx_right = new MSX_Model("msx.obj", v.width * 0.75f, v.height * 0.75f, 300, 35, 200);
    }

    private void setupUFOs() {
        for (int i = 0; i < 5; i++) {
            UFO_Model ufo = new UFO_Model("lil_ufo.obj", v.random(-700, 700), v.random(20, 50), v.random(20, 50), v.random(256));
            ships.add(ufo);
        }
    }

    public void render(int elapsed) {
        //v.background(0);

        v.rotateX(v.pit);
        v.rotateY(v.yaw);
        v.rotateZ(v.rol);

        updateFPSAndCycle();
        drawStars();
        drawGround();
        spawnCrowd();
        drawTable();

        msx_left.render();
        msx_right.render_reverse();

        v.pushMatrix();
        v.translate(v.width / 2, v.height / 2);
        ufoSpawner();
        djAnimation();
        v.popMatrix();
    }

    private void updateFPSAndCycle() {
        if (fps == 5) {
            fps = 0;
            if (cycle == 25) {
                cycle = 0;
                wf = !wf;
            } else {
                cycle++;
                crowd_y_switch = 1 - crowd_y_switch;
                r = v.random(0, 256);
                g = v.random(0, 256);
                b = v.random(0, 256);
            }
        } else {
            fps++;
        }
    }

    private void drawStars() {
        if (v.random(1) < 0.5) {
            stars.add(new Star());
        }

        for (Star star : stars) {
            star.update();
            star.display();
        }

        stars.removeIf(star -> star.alpha <= 0);
    }

    private void drawGround() {
        v.noStroke();
        v.fill(v.hueShift(r / 2), 255, 255, v.alp);
        v.rect(0, v.height / 1.5f, v.width, v.height / 1.5f);
    }


    // Inner classes (Star, UFO_Model, DJ_Model, MSX_Model) would be defined here

    // Example of one inner class:
    private class Star {
        float x, y, size, alpha, stretch;

        Star() {
            x = v.random(v.width);
            y = v.random(v.height);
            size = v.random(5, 10);
            alpha = 255;
            stretch = v.random(1, 1.5f);
        }

        void update() {
            alpha--;
        }

        void display() {
            v.fill(v.hueShift(255), 255, 255, v.alp);
            v.ellipse(x, y, size, stretch * size);
        }
    }

    private void spawnCrowd() {
        v.noStroke();

        if (crowd_y_switch == 0) {
            v.pushMatrix();
            for (int i = 0; i < 40; i++) {
                crowd_x = i * 50;
                v.image(img5, crowd_x - 20, crowd_y1, 196 / 1.5f, 488 / 1.5f);
            }
            for (int i = 0; i < 25; i++) {
                crowd_x = i * 100;
                v.image(img6, crowd_x - 20, crowd_y2, 284 / 2f, 580 / 2f);
            }
            for (int i = 0; i < 20; i++) {
                crowd_x = i * 250;
                v.image(img2, crowd_x - 20, crowd_y1, 284 / 2f, 580 / 2f);
            }
            for (int i = 0; i < 25; i++) {
                crowd_x = i * 100;
                v.image(img1, crowd_x - 20, crowd_y2, 284 / 2f, 580 / 2f);
            }
            for (int i = 0; i < 20; i++) {
                crowd_x = i * 150;
                v.image(img4, crowd_x - 20, crowd_y1, 196 / 1.5f, 488 / 1.5f);
            }
            for (int i = 0; i < 20; i++) {
                crowd_x = i * 200;
                v.image(img3, crowd_x - 20, crowd_y2, 284 / 1.75f, 580 / 1.75f);
            }
            v.popMatrix();
        } else {
            v.pushMatrix();
            // Similar pattern as above, but with y1 and y2 swapped
            v.popMatrix();
        }
    }

    private void drawTable() {
        v.fill(v.hueShift(200), 255, 255, v.alp);
        v.stroke(0);
        v.strokeWeight(4);
        v.rect(460, 700, 1000, 500);

        // DISC 1
        float moving_point1_x = 1340 + v.cos(v.radians(angle1)) * radius;
        float moving_point1_y = 825 + v.sin(v.radians(angle1)) * radius;
        float moving_point2_x = 1340 + v.cos(v.radians(angle2)) * radius;
        float moving_point2_y = 825 + v.sin(v.radians(angle2)) * radius;

        v.stroke(0);
        v.fill(0);
        v.ellipse(1340, 825, radius * 2, radius * 2);

        v.stroke(v.hueShift(200), 255, 255, v.alp);
        v.strokeWeight(10);
        v.line(1340, 825, moving_point1_x, moving_point1_y);
        v.line(1340, 825, moving_point2_x, moving_point2_y);

        // DISC 2
        // Similar to DISC 1, but with different x-coordinates

        angle1 += speed;
        angle2 += speed;

        v.noStroke();
        v.fill(v.hueShift(v.random(255)), 255, 255, v.alp);
        v.ellipse(1340, 825, 30, 30);
        v.ellipse(580, 825, 30, 30);

        table_x += speed * direction;

        // SLIDERS
        for (int i = 0; i < 3; i++) {
            v.stroke(0);
            v.strokeWeight(4);
            v.circle(750, 740 + (25 * i), 20);
            v.rect(770, 730 + (25 * i), 100, 20);
            v.line(table_x + 20, table_y + (25 * i), table_x + 20, table_y + 20 + (25 * i));
        }

        v.fill(v.hueShift(v.random(255)), 255, 255, v.alp);
        if (table_x >= v.width - 1070 || table_x <= 750) {
            direction *= -1;
        }

        // SECTION W TRIANGLES
        // Drawing lines and triangles

        // BOXE W CIRCLES
        v.fill(v.hueShift(200), 255, 255, v.alp);
        v.rect(698, 820, 520, 70);
        for (int a = 0; a < 34; a++) {
            for (int i = 0; i < 4; i++) {
                v.fill(v.hueShift(200), 255, 255, v.alp);
                v.circle(710 + (15 * a), 832 + (15 * i), 12);
            }
        }

        // TEXT
        v.textSize(18);
        v.textAlign(v.CENTER, v.CENTER);
        for (int i = 0; i < 12; i++) {
            v.fill(v.hueShift(200), 255, v.random(255), v.alp);
            v.text("URANUS", 504 + (83 * i), 710);
        }
    }

    private void ufoSpawner() {
        v.strokeWeight(5);

        if (ships.size() < 50 && v.random(1) < 0.05) {
            UFO_Model newUfo = new UFO_Model("lil_ufo.obj", v.random(-700, 700), v.random(20, 50), v.random(120, 150), v.random(256));
            ships.add(newUfo);
        }

        for (UFO_Model ufo : ships) {
            ufo.render();
        }

        if (v.frameCount - lastFrame >= 300) {
            if (!ships.isEmpty()) {
                ships.remove(0);
            }
            lastFrame = v.frameCount;
        }
    }

    private void djAnimation() {
        v.lights();

        DJ_Model currentPose = getCurrentPose();
        if (currentPose != null) {
            if (wf) {
                v.noFill();
                v.strokeWeight(3);
                v.stroke(v.hueShift(r), 255, 255, v.alp);
            } else {
                v.fill(v.hueShift(r), g, b, v.alp);
            }
            currentPose.render();
        }
    }

    private DJ_Model getCurrentPose() {
        switch (cycle) {
            case 0: return pR;
            case 1: return p1f1;
            case 2: return p1f2;
            case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: return p1f3;
            case 11: return p1f2;
            case 12: case 13: return p1f1;
            case 14: return p2f1;
            case 15: return p2f2;
            case 16: case 17: case 18: case 19: case 20: case 21: case 22: return p2f3;
            case 23: return p2f2;
            case 24: return p2f1;
            default: return pR;
        }
    }

    private class DJ_Model {
        private PVector pos;
        private float s;
        private PShape spawn;

        public DJ_Model(String file_name, float x, float y, float z, float s) {
            this.pos = new PVector(x, y, z);
            this.s = s;
            this.spawn = v.loadShape(file_name);
            this.spawn.disableStyle();
        }

        public void render() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateY(v.PI);
            v.rotateZ(v.PI);
            v.scale(200);
            v.shape(spawn);
            v.popMatrix();
        }
    }

    private class UFO_Model {
        private PVector pos;
        private float h;
        private PShape sh;
        private float theta;
        private float scale_factor;

        public UFO_Model(String file_name, float x, float y, float z, float h) {
            this.pos = new PVector(x, y, z);
            this.h = h;
            this.sh = v.loadShape(file_name);
            this.sh.disableStyle();
            this.theta = 0;
            this.scale_factor = v.random(0.5f, 2f);
        }

        public void render() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateY(theta);
            v.rotateZ(v.PI);

            v.scale(750 * scale_factor);
            v.noStroke();
            v.fill(v.hueShift(h + v.mouseX + v.frameCount), 255, 255, v.alp);
            v.shape(sh);
            v.popMatrix();
            theta += 0.005f;
        }
    }

    private class MSX_Model {
        private PVector pos;
        private float s;
        private float h;
        private PShape sh;
        private float theta;

        public MSX_Model(String file_name, float x, float y, float z, float s, float h) {
            this.pos = new PVector(x, y, z);
            this.s = s;
            this.h = h;
            this.sh = v.loadShape(file_name);
            this.sh.disableStyle();
            this.theta = 0;
        }

        public void render() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateY(theta);
            v.rotateX(-v.HALF_PI);
            v.scale(s);

            v.strokeWeight(5);
            v.stroke(v.hueShift(0));
            v.noFill();
            v.shape(sh);
            v.popMatrix();
            theta += 0.05f;
        }

        public void render_reverse() {
            v.pushMatrix();
            v.translate(pos.x, pos.y, pos.z);
            v.rotateY(theta);
            v.rotateX(-v.HALF_PI);
            v.scale(s);

            v.strokeWeight(5);
            v.stroke(0);
            v.noFill();
            v.shape(sh);
            v.popMatrix();
            theta -= 0.05f;
        }
    }

    // Inner classes (UFO_Model, DJ_Model, MSX_Model) would be defined here

    // Other inner classes would be similarly defined
}