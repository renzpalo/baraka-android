package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderUpdatesPhp {

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

        @SerializedName("order_status")
        @Expose
        private List<OrderStatus> orderStatus = null;

        public List<OrderStatus> getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(List<OrderStatus> orderStatus) {
            this.orderStatus = orderStatus;
        }


    }

    public class OrderStatus {

        @SerializedName("order_update_id")
        @Expose
        private String orderUpdateId;
        @SerializedName("order_update_info")
        @Expose
        private String orderUpdateInfo;
        @SerializedName("order_update_date")
        @Expose
        private String orderUpdateDate;

        public String getOrderUpdateId() {
            return orderUpdateId;
        }

        public void setOrderUpdateId(String orderUpdateId) {
            this.orderUpdateId = orderUpdateId;
        }

        public String getOrderUpdateInfo() {
            return orderUpdateInfo;
        }

        public void setOrderUpdateInfo(String orderUpdateInfo) {
            this.orderUpdateInfo = orderUpdateInfo;
        }

        public String getOrderUpdateDate() {
            return orderUpdateDate;
        }

        public void setOrderUpdateDate(String orderUpdateDate) {
            this.orderUpdateDate = orderUpdateDate;
        }

    }
}
