import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class WateringCan extends Tool {
    private static BufferedImage wateringCanImage;
    
    public WateringCan()
    {
        super();
        if(wateringCanImage == null)
        {
            wateringCanImage = View.LOAD_IMAGE("Images\\toolImages\\wateringCan.png");
        }
    }

    @Override
    public boolean isWateringCan()
    {
        return true;
    }

    public void drawYourself(Graphics g, int x, int y)
    {
        g.drawImage(wateringCanImage, x, y, 100, 100, null);
    }

}
