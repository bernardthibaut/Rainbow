package game.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();

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
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH / 5, Game.HEIGHT - 60, ID.Player, handler));
				handler.clearEnemys();

				playSound();
			}

			// help button
			if (mouseOver(mx, my, 210, 250, 200, 64)) {
				Game.gameState = STATE.Help;

				playSound();
			}

			// quit button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				System.exit(1);
			}
		}

		// back button for help
		if (Game.gameState == STATE.Help) {
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				game.gameState = STATE.Menu;
				playSound();
				return;
			}
		}

		// retry button
		if (Game.gameState == STATE.End) {
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
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

	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width)
			if (my > y && my < y + height)
				return true;
			else
				return false;
		else
			return false;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		if (Game.gameState == STATE.Menu) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.black);
			g.drawString("Game", 240, 70);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 270, 190);

			g.drawRect(210, 250, 200, 64);
			g.drawString("Help", 270, 290);

			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 270, 390);
		} else if (Game.gameState == STATE.Help) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);

			g.setFont(fnt);
			g.setColor(Color.black);
			g.drawString("Help", 240, 70);

			g.setFont(fnt3);
			g.drawString("Use ZQSD keys to move player", 50, 200);

			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		} else if (Game.gameState == STATE.End) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);

			g.setFont(fnt);
			g.setColor(Color.black);
			g.drawString("Game Over", 180, 70);

			g.setFont(fnt3);
			g.drawString("You lost with a score of: " + hud.getScore(), 175, 200);

			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Try Again", 245, 390);
		}

	}

}
