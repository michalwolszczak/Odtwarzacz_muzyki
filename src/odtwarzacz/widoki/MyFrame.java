package odtwarzacz.widoki;

import odtwarzacz.app.Application;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;


public class MyFrame extends JFrame {
    public static ImageIcon iconStart,iconPause, iconNext, iconBef, iconAdd;
    public JLabel labelTime,labelTimeEnd, labelTitle;
    public JButton buttonStart, buttonNext, buttonBef, buttonAdd;
    private Border emptyBorder;
    private Application app;
    public JProgressBar progressBar;

    public MyFrame(){
        super("Odtwarzacz MP3");

        if (app == null) app = new Application();

        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        this.getContentPane().setBackground(Color.white);

        app.startCheckedFiles(this, false);

        labelTime = new JLabel();
        labelTime.setText("00:00");
        labelTime.setBounds(250,549,50,50);

        labelTimeEnd = new JLabel();
        labelTimeEnd.setText("00:00");
        labelTimeEnd.setBounds(765,549,50,50);

        labelTitle = new JLabel("",SwingConstants.CENTER);
        labelTitle.setFont(new Font("Serif", Font.PLAIN, 45));
        labelTitle.setBounds(250,400,500,200);

//        labelFile = new JLabel("",SwingConstants.CENTER);
//        labelFile.setFont(new Font("Serif", Font.PLAIN, 20));
//        labelFile.setBounds(50,,500,200);

        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setBounds(300, 570, 450, 10);

        buttonStart = new JButton();
        buttonStart.setBounds(470,600,100,100);

        buttonNext = new JButton();
        buttonNext.setBounds(570,600,100,100);

        buttonBef = new JButton();
        buttonBef.setBounds(370,600,100,100);

        buttonAdd = new JButton();
        buttonAdd.setBounds(20,20,50,50);

        emptyBorder = BorderFactory.createEmptyBorder();
        buttonAdd.setBorder(emptyBorder);
        buttonStart.setBorder(emptyBorder);
        buttonNext.setBorder(emptyBorder);
        buttonBef.setBorder(emptyBorder);
        iconAdd = new ImageIcon("src\\ikony\\add.jpg");
        iconStart = new ImageIcon("src\\ikony\\start.png");
        iconPause = new ImageIcon("src\\ikony\\pause.png");
        iconNext = new ImageIcon("src\\ikony\\next.png");
        iconBef = new ImageIcon("src\\ikony\\bef.png");
        buttonAdd.setIcon(iconAdd);
        buttonStart.setIcon(iconStart);
        buttonNext.setIcon(iconNext);
        buttonBef.setIcon(iconBef);

        buttonStart.addActionListener((ActionEvent e) -> {
            app.start();
        });

        buttonAdd.addActionListener(e -> {
            app.addFiles();
            app.startCheckedFiles(this, true);
        });

        buttonNext.addActionListener(e -> System.out.println("1"));


        buttonBef.addActionListener((ActionEvent e) -> {
            app.stop();
        });

        add(progressBar);
        add(buttonStart);
        add(buttonAdd);
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
}