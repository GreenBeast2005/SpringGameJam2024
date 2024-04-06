/******************************************************************
Name: 			Rylan Davidson
Date: 			03/14/2024
Description: 	This is the model class for the basic pacman project.
				This class handles all the logic of the pacman game.
******************************************************************/
import java.util.ArrayList;
import java.util.Iterator;

public class Model
{
	private ArrayList<Sprite> sprites;

	//Rather than sprites being added directly to sprites they are added here then updated
	private ArrayList<Sprite> spritesToBeAdded;
	
	//Gets called in the remove all sprites function, used by update to make sure there isnt any weirdness happening with listeners
	private boolean spritesToBeCleared;

	//Obselete kind of sort of
	private Pacman pacman;

	//Default constructor
	public Model()
	{
		sprites = new ArrayList<Sprite>();
		spritesToBeAdded = new ArrayList<Sprite>();
		
		spritesToBeCleared = false;

		//I call game world size to make sure pacman starts in the center of the world, also this puts a pacman in the sprites collection
		pacman = new Pacman(Game.GAME_WORLD_SIZE_X/2 - 15, Game.GAME_WORLD_SIZE_Y/2 - 15, 30, 30);
		sprites.add(pacman);
	}

	//Constructor based on the Json object
	public Model(Json ob)
  	{
		this();
        unMarshal(ob);
    }

	//Marshals this object into a JSON DOM
    public Json marshal()
    {
        Json ob = Json.newObject();
        Json wallList = Json.newList();
		Json pelletList = Json.newList();
		Json ghostList = Json.newList();
		Json fruitList = Json.newList();
        for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).isWall())
			{
            	wallList.add(sprites.get(i).marshal());
			}
			if(sprites.get(i).isPellet())
			{
				pelletList.add(sprites.get(i).marshal());
			}
			if(sprites.get(i).isGhost())
			{
				ghostList.add(sprites.get(i).marshal());
			}
			if(sprites.get(i).isFruit())
			{
				fruitList.add(sprites.get(i).marshal());
			}
		}
		ob.add("walls", wallList);
		ob.add("pellets", pelletList);
		ob.add("ghosts", ghostList);
		ob.add("fruits", fruitList);
        return ob;
    }

	//A set function that sets the model based on the Json object
	public void unMarshal(Json ob)
	{
		sprites = new ArrayList<Sprite>();
		sprites.add(pacman);
        Json wallList = ob.get("walls");
		Json pelletList = ob.get("pellets");
		Json ghostList = ob.get("ghosts");
		Json fruitList = ob.get("fruits");
        for(int i = 0; i < wallList.size(); i++)
        	sprites.add(new Wall(wallList.get(i)));
		for(int i = 0; i < pelletList.size(); i++)
        	sprites.add(new Pellet(pelletList.get(i)));
		for(int i = 0; i < ghostList.size(); i++)
        	sprites.add(new Ghost(ghostList.get(i)));
		for(int i = 0; i < fruitList.size(); i++)
        	sprites.add(new Fruit(fruitList.get(i)));
    }

	//This update function is called in the main loop, doesnt do anything for this project, but I kept it anyway
	public void update() 
	{
		//This goes through and updates the sprites list with sprites that were recently added.
		for(int i = 0; i < spritesToBeAdded.size(); i++)
		{
			if(spritesToBeAdded.get(i).isWall() && !isWallThere(spritesToBeAdded.get(i)))
				sprites.add(spritesToBeAdded.get(i));
			if(spritesToBeAdded.get(i).isPellet() && !isPelletThere(spritesToBeAdded.get(i)))
				sprites.add(spritesToBeAdded.get(i));
			if(!(spritesToBeAdded.get(i).isWall() || spritesToBeAdded.get(i).isPellet()))
				sprites.add(spritesToBeAdded.get(i));
			spritesToBeAdded.remove(i);
		}

		//Makes sure sprites are properly cleared on the update timing
		if(spritesToBeCleared)
		{
			Pacman tempPac = pacman;
			sprites.clear();
			pacman = tempPac;
			sprites.add(pacman);
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
	}

	//Creates and adds pellets to sprites array list
	public void createPelletOnGrid(int x, int y, int scrollPosY)
	{
		y += scrollPosY;

		//These are here to make sure when you pass in the mouse x and y, their position is translated to be on the grid
		x = Pellet.CONVERT_CORD_TO_PELLET_GRID(x);
		y = Pellet.CONVERT_CORD_TO_PELLET_GRID(y);
				
		spritesToBeAdded.add(new Pellet(x, y));
	}

	public boolean isPelletThere(Sprite comparedPellet)
	{
		Iterator<Sprite> iter = sprites.iterator();
		while(iter.hasNext())
		{
			Sprite potentialPellet = iter.next();
			if(potentialPellet.isPellet())
				if(((Pellet)potentialPellet).isClicked(comparedPellet.getX(), comparedPellet.getY()))
					return true;
		}
		return false;
	}

	//Creates and adds walls to sprites array list
	public void createWallOnGrid(int x, int y, int scrollPosY)
	{
		y += scrollPosY;

		//These are here to make sure when you pass in the mouse x and y, their position is translated to be on the grid
		x = Wall.CONVERT_CORD_TO_GRID(x);
		y = Wall.CONVERT_CORD_TO_GRID(y);
				
		spritesToBeAdded.add(new Wall(x, y));
	}

	//Removes walls from array list
	public void removeWallOnGrid(int x, int y, int scrollPosY)
	{
		y += scrollPosY;

		//Makes sure that the mouse x and y are translated into the grid.
		x = Wall.CONVERT_CORD_TO_GRID(x);
		y = Wall.CONVERT_CORD_TO_GRID(y);

		//For loop that checks array list based on adjusted grid coordinates, since the coordinates are adjusted ykou dont have to
		//check the range based on width and height.
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite wall = sprites.get(i);
			if(wall.getX() == x && wall.getY() == y && wall.isWall())
			{
				sprites.remove(i);
				break;
			}
		}
	}

	//Used to add just normal sprites that dont have to be on the grid
	public void createGenericSprite(Sprite genericSprite, int scrollPosY)
	{
		genericSprite.setY(genericSprite.getY() + scrollPosY);
		spritesToBeAdded.add(genericSprite);
	}

	//This is used by view to do the scrolling stuff.
	public int getPacmanCenterYPosition()
	{
		return pacman.getCenterYPosition();
	}

	//Handles pacman warping around the screen, gets called in model.update
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

	//Removes all the walls
	public void removeAllSprites()
	{
		spritesToBeCleared = true;
	}

	//Checks array list to see if a wall is in the the x, y coordinates, in the wall class the coordinates are adjusted to be on the grid.
	public boolean isWallThere(Sprite comparedWall)
	{
		Iterator<Sprite> iter = sprites.iterator();
		while(iter.hasNext())
		{
			Sprite potentialWall = iter.next();
			if(potentialWall.isWall())
				if(((Wall)potentialWall).isClicked(comparedWall.getX(), comparedWall.getY()))
					return true;
		}
		return false;
	}

	//returns true if pacman is colliding with a wall
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

	//These are called in the controller class, handles what happens when input is recieved.
	public void recieveUpInput()
	{
		pacman.moveUp();
	}
	public void recieveDownInput()
	{
		pacman.moveDown();
	}
	public void recieveLeftInput()
	{
		pacman.moveLeft();
	}
	public void recieveRightInput()
	{
		pacman.moveRight();
	}
	public void recieveNoInput()
	{
		pacman.setNotMoving();
	}

	public ArrayList<Sprite> getSpriteList()
	{
		return sprites;
	}
}