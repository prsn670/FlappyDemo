package com.ariannelexine.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

import java.util.Random;

/**
 * Created by Arianne on 3/18/17.
 */

public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    //Difference between the openings in the tubes
    private static final int TUBE_GAP = 100;
    /*LOWEST_OPEING is used so that the top the bottom tube can't be below the area of the screen.*/
    private static final int LOWEST_OPENING = 120;

    private Texture topTube, bottomTube;//see following link for Texture class https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/Texture.html
    //x,y position for each of our tubes
    private Vector2 posTopTube, posBotTube;
    //Used to create rectangular boundries for collision detection
    private Rectangle boundsTop, boundsBot;
    //rand is used to randomize the positions of the tubes
    private Random rand;

    /* Argument in method is where the tube will start on the x-axis.*/
    public Tube(float x) {
        topTube = new Texture("topTube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        /* .nextInt is a method of the Random class. It returns the next pseudorandom, uniformly
         * distributed int value from this random number generator's sequence. Variable FLUCTUATION
         * initialized above is used as an argument in .nextInt, it means that the random number generated
         * will be between 0 and 130. */
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        /* The subtraction of bottomTube.getHeight is done because will start drawing the image offscreen as it is
         * a tall image.*/
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        /* Class Rectangle takes four parameters. Tube's x,y positions, and then it's width and height.*/
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());


    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    /* Once our character passes an obstacle and it is no longer seen, we don't need it anymore because there is no
     * backward movement. The following method repositions the tube further in front of the character off-screen. */
    public void reposition(float x) {
        /* Set is a Vector3 method that replaces the element at the specified position in the vector with the specified
         * element. */
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        /* Since the width and height of each tube is static, we only need to update the position of the rectangle
         * boundary when the tube is repositioned.*/
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    /* If the character's boundary overlaps with the boundry of the top or bottom tube, a bool is returned.
     * Method is used in PlayState class for collision obstruction*/
    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public void dispose()
    {
        topTube.dispose();
        bottomTube.dispose();
    }
}
