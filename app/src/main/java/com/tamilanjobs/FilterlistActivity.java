package com.tamilanjobs;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Searchjobdetail;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterlistActivity extends AppCompatActivity {

    RecyclerView recycle_filterlist;
    FilterlistAdapter filterlistAdapter;
    ArrayList<Searchjobdetail> searchjob_list = new ArrayList<>();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterlist);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.toolbar_backarrowicon);


        toolbar.setTitleTextColor(Color.WHITE);

        recycle_filterlist  = (RecyclerView)findViewById(R.id.recycle_filterlist);

        recycle_filterlist.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(FilterlistActivity.this);

        if (getIntent() != null) {

            searchjob_list = (ArrayList) getIntent().getParcelableArrayListExtra("marketingstocklist");
            // manageleadlist =  getIntent().getParcelableArrayListExtra("leadlist");

            Log.d("sdsdssds", "onResponse: " + searchjob_list.size());
        }



       // joblistfilter_detail();
    }


    //joblist

//    Call<BasicResponse> joblistcall;
//
//    private void joblistfilter_detail() {
//
//
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//
//
//        progressDialog.show();
//
//        joblistcall = apiService.getJobList();
//
//        joblistcall.enqueue(new Callback<BasicResponse>() {
//            @Override
//            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
//
//
//                if (response.body().getSuccess() == 1) {
//
//
//
//                    if (searchjob_list.size() != 0) {
//
//
//                        progressDialog.dismiss();
//                        filterlistAdapter = new FilterlistAdapter(FilterlistActivity.this,searchjob_list);
//                        recycle_filterlist.setAdapter(filterlistAdapter);
//                    }
//
//                } else {
//                    progressDialog.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<BasicResponse> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//        });
//
//    }



    //onbackpress

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
