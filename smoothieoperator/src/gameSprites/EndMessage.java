package smoothieoperator.src.gameSprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import smoothieoperator.src.spriteEssentials.*;

/**
 * Represents the message that displays at the end of a game.
 */
public class EndMessage extends Sprite {

    private boolean displayMessage;
    private boolean hasWon;
    private BufferedImage losingImage;
    private BufferedImage winningImage;

    public EndMessage(int x, int y, BufferedImage losingImage, BufferedImage winningImage) {
        if (losingImage == null) {
            throw new IllegalArgumentException("losingImage parameter cannot be null.");
        }
        if (winningImage == null) {
            throw new IllegalArgumentException("winningImage parameter cannot be null.");
        }
        this.x = x;
        this.y = y;
        this.losingImage = losingImage;
        this.winningImage = winningImage;
        this.displayMessage = false;
        this.solid = false;
        try {
            this.addSound("/smoothieoperator/src/media/sounds/winning.wav", "winningSong");
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        }
        try {
            this.addSound("/smoothieoperator/src/media/sounds/losing.wav", "losingSong");
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        }
    }

    public void displayEndMessage(boolean display) {
        this.displayMessage = display;
    }

    public void playerWon(boolean status) {
        if (status) {
            this.hasWon = true;
        } else {
            this.hasWon = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (displayMessage) {
            if (hasWon) {
                g2.drawImage(winningImage, null, this.x, this.y);
            } else {
                g2.drawImage(losingImage, null, this.x, this.y);
            }
        }
    }
}