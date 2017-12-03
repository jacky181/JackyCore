package vn.com.jackycore.view.jActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public abstract class JBaseActivity extends AppCompatActivity implements OnBaseToolbarAction {

    public Unbinder unbinder;

    public JBaseActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindView();
        setUpViewsAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindView();
    }

    public void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    public void unBindView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public abstract void setUpViewsAndData();

    public abstract int getLayoutId();

}
