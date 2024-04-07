import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DayCycleTrigger extends Sprite implements Interactable {

    private BufferedImage dayCycleTriggerImage;

    public DayCycleTrigger(int x, int y)
    {
        super(x,y, Grid.GRID_SIZE, Grid.GRID_SIZE);

        dayCycleTriggerImage = (dayCycleTriggerImage == null) ? View.LOAD_IMAGE("Images\\bed.png") : dayCycleTriggerImage;
    }

    public DayCycleTrigger(Json ob)
    {
        this((int)ob.getLong("x"), (int)ob.getLong("y"));
    }

    public boolean isInteractable()
    {
        return true;
    }

    public boolean isDayCycleTrigger()
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

   // Marshals this object into a JSON DOM
    @Override
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("h", h);
        ob.add("w", w);
        return ob;
    }
    
}
