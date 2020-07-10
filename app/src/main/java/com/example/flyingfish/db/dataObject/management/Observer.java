package com.example.flyingfish.db.dataObject.management;

import com.example.flyingfish.db.dataObject.Item;
import com.example.flyingfish.db.dataObject.Level;
import com.example.flyingfish.db.dataObject.User;

import java.util.ArrayList;

public interface Observer {

    void updateUsers(ArrayList<User> users);

    void updateItems(ArrayList<Item> items);

    void updateLevel(ArrayList<Level> levels);
}
