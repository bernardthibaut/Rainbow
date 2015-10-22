package game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Bonus extends GameObject {

	private Random r = new Random();

	private int red = r.nextInt(150) + 50;
	private int green = r.nextInt(150) + 50;
	private int blue = r.nextInt(150) + 50;

	private Color color = new Color(red, green, blue);

	public Bonus(int x, int y, ID id) {
		super(x, y, id);

		velX = -8;
		size = 32;
	}

	@Override
	public void tick() {
		x += velX;

		if (r.nextInt(2) == 0)
			red += r.nextInt(25);
		else
			red -= r.nextInt(25);

		if (r.nextInt(2) == 0)
			green += r.nextInt(25);
		else
			green -= r.nextInt(25);

		if (r.nextInt(2) == 0)
			blue += r.nextInt(25);
		else
			blue -= r.nextInt(25);

		red = (int) Game.clamp(red, 0, 240);
		green = (int) Game.clamp(green, 0, 240);
		blue = (int) Game.clamp(blue, 0, 240);

		color = new Color(red, green, blue);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval((int) x, (int) y, size, size);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}

}
