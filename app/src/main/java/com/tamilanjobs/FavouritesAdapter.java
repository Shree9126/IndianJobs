package com.tamilanjobs;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
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

import static com.tamilanjobs.TamilanjobsAdapter.toCalendar;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by mahei on 2/3/2018.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    FavoritesActivity context;
    List<Inventory> inventories;

    int dobYear;
    int dobMonth;
    int dobDay;


    int mHour;
    int mMinute;

    Long id;

    AlarmReceiver mAlarmReceiver;

    Calendar Mycalendertype;

    String remindermename, remindmedescription;
    LinearLayout lay_nodata_found;

    public FavouritesAdapter(FavoritesActivity context, List<Inventory> inventories, LinearLayout lay_nodata_found) {
        this.context = context;
        this.inventories = inventories;
        this.lay_nodata_found = lay_nodata_found;
        Mycalendertype = Calendar.getInstance();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_favourites, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        //Log.d(TAG, "onBindViewHolder: " + inventories.get(position).getId());


        List<Inventory> reminders = new Select()
                .from(Inventory.class)
                .where("postid = ?", inventories.get(position).getPostid())
                .execute();

        try {
            if (!inventories.get(position).getIsReminder().equals("0")) {
                holder.linear_reminder_empty.setVisibility(View.VISIBLE);
                holder.linear_reminderset.setVisibility(View.GONE);
            } else {
                holder.linear_reminder_empty.setVisibility(View.GONE);
                holder.linear_reminderset.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        if (inventories.get(position).getName() != null) {
            holder.txt_favtitle.setText(inventories.get(position).getName().trim());
        }

        if (inventories.get(position).getOrganization() != null) {
            holder.txt_organization.setText(inventories.get(position).getOrganization().trim());
        }

        if (inventories.get(position).getVacancy() != null) {
            holder.txt_vacanfavcount.setText(inventories.get(position).getVacancy());
        }

//        if (inventories.get(position).getFavdate() != null) {
//            holder.txt_favdate.setText(inventories.get(position).getFavdate());
//            Log.d("dddsds", "onBindViewHolder: " + inventories.get(position).getFavdate());
//        }

        if (inventories.get(position).getFavimage() != null) {


            Picasso.with(context).load(ApiClient.URL_ACCOUNT_PHOTO + inventories.get(position).getFavimage()).
                    placeholder(R.drawable.sample_image)// Place holder image from drawable folder
                    .error(R.drawable.sample_image)
                    .into(holder.img_favimage);

        }


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String getCurrentDateTime = sdf.format(c.getTime());
        Date dateDob = null;
        Date dateDoa = null;

        try {

            if (!inventories.get(position).getFavdate().isEmpty()) {
                dateDob = sdf.parse(getCurrentDateTime);

                SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                date = form.parse(inventories.get(position).getFavdate());
                dateDoa = date;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }



        try {
            if (dateDob.after(dateDoa)) {
                holder.expiredTag.setVisibility(View.VISIBLE);
            } else {
                holder.expiredTag.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }




    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_favtitle, txt_organization, txt_vacanfavcount, txt_favdate;


        LinearLayout linear_favouriteremove,
                linear_fav,
                linear_favmovetofullview,
                linear_whatsappshare,
                linear_reminderset;

        ImageView  expiredTag, reminderIcon;

        View linear_reminder_empty;
        CircleImageView img_favimage;



        public ViewHolder(View itemView) {
            super(itemView);


            reminderIcon = itemView.findViewById(R.id.reminderIcon);
            linear_reminder_empty = itemView.findViewById(R.id.linear_reminder_empty);
            txt_favtitle = (TextView) itemView.findViewById(R.id.txt_favtitle);
            txt_organization = (TextView) itemView.findViewById(R.id.txt_organization);
            txt_vacanfavcount = (TextView) itemView.findViewById(R.id.txt_vacanfavcount);
          //  txt_favdate = (TextView) itemView.findViewById(R.id.txt_favdate);
            linear_favouriteremove = (LinearLayout) itemView.findViewById(R.id.linear_favouriteremove);
            linear_whatsappshare = (LinearLayout) itemView.findViewById(R.id.linear_whatsappshare);
            linear_reminderset = (LinearLayout) itemView.findViewById(R.id.linear_reminderset);
            //linear_fav = (LinearLayout) itemView.findViewById(R.id.linear_fav);
            linear_favmovetofullview = (LinearLayout) itemView.findViewById(R.id.linear_favmovetofullview);
            img_favimage =  itemView.findViewById(R.id.img_favimage);
            expiredTag = (ImageView) itemView.findViewById(R.id.expiredTag);


            //onclick
            linear_favouriteremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("sdsdsdsds", "onClick: " + inventories.get(getAdapterPosition()).getPostid());


                    new Delete().from(Inventory.class).where("postid = ?", inventories.get(getAdapterPosition()).getPostid()).execute();
                    DashboardActivity.adapterNotify("fefe");
                    inventories.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), inventories.size());
                    notifyDataSetChanged();

                    if(inventories.size()!=0){
                        lay_nodata_found.setVisibility(View.GONE);
                    }else {
                        lay_nodata_found.setVisibility(View.VISIBLE);
                    }

                    Toast.makeText(context, "விருப்பமானவையிலிருந்து நீக்கப்பட்டது", Toast.LENGTH_SHORT).show();

                }
            });


            linear_favmovetofullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent linear_movetofullview = new Intent(context, JobsfullviewActivity.class);
                    linear_movetofullview.putExtra("jobid", inventories.get(getAdapterPosition()).getPostid());
                    linear_movetofullview.putExtra("reminder_id", inventories.get(getAdapterPosition()).getIsReminder());
                    linear_movetofullview.putExtra("favid", "1");
                   // linear_movetofullview.putExtra("jobtypeid", inventories.get(getAdapterPosition()).getFavouritetypeid());
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
                            "*"+inventories.get(getAdapterPosition()).getName()+ "*" + "\n\n" +
                                    "*பணி இடங்கள்:*"+" "+ inventories.get(getAdapterPosition()).getVacancy() + "\n\n" +
                                    "*கடைசி தேதி:*" + " " + inventories.get(getAdapterPosition()).getFavdate() + "\n\n" +
                                    "*கூடுதல் தகவலுக்கு:*" + " " + inventories.get(getAdapterPosition()).getApplylink() + "\n\n" +
                                    "மேலும் இது போன்ற வேலை வாய்ப்பு தகவல்களை உடனுக்குடன் தெரிந்து கொள்ள *\"Tamilan jobs - Velai Vaaippu\"* செயலியை உடனே டவுன்லோட் செய்து கொள்ளுங்க..." + "\n\n" +
                                    "*Click here to download:*" + " " + "https://www.tamilanjobs.com/androidapp/" + "\n\n"+
                                    "*குறிப்பு:* இந்த தகவலை உங்கள் நண்பர்கள் அனைவருக்கும் ஷேர் பண்ணுங்க.. வேலை தேடும் நம் நண்பர்களுக்கும் கண்டிப்பாக உதவுங்கள்...");


//
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);

                }
            });

            linear_reminderset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String getCurrentDateTime = sdf.format(c.getTime());
                    Date dateDob = null;
                    Date dateDoa = null;

                    try {


                        dateDob = sdf.parse(getCurrentDateTime);
                        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = null;
                        date = form.parse(inventories.get(getAdapterPosition()).getFavdate());
                        dateDoa = date;


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dateDob.after(dateDoa)) {
                        Toast.makeText(context, "Job expired!, You could not set reminder", Toast.LENGTH_SHORT).show();
                    } else {


                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.reminderme_dialog);
                        dialog.show();


                        View img_closedialog = dialog.findViewById(R.id.img_closedialog);
                        final EditText edt_remindmedate = (EditText) dialog.findViewById(R.id.edt_remindmedate);
                        final EditText edt_remindermename = (EditText) dialog.findViewById(R.id.edt_remindermename);
                        final EditText edt_remindmedescription = (EditText) dialog.findViewById(R.id.edt_remindmedescription);
                        final EditText edt_remindmetime = (EditText) dialog.findViewById(R.id.edt_remindmetime);
                        final LinearLayout linear_remindermeupdate = (LinearLayout) dialog.findViewById(R.id.linear_remindermeupdate);


                        List<Reminder> reminders = new Select()
                                .from(Reminder.class)
                                .where("reminderpostid = ?", inventories.get(getAdapterPosition()).getPostid())
                                .execute();

                        Log.d("dsddsssds", "onClick: " + inventories.get(getAdapterPosition()).getPostid());

                        if (reminders.size() != 0) {

                            edt_remindermename.setText(inventories.get(getAdapterPosition()).getName());
                            edt_remindmedescription.setText(inventories.get(getAdapterPosition()).getOrganization());
                            edt_remindmedate.setText(reminders.get(0).getReminderdate());
                            edt_remindmetime.setText(reminders.get(0).getRemindertime());
                        } else {
                            edt_remindermename.setText(inventories.get(getAdapterPosition()).getOrganization());
                            edt_remindmedescription.setText(inventories.get(getAdapterPosition()).getOrganization());

                        }

                        img_closedialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });


                        edt_remindmedate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                setAppointment(edt_remindmedate, edt_remindmetime, getAdapterPosition());
                            }
                        });


                        linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                remindermename = edt_remindermename.getText().toString().trim();
                                remindmedescription = edt_remindmedescription.getText().toString().trim();
                                String remindmedate = edt_remindmedate.getText().toString().trim();
                                String remindmetime = edt_remindmetime.getText().toString().trim();

                                if (!remindermename.isEmpty()) {
                                    if (!remindmedescription.isEmpty()) {
                                        if (edt_remindmedate.getText().toString().trim() != null &&
                                                !edt_remindmetime.getText().toString().trim().isEmpty()) {
                                            savereminder(inventories.get(getAdapterPosition()).getPostid(), inventories.get(getAdapterPosition()).getVacancy(), inventories.get(getAdapterPosition()).getFavdate(),
                                                    remindermename, remindmedescription, remindmedate,
                                                    remindmetime, inventories.get(getAdapterPosition()).getFavimage(), dialog);
                                            notifyDataSetChanged();

                                        } else {
                                            Toast.makeText(context, "Please select the date and time", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "Please select reminder description", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(context, "Please select reminder name", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }

                }
            });

        }
    }


    //cae

    public void setAppointment(final EditText edt_remindmedate,
                               final EditText edt_remindmetime,
                               int adapterPosition) {


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                                                    String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                                                                    String month = months[monthOfYear];

                                                                    //  Toast.makeText(context, "" + year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();

                                                                    Mycalendertype.set(Calendar.YEAR, year);
                                                                    Mycalendertype.set(Calendar.MONTH, monthOfYear);
                                                                    Mycalendertype.set(Calendar.DAY_OF_MONTH, dayOfMonth);

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

        Date maximumdate = null;

        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = form.parse(inventories.get(adapterPosition).getFavdate());
            maximumdate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Date maximumdate = null;
//
//        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = null;
//        try {
//            if (inventories.get(adapterPosition).getFavouritetypeid().equals("3")) {
//                date = form.parse(inventories.get(adapterPosition).getFavdate());
//
//            } else {
//                date = form.parse(job_list.get(adapterPosition).getJpost_end_date());
//
//            }
//            maximumdate = date;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


        dpd.setMinDate(now);

        dpd.setMaxDate(toCalendar(maximumdate));


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


                        Mycalendertype.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        Mycalendertype.set(Calendar.MINUTE, minute);

                        boolean isPM = (hourOfDay >= 12);
                        edt_remindmetime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));


                        // edt_remindmetime.setText(hourOfDay + ":" + minute);
                    }

                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void savereminder(String jpostId, String vacancy, String datetime, String remindermename,
                              String remindmedescription, String remindmedate,
                              String remindmetime, String jpost_image, Dialog dialog) {
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

        setAlarm(jpostId);

        dialog.cancel();
        // getAll();

        Toast.makeText(context, "Reminder job successfully", Toast.LENGTH_LONG).show();
    }


    private void setAlarm(String jpostId) {

        if (Mycalendertype != null) {
            Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
            intent.putExtra("jobid",jpostId);
            intent.putExtra("jobtitle", remindermename);
            intent.putExtra("joborganization", remindmedescription);
            // PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RQS_1, intent, 0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(jpostId), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, Mycalendertype.getTimeInMillis(), pendingIntent);
        } else {
            Toast.makeText(context, "No alarm available", Toast.LENGTH_SHORT).show();
        }


    }
}
