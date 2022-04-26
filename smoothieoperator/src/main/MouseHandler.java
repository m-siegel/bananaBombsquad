package smoothieoperator.src.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Processes mouse clicks for use by JComponents.
 */
public class MouseHandler extends MouseAdapter {
  private boolean buttonPressed;

  /**
   * Creates a new MouseHandler with the buttonPressed variable set to false.
   */
  public MouseHandler() {
    this.buttonPressed = false;
  }

  /**
   * Sets buttonPressed to true when the mouse is clicked.
   */
  public void mouseClicked(MouseEvent e) {
    this.buttonPressed = true;
  }

  public boolean getButtonPressed() {
    return buttonPressed;
  }
}
