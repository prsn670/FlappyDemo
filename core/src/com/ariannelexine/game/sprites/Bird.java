package com.ariannelexine.game.sprites;
/* This class is used to set the position of our character texture on the screen, allowing it to jump up and down.*/
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Arianne on 3/18/17.
 */

public class Bird {
    private static final int GRAVITY = -15; //variable that dictates how fast the character falls
    private static final int MOVEMENT = 100;//the horizontal (x-axis) movement of our character.
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    //private Animation birdAnimation; //can use this when we get our character made
    //private Texture texture //can use this when we get our character made

    private Texture bird;
    /* We create the Sound object here because we want the the sound effect associated with the character*/
    private Sound flap;

    //constructor takes parameters that refer to character's starting position.
    public Bird(int x, int y) {

        /* We're using a Vector3 class that has parameters for an x, y and z coordinate system
         * for the position; but since our game is 2d, the last parameter for the z-coord will be 0.
         * There is also a Vector2 class, but the tutorial we used decided on the Vector3 class. I believe
         * it wouldn't have made a difference to use Vector2 instead.*/
        position = new Vector3(x, y, 0);//starting position

        /* Starting velocity is all 0's because we are not moving at the very beginning.*/
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("aud.psd");
        //texture = new Texture("birdanimation.png"); Part of the animation, can use this later with our character
        /* Invoking the construction of our Animation class. Puts in texture, # of frames, and the total cycle time.*/
        //birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, bird.getWidth(), bird.getHeight()); //sets the bounds of the character.
        //bounds = new Rectangle(x, y, texture.getWidth()/3, texture.getHeight()); //Part of the animation portion of video
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));//the original file from github was corrupted. This is another sound effect
    }

    /* This update class sends the delta time to this class that allows it to update the position of our character texture*/
    public void update(float dt) {

        //birdAnimation.update(dt); //part of the Animation portion of the code

        /* Every time the character texture is updated, we need to add GRAVITY to our velocity in the y-position, but add
         * nothing in the x, z position. Gravity is only added if the character hasn't hit the ground yet.*/
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        /* Since we're changing it's velocity with respect to the change time (dt) we need to scale it's velocity
         * by the change in time. The .scl method is a Vector3 method, it will multiply velocity by our delta time(dt).
         * The parameter in this case is just a float value. In terms of vectors, dt would be a scalar value that
         * multiplies the entire vector. */
        velocity.scl(dt);

        /* Sets our position. Our new scaled velocity in the y parameter is set as our new y-coord in our position Vector.
         * The MOVEMENT variable is multiplied by our delta time to account for the move related to delta time*/
        position.add(MOVEMENT * dt, velocity.y, 0);

        /* This makes sure that when our character hits the bottom of the screen, it doesn't keep going where we can't see it.*/
        if(position.y < 0)
            position.y = 0;

        /*This reverses the scaling so that it can added again in the next frame.*/
        velocity.scl(1/dt);
        /* Update the position of your bounds based on where the character is*/
        bounds.setPosition(position.x, position.y);
    }


    /* These getters are used to get information back to our PlayState.*/
    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return bird;
    }

    //part of Animation portion of code
    /*public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }*/

    public void jump()
    {
        velocity.y = 250;
        flap.play(0.5f); /* Whenever our character jumps, it plays this sound effect
                          * The volume is set to 50% of max(1).*/
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose() {
        bird.dispose(); /*texture.dispose() used for animation portion*/
        flap.dispose();
    }
}
