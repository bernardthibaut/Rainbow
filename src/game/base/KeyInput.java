package game.base;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean[] keyDown = new boolean[4];

	private boolean isMusic = false;

	private Game game;

	public KeyInput(Handler handler, Game game) {
		this.handler = handler;

		this.game = game;

		for (int i = 0; i < keyDown.length; i++) {
			keyDown[i] = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Player) {
				// Key events for player

				if (key == KeyEvent.VK_SPACE) {

					if (!tempObject.isJumping) {
						tempObject.isJumping = true;
					} else if (tempObject.doubleJump == 100) {
						tempObject.jumpCurve = 0;
						tempObject.doubleJump = 0;
						tempObject.isJumping = true;
					}

					keyDown[0] = true;
				}

			}
		}

		if (key == KeyEvent.VK_P) {
			if (game.gameState == STATE.Game)
				if (Game.paused)
					Game.paused = false;
				else
					Game.paused = true;
		}

		if (key == KeyEvent.VK_ESCAPE)
			System.exit(1);

		if (key == KeyEvent.VK_M)
			if (isMusic) {
				AudioPlayer.getMusic("music").stop();
				isMusic = false;
			} else {
				AudioPlayer.getMusic("music").loop();
				isMusic = true;

			}
	}

}
