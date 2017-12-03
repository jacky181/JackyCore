package vn.com.jackycore.view.jFragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.List;

import vn.com.jackycore.R;

/**
 * Created by Jacky Hua on 02/12/2017.
 */

public class FragmentHelper implements OnFragmentAction {
    private FragmentManager fragmentManager;
    private int idContent = R.id.fragment_content;

    public FragmentHelper(FragmentManager fragmentManager, int idContent) {
        this.fragmentManager = fragmentManager;
        this.idContent = idContent;
    }


    @Override
    public void replaceFragment(int id, JBaseFragment baseFragment) {
        fragmentManager.beginTransaction().replace(id, baseFragment)
                .addToBackStack(baseFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(int id, JBaseFragment baseFragment, Bundle bundle) {
        baseFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(id, baseFragment)
                .addToBackStack(baseFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(JBaseFragment baseFragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(idContent, baseFragment)
                .addToBackStack(baseFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(JBaseFragment baseFragment, boolean isBackStack, int inAnim, int outAnim) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(inAnim, outAnim)
                .replace(idContent, baseFragment);

        if (isBackStack) {
            fragmentTransaction.addToBackStack(baseFragment.getClass().getName());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(JBaseFragment baseFragment, Bundle bundle) {
        baseFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(idContent, baseFragment)
                .addToBackStack(baseFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(int id, Fragment fragment) {
        fragmentManager.beginTransaction().replace(id, fragment)
                .addToBackStack(fragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(int id, Fragment fragment, boolean isBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        if (isBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void replaceFragmentWithSharedElement(JBaseFragment baseFragment, List<View> views) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .replace(idContent, baseFragment);
        if (views != null && !views.isEmpty()) {
            for (View view : views) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragmentTransaction.addSharedElement(view, view.getTransitionName());
                }
            }
        }

        fragmentTransaction.addToBackStack(baseFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void addFragment(JBaseFragment baseFragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(idContent, baseFragment)
                .addToBackStack(baseFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void addFragment(JBaseFragment baseFragment, boolean isBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(idContent, baseFragment);
        if (isBackStack) {
            fragmentTransaction.addToBackStack(baseFragment.getClass().getName());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void addFragmentWithSharedElement(JBaseFragment baseFragment, List<View> views) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .add(idContent, baseFragment);
        if (views != null && !views.isEmpty()) {
            for (View view : views) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragmentTransaction.addSharedElement(view, view.getTransitionName());
                }
            }
        }

        fragmentTransaction.addToBackStack(baseFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Override
    public void popBackStack() {
        fragmentManager.popBackStack();
    }

    @Override
    public void removeFragment(Fragment baseFragment) {
        fragmentManager.beginTransaction().remove(baseFragment).commitAllowingStateLoss();
    }

    @Override
    public int getSizeFragmentManager() {
        return fragmentManager.getFragments().size();
    }

    @Override
    public void clearAllFragments() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    @Override
    public JBaseFragment getLastFragment() {
        return (JBaseFragment) fragmentManager.getFragments().get(getSizeFragmentManager() - 1);
    }
}
