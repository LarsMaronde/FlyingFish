package com.example.flyingfish.dataObject;

public class Item {
    private String name;
    private int price;
    private boolean equiped;
    private boolean bought;

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
}
