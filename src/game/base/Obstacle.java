package game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstacle extends GameObject {

	private Handler handler;

	public Obstacle(int x, int y, ID id, Handler handler) {
		super(x, y, id);

		this.handler = handler;

		// Speed in the X axis ( '-' Left | '+' Right )
		velX = -8;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}

	public void tick() {
		x += velX;

	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, size, size);
	}

}
