package Game;

public class GameThread extends Thread
{
    /* Variables */
    private GameArea _gameArea;
    private GameForm _gameForm;
    private int _score;
    private int _level = 1;
    private int _levelUp = 3;
    private int _pause = 1000;
    private int _speedUp = 200;


    /* Constructors */
    public GameThread(GameArea gameArea, GameForm gameForm)
    {
        _gameArea = gameArea;
        _gameForm = gameForm;
    }

    @Override
    public void run()
    {
        // Main Game Loop
        while (true)
        {
            _gameArea.SpawnBlock();

            while (_gameArea.MoveBlockDown())
            {
                try
                {
                    Thread.sleep(_pause);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }

            if ( _gameArea.IsBlockOutOfBounds() )
                break;

            int threeRowsCleared = 3;
            int fiveRowsCleared = 5;
            int points = 0;
            int extraPointsForThree = 5;
            int extraPointsForFive = 10;

            _gameArea.AddToBackground();

            points = _gameArea.ClearLines();
            if (points >= threeRowsCleared && points < fiveRowsCleared)
                _score += points + extraPointsForThree;
            else if ( points >= fiveRowsCleared )
                _score += points + extraPointsForFive;
            else
                _score += points;

            _gameForm.UpdateScore(_score);

            int lvl = _score / _levelUp + 1;

            if (  lvl > _level )
            {
                _level = lvl;
                _gameForm.UpdateLevel(_level);

                if (_pause > 300)
                    _pause -= _speedUp;
            }
        }

        // Save Game
        _gameArea.ScoreFrame( _score );
    }
}
