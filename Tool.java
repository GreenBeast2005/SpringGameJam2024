import java.awt.Graphics;

public abstract class Tool {
    
    public Tool()
    {

    }

    public boolean isWateringCan()
    {
        return false;
    }
    public boolean isHoe()
    {
        return false;
    }
    public boolean isBag()
    {
        return false;
    }
    public boolean isHand()
    {
        return false;
    }
    
    public abstract void drawYourself(Graphics g, int x, int y);
} 
