package com.giousa.imchart.model;

import cn.bmob.v3.BmobUser;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/8
 * Email:65489469@qq.com
 */
public class User extends BmobUser {
    public User(String userName, String password) {
        setUsername(userName);
        setPassword(password);
    }
}
