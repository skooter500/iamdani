package ie.tudublin.visual;

/**
 * An interface for ease functions.
 * It is used to create a smooth transition between two values
 *
 * @see <a href="https://easings.net/">https://easings.net/</a>
 * @see <a href=
 *      "https://web.dev/choosing-the-right-easing/">https://web.dev/choosing-the-right-easing/</a>
 * @see <a href="https://youtu.be/YJB1QnEmlTs">https://youtu.be/YJB1QnEmlTs</a>
 */
public interface EaseFunction {
    /**
     * The ease function
     *
     * @param t the value to be eased
     * @return the eased value
     */
    float ease(float t);
    // - Ease in, ease out or both, 200ms - 500ms is recommended

    /**
     * easeLinear: <code>y = x </code><br><br>
     * Oh you thought its more complicated than that?
     * Here is the full equation:
     * <code> y = (2(x^2-12345)^2*100/2(x^2-12345)^2) - sqrt(10^4) </code>
     */
    public static EaseFunction easeLinear = (t) -> t;

    /**
     * Smoothstep is a combination of quadEaseIn and quadEaseOut lerped
     * <code> y = lerp(quadEaseIn(x), quadEaseOut(x), t) </code>
     *
     * @see <a href=
     *      "https://youtu.be/YJB1QnEmlTs?t=179">https://youtu.be/YJB1QnEmlTs?t=179</a>
     */
    public static EaseFunction easeSmoothstep = (f) -> {
        return f * f + f * ((1 - (1 - f) * (1 - f)) - f * f);
    };

    /**
     * easeOutQuad: <code>y = x * (2 - x) </code><br><br>
     * @see <a href="https://easings.net/#easeOutQuad">https://easings.net/#easeOutQuad</a>
     */
    public static EaseFunction easeOutQuad = (f) -> f * (2 - f);
    /**
     * easeInQuad: <code>y = x^2 </code><br><br>
     * @see <a href="https://easings.net/#easeInQuad">https://easings.net/#easeInQuad</a>
     */
    public static EaseFunction easeOutQuint = (f) -> 1 + (--f) * f * f * f * f;

    /**
     * easeOutBounce is a function that is used to create a bounce effect.
     * Added as something a lil fun.
     * @see <a href="https://easings.net/#easeOutBounce">https://easings.net/#easeOutBounce</a>
     */
    public static EaseFunction easeOutBounce = (f) -> {
        // Constants for the bounce function, don't change these
        final float n1 = 7.5625f;
        final float d1 = 2.75f;

        if (f < 1 / d1) {
            return n1 * f * f; // First arc
        } else if (f < 2 / d1) {
            return n1 * (f -= 1.5f / d1) * f + 0.75f; // Second arc
        } else if (f < 2.5 / d1) {
            return n1 * (f -= 2.25f / d1) * f + 0.9375f; // Third arc
        } else {
            return n1 * (f -= 2.625f / d1) * f + 0.984375f; // Fourth arc
        }
    };
}