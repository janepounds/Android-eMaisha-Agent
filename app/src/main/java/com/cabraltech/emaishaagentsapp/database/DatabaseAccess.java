package com.cabraltech.emaishaagentsapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cabraltech.emaishaagentsapp.models.RegionDetails;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }





    private int getFarmerID(String id) {
        this.database = openHelper.getWritableDatabase();
        Cursor c = database.query("farmers", new String[]{"id"}, "id =? ", new String[]{id}, null, null, null, null);
        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("id"));

        c.close();
        return -1;
    }


    //insert farmer
    public boolean addFarmer(String first_name, String last_name, String dob, String age, String gender, String nationality, String religion, String level_of_education, String marital_status, String household_size, String language_used, String source_of_income, String household_head, String district, String sub_county, String village, String phone_number, String next_of_kin, String next_of_kin_relation, String next_of_kin_contact, String next_of_kin_address, String farming_land_size, String main_crop, String second_crop, String third_crop, String main_livestock, String second_livestock) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("first_name", first_name);
        values.put("last_name", last_name);
        values.put("dob", dob);
        values.put("age", age);
        values.put("gender", gender);
        values.put("nationality", nationality);
        values.put("religion", religion);
        values.put("level_of_education", level_of_education);
        values.put("marital_status", marital_status);
        values.put("household_size", household_size);
        values.put("language_used", language_used);
        values.put("source_of_income", source_of_income);
        values.put("household_head", household_head);
        values.put("district", district);
        values.put("sub_county", sub_county);
        values.put("village", village);
        values.put("phone_number", phone_number);
        values.put("next_of_kin", next_of_kin);
        values.put("next_of_kin_relation", next_of_kin_relation);
        values.put("next_of_kin_contact", next_of_kin_contact);
        values.put("next_of_kin_address", next_of_kin_address);
        values.put("farming_land_size", farming_land_size);
        values.put("main_crop", main_crop);
        values.put("second_crop", second_crop);
        values.put("third_crop", third_crop);
        values.put("main_livestock", main_livestock);
        values.put("second_livestock", second_livestock);
        values.put("sync_status",0);

        long check = database.insert("farmers", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    //////////**************GET LAST ENTERED REGION ID**************///////////////////
    public int getMaxRegionId() {
        this.database = openHelper.getWritableDatabase();

        Cursor cur = database.rawQuery("SELECT MAX(id) FROM  regions", null);
        cur.moveToFirst();

        int regionId = cur.getInt(0);

        // close cursor and DB
        cur.close();
        database.close();

        return regionId;

    }

    ///**********************insert regions**************************//////////////////
    public void insertRegionDetails(List<RegionDetails> regionDetails) {
        this.database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(RegionDetails regionDetail : regionDetails) {
            contentValues.put("id", regionDetail.getId());
            contentValues.put("regionType", regionDetail.getRegionType());
            contentValues.put("region", regionDetail.getRegion());
            contentValues.put("belongs_to", regionDetail.getBelongs_to());
            database.insert("regions", null, contentValues);
        }
        database.close();
    }

    //******GET DISTRICTS*****//

    public ArrayList<RegionDetails> getRegionDetails( String district) throws JSONException {
        this.database = openHelper.getWritableDatabase();
        ArrayList<RegionDetails> array_list = new ArrayList();

        Cursor res = database.rawQuery("select * from regions where  regionType = '" + district + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setTableId(Integer.parseInt(res.getString(res.getColumnIndex("table_id"))));
            regionDetails.setId(Integer.parseInt(res.getString(res.getColumnIndex("id"))));
            regionDetails.setRegionType(res.getString(res.getColumnIndex("regionType")));
            regionDetails.setRegion(res.getString(res.getColumnIndex("region")));
            regionDetails.setBelongs_to(res.getString(res.getColumnIndex("belongs_to")));
            array_list.add(regionDetails);
            res.moveToNext();
        }

        res.close();
        database.close();
        Log.d("RegionDetails ", array_list.size() + "");

        return array_list;
    }

    //******GET SUB COUNTY*****//

    public ArrayList<RegionDetails> getSubcountyDetails( String belongs_to, String subcounty) throws JSONException {
        this.database = openHelper.getWritableDatabase();
        ArrayList<RegionDetails> array_list = new ArrayList();

        Cursor res = database.rawQuery("select * from regions where  belongs_to   = '" + belongs_to + "'" + " AND  regionType  = '" + subcounty + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setTableId(Integer.parseInt(res.getString(res.getColumnIndex("table_id"))));
            regionDetails.setId(Integer.parseInt(res.getString(res.getColumnIndex("id"))));
            regionDetails.setRegionType(res.getString(res.getColumnIndex("regionType")));
            regionDetails.setRegion(res.getString(res.getColumnIndex("region")));
            regionDetails.setBelongs_to(res.getString(res.getColumnIndex("belongs_to")));
            array_list.add(regionDetails);
            res.moveToNext();
        }

        res.close();
        database.close();
        Log.d("RegionDetails ", array_list.size() + "");

        return array_list;
    }

    //******GET VILLAGES*****//

    public ArrayList<RegionDetails> getVillageDetails( String belongs_to, String subcounty) throws JSONException {
        this.database = openHelper.getWritableDatabase();
        ArrayList<RegionDetails> array_list = new ArrayList();

        Cursor res = database.rawQuery("select * from regions where  belongs_to   = '" + belongs_to + "'" + " AND  regionType  = '" + subcounty + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setTableId(Integer.parseInt(res.getString(res.getColumnIndex("table_id"))));
            regionDetails.setId(Integer.parseInt(res.getString(res.getColumnIndex("id"))));
            regionDetails.setRegionType(res.getString(res.getColumnIndex("regionType")));
            regionDetails.setRegion(res.getString(res.getColumnIndex("region")));
            regionDetails.setBelongs_to(res.getString(res.getColumnIndex("belongs_to")));
            array_list.add(regionDetails);
            res.moveToNext();
        }

        res.close();
        database.close();
        Log.d("RegionDetails ", array_list.size() + "");

        return array_list;
    }


    //update farmer sync status
    public boolean updateFarmerSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("farmers", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete farmer
    public boolean deleteFarmer(String id) {

        this.database = openHelper.getWritableDatabase();

        long check = -1;
        check = database.delete("farmers",  "id = ?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }


    //get un synced farmers
    public ArrayList<HashMap<String, String>> getUnSyncedFarmers() {
        ArrayList<HashMap<String, String>> farmers = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM farmers WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",cursor.getString(0));
                map.put("first_name", cursor.getString(1));
                map.put("last_name", cursor.getString(2));
                map.put("dob", cursor.getString(3));
                map.put("age", cursor.getString(4));
                map.put("gender", cursor.getString(5));
                map.put("nationality", cursor.getString(6));
                map.put("religion", cursor.getString(7));
                map.put("level_of_education", cursor.getString(8));
                map.put("marital_status", cursor.getString(9));
                map.put("household_size", cursor.getString(10));
                map.put("language_used", cursor.getString(11));
                map.put("source_of_income", cursor.getString(12));
                map.put("household_head", cursor.getString(13));
                map.put("district", cursor.getString(14));
                map.put("sub_county", cursor.getString(15));
                map.put("village", cursor.getString(16));
                map.put("phone_number", cursor.getString(17));
                map.put("next_of_kin", cursor.getString(18));
                map.put("next_of_kin_relation", cursor.getString(19));
                map.put("next_of_kin_contact", cursor.getString(20));
                map.put("next_of_kin_address", cursor.getString(21));
                map.put("farming_land_size", cursor.getString(22));
                map.put("main_crop", cursor.getString(23));
                map.put("second_crop", cursor.getString(24));
                map.put("third_crop", cursor.getString(25));
                map.put("main_livestock", cursor.getString(26));
                map.put("second_livestock", cursor.getString(27));

                farmers.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return farmers;
    }

    //insert association
    public boolean addAssociation(String name, String year_of_registration, String district, String sub_county, String village, String full_address, String telephone, String email, String crop_chain_value, String livestock_chain_value, String chairperson, String chairperson_contact, String vice_chairperson, String vice_chairperson_contact, String number_of_male_members, String number_of_female_members, String registration_level, String respondent, String respondent_contact, String asset_ownership, String organisation_type, String main_activities, String market, String market_channels, String funding_source, String additional_services) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("name", name);
        values.put("year_of_registration", year_of_registration);
        values.put("district", district);
        values.put("sub_county", sub_county);
        values.put("village", village);
        values.put("full_address", full_address);
        values.put("association_telephone", telephone);
        values.put("association_email", email);
        values.put("number_of_female_members", number_of_female_members);
        values.put("number_of_male_members", number_of_male_members);
        values.put("crop_value_chain", crop_chain_value);
        values.put("livestock_value_chain", livestock_chain_value);
        values.put("chairperson", chairperson);
        values.put("chairperson_contact", chairperson_contact);
        values.put("secretary", vice_chairperson);
        values.put("secretary_contact", vice_chairperson_contact);
        values.put("organisation_type", organisation_type);
        values.put("registration_level", registration_level);
        values.put("respondent", respondent);
        values.put("respondent_contact", respondent_contact);
        values.put("main_activities", main_activities);
        values.put("asset_ownership", asset_ownership);
        values.put("market", market);
        values.put("marketing_channels", market_channels);
        values.put("funding_source", funding_source);
        values.put("additional_services", additional_services);
        values.put("sync_status", 0);


        long check = database.insert("associations", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    //get un synced associations
    public ArrayList<HashMap<String, String>> getUnSyncedAssociations() {
        ArrayList<HashMap<String, String>> associations = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM associations WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("year_of_registration", cursor.getString(2));
                map.put("district", cursor.getString(3));
                map.put("sub_county", cursor.getString(4));
                map.put("village", cursor.getString(5));
                map.put("full_address", cursor.getString(6));
                map.put("association_telephone", cursor.getString(7));
                map.put("association_email", cursor.getString(8));
                map.put("number_of_male_members", cursor.getString(9));
                map.put("crop_value_chain", cursor.getString(10));
                map.put("livestock_value_chain", cursor.getString(11));
                map.put("chairperson", cursor.getString(12));
                map.put("chairperson_contact", cursor.getString(13));
                map.put("secretary", cursor.getString(14));
                map.put("secretary_contact", cursor.getString(15));
                map.put("number_of_female_members", cursor.getString(17));
                map.put("organisation_type", cursor.getString(18));
                map.put("registration_level", cursor.getString(19));
                map.put("respondent", cursor.getString(20));
                map.put("respondent_contact", cursor.getString(21));
                map.put("main_activities", cursor.getString(22));
                map.put("asset_ownership", cursor.getString(23));
                map.put("market", cursor.getString(24));
                map.put("marketing_channels", cursor.getString(25));
                map.put("funding_source", cursor.getString(26));
                map.put("additional_services", cursor.getString(27));


                associations.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return associations;
    }

    //update association sync status
    public boolean updateAssociationSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("associations", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete association
    public boolean deleteAssociation(String id) {

        this.database = openHelper.getWritableDatabase();

        long check = -1;
        check = database.delete("associations", "id = ?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    //insert dealer
    public boolean addDealer(String name, String district, String sub_county, String village, String full_address,  String certification_type, String certification_number, String registration_body, String registration_year, String registration_status, String association_membership, String association_name, String business_type, String number_of_outlets, String types_of_sales, String items_sold, String marketing_channels, String funding_source, String additional_services,String owner,String owner_contact) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("business_name", name);
        values.put("district", district);
        values.put("sub_county", sub_county);
        values.put("village", village);
        values.put("full_address", full_address);
        values.put("certification_type", certification_type);
        values.put("certification_number", certification_number);
        values.put("registration_body", registration_body);
        values.put("registration_year", registration_year);
        values.put("registration_status", registration_status);
        values.put("association_membership", association_membership);
        values.put("association_name", association_name);
        values.put("business_type", business_type);
        values.put("number_of_outlets", number_of_outlets);
        values.put("types_of_sales", types_of_sales);
        values.put("items_sold", items_sold);
        values.put("marketing_channels", marketing_channels);
        values.put("funding_source", funding_source);
        values.put("additional_services", additional_services);
        values.put("owner", owner);
        values.put("owner_contact", owner_contact);
        values.put("sync_status", 0);
        long check = database.insert("agro_input_dealers", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    //get un synced dealers
    public ArrayList<HashMap<String, String>> getUnSyncedDealers() {
        ArrayList<HashMap<String, String>> agro_dealers = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM agro_input_dealers WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",cursor.getString(0));
                map.put("business_name", cursor.getString(1));
                map.put("district", cursor.getString(2));
                map.put("sub_county", cursor.getString(3));
                map.put("village", cursor.getString(4));
                map.put("full_address", cursor.getString(5));
                map.put("certification_type", cursor.getString(7));
                map.put("certification_number", cursor.getString(8));
                map.put("registration_body", cursor.getString(10));
                map.put("registration_year", cursor.getString(11));
                map.put("registration_status", cursor.getString(12));
                map.put("association_membership", cursor.getString(13));
                map.put("association_name", cursor.getString(14));
                map.put("business_type", cursor.getString(15));
                map.put("number_of_outlets", cursor.getString(16));
                map.put("types_of_sales", cursor.getString(17));
                map.put("items_sold", cursor.getString(18));
                map.put("marketing_channels", cursor.getString(19));
                map.put("funding_source", cursor.getString(20));
                map.put("additional_services", cursor.getString(21));
                map.put("owner", cursor.getString(22));
                map.put("owner_contact", cursor.getString(23));
                agro_dealers.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return agro_dealers;
    }


    //update dealer sync status
    public boolean updateDealerSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("agro_input_dealers", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete agro dealer input
    public boolean deleteAgroInputDealer(String id) {

        this.database = openHelper.getWritableDatabase();

        long check = -1;
        check = database.delete("agro_input_dealers", "id = ?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    //insert trader
    public boolean addTrader(String business_type, String business_name,String owner, String commodities, String phone_number, String email_address, String district, String sub_county, String village, String actual_address, String supplier_location, String supply_source, String funding_source, String marketing_channels) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("business_type", business_type);
        values.put("business_name", business_name);
        values.put("owner", owner);
        values.put("commodities", commodities);
        values.put("phone_number", phone_number);
        values.put("email_address", email_address);
        values.put("district", district);
        values.put("sub_county", sub_county);
        values.put("village", village);
        values.put("supply_source", supply_source);
        values.put("supplier_location", supplier_location);
        values.put("funding_source", funding_source);
        values.put("marketing_channels", marketing_channels);
        values.put("full_address", actual_address);
        values.put("sync_status", 0);
        long check = database.insert("agro_traders", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    //get un synced traders
    public ArrayList<HashMap<String, String>> getUnSyncedTrader() {
        ArrayList<HashMap<String, String>> agro_traders = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM agro_traders WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("business_name", cursor.getString(1));
                map.put("owner", cursor.getString(2));
                map.put("commodities", cursor.getString(3));
                map.put("phone_number", cursor.getString(4));
                map.put("email_address", cursor.getString(5));
                map.put("district", cursor.getString(6));
                map.put("sub_county", cursor.getString(7));
                map.put("full_address", cursor.getString(8));
                map.put("business_type", cursor.getString(10));
                map.put("supply_source", cursor.getString(11));
                map.put("supplier_location", cursor.getString(12));
                map.put("marketing_channels", cursor.getString(13));
                map.put("village", cursor.getString(9));
                agro_traders.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return agro_traders;
    }


    //update trader sync status
    public boolean updateTraderSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("agro_traders", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }


    //insert market
    public boolean addMarket(String name, String street_address, String phone_number, String district, String sub_county, String village, String contact_person) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("name", name);
        values.put("street_address", street_address);
        values.put("phone_number", phone_number);
        values.put("district", district);
        values.put("sub_county", sub_county);
        values.put("town", village);
        values.put("contact_person", contact_person);
        values.put("sync_status", 0);
        long check = database.insert("markets", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    //get un synced traders
    public ArrayList<HashMap<String, String>> getUnSyncedMarkets() {
        ArrayList<HashMap<String, String>> markets = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM markets WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("street_address", cursor.getString(2));
                map.put("district", cursor.getString(3));
                map.put("sub_county", cursor.getString(4));
                map.put("town", cursor.getString(5));
                map.put("contact_person", cursor.getString(6));
                map.put("phone_number", cursor.getString(7));
                markets.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return markets;
    }

    //update dealer sync status
    public boolean updateMarketSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("markets", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete market
    public boolean deleteMarket(String id) {

        this.database = openHelper.getWritableDatabase();

        long check = -1;
        check = database.delete("markets", "id = ?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    //insert market price
    public boolean addMarketPrice(String date, String commodities, String variety, String market, String measurement_units, String wholesale_price, String retail_price) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("date", date);
        values.put("variety", variety);
        values.put("market", market);
        values.put("measurement_units", measurement_units);
        values.put("wholesale_price", wholesale_price);
        values.put("retail_price", retail_price);
        values.put("sync_status", 0);
        values.put("commodity", commodities);
        long check = database.insert("market_prices", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    //get un synced market prices
    public ArrayList<HashMap<String, String>> getUnSyncedMarketprices() {
        ArrayList<HashMap<String, String>> market_prices = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM market_prices WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",cursor.getString(0));
                map.put("date", cursor.getString(1));
                map.put("variety", cursor.getString(2));
                map.put("market", cursor.getString(3));
                map.put("measurement_units", cursor.getString(4));
                map.put("wholesale_price", cursor.getString(5));
                map.put("retail_price", cursor.getString(6));
                map.put("commodity", cursor.getString(8));
                market_prices.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return market_prices;
    }

    //update dealer sync status
    public boolean updateMarketPriceSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("market_prices", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete market prices
    public boolean deleteMarketPrice(String id) {

        this.database = openHelper.getWritableDatabase();

        long check = -1;
        check = database.delete("market_prices", "id = ?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    //insert pest reports
    public boolean addPestReport(String date, String name, String district, String sub_county, String village, String farmer_phone, String signs_and_symptoms, String suspected_pest, String damage_assesment, String recommendation, String photo_of_damage) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("date", date);
        values.put("farmer_name", name);
        values.put("district", district);
        values.put("sub_county", sub_county);
        values.put("village", village);
        values.put("farmer_phone", farmer_phone);
        values.put("signs_and_symptoms", signs_and_symptoms);
        values.put("suspected_pest", suspected_pest);
        values.put("damage_assesment", damage_assesment);
        values.put("recommendation", recommendation);
        values.put("photo_of_damage", photo_of_damage);
        values.put("sync_status",0);
        long check = database.insert("pest_reports", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    //get un synced pest reports
    public ArrayList<HashMap<String, String>> getUnSyncedPestReports() {
        ArrayList<HashMap<String, String>> pest_reports = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM pest_reports WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",cursor.getString(0));
                map.put("date", cursor.getString(1));
                map.put("district", cursor.getString(2));
                map.put("sub_county", cursor.getString(3));
                map.put("village", cursor.getString(4));
                map.put("farmer_phone", cursor.getString(5));
                map.put("signs_and_symptoms", cursor.getString(6));
                map.put("suspected_pest", cursor.getString(7));
                map.put("damage_assesment", cursor.getString(8));
                map.put("recommendation", cursor.getString(9));
                map.put("photo_of_damage", cursor.getString(10));
                map.put("farmer_name",cursor.getString(11));
                pest_reports.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return pest_reports;
    }

    //update dealer sync status
    public boolean updatePestReportSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("pest_reports", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete pest report
    public boolean deletePestReport(String id) {

        this.database = openHelper.getWritableDatabase();

        long check = -1;
        check = database.delete("pest_reports",  "id = ?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //insert scouting reports
    public boolean addScoutingReport(String date, String farmer_name, String district, String sub_county, String village, String farmer_phone, String infested, String infestation_type, String infestation, String infestation_level, String recommendation) {
        this.database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();
        values.put("date", date);
        values.put("farmer_name", farmer_name);
        values.put("district", district);
        values.put("sub_county", sub_county);
        values.put("village", village);
        values.put("phone_number", farmer_phone);
        values.put("infested", infested);
        values.put("infestation_type", infestation_type);
        values.put("infestation", infestation);
        values.put("infestation_level", infestation_level);
        values.put("recommendation", recommendation);
        values.put("sync_status", 0);
        long check = database.insert("scouting_reports", null, values);
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    //get un synced market prices
    public ArrayList<HashMap<String, String>> getUnSyncedScoutingReports() {
        ArrayList<HashMap<String, String>> scouting_reports = new ArrayList<>();
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM scouting_reports WHERE sync_status='0'", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",cursor.getString(0));
                map.put("date", cursor.getString(1));
                map.put("farmer_name", cursor.getString(2));
                map.put("district", cursor.getString(3));
                map.put("sub_county", cursor.getString(4));
                map.put("village", cursor.getString(5));
                map.put("phone_number", cursor.getString(6));
                map.put("infested", cursor.getString(7));
                map.put("infestation_type", cursor.getString(8));
                map.put("infestation", cursor.getString(9));
                map.put("infestation_level", cursor.getString(10));
                map.put("recommendation", cursor.getString(11));
                scouting_reports.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return scouting_reports;
    }

    //update dealer sync status
    public boolean updateScoutingReportSyncStatus(String id, String sync_status) {
        ContentValues values = new ContentValues();
        this.database = openHelper.getWritableDatabase();

        values.put("sync_status", sync_status);
        long check = -1;
        check = database.update("scouting_reports", values, "id=?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete scouting report
    public boolean deleteScoutingReport(String id) {

        this.database = openHelper.getWritableDatabase();

        long check = -1;
        check = database.delete("scouting_reports",  "id = ?", new String[]{id});

        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }


}
