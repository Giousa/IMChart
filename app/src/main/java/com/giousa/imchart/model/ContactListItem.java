package com.giousa.imchart.model;

import com.giousa.imchart.R;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class ContactListItem {

    public static final String TAG = "ContactListItem";

    public int avatar = R.mipmap.avatar6;

    public String userName;

    public boolean showFirstLetter = true;

    public char getFirstLetter() {
        return userName.charAt(0);
    }

    public String getFirstLetterString() {
        return String.valueOf(getFirstLetter()).toUpperCase();
    }
}
