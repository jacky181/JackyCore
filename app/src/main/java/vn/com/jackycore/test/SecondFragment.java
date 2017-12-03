package vn.com.jackycore.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import vn.com.jackycore.R;
import vn.com.jackycore.view.jFragment.GeneralFragment;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public class SecondFragment extends GeneralFragment {

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  showBackButton(false);
        setTitleToolbar("Second");
        setToolbarColor(R.color.Wheat);
        setTitleBarColor(ContextCompat.getColor(getContext(), R.color.yellow));



    }




    @Override
    public int getLayoutId() {
        return R.layout.fragment_second;
    }
}
