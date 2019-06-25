package minesweeper.test;

import java.io.File;

import org.junit.*;

import minesweeper.*;

public class FileBackedHighscoreManagerTest
{
	private static final String test_file_name = "test.dat";
	
	@Before
	public void cleanupScoreboard()
	{
		try
		{
			new File(test_file_name).delete();
		}
		catch (Exception e)
		{
		}
	}
	
	@Test
	public void testGenerated()
	{
		FileBackedHighscoreManager m = new FileBackedHighscoreManager(test_file_name);
		ScoreEntry[] en = m.getHighscores(new GameDimensions(1, 1, 1));
		Assert.assertEquals(10, en[0].score);
	}
	
	@Test
	public void testScoreHighscore()
	{
		FileBackedHighscoreManager m = new FileBackedHighscoreManager(test_file_name);
		GameDimensions dims = new GameDimensions(1, 1, 1);
		ScoreEntry[] en = m.getHighscores(dims);
		Assert.assertEquals(true, m.isScoreHighscore(dims, en[0].score - 1));
	}

	@Test
	public void testOrder()
	{
		FileBackedHighscoreManager m = new FileBackedHighscoreManager(test_file_name);
		GameDimensions dims = new GameDimensions(10, 10, 99);
		m.addHighscore(dims, new ScoreEntry(1, "1"));
		m.addHighscore(dims, new ScoreEntry(3, "3"));
		ScoreEntry[] en = m.getHighscores(dims);
		Assert.assertEquals(1, en[0].score);
		Assert.assertEquals(3, en[1].score);
		m.addHighscore(dims, new ScoreEntry(2, "2"));
		Assert.assertEquals(1, en[0].score);
		Assert.assertEquals(2, en[1].score);
		Assert.assertEquals(3, en[2].score);
	}
	
	@Test
	public void testSaveLoad()
	{
		FileBackedHighscoreManager m = new FileBackedHighscoreManager(test_file_name);
		GameDimensions dims = new GameDimensions(10, 10, 99);
		m.addHighscore(dims, new ScoreEntry(1, "1"));
		m = new FileBackedHighscoreManager(test_file_name);
		ScoreEntry[] en = m.getHighscores(dims);
		Assert.assertEquals(1, en[0].score);
	}
}