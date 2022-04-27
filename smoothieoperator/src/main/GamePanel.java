package smoothieoperator.src.main;

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
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;

import smoothieoperator.src.spriteEssentials.SpriteList;
import smoothieoperator.src.gameSprites.*;

/**
 * Holds game components and runs game logic for the Smoothie Operator game.
 * Instantiates game sprites and runs the game loop that updates and draws sprites,
 * takes player input, and determines points won, lives lost, and the end of a round.
 */
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
    private EndMessage endMessage;
    private boolean launchedProjectile;

    private boolean fatalError;
    private String errorMessage;

    /**
     * Creates a new GamePanel object for the Smoothie Operator game.
     */
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

        this.fatalError = false;
        this.errorMessage = "";
        this.loadSprites();
    }

    /**
     * Calls helper methods to initialize the Background, Wall, Cannon,
     * Target, PowerBar and Lives sprites for this game.
     * Calls helper method to load the images that will be used to instantiate projectiles
     * into the projectileImages HashMap.
     * 
     * <p>Sets fatalError to true if any loading methods fail and appends message to errorMessage.
     */
    public void loadSprites() {

        if (!loadLives()) {
            this.fatalError = true;
            this.errorMessage += "loading Lives, ";
        }
        if (!loadPowerBar()) {
            this.fatalError = true;
            this.errorMessage += "loading PowerBar, ";
        }
        if (!loadCannon()) {
            this.fatalError = true;
            this.errorMessage += "loading Cannon, ";
        }
        if (!loadTarget()) {
            this.fatalError = true;
            this.errorMessage += "loading Target, ";
        }
        if (!loadBackground()) {
            this.fatalError = true;
            this.errorMessage += "loading Background, ";
        }
        if (!loadWall()) {
            this.fatalError = true;
            this.errorMessage += "loading Wall, ";
        }
        if (!loadProjectileImages()) {
            this.fatalError = true;
            this.errorMessage += "loading Projectile Images, ";
        } 
        if (!loadEndMessage()) {
            this.fatalError = true;
            this.errorMessage += "loading End Message, ";
        }
    }

    /**
     * Loads the images for the Lives object and instantiates the Lives object. Returns
     * true if the object is successfully instantiated. Returns false if any errors occur.
     * 
     * @return true if lives is instantiated; false if exceptions occur.
     */
    private boolean loadLives() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        ArrayList<BufferedImage> tempImages = new ArrayList<>();

        String filepath = "";
        InputStream inputStream = null;

        // create lives
        try {
            for (int i = 0; i < 7; i++) {
                filepath =
                        String.format("/smoothieoperator/src/media/images/lives/lives-%d.png", i);
                inputStream = getClass().getResourceAsStream(filepath);
                if (inputStream != null) { // or risk IllegalArgumentException
                    BufferedImage temp = ImageIO.read(inputStream);
                    tempImages.add(scaleOp.filter(temp, null));
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't find lives image file: " + filepath);
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

        try {
            this.lives =
                new Lives((TILE_SIZE * SCALE / 2), (TILE_SIZE * SCALE / 2), tempImages, 
                this.keyH, "/smoothieoperator/src/media/sounds/splat.wav", "loseLife");
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't instantiate Lives object.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Loads the images for the PowerBar object and instantiates the PowerBar object. Returns
     * true if the object is successfully instantiated. Returns false if any errors occur.
     * 
     * @return true if PowerBar is instantiated; false if exceptions occur.
     */
    private boolean loadPowerBar() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        ArrayList<BufferedImage> tempImages = new ArrayList<>();

        String filepath = "";
        InputStream inputStream = null;

        // create powerbar
        try {
            for (int i = 0; i < 21; i++) {
                filepath = String.format(
                    "/smoothieoperator/src/media/images/powerbar/powerbar-%d.png", i);
                inputStream = getClass().getResourceAsStream(filepath);
                if (inputStream != null) {
                    BufferedImage temp = ImageIO.read(inputStream);
                    tempImages.add(scaleOp.filter(temp, null));
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't find power bar image file:" + filepath);
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

        // index into tempImages safely to get correct location based on image size
        try {
            this.powerBar = new PowerBar((TILE_SIZE * SCALE / 2),
                    SCREEN_HEIGHT - (TILE_SIZE * SCALE / 2 + tempImages.get(0).getHeight()),
                    tempImages, this.keyH);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Couldn't find power bar image file: " + filepath);
            e.printStackTrace();
            this.powerBar = new PowerBar((TILE_SIZE * SCALE / 2),
                    SCREEN_HEIGHT - (TILE_SIZE * SCALE / 2), tempImages, this.keyH);
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't instantiate PowerBar object.");
            return false;
        }

        return true;
    }

    /**
     * Loads the images for the Cannon object and instantiates the Cannon object. Returns
     * true if the object is successfully instantiated. Returns false if any errors occur.
     * 
     * @return true if Cannon is instantiated; false if exceptions occur.
     */
    private boolean loadCannon() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        String filepath = "";
        InputStream inputStream = null;

        // create cannon
        try {
            BufferedImage tempCannon = null;
            BufferedImage tempWheel = null;

            filepath = String.format("/smoothieoperator/src/media/images/cannon/main-cannon.png");
            inputStream = getClass().getResourceAsStream(filepath);
            if (inputStream != null) {
                tempCannon = ImageIO.read(inputStream);
                tempCannon = scaleOp.filter(tempCannon, null);
                inputStream.close();
            }

            filepath = String.format("/smoothieoperator/src/media/images/cannon/wheel-size-2.png");
            inputStream = getClass().getResourceAsStream(filepath);
            if (inputStream != null) {
                tempWheel = ImageIO.read(inputStream);
                tempWheel = scaleOp.filter(tempWheel, null);
                inputStream.close();
            }
            
            this.cannon = new Cannon((TILE_SIZE * SCALE / 2 + this.powerBar.getWidth()),
                    SCREEN_HEIGHT - (TILE_SIZE * SCALE + tempCannon.getHeight()), tempCannon,
                    tempWheel, this.keyH, "/smoothieoperator/src/media/sounds/boom.wav",
                    "boom");
        } catch (IOException e) {
            System.out.println("Couldn't find cannon or wheel image files: " + filepath);
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            System.out.println("Couldn't find cannon or wheel image files: " + filepath);
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't instantiate Cannon object.");
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

        return true;
    }

    /**
     * Loads the images for the Target object and instantiates the Target object. Returns
     * true if the object is successfully instantiated. Returns false if any errors occur.
     * 
     * @return true if Target is instantiated; false if exceptions occur.
     */
    private boolean loadTarget() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        ArrayList<BufferedImage> tempImages = new ArrayList<>();

        String filepath = "";
        InputStream inputStream = null;

        // create target
        try {
            for (int i = 1; i < 5; i++) {
                filepath = String.format(
                        "/smoothieoperator/src/media/images/blender/blender-%d.png", i);
                inputStream = getClass().getResourceAsStream(filepath);
                if (inputStream != null) {
                    BufferedImage temp = ImageIO.read(inputStream);
                    tempImages.add(scaleOp.filter(temp, null));
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't find target image files: " + filepath);
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

        try {
            this.target = new Target(tempImages,
                "/smoothieoperator/src/media/sounds/splash.wav", "splash");
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't instantiate Target object.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Loads the images for the Background object and instantiates the Background object. Returns
     * true if the object is successfully instantiated. Returns false if any errors occur.
     * 
     * @return true if Background is instantiated; false if exceptions occur.
     */
    private boolean loadBackground() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        String filepath = "";
        InputStream inputStream = null;

        // create background
        try {
            filepath = String.
                    format("/smoothieoperator/src/media/images/background/background.png");
            inputStream = getClass().getResourceAsStream(filepath);
            if (inputStream != null) {
                BufferedImage tempBackground = ImageIO.read(inputStream);
                inputStream.close();
                tempBackground = scaleOp.filter(tempBackground, null);
                this.background = new Background(tempBackground, 
                        "/smoothieoperator/src/media/sounds/slowsong.wav", "GameSong");
            }
        } catch (IOException e) {
            System.out.println("Couldn't find background image file: " + filepath);
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't instantiate Background object.");
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

        return true;
    }

    /**
     * Loads the images for the EndMessage object and instantiates the EndMessage object. Returns
     * true if the object is successfully instantiated. Returns false if any errors occur.
     * 
     * @return true if EndMessage is instantiated; false if exceptions occur.
     */
    private boolean loadEndMessage() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        String filepath = "";
        InputStream inputStream = null;

        try {
            BufferedImage tempLosing = null;
            BufferedImage tempWinning = null;

            filepath = String.format("/smoothieoperator/src/media/images/endmessage/losing.png");
            inputStream = getClass().getResourceAsStream(filepath);
            if (inputStream != null) {
                tempLosing = ImageIO.read(inputStream);
                tempLosing = scaleOp.filter(tempLosing, null);
                inputStream.close();
            }

            filepath = String.format("/smoothieoperator/src/media/images/endmessage/winning.png");
            inputStream = getClass().getResourceAsStream(filepath);
            if (inputStream != null) {
                tempWinning = ImageIO.read(inputStream);
                tempWinning = scaleOp.filter(tempWinning, null);
                inputStream.close();
            }
            
            this.endMessage = new EndMessage((SCREEN_WIDTH * SCALE / 2) - (tempLosing.getWidth() / 2),
                    (SCREEN_HEIGHT * SCALE / 2) - (tempLosing.getHeight() / 2), tempLosing,
                    tempWinning, this.keyH);
        } catch (IOException e) {
            System.out.println("Couldn't find end message image files: " + filepath);
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            System.out.println("Couldn't find end message image files: " + filepath);
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't instantiate EndMessage object.");
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

        return true;
    }

    /**
     * Loads the images for the Wall object and instantiates the Wall object. Returns
     * true if the object is successfully instantiated. Returns false if any errors occur.
     * 
     * @return true if Wall is instantiated; false if exceptions occur.
     */
    private boolean loadWall() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        String filepath = "";
        InputStream inputStream = null;

        // create wall
        try {
            filepath = String.format("/smoothieoperator/src/media/images/wall/wall.png");
            inputStream = getClass().getResourceAsStream(filepath);
            if (inputStream != null) {
                BufferedImage tempWall = ImageIO.read(inputStream);
                inputStream.close();
                tempWall = scaleOp.filter(tempWall, null);
                this.wall = new Wall(tempWall);
            }
        } catch (IOException e) {
            System.out.println("Couldn't find wall image file: " + filepath);
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Couldn't instantiate Wall object.");
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

        return true;
    }

    /**
     * Loads the images for the Projectiles into the projectileImages HashMap. Returns
     * false if any errors occur.
     * 
     * @return false if any errors occur; true otherwise.
     */
    private boolean loadProjectileImages() {
        // scale images as they're read in
        AffineTransform imageScale = AffineTransform.getScaleInstance(SCALE, SCALE);
        AffineTransformOp scaleOp =
                new AffineTransformOp(imageScale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        String filepath = "";
        InputStream inputStream = null;

        // load projectile images
        for (String fruit : FRUIT_NAMES) {
            ArrayList<BufferedImage> tempFlying = new ArrayList<BufferedImage>();
            ArrayList<BufferedImage> tempSplattered = new ArrayList<BufferedImage>();

            try {
                for (int i = 1; i < 5; i++) {
                    filepath = String.format(
                            "/smoothieoperator/src/media/images/projectiles/%s/flying/%s-%d.png",
                            fruit, fruit, i);
                    inputStream = getClass().getResourceAsStream(filepath);
                    if (inputStream != null) {
                        BufferedImage temp = ImageIO.read(inputStream);
                        tempFlying.add(scaleOp.filter(temp, null));
                        inputStream.close();
                    }
                }
                for (int i = 1; i < 2; i++) {
                    filepath = String.format(
                            "/smoothieoperator/src/media/images/projectiles/"
                            + "%s/splattered/%s-splat-%d.png", fruit, fruit, i);
                    inputStream = getClass().getResourceAsStream(filepath);
                    if (inputStream != null) {
                        BufferedImage temp = ImageIO.read(inputStream);
                        tempSplattered.add(scaleOp.filter(temp, null));
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                System.out.printf("Couldn't find %s image files.\n", fruit);
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    } 
                }
            }
            projectileImages.put(fruit, new HashMap<String, ArrayList<BufferedImage>>());
            projectileImages.get(fruit).put("flying", tempFlying);
            projectileImages.get(fruit).put("splattered", tempSplattered);
        }

        return true;
    }


    /**
     * Starts the game if no fatal error has occurred.
     * 
     * <p>Sets up the game, sets isRunning to true and starts the game loop.
     */
    public void startGame() {
        if (fatalError) {
            return;
        }
        this.isRunning = true;
        this.gameSetup();
        this.run();
    }

    /**
     * Resets components of the game if no fatal error has occurred.
     * 
     * <p>Clears endMessage and projectiles SpriteList.
     * Resets number of lives and the position of the target.
     * Stops the ending music and plays the in-game music.
     */
    public void gameSetup() {
        if (fatalError) {
            return;
        }
        if (endMessage.getSound("losingSong") != null) {
            endMessage.getSound("losingSong").stopSound();
        }
        if (endMessage.getSound("winningSong") != null) {
            endMessage.getSound("winningSong").stopSound();
        }
        Sound gameSong = this.background.getSound("GameSong");
        if (gameSong != null) {
            gameSong.loopSound();
        }
        endMessage.removeEndMessage();
        projectiles.clear();
        target.reset();
        lives.livesReset();
    }

    /**
     * Game loop.
     * Calls update() and paintComponent() FPS times per second while the game is running
     * and no fatal error has occurred.
     */
    public void run() {
        double drawInterval = 1000000000 / FPS; // get 1/60 of a second in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (isRunning && !fatalError) {
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

    /**
     * Main game logic.
     * 
     * <p>Resets the game as needed, in response to keyboard input.
     * 
     * <p>Ends the game (either winning or losing) based on whether the target is full
     * or the player is dead.
     * 
     * <p>Registers and reacts to collisions between projectiles and other solid objects.
     * 
     * <p>Launches projectiles in response to keyboard input.
     * 
     * <p>Updates all sprites with their update methods, if no projectile is flying. Updates
     * projectiles with each call.
     */
    public void update() {
        // Checking for restart of the game
        if (keyH.getResetTyped()) {
            startGame();
        }

        // Checking for the end of game
        if (lives.isDead()) {
            endMessage.displayEndMessage();
            if (this.background.getSound("GameSong") != null) {
                this.background.getSound("GameSong").stopSound();
            }
            if (endMessage.getSound("losingSong") != null) {
                endMessage.getSound("losingSong").loopSound();
            }
            endMessage.playerWon(false);
            return;
        }
        if (target.isFull()) {
            endMessage.displayEndMessage();
            if (this.background.getSound("GameSong") != null) {
                this.background.getSound("GameSong").stopSound();
            }
            if (endMessage.getSound("winningSong") != null) {
                endMessage.getSound("winningSong").loopSound();
            }
            endMessage.playerWon(true);
            return;
        }

        // if flying projectile, check for collisions
        if (projectiles.size() > 0 && launchedProjectile) {
            if (projectiles.get(projectiles.size() - 1).collidesWith(target)) {
                projectiles.remove(projectiles.size() - 1);
                target.getSound("splash").playSound();
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
            } else if (projectiles.get(projectiles.size() - 1).getX() >= SCREEN_WIDTH
                    || projectiles.get(projectiles.size() - 1).getY() > SCREEN_HEIGHT) {
                projectiles.remove(projectiles.size() - 1);
                lives.loseLife();
                target.reset();
                launchedProjectile = false;
            }

        // if not a flying projectile, we check to see if player wants to shoot
        } else {
            if (keyH.getShootButtonPressed()) {
                this.cannon.getSound("boom").playSound();
                Random randomizer = new Random();
                int i = randomizer.nextInt(FRUIT_NAMES.length);
                String fruit = FRUIT_NAMES[i];
                try {
                    projectiles.add(
                            new Projectile(cannon.getLaunchX(), cannon.getLaunchY(), cannon.getAngle(),
                                    powerBar.getPower(), projectileImages.get(fruit).get("flying"),
                                    projectileImages.get(fruit).get("splattered"), 5));
                    launchedProjectile = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Projectile could not be instantiated: " + fruit);
                    e.printStackTrace();
                    fatalError = true;
                    errorMessage += "instantiating Projectile, ";
                    return;
                }
                target.getSound("splash").stopSound();
            }
            cannon.update();
            powerBar.update();
            target.update();
            lives.update();
        }

        projectiles.update();
    }

    /**
     * Draws the screen and each component from farthest to nearest.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        if (!fatalError) {
            // draw components back to front
            background.draw(g2D);
            wall.draw(g2D);
            target.draw(g2D);
            powerBar.draw(g2D);
            lives.draw(g2D);
            projectiles.draw(g2D);
            cannon.draw(g2D);
            endMessage.draw(g2D);

            g2D.setFont(new Font("MS Gothic", Font.PLAIN, 36));
            g2D.setColor(Color.MAGENTA);
        } else {
            // Draw error screen
            g2D.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g2D.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
            g2D.setColor(Color.WHITE);
            g2D.drawString("Uh oh...the game won't run!", TILE_SIZE, 3 * TILE_SIZE);
            g2D.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
            // trim off last comma and space
            g2D.drawString("We encountered errors with: ", TILE_SIZE, 4 * TILE_SIZE);
            g2D.drawString(errorMessage.substring(
                    0, errorMessage.length() - 2), TILE_SIZE, 5 * TILE_SIZE);
            g2D.drawString("We recommend downloading Smoothie Operator package again.",
                    TILE_SIZE, SCREEN_HEIGHT - 4 * TILE_SIZE);
            g2D.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
            g2D.drawString("Hopefully the next run will be smoothier ;)",
                    TILE_SIZE, SCREEN_HEIGHT - 3 * TILE_SIZE);
        }
    }
}