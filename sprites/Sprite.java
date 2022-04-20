package sprites;
class Sprite {

  // no argument constructor
  // contructor(String filepate) -- constructs with this one image
  // constructor(int x, int y) -- sets with x and y, maybe one with speed

  public int getX();
  public int getY();
  public int getSpeed();

  public boolean isActive(); // to determine if the Sprite should be removed (eg is off the screen or is killed)
  
  // Idk if we want public setters
  public boolean setX();
  public boolean setY();
  public boolean setSpeed();

  public void update(); // we might want this to be a boolean
  // maybe updateLocation() and updateImage() (for animating image)

  public void draw(Graphics2d g2);

  public HitBox hitBox(); // returns the hit box (like the rectangle in the video) for collisions
  public boolean collidesWith(Sprite other); // returns true if this Sprite collides with the other
}
