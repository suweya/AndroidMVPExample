package net.suweya.androidmvp.base;

/**
 * HttpResponse
 */
public class HttpResponse<T> {

    public boolean success;

    public String message;

    public T value;

    public void dispatchResponse(BaseModel.OnResponseCallback<T> callback) {
        callback.onComplete();
        if (success) {
            callback.onSuccess(value);
        } else {
            callback.onError(message);
        }
    }
}
