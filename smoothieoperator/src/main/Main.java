package smoothieoperator.src.main;

import javax.swing.JFrame;

public class Main {

    /**
     * Sets up a new JFrame and loads the GamePanel onto it.
     * Sets the JFrame's default close operation to exit.
     * Sets the JFrame's title to the name of the game.
     * Disables resizing the JFrame window.
     * Sizes the JFrame's window to the dimensions of the GamePanel.
     * Sets the JFrame window to be visible.
     * Starts the game.
     */
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Smoothie Operator");
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //window is sized to fit the preferred size and layouts of the gamepanel

        window.setLocationRelativeTo(null); //sets window to appear at center of screen
        window.setVisible(true); //allows us to see the window

        gamePanel.startGame();
    }
}
