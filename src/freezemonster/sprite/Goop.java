package freezemonster.sprite;

import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;

public class Goop extends BadSprite {

    private boolean destroyed;

    public Goop(int x, int y) {

        initGoop(x, y);
    }

    private void initGoop(int x, int y) {

        setDestroyed(true);

        this.x = x;
        this.y = y;

        String bombImg = "images/goop.png";
        ImageIcon ii = new ImageIcon(bombImg);
        setImage(ii.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH));
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public int getMonsterImageIndice() {
        return 0;
    }

}
