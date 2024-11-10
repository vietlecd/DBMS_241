package com.project.shopapp.utils;

public class NumberUtil {
    public static boolean isNumber(String value) {
        try {
            Long number = Long.parseLong(value);// chuyển chuỗi thành số

        }
        catch(NumberFormatException ex){
            return false;  /// không chuyển được thành dạng số
        }
        return true;
    }
}
