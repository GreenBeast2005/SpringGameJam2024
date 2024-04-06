import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FarmLand extends Sprite{
    private boolean occupied;
    private int currentState;

    private BufferedImage farmLandImage;

    public FarmLand(int x, int y)
    {
        super(x, y, Grid.GRID_SIZE, Grid.GRID_SIZE);

        occupied = false;
        if(farmLandImage == null)
        {
            farmLandImage = View.LOAD_IMAGE("Images\\cropImages\\ground.png");
        }
        currentState = 1;
    }

    @Override
    public boolean isFarmLand()
    {
        return true;
    }

    public void water()
    {
        currentState = 2;
    }

    @Override
    public void passTime()
    {
        currentState = 1;
    }
    
    public boolean plantSeed()
    {
        if(!occupied)
        {
            occupied = true;
            return true;
        }
        return false;
    }

    @Override
    public void update() { }

    @Override
    public void draw(Graphics g, int scrollPosY) 
    { 
        g.drawImage(farmLandImage.getSubimage(0 + (currentState * 50), 0, 50, 50), x, y - scrollPosY, w, h, null);
    }

    @Override
    public void handleCollision(Sprite collidingSprite) { }
    
}
