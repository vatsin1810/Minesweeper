/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	

    public void run() {
        
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);
        GameBoard gb = new GameBoard(40, 16, 16);
        frame.add(gb);
        Timer timer = new Timer(100, new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		frame.pack();
        	}
        });
        timer.start();
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}