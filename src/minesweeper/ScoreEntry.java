package minesweeper;

import java.io.Serializable;

/// A score entry
public class ScoreEntry implements Serializable
{
	private static final long serialVersionUID = 3820129662389983636L;
	
	public int score;
	public String name;
	
	public ScoreEntry(int score, String name)
	{
		this.score = score;
		this.name = name;
	}
	
	/// Get the score in mm:ss format
	public String scoreString()
	{
		return String.format("%02d:%02d", score / 60, score % 60);
	}
}
