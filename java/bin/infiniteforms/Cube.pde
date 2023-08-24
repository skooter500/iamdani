class Cube
{
  float size;
  float speed;
  float theta;
  color c;
  PVector pos;
  
  Cube()
  {
    this(100, 0.05f, color(255, 255, 255), width / 2, height / 2);
  }
  
  Cube(float size, float speed, color c, float x, float y)
  {
    this.size = size; // Disambiguation
    this.speed = speed;
    this.c = c;
    theta = 0;
    this.pos = new PVector(x, y);
  }
  
  void update()
  {
    theta += speed;
  }
  
  void render()
  {
    pushMatrix();
    strokeWeight(5);
    noFill();
    stroke(c);
    translate(pos.x, pos.y);  
    rotateX(theta);
    rotateY(theta);  
    box(size);
    popMatrix();
  }
}
