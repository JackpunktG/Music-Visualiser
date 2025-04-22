import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;


public class MusicVisualiser
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Visualise");         //opening frame
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(148, 50, 230));

        rectangle low = new rectangle(0, 600, 400, 20);     //setting the visuals
        rectangle mid = new rectangle(400, 600, 400, 20);
        rectangle high= new rectangle(800, 600, 400, 20);

                      //adding the visuals
        panel.add(low);
        panel.add(mid);
        panel.add(high);
        frame.add(panel);
        frame.setVisible(true);

        try {
            File soundFile = new File("Perhaps it never mattered.wav");
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

                   //byte[] filtered = high.applyBandPass_Sound(buffer, 6000, 0.2f, sampleRate);     //test audio band
                   low.applyBandPass(buffer, 350, 2, sampleRate);
                   mid.applyBandPass(buffer, 2500, 1, sampleRate);
                   high.applyBandPass(buffer, 6000, 0.2f, sampleRate);

                   //speakers.write(filtered, 0, readBuffer);                    //play filtered audio
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
