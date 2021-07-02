package Game.Snake.SnakeComponents;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Snake_forgedComponents extends JPanel implements ActionListener, KeyListener {
	private int[] previousKeystroke = { -1, -1 };
	private Snake_controlls controlls;
	private Snake_body body;
	private boolean alreadyTyped = false;
	private final Snake_playground playground;

	public Snake_forgedComponents(Snake_playground playground, int player) {
		this.playground = playground;
		addKeyListener(this);

		if (player == 1) {
			this.controlls = new Snake_controlls(1);
			this.body = new Snake_body(new Point(playground.getLeft(), playground.getUp()), playground.getSize(),
					this.controlls.getColor());
		} else if (player == 2) {
			this.controlls = new Snake_controlls(2);
			this.body = new Snake_body(
					new Point(playground.getRight() - playground.getSize(),
							playground.getDown() - playground.getSize()),
					playground.getSize(), this.controlls.getColor());
		}
	}

	public void paint(Graphics g) {
		this.body.paint(g);
	}

	public void actionPerformed(ActionEvent e) {
		this.alreadyTyped = false;
		String controll = "";

		if (this.previousKeystroke[0] != -1) {
			controll = this.controlls.getDirectionForEvent(this.previousKeystroke[0]);
			this.previousKeystroke[0] = -1;
		} else if (this.previousKeystroke[1] != -1) {
			controll = this.controlls.getDirectionForEvent(this.previousKeystroke[1]);
			this.previousKeystroke[1] = -1;
		} else {
			controll = this.controlls.getDirection();
		}

		this.controlls.setDirection(controll);
		switch (controll) {
		case "UP":
			this.body.move(0, -this.playground.getSize());
			if ((int) this.body.getHead().getY() + this.playground.getSize() == this.playground.getUp())
				this.body.getHead().setLocation(this.body.getHead().getX(),
						this.playground.getDown() - this.playground.getSize());
			break;

		case "DOWN":
			this.body.move(0, this.playground.getSize());
			if ((int) this.body.getHead().getY() == this.playground.getDown()) {
				this.body.getHead().setLocation(this.body.getHead().getX(), this.playground.getUp());
			}

			break;
		case "LEFT":
			this.body.move(-this.playground.getSize(), 0);
			if ((int) this.body.getHead().getX() + this.playground.getSize() == this.playground.getLeft()) {
				this.body.getHead().setLocation(this.playground.getRight() - this.playground.getSize(),
						this.body.getHead().getY());
			}
			break;
		case "RIGHT":
			this.body.move(this.playground.getSize(), 0);
			if ((int) this.body.getHead().getX() == this.playground.getRight()) {
				this.body.getHead().setLocation(this.playground.getLeft(), this.body.getHead().getY());
			}

			break;
		}

	}

	public void keyPressed(KeyEvent e) {
		if (this.controlls.getControllCodes().contains(Integer.valueOf(e.getKeyCode())))
			if (!this.alreadyTyped) {
				String newDirection = this.controlls.getDirectionForEvent(e.getKeyCode());

				if (allowedToChangeDirection(this.controlls.getDirectionForEvent(e.getKeyCode()))) {
					this.controlls.setDirection(newDirection);

					this.previousKeystroke[0] = (this.previousKeystroke[1] == -1 ? e.getKeyCode()
							: this.previousKeystroke[0]);
					this.alreadyTyped = true;
				}
			} else {
				this.previousKeystroke[1] = ((this.previousKeystroke[1] == -1)
						&& (allowedToChangeDirection(this.controlls.getDirectionForEvent(e.getKeyCode())))
								? e.getKeyCode()
								: this.previousKeystroke[1]);
			}
	}

	private boolean allowedToChangeDirection(String s) {
		boolean result = true;
		switch (s) {
		case "UP":
			result = !this.controlls.getDirection().equals("DOWN");
			break;
		case "DOWN":
			result = !this.controlls.getDirection().equals("UP");
			break;
		case "LEFT":
			result = !this.controlls.getDirection().equals("RIGHT");
			break;
		case "RIGHT":
			result = !this.controlls.getDirection().equals("LEFT");
			break;
		}

		return result;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public Snake_body getSnakeBody() {
		return this.body;
	}
}