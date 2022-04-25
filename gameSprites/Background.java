package gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.GamePanel;
import spriteEssentials.*;

public class Background extends Sprite {

    public Background(BufferedImage image) {
        super(0, 0, image);
        this.solid = true;
    }

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
        g2.drawImage(super.images.get(0), null, super.getX(), super.getY());
    }    
}
