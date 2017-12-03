package vn.com.jackycore.listener;

/**
 * Created by sgvn213 on 9/20/2017.
 */

public class BaseListener {
    public interface OnItemClickListener<T> {
        void listener(T t);
    }

    public interface OnMultiClickListener<T> {
        void groupClick(T t);

        void send(String s, T t);
    }

    public interface OnClickListener<T, V> {
        void listener(T t, V v);
    }
}
