/******************************************************************
Name: 			Rylan Davidson
Date: 			03/14/2024
Description: 	This is the view class for our basic pacman java project
				this class is in charge of actually generating what you see
				in the JFrame.
******************************************************************/

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Iterator;
import java.io.File;

public class View extends JPanel
{
	private Model model;

	private int scrollPosY;

	//Constructor, gives the view class a reference to the controller, and the model.
	public View(Controller c, Model m)
	{
		model = m;

		scrollPosY = 0;
		
		c.setView(this);
	}

	//Used by other classes to load their images
	public static BufferedImage LOAD_IMAGE(String filePath)
	{
		try
		{
			return ImageIO.read(new File(filePath));
		}
		catch(Exception e) 
		{
			e.printStackTrace(System.err);
			System.out.println("Unable to load " + filePath);
			System.exit(1);
		}
		return null;
	}

	public void handleScrolling()
	{
		scrollPosY = model.getPacmanCenterYPosition() - (Game.GAME_WINDOW_SIZE_Y / 2);
		if(scrollPosY > Game.GAME_WORLD_SIZE_Y - Game.GAME_WINDOW_SIZE_Y)
			scrollPosY = Game.GAME_WORLD_SIZE_Y - Game.GAME_WINDOW_SIZE_Y;
		if(scrollPosY < 0)
			scrollPosY = 0;
	}

	//Already part of JPanel class, and by having this here it gets overwritten in order to display the things I choose
	public void paintComponent(Graphics g)
	{
		//This handles the logic of the camera moving around and stuff
		handleScrolling();

		//Sets the font of the little edit mode text in the bottom right corner
		g.setFont(new Font("Dialog", Font.PLAIN, 14));
		
		//checks if you are in edit mode and changes the background color if you are
		if(Controller.GetEditMode())
		{
			g.setColor(new Color(0, 0, 100));
		}else
		{
			g.setColor(new Color(0, 0, 0));
		}
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		//Draws all the walls in the model class wall list
		Iterator<Sprite> iter = model.getSpriteList().iterator();
		while(iter.hasNext())
		{
			Sprite temp = iter.next();
			temp.draw(g, scrollPosY);
		}

		//Checks again if you are in edit mode, its at the bottom because I want the text to render on top of everything else
		if(Controller.GetEditMode()) 
		{
			g.setColor(new Color(255, 255, 0));
			g.drawString("Edit Mode", this.getWidth() - 110, this.getHeight() - 30);
			
			//Adds a second piece of text that displays if you are in add wall mode or remove mode
			switch(Controller.GetSpecificEditMode())
			{
				case addWall: g.drawString("Adding Walls", this.getWidth() - 110, this.getHeight() - 15); break;
				case removeWall: g.drawString("Removing Walls", this.getWidth() - 110, this.getHeight() - 15); break;
				case addPellet: g.drawString("Adding Pellets", this.getWidth() - 110, this.getHeight() - 15); break;
				case addGhost: g.drawString("Adding Ghosts", this.getWidth() - 110, this.getHeight() - 15); break;
				case addFruit: g.drawString("Adding Fruits", this.getWidth() - 110, this.getHeight() - 15); break;
			}
		}
	}

	public int getScrollPosY()
	{
		return scrollPosY;
	}
}
