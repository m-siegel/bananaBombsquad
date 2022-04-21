package gameSprites;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import main.KeyHandler;
import java.awt.Graphics2D;
import spriteEssentials.*;

public class PowerBar extends Sprite {
    private int maxPower;
    private int minPower;
    private int power;

    public PowerBar(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        this.maxPower = images.size();
        if (images.size() < 1) {
            throw new IllegalArgumentException("You need at least one image.");
        }
        this.minPower = 1;
        this.power = 5;
    }

    public int getPower() {
        return this.power;
    }

    public void increasePower() {
        if (this.power <= maxPower) {
            this.power++;
        }
    }

    public void decreasePower() {
        if (this.power >= minPower) {
            this.power--;
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
        g2.drawImage(super.images.get(power - 1), null, super.getX(), super.getY());

    }
}