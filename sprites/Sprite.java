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

  // no argument constructor
  // contructor(String filepate) -- constructs with this one image
  // constructor(int x, int y) -- sets with x and y, maybe one with speed

  protected Sprite() {}

  public Sprite(int x, int y, int speed, ArrayList<BufferedImage> images) {}
  
  public Sprite(int x, int y, int speed, ArrayList<BufferedImage> images, KeyHandler keyH) {}

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
}
