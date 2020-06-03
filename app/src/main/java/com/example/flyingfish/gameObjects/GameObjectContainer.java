package com.example.flyingfish.gameObjects;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameObjectContainer extends GameObject {

    private final List<GameObject> gameObjects;
    private final List<GameObject> GameObjectsToRemove;
    private final List<GameObject> GameObjectsToAdd;
    private boolean visible = true;

    public GameObjectContainer(){
        gameObjects = new ArrayList<GameObject>();
        GameObjectsToRemove = new ArrayList<GameObject>();
        GameObjectsToAdd = new ArrayList<GameObject>();
    }

    @Override
    public void draw() {

            for (GameObject s: gameObjects) {
                s.draw();
            }

    }

    @Override
    public void update() {
        for (GameObject s: gameObjects) {
            s.update();
        }
        for(GameObject s: GameObjectsToRemove){
            gameObjects.remove(s);
        }
        GameObjectsToRemove.clear();
        for(GameObject s: GameObjectsToAdd){
            gameObjects.add(s);
            s.setVisible(visible);
        }
        GameObjectsToAdd.clear();
    }

    public void addGameObject(GameObject s){
        s.setParent(this);
        GameObjectsToAdd.add(s);
    }

    public void removeGameObject(GameObject s){
        GameObjectsToRemove.add(s);
    }

    public int size(){
        return gameObjects.size();
    }

    @Override
    public void setVisible(boolean value) {
        visible = value;
        for (GameObject go: gameObjects ) {
            go.setVisible(value);
        }
    }
}
