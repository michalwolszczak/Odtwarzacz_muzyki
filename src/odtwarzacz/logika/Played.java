package odtwarzacz.logika;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import javax.sound.sampled.UnsupportedAudioFileException;


public class Played {
    private File filename;
    private Player player;
    private int duration=0, seconds=0, minute=0;
    private String mp3Second, mp3Minutes;
    private AudioFile audioFile;
    private FileInputStream fis;
    private BufferedInputStream bis;

    public Played(String filename) {
        this.filename = new File(filename);
    }

    public void close() { if (player != null) player.close(); }

    public String play() {
        try {
            fis     = new FileInputStream(filename);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            audioFile = AudioFileIO.read(filename);
            duration= audioFile.getAudioHeader().getTrackLength();

            seconds = duration % 60;
            minute = (duration - seconds) / 60;

            if (seconds < 10) {
                mp3Second = "0" + seconds;
            } else {
                mp3Second = "" + seconds;
            }

            if (minute < 10) {
                mp3Minutes = "0" + minute;
            } else {
                mp3Minutes = "" + minute;
            }
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
        return mp3Minutes+"."+mp3Second;
    }


    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        String filename = "C:\\Users\\Michał\\Desktop\\BEKA KSH x QBIK - Dla Niej To Mało.mp3";
        Played mp3 = new Played(filename);
        mp3.play();


        //mp3.close();

        // play from the beginning
        //mp3 = new odtwarzacz.logika.Played(filename);
        //mp3.play();

    }

}
