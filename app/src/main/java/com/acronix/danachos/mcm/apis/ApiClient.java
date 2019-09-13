package com.acronix.danachos.mcm.apis;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 11/27/2017.
 */

public class ApiClient {


    private static Retrofit mRetrofit;

    public static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if(mRetrofit == null){

            mRetrofit=new Retrofit.Builder()
                    .baseUrl("http://dbwizku.atwebpages.com/restapi/public/index.php/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();


        }

        return  mRetrofit;
    }



}
