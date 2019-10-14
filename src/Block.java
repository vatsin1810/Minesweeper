import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Block extends JComponent {
	private int neighbours;
	private boolean isMine;
	private boolean covered = true;
	private boolean flagged = false;
	
	Block (boolean isMine) {
		this.isMine = isMine;
	}
	
	public void setNeighbours (int neighbours) {
		this.neighbours = neighbours;
	}
	
	public void setIsMine (boolean isMine) {
		this.isMine = isMine;
	}
	
	public void setCovered (boolean covered) {
		this.covered = covered;
		repaint();
	}
	
	public void setFlagged (boolean flagged) {
		this.flagged = flagged;
		repaint();
	}
	
	public int getNeighbours () {
		return neighbours;
	}
	
	public boolean getIsMine () {
		return isMine;
	}
	
	public boolean getCovered () {
		return covered;
	}
	
	public boolean getFlagged () {
		return flagged;
	}
	
	private BufferedImage readImage (String name) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(name));
		} catch (IOException e) {}
		return img;
	}
	
	@Override
	protected void paintComponent (Graphics g) {
		g.drawRect(0, 0, 25, 25);
		if (covered) {
			if (flagged) 
				g.drawImage(readImage("files/flag.png"), 0, 0, null);
			else 
				g.drawImage(readImage("files/unopened.png"), 0, 0, null);
		} else {
			if (isMine)
				g.drawImage(readImage("files/mine.jpeg"), 0, 0, null);
			else
				g.drawImage(readImage("files/" + neighbours + ".png"), 0, 0, null);
		}
			
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(25, 25);
	}

}
