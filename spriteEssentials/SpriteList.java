package spriteEssentials;

import java.util.ArrayList;
import java.awt.Graphics2D;

public class SpriteList extends ArrayList<Sprite> {

    public void update() {
        for (Sprite x : this) {
            x.update();
        }
    }

    public void draw(Graphics2D g2) {
        for (Sprite x : this) {
            x.draw(g2);
        }
    }
}
