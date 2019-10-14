import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameBoard extends JPanel{
	private int mines;
	private int covered;
	private Block[][] board;
	private int width;
	private int height;
	private long startTime;
	private JPanel game = new JPanel();
	private JPanel topPane = new JPanel();
	private JButton newGame = new JButton("New Game");
	private JButton instructions = new JButton("Instructions");
	private JLabel time = new JLabel("Time: 0");
	private JLabel coveredBlocks = new JLabel("Covered: " + covered);
	private Random gen = new Random();
	
	private Timer timer = new Timer(30, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            tick();
        }
    });
	
	private MouseListener mo = (new MouseAdapter() {
			@Override
			public void mousePressed (MouseEvent e) {
				Point p = e.getPoint();
				int x = p.x/25;
				int y = p.y/25;
				if (SwingUtilities.isRightMouseButton(e) && board[x][y].getCovered()) 
					board[x][y].setFlagged(!board[x][y].getFlagged());
				else if (!SwingUtilities.isRightMouseButton(e) 
						&& board[x][y].getCovered() && !board[x][y].getFlagged()) {
					if (board[x][y].getIsMine()) 
						endGame(false);
					else {
						board[x][y].setCovered(false);
						clearBlocks(x, y);
					}
				}	
			}
		});
	
	GameBoard (int mines, int width, int height) {
		
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		
		instructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayInstructions();
			}
		});
		
		displayInstructions();
		reset(mines, width, height);
		
	}
	
	public void clearBlocks(int x, int y) {
		if (board[x][y].getNeighbours() == 0 && !board[x][y].getIsMine()) {
			board[x][y].setCovered(false);
			covered--;
			for (int i = Math.max(0, x-1); i <= Math.min(x+1, width-1); i++) {
				for (int j = Math.max(0, y-1); j <= Math.min(y+1, height-1); j++) {
					if (!(i==x && j==y) && board[i][j].getCovered()) {
						clearBlocks(i, j);
					}	
				}
			}
		} else if (!board[x][y].getIsMine()) {
			board[x][y].setCovered(false);
			covered--;
		}
		if (covered <= mines)
			endGame(true);
	}
	
	public void tick() {
		time.setText("Time: " + (System.currentTimeMillis() - startTime)/1000);
		coveredBlocks.setText("Covered: " + covered);
	}
	
	public void endGame(boolean won) {
		timer.stop();
		game.removeMouseListener(mo);
		if (won) {
			long gameTime = (System.currentTimeMillis() - startTime)/1000;
			String inputname = JOptionPane.showInputDialog(this, "You Won! Please enter your nick"
					+ "name", "Game Ended", JOptionPane.PLAIN_MESSAGE);
			BufferedWriter w = null;
			BufferedReader r = null;
			File f = new File("files/HighScores.txt");
			LinkedList<String> l = new LinkedList<String>();
			try {
				r = new BufferedReader(new FileReader(f));
				String temp;
				while (true) {
					temp = r.readLine();
					if (temp == null) {
						l.add(inputname + "," + gameTime);
						break;
					} else if (Integer.parseInt(temp.substring(temp.lastIndexOf(",") + 1)) > gameTime) {
						l.add(inputname + "," + gameTime);
						l.add(temp);
						while ((temp = r.readLine()) != null)
							l.add(temp);
						break;
					} else {
						l.add(temp);
					}
				}
				r.close();
				w = new BufferedWriter(new FileWriter(f));
				Iterator<String> it = l.iterator();
				while(it.hasNext()) {
					w.write(it.next());
					w.newLine();
				}
				w.close();
				String scores = "";
				r = new BufferedReader(new FileReader(f));
				for (int i = 0; i < 5; i++) {
					if ((temp = r.readLine()) == null)
						break;
					scores = scores + temp + "\n";
				}
				
				JOptionPane.showMessageDialog(this, "Top Times: \n" + scores, "Top Times", 
						JOptionPane.INFORMATION_MESSAGE);
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Oops! Something went wrong!", "Error", 
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			
		} else {
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					if (board[j][i].getIsMine()) {
						board[j][i].setFlagged(false);
						board[j][i].setCovered(false);
					}
				}
			}
			repaint();
			JOptionPane.showMessageDialog(this, "You Lost!", "Game Ended", 
					JOptionPane.DEFAULT_OPTION);
		}
		Object[] options = {"Yes", "No"};
		int m = JOptionPane.showOptionDialog(this, "Do you want to start a New Game?", "Game Ended", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		if (m == JOptionPane.YES_OPTION) {
			newGame();
		}
	}
	
	public void reset(int mines, int width, int height) {
		game.addMouseListener(mo);
		
		this.removeAll();
		game.removeAll();
		topPane.removeAll();
		
		this.mines = mines;
		this.width = width;
		this.height = height;
		covered = width*height;
		board = new Block[width][height];
		this.setLayout(new BorderLayout());
		game.setLayout(new GridLayout(width, height, 0, 0));
		topPane.setLayout(new FlowLayout());
		
		topPane.add(instructions);
		topPane.add(newGame);
		topPane.add(time);
		topPane.add(coveredBlocks);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[j][i] = new Block(false);
				game.add(board[j][i]);
			}
		}
		
		int s = 0;
		while (s < mines) {
			int w = gen.nextInt(width);
			int h = gen.nextInt(height);
			if (!board[w][h].getIsMine()) {
				board[w][h].setIsMine(true);
				board[w][h].setNeighbours(-1);
				s++;
			}
		}
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if (!board[j][i].getIsMine()) {
					int neighbours = 0;
					for (int k = Math.max(0, i-1); k <= Math.min(i+1, height-1); k++) {
						for (int l = Math.max(0, j-1); l <= Math.min(j+1, width-1); l++) {
							Block b2 = board[l][k];
							if (b2 != null && b2.getIsMine())
								neighbours++;
						}
					}
					board[j][i].setNeighbours(neighbours);
				}
			}
		}
		
		while (true) {
			int w = gen.nextInt(width);
			int h = gen.nextInt(height);
			if (board[w][h].getNeighbours() == 0) {
				board[w][h].setCovered(false);
				clearBlocks(w, h);
				break;
			}
		}
		
		startTime=System.currentTimeMillis();
		this.add(game, BorderLayout.SOUTH);
		this.add(topPane, BorderLayout.NORTH);
		this.setSize(25*width, 25*height+50);
		repaint();
		timer.start();
	}
	
	public void newGame() {
		Object[] options = {"Yes", "No"};
		int min, wid, hei;
		int i = JOptionPane.showOptionDialog(game, "Are you sure you want to a "
				+ "start a new game? Your progress will not be saved.", "New Game", 
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		if(i == JOptionPane.YES_OPTION) {
			String m = JOptionPane.showInputDialog(game, "Enter the width of the board", 
					"New Game", JOptionPane.QUESTION_MESSAGE);
			while(true) {
				try {
					wid = Integer.parseInt(m);
					if (wid < 1)
						throw new NumberFormatException();
					break;
				} catch(NumberFormatException n) {}
				m = JOptionPane.showInputDialog(game, "Please enter a valid width", 
						"New Game", JOptionPane.QUESTION_MESSAGE);
			}
			m = JOptionPane.showInputDialog(game, "Enter the height of the board", 
					"New Game", JOptionPane.QUESTION_MESSAGE);
			while(true) {
				try {
					hei = Integer.parseInt(m);
					if (hei < 1)
						throw new NumberFormatException();
					break;
				} catch(NumberFormatException n) {}
				m = JOptionPane.showInputDialog(game, "Please enter a valid height", 
						"New Game", JOptionPane.QUESTION_MESSAGE);
			}
			m = JOptionPane.showInputDialog(game, "Enter the number of mines. The number of "
					+ "mines should be less than the number of blocks.", 
					"New Game", JOptionPane.QUESTION_MESSAGE);
			while(true) {
				try {
					min = Integer.parseInt(m);
					if ((double) min > width * height || min < 1)
						throw new NumberFormatException();
					break;
				} catch(NumberFormatException n) {}
				m = JOptionPane.showInputDialog(game, "Please enter a valid number of mines", 
						"New Game", JOptionPane.QUESTION_MESSAGE);
			}
			reset(min, wid, hei);
		}

	}
	
	public void displayInstructions() {
		JOptionPane.showMessageDialog(this, "Hello, welcome to Minesweeper! \nInstructions: \n"
				+ "Blocks with a number indicate the number of mines directly adjacent to them \n"
				+ "Blocks without a number of no mines directly adjacent to them \n"
				+ "Uncover all the blocks without mines to win \n"
				+ "Be Careful! Uncovering a mine will end the game \n"
				+ "Controls: \n"
				+ "Use the left mouse button to uncover blocks \n"
				+ "Use the right mouse button to flag blocks \n"
				+ "Try to solve the game in the lowest time for a spot in the top 5!", 
				"Instructions Window", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void setMine (int x, int y) {
		if (!board[x][y].getIsMine()) {
			board[x][y].setIsMine(true);
			mines++;
			board[x][y].setNeighbours(-1);
			for (int i = Math.max(0, x-1); i <= Math.min(x+1, width-1); i++) {
				for (int j = Math.max(0, y-1); j <= Math.min(y+1, height-1); j++) {
					if (!(i==x && j==y) && !board[i][j].getIsMine()) {
						board[i][j].setNeighbours(board[i][j].getNeighbours()+1);
					}	
				}
			}
		}
		
	}
	
	public boolean isMine (int x, int y) {
		return board[x][y].getIsMine();
	}
	
	public boolean isCovered (int x, int y) {
		return board[x][y].getCovered();
	}
	
	public void uncover (int x, int y) {
		board[x][y].setCovered(false);
		clearBlocks(x, y);
	}
	
	public int getNeighbours (int x, int y) {
		return board[x][y].getNeighbours();
	}
	
	public boolean wonGame() {
		return covered == mines;
	}
	
	public int countCoveredMines() {
		int count = 0;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if (board[i][j].getIsMine())
					count++;
			}
		}
		return count;	
	}
	
	public void resetBoard() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				board[i][j].setIsMine(false);
				board[i][j].setNeighbours(0);
				board[i][j].setCovered(true);
			}
		}
	}
	
}
