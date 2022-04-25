package gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.GamePanel;
import spriteEssentials.*;

public class Wall extends Sprite {
    
    public Wall(BufferedImage image) {
        super(GamePanel.SCREEN_WIDTH, 0, image);
        // Adjust based on image width after super constructor so guaranteed an image at index 0
        this.x -= this.images.get(0).getWidth();
        this.solid = true;
    }

    @Override
    public HitBox getHitBox() {
        if (!isSolid()) {
            return null;
        }
        return new HitBox(this.x, GamePanel.SCREEN_WIDTH, this.y, GamePanel.SCREEN_HEIGHT);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(0), null, this.getX(), this.getY()); // Only one image
    }
}