package gameSprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import spriteEssentials.*;
import main.*;

/**
 * Represents a cannon in the game. A cannon consists of a barrel and a wheel.
 * A cannon can rotate up to 90 degrees based on keyboard input,
 * but cannot move forward, backward, up or down. 
 */
public class Cannon extends Sprite {

  // TODO -- not usng images or imagesIndex. are other sprites using this?

  // Angle in degrees -- TODO -- could be ints
  public static final double MAX_CARTESIAN_ANGLE = 90;
  public static final double MIN_CARTESIAN_ANGLE = 0;
  // Image starts facing north, so rotation angle = cartesianAngle - ANGLE_OFFSET
  public static final double ANGLE_OFFSET = 90;
  private double cartesianAngle; // East is 0, North is 90

  private BufferedImage wheel;
  private BufferedImage barrel; // Original barrel image.
  private BufferedImage barrelAnimationFrame; // Rotated image for current animation frame.

  // Pivot points relative to upper left corner of the image
  // private int wheelPivotX;
  // private int wheelPivotY;
  private int barrelPivotX;
  private int barrelPivotY;
  // Offset of wheel's top left from barrel's
  private int wheelOffsetX;
  private int wheelOffsetY;

  /**
   * Creates a stationary cannon object with the given wheel and barrel images and access
   * to the given KeyHandler. The cannon's barrel can rotate 90 degrees, but its pivot
   * point cannon move in any direction.
   * 
   * <p>The cannon image should be facing northward. The cannon's pivot point is calculated
   * to be on the vertical centerline, 1/2 the image's width from the bottom of the image.
   * 
   * <p>The wheel image should have equal width and height,
   * and be at least as wide as the barrel's width.
   * 
   * @param wheel the image to draw for the wheel at the base of the cannon.
   * @param barrel the barrel of the cannon to draw and rotate.
   * @param keyHandler the keyHandler for the relevant JPanel to control cannon rotation.
   * @throws NullPointerException if any of the parameters are null.
   * @throws IllegalArgumentException if the wheel image is not square, or the wheel's width is
   *         less than the cannon's width.
   */
  public Cannon(BufferedImage wheel, BufferedImage barrel, KeyHandler keyHandler) {
    if (keyHandler == null) {
      throw new NullPointerException("keyHandler parameter cannot be null.");
    }
    if (wheel == null) {
      throw new NullPointerException("wheel parameter cannot be null.");
    }
    if (barrel == null) {
      throw new NullPointerException("barrel parameter cannot be null.");
    }
    if (wheel.getWidth() != wheel.getHeight()) {
      throw new IllegalArgumentException("wheel image must have equal height and width");
    }
    if (wheel.getWidth() < Math.min(barrel.getWidth(), barrel.getHeight())) {
      throw new IllegalArgumentException("The wheel must be at least as wide as the barrel.");
    }

    // TODO -- decide where we want cannon placed. Currently 50px buffer from edge of screen
    this.x = 50;
    this.y = GamePanel.SCREEN_HEIGHT - (Math.max(barrel.getWidth(), barrel.getHeight()) + 50);
    this.speed = 1; // 1 deg per update (== 60 deg per sec)
    this.solid = true;
    this.keyHandler = keyHandler;

    // Barrel image should start facing North
    this.cartesianAngle = 90;

    this.barrel = barrel;
    this.wheel = wheel;
    int maxDim = Math.max(this.barrel.getWidth(), this.barrel.getHeight());
    this.barrelAnimationFrame = new BufferedImage(maxDim, maxDim, this.barrel.getType());
  
    // Calculate wheel offset and pivot points for wheel and barrel
    wheelOffsetY = this.barrel.getHeight() - this.wheel.getHeight();
    wheelOffsetX = (this.barrel.getWidth() - this.wheel.getWidth()) / 2;
    barrelPivotX = this.barrel.getWidth() / 2;
    barrelPivotY = (this.barrel.getWidth() / 2) - 1; // Extra pixel for border buffer
    // wheelPivotX = this.wheelOffsetX + (this.wheel.getWidth() / 2);
    // wheelPivotY = this.wheelOffsetY + (this.wheel.getHeight() / 2);

    matchBarrelFrameToAngle(); // Must happen after barrel pivot point is calculated
  }

  /**
   * Returns the x-coordinate of the center of the barrel's belly.
   * 
   * @return the x-coordinate of the center of the barrel's belly.
   */
  public int getLaunchX() {
    // TODO -- trig with barrel angle and length from pivot point to almost end unless we start
    // shooting from pivot point
    return x + barrelPivotX; // Shooting from belly.
  }

  /**
   * Returns the y-coordinate of the center of the barrel's belly.
   * 
   * @return the y-coordinate of the center of the barrel's belly.
   */
  public int getLaunchY() {
    // TODO -- trig with barrel angle and length from pivot point to almost end unless we start
    // shooting from pivot point
    return y + barrelPivotY; // Shooting from belly.
  }

  /**
   * Returns the angle in degrees between the bottom of the screen
   * and the center line of the barrel. East is 0 and North is 90.
   * 
   * @return the angle that the barrel is pointing.
   */
  public double getAngle() { // getLaunchAngle()?
    return cartesianAngle;
  }

  /**
   * Updates the rotation of the barrel and the corresponding barrelAnimationFrame.
   */
  @Override
  public void update() {
    // If position were to be updated, then barrelPivots would need to be updated, too.
    updateAngle();
    matchBarrelFrameToAngle();
  }

  /**
   * Updates cartesianAngle based on key presses.
   */
  private void updateAngle() {
    if (keyHandler.getAngleClockwisePressed()) {
      cartesianAngle = Math.max(cartesianAngle - speed, MIN_CARTESIAN_ANGLE);
    }
    if (keyHandler.getAngleCounterClockwisePressed()) {
      cartesianAngle = Math.min(cartesianAngle + speed, MAX_CARTESIAN_ANGLE);
    }
  }

  /**
   * Copies a rotated version of the barrel into barrelAnimationFrame to match current angle.
   */
  private void matchBarrelFrameToAngle() {
    AffineTransform rotation = AffineTransform.getRotateInstance(
        Math.toRadians(cartesianAngle - ANGLE_OFFSET), barrelPivotX, barrelPivotY);
    AffineTransformOp rotationOp =
        new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
    rotationOp.filter(barrel, barrelAnimationFrame);
  }

  /**
   * Draws the barrel and wheel, with the wheel in front of the barrel.
   */
  @Override
  public void draw(Graphics2D g2) {
    // Draw the wheel in front of the barrel
    g2.drawImage(barrelAnimationFrame, x, y, null); // must have correct (x, y) when copied
    g2.drawImage(wheel, x + wheelOffsetX, y + wheelOffsetY, null);
  }
}
