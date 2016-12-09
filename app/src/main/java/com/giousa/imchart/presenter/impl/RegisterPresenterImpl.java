package com.giousa.imchart.presenter.impl;

import com.giousa.imchart.app.Constant;
import com.giousa.imchart.model.User;
import com.giousa.imchart.presenter.RegisterPresenter;
import com.giousa.imchart.utils.StringUtils;
import com.giousa.imchart.utils.ThreadUtils;
import com.giousa.imchart.view.RegisterView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/8
 * Email:65489469@qq.com
 */
public class RegisterPresenterImpl implements RegisterPresenter{

    public static final String TAG = "RegisterPresenterImpl";

    public RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView registerView) {
        mRegisterView = registerView;
    }


    @Override
    public void register(String userName, String pwd, String pwdConfirm) {
        if (StringUtils.checkUserName(userName)) {
            if (StringUtils.checkPassword(pwd)) {
                if (pwd.equals(pwdConfirm)) {
                    mRegisterView.onStartRegister();
                    registerBmob(userName, pwd);
                } else {
                    mRegisterView.onConfirmPasswordError();
                }
            } else {
                mRegisterView.onPasswordError();
            }
        } else {
            mRegisterView.onUserNameError();
        }
    }

    /**
     * 注册用户到Bmob
     * @param userName
     * @param pwd
     */
    private void registerBmob(final String userName, final String pwd) {
        User user = new User(userName, pwd);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    //注册Bmob成功，前往环信注册
                    registerEaseMob(userName, pwd);
                } else {
                    notifyRegisterFailed(e);
                }
            }
        });
    }

    /**
     * 注册用户到环信
     * @param userName
     * @param pwd
     */
    private void registerEaseMob(final String userName, final String pwd) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName, pwd);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterError();
                        }
                    });
                }
            }
        });
    }

    private void notifyRegisterFailed(BmobException e) {
        if (e.getErrorCode() == Constant.ErrorCode.USER_ALREADY_EXIST) {
            mRegisterView.onResisterUserExist();
        } else {
            mRegisterView.onRegisterError();
        }
    }
}
