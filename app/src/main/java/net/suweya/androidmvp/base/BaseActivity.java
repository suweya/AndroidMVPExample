package net.suweya.androidmvp.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Base Presenter Activity
 * Created by suweya on 16/2/29.
 *
 * <ul>
 *     <li>V is View</li>
 *     <li>P is Presenter</li>
 * </ul>
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity
    implements DialogInterface.OnCancelListener {

    protected P mPresenter;
    protected ProgressDialog mProgressDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mPresenter.onCancel();
    }

    protected void showLoadingDialog(int message) {
        showLoadingDialog(getString(message));
    }

    protected void showLoadingDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(this);
        } else if (mProgressDialog.isShowing()) {
            return;
        }

        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
