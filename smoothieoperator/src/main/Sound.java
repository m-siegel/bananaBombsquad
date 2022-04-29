package smoothieoperator.src.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URL;

/**
 * Provides methods for playing, stopping, and looping music and sound effects in the game.
 * Provides a reset method which brings the sound back to its starting position and allows it
 * to be played from the beginning again.
 */
public class Sound {
    
    private Clip clip;
    private URL url;

    /**
     * Creates a new Sound object using the given filepath String.
     * Creates a URL using the filepath, and uses the setFile() method to 
     * create an AudioInputStream with the URL, which provides the sound input to the Clip.
     * 
     * @param filepath the name of the audio file.
     * @throws NullPointerException if the given filepath String is null.
     */
    public Sound(String filepath) {
        if (filepath == null) {
            throw new NullPointerException();
        }
        this.url = getClass().getResource(filepath);
        setFile();
    }

    /**
     * Creates an AudioInputStream which obtains the sound data from the Sound object's URL.
     * Obtains a clip for the Sound object which is opened with the data in the AudioInputStream.
     * Closes the stream to release any system resources it may have used. 
     */
    public void setFile() {
        AudioInputStream stream = null;
        try {
            stream = AudioSystem.getAudioInputStream(this.url);
            this.clip = AudioSystem.getClip();
            this.clip.open(stream);
            stream.close();
        } catch (IOException e){
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        } catch (SecurityException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Clip getClip() {
        return this.clip;
    }

    public URL getURL() {
        return this.url;
    }

    /**
     * Plays the sound clip associated with this Sound object.
     */
    public void playSound() {
        if (this.clip != null) {
            this.clip.start();
        }
    }

    /**
     * Stops the sound clip associated with this Sound object.
     * Moves the position of the sound clip back to its starting media position in microseconds,
     * allowing it to start from this position the next time it is played. The level of precision
     * is not guaranteed.
     */
    public void stopSound() {
        if (this.clip != null) {
            this.clip.stop();
            this.clip.setMicrosecondPosition(0);
        }
    }

    /**
     * Sets the media position of the sound clip back to its zeroeth sample frame,
     * which allows it start from the beginning the next time it is played.
     */
    public void reset() {
        if (this.clip != null) {
            this.clip.setFramePosition(0);
        }
    }

    /**
     * Loops the sound clip associated with this Sound object continuously.
     */
    public void loopSound() {
        if (this.clip != null) {
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}
