package com.ariannelexine.game.States;

import com.ariannelexine.game.FlappyDemo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Arianne on 3/17/17.
 * MenuState extends State, that is MenuState inherits from State.
 */

public class MenuState extends State {
/* Texture is a class from LibGDX.
 * Link here: https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/Texture.html*/
    private Texture background;
    private Texture playBtn;
    //constructor
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);//copied from Playstate class. Helps when porting over to phone
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    /* The justTouched method of the Gdx class returns a boolean when a new touch down event just occurred.*/
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();/* Constantly checks input to see if user has done anything*/

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);//see render method in Playstate for info on setProjectionMatrix
        sb.begin(); /* "Opens" the SpriteBatch container*/
        /* .draw() method draws the texture. It takes arguments SpriteBatch object.draw(texture, x-coord, y-coord, width of screen, height of screen)
         * 0, 0 is the bottom left hand corner of the screen*/
        sb.draw(background, 0, 0);//we don't have the width and height parameters because we are letting the phone decide
        /* If you don't give it the screen size as in the bottom code of the line it will use the default of what it actually
         * is in the image. The equation used to calculate the x-coord below is done this way because FlappyDemo.WIDTH/2
         * will place the button in the middle of screen along the x-coord, but it will start drawing the button
         * from it's bottom left corner, shifting it to the right, making it look like it isn't actually centered.
         * So we subtract half the width of the texture playBtn to offset that.*/
        sb.draw(playBtn, cam.position.x - playBtn.getWidth()/2, cam.position.y); //centers button
        sb.end();//closes the box
    }

    @Override
    /* This method uses the .dispose method from the Texture class to dispose of textures and other media when you are
     * done using them to prevent memory leaks. This is called when you transition states.*/
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }



}
