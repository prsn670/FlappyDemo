package com.ariannelexine.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Arianne on 3/16/17.
 * Class State - state the game is currently in
 */

//abstract means you don't want to instantiate any instances of state. Will create things that extend(inherit from) state instead
public abstract class State {
    protected OrthographicCamera cam; //each state needs a camera to locate a position in the game world. More info on OrthographicCamera here : https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/OrthographicCamera.html
    protected Vector3 mouse; //a pointer, vector3 = xyz coordinate system
    protected GameStateManager gsm; //way to manage states on top of each other (ex. pause state on top of the current game state)

    //Constructor
    protected State(GameStateManager gsm) {
        this.gsm = gsm; //this pointer. More info on this pointer at: https://msdn.microsoft.com/en-us/library/y0dddwwd.aspx
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    //methods
    protected abstract void handleInput(); //
    public abstract void update(float dt); //dt - delta time, time between one frame rendered and another fram rendered

    /* Render uses the updated data from update to render everything onto the screen.*/
    public abstract void render(SpriteBatch sb); //SpriteBatch sb- container for everything we need to render to screen

    /* This method uses the .dispose method from the Texture class to dispose of textures and other media when you are
     * done using them to prevent memory leaks.*/
    public abstract void dispose();

}
