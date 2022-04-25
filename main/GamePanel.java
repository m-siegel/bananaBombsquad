package main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;

import spriteEssentials.HitBox;
import spriteEssentials.SpriteList;
import gameSprites.*;


public class GamePanel extends JPanel {

    // settings
    public static final int TILE_SIZE = 48;
    public static final int SCALE = 1; // can change window size while maintaining aspect ratio
    public static final int SCREEN_WIDTH = TILE_SIZE * SCALE * 16;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCALE * 12;
    public static final int FPS = 60;
    public static final String[] FRUIT_NAMES = {"banana", "strawberry", "orange"};

    // instance variables
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

    // methods
    public void loadSprites() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        ArrayList<BufferedImage> tempImages = new ArrayList<>();

        // create lives
        try {
            for (int i = 0; i < 7; i++) {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(
                    String.format("/media/images/lives/lives-%d.png", i)));
                tempImages.add(scaleOp.filter(temp, null));
            }
        } catch (IOException e) {
            System.out.println("Couldn't find lives image file.");
            e.printStackTrace();
        }
        this.lives =
                new Lives((TILE_SIZE * SCALE / 2), (TILE_SIZE * SCALE / 2), tempImages, this.keyH);
       
        tempImages.clear(); // will reuse

        // create powerbar
        try {
            for (int i = 0; i < 21; i++) {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(
                        String.format("/media/images/powerbar/powerbar-%d.png", i)));
                tempImages.add(scaleOp.filter(temp, null));
            }
        } catch (IOException e) {
            System.out.println("Couldn't find power bar image file.");
            e.printStackTrace();
        }

        // index into tempImages safely to get correct location based on image size
        try {
            this.powerBar = new PowerBar((TILE_SIZE * SCALE / 2),
                    SCREEN_HEIGHT - (TILE_SIZE * SCALE / 2 + tempImages.get(0).getHeight()),
                    tempImages, this.keyH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Couldn't find power bar image file.");
            e.printStackTrace();
            this.powerBar = new PowerBar((TILE_SIZE * SCALE / 2),
                    SCREEN_HEIGHT - (TILE_SIZE * SCALE / 2), tempImages, this.keyH);
        }

        tempImages.clear();

        // create cannon
        try {
            BufferedImage tempCannon = ImageIO.read(getClass()
                    .getResourceAsStream(String.format("/media/images/cannon/main-cannon.png")));
            tempCannon = scaleOp.filter(tempCannon, null);
            BufferedImage tempWheel = ImageIO.read(getClass()
                    .getResourceAsStream(String.format("/media/images/cannon/wheel-size-2.png")));
            tempWheel = scaleOp.filter(tempWheel, null);
            this.cannon = new Cannon((TILE_SIZE * SCALE / 2 + this.powerBar.getWidth()),
                    SCREEN_HEIGHT - (TILE_SIZE * SCALE + tempCannon.getHeight()), tempCannon,
                    tempWheel, this.keyH);
        } catch (IOException e) {
            System.out.println("Couldn't find cannon or wheel image files.");
            e.printStackTrace();
        }

        // create target
        try {
            for (int i = 1; i < 5; i++) {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(
                        String.format("/media/images/blender/blender-%d.png", i)));
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
            BufferedImage tempBackground = ImageIO.read(getClass().getResourceAsStream(
                    String.format("/media/images/background/background.png")));
            tempBackground = scaleOp.filter(tempBackground, null);
            this.background = new Background(tempBackground);
        } catch (IOException e) {
            System.out.println("Couldn't find background image file.");
            e.printStackTrace();
        }

        // create wall
        try {
            BufferedImage tempWall = ImageIO.read(
                    getClass().getResourceAsStream(String.format("/media/images/wall/wall.png")));
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
                    BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(String.format(
                            "/media/images/projectiles/%s/flying/%s-%d.png", fruit, fruit, i)));
                    tempFlying.add(scaleOp.filter(temp, null));
                }
                for (int i = 1; i < 2; i++) {
                    BufferedImage temp = ImageIO.read(getClass().getResourceAsStream(String.format(
                     "/media/images/projectiles/%s/splattered/%s-splat-%d.png", fruit, fruit, i)));
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

    public void startGame() {
        this.isRunning = true;
        this.gameSetup();
        this.run();
    }

    public void gameSetup() {
        projectiles.clear();
        target.reset();
        lives.livesReset();
    }

    public void run() {
        double drawInterval = 1000000000 / FPS; // get 1/60 of a second in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (isRunning) {
            // track time since last update 
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // update fps times per second
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
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
            if (projectiles.get(projectiles.size() - 1).collidesWith(target)) {
                projectiles.remove(projectiles.size() - 1);
                target.incrementNumberOfHits();
                target.resetPosition();
                launchedProjectile = false;
            } else if (projectiles.get(projectiles.size() - 1).collidesWith(wall)) {
                ((Projectile) projectiles.get(projectiles.size() - 1)).splat(90);
                lives.loseLife();
                target.resetPosition();
                launchedProjectile = false;
            } else if (projectiles.get(projectiles.size() - 1).collidesWith(background)) {
                ((Projectile) projectiles.get(projectiles.size() - 1)).splat();
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
                projectiles.add(
                        new Projectile(cannon.getLaunchX(), cannon.getLaunchY(), cannon.getAngle(),
                                powerBar.getPower(), projectileImages.get(fruit).get("flying"),
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
        Graphics2D g2D = (Graphics2D) g;

        // draw components back to front
        background.draw(g2D);
        wall.draw(g2D);
        projectiles.draw(g2D);
        cannon.draw(g2D);
        target.draw(g2D);
        powerBar.draw(g2D);
        lives.draw(g2D);
        g2D.setFont(new Font("MS Gothic", Font.PLAIN, 36));
        g2D.setColor(Color.MAGENTA);

        g2D.drawString(endMessage, SCREEN_WIDTH / 6, SCREEN_HEIGHT / 3);
    }
}
