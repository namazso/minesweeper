package minesweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/// Asks the user their name, then submits the given score.
public class HighscoreNameEntryFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8747876622306250418L;

	JTextField name;
	
	GameDimensions dims;
	HighscoreManager manager;
	int score;
	
	public HighscoreNameEntryFrame(GameDimensions dims, int score, HighscoreManager manager)
	{
		this.dims = dims;
		this.score = score;
		this.manager = manager;
		
		JPanel panel = new JPanel(new GridLayout(0, 2));
		
		this.setTitle("Please enter your name");

		panel.add(name = new JTextField(20));
		JButton button = new JButton("Ok");
		button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						ok();
					}
				});
		panel.add(button);

		this.add(panel);

		this.pack();
		// Maybe we should show the scoreboard even if the user closes the name entry box?
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void ok()
	{
		manager.addHighscore(dims, new ScoreEntry(score, name.getText()));
		new HighscoreFrame(manager.getHighscores(dims));
		this.dispose();
	}
}
