package utilities;

/**
 * Represents a Sprite's hitbox with the min and max x and y boundaries of the Sprite's solid area.
 */
public class HitBox {
  private int xMin;
  private int xMax;
  private int yMin;
  private int yMax;

  /**
   * Creates a new HitBox with the given boundaries.
   * 
   * @param xMin lower x boundary (left of the rectangle).
   * @param xMax upper x boundary (right of the rectangle).
   * @param yMin lower y boundary (bottom of the rectangle).
   * @param yMax upper y boundary (top of the rectangle).
   */
  public HitBox(int xMin, int xMax, int yMin, int yMax) {
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
  }

  public int getXMin() {
    return this.xMin;
  }

  public int getXMax() {
    return this.xMax;
  }

  public int getYMin() {
    return this.yMin;
  }

  public int getYMax() {
    return this.yMax;
  }
}
