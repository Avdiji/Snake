package Game.Snake;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Game.Snake.Rules.Gamemode_rules_singleplayer;
import Game.Snake.SnakeComponents.Snake_forgedComponents;
import Game.Snake.SnakeComponents.Snake_playground;
import Game.Snake.SnakeComponents.Snake_snack;
import Game.UserInterface.UI_MainMenu;

/**
 * 
 * @author fitor
 *
 *         Singleplayer-Mode
 */
@SuppressWarnings("serial")
public class Gamemode_singleplayer extends AGamemode_template {

	/** variable used to draw the score **/
	private final static float FONTSIZE = 30f;
	private final static String SCORE = "SCORE: ";
	private final static String GAMEOVER = "Your Score: ";

	private int score = 0;

	/** to make sure the background is painted only the first time **/
	private boolean start = true;
	/** forged components **/
	private Snake_forgedComponents gamePanel;
	/** field the snake moves in **/
	private final Snake_playground playground;
	/** rules of this gamemode **/
	private final Gamemode_rules_singleplayer gameRules;
	/** snack the snake needs to eat in order to grow **/
	private Snake_snack snack;

	/** playground edges to be calculated **/
	private int l, u, r, d;

	/** keylistener to be able to move the snake **/
	private KeyListener keyboard;

	/**
	 * Constructor
	 */
	public Gamemode_singleplayer(final int s, final int speed, final UI_MainMenu mainMenu) {
		super(s, speed, mainMenu);
		initKeyAdapter();
		addKeyListener(keyboard);

		// initialize snake game-Components
		setPlaygroundSizes();
		snack = new Snake_snack(super.getSnakeSize());
		playground = new Snake_playground(l, u, r, d, super.getSnakeSize());
		gamePanel = new Snake_forgedComponents(playground, 1);
		gameRules = new Gamemode_rules_singleplayer(playground, gamePanel.getSnakeBody());

		snack.setLocation(getSnackLocation(playground, gamePanel.getSnakeBody()));

		// start the game
		setVisible(true);
		super.getTimer().start();

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
		drawScore(g);
		snack.paint(g);
		gamePanel.paint(g);

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
	public void actionPerformed(ActionEvent e) {
		gamePanel.actionPerformed(e);

		// in case the game has reached to an end
		if (gameRules.biteHimself() || gameRules.snakeTooBig()) {
			super.getTimer().stop();
			sendMessage(GAMEOVER + score);
			// in case that the snake needs to add a bodypart
		} else if (eat(gamePanel.getSnakeBody().getHead(), snack.getLocation())) {
			gamePanel.getSnakeBody().addBody();
			snack.setLocation(getSnackLocation(playground, gamePanel.getSnakeBody()));
			score++;
		}
		repaint();
	}

	/**
	 * functions draws the score of the player (location of the drawn string
	 * hardcoded)
	 * 
	 * @param g
	 */
	public void drawScore(Graphics g) {
		// draw the new score
		g.setColor(Color.WHITE);
		g.fillRect(playground.getLeft(), playground.getUp() - 50, playground.getRight(), 50);
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(FONTSIZE));
		g.drawString(SCORE + score, playground.getLeft(), playground.getUp() - 5);
	}

	@Override
	public void initKeyAdapter() {
		keyboard = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				standardKeyEvents(e);
				gamePanel.keyPressed(e);
			}
		};

	}




}
