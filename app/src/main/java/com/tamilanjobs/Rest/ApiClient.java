package com.tamilanjobs.Rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {


   // public static final String BASE_URL = "http://192.168.0.11/tamilanjobs/";
 //  public static final String BASE_URL = "http://2016.mindnotix.net/android/tamilanjobs/";
    public static final String BASE_URL = "http://tamilanjobs.in/api/";
    //public static final String URL_ACCOUNT_PHOTO =  "http://192.168.0.6/Gymkhana/Uploads/Images/";
   //public static final String URL_ACCOUNT_PHOTO =  "http://mindnotix.net/2018/tamilan_jobs/uploads/jobs/";
    public static final String URL_ACCOUNT_PHOTO =  "http://tamilanjobs.in/nirvakam/uploads/jobs/";
    public static final String URL_ACCOUNT_LOCATION = BASE_URL+ "location.php";

    private static Retrofit retrofit;


    public static Retrofit getClient() {
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }
        return retrofit;

    }

}