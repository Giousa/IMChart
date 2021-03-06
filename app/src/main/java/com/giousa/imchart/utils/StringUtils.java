package com.giousa.imchart.utils;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/2
 * Email:65489469@qq.com
 */
public class StringUtils {
    private static final String USER_NAME_REGEX = "^[a-zA-Z]\\w{2,19}$";

    private static final String PASSWORD_REGEX = "^[0-9]{3,20}$";


    public static boolean checkUserName(String userName) {
        return userName.matches(USER_NAME_REGEX);
    }

    public static boolean checkPassword(String pwd) {
        return pwd.matches(PASSWORD_REGEX);
    }
}
