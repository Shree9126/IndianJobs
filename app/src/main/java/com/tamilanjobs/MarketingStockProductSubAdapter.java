package com.tamilanjobs;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Admin on 16-01-2018.
 */

public class MarketingStockProductSubAdapter extends RecyclerView.Adapter<MarketingStockProductSubAdapter.ViewHolder> {

    Activity activity;
    ArrayList<Filterloaddatelist> product_filter;
    public static int T_CHECK = 1;
    int type;
    EditText search;
    LinearLayout linear_search;

    public MarketingStockProductSubAdapter(ArrayList<Filterloaddatelist> product_filter, EditText search, LinearLayout linear_search, Activity activity, int type) {
        this.activity = activity;
        this.product_filter = product_filter;
        this.type = type;
        this.search = search;
        this.linear_search = linear_search;

    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        if(type==T_CHECK)
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_marketingstockfiltersub, parent, false);
        }




        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txtMainMenuType.setText(product_filter.get(position).getName());


        search.setVisibility(View.VISIBLE);
        linear_search.setVisibility(View.VISIBLE);

        holder.chk_projectfilter.setChecked(product_filter.get(position).isChkItem());

        holder.chk_projectfilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // values.get(position).setChkItem(true);
                    product_filter.get(position).setChkItem(true);

                   /* DataJobFilterSub dataJobFilterSub = new DataJobFilterSub();
                    dataJobFilterSub.setChkItem(true);*/
                }else{
                    product_filter.get(position).setChkItem(false);
                    // values.get(position).setChkItem(false);
                 /*   DataJobFilterSub dataJobFilterSub = new DataJobFilterSub();
                    dataJobFilterSub.setChkItem(false);*/
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return product_filter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox chk_projectfilter;
        TextView txtMainMenuType;


        public ViewHolder(View itemView) {
            super(itemView);

            chk_projectfilter = (CheckBox)itemView.findViewById(R.id.chk_projectfilter);
            txtMainMenuType = (TextView) itemView.findViewById(R.id.txtMainMenuType);



        }
    }
}
