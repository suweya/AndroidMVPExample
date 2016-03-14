package net.suweya.androidmvp.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Base Presenter
 * Created by suweya on 16/2/29.
 */
public abstract class BasePresenter<T> {

    protected Reference<T> mViewRef;

    protected void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    protected void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public abstract void onCancel();
}
