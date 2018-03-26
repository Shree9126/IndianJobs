package com.tamilanjobs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.activeandroid.query.Select;
import com.tamilanjobs.Response.Inventory;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recycle_favourites;
    LinearLayout lay_nodata_found;
    FavouritesAdapter favouritesAdapter;
    List<Inventory> inventories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        UIinitialization();

    }

    private void UIinitialization() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.toolbar_backarrowicon);

        toolbar.setTitleTextColor(Color.WHITE);


        lay_nodata_found = (LinearLayout) findViewById(R.id.lay_nodata_found);
        recycle_favourites = (RecyclerView) findViewById(R.id.recycle_favourites);

        recycle_favourites.setLayoutManager(new LinearLayoutManager(this));


        //showInventoryList();


    }


    private List<Inventory> getAll() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Inventory.class)
                .execute();
    }

    private void showInventoryList() {
        //Creating a list and getting all inventories from the method

        inventories = new ArrayList<>();

        inventories.clear();
        getAll().clear();
        inventories = getAll();

        Log.d("dsdsd", "showInventoryList: " + inventories.size());


        if (inventories.size() != 0) {
            lay_nodata_found.setVisibility(View.GONE);
            favouritesAdapter = new FavouritesAdapter(FavoritesActivity.this, inventories,lay_nodata_found);
            recycle_favourites.setAdapter(favouritesAdapter);

        } else {
            favouritesAdapter = new FavouritesAdapter(FavoritesActivity.this, inventories,lay_nodata_found);
            recycle_favourites.setAdapter(favouritesAdapter);
            lay_nodata_found.setVisibility(View.VISIBLE);
        }


        //Updating the inventory list
        // updateInventoryList();
    }

    //onbackpress

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onResume() {
        showInventoryList();
        super.onResume();
    }


}
