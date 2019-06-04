package com.renzpalo.baraka.Utility;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Pattern;

public class DataValidation {

    // Data Validation
    // Full Name - must be letters, no numbers, no special characters.
    // Address - can contain letters, numbers and few symbols.
    // Phone Number - must be number (Mobile number not Landline number)
    //              - length must be > 11, no character, no symbol
    // Password - length > 6, no space
    //


    // Full name from A-Z
    public static String USER_FULLNAME = "[a-zA-Z ]*";
    public static String USER_USERNAME = "[a-zA-Z.+-,0-9 ]*";
    public static String USER_ADDRESS, USER_EMAIL = "[a-zA-Z.+-,0-9 ]*";
    public static String USER_PHONE_NUMBER = "[0-9]*";

    public static Boolean isNotValidPassword(String userPassword) {
        // trim() Method to remove spaces.

        Boolean valid = true;

        if (!TextUtils.isEmpty(userPassword.trim())) {
            if (userPassword.trim().length() > 6) {
                valid = false;
            }
        }

        return valid;
    }

    public static Boolean isNotValidFullName(String userFullName) {
        Boolean valid = true;

        if (!TextUtils.isEmpty(userFullName.trim())) {
            Log.e("Data valid ", "Full " + userFullName.trim());
            if (Pattern.compile(USER_FULLNAME).matcher(userFullName).matches()) {
                Log.e("Data ", "Match success.");
                valid = false;
            }
        }

        return valid;
    }

    public static Boolean isNotValidPhoneNumber(String userPhoneNumber) {
        Boolean valid = true;

        if (!TextUtils.isEmpty(userPhoneNumber.trim()) && userPhoneNumber.length() > 10) {
            if (Pattern.compile(USER_PHONE_NUMBER).matcher(userPhoneNumber).matches()) {
                valid = false;
            }
        }

        return valid;
    }

    public static Boolean isNotValidUsername(String userUsername) {
        Boolean valid = true;

        if (!TextUtils.isEmpty(userUsername.trim())) {
            if (Pattern.compile(USER_USERNAME).matcher(userUsername).matches()) {
                valid = false;
            }
        }
        return valid;
    }

    public static Boolean isNotValidAddress(String userAddress) {
        Boolean valid = true;

        if (!TextUtils.isEmpty(userAddress.trim())) {
            if (Pattern.compile(USER_ADDRESS).matcher(userAddress).matches()) {
                valid = false;
            }
        }
        return valid;
    }

    public static Boolean isNotValidEmail(String userEmail) {
        Boolean valid = true;

        if (!TextUtils.isEmpty(userEmail.trim())) {
            if (Pattern.compile(USER_EMAIL).matcher(userEmail).matches()) {
                valid = false;
            }
        }
        return valid;
    }

}
