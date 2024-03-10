package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import Utility.*;

public class LeaderboardForm extends JFrame
{
    private Stack<JFrame> _forms;
    private String _filePath;
    private PriorityQueue<Score> _scores;
    private Font _font;

    /* Constructors */
    public LeaderboardForm(Stack<JFrame> forms)
    {
        _forms = forms;
        this.InitFrame();
        this.InitButtons();
        this.InitScores();
        this.InitFont();

        this.DisplayScores();
    }

    /* Initialization */
    private void InitFrame()
    {
        int WINDOW_WIDTH = 1100;
        int WINDOW_HEIGHT = 700;

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void InitButtons()
    {
        JButton btnMainMenu = new JButton("Main Menu");
        btnMainMenu.setBounds((this.getWidth() / 2) - 150, 100, 300, 50);
        this.add( btnMainMenu );

        btnMainMenu.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                _forms.pop();
            }
        });
    }

    private void InitScores()
    {
        _filePath = "Resources/leaderboard.txt";
        _scores = new PriorityQueue<>(1, new Comparator<Score>()
        {
            @Override
            public int compare(Score s1, Score s2)
            {
                return s2.GetValue() - s1.GetValue();
            }
        });
    }

    private void InitFont()
    {
        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Resources/orange_juice.ttf")));
        }
        catch (IOException|FontFormatException e)
        {
            e.printStackTrace();
        }

        _font = new Font("orange_juice", Font.PLAIN, 20);
    }

    /* Display Leaderboard */
    private void ReadScoresFromFile()
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

    private void DisplayScores()
    {
        this.ReadScoresFromFile();

        int x_pos = (this.getWidth() / 2);

        JLabel topTen = new JLabel("TOP TEN");
        topTen.setFont(_font.deriveFont(Font.BOLD, 50));
        topTen.setBounds( x_pos - 110, 20, 250, 50);
        this.add(topTen);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont( _font );
        nameLabel.setBounds( x_pos - 100, 150, 100, 50);
        this.add(nameLabel);

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setFont( _font );
        scoreLabel.setBounds( x_pos + 50, 150, 100, 50);
        this.add(scoreLabel);


        int y_pos = 190;
        int index = 0;

        if (_scores.isEmpty())
        {
            JLabel empty = new JLabel("Leaderboard is empty");
            empty.setBounds( x_pos - 65, y_pos, 200, 50 );
            this.add(empty);
            return;
        }

        while ( !_scores.isEmpty() && index < 10 )
        {
            Score tmp = _scores.poll();
            this.AddScoreLabel( tmp.GetName(), tmp.GetValue(), x_pos, y_pos);
            y_pos += 40;
            index++;
        }

    }

    private void AddScoreLabel(String n, int v, int x_pos, int y_pos)
    {
        JLabel name =  new JLabel(n);
        name.setFont( _font );
        name.setBounds(x_pos - 100, y_pos, 50, 50);
        this.add(name);

        JLabel score = new JLabel( String.valueOf(v) );
        score.setFont( _font );
        score.setBounds(x_pos + 50, y_pos, 50, 50);
        this.add(score);
    }

}
