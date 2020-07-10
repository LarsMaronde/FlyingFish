package com.example.flyingfish.db.dataObject;

import com.example.flyingfish.db.dataObject.management.DataObject;

import java.util.Map;

public class UserItem extends DataObject {

    private String name;
    private String username;
    private boolean equiped;

    public UserItem() {
        //empty
    }

    public UserItem(String name, String username, boolean equiped) {
        this.name = name;
        this.username = username;
        this.equiped = equiped;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEquiped() {
        return equiped;
    }

    public void setEquiped(boolean equiped) {
        this.equiped = equiped;
    }

    public void updateDatabase() {

    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }
}

