class Terrain extends Vision
{
  int cols, rows;
  int scl = 50;
  int w = 2000;
  int h = 1600;

  float flying = 0;
float[][] terrain;
Terrain()
{
  
  
   cols = w / scl;
  rows = h/ scl;
  terrain = new float[cols][rows];
}

float offs = 0;

  void render() {

    flying -= speed;

    float yoff = flying;
    for (int y = 0; y < rows; y++) {
      float xoff = 0;
      for (int x = 0; x < cols; x++) {
        terrain[x][y] = map(noise(xoff, yoff), 0, 1, -100, 100);
        xoff += 0.2;
      }
      yoff += 0.2;
    }



    background(0);
    stroke(255);
    noFill();

    translate(width/2, height/2+50);
    rotateX(PI/3);
    translate(-w/2, -h/2);
    for (int y = 0; y < rows-1; y++) {
      beginShape(TRIANGLE_STRIP);
      for (int x = 0; x < cols; x++) {
        stroke(map(x + y + offs, 0, rows + cols, 255, 0) % 255, 255, 255); 
        vertex(x*scl, y*scl, terrain[x][y]);
        vertex(x*scl, (y+1)*scl, terrain[x][y+1]);
        //rect(x*scl, y*scl, scl, scl);
      }
      endShape();
    }
  }
}