import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SellingInteractable extends Sprite implements Interactable {

    private BufferedImage sellingInteractableImage;

    public SellingInteractable(int x, int y)
    {
        super(x, y, Grid.GRID_SIZE, Grid.GRID_SIZE);

        if(sellingInteractableImage == null)
        {
            sellingInteractableImage = View.LOAD_IMAGE("Images\\salesPerson.png");
        }
    }

    public SellingInteractable(Json ob)
    {
        this((int)ob.getLong("x"), (int)ob.getLong("y"));
    }

    @Override
    public boolean isInteractable()
    {
        return true;
    }

    @Override
    public boolean isSellingInteractable()
    {
        return true;
    }

    @Override
    public void interact() 
    { 
        Model.money += Model.bag.sellCrops();
        System.out.println("Selling interacted with");
    
    }

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

    @Override
    public void update() { }

    @Override
    public void draw(Graphics g, int scrollPosY) {

        g.drawImage(View.signImage, x - 40, y - scrollPosY - 40, 180, 40, null);
        g.drawString("Talk to me with me to Sell", x - 38, y - scrollPosY - 20);

        g.drawImage(sellingInteractableImage, x, y - scrollPosY, w, h, null);
    }

    @Override
    public void handleCollision(Sprite collidingSprite) { }

    @Override
    public void water() { }

    @Override
    public void passTime() { }
    
}
