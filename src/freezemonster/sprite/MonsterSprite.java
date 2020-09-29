package freezemonster.sprite;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class MonsterSprite extends BadnessBoxSprite {

    private Goop goop;

    public MonsterSprite(int x, int y) {
        initMonster(x, y);
    }

    private void initMonster(int x, int y) {

        this.x = x;
        this.y = y;

        goop = new Goop(x, y);

        Random random = new Random();
        String alienImg = "images/monster"+random.nextInt(10 - 1 + 1)+".png";
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
