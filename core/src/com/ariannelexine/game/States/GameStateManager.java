package com.ariannelexine.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Arianne on 3/17/17.
 * GameStateManager manages the states of the game.
 * ex we have a play state, and the player pauses, the pause state is put on top of
 * the game state. So then the pause state is being updated and rendered. If we unpause, the
 * pause state gets removed and the active state is the play state again.
 * Best done with STACK of states. Renders the one on top. e.g. play state is on stack -> pausing game pushes pause state
 * on top of stack -> un-pausing pops the pause state off of the stack and you are pointing back at your play state.
 */

public class GameStateManager {

    /* Stack is a class in Java. More info: https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
     * The '<>' is a Java generic. They were designed to extend Java's type system to allow
     * “a type or method to operate on objects of various types while providing compile-time type safety.”
     *  This is why the class State can be used as an argument. More info at: https://en.wikipedia.org/wiki/Generics_in_Java*/
    private Stack<State> states;

    //Constructor
    public GameStateManager() {
        states = new Stack<State>();
    }

    /* Remember that states was instantiated as an object of Stack. Stack contains the push and pop methods.
     * states stores the game state on a stack from our created class State.*/
    public void push(State state) {
        states.push(state);
    }

    /* When we pop a state off the stack, it means we don't need it anymore. So we dispose of it.*/
    public void pop() {states.pop().dispose();}

    /* This method is for times when you want to pop a state and then immediately push another one onto it.*/
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    /* .peek looks at top object of the stack. dt = delta time, the time between 2 renders.
     * Remember that update will update everything, so all of the math and logic goes in update.*/
    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}

