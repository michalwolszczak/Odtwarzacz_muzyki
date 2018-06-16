package odtwarzacz.widoki;

import odtwarzacz.app.Aplikacja;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class MyFrame extends JFrame {
    public static ImageIcon iconStart,iconPause, iconNext, iconBef;
    private JLabel labelTime,labelTimeEnd, labelTitle;
    private JButton buttonStart, buttonNext, buttonBef;
    private Border emptyBorder;
    private Aplikacja app;
    private JProgressBar progressBar;
    private Timer timer1;
    private int timeM = 0,timeS = 0;
    private String timeMi,timeSi,time,title;
    private boolean play = false;

    public MyFrame() {
        super("Odtwarzacz MP3");
        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        this.getContentPane().setBackground(Color.white);

        labelTime = new JLabel();
        labelTime.setText("00:00");
        labelTime.setBounds(250,549,50,50);

        labelTimeEnd = new JLabel();
        labelTimeEnd.setText("00:00");
        labelTimeEnd.setBounds(765,549,50,50);

        labelTitle = new JLabel("",SwingConstants.CENTER);
        labelTitle.setFont(new Font("Serif", Font.PLAIN, 45));
        labelTitle.setBounds(250,400,500,200);

        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setBounds(300, 570, 450, 10);

        buttonStart = new JButton();
        buttonStart.setBounds(470,600,100,100);
        buttonStart.setName("buttonStart");

        buttonNext = new JButton();
        buttonNext.setBounds(570,600,100,100);
        buttonNext.setName("buttonNext");

        buttonBef = new JButton();
        buttonBef.setBounds(370,600,100,100);
        buttonBef.setName("buttonBef");

        emptyBorder = BorderFactory.createEmptyBorder();
        buttonStart.setBorder(emptyBorder);
        buttonNext.setBorder(emptyBorder);
        buttonBef.setBorder(emptyBorder);
        iconStart = new ImageIcon("src\\ikony\\start.png");
        iconPause = new ImageIcon("src\\ikony\\pause.png");
        iconNext = new ImageIcon("src\\ikony\\next.png");
        iconBef = new ImageIcon("src\\ikony\\bef.png");
        buttonStart.setIcon(iconStart);
        buttonNext.setIcon(iconNext);
        buttonBef.setIcon(iconBef);

        buttonStart.addActionListener((ActionEvent e) -> {
            play = !play;

            if (app == null) {
                app = new Aplikacja();
            }

            app.logic(buttonStart, progressBar, play, false);

            time = app.timeS.replace(".",":");
            startTime(play,false);

            if (app.timeS != null) labelTimeEnd.setText(app.timeS.replace(".",":"));
            if (app.filename != null) {
                title = app.filename.toString().replace("utwory\\","");
                title = title.replace(".mp3","");
                labelTitle.setText(title);
            }

        });

        buttonNext.addActionListener(e -> System.out.println("1"));

        buttonBef.addActionListener((ActionEvent e) -> {
            labelTime.setText("00:00");
            app.logic(buttonStart, progressBar,true,true);
            startTime(true,true);
            app.startProgress(progressBar, true);
        });


        add(progressBar);
        add(buttonStart);
        add(buttonNext);
        add(buttonBef);
        add(labelTime);
        add(labelTimeEnd);
        add(labelTitle);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startTime(boolean play, boolean param1){
        if (!play){
            timer1.cancel();
        } else{
            if (param1) {
                timer1.cancel();
                timeS = 0; timeM = 0;
            }
            timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (timeS == 59) {
                        timeS = 0;
                        timeM += 1;
                    }
                    timeS += 1;

                    timeSi = String.valueOf(timeS);
                    timeMi = String.valueOf(timeM);

                    if (timeM<=9){
                        timeMi = "0" + String.valueOf(timeM);
                    }

                    if (timeS<=9){
                        timeSi = "0" + String.valueOf(timeS);
                    }
                    if (time != null && time.equals(timeMi+":"+timeSi)) timer1.cancel();
                    labelTime.setText(timeMi+":"+timeSi);
                }
            }, 1000, 1000);
        }
    }
}
//
//class ImagePanel extends JComponent {
//    private Image image;
//    public ImagePanel(Image image) {
//        this.image = image;
//    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(image, 0, 0, this);
//    }
//}




