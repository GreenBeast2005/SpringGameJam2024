import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Floor extends Sprite {
    private static BufferedImage wallImage;

    //Setting this up so that I only have to change the grid size in one place
    //Default constructor
    public Floor(int x, int y, int h, int w)
    {
        super(x, y, h, w);

        if(wallImage == null)
        {
            wallImage = View.LOAD_IMAGE("Images\\floor.png");
            System.out.println("Loaded Wall Image");
        }
    }

    //This is the other constructor i made for the 8 bit alternative, since every wall has the same size
    public Floor(int x, int y)
    {
        this(x, y, Grid.GRID_SIZE, Grid.GRID_SIZE);
    }

    //Constructor based on json object
    public Floor(Json ob)
  	{
        this((int)ob.getLong("x"), (int)ob.getLong("y"), (int)ob.getLong("h"), (int)ob.getLong("w"));
    }

    //Required methods for sprite class
    //Tells the walls to draw themselves
    @Override
    public void draw(Graphics g, int scrollPosY)
    {
        g.drawImage(wallImage, x, y - scrollPosY, w, h, null);
    }

    @Override
    public void water() { }
    @Override
    public void passTime() { }

    @Override
    public void update() { }

    @Override
    public void handleCollision(Sprite collidedSprite) { }

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
    public boolean isFloor()
    {
        return true;
    }

    //Used for debuging
    @Override 
    public String toString()
    {
        return "Wall (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}
