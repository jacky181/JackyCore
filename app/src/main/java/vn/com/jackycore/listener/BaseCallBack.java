package vn.com.jackycore.listener;

/**
 * Created by sgvn213 on 9/21/2017.
 */

public interface BaseCallBack {
    interface EmptyValueCallback{
        void callback();
    }
    interface Callback<T>{
        void callback(T t);
    }
}
