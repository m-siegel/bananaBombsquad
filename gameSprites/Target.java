package gameSprites;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import spriteEssentials.*;

public class Target extends Sprite {

    public static final int MAX_HITS = 3;

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

    public int getNumberOfHits() {
        return this.imagesIndex;
    }

    public boolean incrementNumberOfHits() {
        if (this.imagesIndex >= 3) { // if already full, do not increment
            return false;
        }
        this.imagesIndex++;
        return true;
    }

    public boolean isFull() {
        return this.imagesIndex >= 3;
    }

    public int getRandomXCoordinate() {
        Random randomizer = new Random();
        int oneThirdsScreen = GamePanel.SCREEN_WIDTH / 3;
        int randomInt = randomizer.nextInt(oneThirdsScreen 
                - this.images.get(getNumberOfHits()).getWidth());
        return randomInt + 2 * oneThirdsScreen;
    }

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

    public void resetPosition() {
        this.x = getRandomXCoordinate();
        this.y = getRandomYCoordinate();
    }

    public void resetHits() {
        this.imagesIndex = 0;
    }

    public void reset() {
        resetPosition();
        resetHits();
    }
}
