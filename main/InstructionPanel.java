package main;

import javax.swing.JPanel;
import javax.swing.JButton;

public class InstructionPanel extends JPanel{

    //settings - same as GamePanel
    public static final int TILE_SIZE = 48;
    public static final int SCALE = 4;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCALE * 4;
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCALE * 3;
    public static final int FPS = 60;

    JButton button = new JButton("Let's Play!");
    

    
}
