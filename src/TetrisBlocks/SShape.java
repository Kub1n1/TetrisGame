package TetrisBlocks;

import java.util.ArrayList;
import java.util.Arrays;

public class SShape extends TetrisBlock
{
    /* Constructors */
    public SShape()
    {
        super();
        this.InitShape();
    }

    /* Initialization */
    private void InitShape()
    {
        ArrayList<ArrayList< Boolean >> shape = new ArrayList<>();
        shape.add(new ArrayList<>(Arrays.asList(true, true, false)));
        shape.add(new ArrayList<>(Arrays.asList(false, true, true)));

        super.SetShape(shape);
    }
}
