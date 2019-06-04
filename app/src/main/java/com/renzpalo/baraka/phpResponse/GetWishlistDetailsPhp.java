package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetWishlistDetailsPhp {

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

        @SerializedName("product_array")
        @Expose
        private List<ProductArray> productArray = null;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;

        public List<ProductArray> getProductArray() {
            return productArray;
        }

        public void setProductArray(List<ProductArray> productArray) {
            this.productArray = productArray;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }

    }

    public class ProductArray {

        @SerializedName("prod_id")
        @Expose
        private String prodId;
        @SerializedName("prod_name")
        @Expose
        private String prodName;
        @SerializedName("prod_price")
        @Expose
        private String prodPrice;
        @SerializedName("prod_image")
        @Expose
        private String prodImage;
        @SerializedName("prod_quantity")
        @Expose
        private String prodQuantity;

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

        public String getProdImage() {
            return prodImage;
        }

        public void setProdImage(String prodImage) {
            this.prodImage = prodImage;
        }

        public String getProdQuantity() {
            return prodQuantity;
        }

        public void setProdQuantity(String prodQuantity) {
            this.prodQuantity = prodQuantity;
        }

    }

}
