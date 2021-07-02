package Game.UserInterface;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Game.Snake.IGamemode_template;
import Game.Snake.Gamemode_singleplayer;
import Game.Snake.Gamemode_splitscreen;
import Game.Snake.Gamemode_twoplayer;

/**
 * 
 * @author fitor
 *
 *         Main Menu of the game
 */
@SuppressWarnings("serial")
public class UI_MainMenu extends JFrame {

	/** Color of the background of the menu **/
	private final static Color BACKGROUNDCOLOR = Color.CYAN;
	/** Font size of the components **/
	private final static float FONTSIZE = 25f;
	private final static float LABELFONT = 20f;

	/** size of the frame **/
	private final static int WIDTH = 500;
	private final static int HEIGHT = 400;

	/** initial values for the gamemode, sizes and the speed **/
	private final static String[] GAMEMODES = { "Singleplayer", "2-Player", "Splitscreen" };
	private final static String[] SIZES = { "Microscopic", "Small", "Normal", "Big", "Planetary" };
	private final static String[] SPEEDOPTIONS = { "Superslow", "Slow", "Normal", "Fast", "Ultrasonic speed" };

	/** Strings to instruct the player **/
	private final static String GAMEMODESL = "Gamemode: ";
	private final static String SIZESL = "Snakesize: ";
	private final static String SPEEDL = "Speed: ";

	/** corresponding sizes/speed of the snake **/
	private final static Point[] SIZENSPEEDVALUES = { new Point(10, 1000), new Point(25, 250), new Point(50, 100),
			new Point(100, 50), new Point(200, 25) };

	/** final static button labels **/
	private final static String STARTGAME = "Start";
	private final static String EXITGAME = "Exit the Game";

	/** edit the layout of the Frame **/
	private GridBagConstraints gbc;

	/** Panels with Comboboxes **/
	private JPanel gameModesPanel;
	private JPanel sizesPanel;
	private JPanel speedPanel;

	/** Labels for the Buttons **/
	private JLabel gameModesLabel;
	private JLabel sizesLabel;
	private JLabel speedLabel;

	/** Panel combines all menuComponents **/
	private JPanel menu;

	/** Comboboxes **/
	private JComboBox<String> gameModes;
	private JComboBox<String> sizes;
	private JComboBox<String> speed;

	/** Buttons to start/exit the game **/
	private JButton startGame;
	private JButton exitGame;

	/** Frame appears when the game has ended **/
	private UI_loserMessage popUpMessage;

	/** Actual Game **/
	private IGamemode_template snake;

	/**
	 * Constructor
	 */
	public UI_MainMenu() {
		initComponents();
		initFrame();

		createDropDownList(gameModesPanel, gameModes, GAMEMODES);
		createDropDownList(sizesPanel, sizes, SIZES);
		createDropDownList(speedPanel, speed, SPEEDOPTIONS);

		gameModes.setSelectedItem(GAMEMODES[0]);
		sizes.setSelectedItem("Normal");
		speed.setSelectedItem("Normal");

		addToComponent(menu);
		add(menu);

		setVisible(true);
	}

	/**
	 * Function initializes the frame
	 */
	private void initFrame() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/** helperMethod **/
	private void createDropDownList(final JPanel panel, final JComboBox<String> combobox, final String... strings) {

		for (String x : strings) {
			combobox.addItem(x);
		}

		combobox.setFont(combobox.getFont().deriveFont(FONTSIZE));
		panel.add(combobox);

	}

	/** Function initializes the components of the game **/
	private void initComponents() {
		gameModesPanel = new JPanel();
		sizesPanel = new JPanel();
		speedPanel = new JPanel();

		gameModesLabel = new JLabel(GAMEMODESL);
		sizesLabel = new JLabel(SIZESL);
		speedLabel = new JLabel(SPEEDL);

		gameModesLabel.setFont(gameModesLabel.getFont().deriveFont(LABELFONT));
		sizesLabel.setFont(sizesLabel.getFont().deriveFont(LABELFONT));
		speedLabel.setFont(speedLabel.getFont().deriveFont(LABELFONT));

		gameModes = new JComboBox<String>();
		sizes = new JComboBox<String>();
		speed = new JComboBox<String>();

		gameModesPanel.setBackground(BACKGROUNDCOLOR);
		sizesPanel.setBackground(BACKGROUNDCOLOR);
		speedPanel.setBackground(BACKGROUNDCOLOR);

		menu = new JPanel();
		menu.setLayout(new GridBagLayout());
		menu.setBackground(BACKGROUNDCOLOR);

		startGame = new JButton(STARTGAME);
		exitGame = new JButton(EXITGAME);
		startGame.setFont(startGame.getFont().deriveFont(FONTSIZE));
		exitGame.setFont(exitGame.getFont().deriveFont(FONTSIZE));

		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				snake = chooseGame();
				initPopUpMessage();
				setVisible(false);

			}
		});

		exitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}

	/**
	 * Function adds the components to the frame
	 */
	private void addToComponent(final JComponent comp) {
		gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		comp.add(gameModesLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		comp.add(gameModesPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		comp.add(sizesLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		comp.add(sizesPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		comp.add(speedLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		comp.add(speedPanel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 4;
		comp.add(exitGame, gbc);

		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridx = 1;
		gbc.gridy = 3;
		comp.add(startGame, gbc);

	}

	/** select the game, depending on the chosen settings **/
	private IGamemode_template chooseGame() {
		int size = (int) SIZENSPEEDVALUES[sizes.getSelectedIndex()].getX();
		int timerval = (int) SIZENSPEEDVALUES[speed.getSelectedIndex()].getY();
		IGamemode_template result = null;

		switch ((String) gameModes.getSelectedItem()) {
		case "Singleplayer":
			result = new Gamemode_singleplayer(size, timerval, this);
			break;
		case "2-Player":
			result = new Gamemode_twoplayer(size, timerval, this);
			break;
		case "Splitscreen":
			result = new Gamemode_splitscreen(size,timerval,this);
			break;
		}

		return result;

	}

	/** initialize the popUpMessage **/
	private void initPopUpMessage() {
		popUpMessage = new UI_loserMessage(this);
	}

	/** getter for the popUpMessage **/
	public UI_loserMessage getPopUpMessage() {
		return popUpMessage;
	}

	/** restart the game **/
	public void restartGame() {
		snake.exitGame();
		snake = chooseGame();

	}

	/** exit the current game **/
	public void exitGame() {
		snake.exitGame();
	}

	/** exit the application **/
	public void exitAll() {
		snake.exitGame();
		popUpMessage.dispose();
		dispose();
	}

}
