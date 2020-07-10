package com.example.flyingfish.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.db.dataObject.Item;
import com.example.flyingfish.db.dataObject.Level;
import com.example.flyingfish.db.dataObject.User;
import com.example.flyingfish.db.dataObject.management.DataObjectManager;
import com.example.flyingfish.db.dataObject.management.Observer;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements Observer {


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        //listen for changes
        DataObjectManager.getInstance().addObserver(this);


        //initialize with the current values the manager has
        updateCoins();
        fillShopList(getShopList());
    }

    private List<Item> getShopList() {
        ArrayList<Item> newList = new ArrayList<>();

        List<Item> allItems = DataObjectManager.getInstance().getItemList();
        List<Item> userItems = DataObjectManager.getInstance().getItemListOfUser(Constants.CURRENT_USERNAME);

        for(int i = 0; i < allItems.size(); i++) {
            Item item = allItems.get(i);
            newList.add(new Item(item.getName(), item.getPrice(), false, false));
        }

        for(int i = 0; i < newList.size(); i++) {
            Item item = newList.get(i);
            if(userItems != null){
                for(int j = 0; j < userItems.size(); j++){
                    Item userItem = userItems.get(j);
                    if(item.getName().equals(userItem.getName())){
                        item.setBought(true);
                        item.setEquiped(userItem.isEquiped());
                    }
                }
            }
        }

        DataObjectManager.bubbleSort(newList, true);

        return newList;
    }

    private void updateCoins() {
        int coins = DataObjectManager.getInstance().getUserCurrentCoins(Constants.CURRENT_USERNAME);
        TextView cV = findViewById(R.id.userCoinCount);
        cV.setText(Integer.toString(coins));
    }


    public void backToMainMenu(View v) {
        DataObjectManager.getInstance().removeObserver(this);
        startActivity(new Intent(this, MainMenuActivity.class));
    }


    private void fillShopList(List<Item> allItems) {
        LinearLayout shopList = findViewById(R.id.shopList);
        shopList.removeAllViews();

        for(final Item i: allItems) {
            final LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            shopList.addView(row);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            if(i.isEquiped()) {
                row.setBackgroundColor(Color.rgb(255,170,0));
            }

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataObjectManager.getInstance().buyItemAndEquip(Constants.CURRENT_USERNAME, i.getName());
                }
            });

            ImageView itemImg = new ImageView(this);
            try{
                int resourceId = getResources().getIdentifier(i.getName().toLowerCase().replace(" ","_")+"_hr", "drawable", getPackageName());
                Drawable img = getResources().getDrawable(resourceId);

                Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100,100, true));


                itemImg.setImageDrawable(d);
            }catch(Exception e){
                itemImg.setImageDrawable(getResources().getDrawable(R.mipmap.notfoundimageicon));
            }

            /* ITEM NAME */
            TextView itemnameTv = new TextView(this);
            itemnameTv.setShadowLayer(5,2,2,Color.BLACK);
            itemnameTv.setText(i.getName());
            itemnameTv.setTextColor(Color.rgb(255,255,255));
            itemnameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            LinearLayout.LayoutParams llpName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llpName.setMargins(10,0,0,0);
            itemnameTv.setLayoutParams(llpName);

            row.addView(itemImg);
            row.addView(itemnameTv);

            if(i.getPrice() > 0 && !i.isBought()) {
                /* PRICE */
                TextView itemPrice  = new TextView(this);
                itemPrice.setText(Integer.toString(i.getPrice()));
                itemPrice.setTextColor(Color.rgb(255, 193, 7));
                itemPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                itemPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                LinearLayout.LayoutParams llpPrice = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llpPrice.setMargins(20,0,10,0);
                itemPrice.setLayoutParams(llpPrice);

                ImageView coinsIcon = new ImageView(this);
                Drawable coinsIconDrawable = getResources().getDrawable(R.drawable.coins_hr);
                Bitmap bitmap = ((BitmapDrawable) coinsIconDrawable).getBitmap();
                Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25,20, true));

                coinsIcon.setImageDrawable(d);
                row.addView(itemPrice);
                row.addView(coinsIcon);
            }
        }
    }

    @Override
    public void updateUsers(ArrayList<User> users) {
        updateCoins();
    }

    @Override
    public void updateItems(ArrayList<Item> items) {
        fillShopList(getShopList());
    }

    @Override
    public void updateLevel(ArrayList<Level> levels) {

    }
}
