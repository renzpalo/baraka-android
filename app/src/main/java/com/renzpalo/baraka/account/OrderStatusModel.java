package com.renzpalo.baraka.account;

public class OrderStatusModel {

    private String orderStatusId, orderStatusInfo, orderStatusDate;

    public OrderStatusModel(String orderStatusId, String orderStatusInfo, String orderStatusDate) {
        this.orderStatusId = orderStatusId;
        this.orderStatusInfo = orderStatusInfo;
        this.orderStatusDate = orderStatusDate;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusInfo() {
        return orderStatusInfo;
    }

    public void setOrderStatusInfo(String orderStatusInfo) {
        this.orderStatusInfo = orderStatusInfo;
    }

    public String getOrderStatusDate() {
        return orderStatusDate;
    }

    public void setOrderStatusDate(String orderStatusDate) {
        this.orderStatusDate = orderStatusDate;
    }
}
