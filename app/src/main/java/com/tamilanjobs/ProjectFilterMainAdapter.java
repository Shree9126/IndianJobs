package com.tamilanjobs;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Admin on 21-12-2017.
 */

public class ProjectFilterMainAdapter extends RecyclerView.Adapter<ProjectFilterMainAdapter.ViewHolder> {

    FilterActivity context;
    ArrayList<String> mainjobs;
    int globalPosition;

    public ProjectFilterMainAdapter(FilterActivity context, ArrayList<String> mainjobs) {
        this.context = context;
        this.mainjobs = mainjobs;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_projectfiltermain, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txt_projectnames.setText(mainjobs.get(position));

        if (position == globalPosition) {
            holder.vw_select.setBackgroundColor(Color.parseColor("#e31e24"));
        } else {
            holder.vw_select.setBackgroundColor(Color.parseColor("#ebebeb"));
        }


        if(position==0){
            holder.imageLoad.setImageResource(R.drawable.edu_redicon);
        }
        if(position==1){
            holder.imageLoad.setImageResource(R.drawable.ic_job_search_in_newspapers);
        }






    }

    @Override
    public int getItemCount() {
        return mainjobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_projectnames;
        View vw_select;
        LinearLayout lin_lay_search;
        ImageView imageLoad;

        public ViewHolder(View itemView) {
            super(itemView);


            txt_projectnames = (TextView) itemView.findViewById(R.id.txt_projectnames);
            lin_lay_search = (LinearLayout) itemView.findViewById(R.id.lin_lay_search);
            vw_select = itemView.findViewById(R.id.vw_select);
            imageLoad = itemView.findViewById(R.id.imageLoad);
//
            lin_lay_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    context.load_data(getAdapterPosition());
                    globalPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });

        }
    }
}
