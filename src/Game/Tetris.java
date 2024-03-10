package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Tetris extends Thread
{
    private Stack<JFrame> _forms;
    private int _lastSize;

    /* Constructors */
    private Tetris()
    {
        this.InitForms();
    }

    /* Initialization */
    private void InitForms()
    {
        _forms = new Stack<>();
        _forms.add( new StartupForm( _forms ) );
        _lastSize = _forms.size();
    }

    /* Thread */
    @Override
    public void run()
    {
        while ( !_forms.empty() )
        {
            if (_forms.size() != _lastSize)
            {
                if ( !_forms.peek().isVisible() )
                    _forms.peek().setVisible(true);

                _lastSize = _forms.size();
            }
        }
    }

    /* Main */
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        tetris.start();
    }


}
