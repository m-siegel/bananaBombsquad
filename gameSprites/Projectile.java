package gameSprites;

import java.util.ArrayList;

import spriteEssentials.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Projectile extends Sprite {

    public Projectile(int x, int y, double angle, int velocity, ArrayList<BufferedImage> flyingImages,
            ArrayList<BufferedImage> splatteredImages, int updatesPerSec) {
        if (flyingImages == null) {
            throw new NullPointerException("Cannot instantiate with null flyingImages.");
        }
        if (flyingImages.size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot instantiate with an empty flyingImages ArrayList");
        }
        if (splatteredImages == null) {
            throw new NullPointerException("Cannot instantiate with null splatteredImage.");
        }
        if (splatteredImages.size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot instantiate with an empty splatteredImages ArrayList");
        }
        this.flyingImages = Sprite.copyBufferedImages(flyingImages);
        this.splatteredImages = Sprite.copyBufferedImages(splatteredImages);
        this.splattered = false;
        this.images = this.flyingImages;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity / 100; // divide by 100 to slow it down a bit

        // TODO -- comment why this is
        this.internalX = 0;
        this.internalY = 0; 
        this.xDisplacement = this.x - this.internalX;
        this.yDisplacement = this.y - this.internalY;

        this.solid = true;
        this.keyHandler = null;
        this.speed = 3;
        this.imagesIndex = 0;
        this.updatesSinceFrameChange = 0;
        this.updatesPerFrame = FPS / updatesPerSec;
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
    private ArrayList<BufferedImage> splatteredImages;
    private ArrayList<BufferedImage> flyingImages;

    private int updatesSinceFrameChange;
    private int updatesPerFrame;

    //getters
    public boolean isSplattered() {
        return this.splattered;
    }
    public void splat() {
        this.splattered = true;
        this.imagesIndex = 0;
        this.images = splatteredImages;
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
        }
        animateImage();
    }

    private void updatePosition() {
        // Find projectile position relative to start at (0, 0) internally (draw arc)
        this.internalX += this.speed; // update x over time
        // find y in terms of x
        // don't bother with height because coerced starting point to be (0, 0), height == 0
        // 9.8 for gravity -- TODO -- static var

        //this.internalY -= this.speed;

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

    private void animateImage() {
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
