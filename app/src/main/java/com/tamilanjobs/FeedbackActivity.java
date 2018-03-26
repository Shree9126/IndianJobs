package com.tamilanjobs;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tamilanjobs.Helper.Utils;
import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {


    Button btn_feedbackcreate;

    EditText edt_feedbackname, edt_feedbackphone, edt_feedbackemail, edt_feedbackdescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        UiInitialization();
    }

    private void UiInitialization() {

        //toolbar init
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.toolbar_backarrowicon);

        edt_feedbackname = (EditText) findViewById(R.id.edt_feedbackname);
        edt_feedbackphone = (EditText) findViewById(R.id.edt_feedbackphone);
        edt_feedbackemail = (EditText) findViewById(R.id.edt_feedbackemail);
        edt_feedbackdescription = (EditText) findViewById(R.id.edt_feedbackdescription);
        btn_feedbackcreate = (Button) findViewById(R.id.btn_feedbackcreate);

        //onclick
        btn_feedbackcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldcheck();
            }
        });
    }

    private void fieldcheck() {

        if (edt_feedbackname.getText().length() != 0) {
            if (edt_feedbackemail.getText().length() != 0) {
                if(Utils.isValidMail(edt_feedbackemail.getText().toString())) {
                if (edt_feedbackphone.getText().length() != 0) {
                        if (edt_feedbackdescription.getText().length() != 0) {

                            Createfeedback();

                        } else {
                            Toast.makeText(this, "Enter the description", Toast.LENGTH_SHORT).show();

                        }

                } else {
                    Toast.makeText(this, "Enter the phone", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Enter the valid mail", Toast.LENGTH_SHORT).show();
            }
            } else {
                Toast.makeText(this, "Enter the mail", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Enter the name", Toast.LENGTH_SHORT).show();
        }
    }


    Call<BasicResponse> feedbackcall;


    private void Createfeedback() {


        String name = edt_feedbackname.getText().toString();
        String phone = edt_feedbackphone.getText().toString();
        String email = edt_feedbackemail.getText().toString();
        String description = edt_feedbackdescription.getText().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        feedbackcall = apiService.create_feedback(name, email,phone, description);

        feedbackcall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                Intent intent = new Intent(FeedbackActivity.this, DashboardActivity.class);
                startActivity(intent);
                Toast.makeText(FeedbackActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                Toast.makeText(FeedbackActivity.this, ""+t, Toast.LENGTH_SHORT).show();
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
