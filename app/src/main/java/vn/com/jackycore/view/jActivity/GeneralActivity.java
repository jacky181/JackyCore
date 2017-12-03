package vn.com.jackycore.view.jActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import vn.com.jackycore.R;
import vn.com.jackycore.view.jFragment.FragmentHelper;
import vn.com.jackycore.view.jFragment.JBaseFragment;
import vn.com.jackycore.view.jToolbar.OnCallBackToolbarAction;
import vn.com.jackycore.view.jToolbar.ToolbarHelper;
import vn.com.jackycore.utils.Utils;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public abstract class GeneralActivity<T extends ToolbarHelper> extends JBaseActivity implements OnCallBackToolbarAction {

    private final String NULL_TOOLBAR_EX = "Can\'t find toolbar of this activity. Please checking it. Note: With raw id : R.id.toolbar";
    public ToolbarHelper toolbarHelper;
    protected Toolbar toolbar;
    protected JBaseFragment currentFragment;
    public FragmentHelper fragmentHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentHelper = new FragmentHelper(this.getSupportFragmentManager(), R.id.fragment_content);
        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        View statusBar = toolbar.findViewById(R.id.viewStatusBar);
        ((LinearLayout.LayoutParams) statusBar.getLayoutParams()).height = Utils.getStatusBarHeight(this);
        statusBar.requestLayout();
        try {
            this.setupToolbar();
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
        }
    }


    private void setupToolbar() throws IllegalAccessException {
        if (toolbar == null) {
            throw new NullPointerException(NULL_TOOLBAR_EX);
        } else {
            setSupportActionBar(toolbar);
            toolbar.setBackgroundResource(onColorOfToolbar());
            toolbarHelper = getToolbarHelper();
            if (toolbarHelper == null) {
                toolbarHelper = new ToolbarHelper(toolbar);
            }
            toolbarHelper.setImageForLeftButton(onImageForLeftButtonToolbar());
        }
    }

    public abstract int onColorOfToolbar();

    public abstract int onImageForLeftButtonToolbar();

    @Override
    public void setTitleToolbar(String var1) {
        toolbarHelper.setTitle(var1);
    }

    @Override
    public void setTitleToolbar(String var1, String var2) {
        toolbarHelper.setTitle(var1, var2);
    }

    @Override
    public void setTitleBarColor(int color) {
        toolbarHelper.setTitleMainColor(color);
    }

    public void setToolbarColor(int color) {
        toolbar.setBackgroundResource(color);
    }

    @Override
    public void showBackButton(boolean isShow) {
        toolbarHelper.showBackButton(isShow, this);
    }

    @Override
    public void showMenu(boolean var1) {

    }

    //    @Override
//    public ToolbarHelper getToolbarHelper() throws IllegalAccessException {
//        return toolbarHelper;
//    }
    public T getToolbarHelper() throws IllegalAccessException {
        return (T) this.toolbarHelper;
    }

    @Override
    public void setUpViewsAndData() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void onCallBackToolbar() {
        // check
        fragmentHelper.popBackStack();
    }

    public FragmentHelper getFragmentHelper() {
        if (fragmentHelper == null) {
            this.fragmentHelper = new FragmentHelper(this.getSupportFragmentManager(), R.id.fragment_content);
        }
        return fragmentHelper;
    }

    public void changeFragment(JBaseFragment fragment, boolean isBackStack) {
        currentFragment = fragment;
        if (isBackStack) {
            showBackButton(true);
        } else {
            showBackButton(false);
        }
        fragmentHelper.replaceFragment(fragment, isBackStack, R.anim.fade_in, R.anim.fade_out);
    }
}
