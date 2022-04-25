package gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.GamePanel;
import spriteEssentials.*;

public class Background extends Sprite {

    /**
    * Creates the game's background from a single image. Coordinates (0, 0) 
    * are top left of the game screen. The background is stationary and is 
    * set to solid by default.
    * @param image the background image, which should have same proportions 
    * and pixel height/width as the game screen
    */
    public Background(BufferedImage image) {
        super(0, 0, image);
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

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(0), null, this.getX(), this.getY());
    }    
}