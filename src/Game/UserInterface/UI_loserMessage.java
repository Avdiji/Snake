package Game.UserInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author fitor
 *
 *         Message when the game ends
 */
@SuppressWarnings("serial")
public class UI_loserMessage extends JFrame {

	/** size of the font of each component **/
	private final static float FONTSIZE = 25f;

	/** Strings for the buttons in this class **/
	private final static String RESTART = "Restart";
	private final static String MAINMENU = "Main Menu";
	private final static String EXIT = "Exit Game";

	/** size of the frame **/
	private final static int WIDTH = 400;
	private final static int HEIGHT = 400;

	/** Constraints **/
	private GridBagConstraints gbc;

	/** message for the player's **/
	private JLabel message;

	/**
	 * Buttons in case the player wants to restart, go to the main menu or end the
	 * application
	 **/
	private JButton restart;
	private JButton mainMenu;
	private JButton exit;

	/** to be able to manipulate the game and the menu **/
	private UI_MainMenu gameMenu;

	/**
	 * Constructor
	 */
	public UI_loserMessage(final UI_MainMenu gameMenu) {
		this.gameMenu = gameMenu;
		gbc = new GridBagConstraints();
		message = new JLabel();
		initFrame();

		initComponents();
		addComponents();

	}

	/**
	 * initialize the frame
	 */
	private void initFrame() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setUndecorated(true);
	}

	/** change the message for the player **/
	public void setMessage(final String s) {
		message.setText(s);
		setVisible(true);
	}

	/** initialize the components of the frame **/
	private void initComponents() {
		message = new JLabel();

		restart = new JButton(RESTART);
		mainMenu = new JButton(MAINMENU);
		exit = new JButton(EXIT);

		message.setFont(message.getFont().deriveFont(FONTSIZE));
		restart.setFont(restart.getFont().deriveFont(FONTSIZE));
		mainMenu.setFont(mainMenu.getFont().deriveFont(FONTSIZE));
		exit.setFont(exit.getFont().deriveFont(FONTSIZE));

		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMenu.restartGame();
				setVisible(false);
			}
		});

		mainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMenu.exitGame();
				gameMenu.setVisible(true);
				setVisible(false);
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMenu.exitAll();
			}
		});
	}

	/**
	 * add components to the frame
	 */
	private void addComponents() {
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weighty = 1;
		gbc.gridy = 0;
		add(message, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 0.3;

		gbc.gridy = 2;
		add(mainMenu, gbc);
		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridy = 1;
		add(restart, gbc);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridy = 3;
		add(exit, gbc);

	}

}
