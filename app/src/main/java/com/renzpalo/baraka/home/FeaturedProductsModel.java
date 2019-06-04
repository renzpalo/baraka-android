package com.renzpalo.baraka.home;

public class FeaturedProductsModel {

    private String productId, productName, productImage, productOldPrice, productPrice, productRatings;

    public FeaturedProductsModel(String productId, String productName, String productImage, String productOldPrice, String productPrice, String productRatings) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productOldPrice = productOldPrice;
        this.productPrice = productPrice;
        this.productRatings = productRatings;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductOldPrice() {
        return productOldPrice;
    }

    public void setProductOldPrice(String productOldPrice) {
        this.productOldPrice = productOldPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductRatings() {
        return productRatings;
    }

    public void setProductRatings(String productRatings) {
        this.productRatings = productRatings;
    }
}
