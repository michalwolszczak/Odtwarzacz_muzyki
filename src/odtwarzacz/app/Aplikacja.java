package odtwarzacz.app;

import odtwarzacz.logika.PausablePlayer;
import odtwarzacz.widoki.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Aplikacja {
    private PausablePlayer mp3 = null;
    public File filename;
    public String timeS,minutes,seconds;
    private Timer timer1;
    private int sec = 1;

    public static void main(String[] args){
        EventQueue.invokeLater(MyFrame::new);
    }

    public void logic(JButton buttonStart,JProgressBar progressBar, boolean isStart, boolean goToStart) {
        filename = new File("utwory\\BEKA KSH WOLA.mp3");
        if (goToStart) {
            mp3.stop();
            mp3.close();
            mp3 = null;
        }
        try {
            if (mp3 == null) {
                mp3 = new PausablePlayer(filename);
                mp3.play();
            }
            if (isStart){
                mp3.resume();
                buttonStart.setIcon(MyFrame.iconPause);
                if (!goToStart) {
                    timeS = mp3.getTrackLength(filename);
                    startProgress(progressBar, false);
                }
            }
            else {
                mp3.pause();
                pauseProgress();
                buttonStart.setIcon(MyFrame.iconStart);
            }

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startProgress(JProgressBar progressBar, boolean param1){
        minutes = timeS.substring(0, timeS.indexOf("."));
        seconds = timeS.substring(timeS.indexOf(".")+1,timeS.length());
        if (param1) {
            progressBar.setValue(0);
            sec = 1;
            timer1.cancel();
        }
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

    private void pauseProgress() {
        timer1.cancel();
        timer1.purge();
    }
}
