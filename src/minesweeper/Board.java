package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/// A game board with already set up parameters.
public class Board extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3077418121120939830L;

	/// In [y][x] order.
	private Cell[][] cells;

	private final GameDimensions dims;
	private GameFinishListener listener;
	
	/// To prevent multiple losses when revealing the board because of a loss.
	boolean game_finished = false;
	
	public Board(final GameDimensions dims, GameFinishListener listener)
	{
		super(new GridLayout(dims.height, dims.width));

		this.dims = dims;
		this.listener = listener;
		
		if(dims.mines > dims.width * dims.height) // Can't have more mines than cells
			throw new IllegalArgumentException();
		
		makeCells();
		makeMines();
		updateCells();
	}
	
	/// Make the cells and add their buttons to the grid.
	private void makeCells()
	{
		cells = new Cell[dims.height][dims.width];
		for (int y = 0; y < dims.height; ++y)
			for (int x = 0; x < dims.width; ++x)
				this.add((cells[y][x] = new Cell(this, x, y)).getButton());
	}

	/// Pick random fields and turn them into mines.
	private void makeMines()
	{
		for (int i = 0; i < dims.mines; ++i)
			while (!getCell(
					ThreadLocalRandom.current().nextInt(0, dims.width),
					ThreadLocalRandom.current().nextInt(0, dims.height))
					.trySetMine())
				;
	}

	/// (Re)calculate the numbers in the non-mine fields based on neighboring mines.
	private void updateCells()
	{
		for (Cell[] y : cells)
			for (Cell x : y)
				x.updateValue();
	}

	/// Returns cell at coordinates, or null if out of bounds.
	public Cell getCell(int x, int y)
	{
		if (x < 0 || x >= dims.width)
			return null;

		if (y < 0 || y >= dims.height)
			return null;

		return cells[y][x];
	}
	
	/// Game ended either by winning or losing, call the listener.
	public void finishGame(boolean win)
	{
		listener.gameFinished(win, dims);
	}

	/// User clicked a mine, certainly lost.
	public void lose()
	{
		if(game_finished)
			return;
		
		game_finished = true;
		
		for (Cell[] y : cells)
			for (Cell x : y)
				x.doReveal();
		
		finishGame(false);
	}
	
	/// User opened a non-mine field, might have won. 
	public void checkWin()
	{
		if(game_finished)
			return;
		
		for (Cell[] y : cells)
			for (Cell x : y)
				if(!x.isMine() && !x.isRevealed())
					return;

		game_finished = true;
		
		finishGame(true);
	}
}