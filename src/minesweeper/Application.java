package minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// /Main application frame that hosts the main window.
public class Application extends JFrame implements GameFinishListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 754390673491159252L;
	JTextField width_input = new JTextField("16", 3);
	JTextField height_input = new JTextField("30", 3);
	JTextField mines_input = new JTextField("99", 5);
	
	GuiTimeCounter counter = new GuiTimeCounter();
	
	HighscoreManager manager;
	
	Board board;
	
	public Application(HighscoreManager manager)
	{
		this.manager = manager;
		
		setTitle("Minesweeper");
		
		JPanel control = new JPanel();
		
		control.add(new JLabel("W:"));
		control.add(width_input);
		control.add(new JLabel("H:"));
		control.add(height_input);
		control.add(new JLabel("M:"));
		control.add(mines_input);
		
		JButton button = new JButton("Reset");
		button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						makeBoard();
					}
				});
		control.add(button);
		
		getContentPane().add(control, BorderLayout.WEST);
		getContentPane().add(counter, BorderLayout.EAST);

		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/// Destroy any previous board and set up a new one with the current properties.
	public void makeBoard()
	{
		if(board != null) // We have an active board
		{
			getContentPane().remove(board);
			board = null;
		}
	
		try
		{
			int width = Integer.parseInt(width_input.getText());
			int height = Integer.parseInt(height_input.getText());
			int mines = Integer.parseInt(mines_input.getText());

			board = new Board(new GameDimensions(width, height, mines), this);
		}
		catch (Exception e)
		{
			// Erroneous user input, ignore whatever exception we got
		}
		finally
		{
			getContentPane().add(board, BorderLayout.SOUTH);
			counter.reset();
			counter.start();
		}
		
		pack();
	}

	/// Our current board's callback about the user winning or losing.
	@Override
	public void gameFinished(boolean won, GameDimensions dims)
	{
		counter.stop();
		int score = counter.getSeconds();

		if(won && manager.isScoreHighscore(dims, score)) // User scored a highscore, ask for their name
			new HighscoreNameEntryFrame(dims, score, manager);
		else // User lost or didn't have a top score, just show the highscore board
			new HighscoreFrame(manager.getHighscores(dims));
	}
	
	/// Entry point.
	public static void main(String[] args)
	{
		Application app = new Application(new FileBackedHighscoreManager("top.dat"));
		app.makeBoard();
	}
}