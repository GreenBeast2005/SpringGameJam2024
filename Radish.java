import java.awt.Graphics;

public class Radish extends Crop
{
    public Radish(int x, int y, FarmLand l)
    {
        super(x,y, 5, l);
        h = Grid.GRID_SIZE;
        w = Grid.GRID_SIZE;
        growthStage = 0;
        isWatered = false;
    }

    public void draw(Graphics g, int scr)
    {
        if(!isWhithered) 
        {
            g.drawImage(cropImage.getSubimage(240 - (growthStage * 48), 0, 48, 48), x, y - scr, w, h, null);

            if(!isWatered)
            {
                g.drawImage(waterImage, x, y - scr, w/2, h/2, null);
            }
        } else
        {
            g.drawImage(whitheredImage, x, y - scr, w, h, null);
        }
    }

    @Override
    public void passTime()
    {
        Age+=1;

        if(growthStage < maxGrowthStage && isWatered && Age%2==0)
        {
            growthStage += 1;
            isWatered = false;
            daysSinceWater = 0;
            return;
        }

        daysSinceWater += 1;

        if(daysSinceWater == 5)
            isWhithered = true;
    }

    @Override
    public boolean isRadish()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Radish (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}