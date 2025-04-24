import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

class rectangle extends JComponent
{
    int x, y, width, height;
    int ID = 0;
    static int RectangleAmount = 0;

    rectangle(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width =width;
        this.height = height;
        ++RectangleAmount;
        this.ID = RectangleAmount;

        updateBounds();
    }

     void updateBounds()
     {
         int drawY = (height < 0) ? y : y - height;
         this.setBounds(x, drawY, width, Math.abs(height));
     }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    void dynamicHeight(byte[] input)
    {
        int bufferLength = input.length / RectangleAmount;
        int sum = 0;

        for (int i = (this.ID - 1) * bufferLength; i < this.ID * bufferLength; i++) {           //Taking the buffer length of each rectangle and summing it
            sum += input[i];
        }
        this.height = (sum - (bufferLength * -128)) * (650 - 0) / ((bufferLength * 127) - (bufferLength * -128)) + 0;  //Resizing rectangle based on  number of rectangles and size of screen
        updateBounds();
        repaint();

    }

    static byte[] applyBandPass_Sound(byte[] input, float centerFreq, float Q, float sampleRate)
    {
        byte[] output = new byte[input.length];


        // float centerFreq = center the filter
        // float Q = narrowness - 0.5 wide filter : 1 fairly normal : 10 + very narrow
        // float sampleRate

        BiquadBandPass bp = new BiquadBandPass(centerFreq, sampleRate, Q);

        for (int i = 0; i < input.length - 1; i += 2) {
            int low = (input[i] & 0xff);
            int high = input[i + 1] << 8;
            short sample = (short)(high | low);

            float filtered = bp.process(sample);
            short outSample = (short)Math.max(Math.min(filtered, Short.MAX_VALUE), Short.MIN_VALUE);

            output[i] = (byte)(outSample & 0xff);
            output[i + 1] = (byte)((outSample >> 8) & 0xff);
        }

        return output;
    }

    void dynamicHeight(byte[] input, float centerFreq, float Q, float sampleRate)
    {
        byte[] output = new byte[input.length];

        // float centerFreq = center the filter
        // float Q = narrowness - 0.5 wide filter : 1 fairly normal : 10 + very narrow
        // float sampleRate

        BiquadBandPass bp = new BiquadBandPass(centerFreq, sampleRate, Q);

        for (int i = 0; i < input.length - 1; i += 2) {
            int low = input[i] & 0xff;
            int high = input[i + 1] << 8;
            short sample = (short)(high | low);

            float filtered = bp.process(sample);
            short outSample = (short)Math.max(Math.min(filtered, Short.MAX_VALUE), Short.MIN_VALUE);

            output[i] = (byte)(outSample & 0xff);
            output[i + 1] = (byte)((outSample >> 8) & 0xff);
        }

       /* for (int i = 0; i < output.length; i++) {
            this.height = (output[i] - 0) * (2200 + 20) / (800 - 0) - 20;
            if (this.height > -10) {
                updateBounds();
                repaint();
            }
        }
        */
        int height = IntStream.range(0, output.length).map(i -> output[i]).sum();       //summing the filtered buffer byte array into single int
        this.height = (height - 0) * (220 - 20) / (22000 - 0) + 20;         //resizing the input to the screen height
        if (this.height > 0) {
            updateBounds();
            repaint();
        }

    }
}
