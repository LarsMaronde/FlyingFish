package com.example.flyingfish.db.dataObject;

import java.util.HashMap;
import java.util.Map;

public class Item extends DataObject {

    private String name;
    private int price;
    private boolean equiped;
    private boolean bought;
    private String username;

    public Item() {
        //empty
    }

    public Item(String name, int price, boolean equiped, boolean bought) {
        this.name = name;
        this.price = price;
        this.equiped = equiped;
        this.bought = bought;
    }

    public Item(String name, int price){
        this.name = name;
        this.price = price;
    }

    public Item(String name, boolean equiped){
        this.name = name;
        this.equiped = equiped;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isEquiped() {
        return equiped;
    }

    public void setEquiped(boolean equiped) {
        this.equiped = equiped;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("equiped", equiped);
        map.put("name", name);
        map.put("username", username);
        return map;
    }
}
