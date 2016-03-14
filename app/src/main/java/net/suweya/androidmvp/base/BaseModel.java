package net.suweya.androidmvp.base;

/**
 * BaseModel
 */
public abstract class BaseModel {

    public abstract void cancel();

    public interface OnResponseCallback<T> {
        void onComplete();

        void onSuccess(T response);

        void onError(String message);
    }
}
