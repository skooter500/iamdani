class Kaleidoscope extends Vision
{
  PGraphics p;
  
  int w, h;
  float s = 0;
  float hue = 0;
  float frequency = 20.0f;
  Painter painter;
  
  Kaleidoscope(Painter painter)
  {
    w = width;
    h = height;    
    this.painter = painter;
  }
  
  void initialize()
  {
    p = createGraphics(w, h); //<>//
  }
  float theta = 0;
  void render()
  { //<>//
    p.beginDraw();
    //p.rotate(theta);
    theta += timeDelta * 0.1;
    p.colorMode(RGB);
    p.fill(255, 20);
    p.rectMode(CORNER);
   p.rect(0, 0, width, height);    
   //p.rotate(theta);
   //p.translate(-w/2,-h/2);
   
    float toPass = (1.0f / frequency);
    if (s > toPass)
    {
      painter.paint(p, hue, w, h);      
      s = 0;
    }
    p.endDraw();
    image(p, 0, 0, width / 2 , height / 2);
    
    pushMatrix();
    translate(width,0);
    scale(-1, 1);
    image(p, 0,0, width / 2 , height / 2);  
    popMatrix();
    
    pushMatrix();
    translate(0,height);
    scale(1, -1);
    image(p, 0,0, width / 2 , height / 2);  
    popMatrix();

    pushMatrix();
    translate(width,height);
    scale(-1, -1);
    image(p, 0,0, width / 2 , height / 2);  
    popMatrix();
    s+= timeDelta;
    hue += timeDelta * 10;
    if (hue > 255)
    {
      hue = 0;
    }
  }  
}

interface Painter
{
  void paint(PGraphics p, float hue, int w, int h);
}

Painter linePainter = new Painter()
{
  void paint(PGraphics p, float hue, int w, int h)
  {
    p.colorMode(HSB);    
    p.stroke(random(0, 255), sat, value);
    //p.noStroke();
    p.strokeWeight(2);
    p.line(random(0, w), random(0, h), random(0, w), random(0, h));    
  }
};

Painter circlePainter = new Painter()
{
  void paint(PGraphics p, float hue, int w, int h)
  {
    p.colorMode(HSB);    
    noFill();
    p.stroke(random(0, 255), sat, value);
    //p.noStroke();
    p.strokeWeight(1);
    float ww = random(0, w);
    p.ellipse(random(0, w), random(0, h), ww, ww);    
  }
};
