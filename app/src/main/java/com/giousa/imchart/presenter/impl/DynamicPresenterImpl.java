package com.giousa.imchart.presenter.impl;

import com.giousa.imchart.adapter.EMCallBackAdapter;
import com.giousa.imchart.presenter.DynamicPresenter;
import com.giousa.imchart.utils.ThreadUtils;
import com.giousa.imchart.view.DynamicView;
import com.hyphenate.chat.EMClient;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class DynamicPresenterImpl implements DynamicPresenter {

    private DynamicView mDynamicView;

    public DynamicPresenterImpl(DynamicView dynamicView) {
        mDynamicView = dynamicView;
    }

    @Override
    public void logout() {
        mDynamicView.onStartLogout();
        EMClient.getInstance().logout(true, mEMCallBackAdapter);
    }

    private EMCallBackAdapter mEMCallBackAdapter = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDynamicView.onLogoutSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDynamicView.onLogoutFailed();
                }
            });
        }
    };
}
