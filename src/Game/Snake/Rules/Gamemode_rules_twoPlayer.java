package Game.Snake.Rules;

import Game.Snake.SnakeComponents.Snake_body;
import Game.Snake.SnakeComponents.Snake_playground;

/**
 * 
 * @author fitor
 *
 *
 *         class defines the rules for the twoplayer mode
 */
public class Gamemode_rules_twoPlayer {

	/** player in this gamemode **/
	private Snake_body player1;
	private Snake_body player2;

	/** playground of the player'S **/
	private Snake_playground playground;

	/**
	 * 
	 * @param p
	 * @param bodies
	 */
	public Gamemode_rules_twoPlayer(final Snake_playground p, Snake_body p1, Snake_body p2) {
		playground = p;
		player1 = p1;
		player2 = p2;
	}

	/**
	 * 
	 * @return 0 if no player has bitten himself, 1 if player1 bite himself, 2 if
	 *         player2 bite himself
	 */
	public int playerBiteHimself() {
		int result = 0;
		int p1 = (int) player1.getLocations().stream().filter(x -> x.equals(player1.getHead())).count();
		int p2 = (int) player2.getLocations().stream().filter(x -> x.equals(player2.getHead())).count();

		result = (p1 >= 2) ? 1 : result;
		result = (p2 >= 2) ? 2 : result;

		return result;

	}

	/**
	 * 
	 * @return 0 if no one has bitten another player, 1 if p1 bit p2, 2 if p2 bit
	 *         p1, 3 if both players have bitten each other at the same moment
	 */
	public int playerBitEnemy() {
		int result = 0;

		boolean p1 = player2.getLocations().contains(player1.getHead());
		boolean p2 = player1.getLocations().contains(player2.getHead());

		result = p1 ? 1 : result;
		result = p2 ? 2 : result;
		result = (p1 && p2) ? 3 : result;

		return result;

	}

	/**
	 * 
	 * @return true if both players have filled the entire playground
	 */
	public boolean snakeTooBig() {
		return (player1.getLocations().size() + player2.getLocations().size()) == playground.getCellAmount();
	}

}
