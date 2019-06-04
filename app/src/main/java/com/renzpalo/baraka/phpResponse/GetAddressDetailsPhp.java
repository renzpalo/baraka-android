package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddressDetailsPhp {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("information")
    @Expose
    private List<Information> information = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Information> getInformation() {
        return information;
    }

    public void setInformation(List<Information> information) {
        this.information = information;
    }

    public class Information {

        @SerializedName("ad_id")
        @Expose
        private String adId;
        @SerializedName("ad_fulname")
        @Expose
        private String adFulname;
        @SerializedName("ad_contact")
        @Expose
        private String adContact;
        @SerializedName("ad_street")
        @Expose
        private String adStreet;
        @SerializedName("ad_barangay")
        @Expose
        private String adBarangay;
        @SerializedName("ad_city_muni")
        @Expose
        private String adCityMuni;
        @SerializedName("ad_province")
        @Expose
        private String adProvince;
        @SerializedName("ad_zipcode")
        @Expose
        private String adZipcode;
        @SerializedName("ad_notes")
        @Expose
        private String adNotes;

        public String getAdId() {
            return adId;
        }

        public void setAdId(String adId) {
            this.adId = adId;
        }

        public String getAdFulname() {
            return adFulname;
        }

        public void setAdFulname(String adFulname) {
            this.adFulname = adFulname;
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
            return adZipcode;
        }

        public void setAdZipcode(String adZipcode) {
            this.adZipcode = adZipcode;
        }

        public String getAdNotes() {
            return adNotes;
        }

        public void setAdNotes(String adNotes) {
            this.adNotes = adNotes;
        }

    }

}
