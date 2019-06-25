package minesweeper.test;

import org.junit.*;

import minesweeper.*;

public class BoardTest
{
	boolean won;
	boolean happened;
	
	@Before
	public void reset()
	{
		won = false;
		happened = false;
	}
	
	public class BoardTestGameFinishListener implements GameFinishListener
	{
		@Override
		public void gameFinished(boolean win, GameDimensions dimensions)
		{
			happened = true;
			won = win;
		}
	}
	
	@Test
	public void testNumber()
	{
		Board board = new Board(new GameDimensions(2, 1, 1), new BoardTestGameFinishListener());
		Cell c = board.getCell(0, 0);
		if(c.isMine())
			c = board.getCell(1, 0);
		c.doReveal();
		Assert.assertEquals("1", c.getButton().getText());
	}
	
	@Test
	public void testWin()
	{
		Board board = new Board(new GameDimensions(2, 1, 1), new BoardTestGameFinishListener());
		Cell c = board.getCell(0, 0);
		if(c.isMine())
			c = board.getCell(1, 0);
		c.doReveal();
		Assert.assertEquals(true, happened);
		Assert.assertEquals(true, won);
	}

	@Test
	public void testLose()
	{
		Board board = new Board(new GameDimensions(2, 1, 1), new BoardTestGameFinishListener());
		Cell c = board.getCell(0, 0);
		if(!c.isMine())
			c = board.getCell(1, 0);
		c.doReveal();
		Assert.assertEquals(true, happened);
		Assert.assertEquals(false, won);
	}

	@Test
	public void testIllegalMineCount()
	{
		try
		{
			new Board(new GameDimensions(2, 1, 3), new BoardTestGameFinishListener());
			Assert.fail("no exception on illegal mine count");
		}
		catch (Exception e)
		{
		}
	}
}
