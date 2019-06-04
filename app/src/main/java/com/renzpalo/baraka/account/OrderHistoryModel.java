package com.renzpalo.baraka.account;

public class OrderHistoryModel {

    private String orderId, orderTotal, orderStatus, orderPayment, orderDate, orderAddress, orderFullname, orderContact;

    public OrderHistoryModel(String orderId, String orderTotal, String orderStatus, String orderPayment, String orderDate, String orderAddress, String orderFullname, String orderContact) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.orderStatus = orderStatus;
        this.orderPayment = orderPayment;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
        this.orderFullname = orderFullname;
        this.orderContact = orderContact;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderFullname() {
        return orderFullname;
    }

    public void setOrderFullname(String orderFullname) {
        this.orderFullname = orderFullname;
    }

    public String getOrderContact() {
        return orderContact;
    }

    public void setOrderContact(String orderContact) {
        this.orderContact = orderContact;
    }
}
