package com.giousa.imchart.ui.activity;

import android.util.Log;

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

    public static final String TAG = SplashActivity.class.getSimpleName();

    private static final int DELAY = 1000;

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

    /**
     * 若是已经成功登陆，那么下次进入，直接进入主界面
     */
    @Override
    public void onLoggedIn() {
        startActivity(MainActivity.class);
    }
}
