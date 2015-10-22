package game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MovingObstacle extends GameObject {

	private Handler handler;
	private Random r = new Random();

	public MovingObstacle(int x, int y, ID id, Handler handler) {
		super(x, y, id);

		this.handler = handler;

		// Speed in the X axis ( '-' Left | '+' Right )
		velX = -8;
		if (r.nextInt(2) == 0)
			velY = -2;
		else
			velY = 2;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y < Game.HEIGHT - 180 || y > Game.HEIGHT - size * 2 + 5)
			velY *= -1;

	}

	public void render(Graphics g) {
		g.setColor(new Color(255, 150, 0));
		g.fillRect((int) x, (int) y, size, size);
	}

}
