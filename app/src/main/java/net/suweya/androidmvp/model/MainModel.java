package net.suweya.androidmvp.model;

import android.os.AsyncTask;
import android.os.SystemClock;

import net.suweya.androidmvp.base.BaseModel;
import net.suweya.androidmvp.base.HttpResponse;
import net.suweya.androidmvp.entities.DataEntity;

import java.util.ArrayList;
import java.util.Random;

/**
 * MainModel
 */
public class MainModel extends BaseModel {

    private GetDataTask mGetDataTask;

    @Override
    public void cancel() {
        if (mGetDataTask != null && !mGetDataTask.isCancelled()) {
            mGetDataTask.cancel(true);
        }
    }

    public void getDataByPage(int pageIndex, OnResponseCallback<DataEntity> callback) {
        cancel();

        mGetDataTask = new GetDataTask(callback);
        mGetDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pageIndex);
    }

    private static class GetDataTask extends AsyncTask<Integer, Void, HttpResponse<DataEntity>> {

        private OnResponseCallback<DataEntity> mCallback;
        private final Random mRandom = new Random();

        public GetDataTask(BaseModel.OnResponseCallback<DataEntity> callback) {
            mCallback = callback;
        }

        @Override
        protected HttpResponse<DataEntity> doInBackground(Integer... params) {
            int pageIndex = params[0];
            HttpResponse<DataEntity> response = new HttpResponse<>();
            boolean result = mRandom.nextBoolean();
            response.success = result;
            response.message = result ? "获取成功" : "获取失败";
            if (result) {
                DataEntity entity = new DataEntity();
                int dataCount = pageIndex > 5 ? 0 : 25;
                ArrayList<String> data = new ArrayList<>(dataCount);
                for (int i = 0; i < dataCount; i++) {
                    data.add("Item : " + i);
                }
                entity.data = data;
                response.value = entity;
            }
            SystemClock.sleep(2000);
            return response;
        }

        @Override
        protected void onPostExecute(HttpResponse<DataEntity> response) {
            super.onPostExecute(response);
            if (mCallback != null && response != null) {
                response.dispatchResponse(mCallback);
            }
        }
    }
}
