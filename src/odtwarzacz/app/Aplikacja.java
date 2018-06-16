package odtwarzacz.app;

import javazoom.jl.decoder.JavaLayerException;
import odtwarzacz.logika.PausablePlayer;
import odtwarzacz.widoki.MyFrame;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Aplikacja {
    private PausablePlayer mp3 = null;
    public File filename;
    public String timeS,minutes,seconds;
    private double timeD;
    private MyFrame panel;
    private Timer timer1;
    private int sec = 1;

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MyFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logic(JButton buttonStart,JProgressBar progessBar, boolean isStart) throws IOException, JavaLayerException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
          filename = new File("utwory\\BEKA KSH WOLA.mp3");
        try {

            if (mp3 == null) {
                mp3 = new PausablePlayer(filename);
                mp3.play();
            }
            if (isStart){
                mp3.resume();
                buttonStart.setIcon(panel.iconPause);
                timeS = mp3.getTrackLength(filename);
                startProgress(progessBar);
            }
            else {
                mp3.pause();
                pauseProgress();
                buttonStart.setIcon(panel.iconStart);
            }

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startProgress(JProgressBar progressBar){
        timeD = Double.parseDouble(timeS);
        minutes = timeS.substring(0,timeS.indexOf("."));
        seconds = timeS.substring(timeS.indexOf(".")+1,timeS.length());

        progressBar.setMaximum(Integer.parseInt(minutes)*60 + Integer.parseInt(seconds));

        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                progressBar.setValue(sec);
                sec++;
            }
        }, 1000, 1000);
    }

    public void pauseProgress() {
        timer1.cancel();
        timer1.purge();
    }
}
