class WaveForm extends Vision
{
    float cy = 0;
    float[] lerpedAb; 

    public WaveForm()
    {
        cy = height / 2;
        lerpedAb = new float[ab.size()];
    }

    public void render()
    {
        background(0);
        colorMode(HSB);
        float w = width / ab.size();
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            noStroke();
            fill(
                map(i, 0, ab.size(), 0, 255)
                , 255
                , 255
            );

            float x = map(i, 0, ab.size(), 0, width);
            rect(x, cy, w, -1 - cy * 6 * abs(lerpedAb[i]));
            rect(x, cy, w, +1 + cy * 6 * abs(lerpedAb[i]));
            lerpedAb[i] = lerp(lerpedAb[i], ab.get(i), 0.1f);
        }
    }
}
