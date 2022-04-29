package smoothieoperator.src.gameSprites;

import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

import smoothieoperator.src.spriteEssentials.*;

/**
 * Represents a projectile onscreen and in the game logic.
 * Given an initial location, angle, and velocity, a Projectile moves in accordance with
 * the laws of projectile motion across the screen. Projectiles can rotate as they fly.
 * A projectile can also "splat," displaying its "splatteredImages" and not moving.
 */
public class Projectile extends Sprite {

    // instance variables
    public static final int FPS = 30;
    public static final double GRAVITY = 9.8;

    // for projectile motion
    private int x0; // initial x and y
    private int y0;
    private double time;
    private double angle;
    private int velocity;

    private boolean splattered;
    private ArrayList<BufferedImage> splatteredImages;
    private ArrayList<BufferedImage> flyingImages;
    private int updatesSinceFrameChange;
    private int updatesPerFrame;

    /**
     * Creates a new projectile object at coordinates x and y, with given angle, velocity,
     * flying images, and splattered images.
     * Uses given updates per second to calculate number of updates per frame. Updates per
     * second must be at least one.
     * Sets flyingImages as the default set of images placed into the images ArrayList.
     * Adds the two sounds associated with a Projectile - a "whoosh" and a "splat".
     * 
     * @param x Projectile's x-coordinate
     * @param y Projectile's y-coordinate
     * @param angle Projectile's angle
     * @param velocity Projectile's velocity
     * @param flyingImages Projectile's list of images in its flying state
     * @param splatteredImages Projectile's list of images in its splattered state
     * @param updatesPerSec Projectile's number of updates per second. Must be positive.
     * @throws IllegalArgumentException if either ArrayList is null, contains null elements,
     *         or is empty.
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public Projectile(int x, int y, double angle, int velocity,
            ArrayList<BufferedImage> flyingImages, ArrayList<BufferedImage> splatteredImages,
            int updatesPerSec) {
        if (flyingImages == null) {
            throw new IllegalArgumentException("Cannot instantiate with null flyingImages.");
        }
        if (flyingImages.size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot instantiate with an empty flyingImages ArrayList");
        }
        for (BufferedImage elem : flyingImages) {
            if (elem == null) {
                throw new IllegalArgumentException(
                        "Cannot have a null element in the flyingImages ArrayList");
            }
        }
        if (splatteredImages == null) {
            throw new IllegalArgumentException("Cannot instantiate with null splatteredImage.");
        }
        if (splatteredImages.size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot instantiate with an empty splatteredImages ArrayList");
        }
        for (BufferedImage elem : splatteredImages) {
            if (elem == null) {
                throw new IllegalArgumentException(
                        "Cannot have a null element in the splatteredImages ArrayList");
            }
        }

        if (updatesPerSec <= 0) {
            updatesPerSec = 1;
        }

        this.flyingImages = Sprite.copyBufferedImages(flyingImages);
        this.splatteredImages = Sprite.copyBufferedImages(splatteredImages);
        this.splattered = false;
        this.images = this.flyingImages;
        this.x = x;
        this.y = y;
        this.angle = angle;
        // give projectiles more power, especially relative to low velocities
        this.velocity = (velocity + 2) * 5;

        this.x0 = x;
        this.y0 = y;
        this.time = 0;

        this.solid = true;
        this.keyHandler = null;
        this.speed = 1;
        this.imagesIndex = 0;
        this.updatesSinceFrameChange = 0;
        this.updatesPerFrame = FPS / updatesPerSec;

        try {
            this.addSound("/smoothieoperator/src/media/sounds/splat.wav", "splat");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.addSound("/smoothieoperator/src/media/sounds/whooshFast.wav", "whoosh");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void splat() { // case when image is splattered on the ground
        this.splat(0);
    }

    /**
     * Rotates each of the splattered images to match the parameter rotation angle in degrees.
     * Sets splattered to true and changes the images ArrayList and corresponding imagesIndex
     * to use this proejctile's splattered images instead of flying images.
     * Stops the Projectile's "whoosh" sound and plays its "splat" sound.
     * 
     * @param rotation
     */
    public void splat(int rotation) {
        if (rotation != 0) {
            // Rotate all splattered images
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
        if (this.sounds.get("whoosh") != null) {
            this.sounds.get("whoosh").stopSound();
        }
        if (this.sounds.get("splat") != null) {
            this.sounds.get("splat").playSound();
        }
    }

    /**
     * Returns the value of this splattered variable.
     * 
     * @return the value of this splattered variable.
     */
    public boolean isSplattered() {
        return this.splattered;
    }

    public double getAngle() {
        return this.angle;
    }

    public int getVelocity() {
        return this.velocity;
    }

    public int getInitialX() {
        return this.x0;
    }

    public int getInitialY() {
        return this.y0;
    }

    /**
     * Returns the value of this projectile's time variable. The time variable
     * is a counter that increments with calls to update(), and it is completely
     * different than actual measurements of time.
     * 
     * @return the value of the time variable.
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Implements animation by updating projectile's position and image.
     */
    public void update() {
        if (!this.isSplattered()) {
            updatePosition();
            playWhoosh();
        }
        animateImage();
    }

    /**
     * Updates the position of projectile by changing its x and y coordinates based on laws of
     * projectile motion.
     */
    private void updatePosition() {
        /*
         * On a Cartesian plane,
         * x = x0 + V0x * t + (1/2ax t^2); acceleration is 0, so x = x0 + V0x * t.
         * y = y0 + V0y * t + (1/2ay t^2); acceleration is gravity (-9.8),
         * so y = y0 + V0y * t + (-4.9 t^2).
         * 
         * Since the window's y-axis is the opposite of the Cartesian system,
         * we reflect the _shape_ of the parabola across the x-axis (-f(x)), without changing the
         * value of y0: y = y0 - (V0y * t) + (4.9 t^2).
         * 
         * V0x = cos(theta) and V0y = sin(theta).
         * 
         * Finally,
         * x = x0 + (V * cos(ang) * t)
         * y = y0 - (V * sin(ang) * t) + ((gravity / 2) t^2))
         */
        this.x = (int) (this.x0 + (velocity * Math.cos(Math.toRadians(this.angle)) * this.time));
        this.y = (int) (this.y0 - (velocity * Math.sin(Math.toRadians(this.angle)) * this.time)
                + ((GRAVITY / 2) * this.time * this.time));
        this.time += .1; // this method is called so quickly that we slow motion here.
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

    /**
     * Plays whoosh sound in time with image cycle.
     */
    private void playWhoosh() {
        // play in time with image revolution
        if (updatesSinceFrameChange == 0) {
            if (imagesIndex == 0) {
                if (this.sounds.get("whoosh") != null) {
                    this.sounds.get("whoosh").reset();
                    this.sounds.get("whoosh").playSound();
                }
            }
        }
    }

    /**
     * Draws the image at the current imagesIndex in the images ArrayList at the current (x, y)
     * location onscreen.
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(imagesIndex), this.x, this.y, null);
    }

    /**
     * Returns a hitbox 1/2 of the width of the current image and 1/2 of the
     * height of the current image.
     */
    @Override
    public HitBox getHitBox() {
        if (!isSolid()) {
            return null;
        }
        int width = images.get(imagesIndex).getWidth();
        int height = images.get(imagesIndex).getHeight();
        return new HitBox(this.x + (int) (width * .25), this.x + (int) (width * .75),
                y + (int) (height * .25), y + (int) (height * .75));
    }
}
