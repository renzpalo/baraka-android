package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedProductsPhp {

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

        @SerializedName("prod_id")
        @Expose
        private String prodId;
        @SerializedName("prod_name")
        @Expose
        private String prodName;
        @SerializedName("prod_price")
        @Expose
        private String prodPrice;
        @SerializedName("prod_disc_price")
        @Expose
        private String prodDiscPrice;
        @SerializedName("prod_image")
        @Expose
        private String prodImage;
        @SerializedName("prod_rating")
        @Expose
        private String prodRating;

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

        public String getProdDiscPrice() {
            return prodDiscPrice;
        }

        public void setProdDiscPrice(String prodDiscPrice) {
            this.prodDiscPrice = prodDiscPrice;
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

    }
}
