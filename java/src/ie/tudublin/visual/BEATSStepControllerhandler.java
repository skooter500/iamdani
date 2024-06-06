package ie.tudublin.visual;

import java.util.ArrayList;

import ie.tudublin.ControllerHandler;
import ie.tudublin.IAMDANI;

public class BEATSStepControllerhandler implements ControllerHandler{

    IAMDANI v;

    public BEATSStepControllerhandler(IAMDANI v) {
        this.v = v;
    }


    @Override
    public void controllerChange(int channel, int number, int value) {
        if (v.exp)
            v.println("CH " + channel + " NUM " + number + " VA " + value);

        boolean clockWise = (value < 100);

    if (number == 7) {
        v.targetSpe = v.min(v.max(clockWise ? v.targetSpe + 0.05f : v.targetSpe - 0.05f, 0.0f), 3.58f);
        if (v.exp)
            v.println("Z80 " + v.nf(v.targetSpe, 1, 2) + " MHZ");
    }

    if (number == 10) {
        v.targetBas = v.max(clockWise ? v.targetBas + 0.1f : v.targetBas - 0.1f, 0.01f);
        if (v.exp)
            v.println("SAB " + v.nf(v.targetBas, 3, 2));
    }

    if (number == 114) {
        v.targetMul = v.max(clockWise ? v.targetMul + 0.1f : v.targetMul - 0.1f, 0);
        if (v.exp)
        v.println("LUM " + v.nf(v.targetMul, 3, 2));
    }
    if (number == 74) {
        // hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
        v.targetHue = clockWise ? v.targetHue + 1f : v.targetHue - 1f;
        if (v.exp)
        v.println("EUH " + v.nf(v.targetHue, 3, 2));
    }
    if (number == 73) {
        v.bhu = (clockWise ? v.bhu + 1 : v.bhu - 1);

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
        v.bri = (clockWise ? v.bri + 1f : v.bri - 1f);
        v.bri = v.max(0, v.bri);
        if (v.exp)
        v.println("v.bri " + v.nf(v.bri, 3, 2));


        String fnt = "" + v.matchingFiles[(int)v.bhu];
        

        v.font = v.createFont(fnt, v.bri);
        v.textFont(v.font);
        v.myTextarea.setFont(v.font);
        v.println("abcdefghijklmnopqrstuvwxyz ABCDDEFGHIJKLMNOPQRSTUVWXYZ0123456789 color auto goto list run");
        


    }
    if (number == 75) {
        ArrayList<Integer> group = v.groups.get(v.findGroup(v.whichVisual));
        int newWhichVisual = v.min(v.max(clockWise ? v.whichVisual + 1 : v.whichVisual - 1, group.get(0)),
                group.get(group.size() - 1));
        if (newWhichVisual != v.whichVisual) {
            v.whichVisual = newWhichVisual;
            v.change(v.whichVisual);
        }
        return;
    }
    if (number == 72) {
        int newWhichVisual = v.min(v.max(clockWise ? v.whichVisual + 1 : v.whichVisual - 1, 0), v.visions.size() - 1);
        if (newWhichVisual != v.whichVisual) {
            v.whichVisual = newWhichVisual;
            v.change(v.whichVisual);
        }
        return;
    }

    if (number == 18) {
        // hueShift = min(max(clockWise ? hueShift + 50 : hueShift - 50f, -250), 250);
        v.targetHue = clockWise ? v.targetHue + 5f : v.targetHue - 5f;
        if (v.exp)
            v.println("EUH " + v.nf(v.targetHue, 3, 2));
    }

    if (number == 76) {
        v.targetAld = v.min(v.max(clockWise ? v.targetAld + .1f : v.targetAld - .1f, 0), 50);
        
        if (v.exp)
            v.println("DAL " + v.nf(v.targetAld, 3, 2));
    }

    // if (number == 16) {
    //     targetAld = min(max(clockWise ? targetAld + 1f : targetAld - 1f, 10), 50);
    //     if (v.exp)
    //         println("ALD " + nf(targetAld, 3, 2));
    // }

    if (number == 19) {
        v.targetAlp = v.min(v.max(clockWise ? v.targetAlp + 1f : v.targetAlp - 1f, 5), 255);
        if (v.exp)
            v.println("PLA " + v.nf(v.targetAlp, 3, 2));
    }

    float rotSpeed = 0.01f;

    if (number == 71) {
        v.cqz = v.min(v.max(clockWise ? v.cqz + 1.0f : v.cqz - 1.0f, 1f), 255);
        if (v.exp)
        v.println("cqz " + v.nf(v.cqz, 3, 2));
    }
    
    if (number == 77) {
        v.targetYaw = clockWise ? v.targetYaw + rotSpeed : v.targetYaw - rotSpeed;
        v.targetYaw = v.wrapAngle(v.targetYaw);
        if (v.exp)
        v.println("WAY " + v.nf(v.targetYaw, 3, 2));
    }

    if (number == 93) {
        v.targetRol = clockWise ? v.targetRol + rotSpeed : v.targetRol - rotSpeed;
        v.targetRol = v.wrapAngle(v.targetRol);
        if (v.exp)
        v.println("LOR " + v.nf(v.targetRol, 3, 2));
    }

    if (number == 91) {
        v.targetCCo = clockWise ? v.targetCCo + 1f : v.targetCCo - 1f;
        if (v.exp)
        v.println("OCC " + v.nf(v.targetCCo, 3, 2));

    }

    if (number == 17) {
        v.targetPit = clockWise ? v.targetPit + rotSpeed : v.targetPit - rotSpeed;
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
                int newVisual = pitch % v.visions.size();
                v.change(newVisual);
                return;
            }
            case AutoRandom: {
                if (pitch == 43) {
                    v.takeScreenshot = true;
                    return;
                }
                int newVisual = (int) v.random(0, v.visions.size());
                v.change(newVisual);
                return;
            }
        }

        if (pitch == 43) {
            v.takeScreenshot = true;
            return;
        }

        // Receive a noteOn
        // SPecial codes

        if (pitch >= 44 && pitch <= 47) {
            int g = pitch - 44;
            v.changeToGroupVisual(g);
            return;
        }

        if (pitch == 48)
        {
            v.targetYaw = v.HALF_PI;
            v.targetPit = v.HALF_PI;
            v.targetRol = - v.HALF_PI;
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

        if (pitch == 51) {
            v.showConsole = !v.showConsole;
            v.println("CON:" + v.showConsole);
            v.consoleSize = 0;
            if (!v.showConsole) {
                v.myTextarea.setVisible(v.showConsole);
            }
            return;
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
    }
    
}
