package com.cabraltech.emaishaagentsapp.models.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {
    @SerializedName("lat")
    @Expose
    private String latitude;
    @SerializedName("lon")
    @Expose
    private String longitude;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("visibility")
    @Expose
    private Visibility visibility;
    @SerializedName("humidity")
    @Expose
    private Humidity humidity;
    @SerializedName("wind_speed")
    @Expose
    private WindSpeed windSpeed;

    public WeatherResponse(String latitude, String longitude, Temp temp, Visibility visibility, Humidity humidity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temp = temp;
        this.visibility = visibility;
        this.humidity = humidity;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public class Temp{
        @SerializedName("value")
        @Expose
        private double value;
        @SerializedName("units")
        @Expose
        private String units;

        public Temp(double value, String units) {
            this.value = value;
            this.units = units;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }

    public class Visibility{
        @SerializedName("value")
        @Expose
        private int value;
        @SerializedName("units")
        @Expose
        private String units;

        public Visibility(int value, String units) {
            this.value = value;
            this.units = units;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }
    public class Humidity{
        @SerializedName("value")
        @Expose
        private double value;
        @SerializedName("units")
        @Expose
        private String units;

        public Humidity(double value, String units) {
            this.value = value;
            this.units = units;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }

    public class WindSpeed{

    }

}
