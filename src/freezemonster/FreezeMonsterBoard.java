package freezemonster;


import freezemonster.sprite.Goop;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Shot;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;

public class FreezeMonsterBoard extends AbstractBoard{
    //define sprites
    //private List<BadSprite> aliens;
    private Shot shot;
    
    // define global control vars   
    private int direction = -1;
    private int deaths = 0;


    private String explImg = "images/explosion.png";

    public FreezeMonsterBoard(String image) {
        super(image);
    }

    protected void createBadSprites() {  // create sprites
        int m = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                MonsterSprite monster = new MonsterSprite((Commons.MONSTER_INIT_X + 18 * j * m),
                        (Commons.MONSTER_INIT_Y + 18 * i * m));
                badSprites.add(monster);
                m++;
            }
        }
    }
    
    protected void createOtherSprites() {
        shot = new Shot();
    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    // Override
    protected void drawOtherSprites(Graphics g) {
            drawShot(g);
    }
    
    protected void processOtherSprites(Player player, KeyEvent e) {
		int x = player.getX();
		int y = player.getY();

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {

			if (inGame) {

				if (!shot.isVisible()) {

					shot = new Shot(x, y);
				}
			}
		}
	}

    protected void update() {
        Random random = new Random();
        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        for (Player player : players)
            player.act();

        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (BadSprite monster : badSprites) {

                int alienX = monster.getX();
                int alienY = monster.getY();

                if (monster.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX) && shotX <= (alienX + Commons.MONSTER_WIDTH) && shotY >= (alienY) && shotY <= (alienY + Commons.MONSTER_HEIGHT)) {

                        ImageIcon ii = new ImageIcon("images/monster"+monster.getMonsterImageIndice()+"bg.png");
                        monster.setImage(ii.getImage().getScaledInstance(30, 50, Image.SCALE_DEFAULT));
                        monster.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (BadSprite monster : badSprites) {

            int x = monster.getX();
            int y = monster.getY();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {

                direction = -1;

                Iterator<BadSprite> i1 = badSprites.iterator();

                while (i1.hasNext()) {
                    BadSprite monster2 = i1.next();
                    if(monster2.isVisible()){
                        monster2.setY(monster2.getY() + random.nextInt(100 - 1 + 1));
                        monster2.setX(monster2.getX() + random.nextInt(100 - 1 + 1));
                    }
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {

                direction = 1;

                Iterator<BadSprite> i2 = badSprites.iterator();

                while (i2.hasNext()) {
                    BadSprite monster2 = i2.next();
                    if(monster2.isVisible()){
                        monster2.setY(monster2.getY() + random.nextInt(100 - 1 + 1));
                        monster2.setX(monster2.getX() + random.nextInt(100 - 1 + 1));
                    }
                }
            }
        }

        Iterator<BadSprite> it = badSprites.iterator();

        while (it.hasNext()) {

            BadSprite monster = it.next();

            if (monster.isVisible()) {

                int y = monster.getY();

                if (y > Commons.GROUND - Commons.MONSTER_HEIGHT) {
//                    inGame = false;
//                    message = "Invasion!";
                    ;
                }

                monster.moveX(direction);
                monster.moveY(direction);
            }
        }

        // bombs
        
        updateOtherSprites();
    }

    protected void updateOtherSprites() {
		Random generator = new Random();

        for (BadSprite monster : badSprites) {

            int shot = generator.nextInt(15);
            Goop goop = ((MonsterSprite)monster).getBomb();

            if (shot == Commons.CHANCE && monster.isVisible() && goop.isDestroyed()) {

                goop.setDestroyed(false);
                goop.setX(monster.getX());
                goop.setY(monster.getY());
            }

            int bombX = goop.getX();
            int bombY = goop.getY();

            int monsterX = monster.getX();
            int monsterY = monster.getY();

            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !goop.isDestroyed()) {

                if (
                        ((bombX >= (playerX))
                                && (bombX <= (playerX + Commons.PLAYER_WIDTH))
                                && (bombY >= (playerY))
                                && (bombY <= (playerY + Commons.PLAYER_HEIGHT)))
                        || ((monsterX >= (playerX))
                                && (monsterX <= (playerX + Commons.PLAYER_WIDTH))
                                && (monsterY >= (playerY))
                                && (monsterY <= (playerY + Commons.PLAYER_HEIGHT)))
                ) {

                    ImageIcon ii = new ImageIcon(explImg);
                    players.get(0).setImage(ii.getImage());
                    players.get(0).setDying(true);
                    goop.setDestroyed(true);
                }
            }

            if (!goop.isDestroyed()) {

                goop.setY(goop.getY() + 1);

                if (goop.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

                    goop.setDestroyed(true);
                }
            }
        }
	}

    @Override
    public void doDrawing(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(new Color(87, 161, 112));
        g.fillRect(0, 0, d.width, d.height);

        if (inGame) {

            g.drawLine(0, spriteframework.Commons.GROUND,
                    spriteframework.Commons.BOARD_WIDTH, spriteframework.Commons.GROUND);

            drawBadSprites(g);
            drawBadSprite(g);
            drawPlayers(g);
            drawOtherSprites(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
}

