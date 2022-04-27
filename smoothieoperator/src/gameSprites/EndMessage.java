package smoothieoperator.src.gameSprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import smoothieoperator.src.main.KeyHandler;
import smoothieoperator.src.spriteEssentials.*;

public class EndMessage extends Sprite {

    private boolean displayMessage;
    private boolean hasWon;
    private BufferedImage losingImage;
    private BufferedImage winningImage;

    public EndMessage(int x, int y, BufferedImage losingImage, BufferedImage winningImage, 
            KeyHandler keyHandler) {
        this.x = x;
        this.y = y;
        this.losingImage = losingImage;
        this.winningImage = winningImage;
        this.keyHandler = keyHandler;
        this.displayMessage = false;
        this.solid = false;
    }

    public void displayEndMessage() {
        this.displayMessage = true;
    }

    public void removeEndMessage() {
        this.displayMessage = false;
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