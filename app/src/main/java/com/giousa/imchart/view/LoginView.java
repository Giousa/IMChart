package com.giousa.imchart.view;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/2
 * Email:65489469@qq.com
 */
public interface LoginView {

    String TAG = "LoginView";

    void onUserNameError();

    void onPasswordError();

    void onStartLogin();

    void onLoginSuccess();

    void onLoginFailed();


}
