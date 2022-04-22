package gameSprites;

import java.util.ArrayList;
import spriteEssentials.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Projectile extends Sprite {

    public Projectile(int x, int y, double angle, int velocity, ArrayList<BufferedImage> images, int updatesPerSec) {
        if (images == null) {
            throw new NullPointerException("Cannot instantiate null images.");
        }
        if (images.size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot instantiate with an empty images ArrayList");
        }
        this.images = Sprite.copyBufferedImages(images);
        this.splattered = false;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;

        // TODO -- comment why this is
        this.internalX = 0;
        this.internalY = 0; 
        this.xDisplacement = this.x - this.internalX;
        this.yDisplacement = this.y - this.internalY;

        this.solid = true;
        this.keyHandler = null;
        this.speed = 5;
        this.imagesIndex = 0;
        this.updatesSinceFrameChange = 0;
        this.updatesPerFrame = updatesPerSec / FPS;
    }
    //instance variables
    public static final int FPS = 30;

    private boolean splattered;
    private double angle;
    private int velocity;
    private int internalX;
    private int internalY;
    private int xDisplacement;
    private int yDisplacement;
    private BufferedImage splatteredImage;

    private int updatesSinceFrameChange;
    private int updatesPerFrame;

    //getters
    public boolean isSplattered() {
        return this.splattered;
    }
    public void splat() {
        this.splattered = true;
    }

    public double getAngle() {return this.angle;}
    public int getVelocity() {return this.velocity;}
    public int getInternalX() {return this.internalX;}
    public int getInternalY() {return this.internalY;}
    public int getXDisplacement() {return this.xDisplacement;}
    public int getYDisplacement() {return this.yDisplacement;}

    public void update() {
        if (! this.isSplattered()) {
            updatePosition();
            rotateImage();
        }
    }

    private void updatePosition() {
        // Find projectile position relative to start at (0, 0) internally (draw arc)
        this.internalX += this.speed; // update x over time
        // find y in terms of x
        // don't bother with height because coerced starting point to be (0, 0), height == 0
        // 9.8 for gravity -- TODO -- static var
        this.internalY = (int) ((internalX * Math.tan(Math.toRadians(angle))) - (9.8 * Math.pow(internalX, 2) /
                (2 * Math.pow(velocity, 2) * Math.pow(Math.cos(Math.toRadians(angle)), 2))));

        // this.internalY = (int) Math.round( Math.tan(angle * (Math.PI / 180) ) - (9.8 * (internalX * internalX / 
        //     (2 * velocity * velocity * Math.cos(angle * (Math.PI / 180) ) * Math.cos(angle * (Math.PI / 180) ))) ));

        //formula in deg: y = h + (x)tan(A) - ( 9.8 * ( x^2 / (2v^2 * cos(A)* cos(A)) )
        //h = 0

        // Find actual position
        this.x = internalX + xDisplacement;
        this.y = internalY + yDisplacement;
    }

    private void rotateImage() {
        updatesSinceFrameChange++;
        if (updatesSinceFrameChange >= updatesPerFrame) {
            // update frame
            this.imagesIndex = (imagesIndex + 1) % images.size(); // cycle through images
            updatesSinceFrameChange = 0;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(imagesIndex), this.x, this.y, null);
    }

    public void drawSplat(Graphics2D g2) {
        g2.drawImage(splatteredImage, this.x, this.y, null);
    }

    @Override
    public HitBox getHitBox() {
        if (!isSolid()) {
            return null;
        }
        int width = images.get(imagesIndex).getWidth();
        int height = images.get(imagesIndex).getHeight();
        // TODO -- make scale factor
        return new HitBox(x - (int) (width * .1), x + (int) (width * 1.1),
                y - (int) (height * .1), y + (int) (height * 1.1));
    }
}
