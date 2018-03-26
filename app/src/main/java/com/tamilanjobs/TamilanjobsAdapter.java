package com.tamilanjobs;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.tamilanjobs.Helper.AppController;
import com.tamilanjobs.Helper.Pref;
import com.tamilanjobs.Response.Inventory;
import com.tamilanjobs.Response.Joblistdetail;
import com.tamilanjobs.Response.Reminder;
import com.tamilanjobs.Rest.ApiClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by mahei on 2/3/2018.
 */

public class TamilanjobsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<Joblistdetail> job_list;
    DashboardActivity dashboardActivity;
    DatePickerDialog dpd;
    int dobYear;
    int dobMonth;
    int dobDay;
    int mHour;
    int mMinute;
    Calendar Mycalendertype;
    String remindermename, remindmedescription;

    String mActive;
    String mRepeat;
    private LayoutInflater mLayoutInflater;
    private static final int DEFAULT_VIEW_TYPE = 1;
    private static final int NATIVE_AD_VIEW_TYPE = 2;




    public TamilanjobsAdapter(DashboardActivity dashboardActivity, ArrayList<Joblistdetail> job_list) {
        this.dashboardActivity = dashboardActivity;
        this.job_list = job_list;
        Mycalendertype = Calendar.getInstance();
        getAll();
        mActive = "true";
        mRepeat = "true";


        mLayoutInflater = (LayoutInflater) dashboardActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getItemViewType(int position) {

        if (position != 0 && position % 6 == 0)
            return NATIVE_AD_VIEW_TYPE;
        return DEFAULT_VIEW_TYPE;

//        if(position!=0 && position%4 == 0)
//            return NATIVE_AD_VIEW_TYPE;
//        return DEFAULT_VIEW_TYPE;

    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(dashboardActivity)
//                .inflate(R.layout.list_item_tamilanjobs, parent, false);
//        return new ViewHolder(itemView);
//    }


    public static int getposition(int position) {

        Log.d(TAG, "getposition: " + position % 6);
        Log.d(TAG, "getposition:sdsdsds " + position / 6);
        position = position - (position / 6);
        return position;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        switch (viewType) {
            default:
                v = mLayoutInflater.inflate(R.layout.list_item_tamilanjobs, viewGroup, false);
                return new MyViewHolder(v);
            case NATIVE_AD_VIEW_TYPE:
                v = mLayoutInflater.inflate(R.layout.dummyad, viewGroup, false);
                return new NativeAdViewHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        if (!(holder instanceof MyViewHolder)) {
            return;
        }
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        try {


            //position set value

            List<Inventory> inventory = new Select()
                    .from(Inventory.class)
                    .where("postid = ?", job_list.get(getposition(position)).getJpost_id())
                    .execute();


            List<Reminder> reminders = new Select()
                    .from(Reminder.class)
                    .where("reminderpostid = ?", job_list.get(getposition(position)).getJpost_id())
                    .execute();

            if (inventory.size() > 0) {

                job_list.get(getposition(position)).setIsFav("1");
                myViewHolder.linear_unfavourite.setVisibility(View.VISIBLE);
                myViewHolder.linear_favourite.setVisibility(View.GONE);

            } else {
                job_list.get(getposition(position)).setIsFav("0");
                myViewHolder.linear_unfavourite.setVisibility(View.GONE);
                myViewHolder.linear_favourite.setVisibility(View.VISIBLE);
            }


            if (reminders.size() > 0) {
                job_list.get(getposition(position)).setIsRemind("1");
                myViewHolder.img_snooze_reminder.setImageResource(R.drawable.snooze_icon);
                myViewHolder.img_snooze_reminder.setColorFilter(dashboardActivity.getResources().getColor(R.color.thickgreencolorcode));
            } else {
                job_list.get(getposition(position)).setIsRemind("0");
                myViewHolder.img_snooze_reminder.setImageResource(R.drawable.snooze_icon);
            }


            if (job_list.get(getposition(position)).getJpost_title() != null) {

                Spanned shorttitle = Html.fromHtml(job_list.get(getposition(position)).getJpost_title().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));

                myViewHolder.txt_jobtitle.setText(shorttitle.toString().trim());
            }

            if (job_list.get(getposition(position)).getJpost_organization() != null) {
                Spanned organization = Html.fromHtml(job_list.get(getposition(position)).getJpost_organization().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));


                myViewHolder.txt_joborganization.setText(organization.toString().trim());
            }

            if (job_list.get(getposition(position)).getJpost_image() != null) {

                Log.v("efhefhehfhefueufie",ApiClient.URL_ACCOUNT_PHOTO + job_list.get(getposition(position)).getJpost_image());


                Picasso.with(dashboardActivity).load(ApiClient.URL_ACCOUNT_PHOTO + job_list.get(getposition(position)).getJpost_image()).
                        placeholder(R.drawable.sample_image)// Place holder image from drawable folder
                        .error(R.drawable.sample_image)
                        .into(myViewHolder.image);


            } else {
                myViewHolder.image.setImageResource(R.drawable.jobsicon);
            }


            if (job_list.get(getposition(position)).getJpost_total_vacancy() != null) {

                myViewHolder.txt_jobvacancycount.setText(job_list.get(getposition(position)).getJpost_total_vacancy());
            } else {
                myViewHolder.txt_jobvacancycount.setText("Various");

            }


//            if (job_list.get(getposition(position)).getJpost_type().equals("1")) {
//                myViewHolder.txt_joblistdate.setText(job_list.get(getposition(position)).getJpost_end_date());
//            } else if (job_list.get(getposition(position)).getJpost_type().equals("2")) {
//                myViewHolder.txt_joblistdate.setText(job_list.get(getposition(position)).getJpost_end_date());
//            } else {
//                myViewHolder.txt_joblistdate.setText(job_list.get(getposition(position)).getJpost_walkin_date());
//            }


            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String getCurrentDateTime = sdf.format(c.getTime());
            Date dateDob = null;
            Date dateDoa = null;

            try {

                if (job_list.get(getposition(position)).getJpost_type().equals("1")) {
                    dateDob = sdf.parse(getCurrentDateTime);

                    SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    date = form.parse(job_list.get(getposition(position)).getJpost_end_date());
                    dateDoa = date;

                } else if (job_list.get(getposition(position)).getJpost_type().equals("2")) {
                    dateDob = sdf.parse(getCurrentDateTime);

                    SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    date = form.parse(job_list.get(getposition(position)).getJpost_end_date());
                    dateDoa = date;

                } else {
                    dateDob = sdf.parse(getCurrentDateTime);

                    SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    date = form.parse(job_list.get(getposition(position)).getJpost_walkin_date());
                    dateDoa = date;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (dateDob.after(dateDoa)) {
                myViewHolder.expiredTag.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.expiredTag.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        // return job_list.size();
        return job_list.size() + (job_list.size() / 6);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        LinearLayout linear_whatsappshare, linear_favourite, linear_reminderset, linear_unfavourite;
        CardView linear_movetofullview;

        TextView txt_jobtitle, txt_joborganization, txt_jobvacancycount, txt_joblistdate;

        ImageView img_jobsfav, img_snooze_reminder, img_jobsunfav, expiredTag;

        CircleImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            linear_whatsappshare = (LinearLayout) itemView.findViewById(R.id.linear_whatsappshare);
            linear_reminderset = (LinearLayout) itemView.findViewById(R.id.linear_reminderset);
            img_snooze_reminder = (ImageView) itemView.findViewById(R.id.img_snooze_reminder);

            linear_favourite = (LinearLayout) itemView.findViewById(R.id.linear_favourite);
            linear_unfavourite = (LinearLayout) itemView.findViewById(R.id.linear_unfavourite);

            img_jobsfav = (ImageView) itemView.findViewById(R.id.img_jobsfav);
            img_jobsunfav = (ImageView) itemView.findViewById(R.id.img_jobsunfav);
            expiredTag = (ImageView) itemView.findViewById(R.id.expiredTag);
            linear_movetofullview = itemView.findViewById(R.id.linear_movetofullview);
            txt_jobtitle = (TextView) itemView.findViewById(R.id.txt_jobtitle);
            txt_joborganization = (TextView) itemView.findViewById(R.id.txt_joborganization);
            txt_jobvacancycount = itemView.findViewById(R.id.txt_jobvacancycount);
           // txt_joblistdate = (TextView) itemView.findViewById(R.id.txt_joblistdate);
            image = itemView.findViewById(R.id.image);


            //onclick

            linear_movetofullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.d("dsdsdsdsdsefefeff", "onClick: " + Pref.getAdCount());

                    Log.d("dsdsdsdsds", "onClick: " + job_list.get(getposition(getAdapterPosition())).getIsFav());

                    Intent linear_movetofullview = new Intent(dashboardActivity, JobsfullviewActivity.class);
                    linear_movetofullview.putExtra("favid", job_list.get(getposition(getAdapterPosition())).getIsFav());
                    linear_movetofullview.putExtra("reminder_id", job_list.get(getposition(getAdapterPosition())).getIsRemind());
                    linear_movetofullview.putExtra("jobid", job_list.get(getposition(getAdapterPosition())).getJpost_id());
                    //linear_movetofullview.putExtra("jobtypeid", job_list.get(getposition(getAdapterPosition())).getJpost_type());
                    linear_movetofullview.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    dashboardActivity.startActivity(linear_movetofullview);
                }
            });


            linear_whatsappshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (job_list.get(getposition(getAdapterPosition())).getJpost_type().equals("1")) {

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "*"+job_list.get(getposition(getAdapterPosition())).getJpost_short_title() + "*" + "\n\n"
                                       +"*பணி இடங்கள்:*"+" "+
                                        job_list.get(getposition(getAdapterPosition())).getJpost_total_vacancy() + "\n\n" +
                                        "*கடைசி தேதி:*" + " "+ job_list.get(getposition(getAdapterPosition())).getJpost_end_date() + "\n\n" +
                                        "*கூடுதல் தகவலுக்கு:*" + " " + job_list.get(getposition(getAdapterPosition())).getJpost_more_detail_link() + "\n\n" +
                                        "மேலும் இது போன்ற வேலை வாய்ப்பு தகவல்களை உடனுக்குடன் தெரிந்து கொள்ள *\"Tamilan jobs - Velai Vaaippu\"* செயலியை உடனே டவுன்லோட் செய்து கொள்ளுங்க..." + "\n" +
                                        "*Click here to download:*" + " " + "https://www.tamilanjobs.com/androidapp/" + "\n\n"+
                                         "*குறிப்பு:* இந்த தகவலை உங்கள் நண்பர்கள் அனைவருக்கும் ஷேர் பண்ணுங்க.. வேலை தேடும் நம் நண்பர்களுக்கும் கண்டிப்பாக உதவுங்கள்...");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        dashboardActivity.startActivity(sendIntent);
                    } else if (job_list.get(getposition(getAdapterPosition())).getJpost_type().equals("2")) {

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "*"+job_list.get(getposition(getAdapterPosition())).getJpost_short_title() + "*" + "\n\n"
                                        +"*பணி இடங்கள்:*"+" "+
                                        job_list.get(getposition(getAdapterPosition())).getJpost_total_vacancy() + "\n\n" +
                                        "*கடைசி தேதி:*" + " "+ job_list.get(getposition(getAdapterPosition())).getJpost_end_date() + "\n\n" +
                                        "*கூடுதல் தகவலுக்கு:*" + " " + job_list.get(getposition(getAdapterPosition())).getJpost_more_detail_link() + "\n\n" +
                                        "மேலும் இது போன்ற வேலை வாய்ப்பு சமந்தமான தகவல்களை உடனுக்குடன் தெரிந்து கொள்ள *\"Tamilan jobs - Velai Vaaippu\"* செயலியை உடனே டவுன்லோட் செய்து கொள்ளுங்க..." + "\n" +
                                        "*Click here to download:*" + " " + "https://www.tamilanjobs.com/androidapp/" + "\n\n"+
                                        "*குறிப்பு:* இந்த தகவலை உங்கள் நண்பர்கள் அனைவருக்கும் ஷேர் பண்ணுங்க.. வேலை தேடும் நம் நண்பர்களுக்கும் கண்டிப்பாக உதவுங்கள்...");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        dashboardActivity.startActivity(sendIntent);

                    } else {

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "*"+job_list.get(getposition(getAdapterPosition())).getJpost_short_title() + "*" + "\n\n"
                                        +"*பணி இடங்கள்:*"+" "+
                                        job_list.get(getposition(getAdapterPosition())).getJpost_total_vacancy() + "\n\n" +
                                        "*கடைசி தேதி:*" + " " + job_list.get(getposition(getAdapterPosition())).getJpost_walkin_date() + "\n\n" +
                                        "*கூடுதல் தகவலுக்கு:*" + " " + job_list.get(getposition(getAdapterPosition())).getJpost_more_detail_link() + "\n\n" +
                                        "மேலும் இது போன்ற வேலை வாய்ப்பு சமந்தமான தகவல்களை உடனுக்குடன் தெரிந்து கொள்ள *\"Tamilan jobs - Velai Vaaippu\"* செயலியை உடனே டவுன்லோட் செய்து கொள்ளுங்க..." + "\n" +
                                        "*Click here to download:*" + " " + "https://www.tamilanjobs.com/androidapp/" + "\n\n"+
                                        "*குறிப்பு:* இந்த தகவலை உங்கள் நண்பர்கள் அனைவருக்கும் ஷேர் பண்ணுங்க.. வேலை தேடும் நம் நண்பர்களுக்கும் கண்டிப்பாக உதவுங்கள்...");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        dashboardActivity.startActivity(sendIntent);
                    }


                }
            });

            linear_favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String urlimage = " ";

                    if (job_list.get(getposition(getAdapterPosition())).getJpost_type().equals("3")) {

                        if (job_list.get(getposition(getAdapterPosition())).getJpost_image() != null) {
                            urlimage = job_list.get(getposition(getAdapterPosition())).getJpost_image();

                        } else {
                            urlimage = " ";

                        }
                        Spanned title = Html.fromHtml(job_list.get(getposition(getAdapterPosition())).getJpost_short_title().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));
                        Spanned organization1 = Html.fromHtml(job_list.get(getposition(getAdapterPosition())).getJpost_organization().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));



                        saveInventory(job_list.get(getposition(getAdapterPosition())).getJpost_id(),
                                title.toString(),
                                job_list.get(getposition(getAdapterPosition())).getJpost_total_vacancy(),
                                organization1.toString(),
                                job_list.get(getposition(getAdapterPosition())).getJpost_walkin_date(),
                                urlimage,
                                job_list.get(getposition(getAdapterPosition())).getJpost_more_detail_link(),
                                job_list.get(getposition(getAdapterPosition())).getIsRemind(),
                                job_list.get(getposition(getAdapterPosition())).getJpost_type());

                        Log.d("fefhefiefiefieoifoie", urlimage);
                        Log.v("fenfefefhefehfuie", job_list.get(getAdapterPosition()).getIsRemind());
                    } else {

                        urlimage = job_list.get(getposition(getAdapterPosition())).getJpost_image();
                        Spanned title = Html.fromHtml(job_list.get(getposition(getAdapterPosition())).getJpost_short_title().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));
                        Spanned organization1 = Html.fromHtml(job_list.get(getposition(getAdapterPosition())).getJpost_organization().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));


                        saveInventory(job_list.get(getposition(getAdapterPosition())).getJpost_id(),
                                title.toString(),
                                job_list.get(getposition(getAdapterPosition())).getJpost_total_vacancy(),
                                organization1.toString(),
                                job_list.get(getposition(getAdapterPosition())).getJpost_end_date(),
                                urlimage,
                                job_list.get(getposition(getAdapterPosition())).getJpost_more_detail_link(),
                                job_list.get(getposition(getAdapterPosition())).getIsRemind(),
                                job_list.get(getposition(getAdapterPosition())).getJpost_type());

                    }


                    job_list.get(getposition(getAdapterPosition())).setIsFav("1");
                    notifyDataSetChanged();

                    linear_unfavourite.setVisibility(View.VISIBLE);
                    linear_favourite.setVisibility(View.GONE);


                }
            });

            linear_unfavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new Delete().from(Inventory.class).where("postid = ?",
                            job_list.get(getposition(getAdapterPosition())).getJpost_id()).execute();

                    job_list.get(getposition(getAdapterPosition())).setIsFav("0");
                    notifyDataSetChanged();

                    linear_unfavourite.setVisibility(View.GONE);
                    linear_favourite.setVisibility(View.VISIBLE);

                    Toast.makeText(dashboardActivity, "விருப்பமானவையிலிருந்து நீக்கப்பட்டது", Toast.LENGTH_SHORT).show();

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

                        if (job_list.get(getposition(getAdapterPosition())).getJpost_type().equals("1")) {
                            dateDob = sdf.parse(getCurrentDateTime);

                            SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = null;
                            date = form.parse(job_list.get(getposition(getAdapterPosition())).getJpost_end_date());
                            dateDoa = date;

                        } else if (job_list.get(getposition(getAdapterPosition())).getJpost_type().equals("2")) {
                            dateDob = sdf.parse(getCurrentDateTime);

                            SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = null;
                            date = form.parse(job_list.get(getposition(getAdapterPosition())).getJpost_end_date());
                            dateDoa = date;

                        } else {
                            dateDob = sdf.parse(getCurrentDateTime);

                            SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = null;
                            date = form.parse(job_list.get(getposition(getAdapterPosition())).getJpost_walkin_date());
                            dateDoa = date;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (dateDob.after(dateDoa)) {
                        Toast.makeText(dashboardActivity, "Job expired!, You could not set reminder", Toast.LENGTH_SHORT).show();
                    } else {

                        String end_date = null;

                        final Dialog dialog = new Dialog(dashboardActivity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.reminderme_dialog);
                        dialog.show();


                        View img_closedialog = dialog.findViewById(R.id.img_closedialog);
                        final EditText edt_remindmedate = (EditText) dialog.findViewById(R.id.edt_remindmedate);
                        final LinearLayout linear_dialogreminddate = (LinearLayout) dialog.findViewById(R.id.linear_dialogreminddate);
                        final LinearLayout linear_dialogremindtime = (LinearLayout) dialog.findViewById(R.id.linear_dialogremindtime);
                        final EditText edt_remindermename = (EditText) dialog.findViewById(R.id.edt_remindermename);
                        final EditText edt_remindmedescription = (EditText) dialog.findViewById(R.id.edt_remindmedescription);
                        final EditText edt_remindmetime = (EditText) dialog.findViewById(R.id.edt_remindmetime);
                        final LinearLayout linear_remindermeupdate = (LinearLayout) dialog.findViewById(R.id.linear_remindermeupdate);


                        List<Reminder> reminders = new Select()
                                .from(Reminder.class)
                                .where("reminderpostid = ?", job_list.get(getposition(getAdapterPosition())).getJpost_id())
                                .execute();

                        Log.d("dsddsssds", "onClick: " + job_list.get(getposition(getAdapterPosition())).getJpost_id());


                        Spanned title = Html.fromHtml(job_list.get(getposition(getAdapterPosition())).getJpost_short_title().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));
                        Spanned organization1 = Html.fromHtml(job_list.get(getposition(getAdapterPosition())).getJpost_organization().trim().replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " "));


                        if (reminders.size() != 0) {

                            edt_remindermename.setText(title);
                            edt_remindmedescription.setText(organization1);
                            edt_remindmedate.setText(reminders.get(0).getReminderdate());
                            edt_remindmetime.setText(reminders.get(0).getRemindertime());
                        } else {
                            edt_remindermename.setText(title);
                            edt_remindmedescription.setText(organization1);

                        }


                        img_closedialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });


                        linear_dialogreminddate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (job_list.get(getposition(getAdapterPosition())).getJpost_type().equals("3")) {


                                    setAppointment(edt_remindmedate,
                                            edt_remindmetime,
                                            job_list.get(getposition(getAdapterPosition())).getJpost_walkin_date(),
                                            dialog,
                                            getposition(getAdapterPosition()));
                                } else {

                                    setAppointment(edt_remindmedate,
                                            edt_remindmetime,
                                            job_list.get(getposition(getAdapterPosition())).getJpost_end_date(),
                                            dialog,
                                            getposition(getAdapterPosition()));
                                }


                            }
                        });


                        linear_remindermeupdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                remindermename = edt_remindermename.getText().toString().trim();
                                remindmedescription = edt_remindmedescription.getText().toString().trim();
                                String remindmedate = edt_remindmedate.getText().toString().trim();
                                String remindmetime = edt_remindmetime.getText().toString().trim();

                                job_list.get(getposition(getAdapterPosition())).setIsRemind("1");

                                Log.d(TAG, "onClick: " + remindmedate);
                                Log.d(TAG, "onClick: " + remindmetime);

                                if (job_list.get(getposition(getAdapterPosition())).getJpost_type().equals("3")) {

                                    if (!edt_remindermename.getText().toString().isEmpty()) {
                                        if (!edt_remindmedescription.getText().toString().isEmpty()) {
                                            if (edt_remindmedate.getText().toString().trim() != null &&
                                                    !edt_remindmetime.getText().toString().trim().isEmpty()) {

                                                savereminder(job_list.get(getposition(getAdapterPosition())).getJpost_id(),
                                                        job_list.get(getposition(getAdapterPosition())).getJpost_total_vacancy(),
                                                        job_list.get(getposition(getAdapterPosition())).getJpost_walkin_date(),
                                                        remindermename, remindmedescription, remindmedate, remindmetime,
                                                        job_list.get(getposition(getAdapterPosition())).getJpost_image(),
                                                        job_list.get(getposition(getAdapterPosition())).getJpost_type());
                                                notifyDataSetChanged();
                                                dialog.cancel();


                                            } else {
                                                Toast.makeText(dashboardActivity, "Please enter the date and time", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(dashboardActivity, "Please enter reminder description", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(dashboardActivity, "Please enter reminder name", Toast.LENGTH_SHORT).show();
                                    }


                                } else {


                                    if (!edt_remindermename.getText().toString().isEmpty()) {
                                        if (!edt_remindmedescription.getText().toString().isEmpty()) {
                                            if (edt_remindmedate.getText().toString().trim() != null &&
                                                    !edt_remindmetime.getText().toString().trim().isEmpty()) {


                                                savereminder(job_list.get(getposition(getAdapterPosition())).getJpost_id(),
                                                        job_list.get(getposition(getAdapterPosition())).getJpost_total_vacancy(),
                                                        job_list.get(getposition(getAdapterPosition())).getJpost_end_date(),
                                                        remindermename, remindmedescription, remindmedate,
                                                        remindmetime,
                                                        job_list.get(getposition(getAdapterPosition())).getJpost_image()
                                                        , job_list.get(getposition(getAdapterPosition())).getJpost_type());
                                                notifyDataSetChanged();

                                                dialog.cancel();

                                            } else {
                                                Toast.makeText(dashboardActivity, "Please enter the date and time", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(dashboardActivity, "Please enter reminder description", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(dashboardActivity, "Please select reminder name", Toast.LENGTH_SHORT).show();
                                    }


                                }


                            }
                        });

                    }


                }
            });


        }

    }


    public class NativeAdViewHolder extends RecyclerView.ViewHolder {

       // private final NativeExpressAdView mNativeAd;
       AdView adViewbottom;

        public NativeAdViewHolder(final View itemView) {
            super(itemView);
          //  mNativeAd = (NativeExpressAdView) itemView.findViewById(R.id.nativeAd);

           // mNativeAd.setVisibility(View.GONE);

//            AdRequest adRequest = new AdRequest.Builder()
//                    .build();
//
//            mNativeAd.loadAd(adRequest);
//
//            mNativeAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                    Log.d(TAG, "onAdLoadednative: " + "onAdLoaded");
//                    mNativeAd.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAdClosed() {
//                    super.onAdClosed();
//                    Log.d(TAG, "onAdLoadednative: " + "onAdClosed");
//                }
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//                    super.onAdFailedToLoad(errorCode);
//                    Log.d(TAG, "onAdLoadednative: " + "onAdFailedToLoad");
//                    Log.d(TAG, "onAdLoadednative: " + errorCode);
//
//                }
//
//                @Override
//                public void onAdLeftApplication() {
//                    super.onAdLeftApplication();
//                    Log.d(TAG, "onAdLoadednative: " + "onAdLeftApplication");
//
//
//                }
//
//                @Override
//                public void onAdOpened() {
//                    super.onAdOpened();
//                    Log.d(TAG, "onAdLoadednative: " + "onAdOpened");
//
//
//                }
//            });

            adViewbottom = itemView.findViewById(R.id.adViewbottom);
            AdRequest adRequest = new AdRequest.Builder().build();
            adViewbottom.loadAd(adRequest);

            AdView adView = new AdView(dashboardActivity);
            adView.setAdSize(AdSize.BANNER);
            //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            adView.setAdUnitId(dashboardActivity.getString(R.string.Banner_id));



            adViewbottom.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.d(TAG, "onAdLoaded: " + "loaded");
                    adViewbottom.setVisibility(View.VISIBLE);
                    //  Toast.makeText(JobsfullviewActivity.this, "loaded", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Log.d(TAG, "onAdLoaded: " + "onAdFailedToLoad");
                    // Toast.makeText(JobsfullviewActivity.this, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.d(TAG, "onAdLoaded: " + "onAdOpened");
                    //  Toast.makeText(JobsfullviewActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    Log.d(TAG, "onAdLoaded: " + "onAdLeftApplication");
                    // Toast.makeText(JobsfullviewActivity.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                    Log.d(TAG, "onAdLoaded: " + "onAdClosed");
                    // Toast.makeText(JobsfullviewActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    public void setAppointment(final EditText edt_remindmedate,
                               final EditText edt_remindmetime,
                               String end_date, Dialog dialog,
                               int adapterPosition) {


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                @Override
                                                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                                                    String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                                                                    String month = months[monthOfYear];


                                                                    edt_remindmedate.setText(dayOfMonth + "-" + month + "-" + year);

                                                                    Mycalendertype.set(Calendar.YEAR, year);
                                                                    Mycalendertype.set(Calendar.MONTH, monthOfYear);
                                                                    Mycalendertype.set(Calendar.DAY_OF_MONTH, dayOfMonth);

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


        dpd.setMinDate(now);

        Date maximumdate = null;

        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            if (job_list.get(adapterPosition).getJpost_type().equals("3")) {
                date = form.parse(job_list.get(adapterPosition).getJpost_walkin_date());

            } else {
                date = form.parse(job_list.get(adapterPosition).getJpost_end_date());

            }
            maximumdate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        dpd.setMaxDate(toCalendar(maximumdate));


//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = null;
//        try {
//            date = sdf.parse(end_date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long millis = date.getTime();

//        dpd.setMinDate(now);
//        dpd.setMaxDate(date);

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(date.getYear(),date.getMonth(),date.getDate());
//
//
//        try
//        {
//            dpd.setMinDate(now);
//            dpd.setMaxDate(end_date);
//        }catch (Exception e){
//                e.printStackTrace();
//        }


//        Calendar lastdate = new Calendar(String.valueOf(date));
//        dpd.setMaxDate(date.getTime());

        // String.format("%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp", calendar);

        dpd.show(dashboardActivity.getFragmentManager(), "Datepickerdialog");

        //dpd.setMaxDate(now.getTimeInMillis());
        //dpd.setSelectableDays(Calendar.DAY_OF_MONTH,"1");

    }

    private void tiemPicker(final EditText edt_remindmetime) {


        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


        //Mycalendertype = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(dashboardActivity,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;

                        Mycalendertype.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        Mycalendertype.set(Calendar.MINUTE, minute);

                        boolean isPM = (hourOfDay >= 12);
                        edt_remindmetime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));


                        String time = "You picked the following time: "+hourOfDay+"h"+minute;
                      //  edt_remindmetime.setText(time);


                        // edt_remindmetime.setText(hourOfDay + ":" + minute);
                    }

                }, mHour, mMinute, false);

        //timePickerDialog.setMin(hour, minute);

//        timePickerDialog.setMinTime(now.get(Calendar.HOUR_OF_DAY),now2.get(Calendar.MINUTE),
//                now2.get(Calendar.SECOND));

//        c.setTime(timePickerDialog.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),
//                c.get(Calendar.SECOND));

        timePickerDialog.show();
    }

//    private void tiemPicker(final EditText edt_remindmetime) {
//
//    }
//
//    Calendar now = Calendar.getInstance();
//    TimePickerDialog tpd = TimePickerDialog.newInstance(
//            dashboardActivity,
//            now.get(Calendar.HOUR_OF_DAY),
//            now.get(Calendar.MINUTE),
//            mode24Hours.isChecked()
//    );
//                tpd.setThemeDark(modeDarkTime.isChecked());
//                tpd.vibrate(vibrateTime.isChecked());
//                tpd.dismissOnPause(dismissTime.isChecked());
//                tpd.enableSeconds(enableSeconds.isChecked());
//                if(modeCustomAccentTime.isChecked())
//
//    {
//        tpd.setAccentColor(Color.parseColor("#9C27B0"));
//    }
//                if(titleTime.isChecked())
//
//    {
//        tpd.setTitle("TimePicker Title");
//    }
//                if(limitTimes.isChecked())
//
//    {
//        tpd.setTimeInterval(2, 5, 10);
//    }
//                tpd.setOnCancelListener(new DialogInterface.OnCancelListener()
//
//    {
//        @Override
//        public void onCancel (DialogInterface dialogInterface){
//        Log.d("TimePicker", "Dialog was cancelled");
//    }
//    });
//                tpd.show(
//
//    getFragmentManager(), "Timepickerdialog");
//
//}

    private void saveInventory(String jpost_id,
                               String short_title,
                               String jpost_total_vacancy,
                               String jpost_organization,
                               String jpost_createddate,
                               String jpost_image,
                               String applylink,
                               String isRemind, String jpost_type) {


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
        inventory.favouritetypeid = jpost_type;
        //Saving name to sqlite database
        inventory.save();


        Toast.makeText(dashboardActivity, "விருப்பமானவையில் சேர்க்கப்பட்டது", Toast.LENGTH_LONG).show();
    }


    private List<Inventory> getAll() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Inventory.class)
                .orderBy("postid ASC")
                .execute();
    }


    private void savereminder(String jpostId, String vacancy, String datetime,
                              String remindermename,
                              String remindmedescription,
                              String remindmedate,
                              String remindmetime,
                              String jpost_image, String jpost_type) {
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
        reminder.remindertypeid = jpost_type;


        Log.d(TAG, "savereminder:time "  + remindmetime);
        Log.d(TAG, "savereminder:date "  + remindmedate);

        //Saving name to sqlite database
        reminder.save();
        //  inventoryItems.add(jpost_id);


        setAlarm(jpostId);



        // getAll();

        Toast.makeText(dashboardActivity, "Reminder job successfully", Toast.LENGTH_LONG).show();
    }


    //setalarm

    private void setAlarm(String jpostId) {


        Log.d("dsdsddsdss", "savereminder: " + jpostId);

        if (Mycalendertype != null) {
            Log.d(TAG, "setAlarm:name " + remindermename);
            Log.d(TAG, "setAlarm:description " + remindmedescription);
            Intent intent = new Intent(dashboardActivity, AlarmManagerBroadcastReceiver.class);
            intent.putExtra("jobid",jpostId);
            intent.putExtra("jobtitle", remindermename);
            intent.putExtra("joborganization", remindmedescription);


            // PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RQS_1, intent, 0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(dashboardActivity, Integer.parseInt(jpostId), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) dashboardActivity.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, Mycalendertype.getTimeInMillis(), pendingIntent);


            Log.d("fdfdfd", "setAlarm: " + Mycalendertype);

           // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dueDate.getMillis(), due.getMillis(), operation);
        } else {
            Toast.makeText(dashboardActivity, "No alarm available", Toast.LENGTH_SHORT).show();
        }


    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }




}
