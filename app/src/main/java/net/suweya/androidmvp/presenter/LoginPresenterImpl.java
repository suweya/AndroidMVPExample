package net.suweya.androidmvp.presenter;

import android.os.AsyncTask;
import android.os.SystemClock;

import net.suweya.androidmvp.base.BasePresenter;
import net.suweya.androidmvp.ui.interfaces.ILoginView;

/**
 * Login Presenter Implementation
 * Created by suweya on 16/2/29.
 */
public class LoginPresenterImpl extends BasePresenter<ILoginView> implements ILoginPresenter {

    @Override
    public void login(String userName, String password) {

        ILoginView view = getView();
        if (view != null) {
            view.showLoginLoading();
            new LoginTask().execute(userName, password);
        }
    }

    class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            SystemClock.sleep(2000);
            return "suweya".equals(params[0]) && "123".equals(params[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ILoginView view = getView();
            if (view != null) {
                view.hideLoginLoading();
                if (result) {
                    view.onLoginSuccess(1, "suweya");
                } else {
                    view.onLoadingFailed("login failed");
                }
            }
        }
    }
}
