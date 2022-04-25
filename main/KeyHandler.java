package main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Processes keyboard input into boolean instance variables for use by JComponents.
 */
public class KeyHandler implements KeyListener {

  private boolean angleCounterClockwisePressed; // left arrow key, or A key

  private boolean angleClockwisePressed; // right arrow key, or D key

  private boolean powerUpPressed; // up arrow key, or W key

  private boolean powerDownPressed; // down arrow key, or S key

  private boolean shootButtonPressed;

  // getters
  public boolean getAngleCounterClockwisePressed() {
    return this.angleCounterClockwisePressed;
  }

  public boolean getAngleClockwisePressed() {
    return this.angleClockwisePressed;
  }

  public boolean getPowerUpPressed() {
    return this.powerUpPressed;
  }

  public boolean getPowerDownPressed() {
    return this.powerDownPressed;
  }

  public boolean getShootButtonPressed() {
    return this.shootButtonPressed;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      angleCounterClockwisePressed = true;
    }
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      angleClockwisePressed = true;
    }
    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
      powerUpPressed = true;
    }
    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
      powerDownPressed = true;
    }
    if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
      shootButtonPressed = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      angleCounterClockwisePressed = false;
    }
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      angleClockwisePressed = false;
    }
    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
      powerUpPressed = false;
    }
    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
      powerDownPressed = false;
    }
    if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
      shootButtonPressed = false;
    }
  }
}
