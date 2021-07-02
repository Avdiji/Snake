
package Game.Snake.SnakeComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * 
 * @author fitor
 *
 *
 *         Class represents the consumable snack, the snake needs to grow
 */
@SuppressWarnings("serial")
public class Snake_snack extends JPanel {

	/** Color of the snack **/
	private final static Color snackColor = Color.GREEN;
	/** location of the snack **/
	private Point location;
	/** size of the snack **/
	private final int size;

	/**
	 * Constructor
	 * 
	 * @param size
	 */
	public Snake_snack(final int size) {
		this.size = size;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(snackColor);
		g.fillOval((int) location.getX(), (int) location.getY(), size, size);
	}

	/**
	 * getter for the current location of the cake
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * setter for the location of the cake
	 */
	public void setLocation(final Point p) {
		location = p;
	}

}
