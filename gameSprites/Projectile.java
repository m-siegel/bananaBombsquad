package gameSprites;

import java.util.ArrayList;
import spriteEssentials.*;

public class Projectile extends Sprite {

    public Projectile(double angle, int x, int y, int velocity) {
        this.splattered = false;
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

    private boolean splattered;
    private double angle;
    private int velocity;
    private int internalX;
    private int internalY;
    private int xDisplacement;
    private int yDisplacement;

    //getters
    public boolean isSplattered() {
        return this.splattered;
    }
    public double getAngle() {return this.angle;}
    public int getVelocity() {return this.velocity;}
    public int getInternalX() {return this.internalX;}
    public int getInternalY() {return this.internalY;}
    public int getXDisplacement() {return this.xDisplacement;}
    public int getYDisplacement() {return this.yDisplacement;}




    public void update() {
        this.internalX += this.speed;
        this.internalY = (int) Math.round( Math.tan(angle * (Math.PI / 180) ) - (9.8 * (internalX * internalX / 
            (2 * velocity * velocity * Math.cos(angle * (Math.PI / 180) ) * Math.cos(angle * (Math.PI / 180) ))) ));

        //formula: y = h + tan(A) - ( 9.8 * ( x^2 / (2v^2 * cos(A)* cos(A)) )
        //h = 0
        
    }

    public void draw() {
        //x-coordinate: x = internalX + xDisplacement
        //y-coordinate: 
    }

}
