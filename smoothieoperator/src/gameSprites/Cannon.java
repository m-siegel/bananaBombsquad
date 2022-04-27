package smoothieoperator.src.gameSprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import smoothieoperator.src.spriteEssentials.*;
import smoothieoperator.src.main.*;

/**
 * Represents a cannon in the game. A cannon consists of a barrel and a wheel.
 * A cannon can rotate up to 90 degrees based on keyboard input,
 * but cannot move forward, backward, up or down. 
 */
public class Cannon extends Sprite {

  // Angle in degrees
  public static final double MAX_CARTESIAN_ANGLE = 90;
  public static final double MIN_CARTESIAN_ANGLE = 0;
  // Image starts facing North, so the angle to rotate the image = cartesianAngle - ANGLE_OFFSET
  public static final double ANGLE_OFFSET = 90;
  private double cartesianAngle; // East is 0, North is 90

  private BufferedImage wheel;
  private BufferedImage barrel; // Original barrel image
  private BufferedImage barrelAnimationFrame; // Rotated image for current animation frame

  // Pivot points relative to upper left corner of the image
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
   * The cannon image should be facing northward. The cannon's pivot point is calculated
   * to be on the vertical center line, 1/2 the image's width from the bottom of the image.
   * 
   * The wheel image should have equal width and height,
   * and be at least as wide as the barrel's width.
   * 
   * @param wheel the image to draw for the wheel at the base of the cannon
   * @param barrel the barrel of the cannon to draw and rotate
   * @param keyHandler the keyHandler for the relevant JPanel to control cannon rotation
   * @throws IllegalArgumentException if the wheel image is not square, or the wheel's width is
   *         less than the cannon's width, or if any of the reference type parameters are null.
   */
  public Cannon(int x, int y, BufferedImage barrel, BufferedImage wheel,
          KeyHandler keyHandler, String soundFile, String soundName) {
    if (keyHandler == null) {
      throw new IllegalArgumentException("keyHandler parameter cannot be null.");
    }
    if (wheel == null) {
      throw new IllegalArgumentException("wheel parameter cannot be null.");
    }
    if (barrel == null) {
      throw new IllegalArgumentException("barrel parameter cannot be null.");
    }
    if (soundFile == null) {
      throw new IllegalArgumentException("soundFile parameter cannot be null");
    }
    if (soundName == null) {
      throw new IllegalArgumentException("soundName parameter cannot be null");
    }
    if (wheel.getWidth() != wheel.getHeight()) {
      throw new IllegalArgumentException("wheel image must have equal height and width");
    }
    if (wheel.getWidth() < Math.min(barrel.getWidth(), barrel.getHeight())) {
      throw new IllegalArgumentException("The wheel must be at least as wide as the barrel.");
    }

    this.x = x;
    this.y = y;
    this.speed = 1; // number of degrees per call to update()
    this.solid = false;
    this.keyHandler = keyHandler;
    try {
      this.sounds.put(soundName, new Sound(soundFile));
    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
      System.out.println("Error retrieving sound file");
      e.printStackTrace();
    }

    // barrel image should start facing North
    this.cartesianAngle = 90;

    this.barrel = barrel;
    this.wheel = wheel;
    int maxDim = Math.max(this.barrel.getWidth(), this.barrel.getHeight());
    // Make large enough to not cut off any of the rotated image
    this.barrelAnimationFrame = new BufferedImage(maxDim, maxDim, this.barrel.getType());
  
    // calculate wheel offset and pivot points for wheel and barrel
    wheelOffsetY = this.barrel.getHeight() - this.wheel.getHeight();
    wheelOffsetX = (this.barrel.getWidth() - this.wheel.getWidth()) / 2;
    barrelPivotX = this.barrel.getWidth() / 2;
    // extra pixel for border buffer
    barrelPivotY = this.barrel.getHeight() - (this.barrel.getWidth() / 2) - 1;

    matchBarrelFrameToAngle(); // Must happen after barrel pivot point is calculated
  }

  /**
   * Returns an x-coordinate near the tip of the barrel's spout. The coordinate is
   * offset a little to the left of center as the barrel faces Northward.
   * 
   * @return an x-coordinate near the tip of the barrel's spout.
   */
  public int getLaunchX() {
    int bellyX = x + barrelPivotX;
    int spoutX = (int) ((barrelPivotY - GamePanel.TILE_SIZE) * Math.cos(
        Math.toRadians(cartesianAngle)));
    // Since (x, y) coords for projectile are in the upper left, 
    // shift this left a bit when facing North.
    int offsetX = (int) (((GamePanel.TILE_SIZE / 2) * GamePanel.SCALE)
        * Math.sin(Math.toRadians(cartesianAngle)));
    return bellyX + spoutX - offsetX;
  }

  /**
   * Returns a y-coordinate near the tip of the barrel's spout. The coordinate is
   * offest a bit North of center as the barrel faces Eastward.
   * 
   * @return a y-coordinate near the tip of the barrel's spout.
   */
  public int getLaunchY() {
    int bellyY = y + barrelPivotY;
    int spoutY = (int) ((barrelPivotY) * Math.sin(Math.toRadians(cartesianAngle)));
    // Since (x, y) coords for projectile are in the upper left, 
    // shift this up a bit when facing East.
    int offsetY = (int) (((GamePanel.TILE_SIZE / 2) * GamePanel.SCALE)
        * Math.cos(Math.toRadians(cartesianAngle)));
    return bellyY - spoutY - offsetY;
  }

  /**
   * Returns the angle in degrees between the bottom of the screen
   * and the center line of the barrel. East is 0 and North is 90.
   * 
   * @return the angle that the barrel is pointing.
   */
  public double getAngle() {
    return cartesianAngle;
  }

  /**
   * Updates the rotation of the barrel and the corresponding barrelAnimationFrame.
   */
  @Override
  public void update() {
    // if position is updated, then barrelPivots also needs to be updated
    updateAngle();
    matchBarrelFrameToAngle();
    if (this.getSound("boom") != null) {
      this.getSound("boom").reset();
    }
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
    // AffineTransform rotates clockwise
    AffineTransform rotation = AffineTransform.getRotateInstance(
        Math.toRadians(-(cartesianAngle - ANGLE_OFFSET)), barrelPivotX, barrelPivotY);
    AffineTransformOp rotationOp =
        new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
    rotationOp.filter(barrel, barrelAnimationFrame);
  }

  /**
   * Draws the cannon, with the wheel in front of the barrel.
   */
  @Override
  public void draw(Graphics2D g2) {
    // Draw the wheel in front of the barrel
    g2.drawImage(barrelAnimationFrame, x, y, null); // must have correct (x, y) when copied
    g2.drawImage(wheel, x + wheelOffsetX, y + wheelOffsetY, null);
  }
}
