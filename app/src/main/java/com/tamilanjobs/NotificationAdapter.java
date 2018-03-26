package com.tamilanjobs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.tamilanjobs.Helper.Pref;
import com.tamilanjobs.Helper.Utils;
import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Notificationdetail;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Admin on 20-12-2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    ArrayList<Notificationdetail> notification_list;

    public NotificationAdapter(Context context, ArrayList<Notificationdetail> notification_list) {
        this.context = context;
        this.notification_list = notification_list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.notifydummyswipe, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        //position set value
//        holder.txt_notificationtitle.setText("1");
//        holder.txt_notificationdate.setText("1");
//        holder.txt_notificationdescription.setText("1");


        if (notification_list.get(position).getNotify_title() != null) {

            Spanned title = Html.fromHtml(notification_list.get(position).getNotify_title().replace("&lt;", "<").
                            replace("&gt;", ">").replace("&nbsp;", " "));

            holder.txt_notificationtitle.setText(title);
        }

        if (notification_list.get(position).getNotify_short_desc() != null) {

            Spanned shorttitle = Html.fromHtml(notification_list.get(position).getNotify_short_desc().replace("&lt;", "<").
                    replace("&gt;", ">").replace("&nbsp;", " "));


            holder.txt_notificationdescription.setText(shorttitle);
        }

        if (notification_list.get(position).getNotify_created_on() != null) {
            holder.txt_notificationdate.setText(Utils.getDateString(notification_list.get(position).getNotify_created_on()));



        }





        holder.linear_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(notification_list.get(position).getNotify_ntype_id().equals("1")){

                    Intent intent = new Intent(context,NotificationViewActivity.class);
                    intent.putExtra("notify_id",notification_list.get(position).getNotify_id());
                    context.startActivity(intent);
                }else {


                        Intent linear_movetofullview = new Intent(context, JobsfullviewActivity.class);
                        linear_movetofullview.putExtra("jobid", notification_list.get(position).getNotify_jpost_id());
                        linear_movetofullview.putExtra("reminder_id", "0");
                        linear_movetofullview.putExtra("favid", "0");
                      //  linear_movetofullview.putExtra("jobtypeid", "1");
                        linear_movetofullview.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(linear_movetofullview);


                }

            }
        });


        holder.lay_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(notification_list.get(position).getNotify_id()!=null){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                   // alertDialog.setTitle("Confirm Delete...");
                    alertDialog.setTitle("இந்த அறிவிப்பை நீக்க விரும்புகிறீர்களா");

                    // Setting Dialog Message
                   // alertDialog.setMessage("Are you sure you want delete this?");

                    // Setting Icon to Dialog

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("ஆம்", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            // Write your code here to invoke YES event

                            delete_notify(notification_list.get(position).getNotify_id(), position);

                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("இல்லை", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                          //  Toast.makeText(context, "No notification deleted", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                }else {
                    Toast.makeText(context, "No notification available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.lay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent cancel = new Intent(context,NotificationActivity.class);
//                cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(cancel);
//
//
//                context.startA);

                holder.swipe.close();

               // cancel(position);
            }
        });





    }



    @Override
    public int getItemCount() {
        return notification_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linear_notification,lay_delete,lay_cancel;
        TextView txt_notificationtitle, txt_notificationdate, txt_notificationdescription;

        ImageView notifydelete;

        SwipeLayout swipe;

        public ViewHolder(View itemView) {
            super(itemView);


            swipe =  itemView.findViewById(R.id.swipe);
            linear_notification = (LinearLayout) itemView.findViewById(R.id.linear_notification);
            txt_notificationtitle = (TextView) itemView.findViewById(R.id.txt_notificationtitle);
            txt_notificationdate = (TextView) itemView.findViewById(R.id.txt_notificationdate);
            txt_notificationdescription = (TextView) itemView.findViewById(R.id.txt_notificationdescription);
            lay_delete =  itemView.findViewById(R.id.lay_delete);
            lay_cancel =  itemView.findViewById(R.id.lay_cancel);
            //notifydelete = (ImageView) itemView.findViewById(R.id.notifydelete);


//            lay_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    delete_notify(notification_list.get(getAdapterPosition()).getNotify_id(),);
//                }
//            });


        }
    }

    //aboutuslist

    Call<BasicResponse> notifycall;

    private void delete_notify(String notify_id, final int position) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        notifycall = apiService.deletenotify(notify_id, Pref.getPreAccountDeviceId());

        notifycall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {


                    notification_list.remove(notification_list.get(position).getNotify_id());
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, notification_list.size());
                    notifyDataSetChanged();

                    Intent intent = new Intent(context,NotificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                    Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Notification not available", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
