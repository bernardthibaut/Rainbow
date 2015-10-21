package game.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

	public static float HEALTH = 100;

	private float greenValue = 255;

	private int score = 0;
	private int level = 1;

	public void tick() {
		HEALTH = Game.clamp(HEALTH, 0, 100);
		greenValue = Game.clamp(greenValue, 0, 255);

		greenValue = HEALTH * 2;

		score++;
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(Color.black);
		g.fillRect(13, 13, 604, 36);
		g.setColor(new Color(150, (int) greenValue, 0));
		g.fillRect(15, 15, (int) HEALTH * 6, 32);

		g.setColor(Color.black);
		g.setFont(new Font("arial", 0, 32));

		g.drawString("Score: " + score, 225, 85);
		g.drawString("Level: " + level, 13, 85);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
