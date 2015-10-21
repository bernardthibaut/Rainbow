package game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HealthKit extends GameObject {

	private HUD hud;

	public HealthKit(int x, int y, ID id, HUD hud) {
		super(x, y, id);
		this.hud = hud;

		velX = -8;
		size = 16;
	}

	@Override
	public void tick() {
		x += velX;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int) x, (int) y, size, size);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, size, size);
	}
}
