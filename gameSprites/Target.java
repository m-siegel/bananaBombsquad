package gameSprites;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import spriteEssentials.*;

public class Target extends Sprite {

    public static final int MAX_HITS = 3;

    private int numberOfHits;

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
        this.numberOfHits = 0;
        this.imagesIndex = 0; // Will not be used
        this.images = Sprite.copyBufferedImages(images);
    }

    public boolean incrementNumberOfHits() {
        if (this.numberOfHits >= 3) { // if already full, do not increment
            return false;
        }
        this.numberOfHits++;
        return true;
    }

    public boolean isFull() {
        return this.numberOfHits >= 3;
    }

    public int getRandomXCoordinate() {
        Random randomizer = new Random();
        int oneThirdsScreen = GamePanel.SCREEN_WIDTH / 3;
        int randomInt = randomizer.nextInt(oneThirdsScreen - this.images.get(numberOfHits).getWidth());
        return randomInt + 2 * oneThirdsScreen;
    }

    public int getRandomYCoordinate() {
        Random randomizer = new Random();
        int randomInt = randomizer.nextInt(
                GamePanel.SCREEN_HEIGHT - this.images.get(numberOfHits).getHeight()); // random int between 0 - screen
                                                                                      // height, inclusive
        return randomInt;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(numberOfHits), this.x, this.y, null);
    }

    public void resetPosition() {
        this.x = getRandomXCoordinate();
        this.y = getRandomYCoordinate();
    }

    public void resetHits() {
        this.numberOfHits = 0;
    }

    public void reset() {
        resetPosition();
        resetHits();
    }
}
