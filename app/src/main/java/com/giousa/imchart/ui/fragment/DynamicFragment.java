package com.giousa.imchart.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.presenter.DynamicPresenter;
import com.giousa.imchart.presenter.impl.DynamicPresenterImpl;
import com.giousa.imchart.ui.activity.LoginActivity;
import com.giousa.imchart.view.DynamicView;
import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class DynamicFragment extends BaseFragment implements DynamicView {

    public static final String TAG = "DynamicFragment";

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.logout)
    Button mLogout;

    private DynamicPresenter mDynamicPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void init() {
        super.init();
        mDynamicPresenter = new DynamicPresenterImpl(this);
        String logout = String.format(getString(R.string.logout), EMClient.getInstance().getCurrentUser());
        mLogout.setText(logout);
        mTitle.setText(getString(R.string.dynamic));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.logout)
    public void onClick() {
        mDynamicPresenter.logout();
    }

    @Override
    public void onStartLogout() {
        showProgress(getString(R.string.logouting));
    }

    @Override
    public void onLogoutSuccess() {
        hideProgress();
        toast(getString(R.string.logout_success));
        startActivity(LoginActivity.class, true);
    }

    @Override
    public void onLogoutFailed() {
        hideProgress();
        toast(getString(R.string.logout_failed));
    }
}
