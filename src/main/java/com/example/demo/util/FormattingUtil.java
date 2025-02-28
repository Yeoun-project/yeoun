package com.example.demo.util;

public class FormattingUtil {

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.startsWith("+")) {
            return "010" + phoneNumber.substring(3);
        }
        return phoneNumber;
    }

}
