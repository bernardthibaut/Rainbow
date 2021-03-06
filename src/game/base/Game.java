package game.base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.Random;

import org.newdawn.slick.SlickException;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	// Size of the window
	public static final int WIDTH = 980, HEIGHT = WIDTH / 12 * 9;

	private Thread thread;
	private boolean running = false;

	public static boolean paused = false;

	private Random r = new Random();
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;

	private Color backgroundColor = Color.white;
	public int flash = 0;
	
	public Image skin;

	public static STATE gameState = STATE.Menu;

	public Game() {
		initialize();

		if (gameState == STATE.Game) {

			handler.addObject(new Player(Game.WIDTH / 5, Game.HEIGHT - 60, ID.Player, handler, this, hud, skin));

		} else {

			// Show what you want in the other state of the program (example: in
			// the menu)

		}

	}

	// The action which is done in the run method
	private void tick() {
		if (gameState == STATE.Game) {
			if (!paused) {
				hud.tick();
				spawner.tick();
				handler.tick();

				if (HUD.HEALTH <= 0) {
					HUD.HEALTH = 100;
					gameState = STATE.End;
					handler.clearEnemys();
				}
			}

		} else {
			menu.tick();
			handler.tick();
		}
	}

	// The graphic part of the run method
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		// Background
		if (flash > 0) {
			backgroundColor = new Color(r.nextInt(55) + 200, r.nextInt(55) + 200, r.nextInt(55) + 200);
			flash--;
		} else {
			backgroundColor = Color.white;
		}
		g.setColor(backgroundColor);

		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		if (paused) {
			g.setColor(Color.black);
			g.setFont(new Font("arial", 0, 32));
			g.drawString("PAUSED", 415, 350);
		}

		if (gameState == STATE.Game) {
			hud.render(g);
		} else {
			menu.render(g);
		}

		g.dispose();
		bs.show();
	}

	/*
	 * 
	 * 
	 * 
	 * DO NOT CHANGE WHAT FOLLOWS
	 * 
	 * 
	 * 
	 */

	public void initialize() {
		// Create the class which manage the objects
		handler = new Handler();
		// Create the user interface
		hud = new HUD();
		// Create the menu which opens when you start the program
		menu = new Menu(this, handler, hud);
		// Create the associations with the keyboard and the commands
		this.addKeyListener(new KeyInput(handler, this));
		// Create the actions when you click in the menu
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		// Load the audio files in the res folder
		AudioPlayer.load();
		// Load the image files for the skins
		SkinsCollection.load();
		// Create the window which will be seen
		new Window(WIDTH, HEIGHT, "Game", this);
		// Create the class which manage the spawn of the objects
		spawner = new Spawn(handler, hud, this);
	}

	// Method which add a time dimension to the game
	// DO NOT CHANGE IT
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	// Used to prevent the Gameobjects to go out of a zone
	// ( var = gameObject; min & max = bounds )
	// DO NOT CHANGE IT
	public static float clamp(float var, float min, float max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		return var;
	}

	// Start the game
	// DO NOT CHANGE IT
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	// End the game
	// DO NOT CHANGE IT
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DO NOT CHANGE IT
	public static void main(String[] args) {
		new Game();
	}

}
