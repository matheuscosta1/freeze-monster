package spriteframework.sprite;

import spriteframework.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    protected int width = Commons.PLAYER_WIDTH;
    protected int weight = Commons.PLAYER_HEIGHT;
    private String player;

    public Player(String image) {
        loadImage(image);
		getImageDimensions();
		resetState();
    }

    protected void loadImage(String image) {
        ImageIcon ii = new ImageIcon(image);
        setImage(ii.getImage().getScaledInstance(width, weight, Image.SCALE_DEFAULT));
        player = image.split("/")[1];
    }
    
    public void act() {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void resetState() {

        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }
}
