import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Crop extends Sprite implements Interactable
{
    protected static BufferedImage whitheredImage, waterImage, cropImage = null;
    protected int growthStage, maxGrowthStage, daysSinceWater, Age;
    protected boolean isWatered, isWhithered;
    protected FarmLand land;

    public Crop(int x, int y, int max, FarmLand l)
    {
        super(x,y, 0, 0);
        h = Grid.GRID_SIZE;
        w = Grid.GRID_SIZE;
        growthStage = 0;
        daysSinceWater = 0;
        maxGrowthStage = max;
        isWatered = false;
        isWhithered = false;
        Age = 0;
        land = l;

        waterImage = (waterImage == null) ? View.LOAD_IMAGE("Images\\cropImages\\waterDroplet.png") : waterImage;
        whitheredImage = (whitheredImage == null) ? View.LOAD_IMAGE("Images\\cropImages\\witheredCrop.png") : whitheredImage;
        cropImage = (cropImage == null) ? View.LOAD_IMAGE("Images\\cropImages\\cropSpriteSheet.png") : cropImage;
    }

    public void setLand(FarmLand l)
    {
        land = l;
    }

    public FarmLand getLand()
    {
        return land;
    }

    @Override
    public void passTime()
    {
        Age+=1;

        if(growthStage < maxGrowthStage && isWatered)
        {
            growthStage += 1;
            isWatered = false;
            daysSinceWater = 0;
            return;
        }

        daysSinceWater += 1;

        if(daysSinceWater == 3)
            isWhithered = true;
    }

    public int getGrowthStage()
    {
        return growthStage;
    }

    @Override
    public void interact()
    {
        if(growthStage >= maxGrowthStage)
            destroyed = true;
    }

    @Override
    public void water()
    {
        isWatered = true;
    }

    public void instantGrown()
    {
        growthStage = maxGrowthStage;
    }

    public boolean isGrown()
    {
        if(growthStage == maxGrowthStage)
            return true;

        return false;
    }

    public boolean isWheat()
    {
        return false;
    }

    public boolean isRadish()
    {
        return false;
    }

    @Override
    public Json marshal()
    {
        return null;
    }

    @Override
    public boolean isCrop()
    {
        return true;
    }

    @Override
    public boolean isInteractable()
    {
        return true;
    }

    public abstract void draw(Graphics g, int scr);
    public void handleCollision(Sprite s) { }
    public  void update() { }
}
