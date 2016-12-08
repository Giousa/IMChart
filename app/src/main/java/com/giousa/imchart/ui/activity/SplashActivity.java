package com.giousa.imchart.ui.activity;

import com.giousa.imchart.R;
import com.giousa.imchart.presenter.SplashPresenter;
import com.giousa.imchart.presenter.impl.SplashPresenterImpl;
import com.giousa.imchart.view.SplashView;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/1
 * Email:65489469@qq.com
 */
public class SplashActivity extends BaseActivity implements SplashView {

    public static final String TAG = "SplashActivity";

    private static final int DELAY = 2000;

    private SplashPresenter mSplashPresenter;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        super.init();
        mSplashPresenter = new SplashPresenterImpl(this);
        mSplashPresenter.checkLoginStatus();
    }

    @Override
    public void onNotLogin() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
            }
        }, DELAY);
    }

    @Override
    public void onLoggedIn() {

    }
}
