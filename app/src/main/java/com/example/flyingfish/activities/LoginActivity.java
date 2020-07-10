package com.example.flyingfish.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.db.FirebaseManager;
import com.example.flyingfish.db.callbacks.ErrorCallback;
import com.example.flyingfish.db.callbacks.SuccessCallback;
import com.example.flyingfish.db.dataObject.management.DataObjectManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Constants.CURRENT_USERNAME = "";



        // has to be called to create a new instance but not a
        // new object each time the activity gets created
        FirebaseManager.getInstance();
        DataObjectManager.getInstance();


        if(FirebaseManager.getInstance().currentUser() != null) {
            Constants.CURRENT_USERNAME = FirebaseManager.getInstance().getCurrentUsername();
            startActivity(new Intent(this, MainMenuActivity.class));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login(View v) {

        EditText usernameField = findViewById(R.id.usernameField);
        final String username = usernameField.getText().toString();
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


            FirebaseManager.getInstance().login(username, passwordHash, this, new SuccessCallback() {
                @Override
                public void run(Object... obj) {
                    Context con = (Context) obj[0];
                    Toast.makeText(con, "Logged in", Toast.LENGTH_LONG).show();
                    Constants.CURRENT_USERNAME = username;
                    con.startActivity(new Intent(con, MainMenuActivity.class));
                }
            }, new ErrorCallback() {
                @Override
                public void run(Context con, String errorMsg) {
                    Toast.makeText(con, "Ein Fehler ist aufgetreten: "+ errorMsg,
                    Toast.LENGTH_LONG).show();
                }
            });

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
