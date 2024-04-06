import java.awt.Graphics;

public class Wheat extends Crop
{
    public Wheat(int x, int y)
    {
        super(x,y, 4);
        h = Grid.GRID_SIZE;
        w = Grid.GRID_SIZE;
        growthStage = 0;
        isWatered = false;
    }

    public void draw(Graphics g, int scr)
    {
        if(!isWhithered) 
        {
            g.drawImage(cropImage.getSubimage(240 - (growthStage * 48), 240, 48, 48), x, y - scr, w, h, null);

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
    public String toString()
    {
        return "Wheat (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}