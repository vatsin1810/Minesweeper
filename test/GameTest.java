import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;




import org.junit.Test;

public class GameTest {
	
	@Test
	public void testGameBoard1 () {
		GameBoard gb = new GameBoard(7, 4, 6);
		assertEquals(7, gb.countCoveredMines());
	}
	
	@Test
	public void testClearBlocks1 () {
		GameBoard gb = new GameBoard(4, 4, 4);
		gb.resetBoard();
		gb.setMine(1, 1);
		gb.uncover(3, 3);
		assertTrue(gb.isMine(1, 1));
		assertEquals(0, gb.getNeighbours(3, 2));
		assertFalse(gb.isCovered(3, 2));
		assertTrue(gb.isCovered(0, 0));
		assertTrue(gb.isCovered(1, 1));
	}
	
	@Test
	public void testClearBlocks2 () {
		GameBoard gb = new GameBoard(4, 3, 3);
		gb.resetBoard();
		gb.setMine(1, 2);
		gb.setMine(1, 1);
		gb.setMine(2, 1);
		gb.uncover(2, 2);
		assertFalse(gb.isCovered(2, 2));
		assertEquals(2, gb.getNeighbours(2, 2));
		assertTrue(gb.isCovered(0, 0));
		assertTrue(gb.isCovered(1, 2));
		assertTrue(gb.isCovered(2, 1));
	}
	
	@Test
	public void testClearBlocks3 () {
		GameBoard gb = new GameBoard(3, 4, 4);
		gb.resetBoard();
		gb.setMine(0, 0);
		gb.uncover(3, 3);
		assertFalse(gb.isCovered(3, 2));
		assertTrue(gb.isCovered(0, 0));
	}
	
	@Test
	public void testClearBlocks4 () {
		GameBoard gb = new GameBoard(3, 4, 4);
		gb.resetBoard();
		gb.setMine(1, 0);
		gb.setMine(0, 1);
		gb.uncover(3, 3);
		assertTrue(gb.isCovered(0, 0));
		assertTrue(gb.isCovered(1, 0));
		assertTrue(gb.isCovered(0, 1));
		assertFalse(gb.isCovered(3, 2));
	}
	
	@Test
	public void testNeighbours1 () {
		GameBoard gb = new GameBoard(3, 3, 3);
		gb.resetBoard();
		gb.setMine(0, 0);
		gb.setMine(1, 0);
		gb.setMine(2, 0);
		gb.setMine(2, 1);
		gb.setMine(2, 2);
		gb.setMine(1, 2);
		gb.setMine(0, 2);
		gb.setMine(0, 1);
		assertEquals(8, gb.getNeighbours(1, 1));
		assertEquals(-1, gb.getNeighbours(0, 0));
	}
	
	@Test
	public void testNeighbours2 () {
		GameBoard gb = new GameBoard(3, 3, 3);
		gb.resetBoard();
		gb.setMine(1, 0);
		gb.setMine(2, 1);
		gb.setMine(1, 2);
		gb.setMine(0, 1);
		assertEquals(4, gb.getNeighbours(1, 1));
		assertEquals(2, gb.getNeighbours(0, 0));
	}
	
	@Test
	public void testNeighbours3 () {
		GameBoard gb = new GameBoard(3, 4, 4);
		gb.resetBoard();
		gb.setMine(3, 3);
		gb.setMine(3, 2);
		gb.setMine(3, 1);
		gb.setMine(2, 0);
		assertEquals(2, gb.getNeighbours(2, 2));
		assertEquals(2, gb.getNeighbours(3, 0));
	}
	
	@Test
	public void wonGame1 () {
		GameBoard gb = new GameBoard(3, 3, 3);
		gb.resetBoard();
		gb.setMine(0, 0);
		gb.uncover(2, 2);
		assertTrue(gb.wonGame());
	}
	
	@Test
	public void wonGame2 () {
		GameBoard gb = new GameBoard(3, 3, 3);
		gb.resetBoard();
		gb.setMine(0, 0);
		gb.setMine(1, 0);
		gb.setMine(0, 1);
		gb.setMine(2, 1);
		gb.uncover(2, 2);
		assertFalse(gb.wonGame());
		gb.uncover(2, 0);
		assertTrue(gb.wonGame());
	}
}