package smoothieoperator.src.spriteEssentials;

import java.util.ArrayList;
import java.awt.Graphics2D;

/**
 * Extension of ArrayList<Sprite>.
 * Includes methods to update or draw all elements in the SpriteList.
 */
public class SpriteList extends ArrayList<Sprite> {

    /**
     * Calls the update method for each Sprite in the SpriteList.
     */
    public void update() {
        for (Sprite x : this) {
            x.update();
        }
    }

    /**
     * Calls the draw method for each Sprite in the SpriteList.
     */
    public void draw(Graphics2D g2) {
        for (Sprite x : this) {
            x.draw(g2);
        }
    }
}
