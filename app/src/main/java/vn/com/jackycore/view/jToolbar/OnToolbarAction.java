package vn.com.jackycore.view.jToolbar;

import android.view.View;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public interface OnToolbarAction {
    void setTitle(String title);

    void setTitle(String title, String font);

    void setTitleMainColor(int color);
    void setToolbarColor(int color);

    void showToolbar(boolean isShow);

    void showRightToolbar(boolean isShow);

    void showBackButton(boolean isShow);

    void showBackButton(boolean isShow, OnCallBackToolbarAction onCallBackToolbarAction);

    void setImageForLeftButton(int drawable);

    void setRightToolbarButton(String text, View.OnClickListener onClickListener);

    void setRightToolbarButton(int iconRes, View.OnClickListener onClickListener);
}
