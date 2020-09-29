package freezemonster.sprite;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MonsterSprite extends BadnessBoxSprite {

    private Goop goop;

    public MonsterSprite(int x, int y, int indice) {
        initMonster(x, y, indice);
    }

    private void initMonster(int x, int y, int indice) {

        this.x = x;
        this.y = y;

        goop = new Goop(x, y);

        String alienImg = "images/monster"+indice+".png";
        ImageIcon ii = new ImageIcon(alienImg);

        setImage(ii.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH));
    }

    public Goop getBomb() {
        return goop;
    }


	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aBomb = new LinkedList<BadSprite>();
		aBomb.add(goop);
		return aBomb;
	}
}
