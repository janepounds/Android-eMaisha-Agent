package com.cabraltech.emaishaagentsapp.network;

import com.cabraltech.emaishaagentsapp.models.Regions;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRequests {


    @FormUrlEncoded
    @POST("getregions")
    Call<Regions> getAllRegions(@Field("latest_id") int latest_id);

    @FormUrlEncoded
    @POST("agent/store/farmer")
    Call<ResponseBody>postFarmersList(
         @Field("first_name") String first_name,
         @Field("last_name") String last_name,
         @Field("dob") String dob,
         @Field("age") int age,
         @Field("gender") String gender,
         @Field("nationality") String nationality,
         @Field("religion") String religion,
         @Field("level_of_education") String level_of_education,
         @Field("marital_status") String marital_status,
         @Field("household_size") int household_size,
         @Field("language_used") String language_used,
         @Field("source_of_income") String source_of_income,
         @Field("household_head") String household_head,
         @Field("district") String district,
         @Field("sub_county") String sub_county,
         @Field("village") String village,
         @Field("phone_number") int phone_number,
         @Field("next_of_kin") String next_of_kin,
         @Field("next_of_kin_relation") String next_of_kin_relation,
         @Field("next_of_kin_contact") String next_of_kin_contact,
         @Field("next_of_kin_address") String next_of_kin_address,
         @Field("farming_land_size") int farming_land_size,
         @Field("main_crop") String main_crop,
         @Field("second_crop") String second_crop,
         @Field("third_crop") String third_crop,
         @Field("main_livestock") String main_livestock,
         @Field("second_livestock") String second_livestock


    );


    @FormUrlEncoded
    @POST("agent/store/pestreport")
    Call<ResponseBody> postPestReport(
            @Field("date") String date,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("farmer_phone") int farmer_phone,
            @Field("signs_and_symptoms") String signs_and_symptoms,
            @Field("suspected_pest") String suspected_pest,
            @Field("damage_assesment") String damage_assesment,
            @Field("recommendation") String recommendation,
            @Field("photo_of_damage") String photo_of_damage,
            @Field("farmer_name") String farmer_name


    );


    @FormUrlEncoded
    @POST("agent/store/scountingreport")
    Call<ResponseBody> postScoutingReport(
            @Field("date") String date,
            @Field("farmer_name") String farmer_name,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("phone_number") int phone_number,
            @Field("infested") String infested,
            @Field("infestation_type") String infestation_type,
            @Field("infestation") String infestation,
            @Field("infestation_level") String infestation_level,
            @Field("recommendation") String recommendation



    );

    @FormUrlEncoded
    @POST("agent/store/marketprice")
    Call<ResponseBody> postMarketPrice(
            @Field("date") String date,
            @Field("variety") String variety,
            @Field("market") String market,
            @Field("measurement_units") String measurement_units,
            @Field("wholesale_price") int wholesale_price,
            @Field("retail_price") int retail_price


    );

    @FormUrlEncoded
    @POST("agent/store/market")
    Call<ResponseBody> postMarketDetails(
            @Field("name") String name,
            @Field("street_address") String street_address,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("town") String town,
            @Field("contact_person") String contact_person,
            @Field("phone_number") int phone_number



    );

    @FormUrlEncoded
    @POST("agent/store/association")
    Call<ResponseBody> postAssociation(
            @Field("name") String name,
            @Field("year_of_registration") int year_of_registration,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("full_address") String full_address,
            @Field("association_telephone") int association_telephone,
            @Field("association_email") String association_email,
            @Field("number_of_male_members") int number_of_male_members,
            @Field("crop_value_chain") String crop_value_chain,
            @Field("livestock_value_chain") String livestock_value_chain,
            @Field("main_source_of_funding") String main_source_of_funding,
            @Field("chairperson") String chairperson,
            @Field("chairperson_contact") int chairperson_contact,
            @Field("secretary") String secretary,
            @Field("secretary_contact") int secretary_contact,
            @Field("number_of_female_members") int number_of_female_members,
            @Field("organisation_type") String organisation_type,
            @Field("registration_level") String registration_level,
            @Field("respondent") String respondent,
            @Field("respondent_contact") int respondent_contact,
            @Field("main_activities") String main_activities,
            @Field("asset_ownership") String asset_ownership,
            @Field("market") String market,
            @Field("marketing_channels") String funding_source,
            @Field("funding_source") String marketing_channels,
            @Field("additional_services") String additional_services


    );

    @FormUrlEncoded
    @POST("agent/store/agroInputdealer")
    Call<ResponseBody> postAgroInputDealer(
            @Field("business_name") String business_name,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("full_address") String full_address,
            @Field("certification") String certification,
            @Field("certification_number") String certification_number,
            @Field("registration_body") String registration_body,
            @Field("registration_year") int registration_year,
            @Field("registration_status") String registration_status,
            @Field("association_membership") String association_membership,
            @Field("association_name") String association_name,
            @Field("business_type") String business_type,
            @Field("number_of_outlets") int number_of_outlets,
            @Field("types_of_sales") String types_of_sales,
            @Field("items_sold") String items_sold,
            @Field("marketing_channels") String marketing_channels,
            @Field("funding_source") String funding_source,
            @Field("additional_services") String additional_services



    );


    @FormUrlEncoded
    @POST("agent/store/bulkbuyer")
    Call<ResponseBody> postBulkBuyer(
            @Field("business_name") String business_name,
            @Field("business_type") String business_type,
            @Field("owner") String owner,
            @Field("phone_number") String phone_number,
            @Field("email_address") String email_address,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("full_address") String full_address,
            @Field("commodities") String commodities,
            @Field("supply_source") String supply_source,
            @Field("supplier_location") String supplier_location,
            @Field("funding_source") String funding_source,
            @Field("marketing_channels") String marketing_channels




    );





}
