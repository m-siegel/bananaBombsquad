package gameSprites;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import spriteEssentials.*;

public class PowerBar extends Sprite {
    private int maxPower;
    private int minPower;
    private int power;
    public static final int UPDATES_PER_FRAME = GamePanel.FPS / 10;

    public PowerBar(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
        super(x, y, images, keyH);
        this.maxPower = images.size() - 1;
        if (images.size() < 1) {
            throw new IllegalArgumentException("You need at least one image.");
        }
        this.minPower = 0;
        this.power = 5;
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
        //System.out.printf("Height: %d, Width: %d", images.get(power).getHeight(), images.get(power).getWidth());
        g2.drawImage(images.get(power), this.x, this.y, null);

    }
}