public class Grid {
    public static final int GRID_SIZE = 80;
	//This function makes sure that everytime a mouse positon is fed to a funcion, it adheres to the coordinate system.
	public static int CONVERT_CORD_TO_GRID(int t)
	{
		return t - (t % GRID_SIZE);
	}
}
