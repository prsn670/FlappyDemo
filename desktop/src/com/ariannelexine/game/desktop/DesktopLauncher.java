package com.ariannelexine.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ariannelexine.game.FlappyDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		/* This desktop config was modified with the following three lines; config.width, height, and
		 * title were added to change size of the window
		 *  The class FlappyDemo was created earlier with the variables WIDTH, HEIGHT, and TITLE*/
		config.width = FlappyDemo.WIDTH;
		config.height = FlappyDemo.HEIGHT;
		config.title = FlappyDemo.TITLE;
		new LwjglApplication(new FlappyDemo(), config);
	}
}
