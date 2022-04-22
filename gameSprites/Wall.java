package gameSprites;
import main.GamePanel;
import spriteEssentials.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Wall extends Sprite {
    
    public Wall(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void draw(Graphics2D g2) {
        int tempX = GamePanel.SCREEN_WIDTH - images.get(0).getWidth();
        int tempY = 0;
        while (tempY < GamePanel.SCREEN_HEIGHT) {
            g2.drawImage(images.get(0), null, tempX, tempY);
            tempY += images.get(0).getHeight(); 
        }
    }
}