package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class StartupForm extends JFrame
{
    /* Variables */
    private Font _font;
    private Stack<JFrame> _forms;
    private ArrayList<Integer> _settings;

    public StartupForm( Stack<JFrame> forms)
    {
        _forms = forms;
        _settings = new ArrayList<>();
        this.InitFont();
        this.InitSettings();
        this.InitLogo();
        this.InitFrame();
        this.InitButtons();
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
        int btnHeight = 50;
        int btnWidth = 300;
        int btnPos_x = ( this.getWidth() / 2 ) - ( btnWidth / 2 );
        int btnPos_y = 200;
        int nextPos_y = 75;

        JButton startGame = new JButton("Start Game");
        startGame.setBounds( btnPos_x, btnPos_y, btnWidth, btnHeight );
        btnPos_y += nextPos_y;

        startGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                _forms.add( new GameForm( _forms, _settings ) );
            }
        });

        JButton leaderboard = new JButton("leaderboard");
        leaderboard.setBounds( btnPos_x, btnPos_y, btnWidth, btnHeight );
        btnPos_y += nextPos_y;

        leaderboard.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                _forms.add( new LeaderboardForm( _forms ) );
            }
        });

        JButton settings = new JButton("Settings");
        settings.setBounds(btnPos_x, btnPos_y, btnWidth, btnHeight );
        btnPos_y += nextPos_y;

        settings.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                _forms.add( new SettingsForm( _forms, _settings, _font ) );
            }
        });

        JButton quitGame = new JButton("Quit Game");
        quitGame.setBounds(btnPos_x, btnPos_y, btnWidth, btnHeight );
        quitGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        this.add(quitGame);
        this.add(settings);
        this.add(leaderboard);
        this.add(startGame);
    }

    private void InitFont()
    {
        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Resources/orange_juice.ttf")));
        }
        catch (IOException | FontFormatException e)
        {
            e.printStackTrace();
        }

        _font = new Font("orange_juice", Font.BOLD, 100);
    }

    private void InitLogo()
    {
        int x_pos = 375;
        int y_pos = 50;

        JLabel logo = new JLabel("TETRIS");
        logo.setFont( _font );
        logo.setBounds( x_pos, y_pos, 400, 100 );

        this.add(logo);
    }

    private void InitSettings()
    {
        _settings.add(1);
    }

}