/* this file is pretty much the heart of the game
 * We place the music here because we want it to play at the menu screen and all the game states*/
package com.ariannelexine.game;

import com.ariannelexine.game.States.GameStateManager;
import com.ariannelexine.game.States.MenuState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//extends is the keyword used to inherit the properties of a class. From: https://www.tutorialspoint.com/java/java_inheritance.htm
public class FlappyDemo extends ApplicationAdapter {
	//global variables for width and height of screen
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Flappy Bird";
	private GameStateManager gsm;
	/* A private SpriteBatch is something you only should have one of in the game b/c they are very heavy files
	 * and you only need one to pass around to all your different states. Remember SpriteBatch is the container for everything.
	 * It renders everything to the screen. */
	private SpriteBatch batch;
	Texture img;

	/* See https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/audio/Music.html
	 * for more info on the Music class
	 * */
	private Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		/* newMusic is a method of the Gdx class that creates a new Music instance which is used to
		 * play back a music stream from a file. Mor info at: https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/Audio.html*/
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);//loops music continuously
		music.setVolume(0.1f); //takes a float value. 1.0 is full volume.
		music.play(); //starts music
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	/* Render is on loop all the time. GameStateManager needs to update first then we render the game states.
	 * gsm was instantiated as an object of class GameStateManager to use the update and render methods that were created
	 * in that class.*/
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clear wipes the screen clean and redraws everything fresh
		gsm.update(Gdx.graphics.getDeltaTime()); //tells  delta times between renders;
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		super.dispose();//super is a keyword to refer to the superclass
		music.dispose();//get rid of the music resource when you're done with it
	}
}
