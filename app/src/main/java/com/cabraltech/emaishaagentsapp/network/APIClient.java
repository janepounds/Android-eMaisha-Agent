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
    private static final String WEATHER_BASE_URL ="https://api.climacell.co/v3/";
    private static APIRequests apiRequests;
    private static WeatherApiRequests weatherApiRequests;

    // Singleton Instance of APIRequests
    public static APIRequests getInstance() {
    if (apiRequests == null) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.e("Retrofit2 Errors", "message: "+message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                //.addInterceptor(apiInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            // .addHeader(Constant.Header, authToken)
                            .build();
                    return chain.proceed(request);
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiRequests = retrofit.create(APIRequests.class);

    }
        return apiRequests;


    }

    // Singleton Instance of APIRequests
    public static WeatherApiRequests getWeatherInstance() {
        if (weatherApiRequests == null) {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.e("Retrofit2 Errors", "message: "+message));
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    //.addInterceptor(apiInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                // .addHeader(Constant.Header, authToken)
                                .build();
                        return chain.proceed(request);
                    })
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            weatherApiRequests = retrofit.create(WeatherApiRequests.class);

        }
        return weatherApiRequests;


    }
}
