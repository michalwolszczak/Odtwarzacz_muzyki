package odtwarzacz.app;

import odtwarzacz.logika.Played;
import odtwarzacz.widoki.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Aplikacja {
    private Played mp3 = null;
    public String filename,timeS,minutes,seconds;
    private double timeD;
    private MyFrame panel;
    private volatile boolean running = true;
    private Timer timer1;
    private int sec = 1;
    public Aplikacja() throws IOException {
        //panel = new MyFrame();
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
        minutes = timeS.substring(0,timeS.indexOf("."));
        seconds = timeS.substring(timeS.indexOf(".")+1,timeS.length());

        progressBar.setMaximum(Integer.parseInt(minutes)*60 + Integer.parseInt(seconds));
        running = true;

//        new Thread() {
//            public void run() {
//                try {
//                    while (running) {
//                        progressBar.setValue(progressBar.getValue()+1);
//                        Thread.sleep(1000);
//                    }
//                }
//                catch (Exception e) { System.out.println(e); }
//            }
//        }.start();

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
        running = false;
        timer1.cancel();
        timer1.purge();
        System.out.println(timer1.purge());
    }
}
