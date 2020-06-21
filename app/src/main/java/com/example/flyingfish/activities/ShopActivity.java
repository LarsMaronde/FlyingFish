package com.example.flyingfish.activities;

import android.content.Intent;
import android.content.res.Resources;
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
import com.example.flyingfish.dataObject.Item;
import com.example.flyingfish.db.DatabaseManager;

import java.util.LinkedList;

public class ShopActivity extends AppCompatActivity {


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        updateCoins();
        fillShopList();
    }

    private void updateCoins() {
         int currentCoins = DatabaseManager.getInstance().getCurrentUserCoins(Constants.CURRENT_USERNAME);
         TextView cV = findViewById(R.id.userCoinCount);
         cV.setText(Integer.toString(currentCoins));
    }

    private void fillShopList() {
        LinearLayout shopList = findViewById(R.id.shopList);
        shopList.removeAllViews();
        LinkedList<Item> allItems = DatabaseManager.getInstance().getAllItems(Constants.CURRENT_USERNAME);

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
//                    System.out.println(i.getName());
                    if(DatabaseManager.getInstance().buyItemAndEquip(Constants.CURRENT_USERNAME, i.getName())) {
                        updateCoins();
                        fillShopList();
                    }
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

    public void backToMainMenu(View v) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
