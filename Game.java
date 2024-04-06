/******************************************************************
Name: 			Rylan Davidson
Date: 			03/14/2024
Description: 	This is the main class file for the basic pacman project
				this is where all the main classes are instantiated
				also contains the size of the game world.
******************************************************************/

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;

public class Game extends JFrame
{
	private Model model;
	private Controller controller;
	private View view;

	public static final int GAME_WINDOW_SIZE_X = 700;
	public static final int GAME_WINDOW_SIZE_Y = 700;
	public static final int GAME_WORLD_SIZE_X = 700;
	public static final int GAME_WORLD_SIZE_Y = 1400;

	public Game()
	{
		//Initializes the model, controller, view so that they can be 
		Json firstLevelOb = null;
		try {
			firstLevelOb = Json.load("map.json");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.out.println("Unable to load the map");
			System.exit(1);
		}
		
		model = new Model(firstLevelOb);

		controller = new Controller(model);
		view = new View(controller, model);

		//Enables the view component to actually read input from the mouse, as you can see this is part of JPanel
		view.addMouseListener(controller);
		view.addMouseMotionListener(controller);
		//This enables this JFrame componet to read input from the keyboard, this is part of JFrame
		this.addKeyListener(controller);

		//These are just some of the generic setup for the Window
		this.setTitle("Assignment 5 Stage 1 Pacman!");
		
		//I changed the set size method to this, because the setsize method includes the window border and I want control of the size of the content pane
		view.setPreferredSize(new Dimension(GAME_WINDOW_SIZE_X, GAME_WINDOW_SIZE_Y)); 
		this.setFocusable(true);
		this.getContentPane().add(view); //Right here we set up the JFrame to display our JPanel object
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			//Go to sleep for 40 milliseconds, this leads to a frame rate of 25 frames per second
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	//The magic main function, notice how short it is, most of the work is done in the actual classes not the main function
	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}
