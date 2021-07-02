package Game.Snake.SnakeComponents;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author fitor
 *
 *         class sets the controlls of the snake, based on the player
 */
public class Snake_controlls {

	/** direction the snake's head is moving towards **/
	private String direction;

	/** keybinding of player 1 **/
	private HashMap<String, Integer> player1;
	/** keybinding of player 2 **/
	private HashMap<String, Integer> player2;
	/** keybinding of chosen player **/
	private HashMap<String, Integer> currentPlayer;

	/** Colors of the players snakes **/
	private final static Color PLAYER1COLOR = Color.RED;
	private final static Color PLAYER2COLOR = Color.BLUE;
	private Color currentPlayerColor;

	/** Constructor **/
	public Snake_controlls(final int player) {

		initPlayer1();
		initPlayer2();

		direction = (player == 1) ? "RIGHT" : "LEFT";
		currentPlayer = (player == 1) ? player1 : player2;

		currentPlayerColor = (player == 1) ? PLAYER1COLOR : PLAYER2COLOR;

	}

	/** function initializes the controlls of player1 **/
	private void initPlayer1() {
		player1 = new HashMap<>();

		// Map controll signals to arrow keys
		player1.put("LEFT", 37);
		player1.put("UP", 38);
		player1.put("RIGHT", 39);
		player1.put("DOWN", 40);
	}

	/** function initializes the controlls of player2 **/
	private void initPlayer2() {
		player2 = new HashMap<>();

		// Map controll signals to arrow keys
		player2.put("LEFT", 65);
		player2.put("UP", 87);
		player2.put("RIGHT", 68);
		player2.put("DOWN", 83);
	}

	/** get the keybindings of the current Player **/
	public HashMap<String, Integer> getPlayer() {
		return currentPlayer;
	}

	/** return a boolean-array based on the current keystroke **/
	public String getDirectionForEvent(final int i) {
		String result = direction;

		// check for valid keystroke
		if (currentPlayer.values().contains(i)) {

			// filter the right direction, while also updating it
			result = currentPlayer.entrySet().stream().filter(x -> x.getValue() == i).findAny().get().getKey();
		}

		return result;

	}

	/** setter for the direction **/
	public void setDirection(final String s) {
		direction = s;
	}

	/** get the direction of the snake **/
	public String getDirection() {
		return direction;
	}

	/** get the color of the current player's snakes **/
	public Color getColor() {
		return currentPlayerColor;
	}

	/** get a list of all keycodes **/
	public List<Integer> getControllCodes() {
		return currentPlayer.values().stream().collect(Collectors.toList());
	}

}
