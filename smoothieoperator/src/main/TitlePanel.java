package smoothieoperator.src.main;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Represents the game's title screen with a background and start button.
 * When run() is called, displays until the start button is clicked.
 */
public class TitlePanel extends JPanel{
    private JButton startButton;
    private MouseHandler mouseHandler;
    private BufferedImage backgroundImage;
    private Sound sound;

    /**
     * Creates a new TitlePanel with the dimensions of the GamePanel.
     */
    public TitlePanel() {

        // match to GamePanel screen
        this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));

        this.mouseHandler = new MouseHandler();
        loadBackground();
        loadButton();

        this.setDoubleBuffered(true);

        try {
            this.sound = new Sound("/smoothieoperator/src/media/sounds/SmoothieTime.wav");
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Error retrieving sound file");
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates the backgroundImage. If this method cannot load the file, instantiates an
     * invisible BufferedImage.
     */
    public void loadBackground() {
        AffineTransform imageScale =
                AffineTransform.getScaleInstance(GamePanel.SCALE, GamePanel.SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        try {
            BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(
                    "/smoothieoperator/src/media/images/titlePanel/demo.png"));
            backgroundImage = scaleOp.filter(temp, null);
        } catch (IOException e) {
            backgroundImage = new BufferedImage(
                    GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
            System.out.println("Couldn't find background image.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            backgroundImage = new BufferedImage(
                    GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
            System.out.println("Couldn't find background image.");
            e.printStackTrace();
        }
    }

    /**
     * Instantiates the startButton and adds it to this JPanel.
     */
    public void loadButton() {
        // make clickable button
        this.startButton = new JButton("Start");
        this.startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        this.startButton.setVerticalAlignment(SwingConstants.BOTTOM);
        this.startButton.setFocusable(true);
        this.startButton.addMouseListener(this.mouseHandler);
        this.add(startButton);
    }

    public Sound getSound() {
        return this.sound;
    }

    /**
     * Draws this panel until the button is clicked.
     */
    public void run() {
      double drawInterval = 1000000000 / GamePanel.FPS; // get 1/60 of a second in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (!this.mouseHandler.getButtonPressed()) {
            // track time since last update 
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // update fps times per second
            if (delta >= 1) {
                repaint();
                delta--;
            }
            if (this.sound != null) {
                this.sound.loopSound();
            }
        }
    }

    /**
     * Draw this panel.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(backgroundImage, 0, 0, null);
    }
}
