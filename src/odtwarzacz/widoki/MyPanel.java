//package odtwarzacz.widoki;
//
//import odtwarzacz.app.Aplikacja;
//import java.awt.*;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import javax.swing.border.Border;
//
//public class MyPanel extends JPanel {
//    public Image imgTlo;
//    public ImageIcon iconStart,iconPause;
//    public JLabel label1,label2;
//    public JButton button;
//    public Border emptyBorder;
//    public Aplikacja app;
//    public JProgressBar progressBar;
//
//    public MyPanel() throws IOException {
//
//        setPreferredSize(new Dimension(1024, 768));
//        imgTlo = ImageIO.read(new File("C:\\Users\\Michał\\Desktop\\tlo.jpg"));
////        label1 = new JLabel();
////        label1.setText("czas");
//        //JLabel lab1 = new JLabel("User Name", JLabel.LEFT);
//        setLayout(new BorderLayout());
//        //add(lab1);
//        progressBar = new JProgressBar();
//        progressBar.setMinimum(0);
//        //progressBar.setMaximum(100);
//
//        button = new JButton();
//        button.setBounds(470,650,90,90);
//
//        try {
//            emptyBorder = BorderFactory.createEmptyBorder();
//            button.setBorder(emptyBorder);
//            iconStart = new ImageIcon("C:\\Users\\Michał\\Desktop\\start.png");
//            iconPause = new ImageIcon("C:\\Users\\Michał\\Desktop\\pause.png");
//            button.setIcon(iconStart);
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//        button.addActionListener(e -> {
//            if (app == null) {
//                try {
//                    app = new Aplikacja();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//
//            }
//            app.logic(button, progressBar);
//        });
//
//       // add(label1);
//        add(progressBar);
//        add(button);
//    }
//
//    public void paint(Graphics g) {
//       // g.drawImage(imgTlo, 0, 0, null);
//        //progressBar.setBounds(300, 570, 450, 10);
//        progressBar.repaint();
//        button.repaint();
//    }
//}