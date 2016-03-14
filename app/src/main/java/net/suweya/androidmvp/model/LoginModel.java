package net.suweya.androidmvp.model;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class LoginModel {

    private OnLoginResponseCallback mCallback;

    public void login(String userName, String passWord, OnLoginResponseCallback callback) {
        this.mCallback = callback;
        new LoginTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userName, passWord);
    }

    private void onPostExecute(boolean result) {
        mCallback.onLoginComplete();
        if (result) {
            mCallback.onLoginSuccess(1001, "suweya");
        } else {
            mCallback.onLoginError("login error");
        }
    }

    public interface OnLoginResponseCallback {

        void onLoginComplete();

        void onLoginSuccess(int userId, String userName);

        void onLoginError(String message);
    }

    private static class LoginTask extends AsyncTask<String, Void, Boolean> {

        private WeakReference<LoginModel> mWeakReference;

        public LoginTask(LoginModel model) {
            mWeakReference = new WeakReference<>(model);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String userName = params[0];
            String passWord = params[1];
            return "suweya".equals(userName) && "123".equals(passWord);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            LoginModel model = mWeakReference.get();
            if (model != null) {
                model.onPostExecute(result);
            }
        }
    }
}
