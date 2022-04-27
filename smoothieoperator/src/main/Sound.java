package smoothieoperator.src.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URL;


// ideas for how to use Sound:
// each Sprite has its own Sound (or multiple. will need to use ArrayList<Sound>):
    // what about a HashMap<"sound name", sound file>? -- easier/clearer to access (don't have to track indices)
// Target: splash, Cannon: boom, Projectile: whoosh and splat, Background: game music,
// EndMessage (our newest Sprite): end music, Lives: lose a life, Powerbar: None?, Wall: None?
// Can add methods playSound(Sound s), stopSound(Sound s), and loopSound(Sound s) to the Sprite class
    // what if each Sprite that uses sound is instantiated with a Sound object as a parameter, then from loseLife()
    // or whatever method, it would call the methods on it's Sound object, eg the Projectile splat() could call
    // this.sound.playSound("splat")?
    // this.sounds.get("splat").playSound()
// These methods could be called from other methods within a Sprite's class.
// For example, Lives.loseLife() could call playSound(this.sounds.get(0))
// So the sound would play whenever lives.loseLife() is called from GamePanel

// Other notes:
// LoadingScreen and GamePanel can each have their own instance of a Background which has its own song (and image).
    // LOVE IT!!
// Could make SoundEffect and Music as subclasses of Sound, but I don't know if that's really necessary,
// since they're not that different from each other.
    // I'd lean towards only having one Sound class, but don't feel that strongly
public class Sound {
    
    private Clip clip;
    private URL url;

    public Sound(String filepath) {
        if (filepath == null) {
            throw new NullPointerException();
        }
        this.url = getClass().getResource(filepath);
        setFile();
    }

    public void setFile() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(this.url);
            this.clip = AudioSystem.getClip();
            this.clip.open(stream);
            stream.close();
        } catch (IOException e){
            //TODO - how should these exceptions be handled?
                // we could return false, or set some instance variable to false so we don't try
                // playing a nonexistent clip later and don't crash the game.
                // Or we could pass it up the chain and have GamePanel write to a file the stack
                // traces of all the errors it catches so we could see it afterwards.
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

    // public void play(), stop(), loop()?

    public void playSound() {
        this.clip.start();
    }

    public void stopSound() {
        this.clip.stop();
    }

    public void reset() {
        this.clip.setFramePosition(0);
    }

    public void loopSound() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
}
