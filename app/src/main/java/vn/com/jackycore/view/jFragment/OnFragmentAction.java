package vn.com.jackycore.view.jFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;


/**
 * Created by sgvn213 on 9/22/2017.
 */

public interface OnFragmentAction {
    void replaceFragment(int var1, JBaseFragment var2);

    void replaceFragment(int var1, JBaseFragment var2, Bundle var3);

    void replaceFragment(JBaseFragment var1);

    void replaceFragment(JBaseFragment var1, boolean var2, int var3, int var4);

    void replaceFragment(JBaseFragment var1, Bundle var2);

    void replaceFragment(int var1, Fragment var2);

    void replaceFragment(int var1, Fragment var2, boolean var3);

    void replaceFragmentWithSharedElement(JBaseFragment var1, List<View> var2);

    void addFragment(JBaseFragment var1);

    void addFragment(JBaseFragment var1, boolean var2);

    void addFragmentWithSharedElement(JBaseFragment var1, List<View> var2);

    void popBackStack();

    void removeFragment(Fragment var1);

    int getSizeFragmentManager();

    void clearAllFragments();

    JBaseFragment getLastFragment();
}
