package gameSprites;
import spriteEssentials.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Background extends Sprite {

    public Background(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(super.images.get(0), null, super.getX(), super.getY());
    }    
}
