package gameSprites;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import main.KeyHandler;
import java.awt.Graphics2D;
import spriteEssentials.*;

public class Lives extends Sprite {
    static final int TOTAL_LIVES = 7;
    private int lives;

    public int getLives() { return this.lives; }

    public Lives(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        this.lives = 5;
    }

    // accessed through GamePanel
    public void loseLife() {
        if (lives > 1) {
            this.lives--;
        }
    }

    public void livesReset() {
        this.lives = TOTAL_LIVES;
    }

    // accessed through GamePanel
    public boolean isDead() {
        if (lives <= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(super.images.get(this.lives - 1), null, super.getX(), super.getY());

    }
    
}