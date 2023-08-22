package ie.tudublin.visual;

// Graphics
import processing.core.PApplet;

// Audio
import ddf.minim.*;
import ddf.minim.analysis.*;

/**
 * Visual is an abstract class that will be used to create the Music Visualiser.<br>
 * <br>
 * Visual extends {@link PApplet} and encapsulates {@link Minim},
 * {@link AudioInput}, {@link AudioPlayer}, {@link FFT}, {@link BeatDetect} and {@link AudioAnalysis}.
 *
 * @see <a href="https://processing.org/reference/">Processing Reference</a>
 * @see <a href="https://code.compartmental.net/minim/index.html">Minim Reference</a>
 */
public abstract class Visual extends ie.tudublin.Visual {

    private String[][] lyrics; // [[00:00][lyrics], [00:00][lyrics], ...

    /** @return {@link #bufferSize} */
    public int bufferSize() {
        return frameSize;
    }

    


    /** @return {@link #sampleRate} */
    public int sampleRate() {
        return sampleRate;
    }

    /** @return {@link #minim} */
    public Minim minim() {
        return minim;
    }

    /** @return {@link #ap} */
    public AudioInput audioInput() {
        return ai;
    }

    /**
     * @return {@link #ap}
     */
    public AudioPlayer audioPlayer() {
        return null;
    }

    /** @return {@link #fft} */
    public FFT fft() {
        return fft;
    }

    /** @return {@link #beat} */
    public BeatDetect beat() {
        return beat;
    }

    /** @return {@link #aAnalysis} */
    public AudioAnalysis audioAnalysis() {
        return aAnalysis;
    }

    public Visual() {
        this(1024, 44100, 0.1f);
    }

    /**
     * @param bufferSize    Must be a power of 2
     * @param sampleRate    The sample rate of the audio input
     * @param lerpAmount    The lerp amount for the {@link AudioAnalysis}
     */
    public Visual(int bufferSize, int sampleRate, float lerpAmount) {
        
        super();
        
        this.frameSize = bufferSize;
        this.sampleRate = sampleRate;
    }

    // ======== Audio ========

    public void setAudioLerpAmount(float audioLerpAmount) {
        aAnalysis.setLerpAmount(audioLerpAmount);
    }

    /** Begins audio input from the default audio input device. */
    public void beginAudio() {

        // if (ap != null)
        //     ap.close();
        // aIn = minim.getLineIn(Minim.MONO, bufferSize, 44100, 16);
        // aIn.addListener(aAnalysis);

        System.out.println("Using default audio input");

    }

    /**
     * Begins audio input from the specified audio file.
     * @param filename
     */
    public void beginAudio(String filename) {

        // if (ap != null)
        //     ap.close();
        // if (filename == null || filename.isEmpty()) {
        //     System.out.println("No filename specified, using default audio input");
        //     beginAudio();
        // }
        // ap = minim.loadFile(filename, bufferSize);
        // ap.addListener(aAnalysis);
        // ap.play();

        System.out.println("Playing " + filename);

    }

    public void beginAudio(String audioFilename, String lyricsFilename) {
        beginAudio(audioFilename);
        loadLyrics(lyricsFilename);
    }

    public void seek(int ms) {
        ap.cue(ms);
        System.out.println("Seeking to " + ms + " ms");
    }

    public void seek(int m, int s) {
        int ms = toMs(m, s, 0);
        seek(ms);
    }

    public void seek(int m, int s, int ms) {
        int msNew = toMs(m, s, ms);
        seek(msNew);
    }

    public void pausePlay() {

        if (ap.isPlaying()) {
            ap.pause();
            System.out.println("Paused");
        } else {
            ap.play();
            System.out.println("Playing");
        }

    }

    public void pause() {
        ap.pause();
        System.out.println("Paused");
    }

    public void play() {
        ap.play();
        System.out.println("Playing");
    }

    public void mute() {
        ap.mute();
        System.out.println("Muted");
    }

    // ======== Lyrics ========

    /**
     * Loads lyrics from file into 2D array of [time, string]<br><br>
     * File format:<br><br>
     * <pre>
     * 00:00|String
      *00:00|String
     * ...
     * </pre>
     * @param fileName
     */
    public void loadLyrics(String fileName) {
        String[] rawLyrics = loadStrings(fileName);
        // Convert to 2D array
        lyrics = new String[rawLyrics.length][2];
        for (int i = 0; i < rawLyrics.length; i++) {
            String[] split = rawLyrics[i].split("\\|"); // 00:00|String -> [00:00, String]
            // Copy strings to 2D array
            lyrics[i][0] = split[0];
            lyrics[i][1] = split[1];
        }
    }

    /**
     * Gets lyrics at current time
     * @param offset    Offset by line
     * @return          Lyrics at current time
     */
    public String getLyrics(int offset) {
        if (lyrics == null) {
            System.out.println("No lyrics loaded");
            return "No lyrics loaded";
        }

        String result = "...";
        for (int i = 0; i < lyrics.length - 1; i++) {
            int current = timestampToMs(lyrics[i][0]);
            int next = timestampToMs(lyrics[i + 1][0]);
            if (ap.position() >= current && ap.position() < next) {
                result = lyrics[i + offset][1];
                break;
            }
        }
        return result;
    }

    // ======== Helpers ========

    /**
     * Converts timestamp to milliseconds: 01:05 -> 65000
     * @param timestamp
     * @return
     */
    public int timestampToMs(String timestamp) {
        String[] split = timestamp.split(":");
        int m = Integer.parseInt(split[0]);
        int s = Integer.parseInt(split[1]);
        return toMs(m, s, 0);
    }

    /** Converts minutes, seconds, and milliseconds to milliseconds. */
    public int toMs(int m, int s, int ms) {
        return m * 60000 + s * 1000 + ms;
    }

    /** Log_2(x) = log_e(x) / log_e(2) */
    static float log2(float f) {
        return log(f) / log(2.0f);
    }

    /*
     * LERP (Linear Interpolation)
     * Issue with base lerp is it has different behaviors at different frame
     * rates, below is the different methods of lerp for a moving target
     * Method 1: (not frame rate independent)
     * K = constant (such as 0.1)
     * pos = lerp(pos, target, K)
     *
     * Method 2: (frame rate independent but not smooth)
     * K = constant * frameTime
     * pos = lerp(pos, target, K)
     *
     * Method 3: (frame rate independent and smooth)
     * K = constant - (K * frameTime)
     * pos = lerp(pos, target, K)
     *
     * See: https://youtu.be/YJB1QnEmlTs
     */
    /**
     * Implementation of lerp that is frame rate independent and smooth for
     * a moving target
     *
     * @param start
     * @param stop
     * @param amt
     * @param frameTime
     * @return
     */
    public static float lerp(float start, float stop, float amt, float frameTime) {
        float K = amt - (amt * frameTime);
        return start + (stop - start) * K;
    }

    // Position helpers

    /**
     * Translates the origin to the center of the screen.
     */
    public void translateCenter() {
        translate(width / 2, height / 2);
    }

    /**
     * Translates the origin to the center of the screen and then to the specified
     * point.
     * @param x
     * @param y
     */
    public void translateCenter(float x, float y) {
        translate(width / 2 + x, height / 2 + y);
    }

}
