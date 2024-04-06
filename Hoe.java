import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Hoe extends Tool {
    private static BufferedImage hoeImage;
    
    public Hoe()
    {
        super();
        if(hoeImage == null)
        {
            hoeImage = View.LOAD_IMAGE("Images\\toolImages\\hoe.png");
        }
    }

    public boolean isHoe()
    {
        return true;
    }

    public void drawYourself(Graphics g, int x, int y)
    {
        g.drawImage(hoeImage, x, y, 100, 100, null);
    }

}
