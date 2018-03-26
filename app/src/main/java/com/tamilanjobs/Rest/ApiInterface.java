package com.tamilanjobs.Rest;


import com.tamilanjobs.Response.BasicResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiInterface {



    @FormUrlEncoded
    @POST("loginupdate.php")
    Call<BasicResponse> login(@Field("auser_device_id") String auser_device_id,
                                    @Field("auser_imei_no") String auser_imei_no,
                                    @Field("auser_gcm_id") String auser_gcm_id);

    @FormUrlEncoded
    @POST("getjoblist.php")
    Call<BasicResponse> getJobList(@Field("imieno") String imieno);

    @FormUrlEncoded
    @POST("getjoblistfullview.php")
    Call<BasicResponse> getjobfullviewlist(@Field("jpost_id") int jpost_id);

    @FormUrlEncoded
    @POST("createfeedback.php")
    Call<BasicResponse> create_feedback(@Field("feedback_name") String feedback_name,@Field("feedback_email") String feedback_email,@Field("feedback_mobile") String feedback_mobile,@Field("feedback_message") String feedback_message);


    @FormUrlEncoded
    @POST("getnotificationlist.php")
    Call<BasicResponse> getnotificationlist(@Field("imieno") String imieno);

    @FormUrlEncoded
    @POST("aboutus.php")
    Call<BasicResponse> settingsdata(@Field("page_id") String page_id);

    @FormUrlEncoded
    @POST("deletenotification.php")
    Call<BasicResponse> deletenotify(@Field("notify_id") String notify_id,@Field("imieno") String imieno);


    @GET("searchjobload.php")
    Call<BasicResponse> searchjoblist();

    @FormUrlEncoded
    @POST("search_job_post.php")
    Call<BasicResponse> searchjobpost(@Field("job_emp_type_id")
                                              String job_emp_type_id,
                                      @Field("jrole_id") String jrole_id);

/*    @FormUrlEncoded
    @POST("search_job_post.php")
    Call<BasicResponse> searchjobpost(@QueryMap Map map);*/

    @FormUrlEncoded
    @POST("deleteallnotify.php")
    Call<BasicResponse> deleteallnotify(@Field("imieno") String imieno);


    @FormUrlEncoded
    @POST("getnotificationid.php")
    Call<BasicResponse> loadnotificationmessage(@Field("notify_id") String notify_id);



}