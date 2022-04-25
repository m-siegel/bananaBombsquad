package gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import spriteEssentials.*;

/**
 * Represents an power bar in the game. Changes power based on keyboard input.
 */
public class PowerBar extends Sprite {

    public static final int UPDATES_PER_FRAME = GamePanel.FPS / 10;

    private int maxPower;
    private int minPower;

    public PowerBar(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        this.maxPower = images.size() - 1;
        if (images.size() < 1) {
            throw new IllegalArgumentException("You need at least one image.");
        }
        this.minPower = 0;
        this.imagesIndex = maxPower / 2; // imagesIndex represents the current power
    }

    public int getPower() {
        return this.imagesIndex;
    }

    public void increasePower() {
        if (getPower() < maxPower) {
            this.imagesIndex++;
        }
    }

    public void decreasePower() {
        if (getPower() > minPower) {
            this.imagesIndex--;
        }
    }

    @Override
    public void update() {
        if (keyHandler.getPowerUpPressed()) {
            increasePower();
        }
        if (keyHandler.getPowerDownPressed()) {
            decreasePower();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(getPower()), this.getX(), this.getY(), null);
    }
}