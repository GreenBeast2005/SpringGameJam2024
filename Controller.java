/******************************************************************
Name: 			Rylan Davidson
Date: 			03/14/2024
Description: 	This is the controller class for our basic pacman assignment
				As a basic description, this class handles all the input
				for our "game", that input includes keyboard, and mouse input.
******************************************************************/

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements MouseListener, MouseMotionListener, KeyListener
{
	//Lets the controller have referene to the model and view
	private View view;
	private Model model;

	private boolean moveUp;
	private boolean moveDown;
	private boolean moveRight;
	private boolean moveLeft;

	//I made these bad boys static so that I can reference them in view without an instance of controller to alter the view
	private static boolean editMode;
	private static SpecificEditMode specificEditMode;
	
	//Since this is a data type used by controller and view I figured making it public statatic is fine
	public static enum SpecificEditMode
	{
		addWall,
		removeWall,
		addPellet,
		addGhost,
		addFruit
	}

	//Constructor
	public Controller(Model m)
	{
		model = m;
		editMode = false;
		specificEditMode = SpecificEditMode.addWall;
	}

	//Update function that basically is just in charge of calling model for relevent input.
	public void update() 
	{ 
		if(moveUp) {
			model.recieveUpInput();
		}else if(moveDown) {
			model.recieveDownInput();
		}else if(moveLeft) {
			model.recieveLeftInput();
		}else if(moveRight) {
			model.recieveRightInput();
		}else{
			model.recieveNoInput();
		}
	}

	void setView(View v)
	{
		view = v;
	}

	//This function is called when you click on the window
	public void mousePressed(MouseEvent e)
	{
		if(editMode)
		{
			//Does a check if you are clicking on an already existing wall, and if you are
			//It toggles you into remove wall mode automatically
			switch(specificEditMode)
			{
				case addWall: model.createWallOnGrid(e.getX(), e.getY(), view.getScrollPosY()); break;
				case removeWall: model.removeWallOnGrid(e.getX(), e.getY(), view.getScrollPosY()); break;
				case addPellet: model.createPelletOnGrid(e.getX(), e.getY(), view.getScrollPosY()); break;
				case addGhost: model.createGenericSprite(new Ghost(e.getX(), e.getY()), view.getScrollPosY()); break;
				case addFruit: model.createGenericSprite(new Fruit(e.getX(), e.getY()), view.getScrollPosY()); break;
			}
		}
	}

	//These are a bunch of empty functions, the are required since this is a mouseListener class as well
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { }

	//Allows you to "Draw" walls or erase lots of walls at once
	public void mouseDragged(MouseEvent e)
	{
		if(editMode)
		{
			switch(specificEditMode)
			{
				case addWall: model.createWallOnGrid(e.getX(), e.getY(), view.getScrollPosY()); break;
				case removeWall: model.removeWallOnGrid(e.getX(), e.getY(), view.getScrollPosY()); break;
				case addPellet: model.createPelletOnGrid(e.getX(), e.getY(), view.getScrollPosY()); break;
				default: break;
			}
		}
	}

	public void mouseMoved(MouseEvent e) { }

	//Gets called when the key is pressed down, used for the exiting program calls, and the scrolling window
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_ESCAPE: System.exit(0); break;
			
			//Since this function is called multiple times I just have the functions being called as many times as needed
			case KeyEvent.VK_UP: moveUp = true; break;
			case KeyEvent.VK_DOWN: moveDown = true; break;
			case KeyEvent.VK_LEFT: moveLeft = true; break;
			case KeyEvent.VK_RIGHT: moveRight = true; break;
		}
		char keyPressed = Character.toLowerCase(e.getKeyChar());
		if(keyPressed == 'q')
		{
			System.exit(0);
		}
	}

	//Gets called when the key is released
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: moveUp = false; break;
			case KeyEvent.VK_DOWN: moveDown = false; break;
			case KeyEvent.VK_LEFT: moveLeft = false; break;
			case KeyEvent.VK_RIGHT: moveRight = false; break;
		}
		char keyPressed = Character.toLowerCase(e.getKeyChar());
		switch(keyPressed)
		{
			case 'e': editMode = !editMode; break;
			case 'a':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addWall;
				}
				break;
			case 'r':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.removeWall;
				}
				break;
			case 'p':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addPellet;
				}
				break;
			case 'g':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addGhost;
				}
				break;
			case 'f':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addFruit;
				}
				break;
			case 'c': 
				if(editMode)
				{
					model.removeAllSprites();
					System.out.println("Removed all sprites!");
				}
				break;
			case 's':
				Json saveOb = model.marshal();
				saveOb.save("map.json");
				System.out.println("Saving Level!");
				break;
			case 'l':
				Json loadOb = null;
				try 
				{
					loadOb = Json.load("map.json");
					System.out.println("Loading Level!");
				}catch (RuntimeException error) {
					error.printStackTrace(System.err);
					System.out.println("Unable to load level file :(");
					System.exit(1);
				}
				
				model.unMarshal(loadOb);
				break;
		}
	}

	//Empty function that is required for keyListener
	public void keyTyped(KeyEvent e) { }

	public static SpecificEditMode GetSpecificEditMode()
	{
		return specificEditMode;
	}

	public static boolean GetEditMode()
	{
		return editMode;
	}
}
