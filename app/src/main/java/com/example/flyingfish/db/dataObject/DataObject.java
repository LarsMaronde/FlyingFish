package com.example.flyingfish.db.dataObject;

import java.util.Map;

public abstract class DataObject {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract Map<String, Object> toMap();
}
