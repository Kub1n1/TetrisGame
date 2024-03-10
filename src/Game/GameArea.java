package Game;

import TetrisBlocks.*;
import Utility.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class GameArea extends JPanel
{
    /* Panel Variables */
    private final int DEFAULT_GAME_AREA_WIDTH = 800;
    private final int DEFAULT_GAME_AREA_HEIGHT = 600;
    private final int DEFAULT_GAME_AREA_X_POS = 80;
    private final int DEFAULT_GAME_AREA_Y_POS = 0;
    private final int GAME_AREA_WIDTH;
    private final int GAME_AREA_HEIGHT;
    private final int GAME_AREA_X_POS;
    private final int GAME_AREA_Y_POS;

    /* Grid Variables */
    private int _gridRows;
    private int _gridColumns;
    private  int _gridCellSize;

    /* Blocks Variables */
    private TetrisBlock _block;
    private ArrayList<TetrisBlock> _blocks;
    private HashMap<Integer, Integer> _blocksCounts;
    private HashMap<Integer, Integer> _colorCounts;

    /* Background Variables */
    private ArrayList< ArrayList<Color> > _background;

    /* forms */
    private GameForm _form;

    /* Settings */
    private  ArrayList<Integer> _settings;

    /* Constructors */
    public GameArea(int x, int y, int width, int height, GameForm form, ArrayList<Integer> settings)
    {
        _form = form;
        _settings = settings;
        if (width > 0 && height > 0)
        {
            GAME_AREA_WIDTH = width;
            GAME_AREA_HEIGHT = height;
        }
        else
        {
            GAME_AREA_WIDTH = DEFAULT_GAME_AREA_WIDTH;
            GAME_AREA_HEIGHT = DEFAULT_GAME_AREA_HEIGHT;
        }

        if (x >= 0 && y >= 0)
        {
            GAME_AREA_X_POS = x;
            GAME_AREA_Y_POS = y;
        }
        else
        {
            GAME_AREA_X_POS = DEFAULT_GAME_AREA_X_POS;
            GAME_AREA_Y_POS = DEFAULT_GAME_AREA_Y_POS;
        }

        this.InitPanel();
        this.InitGrid();
        this.InitBackground();
        this.InitBlocks();
    }

    /* Initialization */
    private void InitPanel()
    {
        this.setBounds(GAME_AREA_X_POS, GAME_AREA_Y_POS, GAME_AREA_WIDTH, GAME_AREA_HEIGHT);
        this.setBackground(new Color(238, 238, 238));
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    private void InitGrid()
    {
        _gridColumns = GAME_AREA_WIDTH / (int)(GAME_AREA_WIDTH * 0.07);
        _gridCellSize = GAME_AREA_WIDTH / _gridColumns;
        _gridRows = GAME_AREA_HEIGHT / _gridCellSize;
    }

    private void InitBackground()
    {
        _background = new ArrayList<>();

        for (int i = 0; i < _gridRows; i++)
        {
            _background.add(new ArrayList<>());
            for (int j = 0; j < _gridColumns; j++)
            {
                _background.get(i).add(null);
            }
        }
    }

    private void InitBlocks()
    {
        _blocks = new ArrayList<>();
        _blocks.add( new IShape() );
        _blocks.add( new JShape() );
        _blocks.add( new LShape() );
        _blocks.add( new OShape() );
        _blocks.add( new SShape() );
        _blocks.add( new TShape() );
        _blocks.add( new ZShape() );

        _blocksCounts = new HashMap<>();
        for (int i = 0; i < _blocks.size(); i++)
            _blocksCounts.put(i, 0);

        int colorsNumbers = 4;
        _colorCounts = new HashMap<>();
        for (int i = 0; i < colorsNumbers; i++)
            _colorCounts.put(i, 0);
    }

    /* Spawn methods */
    public void SpawnBlock()
    {
        int blockIndex = this.CalculateBlockIndex();
        int colorIndex = this.CalculateColorIndex();

        _block = _blocks.get( blockIndex );
        _block.ChangeColorSet( _settings.get(0) );
        _block.SetColor( colorIndex );

        _block.Spawn(_gridColumns);
    }

    private int CalculateBlockIndex()
    {
        Random random = new Random();
        int index = random.nextInt( _blocks.size() );

        if (_blocksCounts.get(index) > 2)
        {
            int tmp = index;
            _blocksCounts.put(index, 0);

            while (index == tmp)
            {
                index = random.nextInt( _blocks.size() );
            }
        }
        else
        {
            _blocksCounts.put(index, _blocksCounts.get(index) + 1);
        }

        return index;
    }

    private int CalculateColorIndex()
    {
        int colorsNumbers = 4;
        Random random = new Random();
        int index = random.nextInt( colorsNumbers );

        if (_colorCounts.get(index) > 0)
        {
            int tmp = index;
            _blocksCounts.put(index, 0);

            while (index == tmp)
            {
                index = random.nextInt( colorsNumbers );
            }
        }
        else
        {
            _colorCounts.put(index, _blocksCounts.get(index) + 1);
        }

        return index;
    }

    /* Move methods */
    public Boolean MoveBlockDown()
    {
        if (!this.CheckBottomBorder())
        {
            return false;
        }

        _block.MoveDown();
        repaint();
        return true;
    }

    public void MoveBlockRight()
    {
        if (_block == null)
            return;

        if (this.CheckRightBorder())
            _block.MoveRight();
        repaint();
    }

    public void MoveBlockLeft()
    {
        if (_block == null)
            return;

        if (this.CheckLeftBorder())
            _block.MoveLeft();
        repaint();
    }

    public void DropBlockToBottomEdge()
    {
        if (_block == null)
            return;

        while ( this.CheckBottomBorder() )
        {
            this.MoveBlockDown();
        }

        repaint();
    }

    public void RotateBlock()
    {
        if ( _block == null )
            return;

        _block.Rotate();

        if (_block.GetLeftEdge() < 0)
            _block.GetVector2i().SetX(0);

        if (_block.GetRightEdge() >= _gridColumns)
            _block.GetVector2i().SetX(_gridColumns - _block.GetWidth() );

        if (_block.GetBottomEdge() >= _gridRows)
            _block.GetVector2i().SetY(_gridRows - _block.GetHeight());

        if (this.CheckCollisionWithBackground())
            _block.UnRotate();

        repaint();
    }

    /* Check methods */
    private Boolean CheckBottomBorder()
    {
        if ( _block.GetBottomEdge() == _gridRows)
            return false;

        ArrayList< ArrayList< Boolean > > shape = _block.GetShape();
        int width = _block.GetWidth();
        int height = _block.GetHeight();

        for (int i = 0; i < width; i++)
        {
            for (int j = height - 1; j >= 0; j--)
            {
                if (shape.get(j).get(i))
                {
                    int x = i + _block.GetVector2i().GetX();
                    int y = j + _block.GetVector2i().GetY() + 1;

                    if (y < 0)
                        break;

                    if (_background.get(y).get(x) != null)
                        return false;
                    break;
                }
            }
        }

        return true;
    }

    private Boolean CheckRightBorder()
    {
        if ( _block.GetRightEdge() == _gridColumns)
            return false;

        ArrayList< ArrayList< Boolean > > shape = _block.GetShape();
        int width = _block.GetWidth();
        int height = _block.GetHeight();

        for (int j = 0; j < height; j++)
        {
            for (int i = width - 1; i >= 0; i--)
            {
                if (shape.get(j).get(i))
                {
                    int x = i + _block.GetVector2i().GetX() + 1;
                    int y = j + _block.GetVector2i().GetY();

                    if (y < 0)
                        break;

                    if (_background.get(y).get(x) != null)
                        return false;
                    break;
                }
            }
        }

        return true;
    }

    private Boolean CheckLeftBorder()
    {
        if ( _block.GetLeftEdge() == 0)
            return false;

        ArrayList< ArrayList< Boolean > > shape = _block.GetShape();
        int width = _block.GetWidth();
        int height = _block.GetHeight();

        for (int j = 0; j < height; j++)
        {
            for (int i = 0; i < width; i++)
            {
                if (shape.get(j).get(i))
                {
                    int x = i + _block.GetVector2i().GetX() - 1;
                    int y = j + _block.GetVector2i().GetY();

                    if (y < 0)
                        break;

                    if (_background.get(y).get(x) != null)
                        return false;
                    break;
                }
            }
        }

        return true;
    }

    public Boolean IsBlockOutOfBounds()
    {
        if (_block.GetVector2i().GetY() < 0)
        {
            _block = null;
            return true;
        }

        return false;
    }

    private Boolean CheckCollisionWithBackground()
    {
        ArrayList<ArrayList<Boolean>> shape = _block.GetShape();
        int width = _block.GetWidth();
        int height = _block.GetHeight();

        for (int j = 0; j < height; j++)
        {
            for (int i = 0; i < width; i++)
            {
                if (shape.get(j).get(i))
                {
                    int x = i + _block.GetVector2i().GetX();
                    int y = j + _block.GetVector2i().GetY();

                    if (y >= 0 && _background.get(y).get(x) != null)
                        return true;
                }
            }
        }

        return false;
    }

    /* Background methods */
    public int ClearLines()
    {
        boolean fullLine;
        int clearedLines = 0;

        for (int i = _gridRows - 1; i >= 0; i--)
        {
            fullLine = true;
            for (int j = 0; j < _gridColumns; j++)
            {
                if (_background.get(i).get(j) == null)
                {
                    fullLine = false;
                    break;
                }
            }

            if (fullLine)
            {
                clearedLines++;
                this.ClearLine(i);
                this.ShiftDown(i);
                //this.ClearLine(0);

                i++;

                repaint();
            }

        }

        return clearedLines;
    }

    public void ClearLine(int index)
    {
        for (int k = 0; k < _gridColumns; k++)
        {
            _background.get(index).set(k, null);
        }
    }

    public void ShiftDown(int index)
    {
        for (int i = index; i > 0; i--)
        {
            for (int j = 0; j < _gridColumns; j++)
            {
                _background.get(i).set(j, _background.get(i-1).get(j));
            }
        }
    }

    public void AddToBackground()
    {
        ArrayList< ArrayList<Boolean> > shape = _block.GetShape();
        int height = _block.GetHeight();
        int width = _block.GetWidth();

        int x_pos = _block.GetVector2i().GetX();
        int y_pos = _block.GetVector2i().GetY();

        Color color = _block.GetColor();

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (shape.get(i).get(j))
                    _background.get(i + y_pos).set(j + x_pos, color);
            }
        }

    }

    /* Score */
    private void SaveScore(String name, int sc)
    {
        Score score = new Score(name, sc);
        score.SaveScore();

        _form.EndGame();
    }

    public void ScoreFrame(int sc)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GAME_AREA_WIDTH / 2, GAME_AREA_HEIGHT / 2);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel("Enter your name");
        label.setBounds(100, 50, 100, 50);
        panel.add(label);

        JTextField nameField = new JTextField();
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBounds(50, 100, 200, 50);
        panel.add(nameField);

        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 175, 100, 50);
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SaveScore( nameField.getText(), sc);
                frame.setVisible(false);
            }
        });
        panel.add(okButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    /* Draw methods */
    private void DrawBlock(Graphics g)
    {
        int col = _block.GetHeight();
        int row = _block.GetWidth();
        Color color = _block.GetColor();
        ArrayList< ArrayList< Boolean > > shape = _block.GetShape();

        for (int j = 0; j < col; j++)
        {
            for (int i = 0; i < row; i++)
            {
                if( shape.get(j).get(i) )
                {
                    int x = (_block.GetVector2i().GetX() + i) * _gridCellSize;
                    int y = (_block.GetVector2i().GetY() + j) * _gridCellSize;

                    this.DrawGridSquare(g, color, x, y);
                }
            }
        }
    }

    private void DrawBackground(Graphics g)
    {
        Color color;
        for (int i = 0; i < _gridRows; i++)
        {
            for (int j = 0; j < _gridColumns; j++)
            {
                color = _background.get(i).get(j);

                if (color != null)
                {
                    int x = j * _gridCellSize;
                    int y = i * _gridCellSize;

                    this.DrawGridSquare(g, color, x, y);
                }
            }
        }
    }

    private void DrawGridSquare(Graphics g, Color color, int x, int y)
    {
        g.setColor(color);
        g.fillRect(x, y, _gridCellSize, _gridCellSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, _gridCellSize, _gridCellSize);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.DrawBlock(g);
        this.DrawBackground(g);
    }
}
