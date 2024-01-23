package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    File sellSound;
    File buySound;
    File dropSound;
    File selectedSound;

    public Sound(){
        sellSound = new File("res/sound/sell.wav");
        buySound = new File("res/sound/buy.wav");
        dropSound = new File("res/sound/drop.wav");
    }

    public void playSound(String sound){
        switch (sound){
            case ("sellSound"):
                selectedSound = sellSound;
                break;
            case ("buySound"):
                selectedSound = buySound;
                break;
            case ("dropSound"):
                selectedSound = dropSound;
                break;
            default:
                break;
        }
        System.out.println(selectedSound.toString());
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(selectedSound);
            clip.open(ais);
            clip.start();
        }catch (LineUnavailableException | UnsupportedAudioFileException | IOException e){
            e.printStackTrace();
        }
    }
}
