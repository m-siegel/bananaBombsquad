package smoothieoperator.src.main;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents a game window with multiple screens. Contains an title screen and
 * a gameplay screen. Switches from the title screen to the gameplay screen.
 */
public class GameWindowManager {
    private JFrame window;
    private CardLayout layout;
    private JPanel deck;
    private TitlePanel titlePanel;
    private GamePanel gamePanel;
    
    /**
     * Sets up and runs the game window.
     * 
     * <p>Sets up a new JFrame and loads the TitlePanel and GamePanel
     * into it. Runs the titlePanel, then switches to the gamePanel and
     * starts the game.
     * 
     */
    public void openGameWindow() {

        // Create the window
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Smoothie Operator");
        window.setResizable(false);

        titlePanel = new TitlePanel();
        gamePanel = new GamePanel();

        // Set up the layout
        layout = new CardLayout();
        deck = new JPanel(layout);
        deck.add(titlePanel, "Smoothie Operator Title Screen");
        deck.add(gamePanel, "Smoothie Operator Gameplay Screen");

        // Finish setting up window layout
        window.add(deck);
        window.setLayout(layout);
        window.pack(); //window is sized to fit the preferred size and layouts of the gamePanel
        window.setLocationRelativeTo(null); //sets window to appear at center of screen
        window.setVisible(true); //allows us to see the window

        // Run the title screen and then the game
        titlePanel.run();
        layout.next(deck);
        gamePanel.requestFocusInWindow();
        if (titlePanel.getSound() != null) {
            titlePanel.getSound().stopSound();
        }
        gamePanel.startGame();
    }
}
