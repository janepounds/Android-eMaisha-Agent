package com.cabraltech.emaishaagentsapp;

import androidx.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/JosefinSans-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }

}
