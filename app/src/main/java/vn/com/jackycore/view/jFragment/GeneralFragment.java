package vn.com.jackycore.view.jFragment;

import vn.com.jackycore.view.jActivity.GeneralActivity;
import vn.com.jackycore.view.jActivity.OnBaseToolbarAction;
import vn.com.jackycore.view.jToolbar.ToolbarHelper;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public class GeneralFragment extends JBaseFragment implements OnBaseToolbarAction{


    public GeneralActivity getGeneralActivity(){
        return (GeneralActivity) getJBaseActivity();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void setTitleToolbar(String var1) {
        getJBaseActivity().setTitleToolbar(var1);
    }

    @Override
    public void setTitleToolbar(String var1, String var2) {
        getJBaseActivity().setTitleToolbar(var1,var2);
    }

    @Override
    public void setTitleBarColor(int var1) {
        getJBaseActivity().setTitleBarColor(var1);

    }

    @Override
    public void setToolbarColor(int color) {
        getJBaseActivity().setToolbarColor(color);
    }

    @Override
    public void showBackButton(boolean var1) {
        getJBaseActivity().showBackButton(var1);
    }

    @Override
    public void showMenu(boolean var1) {
        getJBaseActivity().showMenu(var1);
    }

    @Override
    public ToolbarHelper getToolbarHelper() throws IllegalAccessException {
        /*if (getJBaseActivity() instanceof GeneralActivity) {
            return ((GeneralActivity) getJBaseActivity()).toolbarHelper;
        }
        return null;*/
        return this.getJBaseActivity() instanceof GeneralActivity?((GeneralActivity)this.getJBaseActivity()).toolbarHelper:null;
    }


}
