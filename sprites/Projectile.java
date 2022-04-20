package sprites;

import java.util.ArrayList;

public class Projectile extends Sprite {

    public Projectile(double angle, int x, int y, int velocity) {
        this.isSplattered = false;
        this.angle = angle;
        this.x = x;
        this.y = y;
        this.internalX = 0;
        this.internalY = 0; 
        this.xDisplacement = this.x - this.internalX;
        this.yDisplacement = this.y - this.internalY;
        this.velocity = velocity;
        this.images = new ArrayList<>();
        this.solid = true;
        this.keyHandler = null;
        this.speed = 5;
    }
    //instance variables

    private boolean isSplattered;
    private double angle;
    private int velocity;
    private int internalX;
    private int internalY;
    private int xDisplacement;
    private int yDisplacement;

    public void update() {
        //internal x += speed
        //internal y 
        
    }

    public void draw() {
        //x-coordinate: x = internalX + xDisplacement
        //y-coordinate: 
    }

}
