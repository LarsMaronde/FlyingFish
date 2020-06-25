package com.example.flyingfish.gameObjects;

import android.app.Activity;
import android.media.MediaPlayer;
import com.example.flyingfish.R;

public class SoundPlayer {

    private static SoundPlayer instance;
    private MediaPlayer mp;
    private Activity activity;

    public SoundPlayer(Activity activity) {
        this.activity = activity;
        instance = this;
    }

    public static SoundPlayer getInstance() {
        if (instance == null) {
            throw new Error("There is no SoundPlayer yet.");
        }
        return instance;
    }

    public void playBell() {
        mp = MediaPlayer.create(activity, R.raw.gloeckcheneinmal);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
}





