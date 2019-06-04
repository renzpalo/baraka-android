package com.renzpalo.baraka.account;

public class AddressModel {

    private String adId, adFullname, adContact, adStreet, adBarangay, adCityMuni, adProvince, adZipcode, adNotes;

    public AddressModel(String adId, String adFullname, String adContact, String adStreet, String adBarangay, String adCityMuni, String adProvince, String adZipcode, String adNotes) {
        this.adId = adId;
        this.adFullname = adFullname;
        this.adContact = adContact;
        this.adStreet = adStreet;
        this.adBarangay = adBarangay;
        this.adCityMuni = adCityMuni;
        this.adProvince = adProvince;
        this.adZipcode = adZipcode;
        this.adNotes = adNotes;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdFullname() {
        return adFullname;
    }

    public void setAdFullname(String adFullname) {
        this.adFullname = adFullname;
    }

    public String getAdContact() {
        return adContact;
    }

    public void setAdContact(String adContact) {
        this.adContact = adContact;
    }

    public String getAdStreet() {
        return adStreet;
    }

    public void setAdStreet(String adStreet) {
        this.adStreet = adStreet;
    }

    public String getAdBarangay() {
        return adBarangay;
    }

    public void setAdBarangay(String adBarangay) {
        this.adBarangay = adBarangay;
    }

    public String getAdCityMuni() {
        return adCityMuni;
    }

    public void setAdCityMuni(String adCityMuni) {
        this.adCityMuni = adCityMuni;
    }

    public String getAdProvince() {
        return adProvince;
    }

    public void setAdProvince(String adProvince) {
        this.adProvince = adProvince;
    }

    public String getAdZipcode() {
        return adProvince;
    }

    public void setAdZipcode(String adZipcode) {
        this.adZipcode = adZipcode;
    }

    public String getAdNotes() {
        return adZipcode;
    }

    public void setAdNotes(String adNotes) {
        this.adNotes = adNotes;
    }
}
