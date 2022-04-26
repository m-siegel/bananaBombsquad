package gameSprites;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.KeyHandler;
import spriteEssentials.*;

/**
 * Represents the amount of lives in the game. Life indicator itself does not move but
 * animates through ArrayList of images after losing a life in the game or resetting. 
 * TOTAL_LIVES indicates how many lives a round has, and imagesIndex represents which 
 * image in the ArrayList that the object is at during the round. This decrements if 
 * there is no collision and resets at the end of each round. Also has an isDead() 
 * method to indicate when a player is out of lives.
 */
public class Lives extends Sprite {

    static final int TOTAL_LIVES = 6;

    private int imagesIndex;

    /**
    * Creates a stationary life indicator that starts at TOTAL_LIVES and ends at 0 lives.
    * No restrictions on where the x and y coordinates can be placed. Coordinates are the 
    * top-left corner of images.
    * 
    * @param x the x-coordinate of the life indicator object
    * @param y the y-coordinate of the life indicator object
    * @param images the ArrayList of life images - image index should correspond to
    *        the number of lives the image represents, i.e. 2 lives should be at index 2
    * @param keyH the keyHandler, which sets to null
    * @throws IllegalArgumentException if the amount of images in the ArrayList is less
    *         than TOTAL_LIVES + 1 (to account for image with 0 lives)
    */
    public Lives(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        if (this.images.size() < TOTAL_LIVES + 1) {
            throw new IllegalArgumentException("Must have at least " + (TOTAL_LIVES + 1) 
                    + "images (" + TOTAL_LIVES + " lives).");
        }
        this.imagesIndex = TOTAL_LIVES;
    }

    /**
     * Returns the number of lives left (value of imagesIndex).
     * 
     * @return number of lives left (value of imagesIndex).
     */
    public int getLives() {
        return this.imagesIndex;
    }

    /**
     * Decrements the number of lives, if there is at least one left.
     */
    public void loseLife() {
        if (this.imagesIndex >= 1) {
            this.imagesIndex--;
        }
    }

    /**
     * Returns true if there are zero or fewer lives left, meaning the player "is dead".
     * 
     * @return true if there are zero or fewer lives left; false otherwise.
     */
    public boolean isDead() {
        if (imagesIndex <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resets the number of lives (value of imagesIndex) to TOTAL_LIVES.
     */
    public void livesReset() {
        this.imagesIndex = TOTAL_LIVES;
    }

    /**
     * Draws the image from teh images ArrayList that corresponds to the current number of lives.
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(this.imagesIndex), null, this.getX(), this.getY());
    }
}