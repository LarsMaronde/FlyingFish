package com.example.flyingfish.gameObjects;

/**
 * Abstract class defines a gameobject that is drawn onto the screen
 * Each gameobject has to implement a draw and update method
 * the update method updates the properties of the object at each frame
 * and the draw method draws the object onto the screen (or updates the view)
 */
public abstract class GameObject {


    public abstract void draw();

    public abstract void update();

}
