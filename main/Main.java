package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Smoothie Operator");
        window.setResizable(false);

        //InstructionPanel titleScreen = new InstructionPanel();
        //window.add(titleScreen);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //window is sized to fit the preferred size and layouts of the gamepanel

        window.setLocationRelativeTo(null); //sets window to appear at center of screen
        window.setVisible(true); //allows us to see the window

        gamePanel.startGame();


    }
}