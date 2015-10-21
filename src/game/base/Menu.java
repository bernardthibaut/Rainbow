package game.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();

	private Color colorTitle = new Color(0, 0, 0);

	private Rectangle play = new Rectangle((Game.WIDTH / 5) * 2, Game.HEIGHT / 4, Game.WIDTH / 5, Game.HEIGHT / 5);
	private Rectangle help = new Rectangle((Game.WIDTH / 5) * 2, (Game.HEIGHT / 4) * 2 - 20, Game.WIDTH / 5,
			Game.HEIGHT / 5);
	private Rectangle quit = new Rectangle((Game.WIDTH / 5) * 2, (Game.HEIGHT / 4) * 3 - 40, Game.WIDTH / 5,
			Game.HEIGHT / 5);

	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.hud = hud;
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (Game.gameState == STATE.Menu) {
			// play button
			if (mouseOver(mx, my, play)) {
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH / 5, Game.HEIGHT - 60, ID.Player, handler));
				handler.clearEnemys();

				playSound();
			}

			// help button
			if (mouseOver(mx, my, help)) {
				Game.gameState = STATE.Help;

				playSound();
			}

			// quit button
			if (mouseOver(mx, my, quit)) {
				System.exit(1);
			}
		}

		// back button for help
		if (Game.gameState == STATE.Help) {
			if (mouseOver(mx, my, quit)) {
				Game.gameState = STATE.Menu;
				playSound();
				return;
			}
		}

		// retry button
		if (Game.gameState == STATE.End) {
			if (mouseOver(mx, my, quit)) {
				Game.gameState = STATE.Game;
				hud.setLevel(1);
				hud.setScore(0);
				playSound();

				handler.addObject(new Player(Game.WIDTH / 5, Game.HEIGHT - 60, ID.Player, handler));
			}
		}

	}

	private void playSound() {
		try {
			AudioPlayer.getSound("menu_sound").play();
		} catch (NullPointerException e) {

		}

	}

	private boolean mouseOver(int mx, int my, Rectangle r) {
		if (mx > r.x && mx < r.x + r.width)
			if (my > r.y && my < r.y + r.height)
				return true;
			else
				return false;
		else
			return false;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 75);
		Font fnt2 = new Font("arial", 1, 40);
		Font fnt3 = new Font("arial", 1, 20);
		if (Game.gameState == STATE.Menu) {

			g.setFont(fnt);
			int red = colorTitle.getRed() + r.nextInt(5) - 2;
			int green = colorTitle.getGreen() + r.nextInt(5) - 2;
			int blue = colorTitle.getBlue() + r.nextInt(5) - 2;
			red = (int) Game.clamp(red, 50, 255);
			green = (int) Game.clamp(green, 50, 255);
			blue = (int) Game.clamp(blue, 50, 255);

			colorTitle = new Color(red, green, blue);
			g.setColor(colorTitle);
			g.drawString("Rainbow", 335, 115);

			g.setFont(fnt2);
			g.setColor(Color.black);
			g.draw3DRect(play.x, play.y, play.width, play.height, true);
			g.drawString("Play", play.x + 58, play.y + 85);

			g.draw3DRect(help.x, help.y, help.width, help.height, true);
			g.drawString("Help", help.x + 58, help.y + 85);

			g.draw3DRect(quit.x, quit.y, quit.width, quit.height, true);
			g.drawString("Quit", quit.x + 58, quit.y + 85);
		} else if (Game.gameState == STATE.Help) {

			g.setFont(fnt);
			g.setColor(Color.black);
			g.drawString("Help", 240, 70);

			g.setFont(fnt3);
			g.drawString("Use ZQSD keys to move player", 50, 200);

			g.setFont(fnt2);
			g.draw3DRect(quit.x, quit.y, quit.width, quit.height, true);
			g.drawString("Back", (Game.WIDTH / 5) * 2, Game.HEIGHT / 10);
		} else if (Game.gameState == STATE.End) {

			g.setFont(fnt);
			int red = colorTitle.getRed() + r.nextInt(5) - 2;
			int green = colorTitle.getGreen() + r.nextInt(5) - 2;
			int blue = colorTitle.getBlue() + r.nextInt(5) - 2;
			red = (int) Game.clamp(red, 50, 255);
			green = (int) Game.clamp(green, 50, 255);
			blue = (int) Game.clamp(blue, 50, 255);

			colorTitle = new Color(red, green, blue);
			g.setColor(colorTitle);
			g.drawString("Game Over", 290, 125);

			g.setFont(fnt2);
			g.setColor(Color.black);
			g.drawString("You lost with a score of: " + hud.getScore(), 225, 300);

			g.setFont(fnt2);
			g.draw3DRect(quit.x, quit.y, quit.width, quit.height, true);
			g.drawString("Try Again", 400, 590);
		}

	}

}
