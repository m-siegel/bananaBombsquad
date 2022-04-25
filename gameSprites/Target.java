package gameSprites;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import spriteEssentials.*;

public class Target extends Sprite {

    public static final int MAX_HITS = 3;

    /**
     * Creates a new Target with the given ArrayList of images.
     * Chooses random x and y coordinates (constrained to the right side of the screen).
     * Sets speed to zero and keyHandler to null.
     * imagesIndex tracks how many times target is hit, initially 0. Images are drawn accordingly.
     * @param images Image list for the target.
     */
    public Target(ArrayList<BufferedImage> images) {
        if (images == null) {
            throw new NullPointerException("Cannot instantiate a target with null images.");
        }
        if (images.size() < MAX_HITS) {
            throw new IllegalArgumentException("Images must be at least " + MAX_HITS + " elementslong.");
        }
        this.x = getRandomXCoordinate();
        this.y = getRandomYCoordinate();
        this.speed = 0;
        this.keyHandler = null;
        this.solid = true;
        this.imagesIndex = 0;
        this.images = Sprite.copyBufferedImages(images);
    }

    /**
     * Returns the number of hits the target has registered, as tracked by imagesIndex.
     * @return this target's number of hits.
     */
    public int getNumberOfHits() {
        return this.imagesIndex;
    }

    /**
     * Increments the number of times the target has been hit, as tracked by imagesIndex.
     * If the number of hits has reached the maximum of 3, it is not incremented any further.
     * @return boolean to indicate whether the number of hits has been incremented successfully.
     */
    public boolean incrementNumberOfHits() {
        if (this.imagesIndex >= 3) {
            return false;
        }
        this.imagesIndex++;
        return true;
    }

    /**
     * Returns whether the maximum number of hits has been registered.
     * @return boolean to indicate whether the max number of hits has been reached.
     */
    public boolean isFull() {
        return this.imagesIndex >= 3;
    }

    /**
     * Returns a random x coordinate for the target's position.
     * Ensures that the chosen x will position the target on the right side of the screen.
     * @return a random int to determine target's x-coordinate.
     */
    public int getRandomXCoordinate() {
        Random randomizer = new Random();
        int oneThirdsScreen = GamePanel.SCREEN_WIDTH / 3;
        int randomInt = randomizer.nextInt(oneThirdsScreen 
                - this.images.get(getNumberOfHits()).getWidth());
        return randomInt + 2 * oneThirdsScreen;
    }

    /**
     * Returns a random y coordinate for the target's position.
     * Ensures that the chosen y will be no greater than the screen's height.
     * @return a random int to determine target's y coordinate.
     */
    public int getRandomYCoordinate() {
        Random randomizer = new Random();
        // random int between 0 - screen height, inclusive
        int randomInt = randomizer.nextInt(
                GamePanel.SCREEN_HEIGHT - this.images.get(getNumberOfHits()).getHeight()); 
        return randomInt;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(getNumberOfHits()), this.x, this.y, null);
    }

    /**
     * Resets the position of the target by choosing new random x and y coordinates.
     */
    public void resetPosition() {
        this.x = getRandomXCoordinate();
        this.y = getRandomYCoordinate();
    }

    /**
     * Resets the number of hits to 0.
     */
    public void resetHits() {
        this.imagesIndex = 0;
    }

    /**
     * Resets the target to 0 hits and a new random position.
     */
    public void reset() {
        resetPosition();
        resetHits();
    }
}
