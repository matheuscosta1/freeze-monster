package freezemonster;


import freezemonster.sprite.Goop;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Shot;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.PlayerBilateral;
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
    private int direc = 0;
    private int deaths = 0;


    private String explImg = "images/explosion.png";

    public FreezeMonsterBoard(String image) {
        super(image);
    }

    protected void createBadSprites() {  // create sprites
        for (int i = 0; i < Commons.NUMBER_OF_MONSTERS_TO_DESTROY; i++) {

            MonsterSprite monster = new MonsterSprite(Commons.MONSTER_INIT_X + 18 * (int)(Math.random()*10) ,
                    Commons.MONSTER_INIT_Y + 18 * (int)(Math.random()*10), i);
            badSprites.add(monster);
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
        if (deaths == Commons.NUMBER_OF_MONSTERS_TO_DESTROY) {
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

                int monsterX = monster.getX();
                int monsterY = monster.getY();

                if (!monster.isDyingVisible() && shot.isVisible()) {
                    if (shotX >= (monsterX) && shotX <= (monsterX + Commons.MONSTER_WIDTH) && shotY >= (monsterY) && shotY <= (monsterY + Commons.MONSTER_HEIGHT)) {

                        ImageIcon ii = new ImageIcon("images/monster"+monster.getMonsterImageIndice()+"bg.png");
                        monster.setImage(ii.getImage().getScaledInstance(Commons.GOOP_WIDTH, Commons.GOOP_HEIGHT, Image.SCALE_DEFAULT));
                        monster.setDyingVisible(true);
                        monster.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            int x = shot.getX();

            if((players.get(0).getDy()==-2 && players.get(0).getDx()==0 && direc == 0) || direc == 1 ) { //tiro para cima
                y -= 4;
                direc = 1;
            }
            if((players.get(0).getDy()==2 && players.get(0).getDx()==0 && direc == 0) || direc == 2 ) { //tiro para baixo
                y += 4;
                direc = 2;
            }
            if((players.get(0).getDy()==0 && players.get(0).getDx()==2 && direc == 0) || direc == 3 ) { //tiro para direita
                x += 4;
                direc = 3;
            }
            if((players.get(0).getDy()==0 && players.get(0).getDx()==-2 && direc == 0) || direc == 4 ) { //tiro para esquerda
                x = x - 4;
                direc = 4;
            }

            if (y < 0 || y >= Commons.BOARD_HEIGHT || x < 0 || x >= Commons.BOARD_WIDTH) {
                shot.die();
                direc = 0;
            } else {
                shot.setY(y);
                shot.setX(x);
            }
        }

        // aliens

        for (BadSprite monster : badSprites) {

            int x = monster.getX();
            int y = monster.getY();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && !monster.isDyingVisible()) {

                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setMonsterDirection(2);
                }
                if(temp == 1) {
                    monster.setMonsterDirection(7);
                }
                if(temp == 2) {
                    monster.setMonsterDirection(3);
                }

            }

            if (x <= Commons.BORDER_LEFT && !monster.isDyingVisible()) {
                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setMonsterDirection(4);
                }
                if(temp == 1) {
                    monster.setMonsterDirection(5);
                }
                if(temp == 2) {
                    monster.setMonsterDirection(1);
                }
            }
            if (y >= Commons.BOARD_HEIGHT - 50 && !monster.isDyingVisible()) {
                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setMonsterDirection(2);
                }
                if(temp == 1) {
                    monster.setMonsterDirection(8);
                }
                if(temp == 2) {
                    monster.setMonsterDirection(4);
                }
            }

            if (y <= 0 && !monster.isDyingVisible()) {

                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setMonsterDirection(3);
                }
                if(temp == 1) {
                    monster.setMonsterDirection(6);
                }
                if(temp == 2) {
                    monster.setMonsterDirection(1);
                }
            }
        }

        Iterator<BadSprite> it = badSprites.iterator();

        while (it.hasNext()) {

            BadSprite monster = it.next();
            if(monster.getMonsterDirection()==0) {
                monster.setMonsterDirection(new Random().nextInt(8)+1);
            }
            if (!monster.isDyingVisible()) {

                int y = monster.getY();


                if(monster.getMonsterDirection() == 1) {
                    monster.moveX(1);
                    monster.moveY(1);
                }
                if(monster.getMonsterDirection() == 2) {
                    monster.moveX(-1);
                    monster.moveY(-1);
                }
                if(monster.getMonsterDirection() == 3) {
                    monster.moveX(-1);
                    monster.moveY(1);
                }
                if(monster.getMonsterDirection() == 4) {
                    monster.moveX(1);
                    monster.moveY(-1);
                }
                if(monster.getMonsterDirection() == 5) {
                    monster.moveX(1);
                }
                if(monster.getMonsterDirection() == 6) {
                    monster.moveY(1);
                }
                if(monster.getMonsterDirection() == 7) {
                    monster.moveX(-1);
                }
                if(monster.getMonsterDirection() == 8) {
                    monster.moveY(-1);
                }
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

            if(goop.isDirection() == 0) {
                goop.setDirection(new Random().nextInt(8)+1);
            }

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

            if (players.get(0).isVisible() && !goop.isDestroyed() && !monster.isDestroyed()) {

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

                if (goop.isDirection() == 1) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirection() == 2) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirection() == 3) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirection() == 4) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirection() == 5) {
                    goop.setY(goop.getY());
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirection() == 6) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX());
                }
                if (goop.isDirection() == 7) {
                    goop.setY(goop.getY());
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirection() == 8) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX());
                }


                if (goop.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT || goop.getY() <= 0 || goop.getX() <= 0 || goop.getX() >= Commons.BOARD_WIDTH) {

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

    @Override
    protected Player createPlayer(String player) {
        return new PlayerBilateral(player);
    }
}

