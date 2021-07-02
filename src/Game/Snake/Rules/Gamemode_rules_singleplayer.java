package Game.Snake.Rules;

import Game.Snake.SnakeComponents.Snake_body;
import Game.Snake.SnakeComponents.Snake_playground;

/**
 * @author fitor
 * 
 * 
 *         class defines the rules for the singleplayer mode
 */
public class Gamemode_rules_singleplayer {

	/** playground the snake can move in **/
	private Snake_playground playground;
	/** body of the snake **/
	private Snake_body snakebody;

	/**
	 * Constructor
	 * 
	 * @param p
	 * @param b
	 */
	public Gamemode_rules_singleplayer(final Snake_playground p, final Snake_body b) {
		playground = p;
		snakebody = b;
	}

	/**
	 * @return true if the head of the snake is at the same location as it's body
	 **/
	public boolean biteHimself() {
		int hlp = (int) snakebody.getLocations().stream().filter(x -> x.equals(snakebody.getHead())).count();

		return (hlp >= 2) ? true : false;
	}

	/**
	 * 
	 * @return true if the snake covers the whole playground
	 */
	public boolean snakeTooBig() {
		return (snakebody.getLocations().size() == playground.getCellAmount());
	}

}
