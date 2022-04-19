import javax.swing.JPanel;
import java.awt.Dimension;


//IMAGES WE NEED:
// Strawberry lives: 0(outline of a strawberry), 0.5, 1, 1.5, 2, 2.5, 3
// Fruit projectiles: Banana, Banana2(for animation), Strawberry, Strawberry 2, Pineapple, Pineapple 2
// Splattered fruit projectiles: Splattered banana, Splattered strawberry, Splattered pineapple
// Cannon
// Powerbar: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
// Blender: Empty, One-third filled, Two-thirds filled, Completely filled
// Wall
//
//
//
//


public class GamePanelPrototype extends JPanel implements Runnable{
    
    //settings
    public static final int TILE_SIZE = 48;
    public static final int SCALE = 4;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCALE * 4;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCALE * 3;
    public static final int FPS = 60;
    
    
    //instance variables
    private Thread gameThread;
    //private KeyHandler keyH;
    //private Sprite player;
    //private Sprite background;
    //private Sprite target;
    //private Sprite projectile;
    //private Sprite powerbar;
    
    //constructors, getters, setters
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        //this.addKeyListener(keyH);
        this.setDoubleBuffered(true); //improves rendering performance
        
    }
    
    
    //methods

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void loadSprites();

    
    /* requires KeyHandler */
    public void adjustAngleAndPower();


    public void update() {
        initiateLaunch();
    }

    public void initiateLaunch();



    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        //Game loop goes here
        
        update();
        repaint();
        
    }
    
}
