package Game.Snake.SnakeComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * @author fitor
 * 
 *         class represents the body of the snake with all of it's parts
 *
 */
@SuppressWarnings("serial")
public class Snake_body extends JPanel {

	/** size of the snake **/
	private final int size;

	/** Body of the snake **/
	private ArrayList<Point> body;

	/** head and tail of the snake **/
	private Point head;
	private Point tail;

	/** tail before location-update **/
	private Point prevTail;

	/** color of the snake **/
	private final Color color;

	/**
	 * Constructor
	 * 
	 * @param startingLocation of the snake
	 * @param size             of the snake
	 * @param color            of the snake
	 */
	public Snake_body(final Point sl, final int s, final Color c) {
		color = c;
		body = new ArrayList<>();
		size = s;
		body.add(new Point(sl));

		head = body.get(0);
		tail = body.get(body.size() - 1);
		prevTail = tail;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int) prevTail.getX(), (int) prevTail.getY(), size, size);
		g.setColor(Color.GRAY);
		g.drawRect((int) prevTail.getX(), (int) prevTail.getY(), size, size);

		g.setColor(color);
		g.fillRect((int) head.getX(), (int) head.getY(), size, size);
		g.setColor(Color.BLACK);
		g.drawRect((int) head.getX(), (int) head.getY(), size, size);

	}

	/** move the snake to the new location x,y **/
	public void move(final int x, final int y) {
		prevTail = tail;
		body.add(0, new Point((int) head.getX() + x, (int) head.getY() + y));
		body.remove(body.size() - 1);

		head = body.get(0);
		tail = body.get(body.size() - 1);

	}

	/** add a new BodyPart (locations will be adjusted on the run) **/
	public void addBody() {
		body.add(new Point(-500, -500));
		tail = body.get(body.size() - 1);
	}

	/** get the head of the snake **/
	public Point getHead() {
		return head;
	}

	/** get the tail of the snake **/
	public Point getTail() {
		return tail;
	}

	/** getter each location of the snake's body **/
	public ArrayList<Point> getLocations() {
		return body;
	}

}
