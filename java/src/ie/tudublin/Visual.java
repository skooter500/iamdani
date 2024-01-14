package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ie.tudublin.visual.AudioAnalysis;

public abstract class Visual extends PApplet
{
	public int frameSize = 512;
	public int sampleRate = 44100;

	public float[] bands;
	public float[] smoothedBands;

	public Minim minim;
	public AudioInput ai;
	public AudioPlayer ap;
	public AudioBuffer ab;
	public FFT fft;
	public BeatDetect beat;
	BeatListener bl;

	private float amplitude  = 0;
	private float smothedAmplitude = 0;

	public float spe = 1.0f;

	public float pit = 0;
    public float yaw = 0;

	public float rol = 0;
    public float cco = 0;
	
	//AudioPlayer player;
    public BeatDetect getBeat() {
		return beat;
	}

	public void setBeat(BeatDetect beat) {
		this.beat = beat;
	}


	public float alp = 255;
	public void startMinim() 
	{
		minim = new Minim(this);

		fft = new FFT(frameSize, sampleRate);

		bands = new float[(int) log2(frameSize)];
  		smoothedBands = new float[bands.length];

        // Audio analysis
        fft.logAverages(60, 3);

        // Making an annonymous inner class to override the default BeatDetect
        // to use our own thresholds
        beat = new BeatDetect(frameSize, sampleRate) {
            // We can assume that BeatDetect is in FREQ_ENERGY mode
            @Override
            public boolean isHat() {
                int lower = super.detectSize() - 7 < 0 ? 0 : super.detectSize() - 7;
                int upper = super.detectSize() - 1;
                return isRange(lower, upper, 1);
            }

            @Override
            public boolean isKick() {
                int upper = 6 >= super.detectSize() ? super.detectSize() : 6;
                return isRange(1, upper, 2);
            }

            @Override
            public boolean isSnare() {
                int lower = 8 >= super.detectSize() ? super.detectSize() : 8;
                int upper = super.detectSize() - 1;
                int thresh = (upper - lower) / 3 + 1;
                return isRange(lower, upper, thresh);
            }
        };
        beat.setSensitivity(50);

        // Could potentially encapsulate all of the above into AudioAnalysis class
        // Not going to do it as it's not necessary, it works fine as is
        this.aAnalysis = new AudioAnalysis(fft, beat, 0.5f);

	}

	public AudioAnalysis aAnalysis;

	float log2(float f) {
		return log(f) / log(2.0f);
	}

	protected void calculateFFT() throws VisualException
	{
		fft.window(FFT.HAMMING);
		if (ab != null)
		{
			fft.forward(ab);
		}
		else
		{
			throw new VisualException("You must call startListening or loadAudio before calling fft");
		}
	}

	public float bas = 0.1f;
	public float mul = 1.0f;

	public float raw = 0;

	public void calculateAverageAmplitude()
	{
		float total = 0;
		for(int i = 0 ; i < ab.size() ; i ++)
        {
			total += abs(ab.get(i));
		}
		amplitude = total / ab.size();
		raw = amplitude;

		amplitude = bas * 0.4f + (amplitude * mul);

		smothedAmplitude = PApplet.lerp(smothedAmplitude, amplitude, 0.1f);
	}


	protected void calculateFrequencyBands() {
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

	public float pingpongmap(float a, float b, float c, float d, float e) {
        float range1 = c - b;
        float range2 = e - d;
        if (range1 == 0) {
            return d;
        }

        if (range2 == 0) {
            return d;
        }

        float howFar = a - b;

        float howMany = floor(howFar / range1);
        float fraction = (howFar - (howMany * range1)) / range1;
        // println(a + " howMany" + howMany + " fraction: " + fraction);
        // println(range2 + " " + fraction);
        if (howMany % 2 == 0) {
            return d + (fraction * range2);
        } else {
            return e - (fraction * range2);
        }
    }

	public float hue = 0;

	public void startListening()
	{
		ai = minim.getLineIn(Minim.MONO, frameSize, 44100, 16);
		ab = ai.left;
	}

	public void loadAudio(String filename)
	{
		//ap = minim.loadFile(sketchPath("Bee Gees - Stayin' Alive (Official Music Video).wav"));
		ap = minim.loadFile(filename, frameSize);
		ab = ap.mix;
	}

	public void settings(){
		size(1024, 1000, P3D);
	}

	public int getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(int frameSize) {
		this.frameSize = frameSize;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public float[] getBands() {
		return bands;
	}

	public float[] getSmoothedBands() {
		return smoothedBands;
	}

	public Minim getMinim() {
		return minim;
	}

	public AudioInput getAudioInput() {
		return ai;
	}


	public AudioBuffer getAudioBuffer() {
		return ab;
	}

	public float getAmplitude() {
		return amplitude;
	}

	public float getSmoothedAmplitude() {
		return smothedAmplitude;
	}

	public AudioPlayer getAudioPlayer() {
		return ap;
	}

	public FFT getFFT() {
		return fft;
	}

    public float hueShift(float f) {
        return pingpongmap(f + hue, 0, 255, 0, 255);
    }
}

class BeatListener implements AudioListener
{
  private BeatDetect beat;
  private AudioPlayer source;
  
  BeatListener(BeatDetect beat, AudioPlayer source)
  {
    this.source = source;
    this.source.addListener(this);
    this.beat = beat;
  }
  
  public void samples(float[] samps)
  {
    beat.detect(source.mix);
  }
  
  public void samples(float[] sampsL, float[] sampsR)
  {
    beat.detect(source.mix);
  }
}
