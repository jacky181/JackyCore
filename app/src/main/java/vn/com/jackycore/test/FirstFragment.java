package vn.com.jackycore.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.com.jackycore.R;
import vn.com.jackycore.view.jFragment.GeneralFragment;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public class FirstFragment extends GeneralFragment {

    @BindView(R.id.edUserName)
    EditText edUserName;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    Unbinder unbinder;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleToolbar("First");
    }

    @OnClick(R.id.btnLogin)
    public void NextClick() {
        getGeneralActivity().changeFragment(SecondFragment.newInstance(), true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
