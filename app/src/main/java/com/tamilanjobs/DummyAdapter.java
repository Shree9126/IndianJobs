package com.tamilanjobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tamilanjobs.Response.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahei on 2/3/2018.
 */

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.ViewHolder> {

    Context context;
    private static final int LIST_AD_DELTA = 3;
    private static final int CONTENT = 0;
    private static final int AD = 1;
    //ArrayList<String> inventoryItems;
    List<Inventory> inventoryItems;


    public DummyAdapter(Context context, List<Inventory> inventoryItems) {
        this.context = context;
        this.inventoryItems = inventoryItems;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_dummyjobs, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


       // Inventory inventory = this.inventoryItems.get(position);

        //position set value

        holder.txt_name.setText(inventoryItems.get(position).getName());

        Log.d("sdsds", "onBindViewHolder: " + inventoryItems.get(position).getName());


        Toast.makeText(context, ""+inventoryItems.get(position).getName(), Toast.LENGTH_SHORT).show();
//        holder.txt_notificationtitle.setText("1");
//        holder.txt_notificationdate.setText("1");
//        holder.txt_notificationdescription.setText("1");
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_name = (TextView)itemView.findViewById(R.id.txt_name);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0 && position % LIST_AD_DELTA == 0) {
            return AD;
        }
        return CONTENT;
    }
}
