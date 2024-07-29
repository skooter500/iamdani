package ie.tudublin;

import processing.core.PApplet;

public class Ease
{
    
    public enum EASE { LINEAR, QUADRATIC, CUBIC, QUARTIC, QUINTIC, SINUSOIDAL, EXPONENTIAL, CIRCULAR, sqrt 
    }
    public enum TYPE { EASE_IN, EASE_OUT, EASE_IN_OUT }

    /* The map2() function supports the following easing types */

    /*
    * A map() replacement that allows for specifying easing curves
    * with arbitrary exponents.
    *
    * value :   The value to map
    * start1:   The lower limit of the input range
    * stop1 :   The upper limit of the input range
    * start2:   The lower limit of the output range
    * stop2 :   The upper limit of the output range
    * type  :   The type of easing (see above)
    * type  :   One of EASE_IN, EASE_OUT, or EASE_IN_OUT
    */
    public static float Map2(float value, float start1, float stop1, float start2, float stop2, EASE ease, TYPE type)
    {
        float b = start2;
        float c = stop2 - start2;
        float t = value - start1;
        float d = stop1 - start1;
        float p = 0.5f;
        switch (ease)
        {
            case LINEAR:
                return c * t / d + b;
            case sqrt:
                if (type == TYPE.EASE_IN)
                {
                    t /= d;
                    return c * PApplet.pow(t, p) + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    t /= d;
                    return c * (1 - PApplet.pow(1 - t, p)) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    t /= d / 2;
                    if (t < 1) return c / 2 * PApplet.pow(t, p) + b;
                    return c / 2 * (2 - PApplet.pow(2 - t, p)) + b;
                }
                break;
            case QUADRATIC:
                if (type == TYPE.EASE_IN)
                {
                    t /= d;
                    return c * t * t + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    t /= d;
                    return -c * t * (t - 2) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    t /= d / 2;
                    if (t < 1) return c / 2 * t * t + b;
                    t--;
                    return -c / 2 * (t * (t - 2) - 1) + b;
                }
                break;
            case CUBIC:
                if (type == TYPE.EASE_IN)
                {
                    t /= d;
                    return c * t * t * t + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    t /= d;
                    t--;
                    return c * (t * t * t + 1) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    t /= d / 2;
                    if (t < 1) return c / 2 * t * t * t + b;
                    t -= 2;
                    return c / 2 * (t * t * t + 2) + b;
                }
                break;
            case QUARTIC:
                if (type == TYPE.EASE_IN)
                {
                    t /= d;
                    return c * t * t * t * t + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    t /= d;
                    t--;
                    return -c * (t * t * t * t - 1) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    t /= d / 2;
                    if (t < 1) return c / 2 * t * t * t * t + b;
                    t -= 2;
                    return -c / 2 * (t * t * t * t - 2) + b;
                }
                break;
            case QUINTIC:
                if (type == TYPE.EASE_IN)
                {
                    t /= d;
                    return c * t * t * t * t * t + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    t /= d;
                    t--;
                    return c * (t * t * t * t * t + 1) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    t /= d / 2;
                    if (t < 1) return c / 2 * t * t * t * t * t + b;
                    t -= 2;
                    return c / 2 * (t * t * t * t * t + 2) + b;
                }
                break;
            case SINUSOIDAL:
                if (type == TYPE.EASE_IN)
                {
                    return -c * PApplet.cos(t / d * (PApplet.PI / 2)) + c + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    return c * PApplet.sin(t / d * (PApplet.PI / 2)) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    return -c / 2 * (PApplet.cos(PApplet.PI * t / d) - 1) + b;
                }
                break;
            case EXPONENTIAL:
                if (type == TYPE.EASE_IN)
                {
                    return c * PApplet.pow(2, 10 * (t / d - 1)) + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    return c * (-PApplet.pow(2, -10 * t / d) + 1) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    t /= d / 2;
                    if (t < 1) return c / 2 * PApplet.pow(2, 10 * (t - 1)) + b;
                    t--;
                    return c / 2 * (-PApplet.pow(2, -10 * t) + 2) + b;
                }
                break;
            case CIRCULAR:
                if (type == TYPE.EASE_IN)
                {
                    t /= d;
                    return -c * (PApplet.sqrt(1 - t * t) - 1) + b;
                }
                else if (type == TYPE.EASE_OUT)
                {
                    t /= d;
                    t--;
                    return c * PApplet.sqrt(1 - t * t) + b;
                }
                else if (type == TYPE.EASE_IN_OUT)
                {
                    t /= d / 2;
                    if (t < 1) return -c / 2 * (PApplet.sqrt(1 - t * t) - 1) + b;
                    t -= 2;
                    return c / 2 * (PApplet.sqrt(1 - t * t) + 1) + b;
                }
                break;
        };
        return 0;
    }

    /*
    * A map() replacement that allows for specifying easing curves
    * with arbitrary exponents.
    *
    * value :   The value to map
    * start1:   The lower limit of the input range
    * stop1 :   The upper limit of the input range
    * start2:   The lower limit of the output range
    * stop2 :   The upper limit of the output range
    * v     :   The exponent value (e.g., 0.5, 0.1, 0.3)
    * type  :   One of EASE_IN, EASE_OUT, or EASE_IN_OUT
    */
    public static float map3(float value, float start1, float stop1, float start2, float stop2, float v, TYPE type)
    {
        float b = start2;
        float c = stop2 - start2;
        float t = value - start1;
        float d = stop1 - start1;
        float p = v;
        float output = 0;
        if (type == TYPE.EASE_IN)
        {
            t /= d;
        output = c * PApplet.pow(t, p) + b;
        }
        else if (type == TYPE.EASE_OUT)
        {
            t /= d;
        output = c * (1 - PApplet.pow(1 - t, p)) + b;
        }
        else if (type == TYPE.EASE_IN_OUT)
        {
            t /= d / 2;
            if (t < 1) return c / 2 * PApplet.pow(t, p) + b;
        output = c / 2 * (2 - PApplet.pow(2 - t, p)) + b;
        }
        return output;
    }
    
}