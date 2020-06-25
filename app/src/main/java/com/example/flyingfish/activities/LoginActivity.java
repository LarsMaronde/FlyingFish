package com.example.flyingfish.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.db.DatabaseManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Constants.CURRENT_USERNAME = "";

        DatabaseManager dbManager = new DatabaseManager(this);
        try{
            dbManager.insertData();
        }catch (Exception e) {
            //already inserted;
        }
//        dbManager.printAllTables();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login(View v) {

        EditText usernameField = findViewById(R.id.usernameField);
        String username = usernameField.getText().toString();
        EditText passwordField = findViewById(R.id.passwordField);
        String password = passwordField.getText().toString();


        if(!username.isEmpty() || !password.isEmpty()) {

            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String passwordHash = bytesToHex(encodedhash);

            //check if user is correct
            if(DatabaseManager.getInstance().checkCredentials(username, passwordHash)) {

                passwordField.setTextColor(Color.rgb(255,255,255));
                usernameField.setTextColor(Color.rgb(255,255,255));

                Constants.CURRENT_USERNAME = username;
                startActivity(new Intent(this, MainMenuActivity.class));
            }else{
                usernameField.setTextColor(Color.rgb(255,0,0));
                passwordField.setTextColor(Color.rgb(255,0,0));
            }
        }

    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void register(View v){
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
