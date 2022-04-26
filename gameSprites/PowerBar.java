package gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.KeyHandler;
import spriteEssentials.*;

/**
 * Represents an power bar in the game. Changes power based on keyboard input.
 */
public class PowerBar extends Sprite {

    private int maxPower;
    private int minPower;

    /**
     * Create a new PowerBar object in the given location (x, y) with the specified 
     * BufferedImages for animation and KeyHandler to respond to keyboard input.
     * While a PowerBar may be instantiated with a null image or only one image, it
     * should have multiple images for the best effect.
     * 
     * @param x the x-coordinate to be this PowerBar's leftmost edge.
     * @param y the y-coordinate to be this PowerBar's upper edge.
     * @param images BufferedImages to cycle through to animate this PowerBar.
     * @param keyH the game's KeyHandler so this object can respond to keyboard input.
     * @throws IllegalArgumentException if the KeyHandler parameter is null.
     */
    public PowerBar(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        this.maxPower = images.size() - 1;
        if (keyH == null) {
            throw new IllegalArgumentException("The KeyHandler parameter cannot be null.");
        }
        this.minPower = 0;
        this.imagesIndex = maxPower / 2; // imagesIndex represents the current power
    }

    public int getMaxPower() {
        return this.maxPower;
    }

    public int getMinPower() {
        return this.minPower;
    }

    /**
     * Returns this PowerBar's current power setting (the value of imagesIndex).
     * 
     * @return the value of this imagesIndex.
     */
    public int getPower() {
        return this.imagesIndex;
    }

    /**
     * Increments the current power (imageIndex) while it is less than maxPower.
     */
    public void increasePower() {
        if (getPower() < maxPower) {
            this.imagesIndex++;
        }
    }

    /**
     * Increments the current power (imageIndex) while it is greater than minPower.
     */
    public void decreasePower() {
        if (getPower() > minPower) {
            this.imagesIndex--;
        }
    }

    /**
     * Updates this object's current power (imageIndex) based on keyHandler variables.
     */
    @Override
    public void update() {
        if (keyHandler.getPowerUpPressed()) {
            increasePower();
        }
        if (keyHandler.getPowerDownPressed()) {
            decreasePower();
        }
    }

    /**
     * Draws the image corresponding to the current power (imageIndex) value in images.
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(getPower()), this.getX(), this.getY(), null);
    }
}