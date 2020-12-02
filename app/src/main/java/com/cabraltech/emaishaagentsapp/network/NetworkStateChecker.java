package com.cabraltech.emaishaagentsapp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.models.Market;
import com.cabraltech.emaishaagentsapp.models.MarketResponse;
import com.cabraltech.emaishaagentsapp.models.ResponseData;
import com.cabraltech.emaishaagentsapp.models.authentication.LoginResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
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
    private List<HashMap<String, String>> farmersList, pestReport, scoutingReport, marketPrices, marketDetails, association, agroInputDealers, bulkBuyers,total_entries;
    private String sync_status;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        checkConnectivity(context);
    }

    public void checkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        farmersList = databaseAccess.getUnSyncedFarmers();
        Log.d(TAG, "onCreate: MyApp onCreate" + farmersList);

        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                getMarkets();

                for (int i = 0; i < farmersList.size(); i++) {
                    saveFarmersList(
                            farmersList.get(i).get("id"),
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
                Log.d(TAG, "onCreate: MyApp onCreate" + pestReport);
                for (int i = 0; i < pestReport.size(); i++) {
                    savePestReports(
                            pestReport.get(i).get("id"),
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
                            scoutingReport.get(i).get("id"),
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
                Log.d(TAG, "checkConnectivity: " + marketPrices);
                for (int i = 0; i < marketPrices.size(); i++) {
                    saveMarketPrices(
                            marketPrices.get(i).get("id"),
                            marketPrices.get(i).get("date"),
                            marketPrices.get(i).get("variety"),
                            marketPrices.get(i).get("market"),
                            marketPrices.get(i).get("measurement_units"),
                            marketPrices.get(i).get("wholesale_price"),
                            marketPrices.get(i).get("retail_price"),
                            marketPrices.get(i).get("commodity")
                    );
                }

                marketDetails = databaseAccess.getUnSyncedMarkets();
                for (int i = 0; i < marketDetails.size(); i++) {
                    saveMarket(
                            marketDetails.get(i).get("id"),
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
                            association.get(i).get("id"),
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
                            agroInputDealers.get(i).get("id"),
                            agroInputDealers.get(i).get("business_name"),
                            agroInputDealers.get(i).get("district"),
                            agroInputDealers.get(i).get("sub_county"),
                            agroInputDealers.get(i).get("village"),
                            agroInputDealers.get(i).get("full_address"),
                            agroInputDealers.get(i).get("certification"),
                            agroInputDealers.get(i).get("certification_type"),
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

                bulkBuyers = databaseAccess.getUnSyncedTrader();
                for (int i = 0; i < bulkBuyers.size(); i++) {
                    saveBulkBuyer(
                            bulkBuyers.get(i).get("id"),
                            bulkBuyers.get(i).get("business_name"),
                            bulkBuyers.get(i).get("business_type"),
                            bulkBuyers.get(i).get("owner"),
                            bulkBuyers.get(i).get("phone_number"),
                            bulkBuyers.get(i).get("email_address"),
                            bulkBuyers.get(i).get("district"),
                            bulkBuyers.get(i).get("sub_county"),
                            bulkBuyers.get(i).get("village"),
                            bulkBuyers.get(i).get("full_address"),
                            bulkBuyers.get(i).get("commodities"),
                            bulkBuyers.get(i).get("supply_source"),
                            bulkBuyers.get(i).get("supplier_location"),
                            bulkBuyers.get(i).get("funding_source"),
                            bulkBuyers.get(i).get("marketing_channels")
                    );
                }
            }
        }
    }

    private void getMarkets() {
        Call<MarketResponse> call = APIClient.getInstance().getMarkets();

        call.enqueue(new Callback<MarketResponse>() {
            @Override
            public void onResponse(@NotNull Call<MarketResponse> call, @NotNull Response<MarketResponse> response) {
                if (response.isSuccessful()) {

                    List<Market> markets = response.body().getDataList();
                    for (int i = 0; i < markets.size(); i++) {
                        Market market = markets.get(i);
                        Log.d(TAG, "onResponse: Market Id = " + market.getId());
                        Log.d(TAG, "onResponse: Market Name = " + market.getName());
                    }

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();

                    if (databaseAccess.deleteOnlineMarkets())
                        databaseAccess.addMarkets(response.body().getDataList());

                    ArrayList<String> marketss = new ArrayList<>();
                    marketss = databaseAccess.getOnlineMarkets();
                    Log.d(TAG, "onResponse: Markets = " + marketss);
                }
            }

            @Override
            public void onFailure(@NotNull Call<MarketResponse> call, @NotNull Throwable t) {
                Log.d(TAG, "onResponse: Get markets error " + t.getMessage());
            }
        });
    }

    private void saveFarmersList(String id, String first_name, String last_name, String dob, String age, String gender, String nationality, String religion, String level_of_education, String marital_status,
                                 String household_size, String language_used, String source_of_income, String household_head, String district, String sub_county, String village, String phone_number, String next_of_kin, String next_of_kin_relation,
                                 String next_of_kin_contact, String next_of_kin_address, String farming_land_size, String main_crop, String second_crop, String third_crop, String main_livestock, String second_livestock
    ) {
        Call<ResponseData> call = APIClient.getInstance()
                .postFarmersList(
                        first_name, last_name, dob, Integer.parseInt(age), gender, nationality, religion, level_of_education, marital_status, Integer.parseInt(household_size), language_used, source_of_income,
                        household_head, district, sub_county, village, phone_number, next_of_kin, next_of_kin_relation, next_of_kin_contact, next_of_kin_address, Integer.parseInt(farming_land_size),
                        main_crop, second_crop, third_crop, main_livestock, second_livestock

                );
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d(TAG, "onResponse: " + response.body());
                Log.d(TAG, "onResponse: " + response.message());

                if (response.body().getMessage().equalsIgnoreCase("Successful")) {
                    Log.d(TAG, "Farmer List Synced");

                    //update synced status
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();

                    if (databaseAccess.updateFarmerSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated succesfully");
                        //delete local database copy
                        if (databaseAccess.deleteFarmer(id)) {
                            Log.d(TAG, "onResponse: database record deleted succesfully");

                            //update farmer count
                            total_entries =  databaseAccess.getTotalCount();
                            for (int i = 0; i < total_entries.size(); i++) {
                                int  farmers_count = Integer.parseInt(total_entries.get(i).get("farmers_count"));
                                int association_count = Integer.parseInt(total_entries.get(i).get("association_count"));
                                int agro_input_count = Integer.parseInt(total_entries.get(i).get("agro_input_count"));
                                int bulk_buyer_count = Integer.parseInt(total_entries.get(i).get("bulk_buyer_count"));
                                int market_count = Integer.parseInt(total_entries.get(i).get("market_count"));
                                int market_price_count = Integer.parseInt(total_entries.get(i).get("market_price_count"));
                                int pest_report_count = Integer.parseInt(total_entries.get(i).get("pest_report_count"));
                                int scouting_report_count = Integer.parseInt(total_entries.get(i).get("scouting_report_count"));

                                farmers_count++;

                                boolean check =  databaseAccess.upsert(farmers_count,association_count,agro_input_count,bulk_buyer_count,market_count,market_price_count,pest_report_count,scouting_report_count);
                                if(check){
                                    Log.d(TAG, "onResponse: count added/ updated successfully");
                                    
                                }else{
                                    Log.d(TAG, "onResponse: failed to return count");
                                }

                            }

                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed");
                    }
                } else {
                    Log.d(TAG, "Farmers List Synced Failed");
                    Log.d(TAG, String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d(TAG, "Farmers List Synced Failed");
                t.printStackTrace();
            }
        });
    }

    private void savePestReports(String id, String date, String district, String sub_county, String village, String farmer_phone, String signs_and_symptoms, String suspected_pest,
                                 String damage_assesment, String recommendation, String photo_f_damage, String farmer_name) {
        Call<ResponseData> call = APIClient.getInstance()
                .postPestReport(date, district, sub_county, village, farmer_phone, signs_and_symptoms, suspected_pest, damage_assesment, recommendation, photo_f_damage, farmer_name
                );
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d(TAG, "onResponse: " + response.body().getMessage());
                if (response.body().getMessage().equalsIgnoreCase("Successful")) {
                    Log.d(TAG, "Pest Report Synced");

                    //update status locally
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    sync_status = "1";

                    if (databaseAccess.updatePestReportSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated succesfully");
                        //delete local database copy
                        if (databaseAccess.deletePestReport(id)) {
                            Log.d(TAG, "onResponse: database record deleted succesfully");
                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed" + id + response.body().getStatus());
                    }

                } else {
                    Log.d(TAG, "Pest Report Sync Failed");
                    Log.d(TAG, String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d(TAG, "Pest Report Sync failed");

            }
        });
    }

    private void saveScoutingReport(String id, String date, String farmer_name, String district, String sub_county, String village, String phone_number, String infested, String infestation_type,
                                    String infestation, String infestation_level, String recommendation) {
        Call<ResponseData> call = APIClient.getInstance()
                .postScoutingReport(date, farmer_name, district, sub_county, village, phone_number, infested, infestation_type, infestation, infestation_level, recommendation
                );
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getMessage().equalsIgnoreCase("Successful")) {
                    Log.d(TAG, "Scouting Report Synced");

                    //update status locally
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    sync_status = "1";

                    if (databaseAccess.updateScoutingReportSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated succesfully");

                        //delete local database copy
                        if (databaseAccess.deleteScoutingReport(id)) {
                            Log.d(TAG, "onResponse: database record deleted succesfully");
                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed");
                    }

                } else {
                    Log.d(TAG, "Scouting Report Sync Failed");
                    Log.d(TAG, String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d(TAG, "Scouting Report Sync failed");
            }
        });
    }

    private void saveMarketPrices(String id, String date, String variety, String market, String measurement_units, String wholesale_price, String retail_price, String commodity) {
        Call<ResponseData> call = APIClient.getInstance()
                .postMarketPrice(date, variety, market, measurement_units, Integer.parseInt(wholesale_price), Integer.parseInt(retail_price), commodity);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getMessage().equalsIgnoreCase("Successful")) {
                    Log.d(TAG, "Market prices Synced");

                    //update status locally
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    sync_status = "1";

                    if (databaseAccess.updateMarketPriceSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated succesfully");
                        //delete local database copy
                        if (databaseAccess.deleteMarketPrice(id)) {
                            Log.d(TAG, "onResponse: database record deleted succesfully");
                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed");
                    }
                } else {
                    Log.d(TAG, "Market Prices Sync Failed");
                    Log.d(TAG, String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d(TAG, "Market Price Sync failed");
            }
        });
    }

    private void saveMarket(String id, String name, String street_adress, String district, String sub_county, String town, String contact_person, String phone_number) {
        Call<ResponseData> call = APIClient.getInstance()
                .postMarketDetails(name, street_adress, district, sub_county, town, contact_person, phone_number);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getMessage().equalsIgnoreCase("Successful")) {
                    Log.d(TAG, "Market  Synced");

                    //update status locally
                    //update status locally
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    sync_status = "1";

                    if (databaseAccess.updateMarketSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated succesfully");

                        //delete local database copy
                        if (databaseAccess.deleteMarket(id)) {
                            Log.d(TAG, "onResponse: database record deleted succesfully");
                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed");
                    }
                } else {
                    Log.d(TAG, "Market Sync Failed");
                    Log.d(TAG, String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d(TAG, "Market  Sync failed");
            }
        });
    }

    private void saveAssociation(
            String id, String name, String year_of_registration, String district, String sub_county, String village, String full_address, String association_telephone,
            String association_email, String number_of_male_members, String crop_value_chain, String livestock_value_chain, String chairperson,
            String chairperson_contact, String secretary, String secretary_contact, String number_of_female_members, String organisation_type, String registration_level,
            String respondent, String respondent_contact, String main_activities, String asset_ownership, String market, String marketing_channels, String funding_source, String additional_services) {
        Call<ResponseData> call = APIClient.getInstance()
                .postAssociation(name, year_of_registration, district, sub_county, village, full_address, association_telephone, association_email, Integer.parseInt(number_of_male_members), crop_value_chain,
                        livestock_value_chain, chairperson, chairperson_contact, secretary, secretary_contact, Integer.parseInt(number_of_female_members), organisation_type,
                        registration_level, respondent, respondent_contact, main_activities, asset_ownership, market, marketing_channels, funding_source, additional_services);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Association  Synced");

                    //update status locally
                    //update status locally
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();

                    if (databaseAccess.updateAssociationSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated succesfully");
                        //delete local database copy
                        if (databaseAccess.deleteAssociation(id)) {
                            Log.d(TAG, "onResponse: database record deleted succesfully");
                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed");
                    }
                } else {
                    Log.d(TAG, "AAssociation sync Failed");
                    Log.d(TAG, String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d(TAG, "Association Sync failed");
            }
        });

    }

    private void saveAgroInputDealer(String id, String business_name, String district, String sub_county, String village, String full_address, String certification, String certification_type, String certification_number,
                                     String registration_body, String registration_year, String registration_status, String association_membership, String association_name,
                                     String business_type, String number_of_outlets, String types_of_sales, String items_sold, String marketing_channels, String funding_source, String additional_services) {
        Call<ResponseData> call = APIClient.getInstance()
                .postAgroInputDealer(business_name, district, sub_county, village, full_address, certification, certification_type, certification_number, registration_body, registration_year, registration_status, association_membership, association_name, business_type, number_of_outlets, types_of_sales, items_sold, marketing_channels, funding_source, additional_services);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(@NotNull Call<ResponseData> call, @NotNull Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Agro Input Dealer  Synced");

                    //update status locally
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();

                    if (databaseAccess.updateDealerSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated successfully");
                        //delete local database copy
                        if (databaseAccess.deleteAgroInputDealer(id)) {
                            Log.d(TAG, "onResponse: database record deleted successfully");
                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed");
                    }
                } else {
                    Log.d(TAG, "Agro Input Dealer Sync failed");

                    try {
                        Log.d(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseData> call, @NotNull Throwable t) {
                Log.d(TAG, "Agro Input Dealer Sync failed");
            }
        });


    }

    private void saveBulkBuyer(String id, String business_name, String business_type, String owner, String phone_number, String email_address, String district, String sub_county, String village,
                               String full_address, String commodities, String supply_source, String supplier_location, String funding_source, String marketing_channels
    ) {
        Call<ResponseData> call = APIClient.getInstance().postBulkBuyer(business_name, business_type, owner, phone_number, email_address, district, sub_county, village, full_address,
                commodities, supply_source, supplier_location, funding_source, marketing_channels);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(@NotNull Call<ResponseData> call, @NotNull Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Bulk buyer Synced");

                    //update status locally
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();

                    if (databaseAccess.updateTraderSyncStatus(id, response.body().getStatus())) {
                        Log.d(TAG, "onResponse: status updated successfully");
                        //delete local database copy
                        if (databaseAccess.deleteTrader(id)) {
                            Log.d(TAG, "onResponse: database record deleted successfully");
                        } else {
                            Log.d(TAG, "onResponse: database record delete failed");
                        }

                    } else {
                        Log.d(TAG, "onResponse: status update failed");
                    }
                } else {
                    Log.d(TAG, "Bulk buyer sync failed");
                    try {
                        Log.d(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseData> call, @NotNull Throwable t) {
                Log.d(TAG, "Bulk buyer sync failed");
            }
        });
    }
}
