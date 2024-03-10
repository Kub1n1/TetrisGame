package TetrisBlocks;

import java.util.ArrayList;
import java.util.Arrays;

public class OShape extends  TetrisBlock
{
    /* Constructors */
    public OShape()
    {
        super();
        this.InitShape();
    }

    /* Initialization */
    private void InitShape()
    {
        ArrayList<ArrayList< Boolean >> shape = new ArrayList<>();
        shape.add(new ArrayList<>(Arrays.asList(true, true)));
        shape.add(new ArrayList<>(Arrays.asList(true, true)));

        super.SetShape(shape);
    }
}
