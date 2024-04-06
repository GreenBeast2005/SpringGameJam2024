/******************************************************************
Name: 			Rylan Davidson
Date: 			03/14/2024
Description: 	This is the model class for the basic player project.
				This class handles all the logic of the player game.
******************************************************************/
import java.util.ArrayList;
import java.util.Iterator;

public class Model
{
	private ArrayList<Sprite> sprites;
	
	private static ArrayList<Tool> toolBelt;
	private static int currentlySelectedToolNumber;
	
	//Gets called in the remove all sprites function, used by update to make sure there isnt any weirdness happening with listeners
	private boolean spritesToBeCleared;

	private static boolean DayNightCycleHasPassed;

	//Obselete kind of sort of
	private Player player;

	//Default constructor
	public Model()
	{
		sprites = new ArrayList<Sprite>();
		toolBelt = new ArrayList<Tool>();
		toolBelt.add(new Hand());
		toolBelt.add(new WateringCan());
		toolBelt.add(new Hoe());
		toolBelt.add(new Bag());

		currentlySelectedToolNumber = 0;
		
		spritesToBeCleared = false;

		//I call game world size to make sure player starts in the center of the world, also this puts a player in the sprites collection
		player = new Player(Game.GAME_WORLD_SIZE_X/2 - 15, Game.GAME_WORLD_SIZE_Y/2 - 15, 30, 30);
		sprites.add(player);
	}

	//Constructor based on the Json object
	public Model(Json ob)
  	{
		this();
        // unMarshal(ob);
    }

	//Marshals this object into a JSON DOM
    // public Json marshal()
    // {
    //     Json ob = Json.newObject();
    //     Json wallList = Json.newList();
	// 	Json pelletList = Json.newList();
	// 	Json ghostList = Json.newList();
	// 	Json fruitList = Json.newList();
    //     for(int i = 0; i < sprites.size(); i++)
	// 	{
	// 		if(sprites.get(i).isWall())
	// 		{
    //         	wallList.add(sprites.get(i).marshal());
	// 		}
	// 		if(sprites.get(i).isPellet())
	// 		{
	// 			pelletList.add(sprites.get(i).marshal());
	// 		}
	// 		if(sprites.get(i).isGhost())
	// 		{
	// 			ghostList.add(sprites.get(i).marshal());
	// 		}
	// 		if(sprites.get(i).isFruit())
	// 		{
	// 			fruitList.add(sprites.get(i).marshal());
	// 		}
	// 	}
	// 	ob.add("walls", wallList);
	// 	ob.add("pellets", pelletList);
	// 	ob.add("ghosts", ghostList);
	// 	ob.add("fruits", fruitList);
    //     return ob;
    // }

	//A set function that sets the model based on the Json object
	// public void unMarshal(Json ob)
	// {
	// 	sprites = new ArrayList<Sprite>();
	// 	sprites.add(player);
    //     Json wallList = ob.get("walls");
	// 	Json pelletList = ob.get("pellets");
	// 	Json ghostList = ob.get("ghosts");
	// 	Json fruitList = ob.get("fruits");
    //     for(int i = 0; i < wallList.size(); i++)
    //     	sprites.add(new Wall(wallList.get(i)));
	// 	for(int i = 0; i < pelletList.size(); i++)
    //     	sprites.add(new Pellet(pelletList.get(i)));
	// 	for(int i = 0; i < ghostList.size(); i++)
    //     	sprites.add(new Ghost(ghostList.get(i)));
	// 	for(int i = 0; i < fruitList.size(); i++)
    //     	sprites.add(new Fruit(fruitList.get(i)));
    // }

	//This update function is called in the main loop, doesnt do anything for this project, but I kept it anyway
	public void update() 
	{

		//Makes sure sprites are properly cleared on the update timing
		if(spritesToBeCleared)
		{
			Player tempPac = player;
			sprites.clear();
			player = tempPac;
			sprites.add(player);
			spritesToBeCleared = false;
		}

		//Goes through and updates sprites
		//Goes through and handles warping
		//Goes through and checks for collisions
		Iterator<Sprite> checkingSprites = sprites.iterator();
		while(checkingSprites.hasNext())
		{
			Sprite checkingSprite = checkingSprites.next();
			checkingSprite.update();
			handleWarping(checkingSprite);

			if(checkingSprite.shouldBeDestroyed())
			{
				checkingSprites.remove();
				continue;
			}
			if(!checkingSprite.doesHaveCollisionHandling())
			{
				continue;
			}
			Iterator<Sprite> collidingSprites = sprites.iterator();
			while(collidingSprites.hasNext()) 
			{
				Sprite collidingSprite = collidingSprites.next();

				if(checkingSprite != collidingSprite && doSpritesCollide(checkingSprite, collidingSprite))
				{
					checkingSprite.handleCollision(collidingSprite);
				}
			}
		}

		if(DayNightCycleHasPassed)
		{
			Iterator<Sprite> growingSprites = sprites.iterator();
			while(growingSprites.hasNext())
			{
				Sprite temp = growingSprites.next();
				temp.passTime();
			}
			DayNightCycleHasPassed = false;
		}
	}


	

	//This is used by view to do the scrolling stuff.
	public int getPlayerCenterYPosition()
	{
		return player.getCenterYPosition();
	}

	//Handles player warping around the screen, gets called in model.update
	public void handleWarping(Sprite warpingSprite)
	{
		if(warpingSprite.getLeftSidePosition() > Game.GAME_WORLD_SIZE_X)
		{
			warpingSprite.setX(0 - warpingSprite.getW());
		}else if(warpingSprite.getRightSidePosition() < 0)
		{
			warpingSprite.setX(Game.GAME_WORLD_SIZE_X);
		}else if(warpingSprite.getTopSidePosition() > Game.GAME_WORLD_SIZE_Y)
		{
			warpingSprite.setY(0 - warpingSprite.getH());
		}else if(warpingSprite.getBottomSidePosition() < 0)
		{
			warpingSprite.setY(Game.GAME_WORLD_SIZE_Y);
		}
	}

	public void handleCheckingInteraction()
	{
		
		Iterator<Sprite> checkingSprites = sprites.iterator();
		while(checkingSprites.hasNext())
		{
			Sprite checkingSprite = checkingSprites.next();
			if(checkingSprite.isInteractable() && isInteractingWithSprite(checkingSprite, player.getInteractXSpot(), player.getInteractYSpot()))
			{
				((Interactable)checkingSprite).interact();
			}
		}
	}
	public void handleWateringInteraction()
	{
		Iterator<Sprite> checkingSprites = sprites.iterator();
		while(checkingSprites.hasNext())
		{
			Sprite checkingSprite = checkingSprites.next();
			if(isInteractingWithSprite(checkingSprite, player.getInteractXSpot(), player.getInteractYSpot()))
			{
				checkingSprite.water();
			}
		}
	}
	public void selectTool(int selectedTool)
	{
		if(selectedTool < toolBelt.size())
			currentlySelectedToolNumber = selectedTool;
	}

	//Removes all the walls
	public void removeAllSprites()
	{
		spritesToBeCleared = true;
	}

	//Creates and adds walls to sprites array list
	public void createSpriteOnGrid(int x, int y, int scrollPosY, Sprite spriteToBeMade)
	{
		y += scrollPosY;

		//These are here to make sure when you pass in the mouse x and y, their position is translated to be on the grid
		x = Grid.CONVERT_CORD_TO_GRID(x);
		y = Grid.CONVERT_CORD_TO_GRID(y);

		spriteToBeMade.setX(x);
		spriteToBeMade.setY(y);
				
		sprites.add(spriteToBeMade);
	}
	public void handleCreateFarmland(int scrollPosY)
	{
		int x = player.getInteractXSpot();
		int y = player.getInteractYSpot();

		//These are here to make sure when you pass in the mouse x and y, their position is translated to be on the grid
		x = Grid.CONVERT_CORD_TO_GRID(x);
		y = Grid.CONVERT_CORD_TO_GRID(y);

		Sprite spriteToBeMade = new FarmLand(x, y);
				
		sprites.add(spriteToBeMade);
	}

	
	//returns true if player is colliding with a wall
	public boolean doSpritesCollide(Sprite sprite1, Sprite sprite2)
	{
		if(sprite1.getRightSidePosition() < sprite2.getLeftSidePosition())
			return false;
		if(sprite1.getLeftSidePosition() > sprite2.getRightSidePosition())
			return false;
		if(sprite1.getBottomSidePosition() < sprite2.getTopSidePosition())
			return false;
		if(sprite1.getTopSidePosition() > sprite2.getBottomSidePosition())
			return false;
		return true;
	}
	//returns true if player is colliding with a wall
	public boolean isInteractingWithSprite(Sprite sprite1, int x, int y)
	{
		if(sprite1.getRightSidePosition() < x)
			return false;
		if(sprite1.getLeftSidePosition() > x)
			return false;
		if(sprite1.getBottomSidePosition() < y)
			return false;
		if(sprite1.getTopSidePosition() > y)
			return false;
		return true;
	}

	public Player getPlayerSprite()
	{
		return player;
	}
	
	//These are called in the controller class, handles what happens when input is recieved.
	public void recieveUpInput()
	{
		player.moveUp();
	}
	public void recieveDownInput()
	{
		player.moveDown();
	}
	public void recieveLeftInput()
	{
		player.moveLeft();
	}
	public void recieveRightInput()
	{
		player.moveRight();
	}
	public void recieveNoInput()
	{
		player.setNotMoving();
	}

	//Static functions
	public static ArrayList<Tool> GetToolBelt()
	{
		return toolBelt;
	}

	public static int GetSelectedToolNumber()
	{
		return currentlySelectedToolNumber;
	}

	public static void TriggerDayCycle()
	{
		DayNightCycleHasPassed = true;
	}

	public ArrayList<Sprite> getSpriteList()
	{
		return sprites;
	}
}