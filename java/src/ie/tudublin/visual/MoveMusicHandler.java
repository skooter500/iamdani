package ie.tudublin.visual;

import java.util.ArrayList;

import ie.tudublin.ControllerHandler;
import ie.tudublin.IAMDANI;

public class MoveMusicHandler implements ControllerHandler{

    IAMDANI v;

    public MoveMusicHandler(IAMDANI v) {
        this.v = v;
    }


    @Override
    public void controllerChange(int channel, int number, int value) {
        if (v.exp)
            v.println("CH " + channel + " NUM " + number + " VA " + value);

        boolean clockWise = (value < 100);

    if (number == 7) {
        v.targetSpe = v.map((float) value, 0.0f, 127.0f, 0.0f, 3.58f);
        if (v.exp)
            v.println("Z80 " + v.nf(v.targetSpe, 1, 2) + " MHZ");
    }

    if (number == 10) {
        v.targetBas = v.map((float) value, 0.0f, 127.0f,0.0f, 10.0f);
        if (v.exp)
            v.println("SAB " + v.nf(v.targetBas, 3, 2));
    }

    if (number == 114) {
        v.targetMul = v.map((float) value, 0.0f, 127.0f,0.0f, 100.0f);
        if (v.exp)
        v.println("LUM " + v.nf(v.targetMul, 3, 2));
    }
    if (number == 74) {
        // hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
        v.targetHue = v.map((float) value, 0.0f, 127.0f,0.0f, 255.0f);
        if (v.exp)
        v.println("EUH " + v.nf(v.targetHue, 3, 2));
    }
    if (number == 73) {
        v.bhu = (int) v.map((float) value, 0.0f, 127.0f,0.0f, v.matchingFiles.length - 1);

        if (v.bhu < 0)
        {
            v.bhu = v.matchingFiles.length;
        } 
        v.bhu = v.bhu % v.matchingFiles.length;
        String fnt = "" + v.matchingFiles[(int)v.bhu];
        v.font = v.createFont("" + fnt, v.bri);
        v.textFont(v.font);
        v.myTextarea.setFont(v.font);

        if (v.exp)
        v.println("BHU " + v.nf(v.bhu, 3, 2));

        v.println("FNT: " + fnt);
        v.println("abcdefghijklmnopqrstuvwxyz ABCDDEFGHIJKLMNOPQRSTUVWXYZ0123456789 color auto goto list run");
            
    }

    if (number == 79) {
        v.bri = v.map((float) value, 0.0f, 127.0f,1.0f, 100);
        if (v.exp)
        v.println("v.bri " + v.nf(v.bri, 3, 2));


        String fnt = "" + v.matchingFiles[(int)v.bhu];
        

        v.font = v.createFont(fnt, v.bri);
        v.textFont(v.font);
        v.myTextarea.setFont(v.font);
        v.println("abcdefghijklmnopqrstuvwxyz ABCDDEFGHIJKLMNOPQRSTUVWXYZ0123456789 color auto goto list run");
        


    }
    // if (number == 75) {
    //     ArrayList<Integer> group = v.groups.get(v.findGroup(v.whichVisual));
    //     int newWhichVisual = v.min(v.max(clockWise ? v.whichVisual + 1 : v.whichVisual - 1, group.get(0)),
    //             group.get(group.size() - 1));
    //     if (newWhichVisual != v.whichVisual) {
    //         v.whichVisual = newWhichVisual;
    //         v.change(v.whichVisual);
    //     }
    //     return;
    // }
    if (number == 75) {
        v.cue = (int) v.map((float) value, 0.0f, 127.0f,0.0f, v.arts.size() - 1);
        v.println("EUC: " + v.cue);
        v.println("CUE ART: \"" + v.arts.get(v.cue).getClass().getSimpleName().toLowerCase() + ".art\"");

        return;
    }

    
    /*if (number == 18) {
        // hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
        v.targetHue = clockWise ? v.targetHue + 5f : v.targetHue - 5f;
        if (v.exp)
            v.println("EUH " + v.nf(v.targetHue, 3, 2));
    }
    */

    if (number == 76) {
        v.targetAld = v.map((float) value, 0.0f, 127.0f,0.0f, 50.0f);
        
        if (v.exp)
            v.println("DAL " + v.nf(v.targetAld, 3, 2));
    }

    // if (number == 16) {
    //     targetAld = min(max(clockWise ? targetAld + 1f : targetAld - 1f, 10), 50);
    //     if (v.exp)
    //         println("ALD " + nf(targetAld, 3, 2));
    // }

    if (number == 19) {
        v.targetAlp = v.map((float) value, 0.0f, 127.0f,0.0f, 255.0f);
        if (v.exp)
            v.println("PLA " + v.nf(v.targetAlp, 3, 2));
    }

    float rotSpeed = 0.01f;

    if (number == 71) {
        v.targetCqz = v.map((float) value, 0.0f, 127.0f,0.0f, 255.0f);
        if (v.exp)
        v.println("cqz " + v.nf(v.targetCqz, 3, 2));
    }
    
    if (number == 77) {
        v.targetYaw = v.map((float) value, 0.0f, 127.0f,0.0f, v.TWO_PI);        
        if (v.exp)
        v.println("WAY " + v.nf(v.targetYaw, 3, 2));
    }

    if (number == 93) {
        v.targetRol = v.map((float) value, 0.0f, 127.0f,0.0f, v.TWO_PI);
        v.targetRol = v.wrapAngle(v.targetRol);
        if (v.exp)
        v.println("LOR " + v.nf(v.targetRol, 3, 2));
    }

    if (number == 91) {
        v.targetCCo = v.map((float) value, 0.0f, 127.0f,0.0f, 255);
        if (v.exp)
        v.println("OCC " + v.nf(v.targetCCo, 3, 2));

    }

    if (number == 17) {
        v.targetPit = v.map((float) value, 0.0f, 127.0f,0.0f, v.TWO_PI);
        v.targetPit = v.wrapAngle(v.targetPit);
        if (v.exp)
            v.println("TIP " + v.nf(v.targetPit, 3, 2));
    }
    // int newVisual = whichVisual;
    // if (clockWise)
    // newVisual = (newVisual + 1) % visions.size();
    // else {
    // newVisual--;
    // if (newVisual < 0) {
    // newVisual = visions.size() - 1;
    // }
    // }
    // change(newVisual);exp)
    }

    @Override
    public void noteOn(int channel, int pitch, int velocity) {
        // 43 is print screen button
        if (v.exp && pitch != 43)
            v.println("N+ CH " + channel + " PI " + pitch + " VE " + velocity);

        switch (v.mode) {
            case Auto: {
                if (pitch == 43) {
                    v.takeScreenshot = true;
                    return;
                }
                int newVisual = pitch % v.arts.size();
                v.change(newVisual);
                return;
            }
            case AutoRandom: {
                if (pitch == 43) {
                    v.takeScreenshot = true;
                    return;
                }
                int newVisual = (int) v.random(0, v.arts.size());
                v.change(newVisual);
                return;
            }
        }

        if (pitch == 43) {
            v.takeScreenshot = true;
            return;
        }

        if (pitch == 44)
        {
            v.hueShift(true);
            return;
        }

        if (pitch == 36)
        {
            v.hueShift(false);
            return;
        }

        if (pitch == 45)
        {
            v.hueShiftCCo(true);
            return;
        }

        if (pitch == 37)
        {
            v.hueShiftCCo(false);
            return;
        }

        if (pitch == 46)
        {
            v.targetYaw += v.QUARTER_PI;

            if (v.checkKey(v.SHIFT) )
            {
                v.yaw = v.targetYaw;
            }
            v.println("WAY: " + v.nf(v.degrees(v.targetYaw), 3, 0));
            return;
        }

        if (pitch == 47)
        {
            v.targetPit += v.QUARTER_PI;

            if (v.checkKey(v.SHIFT) )
            {
                v.pit = v.targetPit;
            }

            v.println("TIP: " + v.nf(v.degrees(v.targetPit), 3, 0));
            return;
        }

        if (pitch == 48)
        {
            v.targetRol += v.QUARTER_PI;

            if (v.checkKey(v.SHIFT) )
            {
                v.rol = v.targetRol;
            }

            v.println("LOR: " + v.nf(v.degrees(v.targetRol), 3, 0));
            return;
        }

        if (pitch ==38)
        {
            v.targetYaw -= v.QUARTER_PI;

            if (v.checkKey(v.SHIFT) )
            {
                v.yaw = v.targetYaw;
                v.println("shift");
            }

            v.println("WAY: " + v.nf(v.degrees(v.targetYaw), 3, 0));
            return;
        }


        if (pitch == 39)
        {
            v.targetPit -= v.QUARTER_PI;

            if (v.checkKey(v.SHIFT) )
            {
                v.pit = v.targetPit;
            }

            v.println("TIP: " + v.nf(v.degrees(v.targetPit), 3, 0));
            return;
        }

        if (pitch == 40)
        {
            v.targetRol -= v.QUARTER_PI;

            if (v.checkKey(v.SHIFT) )
            {
                v.rol = v.targetRol;
            }

            v.println("LOR: " + v.nf(v.degrees(v.targetRol), 3, 0));
            return;
        }

        if (pitch == 52)
        {
            v.change(v.cue);
            return;
        }

        if (pitch == 54)
        {
            v.targetAld = 5;
            v.println("DAL: " + v.targetAld);
            return;
        }

        if (pitch== 55)
        {
            v.targetAld = 0;
            v.println("DAL: " + v.targetAld);
            return;
        }


        if (pitch == 49)
        {
            v.cue = (v.cue + 1) % v.arts.size();
            v.println("EUC: " + v.cue);
            v.println("CUE ART: " + v.arts.get(v.cue).getClass().getSimpleName().toLowerCase() + ".art\"");

            return;
        }

        if (pitch == 41)
        {
            v.cue = (v.cue - 1);
            if (v.cue < 0)
            {
                v.cue = v.arts.size() - 1;
            }
            v.println("CUE: " + v.cue);
            v.println("CUE ART: " + v.arts.get(v.cue).getClass().getSimpleName().toLowerCase() + ".art\"");
            return;
        }    
        
        if (pitch == 56)
        {
            v.targetBas -= 2f;
            v.println("SAB: " + v.targetBas);
            return;
        }

        if (pitch == 42)
        {
            v.targetBas += 2f;
            v.println("SAB: " + v.targetBas);
            return;
        }

        if (pitch == 53)
        {
            v.defaults();
            return;
        }

        if (pitch == 51) {
            v.showConsole = !v.showConsole;
            v.println("CON:" + v.showConsole);
            v.consoleSize = 0;
            if (!v.showConsole) {
                v.myTextarea.setVisible(v.showConsole);
            }
            return;
        }

        // Receive a noteOn
        // SPecial codes

        /*if (pitch >= 44 && pitch <= 47) {
            int g = pitch - 44;
            v.changeToGroupVisual(g);
            return;
        }

        if (pitch == 48)
        {
            v.targetYaw = v.QUARTER_PI * (int) v.random(0,7);
            v.targetPit = v.QUARTER_PI * (int) v.random(0,7);
            v.targetRol = v.QUARTER_PI * (int) v.random(0,7);
            //v.targetRol = - v.HALF_PI;
            // targetHue = random(0, 255);
            // v.targetAlp = random(10, 255);
            // targetAld = random(0, 50);
            if (v.exp) v.println("RND");
        }

        if (pitch >= 36 && pitch <= 39) {
            int g = pitch - 36;
            g += 4;
            v.changeToGroupVisual(g);            
        }

        if (pitch == 40) {
            v.exp = !v.exp;
            if (v.exp) {
                v.println("TRON");
                return;
            } else {

                v.println("TROFF");
                return;
            }
        }

        
        if (pitch == 49) {
            boolean clockWise = true;
            ArrayList<Integer> group = v.groups.get(v.findGroup(v.whichVisual));
            int newWhichVisual = v.min(v.max(clockWise ? v.whichVisual + 1 : v.whichVisual - 1, group.get(0)),
                    group.get(group.size() - 1));
            if (newWhichVisual != v.whichVisual) {
                v.whichVisual = newWhichVisual;
                v.change(v.whichVisual);
            }
            return;
        }

        if (pitch == 41) {
            boolean clockWise = false;
            ArrayList<Integer> group = v.groups.get(v.findGroup(v.whichVisual));
            int newWhichVisual = v.min(v.max(clockWise ? v.whichVisual + 1 : v.whichVisual - 1, group.get(0)),
                    group.get(group.size() - 1));
            if (newWhichVisual != v.whichVisual) {
                v.whichVisual = newWhichVisual;
                v.change(v.whichVisual);
            }
            return;
        }

        if (pitch == 42) {
            if (v.exp) v.println("RST");
            v.visions.get(v.whichVisual).enter();
            return;
        }

        if (pitch == 50) {

            v.defaults();
            return;
        }
        */
    }
    
}
