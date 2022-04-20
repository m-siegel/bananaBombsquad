package sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Rectangle;

import main.KeyHandler;
import utilities.HitBox;

public class Sprite {

  // coordinates on the screen
  protected int x;
  protected int y;

  protected int speed;

  protected boolean solid; // Whether the Sprite can collide;

  protected ArrayList<BufferedImage> images; // Store one or more frames for animation.

  protected KeyHandler keyHandler; // So Sprite can update in response to keyboard input

  /**
   * Default constructor.
   */
  protected Sprite() {}

  /**
   * Creates a new Sprite at the given x and y coordinates, with the given speed,
   * ArrayList of images, and KeyHandler.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param speed Sprite's speed.
   * @param images Images for this sprite.
   */
  public Sprite(int x, int y, int speed, ArrayList<BufferedImage> images, KeyHandler keyH) {
    this.x = x;
    this.y = y;
    this.speed = speed;
    if (images == null || images.size() < 1) {
      this.images = 
    }
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, with the given speed,
   * ArrayList of images.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param speed Sprite's speed.
   * @param images Images for this sprite.
   */
  public Sprite(int x, int y, int speed, ArrayList<BufferedImage> images) {}

   /**
   * Creates a new Sprite at the given x and y coordinates,
   * ArrayList of images, and KeyHandler.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param images Images for this sprite.
   */
  public Sprite(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
  }


  public Sprite(int x, int y, int speed, BufferedImage image) {}

  public Sprite(int x, int y, int speed, BufferedImage image, KeyHandler keyH) {}

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getSpeed() {
    return this.speed;
  }

  /**
   * Returns whether or not this Sprite is solid (whether it can collide with another Sprite).
   * 
   * @return
   */
  public boolean isSolid() {
    return this.solid;
  }

  /**
   * Decendent classes should override this to implement movement or animation.
   */
  public void update() {}

  /**
   * Draw this Sprite to the screen as a static image.
   * This Sprite's x and y coordinates represent the top left pixel where this image is drawn.
   *
   * @param g2
   */
  public void draw(Graphics2D g2) {
    g2.drawImage(images.get(0));
  }

  /**
   * Returns the hit box for this sprite's current location if this sprite is solid. Returns null
   * if this Sprite is not solid.
   * 
   * Defaults to the coordinates this Sprite's first image.
   * 
   * @return this Sprite's hit box; null if this Sprite is not solid.
   */
  public HitBox getHitBox();

  /**
   * Returns whether or not this Sprite collides with the other Sprite. Both Sprites must be solid
   * in order to collide.
   * 
   * @param other
   * @return
   */
  public boolean collidesWith(Sprite other) {}


/**
 * Returns a copy of an array list of buffered images.
 * 
 * @param original the ArrayList to copy.
 * @return a deep copy of the parameter ArrayList.
 */
protected static ArrayList<BufferedImage> copyBufferedImages(ArrayList<BufferedImages> original) {
  if (original == null) {
    throw new NullPointerException("Cannot copy a null BufferedImage");
  }
  ArrayList<BufferedImage> copy = new ArrayList<BufferedImage>(original.size());
  for (BUfferedImage bi : original) {
    copy.add(copyBufferedImage(bi));
  }
  return copy;
}

/**
 * Returns a copy of the given BufferedImage.
 * 
 * @param original BufferedImage to copy.
 * @return a copy of the parameter BufferedImage.
 */
protected static BufferedImage copyBufferedImage(BufferedImage original) {
  if (original == null) {
    throw new NullPointerException("Cannot copy a null BufferedImage");
  }
  BufferedImage copy =
      new BufferedImage(original.getWidth(), original.getWidth(), original.getType());
  AffineTransform trans = AffineTransform.getScaleInstance(1, 1);
  AffineTransformOp transOp =
      new AffineTransformOp(trans, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
  transOp.filter(original, copy);
  return copy;
}



}


