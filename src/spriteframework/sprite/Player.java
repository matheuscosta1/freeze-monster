package spriteframework.sprite;

import spriteframework.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private int width = 30;
    private int weight = 50;

    public Player(String player) {
        loadImage(player);
		getImageDimensions();
		resetState();
    }

    protected void loadImage (String player) {
        ImageIcon ii = new ImageIcon(player);
        setImage(ii.getImage().getScaledInstance(width, weight, Image.SCALE_DEFAULT));
    }
    
    public void act() {

        x += dx;
        y += dy;

        if (x <= 2) {

            x = 2;
        }
        if (y <= 2) {

            y = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }

        if (y >= Commons.BOARD_HEIGHT - 2 * weight) {

            y = Commons.BOARD_HEIGHT - 2 * weight;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {

            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 0;
        }
    }

    public void resetState() {

        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }
}
