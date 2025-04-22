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

        rectangle low1 = new rectangle(0, 600, 120, 20);     //setting the visuals
        rectangle low2 = new rectangle(120, 600, 120, 20);
        rectangle low3 = new rectangle(240, 600, 120, 20);
        rectangle mid1 = new rectangle(360, 600, 120, 20);
        rectangle mid2 = new rectangle(480, 600, 120, 20);
        rectangle mid3 = new rectangle(600, 600, 120, 20);
        rectangle mid4 = new rectangle(720, 600, 120, 20);
        rectangle high1= new rectangle(840, 600, 120, 20);
        rectangle high2= new rectangle(960, 600, 120, 20);
        rectangle high3= new rectangle(1080, 600, 120, 20);


                      //adding the visuals
        panel.add(low1); panel.add(low2); panel.add(low3);
        panel.add(mid1); panel.add(mid2); panel.add(mid3); panel.add(mid4);
        panel.add(high1); panel.add(high2); panel.add(high3);
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

                   //byte[] filtered = low1.applyBandPass_Sound(buffer, 400, 5f, sampleRate); //Sample audio
                   low1.applyBandPass(buffer, 150 , 5, sampleRate);
                   low2.applyBandPass(buffer, 400, 5, sampleRate);
                   low3.applyBandPass(buffer, 750, 5, sampleRate);
                   mid1.applyBandPass(buffer, 1000, 3, sampleRate);
                   mid2.applyBandPass(buffer, 1500, 3, sampleRate);
                   mid3.applyBandPass(buffer, 2000, 3, sampleRate);
                   mid4.applyBandPass(buffer, 2500, 3, sampleRate);
                   high1.applyBandPass(buffer, 1000, 3.5f, sampleRate);
                   high2.applyBandPass(buffer, 3000, 2, sampleRate);
                   high3.applyBandPass(buffer, 10000, 0.5f, sampleRate);

                   //speakers.write(filtered, 0, readBuffer);       //play filtered audio
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
