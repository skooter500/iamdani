import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

float c = 0;
float speed = 0.0f;

float cx;
float cy;
float sat = 255;
float value = 255;

ArrayList<Vision> visions = new ArrayList<Vision>();

int vision = 0;

float timeDelta = 0;

int frameSize = 256;
int sampleRate = 44100;

float[] bands;
float[] smoothedBands;

Minim minim;
AudioInput ai;
AudioPlayer ap;
AudioBuffer ab;
FFT fft;

float amplitude  = 0;
float smoothedAmplitude = 0;

void setup()
{  
  colorMode(HSB);
  //size(800, 600, P3D);
  fullScreen(P3D);
  noCursor();
  cx = width / 2;
  cy = height / 2;


  /*visions.add(new Kaleidoscope(linePainter));
  visions.add(new Kaleidoscope(circlePainter));
  visions.add(new Life(0, 200));
  visions.add(new Life(1, 200));
  visions.add(new Rects());
  visions.add(new Circles());
  //visions.add(new Triangles());
  visions.add(new FlowField());
  */
  //
  //visions.add(new Cubes2(2, 100, -250));
  
  startMinim();
  frameSize = 256;
  loadAudio("mix.mp3");  
  //startListening();
  
  visions.add(new Models1("infiniteForms.obj"));
  visions.add(new Flower());
  visions.add(new ClusterVisual());
  
  visions.add(new Terrain());
  visions.add(new Bands(200, 0, 0, 0));
  visions.add(new City());

  visions.add(new WaveForm());
  visions.add(new Models1("niksg.obj"));
  
  visions.add(new Cubes1());
  visions.add(new Cubes2(2, 150, -600));  
  visions.add(new Cubes2(7, 250, -600));  
  visions.add(new Cubes2(30, 150, -400));
  visions.add(new Cubes3(20, 10000));  
  visions.add(new Rects());
  visions.add(new Star(7, false, false));
  
  //visions.add(new Circles());
  visions.add(new Star(3, false, false));
  visions.add(new FlowField());  
  visions.add(new Life(0, 1000));
  visions.add(new Life(2, 1000));
  
  visions.add(new Life(1, 1000));
  visions.add(new Terrain());
  
  
  for (Vision v : visions)
  {
    v.initialize();
  }
  previous = millis();
    
  //ap.play();
}

boolean[] keys = new boolean[526];

void keyPressed()
{ 
  keys[keyCode] = true;
  
  if (keys[RIGHT])
  {
    vision = (vision + 1) % visions.size();
  }
  if (keys[LEFT])
  {
    vision = (vision == 0) ? visions.size() - 1 : vision - 1 ;
  }
  
  if (checkKey('w'))
  {
    ap.cue(ap.position() + 30000);
  }
  
  if (checkKey('s'))
  {
    ap.cue(ap.position() - 30000);
  }
  if (keys[' '])
  {
    if (ap != null)
    {
      ap.cue(0);
      ap.play();
    }
  }  
  visions.get(vision).restart();
  
  
}
 
void keyReleased()
{
  keys[keyCode] = false; 
}

boolean checkKey(int k)
{
  if (keys.length >= k) 
  {
    return keys[k] || keys[Character.toUpperCase(k)];  
  }
  return false;
}

void handleInput()
{
  
}

long previous;
float colorOffset = 0;

void draw()
{
  background(0);  
  long now = millis();
  timeDelta = (now - previous) / 1000.0f;
  previous = now;
  float cc = mouseX % 256.0f;
  handleInput();
  //background(cc, 255, 255);
  //background(random(0, 255), 255, 255);
  visions.get(vision).render();

  calculateAmplitude();
  calculateFFT();
  calculateFrequencyBands();

  speed = map(amplitude, 0, 1, 0, 0.1f);
  //numRects = (int) map(mouseY, 0, height, 5, 50);
  //println(frameRate);
  //colorOffset += 0.1f;
}

void startMinim() 
  {
    minim = new Minim(this);

    fft = new FFT(frameSize, sampleRate);

    bands = new float[(int) log2(frameSize)];
    smoothedBands = new float[bands.length];

  }

  float log2(float f) {
    return log(f) / log(2.0f);
  }

  void calculateFFT()
  {
    fft.window(FFT.HAMMING);
    if (ab != null)
    {
      fft.forward(ab);
    }
    else
    {
      println("You must call startListening or loadAudio before calling fft");
    }
  }

  
  void calculateAmplitude()
  {
    float total = 0;
    for(int i = 0 ; i < ab.size() ; i ++)
        {
      total += abs(ab.get(i));
    }
    amplitude = total / ab.size();
    smoothedAmplitude = lerp(smoothedAmplitude, amplitude, 0.1f);
  }


  void calculateFrequencyBands() {
    for (int i = 0; i < bands.length; i++) {
      int start = (int) pow(2, i) - 1;
      int w = (int) pow(2, i);
      int end = start + w;
      float average = 0;
      for (int j = start; j < end; j++) {
        average += fft.getBand(j) * (j + 1);
      }
      average /= (float) w;
      bands[i] = average * 5.0f;
      smoothedBands[i] = lerp(smoothedBands[i], bands[i], 0.05f);
    }
  }

  void startListening()
  {
    ai = minim.getLineIn();
    ab = ai.left;
  }

  void loadAudio(String filename)
  {
    ap = minim.loadFile(filename, frameSize);
    ab = ap.left;
  }
