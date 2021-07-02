package Game.Snake.SnakeComponents;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * @author fitor
 * 
 *         Playground the snake is able to move in (needed for random snack
 *         generation and error testing)
 *
 */
public class Snake_playground {

	/** walls of the gamefield **/
	private final int left, up, right, down;
	/** size of each raster **/
	private final int size;
	/** all Possible locations inside of the playing field **/
	private ArrayList<Point> allLocations;

	/**
	 * Constructor
	 * 
	 * @param left   wall
	 * @param upper  wall
	 * @param right  wall
	 * @param bottom wall
	 * @param size   of each cell
	 */
	public Snake_playground(final int l, final int u, final int r, final int d, final int size) {
		left = l;
		up = u;
		right = r;
		down = d;
		this.size = size;

		allLocations = new ArrayList<>();

		setAllLocations();
	}

	/**
	 * set the exact possible location of each cell
	 */
	private void setAllLocations() {
		// edges of the playground
		final int a = right - left;
		final int b = down - up;

		// amount of cells in the grid
		final int amountHorizontal = a / size;
		final int amountVertical = b / size;

		// indexLocations
		int yValue = up;
		int xValue = left;

		for (int i = 0; i < amountVertical; ++i) {
			for (int j = 0; j < amountHorizontal; j++) {
				allLocations.add(new Point(xValue, yValue));
				xValue += size;
			}
			yValue += size;
			xValue = left;

		}

	}

	/**
	 * 
	 * @return left boundary of the wall
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * 
	 * @return upper boundary of the wall
	 */
	public int getUp() {
		return up;
	}

	/**
	 * 
	 * @return right boundary of the wall
	 */
	public int getRight() {
		return right;
	}

	/**
	 * 
	 * @return bottom boundary of the wall
	 */
	public int getDown() {
		return down;
	}

	/**
	 * 
	 * @return size of each cell
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 
	 * @param i
	 * @return location at given index
	 */
	public Point getLocationAt(final int i) {
		return allLocations.get(i);
	}

	/** get the size of the arraylist, in which all cells are stored **/
	public int getCellAmount() {
		return allLocations.size();
	}

}
