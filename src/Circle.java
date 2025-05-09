import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class Circle extends JComponent
{
    public int row, col;
    int ID = 0;
    int x, y;
    public int radius;
    public Color color;
    public static int circleAmount = 0;

    public Circle(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
        setOpaque(false); // Make background transparent
        ++circleAmount;
        this.ID = circleAmount;
    }

    public void updateBounds(int totalRows, int totalCols, int panelWidth, int panelHeight, int padding) {
        int cellWidth = panelWidth / totalCols;
        int cellHeight = panelHeight / totalRows;

        int radius = Math.min(cellWidth, cellHeight) / 2 - padding;
        radius = Math.max(radius, 1);

        int centerX = col * cellWidth + cellWidth / 2;
        int centerY = row * cellHeight + cellHeight / 2;

        this.x = centerX - radius;
        this.y = centerY - radius;
        int diameter = radius * 2;

        setBounds(x, y, diameter, diameter);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(color);
        g2.fillOval(0, 0, getWidth(), getHeight());  // Always draw based on current component size

        g2.dispose();
    }

    void dynamicCircle(byte[] input)
    {
        int bufferLength = input.length / circleAmount;
        int sum = 0;

        for (int i = (this.ID - 1) * bufferLength; i < this.ID * bufferLength; i++) {           //Taking the buffer length of each rectangle and summing it
            sum += input[i];
        }
        int diameter = (sum - (bufferLength * -128)) * (286 - 0) / ((bufferLength * 127) - (bufferLength * -128)) + 0;
        setBounds(x, y, diameter, diameter);
        repaint();
    }




/*
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(;Color.BLUE);

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        int padding = 90; // Controls how close circles get to the edges

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                int x = col * cellWidth + padding;
                int y = row * cellHeight + padding;
                int diameter = Math.min(cellWidth, cellHeight) - 2 * padding;

                g2d.fillOval(x, y, diameter, diameter);
            }
        }
    }

    void dynamicpaintComponent(Graphics g, byte[] input)
    {
        int sum = 0;
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int i = 0; i < input.length; i++) {           //Taking the buffer length of each rectangle and summing it
            sum += input[i];
        }
        // 0 - 90 padding for 600x600 9 circles
        int padding = (sum - 0) * (90 - 0) / (16000 - 0) + 0; // Controls how close circles get to the edges

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                int x = col * cellWidth + padding;
                int y = row * cellHeight + padding;
                int diameter = Math.min(cellWidth, cellHeight) - 2 * padding;

                g2d.fillOval(x, y, diameter, diameter);

                repaint();
            }
        }
    }


 */
}
