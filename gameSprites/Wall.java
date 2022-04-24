package gameSprites;
import main.GamePanel;
import spriteEssentials.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Wall extends Sprite {
    
    public Wall(BufferedImage image) {
        super(GamePanel.SCREEN_WIDTH, 0, image);
        this.x -= this.images.get(0).getWidth();
        this.solid = true;
    }

    // @Override
    // public void draw(Graphics2D g2) {
    //     int tempX = GamePanel.SCREEN_WIDTH - images.get(0).getWidth();
    //     int tempY = 0;
    //     while (tempY < GamePanel.SCREEN_HEIGHT) {
    //         g2.drawImage(images.get(0), null, tempX, tempY);
    //         tempY += images.get(0).getHeight(); 
    //     }
    // }

    public HitBox getHitBox() {
        if (!isSolid()) {
            return null;
        }
        return new HitBox(this.x, GamePanel.SCREEN_WIDTH, this.y, GamePanel.SCREEN_HEIGHT);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(0), null, this.getX(), this.getY());
    }
}