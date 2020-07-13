/**
 * defines a DataObject, a DataObject is an object that represents
 * the data that is stored inside a database
 * each DataObject has an unique id that represents the id of the document
 * each DataObject has to implement the toMap() method that is called when
 * the DataObject is stored inside of the database
 */
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
