package minesweeper;

import java.io.Serializable;

/// Parameters of a board.
/// Called dimensions because change in any of them always results in a new scoreboard.
public class GameDimensions implements Serializable
{
	private static final long serialVersionUID = -6845414872943679962L;
	
	public int width;
	public int height;
	public int mines;
	
	public GameDimensions(int width, int height, int mines)
	{
		this.width = width;
		this.height = height;
		this.mines = mines;
	}
	
	public boolean equals(Object o)
	{
		GameDimensions gd = (GameDimensions)o;
		if(gd == null)
			return false;
		return width == gd.width && height == gd.height && mines == gd.mines;
	}
	
	public int hashCode()
	{
		return width | (height << 8) | (mines << 16);
	}
}
