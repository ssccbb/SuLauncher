package com.sung.sulauncher;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;

import com.sung.sulauncher.common.UISwitch;
import com.sung.sulauncher.ui.AppListFragment;
import com.sung.sulauncher.ui.IndexFragment;

public class SuHome extends Activity implements UISwitch{
    public final static String TAG = "Suhome Launcher";
    private FragmentManager mFragmentManager;
    private AppListFragment mAppListFragment;
    private IndexFragment mIndexFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        init();
        showIndexFragment();
    }

    private void init(){
        mFragmentManager = getFragmentManager();
        mAppListFragment = AppListFragment.getInstance();
        mIndexFragment = IndexFragment.getInstance();
        mAppListFragment.addUISwitchListener(this);
        mIndexFragment.addUISwitchListener(this);

        mFragmentManager.beginTransaction()
                .add(R.id.activity_launcher, mAppListFragment,AppListFragment.TAG)
                .add(R.id.activity_launcher, mIndexFragment,IndexFragment.TAG)
                .commit();
    }

    private void showAppListFragment(){
        mFragmentManager.beginTransaction().hide(mIndexFragment).show(mAppListFragment).commit();
    }

    private void showIndexFragment(){
        mFragmentManager.beginTransaction().hide(mAppListFragment).show(mIndexFragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //屏蔽返回 防止一直切屏
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (!mAppListFragment.isHidden())
                showIndexFragment();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void showIndex() {
        showIndexFragment();
    }

    @Override
    public void showApplist() {
        showAppListFragment();
    }
}
