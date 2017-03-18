package com.ariannelexine.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Arianne on 3/16/17.
 * Class State - state the game is currently in
 */

public abstract class State {
    protected OrthographicCamera cam; //each state needs a camera to locate a position in the game world
    protected Vector3 mouse; //a pointer, vector3 = xyz coordinate
    protected GameStateManager gsm; //way to manage states on top of eachother (ex. pause state on top of game state)

    protected State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput(); //
    public abstract void update(float dt); //dt - delta time, time between one frame rendered and another fram rendered
    public abstract void render(SpriteBatch sb); //sb - container for everything we need to render to screen
    public abstract void dispose(); //used to avoid memory leaks

}
