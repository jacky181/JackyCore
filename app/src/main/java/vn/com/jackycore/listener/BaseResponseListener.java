package vn.com.jackycore.listener;

/**
 * Created by sgvn213 on 9/21/2017.
 */

public interface BaseResponseListener<D> {
    void onSuccess(D data);
    void onError(String message);
}
