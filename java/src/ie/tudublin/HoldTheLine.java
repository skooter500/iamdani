package ie.tudublin;

import com.jogamp.newt.event.KeyEvent;

import c21348423.AdriansVisual;
import c21383126.JenniferVisuals;
import c21415904.SarahVisual;
import c21415952.AjVisual;
import global.AnimationDemo;
import global.Demo;
import global.GlobalVisual;
import global.PressStart;
import ie.tudublin.visual.VScene;
import ie.tudublin.visual.Visual;

/*
    Song lyrics:
    Intro - 00:00 - Sarah
    Verse 1 - 00:21
        00:21 It's not in the way that you hold me
        00:26 It's not in the way you say you care
        00:31 It's not in the way you've been treating my friends
        00:36 It's not in the way that you stayed till the end
        00:40 It's not in the way you look or the things that you say that you'll do
    Chorus - 00:45 - Sarah
        00:45 Hold the line
        00:48 Love isn't always on time
        00:55 Hold the line
        00:58 Love isn't always on time
    Verse 2 - 01:03 - Jennifer
        01:05 It's not in the words that you told me
        01:11 It's not in the way you say you're mine
        01:15 It's not in the way that you came back to me
        01:21 It's not in the way that your love set me free
        01:25 It's not in the way you look or the things that you say that you'll do
    Chorus - 01:30 - Jennifer
        01:30 Hold the line
        01:33 Love isn't always on time
        01:40 Hold the line
        01:43 Love isn't always on time
    Instrumental Solo 01:48 - Adrian
    Verse 3 - 02:31 - Altahier
        02:31 It's not in the words that you told me
        02:36 It's not in the way you say you're mine
        02:41 It's not in the way that you came back to me
        02:46 It's not in the way that your love set me free
        02:51 It's not in the way you look or the things that you say that you'll do
    Outro - 02:55 - Altahier
        02:55 Hold the line
        02:59 Love isn't always on time
        03:05 Hold the line
        03:09 Love isn't always on time
        03:11 Love isn't always on time
        03:15 Hold the line
        03:18 Love isn't always on time
        03:21 Love isn't always on time
        03:22 Love isn't always on time
        03:25 Hold the line
        03:28 Love isn't always on time
        03:34 Love isn't always on time
        03:38 Love isn't always on time
        03:33 Love isn't always on time
        03:47 Whoah-ooh-ooh
    END - 03:58
*/
public class HoldTheLine extends Visual {
    VScene av; // Adrian's Visual
    VScene jv; // Jennifer's Visual
    VScene sv; // Sarah's Visual
    VScene aj; // Altahier's Visual
    VScene gv; // Global Visual
    VScene demo; // Demo Visual
    VScene aDemo; // Animation Demo Visual
    VScene pressStart; // Press Start Visual

    int visualMode; // 0: Main Music Visualisation, 1: Demo, 2: Animation Demo

    public boolean startScreen = true;

    HoldTheLine() {
        super(1024, 44100, 0.5f);
        visualMode = 0; // Main Music Visualisation
    }

    public void settings() {
        fullScreen(P3D);
    }

    public void setup() {
        colorMode(HSB, 360, 100, 100);

        // Load song and lyrics file and pause it
        beginAudio("Toto - Hold The Line.wav", "Toto - Hold The Line.txt");
        pause();
        background(0);

        pressStart = new PressStart(this);

        av = new AdriansVisual(this);
        jv = new JenniferVisuals(this);
        sv = new SarahVisual(this);
        aj = new AjVisual(this);
        gv = new GlobalVisual(this);

        demo = new Demo(this);
        aDemo = new AnimationDemo(this);
    }

    /** Draw the visuals */
    public void draw() {

        int elapsed = audioPlayer().position();

        // Resets
        noLights();
        blendMode(BLEND);
        colorMode(HSB, 360, 100, 100);

        if (startScreen) {
            pressStart.render();
            return;
        }

        switch (visualMode) {
            case 0:
                av.render(elapsed);
                jv.render(elapsed);
                sv.render(elapsed);
                aj.render(elapsed);
                // gv.render(elapsed);
                break;
            case 1:
                demo.render();
                break;
            case 2:
                aDemo.render(elapsed);
                break;
        }
    }

    /**
     * 1-4: Seek to different parts of the song
     * j: Seek -10 secs
     * l: Seek +10 secs
     * left arrow: Seek -5 secs
     * right arrow: Seek +5 secs
     * q: Main Music Visualisation
     * w: Demo
     * e: Animation Demo
     * Space: Pause/Play
     */
    public void keyPressed() {
        switch (keyCode) {
            case '1':
                seek(0);
                break;
            case '2':
                seek(1, 3);
                break;
            case '3':
                seek(1, 48);
                break;
            case '4':
                seek(2, 31);
                break;
            case KeyEvent.VK_J: // seek -10 secs
                seek(-10000 + audioPlayer().position());
                break;
            case KeyEvent.VK_L: // seek +10 secs
                seek(10000 + audioPlayer().position());
                break;
            case LEFT: // seek -5 secs
                seek(-5000 + audioPlayer().position());
                break;
            case RIGHT: // seek +5 secs
                seek(5000 + audioPlayer().position());
                break;
            case KeyEvent.VK_Q:
                visualMode = 0;
                System.out.println("Debug mode 0");
                break;
            case KeyEvent.VK_W:
                visualMode = 1;
                System.out.println("Debug mode 1");
                break;
            case KeyEvent.VK_E:
                visualMode = 2;
                System.out.println("Debug mode 2");
                break;
            case KeyEvent.VK_K:
            case KeyEvent.VK_SPACE:
                pausePlay();
                startScreen = false;
                break;
            default:
                break;
        }
    }
}
