package minesweeper;

import java.awt.GridLayout;

import javax.swing.*;

// /Displays a set of highscore entries.
public class HighscoreFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5563154763300330241L;

	public HighscoreFrame(ScoreEntry[] se)
	{
		JPanel panel = new JPanel(new GridLayout(se.length, 2));
		
		setTitle("Top 10");

		for(ScoreEntry s : se)
		{
			panel.add(new JLabel(s.name));
			panel.add(new JLabel(s.scoreString()));
		}
		
		this.add(panel);

		this.pack();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
