package TetrisBlocks;

import java.util.ArrayList;
import java.util.Arrays;

public class LShape extends TetrisBlock
{
    /* Constructors */
    public LShape()
    {
        super();
        this.InitShape();
    }

    /* Initialization */
    private void InitShape()
    {
        ArrayList<ArrayList< Boolean >> shape = new ArrayList<>();
        shape.add(new ArrayList<>(Arrays.asList(true, false)));
        shape.add(new ArrayList<>(Arrays.asList(true, false)));
        shape.add(new ArrayList<>(Arrays.asList(true, true)));

        super.SetShape(shape);
    }
}
