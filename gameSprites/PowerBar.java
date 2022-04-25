package gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import spriteEssentials.*;

public class PowerBar extends Sprite {

    public static final int UPDATES_PER_FRAME = GamePanel.FPS / 10;

    private int maxPower;
    private int minPower;
    private int power;

    public PowerBar(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        this.maxPower = images.size() - 1;
        if (images.size() < 1) {
            throw new IllegalArgumentException("You need at least one image.");
        }
        this.minPower = 0;
        this.power = maxPower / 2;
    }

    public int getPower() {
        return this.power;
    }

    public void increasePower() {
        if (this.power < maxPower) {
            this.power++;
        }
    }

    public void decreasePower() {
        if (this.power > minPower) {
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
        g2.drawImage(images.get(power), this.x, this.y, null);

    }
}
