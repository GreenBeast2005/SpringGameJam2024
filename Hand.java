import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Hand extends Tool {
    private static BufferedImage handImage;
    
    public Hand()
    {
        super();
        if(handImage == null)
        {
            handImage = View.LOAD_IMAGE("Images\\toolImages\\hand.png");
        }
    }

    @Override
    public boolean isHand()
    {
        return true;
    }

    public void drawYourself(Graphics g, int x, int y)
    {
        g.drawImage(handImage, x, y, 100, 100, null);
    }

}
