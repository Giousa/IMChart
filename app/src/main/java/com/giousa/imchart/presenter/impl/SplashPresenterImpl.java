package com.giousa.imchart.presenter.impl;

import com.giousa.imchart.presenter.SplashPresenter;
import com.giousa.imchart.view.SplashView;
import com.hyphenate.chat.EMClient;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/1
 * Email:65489469@qq.com
 */
public class SplashPresenterImpl implements SplashPresenter {
    public static final String TAG = "SplashPresenterImpl";

    public SplashView mSplashView;

    public SplashPresenterImpl(SplashView splashView) {
        mSplashView = splashView;
    }

    @Override
    public void checkLoginStatus() {
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()) {
            mSplashView.onLoggedIn();
        } else {
            mSplashView.onNotLogin();
        }
    }
}