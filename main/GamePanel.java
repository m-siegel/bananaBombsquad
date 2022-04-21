package main;
import javax.swing.JPanel;
import spriteEssentials.SpriteList;
import sprites.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GamePanel extends JPanel implements Runnable{
    
    //settings
    public static final int TILE_SIZE = 48;
    public static final int SCALE = 4;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCALE * 4;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCALE * 3;
    public static final int FPS = 60;
    
    
    //instance variables
    private Thread gameThread;
    private KeyHandler keyH;
    private Background background;
    private Wall wall;
    private Ground ground;
    private SpriteList projectiles;
    private Cannon cannon;
    private Target target;
    private PowerBar powerBar;
    private Lives lives;
    
    //constructors, getters, setters
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.addKeyListener(keyH);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        
    }
    
    
    //methods
    public void loadSprites() {

    }

    
    /* requires KeyHandler */
    public void adjustAngleAndPower() {

    }

    // potential method: roundReset()
    //      - reset the angle of the cannon
    //      - reposition the target
    //      - initialize next projectile
    //      - take life away or fill blender
    //      - if isDead -> print: Efforts were fruitless
    //      - if target.isFull() -> print: Winning message
    //          -either case: go back to loading screen
    public void roundReset() {

    }

    public void gameReset() {

    }


    public void update() {
        // create a playRound that depends on current projectile's splattered boolean value
        // call all update methods for all objects currently in use 
        // loop that keeps going
        initiateLaunch();
    }

    public void initiateLaunch() {

    };

    public void startGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start();
    }



    @Override
    public void run() {
        
        //Game loop goes here
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
    
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        // draw the different elements
        background.draw(g2D);
        wall.draw(g2D);
        ground.draw(g2D);
        projectiles.draw(g2D);
        cannon.draw(g2D);
        target.draw(g2D);
        powerBar.draw(g2D);
        lives.draw(g2D);
        
        
        

        


    }
}
