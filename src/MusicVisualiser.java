import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;


public class MusicVisualiser
{
    public static void main(String[] args)
    {
        int frameW = Integer.parseInt(JOptionPane.showInputDialog("Gimme the size ya window - Width: "));
        int frameH = Integer.parseInt(JOptionPane.showInputDialog("Gimme the size ya window - Height: "));
        JFrame frame = new JFrame("Visualise");         //opening frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameW, frameH);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(148, 50, 230));

        int recTotal = Integer.parseInt(JOptionPane.showInputDialog("Resolution of wave?? Best either to do your Window width for high fidelity\n or between 10 - 100 for blocker graphics\n\n  ride the waves... ;)"));
        rectangle[] Rectangle = new rectangle[recTotal];   //declaring the rectanlge array with custom amount

        for (int i = 0; i < recTotal; i++) {
            Rectangle[i] = new rectangle((frame.getWidth() / recTotal) * i, frame.getHeight(), frame.getWidth() / recTotal, 20);  //iterate through initalising them with custom values
            panel.add(Rectangle[i]);                //adding straight to frame - visuals
        }

        frame.add(panel);
        frame.setVisible(true);
        rectangle.screenheight = frame.getHeight();  //set screen height for wave calculations

        JFileChooser fileChooser = new JFileChooser();                  //file selector
        int result = fileChooser.showOpenDialog(null);
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();

        }

        try {
            File soundFile = new File(selectedFile.getAbsolutePath());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);      //start streams file
            AudioFormat format = audioStream.getFormat();                               //gets format from audio
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            float sampleRate = format.getSampleRate();

            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);       //speaker connection
            speakers.open(format);
            speakers.start();

            byte[] buffer = new byte[4096];             //buffered audio stream
            int readBuffer;





            while ((readBuffer = audioStream.read(buffer, 0, buffer.length)) != -1) {

                for (int i = 0; i < recTotal; i++) {
                    Rectangle[i].dynamicHeight(buffer);
                }

                speakers.write(buffer, 0, readBuffer);

            }

            speakers.drain();
            speakers.close();
            audioStream.close();

            }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }


    }
}
