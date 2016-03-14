package net.suweya.androidmvp.presenter;

import net.suweya.androidmvp.base.BaseModel;
import net.suweya.androidmvp.base.BasePresenter;
import net.suweya.androidmvp.entities.AccountInfoEntity;
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
            mLoginModel.login(userName, password, new BaseModel.OnResponseCallback<AccountInfoEntity>() {
                @Override
                public void onComplete() {
                    view.hideLoginLoading();
                }

                @Override
                public void onSuccess(AccountInfoEntity response) {
                    view.onLoginSuccess(response.userId, response.userName);
                }

                @Override
                public void onError(String message) {
                    view.onLoadingFailed(message);
                }
            });
        }
    }

    @Override
    public void onCancel() {
        mLoginModel.cancel();
    }
}
