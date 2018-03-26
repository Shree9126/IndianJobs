package com.tamilanjobs;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Settingsdetail;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsandConditionActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ArrayList<Settingsdetail> settings_list;
    TextView txt_terms;

    AVLoadingIndicatorView avloadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_condition);

        UIinitialization();

    }

    private void UIinitialization() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.toolbar_backarrowicon);


        toolbar.setTitleTextColor(Color.WHITE);

        txt_terms = (TextView) findViewById(R.id.txt_terms);
        avloadingIndicatorView =  findViewById(R.id.avloadingIndicatorView);


        progressDialog = new ProgressDialog(TermsandConditionActivity.this);
        progressDialog.setMessage("Loading");
        settings_list = new ArrayList<>();

        termsdetails();

    }


    //aboutuslist

    Call<BasicResponse> joblistcall;

    private void termsdetails() {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        //progressDialog.show();

        avloadingIndicatorView.setVisibility(View.VISIBLE);
        joblistcall = apiService.settingsdata("3");

        joblistcall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {

                    settings_list.addAll(response.body().getSettings_list());

                    Log.d("sdsdsds", "onResponse: " + settings_list.size());

                    if (settings_list.size() != 0) {


                        avloadingIndicatorView.setVisibility(View.GONE);
                       // progressDialog.dismiss();

                        Spanned appterms = Html.fromHtml(response.body().getSettings_list().get(0).getPage_description()
                                .replace("&lt;", "<").replace("&gt;", ">")
                                .replace("&nbsp;", " "));


                        txt_terms.setText(appterms);

                        Log.d("dsdsdsds", "onResponse: " + response.body().getSettings_list().get(0).getPage_description());

                    }

                } else {
                  //  progressDialog.dismiss();
                    avloadingIndicatorView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                //progressDialog.dismiss();
                avloadingIndicatorView.setVisibility(View.GONE);
            }
        });

    }


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
