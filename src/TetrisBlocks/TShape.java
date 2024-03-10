package TetrisBlocks;

import java.util.ArrayList;
import java.util.Arrays;

public class TShape extends TetrisBlock
{
    /* Constructors */
    public TShape()
    {
        super();
        this.InitShape();
    }

    /* Initialization */
    private void InitShape()
    {
        ArrayList<ArrayList< Boolean >> shape = new ArrayList<>();
        shape.add(new ArrayList<>(Arrays.asList(true, true, true)));
        shape.add(new ArrayList<>(Arrays.asList(false, true, false)));

        super.SetShape(shape);
    }
}
