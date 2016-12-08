package com.giousa.imchart.ui.activity;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.presenter.RegisterPresenter;
import com.giousa.imchart.presenter.impl.RegisterPresenterImpl;
import com.giousa.imchart.view.RegisterView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/2
 * Email:65489469@qq.com
 */
public class RegisterActivity extends BaseActivity implements RegisterView {

    @InjectView(R.id.user_name)
    EditText mUserName;
    @InjectView(R.id.password)
    EditText mPassword;
    @InjectView(R.id.confirm_password)
    EditText mConfirmPassword;
    @InjectView(R.id.register)
    Button mRegister;

    public static final String TAG = RegisterActivity.class.getSimpleName();
    private RegisterPresenter mRegisterPresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();
        mRegisterPresenter = new RegisterPresenterImpl(this);
        mConfirmPassword.setOnEditorActionListener(mOnEditorActionListener);
    }

    @OnClick(R.id.register)
    public void onClick() {
        register();
    }

    @Override
    public void onStartRegister() {
        showProgress(getString(R.string.registering));

    }

    @Override
    public void onRegisterError() {
        hideProgress();
        toast(getString(R.string.register_failed));
    }

    @Override
    public void onResisterUserExist() {
        hideProgress();
        toast(getString(R.string.register_failed_user_exist));
    }

    @Override
    public void onRegisterSuccess() {
        hideProgress();
        toast(getString(R.string.register_success));
        startActivity(LoginActivity.class);
    }

    @Override
    public void onUserNameError() {
        mUserName.setError(getString(R.string.user_name_error));
    }

    @Override
    public void onPasswordError() {
        mPassword.setError(getString(R.string.user_password_error));
    }

    @Override
    public void onConfirmPasswordError() {
        mConfirmPassword.setError(getString(R.string.user_password_confirm_error));
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                register();
                return true;
            }
            return false;
        }
    };

    private void register() {
        hideKeyBoard();
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();
        mRegisterPresenter.register(userName, password, confirmPassword);
    }
}
