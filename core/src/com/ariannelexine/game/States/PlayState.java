package com.ariannelexine.game.States;

import com.ariannelexine.game.FlappyDemo;
import com.ariannelexine.game.sprites.Bird;
import com.ariannelexine.game.sprites.Tube;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Arianne on 3/17/17.
 * Holds all the mechanics for the actual game.
 */

/* Inherits from State class*/
public class PlayState extends State {
    //space between tubes. start of one tube to the start of the next tube
    private static final int TUBE_SPACING = 125;
    //how many tubes total that the game will have at any given time.
    private static final int TUBE_COUNT = 4;
    /* Since the ground was too high, we will offset it's y-position by lowering it. This variable will be used
     * when positioning the ground.*/
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    /*Holds the positions of the 2 grounds that will be on the screen*/
    private Vector2 groundPos1, groundPos2;


    /* This is an array of classes, in this case class Tube*/
    private Array<Tube> tubes;

    //Constructor
    public PlayState(GameStateManager gsm) {
        /* super is used to refer to the gsm from the PlayState's superclass -> State. More info on the super() function
         * here: https://docs.oracle.com/javase/tutorial/java/IandI/super.html*/
        super(gsm);
        bird = new Bird(50, 300);

        /* views only a partial area of the game world, which makes it look like the texture is zoomed in.
         * setToOrtho is a method of the OrthographicCameraClass (libGDX class)
         * More info here: https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/OrthographicCamera.html#setToOrtho-boolean-
         * first argument is a y-down true or false. Does y start at top of screen(true) or bottom-left(false).
         * Next two parameters are the viewport width and the viewport height. These parameters dictate how much of the
         * gameworld you see at any given time. The term viewport refers to how much of game world you see.*/
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        /*Since we want our ground to start originally in the left side of the camera, we have:*/
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        /*sSince the second part of the ground needs to go after the first, we add the width of the ground texture
         * to offset the position of groundPos2.*/
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);


        tubes = new Array<Tube>();

        /* As far as I can tell, the add method is for class ArrayList, but is being used with class Array.
         * I can't find the .add method under the class descriptions for Array. This may add a new Tube class
         * to the beginning of the array and shifts everything else to the right. But I believe it appends the end of the
         * array instead. Link: http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html#add(E)*/
        for(int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    public void handleInput() {

        /* The justTouched method returns a boolean if a new touch down event just occurred.*/
        if(Gdx.input.justTouched())
            bird.jump();


    }

    @Override

    public void update(float dt) {
        handleInput();/* Constantly checks input to see if user has done anything*/
        updateGround();
        bird.update( dt);
        /* This updates the camera positions based on where our character is.*/
        cam.position.x = bird.getPosition().x + 80;

        /*viewportWidth is a variable in the setToOrtho method of class OrthoGraphicCamera.
         * This is an enhanced for loop. It initializes an object of type Tube that iterates through the array
         * tubes, which is just an array of classes Tube.*/
        for(int i = 0; i < tubes.size; i++)
        {
            Tube tube = tubes.get(i);
            /* When tube is off the left side of the screen, reposition the tube. The second part of the inequality
             * calculates where the end of tube is. If the position of the right side of the tube is less than what
             * you can see in the viewport, then reposition.*/
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
                /* The stuff after the + sign is used to move the position all the way to end of the other tubes*/
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));

            if(tube.collides(bird.getBounds()))
                gsm.set(new PlayState(gsm));

            /* If the character collides with the ground, kill the character.*/
            if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            {
                gsm.set(new PlayState(gsm));
            }
        }


        /* Our camera is repositions based on the characters position above. So we have to call update
         * to tell libGDX that camera has been repositioned.*/
        cam.update();
    }

    @Override
    /* Since we used cam.SetToOrtho to set our viewport we have to adjust the SpriteBatch so it knows
     * the coordinate system it is working with in relation to the camera in order to draw. */
    public void render(SpriteBatch sb) {
        /* setProjectionMatrix is a method of class SpriteBatch. It draws only the part of the game world to the screen that
         * the player can see on the screen. */
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        /* The below line of code draws the background where the camera is pointing at any given moment.
         * position of camera - (width of the screen you can see)/2. viewportWidth is a variable in the setToOrtho method of
         * class OrthoGraphicCamera. Makes background halfway off of the screen.*/
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube: tubes)
        {
            tube.dispose();
        }
        System.out.println("Play State Disposed");
    }

    /* Which check if our camera is past the position where the ground ends. If it does, we adjust
     * position bye moving it along the x-axis, in front of the ground that was originally in front of it.*/
    private void updateGround()
    {
        if(cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth())
        {
            groundPos1.add(ground.getWidth() * 2, 0);
        }

        if(cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth())
        {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
