import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;


public class Bag extends Tool {
    private static BufferedImage bagImage, cropImage, instantImage, signImage;
    private final int x = 700;
    private final int y = 50;
    
    private int highlightedItem;
    

    public static enum ItemType{
        wheatSeed,
        wheatCrop,
        radishSeed,
        radishCrop,
        InstantGrow
    }
    private ArrayList<Integer> itemAmounts;

    private final int NumberOfItemTypes = 5;

    public Bag()
    {
        super();
        if(bagImage == null)
        {
            bagImage = View.LOAD_IMAGE("Images\\toolImages\\seedBag.png");
        }

        cropImage = (cropImage == null) ? View.LOAD_IMAGE("Images\\cropImages\\cropSpriteSheet.png") : cropImage;
        instantImage = (instantImage == null) ? View.LOAD_IMAGE("Images\\cropImages\\instantImage.png") : instantImage;
        signImage = (signImage == null) ? View.LOAD_IMAGE("Images\\toolImages\\InfoSign.png") : signImage;

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

		for(int i = 0; i < NumberOfItemTypes; i++)
		{
            String itemName = "";
            String itemAmount = "";

         

            

			g.drawImage(signImage, x, y + (i * 55), 300, 50, null);
 
            if(highlightedItem == i)
            {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(x+5, y + (i * 55) + 5, 295, 50);
            }
            
            switch(i) 
            {
                case 0: 
                {
                    itemName = "Wheat Seeds";
                    itemAmount = "" + itemAmounts.get(0);
                    g.drawImage(cropImage.getSubimage(240, 240, 48, 48), x+15, y + (i*55) + 5, 40, 40, null);
                    
                } break;
                case 1:
                {
                    itemName = "Wheat";
                    itemAmount = "" + itemAmounts.get(1);
                    g.drawImage(cropImage.getSubimage(0, 240, 48, 48), x+15, y + (i*55) + 5, 40, 40, null);
                } break;
                case 2:
                {
                    itemName = "Radish Seed";
                    itemAmount = "" + itemAmounts.get(2);
                    g.drawImage(cropImage.getSubimage(240, 0, 48, 48), x+15, y + (i*55) + 5, 40, 40, null);
                } break;
                case 3:
                {
                    itemName = "Radish";
                    itemAmount = "" + itemAmounts.get(3);
                    g.drawImage(cropImage.getSubimage(0, 0, 48, 48), x+15, y + (i*55) + 5, 40, 40, null);
                } break;
                case 4:
                {
                    itemName = "Instant Grow";
                    itemAmount = "" + itemAmounts.get(4);
                    g.drawImage(instantImage, x+15, y + (i*55) + 5, 40, 40, null);
                } break;
            }

            g.setColor(new Color(0, 0, 0));
			g.drawString(itemAmount + " " + itemName, x + 60, y + 38 + (i * 55));

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

    public ItemType getHighlightedItem()
    {
        return ItemType.values()[highlightedItem];
    }

    public void useSeed(ItemType seed)
    {
        itemAmounts.set(seed.ordinal(), itemAmounts.get(seed.ordinal()) - 1);
    }

    public int sellCrops()
    {
        int sellValue = 0;
        sellValue += (itemAmounts.get(ItemType.wheatCrop.ordinal()) * 40);
        itemAmounts.set(ItemType.wheatCrop.ordinal(), 0);

        sellValue += (itemAmounts.get(ItemType.radishCrop.ordinal()) * 80);
        itemAmounts.set(ItemType.radishCrop.ordinal(), 0);

        return sellValue;
    }

    public void harvestCrop(ItemType crop)
    {
        switch(crop)
        {
            case wheatSeed:
                {
                    itemAmounts.set(ItemType.wheatSeed.ordinal(), itemAmounts.get(ItemType.wheatSeed.ordinal()) + 2);
                    itemAmounts.set(ItemType.wheatCrop.ordinal(), itemAmounts.get(ItemType.wheatCrop.ordinal()) + 1);
                } break;
            case radishSeed:
                {
                    itemAmounts.set(ItemType.radishSeed.ordinal(), itemAmounts.get(ItemType.radishSeed.ordinal()) + 2);
                    itemAmounts.set(ItemType.radishCrop.ordinal(), itemAmounts.get(ItemType.radishCrop.ordinal()) + 1);
                } break;
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
