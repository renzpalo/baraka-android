package com.renzpalo.baraka.provinces;

public class ProvincesModel {

    private String provId, provName, provImage;

    public ProvincesModel(String provId, String provName, String provImage) {
        this.provId = provId;
        this.provName = provName;
        this.provImage = provImage;
    }

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
