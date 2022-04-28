package smoothieoperator.src.gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import smoothieoperator.src.main.GamePanel;
import smoothieoperator.src.spriteEssentials.*;

/**
 * Represents a solid wall along the righthand edge of the screen. A wall is solid, so it
 * may collide with other Sprites.
 */
public class Wall extends Sprite {
    
    /**
     * Creates a new Wall object along the righthand edge of the screen with the given image.
     * 
     * @param image the image to draw onscreen for this Wall.
     */
    public Wall(BufferedImage image) {
        // A null image parameter won't change the behavior of this Sprite, so it's okay.
        super(GamePanel.SCREEN_WIDTH, 0, image);
        // Adjust based on image width after super constructor so guaranteed an image at index 0
        this.x -= this.images.get(0).getWidth();
        this.solid = true;
    }

    /**
     * Returns the HitBox with one corner at this Sprite's (x, y) point, and another corner
     * at the screen's lower righthand corner.
     */
    @Override
    public HitBox getHitBox() {
        if (!isSolid()) {
            return null;
        }
        return new HitBox(this.x, GamePanel.SCREEN_WIDTH, this.y, GamePanel.SCREEN_HEIGHT);
    }

    /**
     * Draws this Sprite's single image to the screen.
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(0), null, this.getX(), this.getY()); // Only one image
    }
}
