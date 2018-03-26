package com.tamilanjobs;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tamilanjobs.Response.Inventory;
import com.tamilanjobs.Response.Reminder;
import com.tamilanjobs.Rest.ApiClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tamilanjobs.DummyActivity.RQS_1;
import static com.tamilanjobs.TamilanjobsAdapter.toCalendar;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by mahei on 2/3/2018.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    ReminderActivity context;


    DatePickerDialog dpd;
    List<Reminder> reminders;
    int dobYear;
    int dobMonth;
    int dobDay;


    int mHour;
    int mMinute;

    Long id;

     AlarmReceiver mAlarmReceiver;

    Calendar Mycalendertype;

    String remindermename,remindmedescription;
    private int mReceivedID;
    LinearLayout lay_nodata_found;

    public ReminderAdapter(ReminderActivity context, List<Reminder> reminders, LinearLayout lay_nodata_found) {
        this.context = context;
        this.reminders = reminders;
        this.lay_nodata_found = lay_nodata_found;
        mAlarmReceiver = new AlarmReceiver();

        Mycalendertype=Calendar.getInstance();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_reminder, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (reminders.get(position).getRemindertitle() != null) {
            holder.txt_remindertitle.setText(reminders.get(position).getRemindertitle());
        }

        if (reminders.get(position).getReminderdesc() != null) {
            holder.txt_reminderorganization.setText(reminders.get(position).getReminderdesc());
        }

        if (reminders.get(position).getReminderdate() != null) {
            holder.txt_reminderdate.setText(reminders.get(position).getReminderdate());
        }

        if (reminders.get(position).getRemindertime() != null) {
            holder.txt_remindertime.setText(reminders.get(position).getRemindertime());
        }

        if (reminders.get(position).getReminderdatetime() != null) {
            holder.txt_reminderlistdate.setText(reminders.get(position).getReminderdatetime());

        }

        if (reminders.get(position).getVacancy() != null) {
            holder.txt_remindervacancy.setText(reminders.get(position).getVacancy());
        }



            Picasso.with(context).load(ApiClient.URL_ACCOUNT_PHOTO + reminders.get(position).getReminderimage()).
                    placeholder(R.drawable.sample_image)// Place holder image from drawable folder
                    .error(R.drawable.sample_image)
                    .into(holder.reminderimage);



        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String getCurrentDateTime = sdf.format(c.getTime());

//        if (getCurrentDateTime.compareTo(reminders.get(position).getReminderdate()) < 0)
//        {
//            holder.expiredTag.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            holder.expiredTag.setVisibility(View.GONE);
//        }



    }



    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_remindertitle, txt_reminderorganization, txt_reminderlistdate, txt_reminderdate,
                txt_remindertime, txt_remindervacancy;

        ImageView img_reminderedit, img_reminderdelete,expiredTag;
        CircleImageView reminderimage;


        LinearLayout linear_reminddelete,linear_remindedit;

        public ViewHolder(View itemView) {
            super(itemView);

            reminderimage =  itemView.findViewById(R.id.reminderimage);
            img_reminderedit = (ImageView) itemView.findViewById(R.id.img_reminderedit);
            txt_remindertitle = (TextView) itemView.findViewById(R.id.txt_remindertitle);
            txt_reminderorganization = (TextView) itemView.findViewById(R.id.txt_reminderorganization);
            txt_reminderlistdate = (TextView) itemView.findViewById(R.id.txt_reminderlistdate);
            img_reminderdelete = (ImageView) itemView.findViewById(R.id.img_reminderdelete);
            linear_reminddelete =  itemView.findViewById(R.id.linear_reminddelete);
            linear_remindedit =  itemView.findViewById(R.id.linear_remindedit);
            txt_reminderdate = (TextView) itemView.findViewById(R.id.txt_reminderdate);
            txt_remindertime = (TextView) itemView.findViewById(R.id.txt_remindertime);
            txt_remindervacancy = (TextView) itemView.findViewById(R.id.txt_remindervacancy);
            expiredTag = (ImageView) itemView.findViewById(R.id.expiredTag);

            //onclick



//            mReceivedID = Integer.parseInt(reminders.get(getAdapterPosition()).getReminderpostid());

            linear_reminddelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                    alertDialog.setTitle("இந்த நினைவூட்டலை நீக்க விரும்புகிறீர்களா");

                    // Setting Dialog Message
                    //alertDialog.setMessage("Are you sure you want delete this?");

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("ஆம்", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            // Write your code here to invoke YES event

                            DashboardActivity.adapterNotify("sdsdsd");

                            saveInventory(reminders.get(getAdapterPosition()).getReminderpostid(),
                                    reminders.get(getAdapterPosition()).getRemindertitle(),
                                    reminders.get(getAdapterPosition()).getVacancy(),
                                    reminders.get(getAdapterPosition()).getReminderdesc(),
                                    reminders.get(getAdapterPosition()).getReminderdate(),
                                    reminders.get(getAdapterPosition()).getReminderimage()," ","0");

                          //  Toast.makeText(context, "Reminder delete successfully", Toast.LENGTH_SHORT).show();
                            new Delete().from(Reminder.class).where("reminderpostid = ?", reminders.get(getAdapterPosition()).getReminderpostid()).execute();
                            reminders.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), reminders.size());
                            notifyDataSetChanged();


                            if(reminders.size()!=0){
                                lay_nodata_found.setVisibility(View.GONE);
                            }else {
                                lay_nodata_found.setVisibility(View.VISIBLE);
                            }





                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("இல்லை", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                          //  Toast.makeText(context, "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();



                }
            });


            linear_remindedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.reminderme_dialog);
                    dialog.show();


                    View img_closedialog = (View) dialog.findViewById(R.id.img_closedialog);
                    final EditText edt_remindmedate = (EditText) dialog.findViewById(R.id.edt_remindmedate);
                    final EditText edt_remindermename = (EditText) dialog.findViewById(R.id.edt_remindermename);
                    final LinearLayout linear_dialogreminddate = (LinearLayout) dialog.findViewById(R.id.linear_dialogreminddate);
                    final EditText edt_remindmedescription = (EditText) dialog.findViewById(R.id.edt_remindmedescription);
                    final EditText edt_remindmetime = (EditText) dialog.findViewById(R.id.edt_remindmetime);
                    final LinearLayout linear_remindermeupdate = (LinearLayout) dialog.findViewById(R.id.linear_remindermeupdate);

                    edt_remindermename.setText(reminders.get(getAdapterPosition()).getRemindertitle());
                    edt_remindmedescription.setText(reminders.get(getAdapterPosition()).getReminderdesc());
                    edt_remindmedate.setText(reminders.get(getAdapterPosition()).getReminderdate());
                    edt_remindmetime.setText(reminders.get(getAdapterPosition()).getRemindertime());


                    img_closedialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });


                    linear_dialogreminddate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            setAppointment(edt_remindmedate,
                                    edt_remindmetime,
                                    getAdapterPosition(),
                                    reminders);
                        }
                    });


                    linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                             remindermename = edt_remindermename.getText().toString().trim();
                             remindmedescription = edt_remindmedescription.getText().toString().trim();
                            String remindmedate = edt_remindmedate.getText().toString().trim();
                            String remindmetime = edt_remindmedate.getText().toString().trim();


                           // Reminder reminder = Select.from(Reminder.class).where("reminderpostid = ?", reminders.get(getAdapterPosition()).getReminderpostid()).executeSingle();

                            String updateSet="remindertitle = ?, reminderdesc = ?, reminderdate = ?,remindertime= ?";
                            new Update(Reminder.class)
                                    .set(updateSet,edt_remindermename.getText().toString().trim(),
                                            edt_remindmedescription.getText().toString().trim(),
                                            edt_remindmedate.getText().toString().trim(),
                                            edt_remindmetime.getText().toString().trim())
                                    .where("reminderpostid = ?", reminders.get(getAdapterPosition()).getReminderpostid())
                                    .execute();


                            reminders.get(getAdapterPosition()).setRemindertitle(edt_remindermename.getText().toString());
                            reminders.get(getAdapterPosition()).setReminderdesc(edt_remindmedescription.getText().toString());


                            setAlarm(reminders.get(getAdapterPosition()).getReminderpostid());

                            notifyItemChanged(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), reminders.size());
                            notifyDataSetChanged();
                            dialog.cancel();

                        }
                    });


                }
            });


        }
    }

    private void saveInventory(String jpost_id,
                               String short_title,
                               String jpost_total_vacancy,
                               String jpost_organization,
                               String jpost_createddate,
                               String jpost_image,
                               String applylink,
                               String isRemind) {


        new Delete().from(Inventory.class).where("postid = ?", jpost_id).execute();

        Inventory inventory = new Inventory();
        //Adding the given name to inventory name
        inventory.postid = jpost_id;
        inventory.name = short_title;
        inventory.vacancy = jpost_total_vacancy;
        inventory.organization = jpost_organization;
        inventory.favdate = jpost_createddate;
        inventory.favimage = jpost_image;
        inventory.applylink = applylink;
        inventory.isReminder = isRemind;
        //Saving name to sqlite database
        inventory.save();



    }








    //cae

    public void setAppointment(final EditText edt_remindmedate,
                               final EditText edt_remindmetime,
                               final int adapterPosition,
                               final List<Reminder> reminders) {


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                                                    String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                                                                    String month = months[monthOfYear];

                                                                  //  Toast.makeText(context, "" + year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();

                                                                    Mycalendertype.set(Calendar.YEAR,year);
                                                                    Mycalendertype.set(Calendar.MONTH,monthOfYear);
                                                                    Mycalendertype.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                                                                    edt_remindmedate.setText(dayOfMonth + "-" + month + "-" + year);

                                                                    reminders.get(adapterPosition).setReminderdate(dayOfMonth + "-" + month + "-" + year);
                                                                    tiemPicker(edt_remindmetime,adapterPosition,reminders);

                                                                    dobYear = year;
                                                                    dobMonth = monthOfYear;
                                                                    dobDay = dayOfMonth;
                                                                }
                                                            },
                now.get(YEAR),
                now.get(MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

//        Date maximumdate = null;
//
//        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = null;
//        try {
//            date = form.parse(reminders.get(adapterPosition).getReminderdate());
//            maximumdate = date;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Date maximumdate = null;

        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = form.parse(reminders.get(adapterPosition).getReminderdatetime());
            maximumdate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dpd.setMinDate(now);

        dpd.setMaxDate(toCalendar(maximumdate));


        dpd.show(context.getFragmentManager(), "Datepickerdialog");


    }

    private void tiemPicker(final EditText edt_remindmetime,
                            final int adapterPosition,
                            List<Reminder> reminders) {


        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;


                        Mycalendertype.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        Mycalendertype.set(Calendar.MINUTE,minute);

                        boolean isPM = (hourOfDay >= 12);
                        edt_remindmetime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                        ReminderAdapter.this.reminders.get(adapterPosition).setRemindertime(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                        notifyDataSetChanged();
                        // edt_remindmetime.setText(hourOfDay + ":" + minute);
                    }

                }, mHour, mMinute, false);
        timePickerDialog.show();
    }



    private void setAlarm(String reminderpostid) {



        if(Mycalendertype!=null){
            Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
            intent.putExtra("jobtitle",remindermename);
            intent.putExtra("joborganization",remindmedescription);
            // PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RQS_1, intent, 0);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context, Integer.parseInt(reminderpostid),intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, Mycalendertype.getTimeInMillis(), pendingIntent);
        }else {
            Toast.makeText(context, "No alarm available", Toast.LENGTH_SHORT).show();
        }


    }

}
