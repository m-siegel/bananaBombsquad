import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    public static final int TILE_SIZE = 48;
    public static final int SCREEN_WIDTH = 1536;
    public static final int SCREEN_HEIGHT = 1152;
    public static final int FPS = 60;
    
    private Thread gameThread;

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
