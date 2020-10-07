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
    private int direction = 0;
    private int deaths = 0;


    private String explImg = "images/explosion.png";

    public FreezeMonsterBoard(String image) {
        super(image);
    }

    protected void createBadSprites() {  // create sprites
        for (int i = 0; i < Commons.NUMBER_OF_ALIENS_TO_DESTROY; i++) {

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

                if (!monster.isDyingvisible() && shot.isVisible()) {
                    if (shotX >= (alienX) && shotX <= (alienX + Commons.MONSTER_WIDTH) && shotY >= (alienY) && shotY <= (alienY + Commons.MONSTER_HEIGHT)) {

                        ImageIcon ii = new ImageIcon("images/monster"+monster.getMonsterImageIndice()+"bg.png");
                        monster.setImage(ii.getImage().getScaledInstance(30, 50, Image.SCALE_DEFAULT));
                        monster.setDyingvisible(true);
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

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && !monster.isDyingvisible()) {

                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setDirecaomonstro(2);
                }
                if(temp == 1) {
                    monster.setDirecaomonstro(7);
                }
                if(temp == 2) {
                    monster.setDirecaomonstro(3);
                }

            }

            if (x <= Commons.BORDER_LEFT && !monster.isDyingvisible()) {
                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setDirecaomonstro(4);
                }
                if(temp == 1) {
                    monster.setDirecaomonstro(5);
                }
                if(temp == 2) {
                    monster.setDirecaomonstro(1);
                }
            }
            if (y >= Commons.BOARD_HEIGHT - 50 && !monster.isDyingvisible()) {
                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setDirecaomonstro(2);
                }
                if(temp == 1) {
                    monster.setDirecaomonstro(8);
                }
                if(temp == 2) {
                    monster.setDirecaomonstro(4);
                }
            }

            if (y <= 0 && !monster.isDyingvisible()) {

                int temp = new Random().nextInt(3);
                if(temp == 0) {
                    monster.setDirecaomonstro(3);
                }
                if(temp == 1) {
                    monster.setDirecaomonstro(6);
                }
                if(temp == 2) {
                    monster.setDirecaomonstro(1);
                }
            }
        }

        Iterator<BadSprite> it = badSprites.iterator();

        while (it.hasNext()) {

            BadSprite monster = it.next();
            if(monster.getDirecaomonstro()==0) {
                monster.setDirecaomonstro(new Random().nextInt(8)+1);
            }
            if (!monster.isDyingvisible()) {

                int y = monster.getY();


                if(monster.getDirecaomonstro() == 1) {
                    monster.moveX(1);
                    monster.moveY(1);
                }
                if(monster.getDirecaomonstro() == 2) {
                    monster.moveX(-1);
                    monster.moveY(-1);
                }
                if(monster.getDirecaomonstro() == 3) {
                    monster.moveX(-1);
                    monster.moveY(1);
                }
                if(monster.getDirecaomonstro() == 4) {
                    monster.moveX(1);
                    monster.moveY(-1);
                }
                if(monster.getDirecaomonstro() == 5) {
                    monster.moveX(1);
                }
                if(monster.getDirecaomonstro() == 6) {
                    monster.moveY(1);
                }
                if(monster.getDirecaomonstro() == 7) {
                    monster.moveX(-1);
                }
                if(monster.getDirecaomonstro() == 8) {
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

            if(goop.isDirecao() == 0) {
                goop.setDirecao(new Random().nextInt(8)+1);
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

                if (goop.isDirecao() == 1) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirecao() == 2) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirecao() == 3) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirecao() == 4) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirecao() == 5) {
                    goop.setY(goop.getY());
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirecao() == 6) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX());
                }
                if (goop.isDirecao() == 7) {
                    goop.setY(goop.getY());
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirecao() == 8) {
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

