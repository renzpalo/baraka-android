package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSellerInformationPhp {

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

        @SerializedName("seller_info")
        @Expose
        private List<SellerInfo> sellerInfo = null;

        public List<SellerInfo> getSellerInfo() {
            return sellerInfo;
        }

        public void setSellerInfo(List<SellerInfo> sellerInfo) {
            this.sellerInfo = sellerInfo;
        }

    }

    public class SellerInfo {

        @SerializedName("seller_id")
        @Expose
        private String sellerId;
        @SerializedName("seller_name")
        @Expose
        private String sellerName;
        @SerializedName("seller_info")
        @Expose
        private String sellerInfo;
        @SerializedName("seller_image")
        @Expose
        private String sellerImage;
        @SerializedName("seller_banner")
        @Expose
        private String sellerBanner;

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public String getSellerInfo() {
            return sellerInfo;
        }

        public void setSellerInfo(String sellerInfo) {
            this.sellerInfo = sellerInfo;
        }

        public String getSellerImage() {
            return sellerImage;
        }

        public void setSellerImage(String sellerImage) {
            this.sellerImage = sellerImage;
        }

        public String getSellerBanner() {
            return sellerBanner;
        }

        public void setSellerBanner(String sellerBanner) {
            this.sellerBanner = sellerBanner;
        }

    }

}
