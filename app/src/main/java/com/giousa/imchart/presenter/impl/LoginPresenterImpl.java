package com.giousa.imchart.presenter.impl;

import com.giousa.imchart.adapter.EMCallBackAdapter;
import com.giousa.imchart.presenter.LoginPresenter;
import com.giousa.imchart.utils.StringUtils;
import com.giousa.imchart.utils.ThreadUtils;
import com.giousa.imchart.view.LoginView;
import com.hyphenate.chat.EMClient;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/2
 * Email:65489469@qq.com
 */
public class LoginPresenterImpl implements LoginPresenter {

    public static final String TAG = "LoginPresenterImpl";
    public LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void login(String userName, String pwd) {
        if (StringUtils.checkUserName(userName)) {
            if (StringUtils.checkPassword(pwd)) {
                mLoginView.onStartLogin();
                startLogin(userName, pwd);
            } else {
                mLoginView.onPasswordError();
            }
        } else {
            mLoginView.onUserNameError();
        }
    }

    private void startLogin(String userName, String pwd) {
        EMClient.getInstance().login(userName, pwd, mEMCallBack);
    }

    private EMCallBackAdapter mEMCallBack = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoginView.onLoginSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoginView.onLoginFailed();
                }
            });
        }
    };
}
