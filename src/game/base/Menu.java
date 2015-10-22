package game.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import org.newdawn.slick.gui.MouseOverArea;

public class Menu extends MouseAdapter implements MouseMotionListener {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();

	private Color colorTitle = new Color(0, 0, 0);
	private int xCustom = 0;
	private Rectangle custom = new Rectangle(275, 60, 55, 55);

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
				handler.addObject(new Player(Game.WIDTH / 5, Game.HEIGHT - 60, ID.Player, handler, game, hud));
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

			// customize button
			if (mouseOver(mx, my, custom)) {
				Game.gameState = STATE.Custom;

				playSound();
			}
		}

		// Custom menu
		if (Game.gameState == STATE.Custom) {

		}

		// back button
		if (Game.gameState == STATE.Help || Game.gameState == STATE.Custom) {
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

				handler.addObject(new Player(Game.WIDTH / 5, Game.HEIGHT - 60, ID.Player, handler, game, hud));
			}
		}

	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (mouseOver(mx, my, custom)) {
			if (xCustom < 20) {
				xCustom++;
			}

		} else {
			if (xCustom > 0)
				xCustom--;

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
		Font fnt3 = new Font("arial", 1, xCustom);
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
			g.drawRect(play.x, play.y, play.width, play.height);
			g.drawString("Play", play.x + 58, play.y + 85);

			g.drawRect(help.x, help.y, help.width, help.height);
			g.drawString("Help", help.x + 58, help.y + 85);

			g.drawRect(quit.x, quit.y, quit.width, quit.height);
			g.drawString("Quit", quit.x + 58, quit.y + 85);

			// Customize your character
			g.setColor(colorTitle);
			g.setFont(fnt3);
			g.drawString("Customize!", 275 - xCustom * 6, 95);
			g.fillRect(275, 60, 55, 55);

		} else if (Game.gameState == STATE.Help) {

			g.setFont(fnt);
			g.setColor(Color.black);
			g.drawString("Help", 405, 70);

			g.setFont(fnt2);
			g.drawString("Use ZQSD keys to move your", 220, 250);
			g.drawString("character and dodge the enemies", 180, 310);

			g.setFont(fnt2);
			g.drawRect(quit.x, quit.y, quit.width, quit.height);
			g.drawString("Back", 445, 590);
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
			g.drawRect(quit.x, quit.y, quit.width, quit.height);
			g.drawString("Try Again", 400, 590);
		} else if (Game.gameState == STATE.Custom) {
			g.setColor(colorTitle);

			g.fillRect(290, 50, 400, 400);

			g.setFont(fnt2);
			g.drawRect(quit.x, quit.y, quit.width, quit.height);
			g.drawString("Back", 445, 590);

		}

	}

}
