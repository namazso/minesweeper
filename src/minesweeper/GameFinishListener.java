package minesweeper;

/// Interface for receiving game finish event from a Board.
public interface GameFinishListener
{
	public void gameFinished(boolean win, final GameDimensions dimensions);
}
