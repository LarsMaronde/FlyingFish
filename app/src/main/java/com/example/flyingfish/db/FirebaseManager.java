/**
 * this class represents the connection to the Firebase Firestore database
 * at initialisation it registers eventlisteners to update to changes on
 * the database if changes occur the data inside of the DataObjectManager object are updated
 */
package com.example.flyingfish.db;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.flyingfish.db.callbacks.ErrorCallback;
import com.example.flyingfish.db.callbacks.SuccessCallback;
import com.example.flyingfish.db.dataObject.DataObject;
import com.example.flyingfish.db.dataObject.Item;
import com.example.flyingfish.db.dataObject.Level;
import com.example.flyingfish.db.dataObject.User;
import com.example.flyingfish.db.dataObject.UserItem;
import com.example.flyingfish.db.dataObject.UserLevel;
import com.example.flyingfish.db.dataObject.management.DataObjectManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class FirebaseManager implements DatabaseConnector {

    private static FirebaseManager fbManager;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private LinkedList<ListenerRegistration> databaseListeners;

    //makes sure the data is loaded in the correct order
    private boolean userDataLoaded;
    private boolean levelDataLoaded;
    private boolean itemDataLoaded;

    public FirebaseManager() {
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        fbManager = this;

        userDataLoaded = false;
        levelDataLoaded = false;
        itemDataLoaded = false;

        databaseListeners = new LinkedList<>();

        initializeListeners();
    }


    /**
     * initializes all listeners to listen to changes
     */
    public void initializeListeners() {
        for(ListenerRegistration rl: databaseListeners){
            rl.remove();
        }
        databaseListeners.clear();

        databaseListeners.add(addUserListener());

        if(userDataLoaded) {
            databaseListeners.add(addItemListener());
        }

        if(itemDataLoaded && userDataLoaded) {
            databaseListeners.add(addLevelsListener());
        }

        if(levelDataLoaded && userDataLoaded && itemDataLoaded) {
            databaseListeners.add(addUserLevelsListener());
            databaseListeners.add(addUserItemsListener());
        }
    }


    public static FirebaseManager getInstance() {
        if(fbManager != null) {
            return fbManager;
        }
        fbManager = new FirebaseManager();
        return fbManager;
    }


    /**
     * tries to register a user, if possible the given successcallback is executed
     * and the user is saved into the database
     * if it is not possible, the errorcallback is executed with the error as parameter
     * @param username the username
     * @param password the hashed password
     * @param con the context of the caller
     * @param successCallback the callback to execute when registration is successfull
     * @param errorCallback the error callback to execute when registration failed
     */
    public void registerUser(final String username, final String password, final Context con, final SuccessCallback successCallback, final ErrorCallback errorCallback) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.createUserWithEmailAndPassword(username+"@flyingfish.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                saveNewUser(username, password);
                successCallback.run(con);
            }else {
                errorCallback.run(con, task.getException().getMessage().replace("email address", "username"));
            }
            }
        });
    }

    /**
     * logs the user out of the firebase
     */
    public void logout() {
        this.fAuth.signOut();
    }


    /**
     * tries to log the user in, checks the firebase authentication via FirebaseAuth
     * if the login was successfull, the successcallback is executed
     * if the login failed, the errorcallback is executed and the error message is passed in as parameter
     * @param username the username
     * @param password the hashed password
     * @param con the context of the caller
     * @param successCallback the callback to execute when login is successfull
     * @param errorCallback the callback to execute when registration failed
     */
    public void login(final String username, final String password, final Context con, final SuccessCallback successCallback, final ErrorCallback errorCallback) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signInWithEmailAndPassword(username+"@flyingfish.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                successCallback.run(con);
            }else {
                errorCallback.run(con, task.getException().getMessage().replace("email address", "username"));
            }
            }
        });
    }

    /**
     * saves a new user in the database
     * the initial fish skin also added to the user and the first level is unlocked for the user
     * @param username the username to store
     * @param password the hashed password to store
     */
    private void saveNewUser(String username, String password) {
        final User u = new User(username, password, 0);
        DataObjectManager.getInstance().addUser(u);

        fStore.collection("Users").add(u.toMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                u.setId(task.getResult().getId());
            }
        });

        createDocument("Users", u);

        Item skin = new Item("Standard Fisch", 0, true, true);
        skin.setUsername(u.getUsername());
        u.addItem(skin);
        createDocument("UserItems", skin);

        Level refLevel = DataObjectManager.getInstance().getLevelByNumber(1);
        Level firstLevel = new Level(1, refLevel.getMaxCoinAmount(), 0);
        u.addLevel(firstLevel);
        firstLevel.setUserLevel(true);
        firstLevel.setUsername(u.getUsername());
        createDocument("UserLevels", firstLevel);

        DataObjectManager.getInstance().notifyAllChange();
    }





    /**************************************
                DATABASE LISTENERS
    ***************************************/


    private ListenerRegistration addUserListener() {
        return fStore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if(snapshot != null){
                    List<DocumentSnapshot> list = snapshot.getDocuments();
                    for(DocumentSnapshot ds: list) {
                        User u = ds.toObject(User.class);
                        u.setId(ds.getId());

                        User sourceU = DataObjectManager.getInstance().getUserByName(u.getUsername());
                        if(sourceU != null){
                            sourceU.setCurrentAmountCoins(u.getCurrentAmountCoins());
                            sourceU.setPassword(u.getPassword());
                            sourceU.setId(ds.getId());
                        }else {
                            DataObjectManager.getInstance().addUser(u);
                        }
                    }

                    for(DocumentChange doc: snapshot.getDocumentChanges()) {
                        if(doc.getType() == DocumentChange.Type.REMOVED){
                            User u = doc.getDocument().toObject(User.class);
                            DataObjectManager.getInstance().removeUserByName(u.getUsername());
                        }
                    }
                    DataObjectManager.getInstance().notifyUserChange();
                    if(!userDataLoaded) {
                        userDataLoaded = true;
                        initializeListeners();
                    }
                }
                if(!userDataLoaded) {
                    userDataLoaded = true;
                    initializeListeners();
                }
            }
        });
    }

    private ListenerRegistration addItemListener() {
        return fStore.collection("Items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if(snapshot != null){
                    List<DocumentSnapshot> list = snapshot.getDocuments();
                    // System.out.println("NEW ITEM UPDATE "+list.size());
                    for(DocumentSnapshot ds: list) {
                        Item i = ds.toObject(Item.class);
                        i.setId(ds.getId());
                        Item sourceI = DataObjectManager.getInstance().getItemByName(i.getName());
                        if(sourceI != null){
                            sourceI.setPrice(i.getPrice());
                            sourceI.setId(ds.getId());
                        }else {
                            DataObjectManager.getInstance().addItem(i);
                        }
                    }

                    for(DocumentChange doc: snapshot.getDocumentChanges()) {
                        if(doc.getType() == DocumentChange.Type.REMOVED){
                            Item i = doc.getDocument().toObject(Item.class);
                            DataObjectManager.getInstance().removeItemByName(i.getName());
                        }
                    }
                    DataObjectManager.getInstance().notifyItemChange();
                    if(!itemDataLoaded){
                        itemDataLoaded = true;
                        initializeListeners();
                    }
                }
                if(!itemDataLoaded){
                    itemDataLoaded = true;
                    initializeListeners();
                }
            }
        });
    }

    public ListenerRegistration addUserItemsListener() {
        return fStore.collection("UserItems").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if(snapshot != null){
                    List<DocumentSnapshot> list = snapshot.getDocuments();
                    for(DocumentSnapshot ds: list) {
                        UserItem ui = ds.toObject(UserItem.class);
                        ui.setId(ds.getId());
                        User sourceUser = DataObjectManager.getInstance().getUserByName(ui.getUsername());
                        if(sourceUser != null) {
                            Item i = sourceUser.getItemByName(ui.getName());
                            if(i == null) {
                                Item targetItem = DataObjectManager.getInstance().getItemByName(ui.getName());
                                if(targetItem != null){
                                    int price = targetItem.getPrice();
                                    Item newItem = new Item(ui.getName(), price, ui.isEquiped(), true);
                                    newItem.setId(ds.getId());
                                    sourceUser.addItem(newItem);
                                }
                            }else {
                                i.setEquiped(ui.isEquiped());
                                i.setBought(true);
                                i.setId(ds.getId());
                            }
                        }
                    }

                   for(DocumentChange doc: snapshot.getDocumentChanges()) {
                        if(doc.getType() == DocumentChange.Type.REMOVED) {
                            UserItem ui = doc.getDocument().toObject(UserItem.class);
                            User sourceUser = DataObjectManager.getInstance().getUserByName(ui.getUsername());
                            if(sourceUser != null){
                                sourceUser.removeItemByName(ui.getName());
                            }
                        }
                   }
                    DataObjectManager.getInstance().notifyUserChange();
                    DataObjectManager.getInstance().notifyItemChange();
                }
            }
        });
    }

    public ListenerRegistration addLevelsListener() {
        return fStore.collection("Levels").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if(snapshot != null) {
                    List<DocumentSnapshot> list = snapshot.getDocuments();
                    for(DocumentSnapshot ds: list) {

                        //weirdly firestore does not map this properties automatically
                        int levelNumber = Math.toIntExact(ds.getLong("levelNumber"));
                        int maxCoinAmount = Math.toIntExact(ds.getLong("maxCoinAmount"));
                        Level l = new Level(levelNumber, maxCoinAmount);
                        l.setId(ds.getId());

                        Level sourceLevel = DataObjectManager.getInstance().getLevelByNumber(l.getLevelNumber());
                        if(sourceLevel == null){
                            DataObjectManager.getInstance().addLevel(l);
                        }else {
                            sourceLevel.setMaxCoinAmount(l.getMaxCoinAmount());
                            sourceLevel.setId(ds.getId());
                        }
                    }
                    DataObjectManager.getInstance().notifyLevelChange();
                    if(!levelDataLoaded){
                        levelDataLoaded = true;
                        initializeListeners();
                    }
                }
                if(!levelDataLoaded){
                    levelDataLoaded = true;
                    initializeListeners();
                }
            }
        });
    }

    public ListenerRegistration addUserLevelsListener() {
        return fStore.collection("UserLevels").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if(snapshot != null){
                    List<DocumentSnapshot> list = snapshot.getDocuments();
                    for(DocumentSnapshot ds: list) {
                        UserLevel ul = ds.toObject(UserLevel.class);
                        ul.setId(ds.getId());

                        //weirdly firestore does not map this property automatically
                        int levelNumber = Math.toIntExact(ds.getLong("levelNumber"));
                        ul.setLevelNumber(levelNumber);

                        User sourceUser = DataObjectManager.getInstance().getUserByName(ul.getUsername());
                        if(sourceUser != null) {
                            Level l = sourceUser.getLevelByNumber(levelNumber);
                            if(l == null){
                                Level newLevel = new Level(levelNumber, true, ul.getCollectedCoins());
                                newLevel.setId(ds.getId());
                                sourceUser.addLevel(newLevel);
                            }else {
                                l.setUnlocked(true);
                                l.setCollectedCoins(ul.getCollectedCoins());
                                l.setId(ds.getId());
                            }
                        }
                    }

                    for(DocumentChange doc: snapshot.getDocumentChanges()) {
                        if(doc.getType() == DocumentChange.Type.REMOVED) {
                            UserLevel ul = doc.getDocument().toObject(UserLevel.class);
                            User sourceUser = DataObjectManager.getInstance().getUserByName(ul.getUsername());
                            if(sourceUser != null){
                                sourceUser.removeLevelByNumber(ul.getLevelNumber());
                            }
                        }
                    }
                    DataObjectManager.getInstance().notifyUserChange();
                    DataObjectManager.getInstance().notifyLevelChange();
                }
            }
        });
    }


    /**
     * updates the given DataObject inside the given collection, document
     * @param collection the collection in which the document is in
     * @param document the id of the document to update
     * @param obj the object that has to be updated
     */
    public void updateDocument(final String collection, final String document, final DataObject obj) {
        fStore.collection(collection).document(document).update(obj.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("successfully updated "+document+" in "+collection);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
    }

    /**
     * creates a new document in the given collection with the DataObject as data
     * @param collection the collection
     * @param obj the DataObject containing the information to store
     */
    public void createDocument(final String collection, final DataObject obj) {
        fStore.collection(collection).add(obj.toMap()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("successfully created in "+collection);
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                obj.setId(task.getResult().getId());
            }
        });
    }

    /**
     * returns the logged in user
     * @return
     */
    public FirebaseUser currentUser() {
         return this.fAuth.getCurrentUser();
    }

    /**
     * returns the username of the current logged in user
     * @return
     */
    public String getCurrentUsername() {
         return this.fAuth.getCurrentUser().getEmail().split("@")[0];
    }
}
