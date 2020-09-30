package spriteframework;

import javax.swing.*;

public abstract class MainFrame extends JFrame  {

    protected abstract AbstractBoard createBoard();
    
    public MainFrame(String t) {
          
        add(createBoard());
		
		setTitle(t);
		setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
    }
}
