package net.suweya.androidmvp.presenter;

import net.suweya.androidmvp.base.BasePresenter;
import net.suweya.androidmvp.model.LoginModel;
import net.suweya.androidmvp.ui.interfaces.ILoginView;

/**
 * Login Presenter Implementation
 * Created by suweya on 16/2/29.
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private LoginModel mLoginModel;

    public LoginPresenter() {
        mLoginModel = new LoginModel();
    }

    public void login(String userName, String password) {

        final ILoginView view = getView();
        if (view != null) {
            view.showLoginLoading();
            mLoginModel.login(userName, password, new LoginModel.OnLoginResponseCallback() {

                @Override
                public void onLoginComplete() {
                    view.hideLoginLoading();
                }

                @Override
                public void onLoginSuccess(int userId, String userName) {
                    view.onLoginSuccess(userId, userName);
                }

                @Override
                public void onLoginError(String message) {
                    view.onLoadingFailed(message);
                }
            });
        }
    }

}
