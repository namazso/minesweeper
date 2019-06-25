package minesweeper;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/// Highscore manager that uses Java's builtin serialization to store highscores in a file.
public class FileBackedHighscoreManager implements HighscoreManager
{
	private HashMap<GameDimensions, ScoreEntry[]> map = new HashMap<GameDimensions, ScoreEntry[]>();
	private String filename;
	
	public FileBackedHighscoreManager(String filename)
	{
		this.filename = filename;
		load();
	}
	
	public void save()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();
			fos.close();
		}
		catch (IOException e)
		{
			// Not good but not fatal, print a stack trace
			e.printStackTrace();
		}
	}
	
	public void load()
	{
		try
		{
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			map = (HashMap<GameDimensions, ScoreEntry[]>)ois.readObject();
			ois.close();
			fis.close();
		}
		catch (IOException | ClassNotFoundException e)
		{
			// Not found / malformed / old save, just ignore and let it get overwritten by a new one
		}
	}
	
	/// Returns true if the given score would get in the top 10 places for the given dimensions.
	@Override
	public boolean isScoreHighscore(GameDimensions dims, int score)
	{
		return getHighscores(dims)[9].score > score;
	}

	/// Adds the given highscore to the specified dimensions' top 10.
	/// If the score isn't good enough nothing happens.
	@Override
	public void addHighscore(GameDimensions dims, ScoreEntry score)
	{
		ScoreEntry[] en = getHighscores(dims);
		int i;
		ScoreEntry old = null;
		for(i = 0; i < 10; ++i)
		{
			if(en[i].score > score.score)
			{
				old = en[i];
				en[i] = score;
				break;
			}
		}
		for(int j = i + 1; j < 10; ++j)
		{
			ScoreEntry tmp = en[j];
			en[j] = old;
			old = tmp;
		}
		save();
	}

	/// Get the top 10 scores for the given dimension.
	/// If there are no scores at all for the dimension, 10 dummy scores are generated.
	@Override
	public ScoreEntry[] getHighscores(GameDimensions dims)
	{
		ScoreEntry[] en = map.get(dims);
		if(en == null)
		{
			en = new ScoreEntry[10];
			map.put(dims, en);
			for(int i = 0; i < 10; ++i)
			{
				// Generate an easily beatable dummy score. Ignore the magic constants, some of these are measured. 
				int score = (int)(2 * dims.height * dims.width * (1 + (float)i / 10) * ((float)dims.mines / (dims.height * dims.width) / 0.2));
				
				// Pick a random name from the top 100 most common English names
				int name_idx = ThreadLocalRandom.current().nextInt(0, CommonEnglishNames.names.length);
				
				en[i] = new ScoreEntry(score, CommonEnglishNames.names[name_idx]);
			}
		}
		return en;
	}
}
