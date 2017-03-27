package com.ariannelexine.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Anil on 3/26/2017.
 * This class will animate our character. For flappy bird it would switch between the pictures of the
 * bird with its wings down, middle, and up.
 * TextureRegion is a LibGDX class. Defines a rectangular area of a texture. The coordinate
 * system used has its origin in the upper left corner with the x-axis pointing to the right
 * and the y axis pointing downwards. Link: https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/TextureRegion.html
 */

public class Animation {
    private Array<TextureRegion> frames;

    /* Tells game how long a frame needs to stay in view before switching.*/
    private float maxFrameTime;

    /* Time that animation has been in current frame.*/
    private float currentFrameTime;
    private int frameCount;
    private int frame; //current frame you game is in.

    /*constructor. The region is all the frames combined into one image. The cycleTime is how long it's
     * going to cycle through all images.*/

    public Animation(TextureRegion region, int frameCount,float cycleTime)
    {
        frames = new Array<TextureRegion>();
        /* Since the region is a combination of all the frames, we need to divide by the number
         * of frames to get the width of one frame*/
        int frameWidth = region.getRegionWidth()/frameCount;

        /* This for loop takes the entire texture region and adds a section of that region to our TextureRegion
         * array. The TextureRegion constructor takes in the arguments of texture, the starting x, y positions of the texture,
          * then the width and height (respectively) of the texture. Basically it is cutting the entire image into slices
          * and putting each slice in the array. There is some inconsistency with what the documentation says how the
          * x, y and coordinates are positioned versus what the instructor said, but we can figure that out later.*/
        for(int i = 0; i < frameCount; i++)
        {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        //each frame gets an equal number of time to show
        maxFrameTime = cycleTime/frameCount;
        frame = 0; //starting frame index
    }

    public void update(float dt)
    {
        currentFrameTime += dt; //how long current frame has been in view
        /* If the time the current frame has show in greater than the allowed frame time you
         * you increment to the next frame in the array, then reset the currentFrameTime*/
        if(currentFrameTime > maxFrameTime)
        {
            frame++;
            currentFrameTime = 0;
        }

        //cycles back to the first frame once you've reached to the end of the array
        if(frame >= frameCount)
        {
            frame = 0;
        }
    }

    /* Get the frame the animation is currently on.*/

    public TextureRegion get_frame()
    {
        return frames.get(frame);
    }
}
