package gameSprites;
import java.util.ArrayList;
import java.util.Random;
import main.GamePanel;
import spriteEssentials.*;
//import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Target extends Sprite {
     // instance variables
     private int numberOfHits;
     //private boolean isHit;

     public Target() {
         this.x = getRandomXCoordinate();
         this.y = getRandomYCoordinate();
         this.speed = 5;
         this.keyHandler = null;
         this.solid = true;
         this.numberOfHits = 0;
         this.images = new ArrayList<BufferedImage>();
         //this.isHit = false;
     }

     
     public int getNumberOfHits() {return this.numberOfHits;}

     public void incrementNumberOfHits() {
         if (this.numberOfHits == 3) { //if already full, no action taken
             return;
         }
         this.numberOfHits++;
     }

    

     public boolean isFull() {return this.numberOfHits >= 3;}

     public int getRandomXCoordinate() {

         Random randomizer = new Random();
         int randomInt = randomizer.nextInt(GamePanel.SCREEN_WIDTH + 1); //random int between 0 - screen width, inclusive
         return randomInt;
     }
     public int getRandomYCoordinate() {

        Random randomizer = new Random();
        int randomInt = randomizer.nextInt(GamePanel.SCREEN_HEIGHT + 1); //random int between 0 - screen height, inclusive
        return randomInt;
     }

     public void update() {
         //set new random position for target
         this.x = getRandomXCoordinate();
         this.y = getRandomYCoordinate();

     }

     public void draw(Graphics2D g2) {
        switch (this.numberOfHits) {
            case 0:
               g2.drawImage(images.get(0), this.x, this.y, null);
               break;

            case 1:
               g2.drawImage(images.get(1), this.x, this.y, null);

            case 2:
               g2.drawImage(images.get(2), this.x, this.y, null);

            case 3:
               g2.drawImage(images.get(3), this.x, this.y, null);


        }
    }

}
