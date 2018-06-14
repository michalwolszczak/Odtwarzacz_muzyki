package odtwarzacz.widoki;

import odtwarzacz.app.Aplikacja;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MyFrame extends JFrame {
    public Image imgTlo;
    public static ImageIcon iconStart,iconPause;
    public JLabel labelTime,labelTimeEnd;
    public JButton button;
    public Border emptyBorder;
    public Aplikacja app;
    public JProgressBar progressBar;
    private Timer timer1;
    private int timeM = 0,timeS = 0;
    private String timeMi,timeSi,time;

    public MyFrame() throws IOException {
        super("Odtwarzacz MP3");
        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));

        imgTlo = ImageIO.read(new File("C:\\Users\\Michał\\Desktop\\tlo.jpg"));
        labelTime = new JLabel();
        labelTime.setText("00:00");
        labelTime.setBounds(250,549,50,50);

        labelTimeEnd = new JLabel();
        labelTimeEnd.setText("00:00");
        labelTimeEnd.setBounds(765,549,50,50);

        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setBounds(300, 570, 450, 10);

        button = new JButton();
        button.setBounds(470,600,100,100);

        try {
            emptyBorder = BorderFactory.createEmptyBorder();
            button.setBorder(emptyBorder);
            iconStart = new ImageIcon("C:\\Users\\Michał\\Desktop\\start.png");
            iconPause = new ImageIcon("C:\\Users\\Michał\\Desktop\\pause.png");
            button.setIcon(iconStart);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        button.addActionListener(e -> {
            if (app == null) {
                try {
                    app = new Aplikacja();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            app.logic(button, progressBar);
            time = app.timeS.replace(".",":");

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
                    if (time.equals(timeMi+":"+timeSi)) timer1.cancel();
                    labelTime.setText(timeMi+":"+timeSi);
                }
            }, 1000, 1000);
            if (app.timeS != null) labelTimeEnd.setText(app.timeS.replace(".",":"));

        });

        //this.setContentPane(new ImagePanel(imgTlo));
        add(progressBar);
        add(button);
        add(labelTime);
        add(labelTimeEnd);

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class ImagePanel extends JComponent {
    private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}




