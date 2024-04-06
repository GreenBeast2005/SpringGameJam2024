import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;


public class Bag extends Tool {
    private static BufferedImage bagImage, cropImage;
    private final int x = 700;
    private final int y = 50;
    
    private int highlightedItem;
    

    public static enum ItemType{
        wheatSeed,
        wheatCrop
    }
    private ArrayList<Integer> itemAmounts;

    private final int NumberOfItemTypes = 2;

    public Bag()
    {
        super();
        if(bagImage == null)
        {
            bagImage = View.LOAD_IMAGE("Images\\toolImages\\seedBag.png");
        }

        cropImage = (cropImage == null) ? View.LOAD_IMAGE("Images\\cropImages\\cropSpriteSheet.png") : cropImage;

        itemAmounts = new ArrayList<Integer>();
        for(int i = 0; i < NumberOfItemTypes; i++)
        {
            itemAmounts.add(10);
        }

        highlightedItem = 0;
    }

    public void drawItems(Graphics g)
	{
		g.setFont(new Font("Dialog", Font.PLAIN, 25));
		g.setColor(new Color(123, 128, 135));

		for(int i = 0; i < 2; i++)
		{
            String itemName = "";
            String itemAmount = "";

            g.setColor(new Color(255, 255, 255));

            if(highlightedItem == i)
            {
                g.setColor(new Color(255, 255, 200));
            }

			g.fillRect(x, y + (i * 55), 300, 50);
            
            switch(i) 
            {
                case 0: 
                {
                    itemName = "Wheat Seeds";
                    itemAmount = "" + itemAmounts.get(0);
                    g.drawImage(cropImage.getSubimage(240, 240, 48, 48), x, y + (i*55) + 5, 40, 40, null);
                    
                } break;
                case 1:
                {
                    itemName = "Wheat";
                    itemAmount = "" + itemAmounts.get(1);
                    g.drawImage(cropImage.getSubimage(0, 240, 48, 48), x, y + (i*55) + 5, 40, 40, null);
                } break;
            }

            g.setColor(new Color(0, 0, 0));
			g.drawString(itemAmount + " " + itemName, x + 45, y + 38 + (i * 55));

		}
	}

    public void itemHighlightedDown()
    {
        highlightedItem+=1;
        highlightedItem = highlightedItem % NumberOfItemTypes;
    }

     public void itemHighlightedUp()
    {
        highlightedItem-=1;
        if(highlightedItem < 0)
        {
            highlightedItem = NumberOfItemTypes - 1;
        }
    }

    @Override
    public boolean isBag()
    {
        return true;
    }

    public void drawYourself(Graphics g, int x, int y)
    {
        g.drawImage(bagImage, x, y, 100, 100, null);
    }

}
