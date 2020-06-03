package com.example.flyingfish.gameObjects;


import android.view.View;

public abstract class GameObject {

    private GameObjectContainer parent;
    public abstract void draw();
    public abstract void update();

    public GameObjectContainer getParent() {
        return parent;
    }

    public void setVisible(boolean value){

    }

    protected void setParent(GameObjectContainer parent) {
        this.parent = parent;
    }
}
