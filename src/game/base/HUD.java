package game.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

	public static float HEALTH = 100;

	private Color colorHealth = Color.green;

	private int score = 0;
	private int level = 1;
	
	public boolean hasShield = false;

	public void tick() {
		HEALTH = Game.clamp(HEALTH, 0, 100);

		if (HEALTH > 70)
			colorHealth = Color.green;
		else if (HEALTH > 40)
			colorHealth = Color.yellow;
		else
			colorHealth = Color.red;
		
		if(hasShield)
			colorHealth = Color.cyan;

		score++;
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(Color.black);
		g.fillRect(13, 13, 604, 36);
		g.setColor(colorHealth);
		g.fillRect(15, 15, (int) HEALTH * 6, 32);
		g.setColor(Color.black);
		g.drawRect(215, 13, 200, 34);

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
