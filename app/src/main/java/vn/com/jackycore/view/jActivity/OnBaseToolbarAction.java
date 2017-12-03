package vn.com.jackycore.view.jActivity;

import vn.com.jackycore.view.jToolbar.ToolbarHelper;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public interface OnBaseToolbarAction {
    void setTitleToolbar(String var1);

    void setTitleToolbar(String var1, String var2);

    void setTitleBarColor(int var1);

    void setToolbarColor(int color);

    void showBackButton(boolean var1);

    void showMenu(boolean var1);

    ToolbarHelper getToolbarHelper() throws IllegalAccessException;
}
