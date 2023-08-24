import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

ArrayList<Cube> cubes = new ArrayList<Cube>();

int numCubes = 0;

void keyPressed()
{
  if (key > '0' && key  < '9')
  {
    numCubes = key - '0';
    makeCubes(numCubes);
  }  
}



void makeCubes(int count)
{
  cubes.clear();
  float halfW = width / 2;
  float halfH = height / 2;
  float radius = 300;
  for(int i = 0 ; i < count ; i ++)
  {
    float theta = map(i, 0, count, 0, TWO_PI);
    float x = halfW + sin(theta) * radius;
    float y = halfH - cos(theta) * radius;
    Cube cube = new Cube(0, 0, 0, x, y);
    cubes.add(cube);
  }
}

void setup()
{
   size(800 , 800, P3D);
  //fullScreen(P3D, SPAN);
  colorMode(HSB);
  
  cube1 = new Cube(); 
  cube = new Cube(100, 0.1f, color(100, 255, 255), width / 2, height / 2);
  
  minim = new Minim(this);
  ai = minim.getLineIn(Minim.MONO, 512, 44100, 16);
  //ap = minim.loadFile("tomp3.cc - 08 PsychNerD and Marco G  More Cowbell.mp3", 512); 
  //ap.play();
  ab = ai.mix;
  
  
  
}

float theta;
Cube cube;
Cube cube1;
Minim minim;
AudioPlayer ap;
AudioInput ai;
AudioBuffer ab;

float average = 0;
float lerpedAverage = 0;



void draw()
{
  rotateX(lerpedAverage);
  float total = 0;
  for(int i = 0 ; i < ab.size() ; i ++)
  {
    total += abs(ab.get(i));
  }
  average = total / (float) ab.size();
  lerpedAverage = lerp(lerpedAverage, average, 0.1f);
  background(0);
  noStroke();
  strokeWeight(2);
  lights();
  
  for(int i = 0 ; i < cubes.size() ; i ++)
  {
    Cube cube = cubes.get(i);
    cube.c = color(map(lerpedAverage, 0.0f, 1f, 0,255), 255, 255);
    cube.speed = map(lerpedAverage, 0.0f, 1.0f, 0,0.2);
    cube.size = map(lerpedAverage, 0, 1, 200, 800);
    cube.update();
    cube.render();
  
  }
  
  
  //cube1.update();
  //cube1.render();
  
  
}
