//
// To change this license header, choose License Headers in Project Properties.
// To change this template file, choose Tools | Templates
// and open the template in the editor.
//
package asteroids;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 * The Purpose of Enum $(name) is to
 * <p>
 * Teacher :: $(teacher)
 * <p>
 * Class :: $(class)
 * <p>
 * Author :: $(user)
 *
 * @author Mason Borenstein
 */
public enum SoundEffect {

    SHOOT("resources/sounds/fire.wav"),
    YOU_DIE("resources/sounds/bangLarge.wav"),
    INTRO("resources/sounds/AtariName.wav"),
    INTRO_SEGA("resources/sounds/Sega.wav"),
    THRUST("resources/sounds/thrust.wav"),
    STATIC("resources/sounds/thrust.wav"),
    ASTEROID_DIE("resources/sounds/bangLarge.wav"),
    METEOR_DIE("resources/sounds/bangMedium.wav"),
    METEORITE_DIE("resources/sounds/bangSmall.wav"),
    ALIEN_DIE("resources/sounds/bangMedium.wav"),
    ALIEN_SHOOT("resources/sounds/fire.wav"),
    NEW_LIFE("resources/sounds/extraShip.wav"),
    BEAT1("resources/sounds/beat1.wav"),
    BEAT2("resources/sounds/beat2.wav"),
    TYPE("resources/sounds/beep-3.wav"),
    HYPER_JUMP("resources/sounds/HyperJump.wav");

    // Nested class for specifying volume
    public static enum Volume {

        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.HIGH;

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    // Constructor to construct each element 
    // of the enum with its own sound file.
    SoundEffect(String soundFileName) {
        try {
            URL url = getClass().getResource(soundFileName);
            AudioInputStream audioInputStream;
            audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException |
                IOException | LineUnavailableException e) {
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play() {
        if (volume != Volume.MUTE) {
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
    }

    public boolean isRunning() {
        return clip.isRunning();
    }

    // Stops the clip
    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
}
