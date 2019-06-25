package minesweeper;

import java.awt.event.*;
import javax.swing.*;

/// A JLabel that counts time passed in seconds.
public class GuiTimeCounter extends JLabel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7235767619747912059L;

	private int seconds = 0;

	/// Run actionPerformed every second, if enabled.
	private Timer timer = new Timer(1000, this);
	
	/// Update the displayed string.
	/// Be sure to call this on any change of seconds.
	private void updateDisplay()
	{
		setText(String.format("%02d:%02d  ", seconds / 60, seconds % 60));
	}

	@Override
	/// Increase seconds every second.
	public void actionPerformed(ActionEvent e)
	{
		seconds++;
		updateDisplay();
	}
	
	/// Start the timer. Not just an enable/disable bool in the handler, to prevent sub-second differences.
	public void start()
	{
		timer.start();
	}
	
	/// Stop the timer. Not just an enable/disable bool in the handler, to prevent sub-second differences.
	public void stop()
	{
		timer.stop();
	}
	
	public int getSeconds()
	{
		return seconds;
	}
	
	/// Set timer second count to zero.
	public void reset()
	{
		seconds = 0;
		updateDisplay();
	}
}
