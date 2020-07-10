package com.example.flyingfish.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.db.FirebaseManager;
import com.example.flyingfish.db.callbacks.ErrorCallback;
import com.example.flyingfish.db.callbacks.SuccessCallback;

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
        final View button = findViewById(R.id.signupButton);
        button.setEnabled(false);
        EditText usernameField = findViewById(R.id.usernameField);
        EditText passwordField = findViewById(R.id.passwordField);
        EditText passwordConfirmField = findViewById(R.id.passwordConfirmField);

        final String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String passwordConfirm = passwordConfirmField.getText().toString();

        if(!username.isEmpty() || !password.isEmpty() || !passwordConfirm.isEmpty()) {

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
                FirebaseManager.getInstance().registerUser(username, passwordHash, this, new SuccessCallback() {
                    @Override
                    public void run(Object... obj) {
                        Context con = (Context) obj[0];
                        Toast.makeText(con, "User created", Toast.LENGTH_LONG).show();
                        Constants.CURRENT_USERNAME = username;
                        con.startActivity(new Intent(con, MainMenuActivity.class));
                    }
                }, new ErrorCallback() {
                    //when username is already taken
                    @Override
                    public void run(Context con, String errorMsg) {
                        Toast.makeText(con, "Ein Fehler ist aufgetreten: "+errorMsg, Toast.LENGTH_LONG).show();
                        button.setEnabled(true);
                    }
                });



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
