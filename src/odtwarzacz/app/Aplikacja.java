package odtwarzacz.app;

import odtwarzacz.logika.Played;
import odtwarzacz.widoki.MyFrame;
import odtwarzacz.widoki.MyPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;

public class Aplikacja {
    private Played mp3 = null;
    private String filename,timeS;
    private double timeD,songTimeMili;
    private MyPanel panel;
    private volatile boolean running = true;
    private Timer timer1;
    public Aplikacja() throws IOException {
        panel = new MyPanel();
    }

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

    public void logic(JButton button,JProgressBar progessBar){
        filename = "utwory\\Zeamsone bb.mp3";
        if (mp3==null) {
            mp3 = new Played(filename);
            button.setIcon(panel.iconPause);
            timeS = mp3.play();
            startProgress(progessBar);
        }else{
            mp3.close();
            pauseProgress();
            button.setIcon(panel.iconStart);
            mp3 = null;
        }

    }

    public void startProgress(JProgressBar progressBar){
        timeD = Double.parseDouble(timeS);
        songTimeMili = timeD*60*10;
        progressBar.setMaximum((int)songTimeMili);
        running = true;

        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                progressBar.setValue(progressBar.getValue()+1);
            }
        }, 1000, 1000);


        new Thread() {
            public void run() {
                try {
                    while (running) {
                        progressBar.setValue(progressBar.getValue()+1);
                        Thread.sleep(100);
                    }
                }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }

    public void pauseProgress() {
        running = false;
    }
}
