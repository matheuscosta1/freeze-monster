package freezemonster.sprite;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class MonsterSprite extends BadnessBoxSprite {

    private Goop goop;
    private String monsterImage;
    private Integer monsterImageIndice;

    public MonsterSprite(int x, int y) {
        initMonster(x, y);
    }

    public int getMonsterImageIndice(){
        return this.monsterImageIndice;
    }

    private void initMonster(int x, int y) {

        this.x = x;
        this.y = y;

        goop = new Goop(x, y);

        Random random = new Random();
        monsterImageIndice = random.nextInt(9 - 1 + 1);
        monsterImage = "images/monster"+monsterImageIndice+".png";
        ImageIcon ii = new ImageIcon(monsterImage);

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
