class FlowField extends Vision
{
  float offs = 0;
  float scale = 0.001;

  int box = 20;

  void render()
  {
    pushStyle();
    background(0);       
    noFill();
    strokeWeight(100);
    for (int x = 0; x < width; x+= box) {
      for (int y = 0; y < height; y+=box) {
        noStroke();
        //rect(x, y, box, box);
        float cx = x + box / 2;
        float cy = y + box / 2;
        pushMatrix();
        translate(cx, cy);
        //rotate(map(noise((cx+ offs) * scale, (cy + offs) * scale), 0, 1, 0, TWO_PI));
        fill(map(noise((cx + offs) * scale, (cy + offs) * scale), 0, 1, 0.0f, 255.0f), 255, 255);     
        rect(0, -10, box, box);
        //line(0, -10, 0, 10, 5, 5);
        //rect(-10, 10, 20, 20);
        popMatrix();
      }
    }
    offs += speed * 200;
    popStyle();
  }
}
