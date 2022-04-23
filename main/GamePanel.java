package main;
import javax.swing.JPanel;
import spriteEssentials.SpriteList;
import gameSprites.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable{
    
    //settings
    public static final int TILE_SIZE = 48;
    public static final int SCALE = 1; // can change window size while maintaining aspect ratio
    public static final int SCREEN_WIDTH = TILE_SIZE * SCALE * 16;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCALE * 12;
    public static final int FPS = 60;
    
    
    //instance variables
    private boolean isRunning;
    private KeyHandler keyH;
    private Background background;
    private Wall wall;
    private Cannon cannon;
    private Target target;
    private PowerBar powerBar;
    private Lives lives;
    private SpriteList projectiles;
    private HashMap<String, HashMap> projectileImages;
    private String endMessage = "";
    private boolean launchedProjectile;

    
    //constructors, getters, setters
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.isRunning = false;
        this.loadSprites();
        launchedProjectile = false;
        
    }
    
    
    //methods
    public void loadSprites() {
        // load in all sprites & instantiate lives, powerbar, cannon, background
        // projectiles - set to empty arraylist
        // create hashmap<String, ArrayList<Buffered Images>> - projectile images



    }

    
    // /* requires KeyHandler */
    // public void adjustAngleAndPower() {

    // }


    //Components of a round:

    //Beginning of game
    // Loading phase - draw all sprites in their initial state



    // 1: Launch preparation - user enters key input for angle and power
    // 2: Launch initiation - user presses "shoot" key, projectile's trajectory is calculated and drawn accordingly
    // 3: Evaluation phase - determine result (was target hit? is game over?) and update accordingly (roundReset)


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

    public void gameSetup() {
        projectiles.clear();
        // TODO -- create reset methods for target and lives
        target.reset();
        lives.livesReset();

    }

    public void startGame() {
        this.isRunning = true;
        this.gameSetup();
        this.run();
    }

    @Override
    public void run() {
        
        //Game loop goes here
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (isRunning) {

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
    
    public void update() {
        // create a playRound that depends on current projectile's splattered boolean value
        // call all update methods for all objects currently in use 
        // loop that keeps going
        //initiateLaunch();

        // Checking for the end of game
        if (lives.isDead()) {
            endMessage = "Your efforts were fruitless.";
            return;
        }
        if (target.isFull()) {
            endMessage = "Your efforts were fruitful.";
            return;
        }

        // if flying projectile, check for collisions
        if (projectiles.size() > 0 && launchedProjectile) {
            if (projectiles.get(projectiles.size()-1).collidesWith(target)) {
                // TODO -- create a splat() method
                projectiles.remove(projectiles.size()-1);
                target.incrementNumberOfHits();
                target.resetPosition();
                launchedProjectile = false;
            } else if (projectiles.get(projectiles.size()-1).collidesWith(wall) 
                    || projectiles.get(projectiles.size()-1).collidesWith(background)) {
                projectiles.get(projectiles.size()-1).splat();
                lives.loseLife();
                target.resetPosition();
                launchedProjectile = false;

            }
            
        // if not a flying projectile, we check to see if player wants to shoot    
        } else {
            if (keyH.getShootButtonPressed()) {
                // TODO -- get random name of fruit
                int i = Math.random(projectileFlyingImages.size());
                String fruit = projectileFlyingImages.getKeys(i);
                projectiles.add(new Projectile(cannon.getLaunchX(), cannon.getLaunchY(), cannon.getAngle(), powerBar.getPower(), projectileFlyingImages.get(fruit), projectile.splatteredImages.get(fruit), updatesPerSec));
                launchedProjectile = true;
            }
            cannon.update();
            powerBar.update();
            target.update();
            lives.update();

        }
        projectiles.update();

    }
    
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        // draw the different elements
        background.draw(g2D);
        wall.draw(g2D);
        projectiles.draw(g2D);
        cannon.draw(g2D);
        target.draw(g2D);
        powerBar.draw(g2D);
        lives.draw(g2D);
        
        
        

        


    }
}
