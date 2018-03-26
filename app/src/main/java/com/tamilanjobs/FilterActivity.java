package com.tamilanjobs;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tamilanjobs.Response.BasicResponse;
import com.tamilanjobs.Response.Searchjobdetail;
import com.tamilanjobs.Rest.ApiClient;
import com.tamilanjobs.Rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {


    RecyclerView recycle_projectfiltermainitem, recycle_projectfiltersubitem;

    ArrayList<String> mainjobs = new ArrayList<>();
    ProjectFilterMainAdapter projectFilterMainAdapter;

    LinearLayout linear_submitfilter;

    ArrayList<Filterloaddatelist> education_qualification;
    ArrayList<Filterloaddatelist> education_qualificationTemp = new ArrayList<>();
    ArrayList<Filterloaddatelist> job_category;

    MarketingStockProductSubAdapter marketingStockProductSubAdapter;
    MarketingStockProjectSubAdapter marketingStockProjectSubAdapter;

    ArrayList<Searchjobdetail> searchjob_list = new ArrayList<>();

    LinearLayout linear_search;

    public EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        UIinitialization();

        // addTextListener();
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


        recycle_projectfiltermainitem = (RecyclerView) findViewById(R.id.recycle_projectfiltermainitem);
        recycle_projectfiltersubitem = (RecyclerView) findViewById(R.id.recycle_projectfiltersubitem);
        linear_submitfilter = (LinearLayout) findViewById(R.id.linear_submitfilter);
        search = (EditText) findViewById(R.id.search);
        linear_search = findViewById(R.id.linear_search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                education_qualificationTemp.clear();
/*                ArrayList<Filterloaddatelist> education_qualification;*/
                for (Filterloaddatelist a : education_qualification) {

                    if (a.getName() != null && a.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        education_qualificationTemp.add(a);
                    }

                }


                marketingStockProductSubAdapter = new MarketingStockProductSubAdapter(
                        education_qualificationTemp, search,
                        linear_search, FilterActivity.this,
                        1);

                recycle_projectfiltersubitem.setAdapter(marketingStockProductSubAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recycle_projectfiltermainitem.setLayoutManager(new LinearLayoutManager(this));
        recycle_projectfiltersubitem.setLayoutManager(new LinearLayoutManager(this));


        mainjobs.add("கல்வித்தகுதி");
        mainjobs.add("வேலை பிரிவு");


        projectFilterMainAdapter = new ProjectFilterMainAdapter(FilterActivity.this, mainjobs);
        recycle_projectfiltermainitem.setAdapter(projectFilterMainAdapter);

        //onclick
//        linear_submitfilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
////                Intent linear_submitfilter = new Intent(FilterActivity.this, FilterlistActivity.class);
////                linear_submitfilter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                startActivity(linear_submitfilter);
//            }
//        });

        linear_submitfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String product_filterid = "";


                    for (int i = 0; i < education_qualification.size(); i++) {
                        if (education_qualification.get(i).isChkItem()) {

                            product_filterid = product_filterid + education_qualification.get(i).getName() + "|";

                            Log.d("sdsd", "onClickclicked: " + product_filterid);

                        }
                    }

                    if (!product_filterid.equals(""))
                        product_filterid = product_filterid.substring(0, product_filterid.length() - 1);

                    Log.d("sdsdsdssd", "submitData: " + product_filterid);

                    String project_filterid = "";
                    for (int i = 0; i < job_category.size(); i++) {
                        if (job_category.get(i).isChkItem()) {

                            project_filterid = project_filterid + job_category.get(i).getId() + ",";

                            Log.d("sdsd", "onClickclicked: " + project_filterid);

                        }
                    }


                    if (project_filterid != null && project_filterid.length() > 0 && project_filterid.charAt(project_filterid.length() - 1) == ',') {
                        project_filterid = project_filterid.substring(0, project_filterid.length() - 1);
                    }

                    Log.d("dddsd", "submitData: " + project_filterid);

                    //submitData();

                    Intent intent = new Intent();
                    intent.putExtra("marketingstocklist", product_filterid);
                    intent.putExtra("marketingfilter", project_filterid);
                    setResult(2, intent);
                    finish();//finishing activity
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        Loadfiltervalues();
    }

    //loaddata
    Call<BasicResponse> loaddatacall;


    private void Loadfiltervalues() {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        loaddatacall = apiService.searchjoblist();
        loaddatacall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                try {

                    if (response.body().getSuccess() == 1) {

                        //submain arraylist

                        education_qualification = new ArrayList<>();
                        job_category = new ArrayList<>();


                        education_qualification.addAll(response.body().getEducation_qualification());
                        job_category.addAll(response.body().getJob_category());


                        marketingStockProductSubAdapter = new MarketingStockProductSubAdapter(
                                education_qualification, search, linear_search,
                                FilterActivity.this,
                                1);

                        recycle_projectfiltersubitem.setAdapter(marketingStockProductSubAdapter);

                    }
                } catch (Exception e) {


                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                t.printStackTrace();

                Toast.makeText(FilterActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void load_data(int position) {

        switch (position) {
            case 0:

                marketingStockProductSubAdapter = new MarketingStockProductSubAdapter(
                        education_qualification, search, linear_search,
                        FilterActivity.this,
                        1);

                recycle_projectfiltersubitem.setAdapter(marketingStockProductSubAdapter);
                break;

            case 1:

                marketingStockProjectSubAdapter = new MarketingStockProjectSubAdapter(
                        job_category, search, linear_search,
                        FilterActivity.this,
                        1);

                recycle_projectfiltersubitem.setAdapter(marketingStockProjectSubAdapter);
                break;

        }
    }


    Call<BasicResponse> call1;


    private void submitData() {


        Log.d("sdsdsdsd", "submitData: ");
        searchjob_list.clear();


        String product_filterid = "";
        for (int i = 0; i < education_qualification.size(); i++) {
            if (education_qualification.get(i).isChkItem()) {

                product_filterid = product_filterid + education_qualification.get(i).getName() + ",";

                Log.d("sdsd", "onClickclicked: " + product_filterid);

            }
        }

        if (!product_filterid.equals(""))
            product_filterid = product_filterid.substring(0, product_filterid.length() - 1);

        Log.d("sdsdsdssd", "submitData: " + product_filterid);

        String project_filterid = "";
        for (int i = 0; i < job_category.size(); i++) {
            if (job_category.get(i).isChkItem()) {

                project_filterid = project_filterid + job_category.get(i).getId() + ",";

                Log.d("sdsd", "onClickclicked: " + project_filterid);

            }
        }


        if (project_filterid != null && project_filterid.length() > 0 && project_filterid.charAt(project_filterid.length() - 1) == ',') {
            project_filterid = project_filterid.substring(0, project_filterid.length() - 1);
        }

        Log.d("dddsd", "submitData: " + project_filterid);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

      /*  Map map = new HashMap<>();
        map.put("jrole_id",product_filterid);
        map.put("job_emp_type_id",project_filterid);*/

        //   call1 = apiService.searchjobpost(product_filterid, project_filterid);
        call1 = apiService.searchjobpost(product_filterid, project_filterid);
        call1.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {


                if (response.body().getSuccess() == 1) {

                    searchjob_list.addAll(response.body().getSearchjob_list());

                    Log.d("sdddsds", "onResponse: " + searchjob_list.size());

                    if (searchjob_list.size() != 0) {

                        //                      Intent btn_applymanageleadfilter = new Intent(FilterActivity.this, FilterlistActivity.class);
//                        btn_applymanageleadfilter.putParcelableArrayListExtra("marketingstocklist", (ArrayList) searchjob_list);
//                        startActivity(btn_applymanageleadfilter);

                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("marketingstocklist", (ArrayList) searchjob_list);
                        setResult(2, intent);
                    }

                }

                if (response.body().getSuccess() == 0) {

                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("marketingstocklist", (ArrayList) searchjob_list);
                    setResult(2, intent);

//                    Intent btn_applymanageleadfilter = new Intent(FilterActivity.this, FilterlistActivity.class);
//                    btn_applymanageleadfilter.putParcelableArrayListExtra("marketingstocklist", (ArrayList) searchjob_list);
//                    startActivity(btn_applymanageleadfilter);
                }


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }


//    public void addTextListener() {
//
//        search.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence query, int start, int before, int count) {
//
//                query = query.toString().toLowerCase();
//
//                final List<String> filteredList = new ArrayList<>();
//
//                for (int i = 0; i < education_qualification.size(); i++) {
//
//                    final String text = education_qualification.get(i).toString();
//                    if (text.contains(query)) {
//
//                        filteredList.add(education_qualification.get(i).toString());
//                    }
//                }
//
//                recycle_projectfiltersubitem.setLayoutManager(new LinearLayoutManager(FilterActivity.this));
//                marketingStockProductSubAdapter = new MarketingStockProductSubAdapter(
//                        filteredList,
//                        FilterActivity.this,
//                        1);
//                marketingStockProductSubAdapter.notifyDataSetChanged();  // data set changed
//            }
//        });
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
