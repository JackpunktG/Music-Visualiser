public class BiquadBandPass    //Biquad band-pass (constant skirt gain, peak gain = Q)
{
    float b0, b1, b2, a1, a2;
    float x1 = 0, x2 = 0;
    float y1 = 0, y2 = 0;

    public BiquadBandPass(float centerFreq, float sampleRate, float Q)
    {
        float omega = (float)(2 * Math.PI * centerFreq / sampleRate);
        float alpha = (float)(Math.sin(omega) / (2 * Q));
        float cos_omega = (float)Math.cos(omega);

        float a0 = 1 + alpha;

        b0 = alpha / a0;
        b1 = 0;
        b2 = -alpha / a0;
        a1 = -2 * cos_omega / a0;
        a2 = (1 - alpha) / a0;
    }

    public float process(float x)
    {
        float y = b0 * x + b1 * x1 + b2 * x2 - a1 * y1 - a2 * y2;

        x2 = x1;
        x1 = x;
        y2 = y1;
        y1 = y;

        return y;
    }
}

