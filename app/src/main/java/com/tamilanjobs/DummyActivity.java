package com.tamilanjobs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tamilanjobs.Response.Inventory;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DummyActivity extends AppCompatActivity {

    private EditText editTextInventoryName;
    private Button buttonSaveInventory;
    private RecyclerView listViewInventories;

    //Array list for storing all the inventoryItems
     ArrayList<String> inventoryItems;

    //Adapter for listview
   // private ArrayAdapter inventoryItemsAdapter;
    private DummyAdapter dummyAdapter;

    TextView txt_delete;

    private AlarmManagerBroadcastReceiver alarm;


    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;



    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        textAlarmPrompt = (TextView) findViewById(R.id.alarmprompt);

        buttonstartSetDialog = (Button) findViewById(R.id.startAlaram);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);

            }
        });

    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();

    }

    android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener = new android.app.TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);
        }
    };

    private void setAlarm(Calendar targetCal) {

        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmManagerBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

    }





}
