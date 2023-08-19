class Rects extends Vision
{
  float box = 50;

  int count = 30;

  void render()
  {
    background(0);
    rectMode(CENTER);
    noStroke();
    float wgap = width / count;
    float hgap = height / count;
    float colorGap = 6;
    for (float i = count; i > 0; i --)
    {
      float w = i * wgap;
      float h = i * hgap;
      float f = abs((c + i * colorGap) % 256.0f);
      fill(f, 255, 255);
      rect(width / 2, height / 2, w, h);

      c = (c - speed * 2);
    }
  }
}