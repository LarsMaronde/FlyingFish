/**
 * The fish represents the player of the game.
 * the fish is created by the GamePanel class and it also sets the properties
 */
package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.flyingfish.Constants;
import com.example.flyingfish.GamePanel;
import com.example.flyingfish.db.dataObject.management.DataObjectManager;
import com.example.flyingfish.gameObjects.interfaces.CircleHitbox;


public class Fish extends GameObject implements CircleHitbox {

    private ImageView imageView;
    private Rect rectangle;
    private double velocityCap; //sets the maximum value of velocity the player can make
    private double velocity;
    private double gravity;
    private double lift;
    private int x, y;
    private float width;
    public enum State {ALIVE, ROTTEN, DEAD}
    private State state = State.ALIVE;

    private Drawable look1, look2;

    public Fish(int x, int y, double gravity, double lift, double velocityCap, ViewGroup container) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.velocityCap = velocityCap;
        this.gravity = gravity;
        this.lift = lift;

        setImageOfFish(container);

        update();
    }

    /**
     * sets the image of the fish
     * this method is called when tapping the screen to change the appearance of the fish
     * @param container the parent container of this imageview
     */
    private void setImageOfFish(ViewGroup container) {
        Context context = container.getContext();

        String skin = DataObjectManager.getInstance().getSelectedFishSkin(Constants.CURRENT_USERNAME).toLowerCase().replace(" ", "_");
        int resourceId = container.getResources().getIdentifier(skin+"_1", "drawable", context.getPackageName());
        this.look1 = container.getResources().getDrawable(resourceId);;

        int resourceId2 = container.getResources().getIdentifier(skin+"_2", "drawable", context.getPackageName());
        this.look2 = container.getResources().getDrawable(resourceId2);


        this.imageView = new ImageView(context);
        this.imageView.setImageDrawable(this.look1);
        container.addView(this.imageView);
        this.width = this.look1.getIntrinsicHeight() * 2;

        //dynamic scaling for different sceen sizes
        this.width = (float) (Constants.SCREEN_HEIGHT/10);

        this.rectangle = new Rect(0, 0, (int) this.width, (int) this.width);
    }

    /**
     * updates the imageviews position on the screen
     */
    @Override
    public void draw() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.imageView.getLayoutParams();
        params.width = this.rectangle.width();
        params.height = this.rectangle.height();
        params.leftMargin = this.rectangle.left;
        params.topMargin = this.rectangle.top;
        this.imageView.setLayoutParams(params);
    }


    /**
     * updates the fish properties
     * applies gravity and checks the state
     */
    @Override
    public void update() {
        this.velocity += this.gravity;
        this.y += this.velocity;

        if(this.checkCollisionWithTopOrBottom()){
            if(this.state == State.ALIVE){
                SoundPlayer.getInstance().playCollision();
            }
            this.die();
        }

        rectangle.set(x - rectangle.width() / 2, y - rectangle.height() / 2,
                x + rectangle.width() / 2, y + rectangle.height() / 2);

        if (this.y > Constants.SCREEN_HEIGHT - rectangle.height()) {
            this.velocity = 0;
            if(state == State.DEAD){
                state = State.ROTTEN;
                GamePanel.getInstance().gameOver();
            }
        }

        if (this.y < 0) {
            this.velocity = 0;
        }
    }

    public Rect getRectangle() {
        return this.rectangle;
    }

    /**
     * appplies upward force depending on the level
     */
    public void swimUp() {
        if(state != State.ALIVE) return;
        this.velocity -= this.lift;
        if (this.velocity < this.velocityCap) {
            this.velocity = this.velocityCap;
        }
    }

    /**
     *  puts the fish into dead state
     */
    public void die() {
        if(state != State.ALIVE) return;
        velocity = 0;
        gravity = 0.06;
        state = State.DEAD;
    }

    /**
     * checks if the fish collides with the top or bottom of the screen
     * @return
     */
    public boolean checkCollisionWithTopOrBottom() {
        return this.y + this.width >=Constants.SCREEN_HEIGHT ||
                this.y - this.width <= 0;
    }

    /**
     * updates position of the fish while swinging, before the game starts
     * @return
     */
    public void updateSwinging(){
        double time = System.currentTimeMillis();
        // the swinging is a sine as a function of time
        y = (int) (Math.sin(time * 0.005) * 50 + Constants.SCREEN_HEIGHT / 3);
        rectangle.set(x - rectangle.width() / 2, y - rectangle.height() / 2,
                x + rectangle.width() / 2, y + rectangle.height() / 2);
    }

    public State getState(){
        return state;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    public void setLook1() {
        this.imageView.setImageDrawable(look1);
    }

    public void setLook2() {
        this.imageView.setImageDrawable(look2);
    }
}
