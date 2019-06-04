package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsPhp {

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

        @SerializedName("product_details")
        @Expose
        private List<ProductDetail> productDetails = null;
        @SerializedName("reviews")
        @Expose
        private List<Review> reviews = null;
        @SerializedName("related_products")
        @Expose
        private List<RelatedProduct> relatedProducts = null;

        public List<ProductDetail> getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(List<ProductDetail> productDetails) {
            this.productDetails = productDetails;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        public List<RelatedProduct> getRelatedProducts() {
            return relatedProducts;
        }

        public void setRelatedProducts(List<RelatedProduct> relatedProducts) {
            this.relatedProducts = relatedProducts;
        }

        public class ProductDetail {

            @SerializedName("prod_id")
            @Expose
            private String prodId;
            @SerializedName("prod_name")
            @Expose
            private String prodName;
            @SerializedName("prod_desc")
            @Expose
            private String prodDesc;
            @SerializedName("prod_price")
            @Expose
            private String prodPrice;
            @SerializedName("prod_old_price")
            @Expose
            private String prodOldPrice;
            @SerializedName("prod_stock")
            @Expose
            private Integer prodStock;
            @SerializedName("prod_image")
            @Expose
            private String prodImage;
            @SerializedName("prod_type")
            @Expose
            private String prodType;
            @SerializedName("prod_ship_fee")
            @Expose
            private String prodShipFee;
            @SerializedName("prod_rating")
            @Expose
            private float prodRating;
            @SerializedName("province")
            @Expose
            private String province;
            @SerializedName("seller")
            @Expose
            private String seller;

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

            public String getProdDesc() {
                return prodDesc;
            }

            public void setProdDesc(String prodDesc) {
                this.prodDesc = prodDesc;
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

            public Integer getProdStock() {
                return prodStock;
            }

            public void setProdStock(Integer prodStock) {
                this.prodStock = prodStock;
            }

            public String getProdImage() {
                return prodImage;
            }

            public void setProdImage(String prodImage) {
                this.prodImage = prodImage;
            }

            public String getProdType() {
                return prodType;
            }

            public void setProdType(String prodType) {
                this.prodType = prodType;
            }

            public String getProdShipFee() {
                return prodShipFee;
            }

            public void setProdShipFee(String prodShipFee) {
                this.prodShipFee = prodShipFee;
            }

            public float getProdRating() {
                return prodRating;
            }

            public void setProdRating(float prodRating) {
                this.prodRating = prodRating;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getSeller() {
                return seller;
            }

            public void setSeller(String seller) {
                this.seller = seller;
            }

        }

        public class RelatedProduct {

            @SerializedName("rel_prod_id")
            @Expose
            private String relProdId;
            @SerializedName("rel_prod_name")
            @Expose
            private String relProdName;
            @SerializedName("rel_prod_price")
            @Expose
            private String relProdPrice;
            @SerializedName("rel_prod_disc_price")
            @Expose
            private String relProdDiscPrice;
            @SerializedName("rel_prod_image")
            @Expose
            private String relProdImage;
            @SerializedName("rel_prod_rating")
            @Expose
            private float relProdRating;

            public String getRelProdId() {
                return relProdId;
            }

            public void setRelProdId(String relProdId) {
                this.relProdId = relProdId;
            }

            public String getRelProdName() {
                return relProdName;
            }

            public void setRelProdName(String relProdName) {
                this.relProdName = relProdName;
            }

            public String getRelProdPrice() {
                return relProdPrice;
            }

            public void setRelProdPrice(String relProdPrice) {
                this.relProdPrice = relProdPrice;
            }

            public String getRelProdDiscPrice() {
                return relProdDiscPrice;
            }

            public void setRelProdDiscPrice(String relProdDiscPrice) {
                this.relProdDiscPrice = relProdDiscPrice;
            }

            public String getRelProdImage() {
                return relProdImage;
            }

            public void setRelProdImage(String relProdImage) {
                this.relProdImage = relProdImage;
            }

            public float getRelProdRating() {
                return relProdRating;
            }

            public void setRelProdRating(float relProdRating) {
                this.relProdRating = relProdRating;
            }

        }

        public class Review {

            @SerializedName("rev_id")
            @Expose
            private String revId;
            @SerializedName("rev_summary")
            @Expose
            private String revSummary;
            @SerializedName("rev_feedback")
            @Expose
            private String revFeedback;
            @SerializedName("rev_image")
            @Expose
            private String revImage;
            @SerializedName("rev_date")
            @Expose
            private String revDate;
            @SerializedName("rev_rating")
            @Expose
            private Float revRating;
            @SerializedName("user_name")
            @Expose
            private String userName;


            public String getRevId() {
                return revId;
            }

            public void setRevId(String revId) {
                this.revId = revId;
            }

            public String getRevSummary() {
                return revSummary;
            }

            public void setRevSummary(String revId) {
                this.revSummary = revSummary;
            }

            public String getRevFeedback() {
                return revFeedback;
            }

            public void setRevFeedback(String revFeedback) {
                this.revFeedback = revFeedback;
            }

            public String getRevImage() {
                return revImage;
            }

            public void setRevImage(String revImage) {
                this.revImage = revImage;
            }

            public String getRevDate() {
                return revDate;
            }

            public void setRevDate(String revDate) {
                this.revDate = revDate;
            }

            public Float getRevRating() {
                return revRating;
            }

            public void setRevRating(Float revRating) {
                this.revRating = revRating;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

        }

    }

}
