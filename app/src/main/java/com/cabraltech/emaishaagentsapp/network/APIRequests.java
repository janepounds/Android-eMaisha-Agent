package com.cabraltech.emaishaagentsapp.network;

import com.cabraltech.emaishaagentsapp.models.Regions;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRequests {


    @FormUrlEncoded
    @POST("getregions")
    Call<Regions> getAllRegions(@Field("latest_id") int latest_id);
}
