package com.giousa.imchart.view;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/8
 * Email:65489469@qq.com
 */
public interface RegisterView {
    void onStartRegister();

    void onRegisterError();

    void onResisterUserExist();

    void onRegisterSuccess();

    void onUserNameError();

    void onPasswordError();

    void onConfirmPasswordError();
}
