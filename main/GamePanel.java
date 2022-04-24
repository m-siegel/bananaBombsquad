package main;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import spriteEssentials.HitBox;
import spriteEssentials.SpriteList;
import gameSprites.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;


public class GamePanel extends JPanel implements Runnable{
    
    //settings
    public static final int TILE_SIZE = 48;
    public static final int SCALE = 1; // can change window size while maintaining aspect ratio
    public static final int SCREEN_WIDTH = TILE_SIZE * SCALE * 16;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCALE * 12;
    public static final int FPS = 60;
    public static final String[] FRUIT_NAMES = {"banana", "strawberry", "orange"};
    
    
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
    private HashMap<String, HashMap<String, ArrayList<BufferedImage>>> projectileImages;
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
        launchedProjectile = false;
        this.projectiles = new SpriteList();
        this.projectileImages = new HashMap<String, HashMap<String, ArrayList<BufferedImage>>>();
        this.loadSprites();
        
    }
    
    
    //methods
    public void loadSprites() {
        // do something with scale here
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp = new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        ArrayList<BufferedImage> tempImages = new ArrayList<>();

        // create lives
        try {
            for (int i = 0; i < 7; i++) {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/lives/lives-%d.png", i)));
                tempImages.add(scaleOp.filter(temp, null));
            }
        } catch (IOException e) {
            System.out.println("Couldn't find lives image file.");
            e.printStackTrace();
        }

        this.lives = new Lives((TILE_SIZE * SCALE / 2), (TILE_SIZE * SCALE / 2), tempImages, this.keyH);
        tempImages.clear();

        // create powerbar
        try {
            for (int i = 0; i < 21; i++) {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/powerbar/powerbar-%d.png", i)));
                tempImages.add(scaleOp.filter(temp, null));
            }
        } catch (IOException e) {
            System.out.println("Couldn't find power bar image file.");
            e.printStackTrace();
        }

        try {
            this.powerBar = new PowerBar((TILE_SIZE * SCALE / 2), SCREEN_HEIGHT - (TILE_SIZE * SCALE / 2 + tempImages.get(0).getHeight()), tempImages, this.keyH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Couldn't find power bar image file.");
            e.printStackTrace();
            this.powerBar = new PowerBar((TILE_SIZE * SCALE / 2), SCREEN_HEIGHT - (TILE_SIZE * SCALE / 2), tempImages, this.keyH);      
        }

        tempImages.clear();

        // create cannon
        try {
            BufferedImage tempCannon = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/cannon/main-cannon.png")));
            tempCannon = scaleOp.filter(tempCannon, null);
            BufferedImage tempWheel = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/cannon/wheel-size-2.png")));
            tempWheel = scaleOp.filter(tempWheel, null);
            this.cannon = new Cannon((TILE_SIZE * SCALE / 2 + this.powerBar.getWidth()), SCREEN_HEIGHT - (TILE_SIZE * SCALE + tempCannon.getHeight()), tempCannon, tempWheel, this.keyH);
        } catch (IOException e) {
            System.out.println("Couldn't find cannon or wheel image files.");
            e.printStackTrace();
        } 

        // create target
        try {
            for (int i = 1; i < 5; i++) {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/blender/blender-%d.png", i)));
                tempImages.add(scaleOp.filter(temp, null));
            }
        } catch (IOException e) {
            System.out.println("Couldn't find target image files.");
            e.printStackTrace();
        }

        this.target = new Target(tempImages);
        tempImages.clear();

        // create background
        try {
            BufferedImage tempBackground = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/background/background.png")));
            tempBackground = scaleOp.filter(tempBackground, null);
            this.background = new Background(tempBackground);
        } catch (IOException e) {
            System.out.println("Couldn't find background image file.");
            e.printStackTrace();
        } 

        // create wall
        try {
            BufferedImage tempWall = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/wall/wall-new.png")));
            tempWall = scaleOp.filter(tempWall, null);
            this.wall = new Wall(tempWall);
        } catch (IOException e) {
            System.out.println("Couldn't find wall image file.");
            e.printStackTrace();
        } 

        // load projectile images


        for (String fruit : FRUIT_NAMES) {
            ArrayList<BufferedImage> tempFlying = new ArrayList<BufferedImage>();
            ArrayList<BufferedImage> tempSplattered = new ArrayList<BufferedImage>();

            try {
                for (int i = 1; i < 5; i++) {
                    BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/projectiles/%s/flying/%s-%d.png", fruit, fruit, i)));
                    tempFlying.add(scaleOp.filter(temp, null));
                }
                for (int i = 1; i < 2; i++) {
                    BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(String.format("/media/images/projectiles/%s/splattered/%s-splat-%d.png", fruit, fruit, i)));
                    tempSplattered.add(scaleOp.filter(temp, null));
                }
            } catch (IOException e) {
                System.out.printf("Couldn't find %s image files.\n", fruit);
                e.printStackTrace();
            }

            projectileImages.put(fruit, new HashMap<String, ArrayList<BufferedImage>>());
            projectileImages.get(fruit).put("flying", tempFlying);
            projectileImages.get(fruit).put("splattered", tempSplattered);

        }
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


    public void gameSetup() {
        projectiles.clear();

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
                System.out.println("Collision with target");
                projectiles.remove(projectiles.size()-1);
                target.incrementNumberOfHits();
                target.resetPosition();
                launchedProjectile = false;
            } else if (projectiles.get(projectiles.size()-1).collidesWith(wall)) {
                // Debugging
                System.out.println("Collision with wall");
                HitBox pH = projectiles.get(projectiles.size()-1).getHitBox();
                HitBox wH = wall.getHitBox();
                System.out.printf("projectile HB: %d, %d, %d, %d\n", pH.getXMin(), pH.getXMax(), pH.getYMin(), pH.getYMax());
                System.out.printf("wall HB: %d, %d, %d, %d\n", wH.getXMin(), wH.getXMax(), wH.getYMin(), wH.getYMax());
                ((Projectile)projectiles.get(projectiles.size()-1)).splat();
                lives.loseLife();
                target.resetPosition();
                launchedProjectile = false;

            } else if (projectiles.get(projectiles.size()-1).collidesWith(background)) {
                System.out.println("Collision with background");
                ((Projectile)projectiles.get(projectiles.size()-1)).splat();
                lives.loseLife();
                target.resetPosition();
                launchedProjectile = false;
            } else if (projectiles.get(projectiles.size() - 1).getX() >= SCREEN_WIDTH) {
                projectiles.remove(projectiles.size() - 1);
                lives.loseLife();
                target.reset();
                launchedProjectile = false;
            }
            
        // if not a flying projectile, we check to see if player wants to shoot    
        } else {
            if (keyH.getShootButtonPressed()) {
                Random randomizer = new Random();
                int i = randomizer.nextInt(FRUIT_NAMES.length);
                String fruit = FRUIT_NAMES[i];
                projectiles.add(new Projectile(cannon.getLaunchX(), cannon.getLaunchY(), 
                    cannon.getAngle(), powerBar.getPower(), projectileImages.get(fruit).get("flying"), 
                    projectileImages.get(fruit).get("splattered"), 1));
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

        //g2D.drawString(endMessage, 100, 100); //TODO - figure out x and y and font and color
        
        
        

        


    }
}
