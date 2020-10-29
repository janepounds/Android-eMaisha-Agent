package com.cabraltech.emaishaagentsapp.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL ="http://emaishaadmin.myfarmnow.com/api/";
    private static APIRequests apiRequests;

    // Singleton Instance of APIRequests
    public static APIRequests getInstance() {
    if (apiRequests == null) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                Log.e("Retrofit2 Errors", "message: "+message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                //.addInterceptor(apiInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                // .addHeader(Constant.Header, authToken)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiRequests = retrofit.create(APIRequests.class);

        return apiRequests;
    }
        else {
        return apiRequests;
    }
}
}
