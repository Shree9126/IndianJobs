package com.tamilanjobs;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.activeandroid.query.Select;
import com.tamilanjobs.Response.Inventory;
import com.tamilanjobs.Response.Reminder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class ReminderActivity extends AppCompatActivity {

    RecyclerView recycle_remainder;
    LinearLayout lay_nodata_found;
    ReminderAdapter reminderAdapter;




     int mReceivedID;
    public static final String EXTRA_REMINDER_ID = "Reminder_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


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
        recycle_remainder = (RecyclerView) findViewById(R.id.recycle_remainder);

//        edt_reminderdate = (EditText) findViewById(R.id.edt_reminderdate);
//        edt_time = (EditText) findViewById(R.id.edt_time);

        recycle_remainder.setLayoutManager(new LinearLayoutManager(this));


//        if(getIntent().getStringExtra(EXTRA_REMINDER_ID)!=null){
//            mReceivedID = Integer.parseInt(getIntent().getStringExtra(EXTRA_REMINDER_ID));
//        }else {
//            Toast.makeText(this, "No alarm available", Toast.LENGTH_SHORT).show();
//        }


        showInventoryList();
    }


    private List<Reminder> getAll() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Reminder.class)
                .execute();
    }

    private void showInventoryList() {
        //Creating a list and getting all inventories from the method
        List<Reminder> reminders = getAll();

        Log.d("dsdsd", "showInventoryList: " + reminders.size());



        if(reminders.size()!=0){
            lay_nodata_found.setVisibility(View.GONE);
            reminderAdapter = new ReminderAdapter(ReminderActivity.this, reminders,lay_nodata_found);
            recycle_remainder.setAdapter(reminderAdapter);
        }else {
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
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
