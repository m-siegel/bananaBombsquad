package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URL;


// ideas for how to use Sound:
// each Sprite has its own Sound (or multiple. will need to use ArrayList<Sound>):
// Target: splash, Cannon: boom, Projectile: whoosh and splat, Background: game music,
// EndMessage (our newest Sprite): end music, Lives: lose a life, Powerbar: None?, Wall: None?
// Can add methods playSound(Sound s), stopSound(Sound s), and loopSound(Sound s) to the Sprite class
// These methods could be called from other methods within a Sprite's class.
// For example, Lives.loseLife() could call playSound(this.sounds.get(0))
// So the sound would play whenever lives.loseLife() is called from GamePanel

// Other notes:
// LoadingScreen and GamePanel can each have their own instance of a Background which has its own song (and image).
// Could make SoundEffect and Music as subclasses of Sound, but I don't know if that's really necessary,
// since they're not that different from each other.
public class Sound {
    
    private Clip clip;
    private URL url;

    public Sound(Clip clip, String url) {
        if (clip == null || url == null) {
            throw new NullPointerException();
        }
        this.url = getClass().getResource(url);
        setFile();
    }

    public void setFile() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(this.url);
            this.clip = AudioSystem.getClip();
            this.clip.open(stream);
        } catch (IOException e){
            //TODO - how should these exceptions be handled?
        } catch (UnsupportedAudioFileException e) {
        } catch (LineUnavailableException e) {
        }
    }

    public Clip getClip() {
        return this.clip;
    }

    public URL getURL() {
        return this.url;
    }
    
}
