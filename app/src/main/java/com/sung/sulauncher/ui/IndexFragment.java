package com.sung.sulauncher.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sung.sulauncher.R;
import com.sung.sulauncher.common.UISwitch;

public class IndexFragment extends BaseFragment implements View.OnClickListener{
    public final static String TAG = "IndexFragment";
    private UISwitch mUISwitch;

    public IndexFragment() {
        // Required empty public constructor
    }

    public static IndexFragment getInstance(){
        return new IndexFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        Button mToApplist = findView(R.id.btn_applist);
        mToApplist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_applist:
                mUISwitch.showApplist();
                break;
            default:
                Log.d(TAG, "onClick: "+view.getId());
                break;
        }
    }

    public void addUISwitchListener(UISwitch uiSwitch){
        this.mUISwitch = uiSwitch;
    }

    @Override
    public boolean onFragmentBackPressed() {
        return super.onFragmentBackPressed();
    }
}
