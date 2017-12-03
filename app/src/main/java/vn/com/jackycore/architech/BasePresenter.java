package vn.com.jackycore.architech;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Jacky Hua on 9/22/2017.
 */

public abstract class BasePresenter<T extends BasePresenter.View> {

    private WeakReference<T> view;
    private AtomicBoolean isViewAlive = new AtomicBoolean();
    protected CompositeDisposable compositeDisposable;
    protected Context context;

    public BasePresenter(Context context) {
        compositeDisposable = new CompositeDisposable();
        this.context = context;
    }

    public void bind(T view) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        this.view = new WeakReference<T>(view);
    }

    public T getView() {
        return view.get();
    }

    public boolean isAttached() {
        if (getView() != null) {
            return true;
        }
        return false;
    }

    public void start() {
        isViewAlive.set(true);
    }

    public void unbind() {
        isViewAlive.set(false);
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = null;
    }


    public interface View {
    }
}
