package com.example.flyingfish.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.dataObject.User;
import com.example.flyingfish.db.DatabaseManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void register(View v) {
        EditText usernameField = findViewById(R.id.usernameField);
        EditText passwordField = findViewById(R.id.passwordField);
        EditText passwordConfirmField = findViewById(R.id.passwordConfirmField);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String passwordConfirm = passwordConfirmField.getText().toString();

        if(!username.isEmpty() || !password.isEmpty() || !passwordConfirm.isEmpty()) {

            //ceck if username is taken
            String u = DatabaseManager.getInstance().getUser(username);
            if(u != null) {
                usernameField.setTextColor(Color.rgb(255,0,0));
                return;
            }

            usernameField.setTextColor(Color.rgb(255,255,255));
            //CHECK IF PASSWORD IS MATCHING
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            String passwordHash = bytesToHex(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
            String passwordConfirmHash = bytesToHex(digest.digest(passwordConfirm.getBytes(StandardCharsets.UTF_8)));

            if(passwordHash.equals(passwordConfirmHash)) {
                passwordField.setTextColor(Color.rgb(255,255,255));
                passwordConfirmField.setTextColor(Color.rgb(255,255,255));

                //SAVE IN DB
                DatabaseManager.getInstance().createUser(username, passwordHash);

                Constants.CURRENT_USERNAME = username;
                startActivity(new Intent(this, MainMenuActivity.class));
            }else {
                passwordField.setTextColor(Color.rgb(255,0,0));
                passwordConfirmField.setTextColor(Color.rgb(255,0,0));
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


    public void backToLogin(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
