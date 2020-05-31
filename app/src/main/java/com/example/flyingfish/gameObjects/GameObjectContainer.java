package com.example.flyingfish.gameObjects;

import java.util.ArrayList;
import java.util.List;

public class GameObjectContainer extends GameObject {

    private final List<GameObject> GameObjects;
    private final List<GameObject> GameObjectsToRemove;
    private final List<GameObject> GameObjectsToAdd;

    public GameObjectContainer(){
        GameObjects = new ArrayList<GameObject>();
        GameObjectsToRemove = new ArrayList<GameObject>();
        GameObjectsToAdd = new ArrayList<GameObject>();
    }

    @Override
    public void draw() {
        for (GameObject s: GameObjects) {
            s.draw();
        }
    }

    @Override
    public void update() {
        for (GameObject s: GameObjects) {
            s.update();
        }
        for(GameObject s: GameObjectsToRemove){
            GameObjects.remove(s);
        }
        GameObjectsToRemove.clear();
        for(GameObject s: GameObjectsToAdd){
            GameObjects.add(s);
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
        return GameObjects.size();
    }
}
