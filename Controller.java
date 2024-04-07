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

	private boolean isShift;

	//I made these bad boys static so that I can reference them in view without an instance of controller to alter the view
	private static boolean editMode;
	private static SpecificEditMode specificEditMode;
	
	//Since this is a data type used by controller and view I figured making it public statatic is fine
	public static enum SpecificEditMode
	{
		addCrop,
		addDayCycleInteractable,
		addSalesPerson,
		addWall,
		addFloor
	}

	//Constructor
	public Controller(Model m)
	{
		model = m;
		editMode = false;
		specificEditMode = SpecificEditMode.addCrop;
		isShift = false;
	}

	//Update function that basically is just in charge of calling model for relevent input.
	public void update() 
	{ 
		if(!isShift)
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
				case addDayCycleInteractable: model.createSpriteOnGrid(e.getX(), e.getY(), view.getScrollPosY(), new DayCycleTrigger(0, 0)); break;
				case addSalesPerson: model.createSpriteOnGrid(e.getX(), e.getY(), view.getScrollPosY(), new SellingInteractable(0, 0)); break;
				case addWall: model.createSpriteOnGrid(e.getX(), e.getY(), view.getScrollPosY(), new Wall(0, 0)); break;
				case addFloor: model.createSpriteOnGrid(e.getX(), e.getY(), view.getScrollPosY(), new Floor(0, 0)); break;
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
			
			case KeyEvent.VK_SHIFT:
				if(Model.GetToolBelt().get(Model.GetSelectedToolNumber()).isBag()) 
					isShift = true; 
				break;
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
			case KeyEvent.VK_UP: 
				if(isShift)
					((Bag)Model.GetToolBelt().get(Model.GetSelectedToolNumber())).itemHighlightedUp();
				moveUp = false; break;
			case KeyEvent.VK_DOWN:
				if(isShift)
					((Bag)Model.GetToolBelt().get(Model.GetSelectedToolNumber())).itemHighlightedDown();
			 	moveDown = false; break;
			case KeyEvent.VK_LEFT: moveLeft = false; break;
			case KeyEvent.VK_RIGHT: moveRight = false; break;

			case KeyEvent.VK_ENTER: 
				if(Model.GetToolBelt().get(Model.GetSelectedToolNumber()).isHoe())
				{
					model.handleCreateFarmland(view.getScrollPosY());
				}else if(Model.GetToolBelt().get(Model.GetSelectedToolNumber()).isWateringCan()){
					model.handleWateringInteraction();
				}else if(Model.GetToolBelt().get(Model.GetSelectedToolNumber()).isHand()){
					model.handleCheckingInteraction();
				}else if(Model.GetToolBelt().get(Model.GetSelectedToolNumber()).isBag()){
					model.handlePlantingInteraction();
				}
				break;
			
			case KeyEvent.VK_SHIFT: isShift = false; break;

			case KeyEvent.VK_1: model.selectTool(0); break;
			case KeyEvent.VK_2: model.selectTool(1); break;
			case KeyEvent.VK_3: model.selectTool(2); break;
			case KeyEvent.VK_4: model.selectTool(3); break;
		} 
		char keyPressed = Character.toLowerCase(e.getKeyChar());
		switch(keyPressed)
		{
			case 'e': editMode = !editMode; break;
			case 'a':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addCrop;
				}
				break;
			case 'r':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addDayCycleInteractable;
				}
				break;
			case 'n':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addSalesPerson;
				}
				break;
			case 'd':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addWall;
				}
				break;
			case 'f':
				if(editMode)
				{
					specificEditMode = SpecificEditMode.addFloor;
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
			// case 'l':
			// 	Json loadOb = null;
			// 	try 
			// 	{
			// 		loadOb = Json.load("map.json");
			// 		System.out.println("Loading Level!");
			// 	}catch (RuntimeException error) {
			// 		error.printStackTrace(System.err);
			// 		System.out.println("Unable to load level file :(");
			// 		System.exit(1);
			// 	}
				
			// 	model.unMarshal(loadOb);
			// 	break;
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
