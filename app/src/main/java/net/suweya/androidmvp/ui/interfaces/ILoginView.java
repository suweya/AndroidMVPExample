package net.suweya.androidmvp.ui.interfaces;

/**
 * Created by suweya on 16/2/29.
 */
public interface ILoginView {

    void showLoginLoading();

    void hideLoginLoading();

    void onLoginSuccess(int userId, String userName);

    void onLoadingFailed(String message);
}
