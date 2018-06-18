package odtwarzacz.app;

import odtwarzacz.logika.PausablePlayer;
import odtwarzacz.widoki.MyFrame;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class Application {
    private PausablePlayer mp3 = null;
    public File filename,source;
    public static MyFrame myFrame;
    public String timeS,minutes,seconds;
    private Timer timerProgress,timerTime;
    private int sec = 1;
    private JFileChooser chooser;
    private boolean play = false;
    private String timeMi,timeSi,time,title;
    private int timeM = 0,timeSe = 0;
    private int height = 50;

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                myFrame = new MyFrame();
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    private void run(JButton buttonStart,JProgressBar progressBar, boolean isStart, boolean goToStart) {
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

    private void startProgress(JProgressBar progressBar, boolean param1){
        minutes = timeS.substring(0, timeS.indexOf("."));
        seconds = timeS.substring(timeS.indexOf(".")+1,timeS.length());
        if (param1) {
            progressBar.setValue(0);
            sec = 1;
            timerProgress.cancel();
        }
        progressBar.setMaximum(Integer.parseInt(minutes)*60 + Integer.parseInt(seconds));

        timerProgress = new Timer();
        timerProgress.schedule(new TimerTask() {
            @Override
            public void run() {
                progressBar.setValue(sec);
                sec++;
            }
        }, 1000, 1000);
    }

    private void pauseProgress() {
        timerProgress.cancel();
        timerProgress.purge();
    }

    public void addFiles(){
        chooser = new JFileChooser();

        if (chooser.showOpenDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
            File source = new File(chooser.getSelectedFile().toString());
            File dest = new File("utwory\\"+chooser.getName(source));
            try {
                FileUtils.copyFile(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        play = !play;

        run(myFrame.buttonStart, myFrame.progressBar, play, false);

        time = timeS.replace(".",":");
        startTime(play,false);

        if (timeS != null) myFrame.labelTimeEnd.setText(timeS.replace(".",":"));
        if (filename != null) {
            title = filename.toString().replace("utwory\\","");
            title = title.replace(".mp3","");
            myFrame.labelTitle.setText(title);
        }
    }

    private void startTime(boolean play, boolean param1){
        if (!play){
            timerTime.cancel();
        } else{
            if (param1) {
                timerTime.cancel();
                timeSe = 0; timeM = 0;
            }
            timerTime = new Timer();
            timerTime.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (timeSe == 59) {
                        timeSe = 0;
                        timeM += 1;
                    }
                    timeSe += 1;

                    timeSi = String.valueOf(timeSe);
                    timeMi = String.valueOf(timeM);

                    if (timeM<=9){
                        timeMi = "0" + String.valueOf(timeM);
                    }

                    if (timeSe<=9){
                        timeSi = "0" + String.valueOf(timeSe);
                    }
                    if (time != null && time.equals(timeMi+":"+timeSi)) timerTime.cancel();
                    myFrame.labelTime.setText(timeMi+":"+timeSi);
                }
            }, 1000, 1000);
        }
    }

    public void stop(){
        myFrame.labelTime.setText("00:00");
        run(myFrame.buttonStart, myFrame.progressBar,true,true);
        startTime(true,true);
        startProgress(myFrame.progressBar, true);
    }

    public void startCheckedFiles(MyFrame frame, boolean param1){
        HashSet <JLabel> set = new HashSet<>();
        File folder = new File("utwory\\");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                set.add(new JLabel(listOfFiles[i].getName()));
            }
        }
        if (param1) {
            for (JLabel label : set) {
                frame.remove(label);
            }
        }
        for(JLabel label : set){
            label.setBounds(300,height,300,100);
            label.setFont(new Font("Serif", Font.PLAIN, 20));
            frame.remove(label);
            frame.add(label);
            label.repaint();
            height += 50;
        }
        //frame.getContentPane().repaint();
    }
}
