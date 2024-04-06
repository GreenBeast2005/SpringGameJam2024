import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DayCycleTrigger extends Sprite implements Interactable {

    private BufferedImage dayCycleTriggerImage;

    public DayCycleTrigger(int x, int y)
    {
        super(x,y, Grid.GRID_SIZE, Grid.GRID_SIZE);

        dayCycleTriggerImage = (dayCycleTriggerImage == null) ? View.LOAD_IMAGE("Images\\bed.png") : dayCycleTriggerImage;
    }

    public boolean isInteractable()
    {
        return true;
    }

    @Override
    public void interact() 
    {
        Model.TriggerDayCycle();
    }

    @Override
    public void update() { }

    @Override
    public void draw(Graphics g, int scrollPosY) 
    { 
        g.drawImage(dayCycleTriggerImage, x, y - scrollPosY, w, h, null);
    }

    @Override
    public void handleCollision(Sprite collidingSprite) { }

    @Override
    public void passTime() { }

    @Override
    public void water() { }
    
}
