class Circles extends Vision
{
  int count = 50  ;
  void render()
  {
    pushStyle();
    noStroke();
    float gap = width * 1.2 / count;
    float colorGap = 5;
    for (float i = count; i >= 0; i --)
    {
      float w = i * gap;
      float f = abs((c + i * colorGap) % 256.0f);
      fill(f, 255, 255);
      ellipse(width / 2, height / 2, w, w);
      c = (c - speed * 5);
    }
    popStyle();
  }
}
