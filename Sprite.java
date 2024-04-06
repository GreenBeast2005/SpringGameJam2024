/******************************************************************
Name: 			Rylan Davidson
Date: 			03/14/2024
Description: 	This is the generic sprite class that all the other
                drawn objects will extend.
******************************************************************/

import java.awt.Graphics;

public abstract class Sprite {
    protected int x, y, w, h;
    protected boolean destroyed, hasCollisionHandling;

    public Sprite(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        destroyed = false;
        hasCollisionHandling = false;
    }

    public abstract void update();

    public abstract void draw(Graphics g, int scrollPosY);

    public abstract Json marshal();

    public abstract void handleCollision(Sprite collidingSprite);

    public boolean shouldBeDestroyed()
    {
        return destroyed;
    }

    public boolean doesHaveCollisionHandling()
    {
        return hasCollisionHandling;
    }

    public boolean isWall()
    {
        return false;
    }
    public boolean isPacman()
    {
        return false;
    }
    public boolean isGhost()
    {
        return false;
    }
    public boolean isPellet()
    {
        return false;
    }
    public boolean isFruit()
    {
        return false;
    }

    //Allows you to set the x and y for warping purposes
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    //Returns relevent sprite information
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getH()
    {
        return h;
    }
    public int getW()
    {
        return w;
    }

    //Used for collision logic, some of them are the same as getY and getX but it makes more sense to call them this way
    public int getLeftSidePosition()
    {
        return x;
    }
    public int getRightSidePosition()
    {
        return x + w;
    }
    public int getTopSidePosition()
    {
        return y;
    }
    public int getBottomSidePosition()
    {
        return y + h;
    }

    @Override
    public String toString()
    {
        return "Sprite (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}
