package com.cabraltech.emaishaagentsapp.models.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponseData {
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("district")
    @Expose
    private String district;

    @SerializedName("sub_county")
    @Expose
    private String subCounty;

    @SerializedName("village")
    @Expose
    private String village;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("next_of_kin")
    @Expose
    private String nextOfKin;

    @SerializedName("next_of_kin_relation")
    @Expose
    private String nextOfKinRelation;

    @SerializedName("next_of_kin_contact")
    @Expose
    private String nextOfKinContact;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("nin")
    @Expose
    private String nin;

    @SerializedName("picture")
    @Expose
    private String picture;

    @SerializedName("national_id_picture")
    @Expose
    private String nationalIdPicture;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("id")
    @Expose
    private String id;
}
