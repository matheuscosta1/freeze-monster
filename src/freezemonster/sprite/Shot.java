package freezemonster.sprite;

import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;


public class Shot extends BadSprite {

    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

        String shotImg = "images/ray.png";
        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH));

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }

    @Override
    public int getMonsterImageIndice() {
        return 0;
    }
}
