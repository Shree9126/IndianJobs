package com.tamilanjobs;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import com.bumptech.glide.Glide;
import com.tamilanjobs.Helper.Utils;
import com.tamilanjobs.Response.Inventory;
import com.tamilanjobs.Response.Notificationdetail;
import com.tamilanjobs.Response.Reminder;
import com.tamilanjobs.Response.Searchjobdetail;
import com.tamilanjobs.Rest.ApiClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by mahei on 2/12/2018.
 */

public class FilterlistAdapter extends RecyclerView.Adapter<FilterlistAdapter.ViewHolder> {

    FilterlistActivity context;
    ArrayList<Searchjobdetail> searchjob_list;

    DatePickerDialog dpd;

    int dobYear;
    int dobMonth;
    int dobDay;


    int mHour;
    int mMinute;

    Calendar Mycalendertype;


    String shortitle, dialogdescription;


    String remindermename,remindmedescription;

    public FilterlistAdapter(FilterlistActivity context, ArrayList<Searchjobdetail> searchjob_list) {
        this.context = context;
        this.searchjob_list = searchjob_list;

        Mycalendertype=Calendar.getInstance();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_filterlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        //position set value
//        holder.txt_notificationtitle.setText("1");
//        holder.txt_notificationdate.setText("1");
//        holder.txt_notificationdescription.setText("1");


        if (searchjob_list.get(position).getJpost_short_title() != null) {
            holder.txt_jobtitle.setText(searchjob_list.get(position).getJpost_short_title());
        }

        if (searchjob_list.get(position).getJpost_organization() != null) {
            holder.txt_joborganization.setText(searchjob_list.get(position).getJpost_organization());
        }

        if(searchjob_list.get(position).getJpost_image()!=null){
            Glide.with(context)
                    .load(ApiClient.URL_ACCOUNT_PHOTO + searchjob_list.get(position).getJpost_image()).into(holder.image);
        }else {
            holder.image.setImageResource(R.drawable.jobsicon);
        }


        if (searchjob_list.get(position).getJpost_total_vacancy() != null) {
            holder.txt_jobvacancycount.setText(searchjob_list.get(position).getJpost_total_vacancy());
        }


        if (searchjob_list.get(position).getJpost_created_on() != null) {
            holder.txt_joblistdate.setText(searchjob_list.get(position).getJpost_created_on());
        }


    }

    @Override
    public int getItemCount() {
        return searchjob_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linear_movetofullview, linear_whatsappshare, linear_favourite, linear_reminderset;

        TextView txt_jobtitle, txt_joborganization, txt_jobvacancycount, txt_joblistdate;

        ImageView img_jobsfav, image;

        public ViewHolder(View itemView) {
            super(itemView);


            linear_whatsappshare = (LinearLayout) itemView.findViewById(R.id.linear_whatsappshare);
            linear_reminderset = (LinearLayout) itemView.findViewById(R.id.linear_reminderset);
            linear_favourite = (LinearLayout) itemView.findViewById(R.id.linear_favourite);
            img_jobsfav = (ImageView) itemView.findViewById(R.id.img_jobsfav);
            linear_movetofullview = (LinearLayout) itemView.findViewById(R.id.linear_movetofullview);
            txt_jobtitle = (TextView) itemView.findViewById(R.id.txt_jobtitle);
            txt_joborganization = (TextView) itemView.findViewById(R.id.txt_joborganization);
            txt_jobvacancycount = itemView.findViewById(R.id.txt_jobvacancycount);
            txt_joblistdate = (TextView) itemView.findViewById(R.id.txt_joblistdate);
            image = itemView.findViewById(R.id.image);


            //onclick

            linear_movetofullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent linear_movetofullview = new Intent(context, JobsfullviewActivity.class);
                    linear_movetofullview.putExtra("jobid", searchjob_list.get(getAdapterPosition()).getJpost_id());
                    linear_movetofullview.putExtra("jobtypeid", searchjob_list.get(getAdapterPosition()).getJpost_type());
                    linear_movetofullview.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(linear_movetofullview);
                }
            });


            linear_whatsappshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            searchjob_list.get(getAdapterPosition()).getJpost_short_title() + " " +
                                    searchjob_list.get(getAdapterPosition()).getJpost_total_vacancy() + "\n" +
                                    "காலி இடங்கள்"+ "\n"+
                                    "கடைசி தேதி:"+ searchjob_list.get(getAdapterPosition()).getJpost_end_date() +"\n"+
                                    "மேலும் அறிய:" + " " + searchjob_list.get(getAdapterPosition()).getJpost_apply_link() + "\n" +
                                    "இது போன்ற வேலை வாய்ப்பு தகவல்களை உடனுக்குடன் தெரிந்து கொள்ள எங்களது வேலை வாய்ப்பு செயலியை டவுன்லோட் செய்து கொள்ளவும்"+"\n"+
                                    "Download Link:" + " " + "Not available");
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);
                }
            });

            linear_favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveInventory(searchjob_list.get(getAdapterPosition()).getJpost_id(), searchjob_list.get(getAdapterPosition()).getJpost_short_title(),
                            searchjob_list.get(getAdapterPosition()).getJpost_total_vacancy(), searchjob_list.get(getAdapterPosition()).getJpost_organization(),
                            searchjob_list.get(getAdapterPosition()).getJpost_created_on(),searchjob_list.get(getAdapterPosition()).getJpost_image(),searchjob_list.get(getAdapterPosition()).getJpost_apply_link());

                }
            });

            linear_reminderset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.reminderme_dialog);
                    dialog.show();

                    //height and width custom

//                    Window dialogWindow = dialog.getWindow();
//                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                    dialogWindow.setGravity(Gravity.CENTER);
//
//                    lp.x = 100; // The new position of the X coordinates
//                    lp.y = 50; // The new position of the Y coordinates
//                    lp.width = 500; // Width
//                    lp.height = 670; // Height
//
//                    dialogWindow.setAttributes(lp);


                    ImageView img_closedialog = (ImageView) dialog.findViewById(R.id.img_closedialog);
                    final EditText edt_remindmedate = (EditText) dialog.findViewById(R.id.edt_remindmedate);
                    final EditText edt_remindermename = (EditText) dialog.findViewById(R.id.edt_remindermename);
                    final EditText edt_remindmedescription = (EditText) dialog.findViewById(R.id.edt_remindmedescription);
                    final EditText edt_remindmetime = (EditText) dialog.findViewById(R.id.edt_remindmetime);
                    final LinearLayout linear_remindermeupdate = (LinearLayout) dialog.findViewById(R.id.linear_remindermeupdate);

                    edt_remindermename.setText(searchjob_list.get(getAdapterPosition()).getJpost_short_title());
                    edt_remindmedescription.setText(searchjob_list.get(getAdapterPosition()).getJpost_organization());

                    img_closedialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });


                    edt_remindmedate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //reminddate = edt_remindmedate.getText().toString();

                            //calendarinitialization(edt_remindmedate);

                            setAppointment(edt_remindmedate, edt_remindmetime);
                        }
                    });


                    linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                             remindermename = edt_remindermename.getText().toString().trim();
                             remindmedescription = edt_remindmedescription.getText().toString().trim();
                            String remindmedate = edt_remindmedate.getText().toString().trim();
                            String remindmetime = edt_remindmetime.getText().toString().trim();

                            savereminder(searchjob_list.get(getAdapterPosition()).getJpost_id(),
                                    searchjob_list.get(getAdapterPosition()).getJpost_total_vacancy(),
                                    searchjob_list.get(getAdapterPosition()).getJpost_created_on(),
                                    remindermename, remindmedescription, remindmedate, remindmetime,
                                    searchjob_list.get(getAdapterPosition()).getJpost_image());


                            dialog.dismiss();
//                            if (remindermename.equalsIgnoreCase("")) {
//                                if (remindmedescription.equalsIgnoreCase("")) {
//                                    if (remindmedate.equalsIgnoreCase("")) {
//                                        if (remindmetime.equalsIgnoreCase("")) {
//
//
//                                            savereminder(job_list.get(getAdapterPosition()).getJpost_id(), remindermename, remindmedescription, remindmedate, remindmetime);
//
//                                        } else {
//                                            Toast.makeText(dashboardActivity, "Please select time", Toast.LENGTH_LONG).show();
//
//                                        }
//                                    } else {
//                                        Toast.makeText(dashboardActivity, "Please select date", Toast.LENGTH_LONG).show();
//
//                                    }
//                                } else {
//                                    Toast.makeText(dashboardActivity, "Please enter description", Toast.LENGTH_LONG).show();
//
//                                }
//
//                            } else {
//                                Toast.makeText(dashboardActivity, "Please enter reminder name", Toast.LENGTH_LONG).show();
//
//                            }


                        }
                    });

                }
            });


        }
    }


    //cae

    public void setAppointment(final EditText edt_remindmedate, final EditText edt_remindmetime) {


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                                                    String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                                                                    String month = months[monthOfYear];


                                                                    Mycalendertype.set(Calendar.YEAR,year);
                                                                    Mycalendertype.set(Calendar.MONTH,monthOfYear);
                                                                    Mycalendertype.set(Calendar.DAY_OF_MONTH,dayOfMonth);


                                                                    edt_remindmedate.setText(dayOfMonth + "-" + month + "-" + year);


                                                                    tiemPicker(edt_remindmetime);

                                                                    dobYear = year;
                                                                    dobMonth = monthOfYear;
                                                                    dobDay = dayOfMonth;
                                                                }
                                                            },
                now.get(YEAR),
                now.get(MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(context.getFragmentManager(), "Datepickerdialog");


    }

    private void tiemPicker(final EditText edt_remindmetime) {


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


                        // edt_remindmetime.setText(hourOfDay + ":" + minute);
                    }

                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void saveInventory(String jpost_id, String short_title, String jpost_total_vacancy, String jpost_organization, String jpost_createddate, String jpost_image, String jpost_apply_link) {
        //Getting name from editText
        //name = editTextInventoryName.getText().toString().trim();

        //Checking if name is blank
//        if (name.equalsIgnoreCase("")) {
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//            return;
//        }

        //If name is not blank creating a new Inventory object

        new Delete().from(Inventory.class).where("postid = ?", jpost_id).execute();

        Inventory inventory = new Inventory();
        //Adding the given name to inventory name
        inventory.postid = jpost_id;
        inventory.name = short_title;
        inventory.vacancy = jpost_total_vacancy;
        inventory.organization = jpost_organization;
        inventory.favdate = jpost_createddate;
        inventory.favimage = jpost_image;
        inventory.applylink = jpost_apply_link;
        //Saving name to sqlite database
        inventory.save();
        //  inventoryItems.add(jpost_id);


        // getAll();

        Toast.makeText(context, "Favourite your job successfully", Toast.LENGTH_LONG).show();
    }


    private List<Inventory> getAll() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Inventory.class)
                .orderBy("postid ASC")
                .execute();
    }


    private void savereminder(String jpostId, String vacancy, String datetime, String remindermename, String remindmedescription, String remindmedate, String remindmetime, String jpost_image) {
        //Getting name from editText
        //name = editTextInventoryName.getText().toString().trim();

        //Checking if name is blank
//        if (name.equalsIgnoreCase("")) {
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//            return;
//        }

        //If name is not blank creating a new Inventory object

        new Delete().from(Reminder.class).where("reminderpostid = ?", jpostId).execute();

        Reminder reminder = new Reminder();
        //Adding the given name to inventory name
        reminder.reminderpostid = jpostId;
        reminder.remindertitle = remindermename;
        reminder.vacancy = vacancy;
        reminder.reminderdesc = remindmedescription;
        reminder.reminderdatetime = datetime;
        reminder.reminderdate = remindmedate;
        reminder.remindertime = remindmetime;
        reminder.reminderimage = jpost_image;
        //Saving name to sqlite database
        reminder.save();
        //  inventoryItems.add(jpost_id);

        setAlarm();

        // getAll();

        Toast.makeText(context, "Reminder job successfully", Toast.LENGTH_LONG).show();

    }

    //setalarm

    private void setAlarm() {



        if(Mycalendertype!=null){
            Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
            intent.putExtra("jobtitle",remindermename);
            intent.putExtra("joborganization",remindmedescription);
            // PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RQS_1, intent, 0);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, Mycalendertype.getTimeInMillis(), pendingIntent);
        }else {
            Toast.makeText(context, "No alarm available", Toast.LENGTH_SHORT).show();
        }


    }
}
