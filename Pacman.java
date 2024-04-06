/******************************************************************
Name: 			Rylan Davidson
Date: 			03/14/2024
Description: 	This is the pacman class for our basic pacman game
                this holds all of pacmans data and cotains its images.
******************************************************************/

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pacman extends Sprite {
    private int preX, preY;
    private int speed;

    private final int PACMAN_IMAGES_PER_DIRECTION = 3;
	private final int PACMAN_NUMBER_OF_DIRECTIONS = 4;
    private int currentFrame;
    private static BufferedImage pacmanImages[][];

    private boolean isMoving;
    private Direction direction;

    //Im using this to make it easier for humans to read the code rather than tying dirictions to numbers
    private enum Direction
    {
        left,
        up,
        right,
        down
    }

    public Pacman(int x, int y, int h, int w)
    {
        super(x, y, h, w);
        preX = x;
        preY = y;
        direction = Direction.down;
        currentFrame = 0;

        speed = 5;

        hasCollisionHandling = true;

        if(pacmanImages == null)
        {
            pacmanImages = new BufferedImage[PACMAN_NUMBER_OF_DIRECTIONS][PACMAN_IMAGES_PER_DIRECTION];
            for(int i = 0; i < PACMAN_NUMBER_OF_DIRECTIONS; i++)
            {
                for(int j = 0; j < PACMAN_IMAGES_PER_DIRECTION; j++)
                {
                    pacmanImages[i][j] = View.LOAD_IMAGE("Images\\pacman" + i + j + ".png");
                    System.out.println("Loaded Pacman Image"+i+j);
                }
            }
        }
    }

    //Constructor based on json object
    public Pacman(Json ob)
  	{
        this((int)ob.getLong("x"), (int)ob.getLong("y"), (int)ob.getLong("h"), (int)ob.getLong("w"));
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

    //Gets called inside model.update currently does nothing, but has potential to do something later
    @Override
    public void update() { }

    public void setPosition(int x, int y, int h, int w)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    @Override
    public void draw(Graphics g, int scrollPosY)
    {
        //Example of using the direction enum, when you do .ordinal it gives the position of the direction in the enum
        g.drawImage(pacmanImages[direction.ordinal()][currentFrame/2], x, y - scrollPosY, w, h, null);
        if(isMoving)
		{
			updateFrame();
		}
    }

    //Gets called if pacman is moving, doesnt get called if its not moving
    public void updateFrame()
	{
		currentFrame = (currentFrame + 1) % (PACMAN_IMAGES_PER_DIRECTION * 2);
	}

    //Used to determine the direction pacman should be adjusted based on the collision
    public int getPreLeftSidePosition()
    {
        return preX;
    }
    public int getPreRightSidePosition()
    {
        return preX + w;
    }
    public int getPreTopSidePosition()
    {
        return preY;
    }
    public int getPreBottomSidePosition()
    {
        return preY + h;
    }

    //Gets called in the view class to make sure pacman sits in the very center of the screen
    public int getCenterYPosition()
    {
        return y + (h / 2);
    }

    //Used to move pacman around
    public void moveUp()
    {
        preY = y;
        y -= speed;
        direction = Direction.up;
        isMoving = true;
    }
    public void moveDown()
    {
        preY = y;
        y += speed;
        direction = Direction.down;
        isMoving = true;
    }
    public void moveLeft()
    {
        preX = x;
        x -= speed;
        direction = Direction.left;
        isMoving = true;
    }
    public void moveRight()
    {
        preX = x;
        x += speed;
        direction = Direction.right;
        isMoving = true;
    }

    //Gets called when pacman stops moving to stop the 
    public void setNotMoving()
    {
        isMoving = false;
    }

    @Override
    public boolean isPacman()
    {
        return true;
    }

    //Used for debuging, gives you all of pacmans relevent information
    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    //This function takes the wall pacman is colliding with and then adjusts the position based on that.
    public void handleCollision(Sprite collidedSprite)
    {
        if(collidedSprite.isWall())
        {
            if(getPreRightSidePosition() <= collidedSprite.getLeftSidePosition())
            {
                x = collidedSprite.getLeftSidePosition() - w - 1;
            }
            if(getPreLeftSidePosition() >= collidedSprite.getRightSidePosition())
            {
                x = collidedSprite.getRightSidePosition() + 1;
            }
            if(getPreBottomSidePosition() <= collidedSprite.getTopSidePosition())
            {
                y = collidedSprite.getTopSidePosition() - h - 1;
            }
            if(getPreTopSidePosition() >= collidedSprite.getBottomSidePosition())
            {
                y = collidedSprite.getBottomSidePosition() + 1;
            }
        }
    }
}
