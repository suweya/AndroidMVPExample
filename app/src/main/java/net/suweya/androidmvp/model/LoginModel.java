package net.suweya.androidmvp.model;

import android.os.AsyncTask;
import android.os.SystemClock;

import net.suweya.androidmvp.base.BaseModel;
import net.suweya.androidmvp.base.HttpResponse;
import net.suweya.androidmvp.entities.AccountInfoEntity;

import java.lang.ref.WeakReference;

public class LoginModel extends BaseModel {

    private LoginTask mLoginTask;

    public void login(String userName, String passWord, OnResponseCallback<AccountInfoEntity> callback) {
        mLoginTask = new LoginTask(callback);
        mLoginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userName, passWord);
    }

    @Override
    public void cancel() {
        if (mLoginTask != null && !mLoginTask.isCancelled()) {
            mLoginTask.cancel(true);
        }
    }

    private static class LoginTask extends AsyncTask<String, Void, HttpResponse<AccountInfoEntity>> {

        private WeakReference<OnResponseCallback<AccountInfoEntity>> mWeakReference;

        public LoginTask(OnResponseCallback<AccountInfoEntity> callback) {
            mWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected HttpResponse<AccountInfoEntity> doInBackground(String... params) {
            String userName = params[0];
            String passWord = params[1];
            boolean result = "suweya".equals(userName) && "123".equals(passWord);
            HttpResponse<AccountInfoEntity> response = new HttpResponse<>();
            response.success = result;
            response.message = result ? "登录成功" : "登录失败";
            if (result) {
                AccountInfoEntity entity = new AccountInfoEntity();
                entity.userId = 123;
                entity.userName = "suweya";
                response.value = entity;
            }
            SystemClock.sleep(2000);
            return response;
        }

        @Override
        protected void onPostExecute(HttpResponse<AccountInfoEntity> response) {
            super.onPostExecute(response);
            OnResponseCallback<AccountInfoEntity> callback = mWeakReference.get();
            if (callback != null && response != null) {
                response.dispatchResponse(callback);
            }
        }
    }
}
