package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProvincesPhp {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("information")
    @Expose
    private Information information;

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

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public class Information {

        @SerializedName("provinces")
        @Expose
        private List<Province> provinces = null;

        public List<Province> getProvinces() {
            return provinces;
        }

        public void setProvinces(List<Province> provinces) {
            this.provinces = provinces;
        }

    }

    public class Province {

        @SerializedName("prov_id")
        @Expose
        private String provId;
        @SerializedName("prov_name")
        @Expose
        private String provName;
        @SerializedName("prov_image")
        @Expose
        private String provImage;

        public String getProvId() {
            return provId;
        }

        public void setProvId(String provId) {
            this.provId = provId;
        }

        public String getProvName() {
            return provName;
        }

        public void setProvName(String provName) {
            this.provName = provName;
        }

        public String getProvImage() {
            return provImage;
        }

        public void setProvImage(String provImage) {
            this.provImage = provImage;
        }

    }

}

