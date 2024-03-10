package Utility;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Score
{
    private String _name;
    private int _value;
    private String _filePath;
    private PriorityQueue<Score> _scores;

    /* Constructors */
    public Score(String name, int value)
    {
        _name = name;
        _value = value;
    }

    /* Save Score */
    public void SaveScore()
    {
        this.InitScores();
        this.ReadScores();

        _scores.add( new Score( _name, _value ) );
        this.SaveToFile();
    }

    private void InitScores()
    {
        _filePath = "TetrisGame/Resources/leaderboard.txt";
        _scores = new PriorityQueue<>(1, new Comparator<Score>()
        {
            @Override
            public int compare(Score s1, Score s2)
            {
                return s2.GetValue() - s1.GetValue();
            }
        });
    }

    private void ReadScores()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader(_filePath) );
            String line;

            while ( ( line = reader.readLine() ) != null )
            {
                String[] data = line.split(" ");
                if (data.length >= 2)
                {
                    _scores.add( new Score( data[0], Integer.parseInt( data[1] ) ) );
                }
            }

            reader.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    private void SaveToFile()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(_filePath));

            while ( !_scores.isEmpty() )
            {
                writer.write(_scores.peek().GetName() + " " + _scores.peek().GetValue() + "\n");
                _scores.poll();
            }


            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    /* Getters */
    public String GetName()
    {
        return _name;
    }

    public int GetValue()
    {
        return _value;
    }

    @Override
    public String toString()
    {
        return String.format(_name + " " + _value) ;
    }
}

