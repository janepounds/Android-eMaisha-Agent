package com.cabraltech.emaishaagentsapp.models.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseData {
    @SerializedName("id")
    @Expose
    private String id;

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

    @SerializedName("agent_date_of_last_logon")
    @Expose
    private String agentDateOfLastLogon;

    @SerializedName("agent_number_of_logons")
    @Expose
    private String agentNumberOfLogons;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(String subCounty) {
        this.subCounty = subCounty;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getNextOfKinRelation() {
        return nextOfKinRelation;
    }

    public void setNextOfKinRelation(String nextOfKinRelation) {
        this.nextOfKinRelation = nextOfKinRelation;
    }

    public String getNextOfKinContact() {
        return nextOfKinContact;
    }

    public void setNextOfKinContact(String nextOfKinContact) {
        this.nextOfKinContact = nextOfKinContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNationalIdPicture() {
        return nationalIdPicture;
    }

    public void setNationalIdPicture(String nationalIdPicture) {
        this.nationalIdPicture = nationalIdPicture;
    }

    public String getAgentDateOfLastLogon() {
        return agentDateOfLastLogon;
    }

    public void setAgentDateOfLastLogon(String agentDateOfLastLogon) {
        this.agentDateOfLastLogon = agentDateOfLastLogon;
    }

    public String getAgentNumberOfLogons() {
        return agentNumberOfLogons;
    }

    public void setAgentNumberOfLogons(String agentNumberOfLogons) {
        this.agentNumberOfLogons = agentNumberOfLogons;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
