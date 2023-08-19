class Star extends Vision
{
  int sides = 8;
  float theta = 0;
  float timer = 0.0f;
  float gOff = 0;
  boolean rotate;
  boolean direction;
  
  Star(int sides, boolean rotate, boolean direction)
  {
    this.sides = sides;
    this.rotate = rotate;
    this.direction = direction;
  }

  void render()
  {

    pushStyle();
    background(255);
    strokeWeight(60);
    float offset = 0 ;
    translate(cx, cy);  
    for (float radius = 0; radius < width * 1.6; radius += 40)
    {
      float t = map(sin(theta + offset), -1.0f, 1.0f, 0.0f, 1.0f);
      color col = color(map(sin(theta + offset), -1, 1, 0, 255), 255, 255) ; // lerpColor(from, to, t);
      if (rotate)
      {
        rotate(speed);
      }
      offset += 0.1f;    
      if (radius == 0)
      {
        fill(col);
        ellipse(0, 0, 20, 20);
      }
      drawStar(0, 0, radius, sides, col);
    }
    if (direction)
      theta += speed;
      else
      theta -= speed;
    popStyle();
  }

  void drawStar(float cx, float cy, float radius, int points, color c)
  {
    float thetaInc = TWO_PI / (points * 2);
    float lastX;
    float lastY;
    lastX = cx;
    lastY = cy -radius; 

    stroke(c);
    noFill();
    beginShape();
    vertex(lastX, lastY);
    for (int i = 1; i <= (points * 2); i ++)
    {
      float theta = (i * thetaInc);
      float x, y;
      float r;
      if (i % 2 == 1)
      {
        r = radius * 0.5f;
      } else
      {
        r = radius;
      }

      x = cx + sin(theta) * r;
      y = cy -cos(theta) * r;
      vertex(x, y);
      lastX = x;
      lastY = y;
    }
    endShape(CLOSE);
  }
}