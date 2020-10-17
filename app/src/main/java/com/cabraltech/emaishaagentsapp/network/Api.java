package com.cabraltech.emaishaagentsapp.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("farmer/save")
    Call<ResponseBody> posFarmer(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("dob") String dob,
            @Field("age") String age,
            @Field("gender") String gender,
            @Field("nationality") String nationality,
            @Field("religion") String religion,
            @Field("level_of_education") String level_of_education,
            @Field("marital_status") String marital_status,
            @Field("household_size") String household_size,
            @Field("language_used") String language_used,
            @Field("source_of_income") String source_of_income,
            @Field("household_head") String household_head,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("phone_number") String phone_number,
            @Field("next_of_kin") String next_of_kin,
            @Field("next_of_kin_relation") String next_of_kin_relation,
            @Field("next_of_kin_contact") String next_of_kin_contact,
            @Field("next_of_kin_address") String next_of_kin_address,
            @Field("farming_land_size") String farming_land_size,
            @Field("main_crop") String main_crop,
            @Field("second_crop") String second_crop,
            @Field("third_crop") String third_crop,
            @Field("main_livestock") String main_livestock,
            @Field("second_livestock") String second_livestock
    );

    @FormUrlEncoded
    @POST("association/save")
    Call<ResponseBody> postAssociation(
            @Field("name") String name,
            @Field("year_of_registration") String year_of_registration,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("full_address") String full_address,
            @Field("association_telephone") String association_telephone,
            @Field("association_email") String association_email,
            @Field("number_of_members") String number_of_members,
            @Field("crop_value_chain") String crop_value_chain,
            @Field("livestock_value_chain") String livestock_value_chain,
            @Field("main_source_of_funding") String main_source_of_funding,
            @Field("chairperson") String chairperson,
            @Field("chairperson_contact") String chairperson_contact,
            @Field("vice_chairperson") String vice_chairperson,
            @Field("vice_chairperson_contact") String vice_chairperson_contact
    );


    @FormUrlEncoded
    @POST("dealer/save")
    Call<ResponseBody> postDealer(
            @Field("business_name") String business_name,
            @Field("proprietor_name") String proprietor_name,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("full_address") String full_address,
            @Field("proprietor_contact") String proprietor_contact,
            @Field("certification") String certification,
            @Field("certification_type") String certification_type,
            @Field("certification_number") String certification_number
    );

    @FormUrlEncoded
    @POST("dealer/save")
    Call<ResponseBody> postTrader(
            @Field("category") String category,
            @Field("business_name") String business_name,
            @Field("proprietor_name") String proprietor_name,
            @Field("commodities") String commodities,
            @Field("phone_number") String phone_number,
            @Field("email_address") String email_address,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("actual_address") String actual_address
    );


    @FormUrlEncoded
    @POST("market/save")
    Call<ResponseBody> postMarket(
            @Field("name") String name,
            @Field("street_address") String street_address,
            @Field("phone_number") String phone_number,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("town") String town,
            @Field("contact_person") String contact_person
    );

    @FormUrlEncoded
    @POST("market_price/save")
    Call<ResponseBody> postMarketPrice(
            @Field("name") String name,
            @Field("commodities") String commodities,
            @Field("variety") String variety,
            @Field("market") String market,
            @Field("measurement_units") String measurement_units,
            @Field("wholesale_price") String wholesale_price,
            @Field("retail_price") String retail_price
    );

    @FormUrlEncoded
    @POST("market_price/save")
    Call<ResponseBody> postPestReport(
            @Field("date") String date,
            @Field("farmer_name") String farmer_name,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("farmer_phone") String farmer_phone,
            @Field("signs_and_symptoms") String signs_and_symptoms,
            @Field("suspected_pest") String suspected_pest,
            @Field("damage_assesment") String damage_assesment,
            @Field("recommendation") String recommendation,
            @Field("photo_of_damage") String photo_of_damage
    );

    @FormUrlEncoded
    @POST("market_price/save")
    Call<ResponseBody> postScoutingReport(
            @Field("date") String date,
            @Field("farmer_name") String farmer_name,
            @Field("district") String district,
            @Field("sub_county") String sub_county,
            @Field("village") String village,
            @Field("phone_number") String phone_number,
            @Field("infested") String infested,
            @Field("infestation_type") String infestation_type,
            @Field("infestation") String infestation,
            @Field("infestation_level") String infestation_level,
            @Field("recommendation") String recommendation
    );
}
