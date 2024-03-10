package Utility;

public class Vector2i
{
    /* Variables */
    private int x;
    private int y;

    /* Constructors */
    public Vector2i(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /* Getters */
    public int GetX()
    {
        return x;
    }

    public int GetY()
    {
        return y;
    }

    public void SetX(int x)
    {
        this.x = x;
    }

    public void SetY(int y)
    {
        this.y = y;
    }

    public void Set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /* Methods */
    public Vector2i Add(Vector2i other)
    {
        return new Vector2i(this.x + other.x, this.y + other.y);
    }

    public Vector2i Subtract(Vector2i other)
    {
        return new Vector2i(this.x - other.x, this.y - other.y);
    }
}