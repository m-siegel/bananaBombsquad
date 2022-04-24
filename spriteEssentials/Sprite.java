package spriteEssentials;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import main.KeyHandler;

public class Sprite {

  // coordinates on the screen
  protected int x;
  protected int y;

  protected int speed;

  protected boolean solid; // Whether the Sprite can collide;

  protected ArrayList<BufferedImage> images; // Store one or more frames for animation.
  protected int imagesIndex; // Index in images of the current image to display for this Sprite.

  protected KeyHandler keyHandler; // So Sprite can update in response to keyboard input

  /**
   * Default constructor creates Sprite at (0, 0) with speed 0, not solid, one 1x1
   * image and a null keyHandler. This is so descendants can create any constructor they'd like.
   */
  protected Sprite() {
    x = 0;
    y = 0;
    speed = 0;
    solid = false;
    images = new ArrayList<BufferedImage>();
    images.add(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
    imagesIndex = 0;
    keyHandler = null;
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, with the given speed, ArrayList of
   * images, and KeyHandler. Sets imagesIndex to 0. If the given images ArrayList is null or empty,
   * creates a new ArrayList with an invisible image.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param speed Sprite's speed.
   * @param images Images for this sprite.
   * @param keyH KeyHandler to control this Sprite.
   */
  public Sprite(int x, int y, int speed, ArrayList<BufferedImage> images, KeyHandler keyH) {
    this.x = x;
    this.y = y;
    this.speed = speed;
    if (images == null || images.size() < 1) {
      this.images = new ArrayList<BufferedImage>();
      this.images.add(new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB));
    } else {
      this.images = copyBufferedImages(images);
    }
    this.keyHandler = keyH;
    imagesIndex = 0;
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, with the given speed, ArrayList of
   * images.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param speed Sprite's speed.
   * @param images Images for this sprite.
   */
  public Sprite(int x, int y, int speed, ArrayList<BufferedImage> images) {
    this(x, y, speed, images, null);
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, ArrayList of images, and KeyHandler.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param images Images for this sprite.
   * @param keyH KeyHandler to control this Sprite.
   */
  public Sprite(int x, int y, ArrayList<BufferedImage> images, KeyHandler keyH) {
    this(x, y, 0, images, keyH);
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, and a BufferedImage images.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param image BufferedImage for this sprite.
   */
  public Sprite(int x, int y, ArrayList<BufferedImage> images) {
    this(x, y, 0, images, null);
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, with the given speed, image, and
   * KeyHandler. Adds the given image to the images ArrayList. If the given image is null,
   * creates a 0-dimensional BufferedImage to add to the images ArrayList.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param speed Sprite's speed.
   * @param image BufferedImage for this sprite.
   * @param keyH KeyHandler to control this Sprite.
   */
  public Sprite(int x, int y, int speed, BufferedImage image, KeyHandler keyH) {
    this.x = x;
    this.y = y;
    this.speed = speed;
    if (image == null) {
      image = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
    }
    this.images = new ArrayList<BufferedImage>();
    this.images.add(image);
    this.keyHandler = keyH;
    imagesIndex = 0;
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, with the given speed, and image. Adds
   * the given image to the images ArrayList. Sets keyHandler to null.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param speed Sprite's speed.
   * @param image BufferedImage for this sprite.
   */
  public Sprite(int x, int y, int speed, BufferedImage image) {
    this(x, y, speed, image, null);
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, and image. Adds the given image to the
   * images ArrayList. Sets speed to 0.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param image BufferedImage for this sprite.
   * @param keyH KeyHandler to control this Sprite.
   */
  public Sprite(int x, int y, BufferedImage image, KeyHandler keyH) {
    this(x, y, 0, image, keyH);
  }

  /**
   * Creates a new Sprite at the given x and y coordinates, and BufferedImage. Adds the given image
   * to the images ArrayList. Sets speed to 0 and keyHandler to null.
   * 
   * @param x Sprite's x coordinate.
   * @param y Sprite's y coordinate.
   * @param image BufferedImage for this sprite.
   */
  public Sprite(int x, int y, BufferedImage image) {
    this(x, y, 0, image, null);
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getSpeed() {
    return this.speed;
  }

  public int getWidth() {
    return this.images.get(imagesIndex).getWidth();
  }

  public int getHeight() {
    return this.images.get(imagesIndex).getHeight();
  }

  /**
   * Returns whether or not this Sprite has a solid region.
   * 
   * @return
   */
  public boolean isSolid() {
    return this.solid;
  }

  // TODO -- make abstract?
  /**
   * Decendent classes should override this to implement movement or animation.
   */
  public void update() {}

  /**
   * Draw this Sprite to the screen as a static image. This Sprite's x and y coordinates represent
   * the top left pixel where this image is drawn.
   *
   * @param g2 // TODO -- fill in
   */
  public void draw(Graphics2D g2) {
    g2.drawImage(images.get(imagesIndex), x, y, null);
  }

  /**
   * Returns the hit box for this sprite's current location if this sprite is solid. Returns null if
   * this Sprite is not solid.
   * 
   * Defaults to the dimension coordinates of this Sprite's first image.
   * 
   * @return this Sprite's hit box; null if this Sprite is not solid.
   */
  public HitBox getHitBox() {
    if (!this.solid) { // TODO -- document for descendents
      return null;
    }
    return new HitBox(this.x, this.x + this.images.get(imagesIndex).getWidth(), this.y,
        this.y + this.images.get(imagesIndex).getHeight()); // TODO -- document: should be relative to curr location
  }

  /**
   * Returns whether or not this Sprite collides with the other Sprite. Both Sprites must be solid
   * in order to collide.
   * 
   * @param other
   * @return
   */
  public boolean collidesWith(Sprite other) {

    //System.out.printf("Checking collision %s-%s\n", this.getClass().toString(), other.getClass().toString());

    if (other == null) {
      return false;
    }
    HitBox thisHitBox = this.getHitBox();
    HitBox otherHitBox = other.getHitBox();
    //System.out.printf("This hitbox: %d, %d, %d, %d\n", thisHitBox.getXMin(), 
        //thisHitBox.getXMax(), thisHitBox.getYMin(), thisHitBox.getYMax());
    //System.out.printf("Other hitbox: %d, %d, %d, %d\n", otherHitBox.getXMin(), 
        //otherHitBox.getXMax(), otherHitBox.getYMin(), otherHitBox.getYMax());
    if (thisHitBox == null || otherHitBox == null) {
      return false;
    }
    if (thisHitBox.getXMin() > otherHitBox.getXMax()
        || thisHitBox.getXMax() < otherHitBox.getXMin()) {
      return false;
    }
    if (thisHitBox.getYMin() > otherHitBox.getYMax()
        || thisHitBox.getYMax() < otherHitBox.getYMin()) {
      return false;
    }
    return true;
  }

  /**
   * Returns a copy of an array list of buffered images.
   * 
   * @param original the ArrayList to copy.
   * @return a deep copy of the parameter ArrayList.
   */
  protected static ArrayList<BufferedImage> copyBufferedImages(ArrayList<BufferedImage> original) {
    if (original == null) {
      throw new NullPointerException("Cannot copy a null BufferedImage");
    }
    ArrayList<BufferedImage> copy = new ArrayList<BufferedImage>(original.size());
    for (BufferedImage bi : original) {
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
        new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
    AffineTransform trans = AffineTransform.getScaleInstance(1, 1);
    AffineTransformOp transOp =
        new AffineTransformOp(trans, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    transOp.filter(original, copy);
    return copy;
  }
}
