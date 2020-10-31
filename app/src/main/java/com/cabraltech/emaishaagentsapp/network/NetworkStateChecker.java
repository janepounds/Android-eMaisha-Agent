package com.cabraltech.emaishaagentsapp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class NetworkStateChecker extends BroadcastReceiver {
    private static final String TAG = "NetworkStateChecker";
    private Context context;
    private List<HashMap<String, String>> farmersList, pestReport, scoutingReport, marketPrices, marketDetails, association, agroInputDealers,bulkBuyers;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        farmersList = databaseAccess.getUnSyncedFarmers();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            for (int i = 0; i < farmersList.size(); i++) {
                saveFarmersList(
                        farmersList.get(i).get("first_name"),
                        farmersList.get(i).get("last_name"),
                        farmersList.get(i).get("dob"),
                        farmersList.get(i).get("age"),
                        farmersList.get(i).get("gender"),
                        farmersList.get(i).get("nationality"),
                        farmersList.get(i).get("religion"),
                        farmersList.get(i).get("level_of_education"),
                        farmersList.get(i).get("marital_status"),
                        farmersList.get(i).get("household_size"),
                        farmersList.get(i).get("language_used"),
                        farmersList.get(i).get("source_of_income"),
                        farmersList.get(i).get("household_head"),
                        farmersList.get(i).get("district"),
                        farmersList.get(i).get("sub_county"),
                        farmersList.get(i).get("village"),
                        farmersList.get(i).get("phone_number"),
                        farmersList.get(i).get("next_of_kin"),
                        farmersList.get(i).get("next_of_kin_relation"),
                        farmersList.get(i).get("next_of_kin_contact"),
                        farmersList.get(i).get("next_of_kin_address"),
                        farmersList.get(i).get("farming_land_size"),
                        farmersList.get(i).get("main_crop"),
                        farmersList.get(i).get("second_crop"),
                        farmersList.get(i).get("third_crop"),
                        farmersList.get(i).get("main_livestock"),
                        farmersList.get(i).get("second_livestock")


                );
            }
            pestReport = databaseAccess.getUnSyncedPestReports();
            for (int i = 0; i < pestReport.size(); i++) {
                savePestReports(
                        pestReport.get(i).get("date"),
                        pestReport.get(i).get("district"),
                        pestReport.get(i).get("sub_county"),
                        pestReport.get(i).get("village"),
                        pestReport.get(i).get("farmer_phone"),
                        pestReport.get(i).get("signs_and_symptoms"),
                        pestReport.get(i).get("suspected_pest"),
                        pestReport.get(i).get("damage_assesment"),
                        pestReport.get(i).get("recommendation"),
                        pestReport.get(i).get("photo_of_damage"),
                        pestReport.get(i).get("farmer_name")

                );
            }
            scoutingReport = databaseAccess.getUnSyncedScoutingReports();
            for (int i = 0; i < scoutingReport.size(); i++) {
                saveScoutingReport(
                        scoutingReport.get(i).get("date"),
                        scoutingReport.get(i).get("farmer_name"),
                        scoutingReport.get(i).get("district"),
                        scoutingReport.get(i).get("sub_county"),
                        scoutingReport.get(i).get("village"),
                        scoutingReport.get(i).get("phone_number"),
                        scoutingReport.get(i).get("infested"),
                        scoutingReport.get(i).get("infestation_type"),
                        scoutingReport.get(i).get("infestation"),
                        scoutingReport.get(i).get("infestation_level"),
                        scoutingReport.get(i).get("recommendation")

                );


            }
            marketPrices = databaseAccess.getUnSyncedMarketprices();
            for (int i = 0; i < marketPrices.size(); i++) {
                saveMarketPrices(
                        marketPrices.get(i).get("date"),
                        marketPrices.get(i).get("variety"),
                        marketPrices.get(i).get("market"),
                        marketPrices.get(i).get("measurement_units"),
                        marketPrices.get(i).get("wholesale_price"),
                        marketPrices.get(i).get("retail_price")
                );


            }

            marketDetails = databaseAccess.getUnSyncedMarkets();
            for (int i = 0; i < marketDetails.size(); i++) {
                saveMarket(
                        marketDetails.get(i).get("name"),
                        marketDetails.get(i).get("street_address"),
                        marketDetails.get(i).get("district"),
                        marketDetails.get(i).get("sub_county"),
                        marketDetails.get(i).get("town"),
                        marketDetails.get(i).get("contact_person"),
                        marketDetails.get(i).get("phone_number")
                );


            }
            association = databaseAccess.getUnSyncedAssociations();
            for (int i = 0; i < association.size(); i++) {
                saveAssociation(
                        association.get(i).get("name"),
                        association.get(i).get("year_of_registration"),
                        association.get(i).get("district"),
                        association.get(i).get("sub_county"),
                        association.get(i).get("village"),
                        association.get(i).get("full_address"),
                        association.get(i).get("association_telephone"),
                        association.get(i).get("association_email"),
                        association.get(i).get("number_of_male_members"),
                        association.get(i).get("crop_value_chain"),
                        association.get(i).get("livestock_value_chain"),
                        association.get(i).get("main_source_of_funding"),
                        association.get(i).get("chairperson"),
                        association.get(i).get("chairperson_contact"),
                        association.get(i).get("secretary"),
                        association.get(i).get("secretary_contact"),
                        association.get(i).get("number_of_female_members"),
                        association.get(i).get("organisation_type"),
                        association.get(i).get("registration_level"),
                        association.get(i).get("respondent"),
                        association.get(i).get("respondent_contact"),
                        association.get(i).get("main_activities"),
                        association.get(i).get("asset_ownership"),
                        association.get(i).get("market"),
                        association.get(i).get("marketing_channels"),
                        association.get(i).get("funding_source"),
                        association.get(i).get("additional_services")


                );


            }
            agroInputDealers = databaseAccess.getUnSyncedDealers();
            for (int i = 0; i < agroInputDealers.size(); i++) {
                saveAgroInputDealer(
                        agroInputDealers.get(i).get("business_name"),
                        agroInputDealers.get(i).get("district"),
                        agroInputDealers.get(i).get("sub_county"),
                        agroInputDealers.get(i).get("village"),
                        agroInputDealers.get(i).get("full_address"),
                        agroInputDealers.get(i).get("certification"),
                        agroInputDealers.get(i).get("certification_number"),
                        agroInputDealers.get(i).get("registration_body"),
                        agroInputDealers.get(i).get("registration_year"),
                        agroInputDealers.get(i).get("registration_status"),
                        agroInputDealers.get(i).get("association_membership"),
                        agroInputDealers.get(i).get("association_name"),
                        agroInputDealers.get(i).get("business_type"),
                        agroInputDealers.get(i).get("number_of_outlets"),
                        agroInputDealers.get(i).get("types_of_sales"),
                        agroInputDealers.get(i).get("items_sold"),
                        agroInputDealers.get(i).get("marketing_channels"),
                        agroInputDealers.get(i).get("funding_source"),
                        agroInputDealers.get(i).get("additional_services")

                );


            }



        }

    }

    private void saveFarmersList(
            String first_name,
            String last_name,
            String dob,
            String age,
            String gender,
            String nationality,
            String religion,
            String level_of_education,
            String marital_status,
            String household_size,
            String language_used,
            String source_of_income,
            String household_head,
            String district,
            String sub_county,
            String village,
            String phone_number,
            String next_of_kin,
            String next_of_kin_relation,
            String next_of_kin_contact,
            String next_of_kin_address,
            String farming_land_size,
            String main_crop,
            String second_crop,
            String third_crop,
            String main_livestock,
            String second_livestock
    ) {
        Call<ResponseBody> call = APIClient.getInstance()
                .postFarmersList(
                        first_name, last_name, dob, Integer.parseInt(age), gender, nationality, religion, level_of_education, marital_status, Integer.parseInt(household_size), language_used, source_of_income,
                        household_head, district, sub_county, village, phone_number, next_of_kin, next_of_kin_relation, next_of_kin_contact, next_of_kin_address, Integer.parseInt(farming_land_size),
                        main_crop, second_crop, third_crop, main_livestock, second_livestock

                );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Farmers List Sync", "Farmer List Synced");
                    //update synced status
//                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
//                    databaseAccess.open();
//                    databaseAccess.updateFarmerSyncStatus()
                } else {
                    Log.d("Farmers Sync Failure", "Farmers List Synced Failed");
                    Log.d("Error", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Farmers Sync Failure", "Farmers List Synced Failed");
                t.printStackTrace();
            }
        });
    }

    private void savePestReports(String date, String district, String sub_county, String village, String farmer_phone, String signs_and_symptoms, String suspected_pest,
                                 String damage_assesment, String recommendation, String photo_f_damage, String farmer_name) {
        Call<ResponseBody> call = APIClient.getInstance()
                .postPestReport(date, district, sub_county, village, farmer_phone, signs_and_symptoms, suspected_pest, damage_assesment, recommendation, photo_f_damage, farmer_name
                );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Pest Report Sync", "Pest Report Synced");

                    //update status locally
                } else {
                    Log.d("PestReport Sync Failure", "Pest Report Sync Failed");
                    Log.d("Error", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("PestReport Sync Failure", "Pest Report Sync failed");

            }
        });

    }

    private void saveScoutingReport(String date, String farmer_name, String district, String sub_county, String village, String phone_number, String infested, String infestation_type,
                                    String infestation, String infestation_level, String recommendation) {
        Call<ResponseBody> call = APIClient.getInstance()
                .postScoutingReport(date, farmer_name, district, sub_county, village, phone_number, infested, infestation_type, infestation, infestation_level, recommendation
                );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Scouting Report Sync", "Scouting Report Synced");

                    //update status locally
                } else {
                    Log.d("Scouting Sync Failure", "Scouting Report Sync Failed");
                    Log.d("Error", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Scouting Sync Failure", "Scouting Report Sync failed");
            }
        });


    }

    private void saveMarketPrices(String date, String variety, String market, String measurement_units, String wholesale_price, String retail_price) {
        Call<ResponseBody> call = APIClient.getInstance()
                .postMarketPrice(date, variety, market, measurement_units, Integer.parseInt(wholesale_price), Integer.parseInt(retail_price));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Market Price Sync", "Market prices Synced");

                    //update status locally
                } else {
                    Log.d("Market Price Failure", "Market Prices Sync Failed");
                    Log.d("Error", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Market Price Failure", "Market Price Sync failed");
            }
        });


    }

    private void saveMarket(String name, String street_adress, String district, String sub_county, String town, String contact_person, String phone_number) {
        Call<ResponseBody> call = APIClient.getInstance()
                .postMarketDetails(name, street_adress, district, sub_county, town, contact_person, phone_number);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Market Sync", "Market  Synced");

                    //update status locally
                } else {
                    Log.d("Market sync Failure", "Market Sync Failed");
                    Log.d("Error", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Market sync Failure", "Market  Sync failed");
            }
        });

    }

    private void saveAssociation(
            String name, String year_of_registration, String district, String sub_county, String village, String full_address, String association_telephone,
            String association_email, String number_of_male_members, String crop_value_chain, String livestock_value_chain, String main_source_of_funding, String chairperson,
            String chairperson_contact, String secretary, String secretary_contact, String number_of_female_members, String organisation_type, String registration_level,
            String respondent, String respondent_contact, String main_activities, String asset_ownership, String market, String funding_source, String marketing_channels, String additional_services) {
        Call<ResponseBody> call = APIClient.getInstance()
                .postAssociation(name, Integer.parseInt(year_of_registration), district, sub_county, village, full_address,association_telephone, association_email, Integer.parseInt(number_of_male_members), crop_value_chain,
                        livestock_value_chain, main_source_of_funding, chairperson, chairperson_contact, secretary,secretary_contact, Integer.parseInt(number_of_female_members), organisation_type,
                        registration_level, respondent, respondent_contact, main_activities, asset_ownership, market, funding_source, marketing_channels, additional_services);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Association Sync", "Association  Synced");

                    //update status locally
                } else {
                    Log.d("Assoc sync Failure", "AAssociation sync Failed");
                    Log.d("Error", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Assoc sync Failure", "Association Sync failed");
            }
        });

    }

    private void saveAgroInputDealer(String business_name, String district, String sub_county, String village, String full_address, String certification, String certification_number,
                                     String registration_body, String registration_year, String registration_status, String association_membership, String association_name,
                                     String business_type, String number_of_outlets, String types_of_sales, String items_sold, String marketing_channels, String funding_source, String additional_services) {
        Call<ResponseBody> call = APIClient.getInstance()
                .postAgroInputDealer(business_name, district, sub_county, village, full_address, certification, certification_number, registration_body, Integer.parseInt(registration_year), registration_status, association_membership, association_name, business_type, Integer.parseInt(number_of_outlets), types_of_sales, items_sold, marketing_channels, funding_source, additional_services);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("AgroInput Sync", "Agro Input Dealer  Synced");

                    //update status locally
                } else {
                    Log.d("AgroInput sync Failure", "Agro Input Dealer Failed");
                    Log.d("Error", String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("AgroInput sync Failure", "Agro Input Dealer   Sync failed");
            }
        });


    }
}
