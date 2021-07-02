package Game.Snake;



import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import Game.Snake.SnakeComponents.Snake_body;
import Game.Snake.SnakeComponents.Snake_playground;

/**
 * 
 * @author fitor
 *
 *         Interface of the gamemodes
 */
public interface IGamemode_template extends ActionListener {

	/** Functions initializes the KeyListener of the gamemode **/
	void initKeyAdapter();

	/**
	 * Functions checks whether a player is able to eat the snack
	 * 
	 * (0 -> no one can eat | 1 -> player1 can eat | 2 -> player2 can eat)
	 */
	default boolean eat(final Point head, final Point snackLocation) {
		return snackLocation.equals(head);
	}

	/**
	 * 
	 * @param p
	 * @return true if the cell at Point p is occupied by a player
	 */
	default boolean occupiedCell(final Point p, final Snake_body... bodies) {
		boolean result = false;

		for (Snake_body x : bodies) {
			if (x.getLocations().contains(p)) {
				result = true;
			}
		}
		return result;

	}

	/**
	 * function sets a new location for the snack the snake needs to eat in order to
	 * grow
	 */
	default Point getSnackLocation(final Snake_playground playground, final Snake_body... bodies) {
		// generate a random locations
		int hlp;
		Point possibleLocation = new Point(0, 0);
		while (true) {
			// generate a random locations
			hlp = (int) (Math.random() * (((playground.getCellAmount() - 1)) + 1));
			possibleLocation = playground.getLocationAt(hlp);
			if (!occupiedCell(possibleLocation, bodies)) {
				break;
			}
		}
		return possibleLocation;
	}

	/**
	 * function adjusts the playground's sizes dynamicly to the user's Monitor
	 */
	void setPlaygroundSizes();

	/** stop all timer and dispose the frame **/
	void standardKeyEvents(KeyEvent e);

	/** exit the game **/
	void exitGame();

	/** send a message when the game has ended **/
	void sendMessage(final String message);
	
	

	
	
	
}
