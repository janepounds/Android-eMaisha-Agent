package com.cabraltech.emaishaagentsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarketResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Market> dataList;

    public MarketResponse() {
    }

    public MarketResponse(String status, String message, List<Market> dataList) {
        this.status = status;
        this.message = message;
        this.dataList = dataList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Market> getDataList() {
        return dataList;
    }

    public void setDataList(List<Market> dataList) {
        this.dataList = dataList;
    }
}
