package spriteframework.sprite;

import spriteframework.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private int width;

    public Player(String player) {
        loadImage(player);
		getImageDimensions();
		resetState();
    }

    protected void loadImage (String player) {
        ImageIcon ii = new ImageIcon(player);
        width = ii.getImage().getWidth(null);
        setImage(ii.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH));
    }
    
    public void act() {

        x += dx;

        if (x <= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
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
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

    public void resetState() {

        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }
}
