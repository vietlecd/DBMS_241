package com.project.shopapp.utils;

public class StringUtil {
    public static boolean checkString(String data) {
        if(data != null && data.equals("")) {
            return true;
        }
        return false;
    }
}
