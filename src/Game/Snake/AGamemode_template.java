package Game.Snake;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import Game.UserInterface.UI_MainMenu;
import Game.UserInterface.UI_loserMessage;

/**
 * 
 * @author fitor
 *
 *
 *         abstract class used a the foundation for new gamemodes
 */
@SuppressWarnings("serial")
public abstract class AGamemode_template extends JFrame implements IGamemode_template {

	
	/** size of the snake's bodyparts **/
	private final int size;
	/** Timer of the gamemode (for the snake's action **/
	private Timer timer;

	/** toogle for the timer **/
	private boolean togglePause = true;
	
	
	/** corresponding menu for this game **/
	private UI_MainMenu mainMenu;

	
	/** Constructor **/
	public AGamemode_template(final int size, final int speed, final UI_MainMenu mainMenu) {
		this.size = size;
		this.mainMenu = mainMenu;
		// initialize frame
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		timer = new Timer(speed, this);

	}

	/** getter for the timer **/
	public Timer getTimer() {
		return timer;
	}

	/** getter for the snakeSize **/
	public int getSnakeSize() {
		return size;
	}

	@Override
	public void standardKeyEvents(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
			timer.stop();
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			if (togglePause) {
				timer.stop();
				togglePause = !togglePause;
			} else {
				timer.start();
				togglePause = !togglePause;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_M) {
			dispose();
			timer.stop();
			mainMenu.setVisible(true);
		}

	}
	
	/** getter for the popUpMessage **/
	public UI_loserMessage getPopUpMessage() {
		return mainMenu.getPopUpMessage();
	}
	
	
	/** exit the game **/
	public void exitGame() {
		timer.stop();
		dispose();
	}
	
	@Override
	public void sendMessage(final String s) {
		mainMenu.getPopUpMessage().setMessage(s);
	}

}
