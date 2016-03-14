package net.suweya.androidmvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.suweya.androidmvp.MainActivity;
import net.suweya.androidmvp.R;
import net.suweya.androidmvp.base.BaseActivity;
import net.suweya.androidmvp.presenter.LoginPresenter;
import net.suweya.androidmvp.ui.interfaces.ILoginView;

/**
 * Login Activity
 * Created by suweya on 16/2/29.
 */
public class LoginActivity extends BaseActivity<ILoginView, LoginPresenter> implements ILoginView {

    private EditText mEtUserName, mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidgets();
    }

    private void initWidgets() {
        mEtUserName = (EditText) findViewById(R.id.et_user_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(mEtUserName.getText().toString(),
                                 mEtPassword.getText().toString());
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoginLoading() {
        showLoadingDialog(R.string.login_loading);
    }

    @Override
    public void hideLoginLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void onLoginSuccess(int userId, String userName) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoadingFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
