package smoothieoperator.src.gameSprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Graphics2D;

import smoothieoperator.src.main.GamePanel;
import smoothieoperator.src.main.Sound;
import smoothieoperator.src.spriteEssentials.*;


/**
 * Represents the background of the game screen. The GamePanel.TILE_SIZE section of this
 * Background along the bottom of the screen is considered solid for the purposes of collisions
 * with other Sprites.
 */
public class Background extends Sprite {

    /**
    * Creates the game's background from a single image. Coordinates (0, 0) 
    * are top left of the game screen. The background is stationary and is 
    * set to solid by default.
    * Creates a Sound object associated with a Background.
    *
    * @param image the background image, which should have same proportions 
    *        and pixel height/width as the game screen
    * @param musicFile the filepath used to retrieve the audio file
    * @param songName the name given to the key associated with the Sound
    * @throws IllegalArgumentException if the image is null, if the image's dimensions
    *         are different than the screen dimensions, if the musicFile is null, or if
    *         the songName is null.
    */
    public Background(BufferedImage image, String musicFile, String songName) {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be null.");
        }
        if (musicFile == null) {
            throw new IllegalArgumentException("musicFile cannot be null.");
        }
        if (songName == null) {
            throw new IllegalArgumentException("songName cannot be null.");
        }
        if (image.getHeight() != GamePanel.SCREEN_HEIGHT
                || image.getWidth() != GamePanel.SCREEN_WIDTH ) {
                    throw new IllegalArgumentException(
                            "image dimensions must match screen dimensions.");
                }
        this.x = 0;
        this.y = 0;
        this.images = new ArrayList<BufferedImage>();
        this.images.add(image);
        this.solid = true;
        
        try {
            this.sounds.put(songName, new Sound(musicFile));
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        }
    }

    /** 
     * Hit box represents the ground, which must be set so projectile 
     * images can collide with it.
     */
    @Override
    public HitBox getHitBox() {
        if (!isSolid()) {
            return null;
        } else {
            // extend beyond the bottom of the screen to catch fast objects
            return new HitBox(0, GamePanel.SCREEN_WIDTH, 
                    GamePanel.SCREEN_HEIGHT - GamePanel.TILE_SIZE, 10 * GamePanel.SCREEN_HEIGHT);
        }        
    }

    /**
     * Draw this Background's single image to the screen.
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(0), null, this.getX(), this.getY());
    }    
}