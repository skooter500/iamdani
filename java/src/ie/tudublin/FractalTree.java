package ie.tudublin;

public class FractalTree extends Art {
    private float initialLength;
    private float branchAngle;
    private float branchRatio;
    private int maxDepth;
    private float hueStart;
    private float hueEnd;

    public FractalTree(Visual v) {
        super(v);
        this.initialLength = 400;
        this.branchAngle = v.PI / 6; // 30 degrees
        this.branchRatio = 0.67f;
        this.maxDepth = 8;
        this.hueStart = 0;
        this.hueEnd = 120;
    }

    public void render(int elapsed) {
        this.branchAngle = v.mul;
        this.initialLength = 50 * v.bas;
        v.stroke(v.hueShift(100), v.sat, 255, v.alp);
        v.strokeWeight(2);

        v.pushMatrix();
        v.translate(v.width *.333f, v.height);
        branch(initialLength, maxDepth);
        v.popMatrix();
        v.pushMatrix();
        v.translate(v.width * .66666f, v.height);
        branch(initialLength, maxDepth);
        v.popMatrix();
    }

    private void branch(float length, int depth) {
        float n = v.noise(v.millis() / 5000.0f);        
        if (depth == 0) return;

        v.stroke(v.hueShift(v.map(depth, 0, maxDepth, hueStart, hueEnd)), 255, 255, v.alp);

        v.line(0, 0, 0, -length);
        v.translate(0, -length);
        v.rotateX(v.pit * n);
        v.rotateY(v.yaw * n);
        v.rotateZ(v.rol * n);
        v.pushMatrix();
        v.rotateX(v.pit * n);
        v.rotateY(v.yaw * n);
        v.rotateZ(v.rol * n);
        v.rotate(branchAngle);
        branch(length * branchRatio, depth - 1);
        v.popMatrix();

        v.pushMatrix();
        v.rotateX(v.pit * n);
        v.rotateY(v.yaw * n);
        v.rotateZ(v.rol * n);
        v.rotate(-branchAngle);
        branch(length * branchRatio, depth - 1);
        v.popMatrix();
    }

    public void setInitialLength(float initialLength) {
        this.initialLength = initialLength;
    }

    public void setBranchAngle(float branchAngle) {
        this.branchAngle = branchAngle;
    }

    public void setBranchRatio(float branchRatio) {
        this.branchRatio = branchRatio;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setHueRange(float hueStart, float hueEnd) {
        this.hueStart = hueStart;
        this.hueEnd = hueEnd;
    }

    public void enter() {
        v.cqz = 1;
        v.targetCqz = 1;
    }

    public void exit() {
    }
}