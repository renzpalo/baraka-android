package com.renzpalo.baraka.phpResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInPhp {

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

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_fullname")
        @Expose
        private String userFullname;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_phone_no")
        @Expose
        private String userPhoneNo;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserFullname() {
            return userFullname;
        }

        public void setUserFullname(String userFullname) {
            this.userFullname = userFullname;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPhoneNo() {
            return userPhoneNo;
        }

        public void setUserPhoneNo(String userPhoneNo) {
            this.userPhoneNo = userPhoneNo;
        }

    }

}
