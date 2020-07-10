package com.example.flyingfish.gameObjects;

import android.app.Activity;
import android.media.MediaPlayer;
import com.example.flyingfish.R;

public class SoundPlayer {

    private static SoundPlayer instance;
    private MediaPlayer backgroundmp;
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
        MediaPlayer mp = MediaPlayer.create(activity, R.raw.einsammeln);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void playCollision() {
        MediaPlayer mp = MediaPlayer.create(activity, R.raw.schlag);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
    }

    public void playLevelWin() {
        MediaPlayer mp = MediaPlayer.create(activity, R.raw.fanfare);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void playLevelLost() {
        MediaPlayer mp = MediaPlayer.create(activity, R.raw.youlose);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void playBackground() {
        backgroundmp = MediaPlayer.create(activity, R.raw.bubbles);
        backgroundmp.start();
        backgroundmp.setLooping(true);
        backgroundmp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void stopBackground() {
        backgroundmp.stop();
        backgroundmp.release();
    }
}





