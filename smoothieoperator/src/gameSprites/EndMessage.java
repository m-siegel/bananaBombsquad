package smoothieoperator.src.gameSprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import smoothieoperator.src.spriteEssentials.*;

/**
 * Represents the message that displays at the end of a game. The end message is
 * stationary and displays either a winning or losing message depending on the
 * outcome of the game.
 */
public class EndMessage extends Sprite {

    private boolean displayMessage;
    private boolean hasWon;

   /**
   * Creates a stationary EndMessage object with the given losingImage and winningImage. 
   * The displayMessage is set to false by default and is set to true when the end of
   * the game is triggered in the GamePanel. Object is set to not solid by default.
   *
   * <p>Creates a Sound object associated with the winning/losing EndMessage.
   * 
   * @param losingImage the image to draw when the player loses the game
   * @param winningImage the image to draw when the player wins the game
   * @throws IllegalArgumentException if any of the reference type parameters are null.
   */
    public EndMessage(int x, int y, BufferedImage losingImage, BufferedImage winningImage) {
        if (losingImage == null) {
            throw new IllegalArgumentException("losingImage parameter cannot be null.");
        }
        if (winningImage == null) {
            throw new IllegalArgumentException("winningImage parameter cannot be null.");
        }
        this.x = x;
        this.y = y;
        this.images = new ArrayList<BufferedImage>();
        this.images.add(winningImage);
        this.images.add(losingImage);
        this.displayMessage = false;
        this.solid = false;
        try {
            this.addSound("/smoothieoperator/src/media/sounds/winning.wav", "winningSong");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.addSound("/smoothieoperator/src/media/sounds/losing.wav", "losingSong");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the displayMessage boolean that determines whether or not this EndMessage is drawn.
     * 
     * @param setVisible whether or not to draw this EndMessage.
     */
    public void displayEndMessage(boolean setVisible) {
        this.displayMessage = setVisible;
    }

    /**
     * Updates hasWon variable based on the parameter.
     * 
     * @param status whether or not the player has won.
     */
    public void playerWon(boolean status) {
        if (status) {
            this.hasWon = true;
        } else {
            this.hasWon = false;
        }
    }

    /**
     * Draws the end message only if displayMessage is true.
     */
    @Override
    public void draw(Graphics2D g2) {
        if (displayMessage) {
            if (hasWon) {
                g2.drawImage(images.get(0), null, this.x, this.y);
            } else {
                g2.drawImage(images.get(1), null, this.x, this.y);
            }
        }
    }
}
