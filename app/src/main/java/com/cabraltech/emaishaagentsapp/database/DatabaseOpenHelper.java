package com.cabraltech.emaishaagentsapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseOpenHelper";
    public static final String DATABASE_NAME = "emaisha_agent_db.db";
    private static final int DATABASE_VERSION = 2;
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;

        String agro_input_dealers_query = " CREATE TABLE IF NOT EXISTS " + "agro_input_dealers" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "business_name" + " TEXT, " + "district" + " TEXT NOT NULL, " + "sub_county" + " TEXT NOT NULL, " + "village" + " TEXT, " + "full_address" + " TEXT, " +
                "certification_type" + " TEXT NOT NULL, " + "certification_number" + " TEXT NOT NULL, " + "sync_status" + " TEXT DEFAULT 0, " + "registration_body" + " TEXT , " + "registration_year" + " TEXT  ," + "registration_status" + " TEXT ,"
                + "association_membership" + " TEXT, " + "association_name" + " TEXT NOT NULL, " + "business_type" + " TEXT NOT NULL, " + "number_of_outlets" + " TEXT, "
                + "types_of_sales" + " TEXT NOT NULL, " + "items_sold" + " TEXT NOT NULL, " + "marketing_channels" + " TEXT , " + "funding_source" + " TEXT , " + "additional_services" + " TEXT, " + "owner" + " TEXT NOT NULL, " + "owner_contact" + " TEXT NOT NULL" +
                " ) ";

        String agro_traders_query = " CREATE TABLE IF NOT EXISTS " + "agro_traders" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "business_name" + " TEXT, " + "owner" + " TEXT NOT NULL, " + "commodities" + " TEXT NOT NULL, " + "phone_number" + " TEXT, " +
                "email_address" + " TEXT , " + "district" + " TEXT NOT NULL, " + "sub_county" + " TEXT , " + "full_address" + " TEXT , " + "sync_status" + " TEXT DEFAULT 0 ," + "business_type" + " TEXT ,"
                + "supply_source" + " TEXT DEFAULT 'none', " + "supplier_location" + " TEXT DEFAULT 'none', " + "funding_source" + " TEXT DEFAULT 'none', " + "marketing_channels" + " TEXT DEFAULT 'none', "
                + "village" + " TEXT NOT NULL " +
                " ) ";

        String associations_query = " CREATE TABLE IF NOT EXISTS " + "associations" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "name" + " TEXT, " + "year_of_registration" + " REAL, " + "district" + " TEXT NOT NULL, " + "sub_county" + " TEXT, " +
                "village" + " TEXT NOT NULL, " + "full_address" + " TEXT NOT NULL, " + "association_telephone" + " TEXT , " + "association_email" + " TEXT , " + "number_of_male_members" + " TEXT  ," + "crop_value_chain" + " TEXT ,"
                + "livestock_value_chain" + " TEXT, " + "chairperson" + " TEXT NOT NULL, " + "chairperson_contact" + " TEXT NOT NULL, " + "secretary" + " TEXT, "
                + "secretary_contact" + " TEXT NOT NULL, " + "sync_status" + " TEXT DEFAULT 0, " + "number_of_female_members" + " TEXT NOT NULL, " + "organisation_type" + " TEXT NOT NULL, " + "registration_level" + " TEXT, " +
                "respondent" + " TEXT NOT NULL, " + "respondent_contact" + " TEXT, " + "main_activities" + " TEXT DEFAULT 'none', " + "asset_ownership" + " TEXT  DEFAULT 'none', " + "market" + " TEXT, " +
                "marketing_channels" + " TEXT  DEFAULT 'none', " + "funding_source" + " TEXT  DEFAULT 'none', " + "additional_services" + " TEXT  DEFAULT 'none'" +
                " ) ";

        String farmers_query = " CREATE TABLE IF NOT EXISTS " + "farmers" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "first_name" + " TEXT, " + "last_name" + " TEXT, " + "dob" + " TEXT NOT NULL, " + "age" + " TEXT, " +
                "gender" + " TEXT NOT NULL, " + "nationality" + " TEXT NOT NULL, " + "religion" + " TEXT , " + "level_of_education" + " TEXT , " + "marital_status" + " TEXT  ," + "household_size" + " TEXT ,"
                + "language_used" + " TEXT, " + "source_of_income" + " TEXT NOT NULL, " + "household_head" + " TEXT NOT NULL, " + "district" + " TEXT, "
                + "sub_county" + " TEXT NOT NULL, " + "village" + " TEXT , " + "phone_number" + " TEXT NOT NULL, " + "next_of_kin" + " TEXT NOT NULL, " + "next_of_kin_relation" + " TEXT, " +
                "next_of_kin_contact" + " TEXT," + "next_of_kin_address" + " TEXT NOT NULL, " + "farming_land_size" + " TEXT, " + "main_crop" + " TEXT, " + "second_crop" + " TEXT, " + "third_crop" + " TEXT, " +
                "main_livestock" + " TEXT, " + "second_livestock" + " TEXT, " + "sync_status" + " TEXT DEFAULT 0, " + "nin" + " TEXT " +
                " ) ";

        String market_prices_query = " CREATE TABLE IF NOT EXISTS " + "market_prices" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "date" + " TEXT, " + "variety" + " TEXT, " + "market" + " TEXT NOT NULL, " + "measurement_units" + " TEXT, " +
                "wholesale_price" + " TEXT NOT NULL, " + "retail_price" + " TEXT NOT NULL, " + "sync_status" + " TEXT DEFAULT 0, " + "commodity" + " TEXT  " +
                " ) ";

        String markets_query = " CREATE TABLE IF NOT EXISTS " + "markets" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "name" + " TEXT, " + "street_address" + " TEXT, " + "district" + " TEXT NOT NULL, " + "sub_county" + " TEXT, " +
                "town" + " TEXT NOT NULL, " + "contact_person" + " TEXT NOT NULL, " + "phone_number" + " TEXT , " + "sync_status" + " TEXT DEFAULT 0 " +
                " ) ";

        String pest_reports_query = " CREATE TABLE IF NOT EXISTS " + "pest_reports" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "date" + " TEXT, " + "district" + " TEXT, " + "sub_county" + " TEXT NOT NULL, " + "village" + " TEXT, " +
                "farmer_phone" + " TEXT NOT NULL, " + "signs_and_symptoms" + " TEXT NOT NULL, " + "suspected_pest" + " TEXT , " + "damage_assesment" + " TEXT , " + "recommendation" + " TEXT  ," + "photo_of_damage" + " TEXT ,"
                + "sync_status" + " TEXT DEFAULT 0, " + "farmer_name" + " TEXT NOT NULL " +
                " ) ";

        String regions_query = " CREATE TABLE IF NOT EXISTS " + "regions" + " ( " + "table_id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "id" + " TEXT, " + "regionType" + " TEXT, " + "region" + " TEXT NOT NULL, " + "belongs_to" + " TEXT " +
                " ) ";

        String scouting_reports_query = " CREATE TABLE IF NOT EXISTS " + "scouting_reports" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "date" + " TEXT, " + "farmer_name" + " TEXT, " + "district" + " TEXT NOT NULL, " + "sub_county" + " TEXT, " +
                "village" + " TEXT NOT NULL, " + "phone_number" + " TEXT NOT NULL, " + "infested" + " TEXT , " + "infestation_type" + " TEXT , " + "infestation" + " TEXT  ," + "infestation_level" + " TEXT ,"
                + "recommendation" + " TEXT , " + "sync_status" + " TEXT DEFAULT 0" +
                " ) ";

        String online_markets_query = " CREATE TABLE IF NOT EXISTS " + "online_markets" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "market_id" + " TEXT NOT NULL, " + "market_name" + " TEXT NOT NULL" + " ) ";

        String total_entries_query = " CREATE TABLE IF NOT EXISTS " + "total_entries" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "farmers_count" + " INTEGER , "+ "association_count" + " INTEGER , " + "agro_input_count" + " INTEGER, " + "bulk_buyer_count" + " INTEGER ,"+ "market_count" + " INTEGER, "+ "market_price_count" + " INTEGER, "+ "pest_report_count" + " INTEGER, "+ "scouting_report_count" + " INTEGER " + " ) ";

        sqLiteDatabase.execSQL(agro_input_dealers_query);
        sqLiteDatabase.execSQL(agro_traders_query);
        sqLiteDatabase.execSQL(associations_query);
        sqLiteDatabase.execSQL(farmers_query);
        sqLiteDatabase.execSQL(market_prices_query);
        sqLiteDatabase.execSQL(markets_query);
        sqLiteDatabase.execSQL(pest_reports_query);
        sqLiteDatabase.execSQL(regions_query);
        sqLiteDatabase.execSQL(scouting_reports_query);
        sqLiteDatabase.execSQL(online_markets_query);
        sqLiteDatabase.execSQL(total_entries_query);

        Log.d(TAG, "onCreate: successfully created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            upGradingTablesFromVersion1ToVersion2(sqLiteDatabase);


        }
        onCreate(sqLiteDatabase);
    }
    public void upGradingTablesFromVersion1ToVersion2(SQLiteDatabase db) {
        sqLiteDatabase = db;
        if (!columnExistsInTable(db,"farmers","next_of_kin_contact")) {
            db.execSQL("ALTER TABLE " + "farmers" + " ADD COLUMN  " + "next_of_kin_contact " + " TEXT");

        }
        if (!columnExistsInTable(db,"agro_input_dealers","full_address")) {
            db.execSQL(" ALTER TABLE " + "agro_input_dealers" + " ADD COLUMN " + "full_address " + " TEXT ");
        }
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + "total_entries" + " ( " + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "farmers_count" + " INTEGER , "+ "association_count" + " INTEGER , " + "agro_input_count" + " INTEGER, " + "bulk_buyer_count" + " INTEGER ,"+ "market_count" + " INTEGER, "+ "market_price_count" + " INTEGER, "+ "pest_report_count" + " INTEGER, "+ "scouting_report_count" + " INTEGER " + " ) "
                    );

    }
    public static boolean columnExistsInTable(SQLiteDatabase db, String table, String columnToCheck) {
        Cursor cursor = null;
        try {
            //query a row. don't acquire db lock
            cursor = db.rawQuery("SELECT * FROM " + table + " LIMIT 0", null);

            // getColumnIndex()  will return the index of the column
            //in the table if it exists, otherwise it will return -1
            if (cursor.getColumnIndex(columnToCheck) != -1) {
                //great, the column exists
                return true;
            }else {
                //sorry, the column does not exist
                return false;
            }

        } catch (SQLiteException Exp) {
            //Something went wrong with SQLite.
            //If the table exists and your query was good,
            //the problem is likely that the column doesn't exist in the table.
            return false;
        } finally {

            //close the cursor
            if (cursor != null) cursor.close();
        }
    }


}
