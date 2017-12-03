package vn.com.jackycore.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import vn.com.jackycore.R;
import vn.com.jackycore.view.jActivity.GeneralActivity;

public class MainActivity extends GeneralActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleToolbar("Main");

    }

    @Override
    public int onColorOfToolbar() {
        return R.color.colorAccent;
    }


    @Override
    public int onImageForLeftButtonToolbar() {
        return R.drawable.ic_keyboard_arrow_left;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCallBackToolbar() {
        // if fragmant >1
        super.onCallBackToolbar();
    }
}
