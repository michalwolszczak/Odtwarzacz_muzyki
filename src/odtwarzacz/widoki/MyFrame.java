package odtwarzacz.widoki;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MyFrame extends JFrame {

    public MyFrame() throws IOException {
        super("Odtwarzacz MP3");
        JPanel panel = new MyPanel();
        add(panel);

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}



