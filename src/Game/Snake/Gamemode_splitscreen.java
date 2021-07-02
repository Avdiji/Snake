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
 *         splitscreen-Mode (when player 1 scores a point player 2 snake will become bigger)
 */
@SuppressWarnings("serial")
public class Gamemode_splitscreen extends AGamemode_template {

	/** to make sure the background is painted only the first time **/
	private boolean start = true;
	
	
	/** forged components **/
	private Snake_forgedComponents player1;
	private Snake_forgedComponents player2;
	
	/** field the snake moves in **/
	private final Snake_playground playground1;
	private final Snake_playground playground2;
	
	/** rules of this gamemode **/
	private final Gamemode_rules_singleplayer gameRules;
	private final Gamemode_rules_singleplayer gameRules2;
	
	/** snack the snake needs to eat in order to grow **/
	private Snake_snack snack1;
	private Snake_snack snack2;

	/** playground edges to be calculated **/
	private int l, u, r, d;

	/** keylistener to be able to move the snake **/
	private KeyListener keyboard;

	/**
	 * Constructor
	 */
	public Gamemode_splitscreen(final int s, final int speed, final UI_MainMenu mainMenu) {
		super(s, speed, mainMenu);
		initKeyAdapter();
		addKeyListener(keyboard);

		// initialize snake game-Components
		snack1 = new Snake_snack(super.getSnakeSize());
		snack2 = new Snake_snack(super.getSnakeSize());
		setPlaygroundSizes();
		playground1 = new Snake_playground(l, u, r, d, super.getSnakeSize());
		setPlaygroundSizes2();
		playground2 = new Snake_playground(l, u, r, d, super.getSnakeSize());
		player1 = new Snake_forgedComponents(playground1, 1);
		player2 = new Snake_forgedComponents(playground2, 2);
		gameRules = new Gamemode_rules_singleplayer(playground1, player1.getSnakeBody());
		gameRules2 = new Gamemode_rules_singleplayer(playground2, player2.getSnakeBody());

		snack1.setLocation(getSnackLocation(playground1, player1.getSnakeBody()));
		snack2.setLocation(getSnackLocation(playground2, player2.getSnakeBody()));

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
			g.fillRect(playground1.getLeft(), playground1.getUp(), playground1.getRight() - playground1.getLeft(),
					playground1.getDown() - playground1.getUp());
			g.fillRect(playground2.getLeft(), playground2.getUp(), playground2.getRight() - playground2.getLeft(),
					playground2.getDown() - playground2.getUp());
			start = false;
		}

		// draw the game and the score
		drawScore(g);
		snack1.paint(g);
		snack2.paint(g);
		player1.paint(g);
		player2.paint(g);

	}

	@Override
	public void setPlaygroundSizes() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// distance from the upperleft corner of the monitor
		int left = 50;
		int up = 75;

		// magic numbers
		int right = (int) (screensize.getWidth()/2) - (2 * left);
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
	
	
	/** set playground sizes for the second player's playground **/
	public void setPlaygroundSizes2() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int tmp = r-l;
		int hlp = 1;
		
		
		while(tmp+ r + hlp * super.getSnakeSize() < (int)screensize.getWidth() - 100) {
			hlp++;
		}

		
		l = r + hlp*super.getSnakeSize();
		r = l+tmp;
		
		

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player1.actionPerformed(e);
		player2.actionPerformed(e);

		// in case the game has reached to an end
		if (gameRules.biteHimself() || gameRules.snakeTooBig()) {
			super.getTimer().stop();
			sendMessage("Player 2 won");
			
		}else if(gameRules2.biteHimself() || gameRules2.snakeTooBig()) {
			super.getTimer().stop();
			sendMessage("Player 1 won");
			// in case that the snake needs to add a bodypart
		} else if (eat(player1.getSnakeBody().getHead(), snack1.getLocation())) {
			player2.getSnakeBody().addBody();
			snack1.setLocation(getSnackLocation(playground1, player1.getSnakeBody()));
		} else if (eat(player2.getSnakeBody().getHead(), snack2.getLocation())) {
			player1.getSnakeBody().addBody();
			snack2.setLocation(getSnackLocation(playground2, player2.getSnakeBody()));
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
		g.fillRect(playground1.getLeft(), playground2.getUp() - 50, playground2.getRight(), 50);
		g.setColor(Color.BLACK);
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




}
