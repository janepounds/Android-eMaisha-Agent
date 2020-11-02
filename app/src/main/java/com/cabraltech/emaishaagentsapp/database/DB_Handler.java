package com.cabraltech.emaishaagentsapp.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cabraltech.emaishaagentsapp.MyApp;


/**
 * DB_Handler contains Database Version and Name and creates/upgrades all tables in the Database
 **/


public class DB_Handler extends SQLiteOpenHelper {

    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    private static final String DB_NAME = "User_DB";


    public DB_Handler() {
        super(MyApp.getContext(), DB_NAME, null, DB_VERSION);
    }


    
    //*********** Creating Database ********//
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating Tables
        db.execSQL(User_Info_DB.createTable());
    }


    
    //*********** Upgrading Database ********//
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + User_Info_DB.TABLE_USER_INFO);

        // Create tables again
        onCreate(db);
    }

}
