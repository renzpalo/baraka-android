package com.renzpalo.baraka.phpResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderHistoryPhp {

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

        @SerializedName("order_history")
        @Expose
        private List<OrderHistory> orderHistory = null;

        public List<OrderHistory> getOrderHistory() {
            return orderHistory;
        }

        public void setOrderHistory(List<OrderHistory> orderHistory) {
            this.orderHistory = orderHistory;
        }

    }

    public class OrderHistory {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("date")
        @Expose
        private String date;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }

}