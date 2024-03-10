package TetrisBlocks;

import java.util.ArrayList;
import java.util.Arrays;

public class IShape extends TetrisBlock
{
    /* Constructors */
    public IShape()
    {
        super();
        this.InitShape();
    }

    /* Initialization */
    private void InitShape()
    {
        ArrayList< ArrayList< Boolean > > shape = new ArrayList<>();
        shape.add(new ArrayList<>(Arrays.asList(true)));
        shape.add(new ArrayList<>(Arrays.asList(true)));
        shape.add(new ArrayList<>(Arrays.asList(true)));
        shape.add(new ArrayList<>(Arrays.asList(true)));

        super.SetShape(shape);
    }

    @Override
    public void Rotate()
    {
        super.Rotate();

        if (this.GetWidth() == 1)
        {
            super.GetVector2i().SetX( super.GetVector2i().GetX() + 1 );
            super.GetVector2i().SetY( super.GetVector2i().GetY() - 1 );
        }
        else
        {
            super.GetVector2i().SetX( super.GetVector2i().GetX() - 1 );
            super.GetVector2i().SetY( super.GetVector2i().GetY() + 1 );
        }
    }

    @Override
    public void UnRotate()
    {
        super.UnRotate();

        if (this.GetWidth() == 1)
        {
            super.GetVector2i().SetX( super.GetVector2i().GetX() + 1 );
            super.GetVector2i().SetY( super.GetVector2i().GetY() - 1 );
        }
        else
        {
            super.GetVector2i().SetX( super.GetVector2i().GetX() - 1 );
            super.GetVector2i().SetY( super.GetVector2i().GetY() + 1 );
        }

    }
}
