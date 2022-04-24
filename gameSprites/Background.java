package gameSprites;
import spriteEssentials.*;
import java.awt.image.BufferedImage;
import main.GamePanel;
import java.awt.Graphics2D;

public class Background extends Sprite {

    public Background(BufferedImage image) {
        super(0, 0, image);
        this.solid = false;
    }

    @Override
    public HitBox getHitBox() {
        if (!isSolid()) {
            return null;
        } else {
            // return new HitBox(0, GamePanel.SCREEN_WIDTH, 
            //     GamePanel.SCREEN_HEIGHT - (GamePanel.TILE_SIZE * GamePanel.SCALE) / 12, GamePanel.SCREEN_HEIGHT);
            return new HitBox(0, GamePanel.SCREEN_WIDTH, 
            GamePanel.SCREEN_HEIGHT - 5, GamePanel.SCREEN_HEIGHT);
        }        
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(super.images.get(0), null, super.getX(), super.getY());
    }    
}
