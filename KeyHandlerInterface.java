interface KeyHandlerInterface {

  /** Returns value of each instance variable indicating whether
    * the keys for each action are pressed.
    */
  public boolean angleUp(); // or angleLeft() or angleCounterClockwise()

  public boolean angleDown();

  public boolean powerUp();

  public boolean powerDown();
  
}
