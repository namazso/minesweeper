package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell
{
	/// "BLACK SUN WITH RAYS" U+2600 kinda looks like a mine.
	private static final String MINE_TEXT = "\u2600";
	/// Literally just an empty string.
	private static final String EMPTY_TEXT = "";
	/// "TRIANGULAR FLAG ON POST" U+1F6A9 too bad java sucks at non-BMP unicode.
	private static final String FLAG_TEXT = new String(Character.toChars(0x1F6A9));

	/// Could be anything that is not 0 - 8.
	private static final int MINE_VALUE = -1;

	/// Our board.
	private Board board;
	/// Our X position on board.
	private int x;
	/// Our Y position on board.
	private int y;

	/// Count of mine neighbors.
	private int value = 0;
	/// User have opened the cell, can see value.
	private boolean revealed = false;
	/// User put a flag on the cell - this is purely cosmetic, doesn't affect gameplay.
	private boolean flagged = false;

	private JButton button;

	public Cell(Board board, int x, int y)
	{
		this.board = board;
		this.x = x;
		this.y = y;

		button = new JButton();
		button.setPreferredSize(new Dimension(20, 20));
		button.setMargin(new Insets(0, 0, 0, 0));

		// Turns out you have to do all this to get right-clicks work with a JButton
		button.addMouseListener(new MouseAdapter()
		{
			boolean pressed;

			@Override
			public void mousePressed(MouseEvent e)
			{
				button.getModel().setArmed(true);
				button.getModel().setPressed(true);
				pressed = true;
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				button.getModel().setArmed(false);
				button.getModel().setPressed(false);

				if (pressed)
				{
					if (SwingUtilities.isRightMouseButton(e))
						flipFlag();
					else
						if (!flagged) // Don't allow clicking flagged cells, it is probably accidental
							doReveal();
				}
				pressed = false;

			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				pressed = false;
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				pressed = true;
			}
		});
	}

	/// Flagging / unflagging a cell.
	private void flipFlag()
	{
		if (!revealed) // Don't allow flagging already revealed cells
		{
			flagged = !flagged;
			button.setText(flagged ? FLAG_TEXT : EMPTY_TEXT);
		}
	}

	/// Get button to add to grid.
	public JButton getButton()
	{
		return button;
	}

	/// (Re)calculate display value from neighbor mines.
	public void updateValue()
	{
		if (value != MINE_VALUE) // Mines don't have numbers
		{
			value = 0;
			for (int i = x - 1; i < x + 2; ++i)
				for (int j = y - 1; j < y + 2; ++j)
				{
					Cell cell = board.getCell(i, j);
					if (cell != null)
						value += cell.isMine() ? 1 : 0;
				}
		}
	}

	public boolean isMine()
	{
		return this.value == MINE_VALUE;
	}

	public boolean trySetMine()
	{
		if (isMine())
			return false;
		this.value = MINE_VALUE;
		return true;
	}

	public boolean isRevealed()
	{
		return revealed;
	}

	public void doReveal()
	{
		if (revealed) // Prevent infinite recursion by two 0s 
			return;
		
		revealed = true;
		
		button.setEnabled(false);
		if (value == MINE_VALUE)
		{
			button.setText(MINE_TEXT);
			button.setBackground(Color.RED);
			board.lose();
		}
		else
		{
			if (value == 0) // Reveal all neighbors of 0 fields
			{
				button.setText(EMPTY_TEXT); // 0 appears as empty
				for (int i = x - 1; i < x + 2; ++i)
					for (int j = y - 1; j < y + 2; ++j)
					{
						Cell cell = board.getCell(i, j);
						if (cell != null)
							cell.doReveal();
					}
			}
			else
			{
				button.setText(String.valueOf(value));
			}
			board.checkWin();
		}
	}
}