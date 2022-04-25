package gameSprites;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.KeyHandler;
import spriteEssentials.*;

public class Lives extends Sprite {

    static final int TOTAL_LIVES = 6;

    private int lives;

    public Lives(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        if (this.images.size() < 7) {
            throw new IllegalArgumentException("Must have at least 7 images (6 lives).");
        }
        this.lives = 6;
    }

    public int getLives() {
        return this.lives;
    }

    public void loseLife() {
        if (this.lives >= 1) {
            this.lives--;
        }
    }

    public boolean isDead() {
        if (lives <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void livesReset() {
        this.lives = TOTAL_LIVES;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(this.lives), null, this.getX(), this.getY());
    }
}