package Game.Snake;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Game.Snake.Rules.Gamemode_rules_twoPlayer;
import Game.Snake.SnakeComponents.Snake_forgedComponents;
import Game.Snake.SnakeComponents.Snake_snack;
import Game.UserInterface.UI_MainMenu;
import Game.Snake.SnakeComponents.Snake_playground;

/**
 * 
 * @author fitor
 *
 *Twoplayer-Mode (battleroyal)
 */
@SuppressWarnings("serial")
public class Gamemode_twoplayer extends AGamemode_template {

	
	private final static String P1LOST = "Player 1 lost!";
	private final static String P2LOST = "Player 2 lost!";
	private final static String BOTHLOST = "Both Players lost!";
	
	/** draw only first time **/
	private boolean start = true;

	/** Components of the snake **/
	private Snake_forgedComponents player1;
	private Snake_forgedComponents player2;
	private Snake_playground playground;
	private Snake_snack snack;
	private Gamemode_rules_twoPlayer gameRules;

	/** playground edges to be calculated **/
	private int l, u, r, d;

	/** keylistener to be able to move the snake's **/
	private KeyListener keyboard;

	/**
	 * Constructor
	 * 
	 * @param size
	 * @param speed
	 */
	public Gamemode_twoplayer(int size, int speed, UI_MainMenu mainMenu) {
		super(size, speed, mainMenu);
		initKeyAdapter();
		addKeyListener(keyboard);

		setPlaygroundSizes();
		snack = new Snake_snack(super.getSnakeSize());
		playground = new Snake_playground(l, u, r, d, super.getSnakeSize());
		player1 = new Snake_forgedComponents(playground, 1);
		player2 = new Snake_forgedComponents(playground, 2);
		gameRules = new Gamemode_rules_twoPlayer(playground, player1.getSnakeBody(), player2.getSnakeBody());

		snack.setLocation(getSnackLocation(playground, player1.getSnakeBody(), player2.getSnakeBody()));

		setVisible(true);
		super.getTimer().start();

	}

	@Override
	public void initKeyAdapter() {
		keyboard = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				standardKeyEvents(e);
				player1.keyPressed(e);
				player2.keyPressed(e);
			}
		};

	}


	@Override
	public void setPlaygroundSizes() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// distance from the upperleft corner of the monitor
		int left = 50;
		int up = 75;

		// magic numbers
		int right = (int) screensize.getWidth() - (2 * left);
		int down = (int) screensize.getHeight() - (2 * up);

		// adjust right and bottom edge
		while (right % super.getSnakeSize() != 0) {
			right--;
		}
		while (down % super.getSnakeSize() != 0) {
			down--;
		}

		// set playground values
		l = left;
		u = up;
		r = left + right;
		d = up + down;

	}

	@Override
	public void paint(Graphics g) {
		// first of all draw the playground
		if (start) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.GRAY);
			g.fillRect(playground.getLeft(), playground.getUp(), playground.getRight() - playground.getLeft(),
					playground.getDown() - playground.getUp());
			start = false;
		}

		// draw the game and the score
		snack.paint(g);
		player1.paint(g);
		player2.paint(g);
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		player1.actionPerformed(e);
		player2.actionPerformed(e);

		if (gameRules.playerBiteHimself() != 0) {
			super.getTimer().stop();
			sendMessage(gameRules.playerBiteHimself() == 1 ? P1LOST : P2LOST);
		
		} else if (gameRules.playerBitEnemy() != 0) {
			super.getTimer().stop();
	
			if (gameRules.playerBitEnemy() == 1) {
				sendMessage(P1LOST);
			} else if (gameRules.playerBitEnemy() == 2) {
				sendMessage(P2LOST);
			} else {
				sendMessage(BOTHLOST);
			}
		} else if (gameRules.snakeTooBig()) {
			super.getTimer().stop();
			sendMessage(BOTHLOST);
			
		} else if (eat(player1.getSnakeBody().getHead(), snack.getLocation())) {
			player1.getSnakeBody().addBody();
			snack.setLocation(getSnackLocation(playground, player1.getSnakeBody(), player2.getSnakeBody()));
		} else if (eat(player2.getSnakeBody().getHead(), snack.getLocation())) {
			player2.getSnakeBody().addBody();
			snack.setLocation(getSnackLocation(playground, player1.getSnakeBody(), player2.getSnakeBody()));
		}

		repaint();

	}
	
	


}
