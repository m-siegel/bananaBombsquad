package smoothieoperator.src.gameSprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;

import smoothieoperator.src.main.GamePanel;
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
    *
    * @param image the background image, which should have same proportions 
    *        and pixel height/width as the game screen
    * @throws IllegalArgumentException if the image is null or if the image's dimensions
    *         are different than the screen dimensions.
    */
    public Background(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be null.");
        }
        if (image.getHeight() != GamePanel.SCREEN_HEIGHT
                || image.getWidth() != GamePanel.SCREEN_WIDTH ) {
                    throw new IllegalArgumentException(
                            "image dimensions must match screen dimensions");
                }
        this.x = 0;
        this.y = 0;
        this.images = new ArrayList<BufferedImage>();
        this.images.add(image);
        this.solid = true;
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