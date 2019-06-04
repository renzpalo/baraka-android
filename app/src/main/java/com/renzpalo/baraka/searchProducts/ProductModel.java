package com.renzpalo.baraka.searchProducts;

public class ProductModel {

    private String prodId, prodName, prodPrice, prodOldPrice, prodImage, prodRating, prodProvince, prodCategory;

    public ProductModel(String prodId, String prodName, String prodPrice, String prodOldPrice, String prodImage, String prodRating, String prodProvince, String prodCategory) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodOldPrice = prodOldPrice;
        this.prodImage = prodImage;
        this.prodRating = prodRating;
        this.prodProvince = prodProvince;
        this.prodCategory = prodCategory;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdOldPrice() {
        return prodOldPrice;
    }

    public void setProdOldPrice(String prodOldPrice) {
        this.prodOldPrice = prodOldPrice;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public String getProdRating() {
        return prodRating;
    }

    public void setProdRating(String prodRating) {
        this.prodRating = prodRating;
    }

    public String getProdProvince() {
        return prodProvince;
    }

    public void setProdProvince(String prodProvince) {
        this.prodProvince = prodProvince;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }
}
