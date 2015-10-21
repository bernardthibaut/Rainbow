package game.base;

import java.util.Random;

public class Spawn {

	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random();

	private int cdSpawn = r.nextInt(100) + 50;

	private int scoreKeep = 0;

	public Spawn(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
	}

	public void tick() {
		scoreKeep++;

		if (scoreKeep >= 250) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
		}

		if (cdSpawn > 0) {
			cdSpawn--;
		} else {
			handler.addObject(new Obstacle(Game.WIDTH, Game.HEIGHT - 60, ID.Obstacle, handler));
			cdSpawn = r.nextInt(50) + 20;
		}
	}

}