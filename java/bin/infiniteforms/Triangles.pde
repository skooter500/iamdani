class Triangles extends Vision
{
  int count = 40;
  
  void render()
  {
    pushStyle();
    noStroke();
    float wgap = width * 3 / count;
    float hgap = height * 3 / count;
    float colorGap = 10;
    for (float i = count; i >= 0; i --)
    {
      float w = i * wgap * 0.5;
      float h = i * hgap * 0.5;
      float f = abs((c + i * colorGap) % 256.0f);
      fill(f, 255, 255);
      c = (c + speed);
      triangle(cx-w, cy+h, cx, cy-h, cx+w, cy+h);
    }
    popStyle();
  }
}
