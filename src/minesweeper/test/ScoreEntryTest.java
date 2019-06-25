package minesweeper.test;

import org.junit.*;

import minesweeper.*;

public class ScoreEntryTest
{
	@Test
	public void testScoreConversion()
	{
		Assert.assertEquals(new ScoreEntry(1, "John").scoreString(), "00:01");
		Assert.assertEquals(new ScoreEntry(60, "John").scoreString(), "01:00");
		Assert.assertEquals(new ScoreEntry(61, "John").scoreString(), "01:01");
	}
}
