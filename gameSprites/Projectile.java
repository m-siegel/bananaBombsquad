package gameSprites;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

import spriteEssentials.*;

public class Projectile extends Sprite {

    // instance variables
    public static final int FPS = 30;
    public static final double GRAVITY = 9.8;

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

    public Projectile(int x, int y, double angle, int velocity,
            ArrayList<BufferedImage> flyingImages, ArrayList<BufferedImage> splatteredImages,
            int updatesPerSec) {
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
        if (angle == 90) {
            angle = 89; // tan(90) is undefined, so quick fix
        }
        this.angle = angle;
        this.velocity = velocity * 5; // divide by 100 to slow it down a bit

        // used to calculate flight path of projectile
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

    public void splat() { // case when image is splattered on the ground
        this.splat(0);
    }

    /**
     * 
     * @param rotation
     */
    public void splat(int rotation) {
        if (rotation != 0) {
            ArrayList<BufferedImage> tempArrayList =
                    new ArrayList<BufferedImage>(splatteredImages.size());

            for (BufferedImage src : splatteredImages) {
                int maxDim = Math.max(src.getWidth(), src.getHeight());
                BufferedImage tempImage = new BufferedImage(maxDim, maxDim, src.getType());
                int pivotX = src.getWidth() / 2;
                int pivotY = src.getHeight() / 2;
                AffineTransform transform = AffineTransform.getRotateInstance(
                        Math.toRadians(-rotation), pivotX, pivotY);
                AffineTransformOp rotateOp =
                        new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
                rotateOp.filter(src, tempImage);
                tempArrayList.add(tempImage);
            }
            splatteredImages = tempArrayList;
            this.x += splatteredImages.get(0).getWidth() / 2; // splat onto (not in front)
        }
        this.splattered = true;
        this.imagesIndex = 0;
        this.images = splatteredImages;
    }

    public boolean isSplattered() {
        return this.splattered;
    }

    public double getAngle() {
        return this.angle;
    }

    public int getVelocity() {
        return this.velocity;
    }

    public int getInternalX() {
        return this.internalX;
    }

    public int getInternalY() {
        return this.internalY;
    }

    public int getXDisplacement() {
        return this.xDisplacement;
    }

    public int getYDisplacement() {
        return this.yDisplacement;
    }

    /**
     * Implements animation by updating projectile's position and image.
     */
    public void update() {
        if (!this.isSplattered()) {
            updatePosition();
        }
        animateImage();
    }

    /**
     * Updates the position of projectile by changing its x and y coordinates.
     * Uses mathematical formula to calculate projectile's trajectory.
     */
    private void updatePosition() {
        // Find projectile position relative to start at cartesian (0, 0) internally (draw arc)
        this.internalX += this.speed; // update x over time
        
        // formula in deg: y = h + (x)tan(A) - ( 9.8 * ( x^2 / (2v^2 * cos(A)* cos(A)) )
        // h = 0
        this.internalY = (int) ((internalX * Math.tan(Math.toRadians(angle))) 
                - (GRAVITY * Math.pow(internalX, 2)
                / (2 * Math.pow(velocity, 2) * Math.pow(Math.cos(Math.toRadians(angle)), 2))));

        // Find actual position
        this.x = internalX + xDisplacement;
        this.y = yDisplacement - internalY;
    }

    /**
     * Updates current imagesIndex to facilitate animation.
     */
    private void animateImage() {
        updatesSinceFrameChange++;
        if (updatesSinceFrameChange >= updatesPerFrame) {
            // update frame
            this.imagesIndex = (imagesIndex + 1) % images.size(); // cycle through images
            updatesSinceFrameChange = 0;
        }
    }

    @Override
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
        return new HitBox(x - (int) (width * .1), x + (int) (width * 1.1), y - (int) (height * .1),
                y + (int) (height * 1.1));
    }
}
