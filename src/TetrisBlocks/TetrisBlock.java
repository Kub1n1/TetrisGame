package TetrisBlocks;

import Utility.Vector2i;

import java.awt.*;
import java.util.*;

public class TetrisBlock
{
    /* Variables */
    private ArrayList<ArrayList< Boolean >> _shape;
    private ArrayList< ArrayList< ArrayList< Boolean > > > _shapes;
    private int _currentRotation;
    private Color _color;
    private ArrayList<Color> _colorsSetCurrent;
    private ArrayList<Color> _colorsSet1;
    private ArrayList<Color> _colorsSet2;
    private Vector2i _vector2i;

    /* Constructors */
    public TetrisBlock()
    {
        _vector2i = new Vector2i(0, 0);

        this.InitColors();
    }

    /* Initialization */
    private void InitShapes()
    {
        _shapes = new ArrayList<>();

        int shapesNumber = 4;

        for (int i = 0; i < shapesNumber; i++)
        {
            _shapes.add(new ArrayList<>());
        }

        for (int i = 0; i < shapesNumber; i++ )
        {
            int row = _shape.get(0).size();
            int col = _shape.size();

            for (int r = 0; r < row; r++)
            {
                _shapes.get(i).add(new ArrayList<>());
                for (int c = 0; c < col; c++)
                {
                    _shapes.get(i).get(r).add(false);
                }
            }

            for (int j = 0; j < row; j++)
            {
                for (int k = 0; k < col; k++)
                {
                    _shapes.get(i).get(j).set(k, _shape.get(col - k - 1).get(j));
                }
            }

            _shape = _shapes.get(i);
        }
    }

    private void InitColors()
    {
        _colorsSet1 = new ArrayList<>();

        _colorsSet1.add(Color.blue);
        _colorsSet1.add(Color.green);
        _colorsSet1.add(Color.red);
        _colorsSet1.add(Color.yellow);

        _colorsSet2 = new ArrayList<>();

        _colorsSet2.add(Color.lightGray);
        _colorsSet2.add(Color.ORANGE);
        _colorsSet2.add(Color.CYAN);
        _colorsSet2.add(Color.magenta);

        _colorsSetCurrent = _colorsSet1;
    }

    /* Methods */
    public void Spawn(int gridWidth)
    {
        Random random = new Random();

        _currentRotation = random.nextInt( _shapes.size() );
        _shape = _shapes.get(_currentRotation);

        _vector2i.SetX( random.nextInt(gridWidth - this.GetWidth() ) );
        _vector2i.SetY( -this.GetHeight() );
    }

    public void Rotate()
    {
        _currentRotation++;

        if ( _currentRotation > 3 )
            _currentRotation = 0;

        _shape = _shapes.get(_currentRotation);
    }

    public void UnRotate()
    {
        _currentRotation--;

        if ( _currentRotation < 0 )
            _currentRotation = 3;

        _shape = _shapes.get(_currentRotation);
    }

    /* Getters */
    public ArrayList<ArrayList< Boolean >> GetShape()
    {
        return _shape;
    }

    public Color GetColor()
    {
        return _color;
    }

    public int GetHeight()
    {
        return _shape.size();
    }

    public int GetWidth()
    {
        return _shape.get(0).size();
    }

    public Vector2i GetVector2i()
    {
        return _vector2i;
    }

    public int GetBottomEdge()
    {
        return _vector2i.GetY() + this.GetHeight();
    }

    public int GetRightEdge()
    {
        return _vector2i.GetX() + this.GetWidth();
    }

    public int GetLeftEdge()
    {
        return _vector2i.GetX();
    }

    /* Setters */
    public void MoveDown()
    {
        _vector2i.SetY( _vector2i.GetY() + 1 );
    }

    public void MoveLeft()
    {
        _vector2i.SetX( _vector2i.GetX() - 1 );
    }

    public void MoveRight()
    {
        _vector2i.SetX( _vector2i.GetX() + 1 );
    }

    public void SetShape ( ArrayList<ArrayList< Boolean >> shape)
    {
        _shape = shape;
        this.InitShapes();
    }

    /* Colors */
    public void SetColor(int index)
    {
        _color = _colorsSetCurrent.get(index);
    }

    public void ChangeColorSet(int setNumber)
    {
        if (setNumber == 1)
            _colorsSetCurrent = _colorsSet1;
        else if (setNumber == 2)
            _colorsSetCurrent = _colorsSet2;
    }

}
