import javax.swing.*;
import java.awt.*;

class rectangle extends JComponent
{
    int x, y, width, height;
    int ID = 0;
    static int RectangleAmount = 0;

    static int screenheight;

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


    void dynamicHeightRectangle(byte[] input)
    {
        int bufferLength = input.length / RectangleAmount;
        int sum = 0;

        for (int i = (this.ID - 1) * bufferLength; i < this.ID * bufferLength; i++) {           //Taking the buffer length of each rectangle and summing it
            sum += input[i];
        }
        this.height = (sum - (bufferLength * -128)) * (screenheight - 0) / ((bufferLength * 127) - (bufferLength * -128)) + 0;  //Resizing rectangle based on  number of rectangles and size of screen
        updateBounds();
        repaint();

    }
}
