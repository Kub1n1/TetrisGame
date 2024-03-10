package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

public class GameForm extends JFrame
{
    /* Frame Variables */
    private final int DEFAULT_WINDOW_WIDTH = 1100;
    private final int DEFAULT_WINDOW_HEIGHT = 700;
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;

    /* Game */
    private GameArea _gameArea;
    private JLabel _scoreLabel;
    private int _score;
    private JLabel _levelLabel;
    private int _level;

    /* Form */
    private Stack<JFrame> _forms;
    /* Settings */
    private ArrayList<Integer> _settings;

    /* Constructors */
    public GameForm( Stack<JFrame> forms, ArrayList<Integer> settings )
    {
        _forms = forms;
        _settings = settings;

        WINDOW_WIDTH = DEFAULT_WINDOW_WIDTH;
        WINDOW_HEIGHT = DEFAULT_WINDOW_HEIGHT;

        this.InitLayout();
        this.InitGameArea();
        this.InitLabels();
        this.InitFrame();
        this.InitControls();
        this.InitButtons();

        this.StartGame();
    }

    /* Methods */
    public void StartGame()
    {
        new GameThread(_gameArea, this).start();
    }

    /* Updates */
    public void UpdateScore(int score)
    {
        _score = score;
        _scoreLabel.setText("Score: " + _score);
    }

    public void UpdateLevel(int level)
    {
        _level = level;
        _levelLabel.setText("Level: " + _level);
    }

    /* Initialization */
    private void InitLayout()
    {
        this.setLayout(null);
    }

    private void InitGameArea()
    {
        int gameAreaWidth = 0;
        int gameAreaHeight = 0;
        int gameAreaPos_x = 0;
        int gameAreaPos_y = 0;

        gameAreaWidth = (int)(WINDOW_WIDTH * 0.6);
        gameAreaHeight = WINDOW_HEIGHT - 39;
        gameAreaPos_x = (int)(WINDOW_WIDTH * 0.2);
        gameAreaPos_y = 0;

        _gameArea = new GameArea(gameAreaPos_x, gameAreaPos_y, gameAreaWidth, gameAreaHeight, this, _settings);
        this.add(_gameArea, BorderLayout.CENTER);
    }

    private void InitLabels()
    {
        _score = 0;
        _level = 1;

        int x_0 = (int)(WINDOW_WIDTH * 0.8 + 10);

        _scoreLabel = new JLabel("Score: " + _score);
        _scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        _scoreLabel.setBounds(x_0, 10, 100, 30);
        this.add(_scoreLabel);

        _levelLabel = new JLabel("Level: " + _level);
        _levelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        _levelLabel.setBounds(x_0, 50, 100, 30);
        this.add(_levelLabel);
    }

    private void InitFrame()
    {
        this.pack();
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void InitControls()
    {
        this.setFocusable(true);

        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down");
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "space");

        this.getRootPane().getActionMap().put("right", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _gameArea.MoveBlockRight();
            }
        });
        this.getRootPane().getActionMap().put("left", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _gameArea.MoveBlockLeft();
            }
        });
        this.getRootPane().getActionMap().put("down", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _gameArea.MoveBlockDown();
            }
        });
        this.getRootPane().getActionMap().put("up", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _gameArea.RotateBlock();
            }
        });
        this.getRootPane().getActionMap().put("space", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _gameArea.DropBlockToBottomEdge();
            }
        });
    }

    private void InitButtons()
    {
        JButton btnMainMenu = new JButton("Main menu");
        btnMainMenu.setBounds(
                (int)(WINDOW_WIDTH * 0.05), (int)(WINDOW_HEIGHT * 0.05),
                (int)(WINDOW_WIDTH * 0.1), (int)(WINDOW_HEIGHT * 0.05)
        );

        btnMainMenu.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                _forms.pop();
            }
        });

        this.add( btnMainMenu );
    }

    /* End Game */
    public void EndGame()
    {
        _forms.pop().setVisible(false);
    }

}
