package minesweeper;

/// An abstract highscore manager, could be an online shared scoreboard.
public interface HighscoreManager
{
	public boolean isScoreHighscore(GameDimensions dims, int score);
	public void addHighscore(GameDimensions dims, ScoreEntry score);
	public ScoreEntry[] getHighscores(GameDimensions dims);
}
