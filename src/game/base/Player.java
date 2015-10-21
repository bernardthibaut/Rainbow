package game.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;

	private int red = r.nextInt(150) + 50;
	private int green = r.nextInt(150) + 50;
	private int blue = r.nextInt(150) + 50;

	private Color color = new Color(red, green, blue);

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;

		yInit = y;

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	public void tick() {

		if (isJumping) {
			if (jumpCurve < 10) {
				y -= 7;
				jumpCurve++;
			} else {
				y += 5;
				jumpCurve++;

				if (y == yInit) {
					isJumping = false;
					jumpCurve = 0;
				}

			}
		}

		y = Game.clamp(y, 0, Game.HEIGHT - 60);

		doubleJump++;
		doubleJump = (int) Game.clamp(doubleJump, 0, 100);

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

		red = (int) Game.clamp(red, 0, 255);
		green = (int) Game.clamp(green, 0, 255);
		blue = (int) Game.clamp(blue, 0, 255);

		color = new Color(red, green, blue);

		// Code to do if the player collide with an ennemy
		if (collision()) {
			HUD.HEALTH -= 2;
		}
	}

	public boolean collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Obstacle) {
				if (getBounds().intersects(tempObject.getBounds())) {
					tempObject.size -= 3;
					tempObject.x += 3;
					tempObject.y += 3;
					return true;
				}
			}
		}
		return false;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int) x, (int) y, size, size);
		g.setColor(new Color(r.nextInt(100) + 100, r.nextInt(100) + 100, r.nextInt(100) + 100));
		g.fillRect((int) x, (int) y - 15, (int) (0.32 * doubleJump), 5);
	}

}
