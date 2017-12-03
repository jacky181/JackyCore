package vn.com.jackycore.view.jFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.com.jackycore.R;
import vn.com.jackycore.view.jActivity.JBaseActivity;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public abstract class JBaseFragment extends Fragment{
    protected View rootView;
    private Unbinder unbinder;
    private JBaseFragment.OnFragmentCallbackListener fragmentCallbackListener;
    private FrameLayout containerPage;
    private ProgressBar progressPage;
    private TextView tvMessagePage;
    private boolean hasStatusPageView;
    private View contentPage;

    public void setFragmentCallbackListener(OnFragmentCallbackListener fragmentCallbackListener) {
        this.fragmentCallbackListener = fragmentCallbackListener;
    }

    public OnFragmentCallbackListener getFragmentCallbackListener() {
        return fragmentCallbackListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getStatusPageView() > 0) {
            this.hasStatusPageView = true;
            this.rootView = LayoutInflater.from(this.getContext()).inflate(this.getStatusPageView(), container, false);
            this.contentPage = LayoutInflater.from(this.getContext()).inflate(this.getLayoutId(), (ViewGroup) null);

            try {
                this.initStatusWidgets();
            } catch (IllegalAccessException var5) {
                var5.printStackTrace();
            }
        } else {
            this.rootView = LayoutInflater.from(this.getContext()).inflate(this.getLayoutId(), container, false);
            this.hasStatusPageView = false;
        }

        this.preInitLayout();
        this.unbinder = ButterKnife.bind(this, this.rootView);
        return this.rootView;
    }
    public void showProgressPage(boolean isShow) {
        if (isShow) {
            this.containerPage.setVisibility(View.GONE);
            this.progressPage.setVisibility(View.VISIBLE);
            this.tvMessagePage.setVisibility(View.GONE);
        } else {
            this.containerPage.setVisibility(View.VISIBLE);
            this.progressPage.setVisibility(View.GONE);
            this.tvMessagePage.setVisibility(View.GONE);
        }

    }

    public void showMessagePage(String message) {
        if (!TextUtils.isEmpty(message)) {
            this.containerPage.setVisibility(View.GONE);
            this.progressPage.setVisibility(View.GONE);
            this.tvMessagePage.setVisibility(View.VISIBLE);
            this.tvMessagePage.setText(message);
        }

    }

    private void initStatusWidgets() throws IllegalAccessException {
        this.containerPage = (FrameLayout) this.rootView.findViewById(R.id.containerPage);
        this.progressPage = (ProgressBar) this.rootView.findViewById(R.id.progressPage);
        this.tvMessagePage = (TextView) this.rootView.findViewById(R.id.tvMessagePage);
        if (this.containerPage == null) {
            throw new IllegalAccessException("Can\'t find containerPage id");
        } else if (this.progressPage == null) {
            throw new IllegalAccessException("Can\'t find progressPage id");
        } else if (this.tvMessagePage == null) {
            throw new IllegalAccessException("Can\'t find tvMessagePage id");
        } else {
            this.containerPage.addView(this.contentPage);
        }
    }

    public void preInitLayout() {
    }

    protected int getStatusPageView() {
        return 0;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.bindView();
        this.bindMenu();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unBindView();
        this.unBindMenu();
    }

    public abstract int getLayoutId();

    public void bindView() {
    }

    public void unBindView() {
        if (this.unbinder != null) {
            this.unbinder.unbind();
        }

    }

    public void bindMenu() {
    }

    public void unBindMenu() {
    }

    public JBaseActivity getJBaseActivity() {

        if (this.getActivity() instanceof JBaseActivity) {
            return (JBaseActivity) this.getActivity();
        } else {
            throw new NullPointerException("Can\'t cast this activity to JBaseActivity");
        }
    }


    /*public FragmentHelper getFragmentHelper() {

        if (this.getJBaseActivity() instanceof GeneralActivity) {
            return ( this.getJBaseActivity()).fragmentHelper;
        } else {
            throw new NullPointerException("Can\'t find Fragment Helper");
        }
    }*/

    public interface OnFragmentCallbackListener {
        void onFragmentCallback(CallbackObject var1);
    }
}
