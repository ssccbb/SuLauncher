package com.sung.sulauncher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sung.sulauncher.adapter.AppListAdapter;
import com.sung.sulauncher.model.AppInfo;
import com.sung.sulauncher.utils.AppInfoProvider;

import java.util.ArrayList;
import java.util.List;

public class SuHome extends AppCompatActivity {
    public final static String TAG = "Suhome Launcher";
    private final static int APP_RULE_DISPLAY_USERS = 0;
    private final static int APP_RULE_DISPLAY_ALL = 1;
    private List<AppInfo> mAppList;
    private RecyclerView mRcAppList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportActionBar().hide();

        initLauncher();
    }

    /**
     * 初始化ui，获得applist
     * */
    private void initLauncher(){
        mRcAppList = (RecyclerView) findViewById(R.id.rc_app_list);
        mAppList = AppInfoProvider.getAppInfos(getApplicationContext());

        ListAppInit();
        for (AppInfo appInfo : mAppList) {
            Log.d(TAG, "appinfo:"+appInfo.toString());
        }
    }

    /**
     * 初始化列表
     * */
    private void ListAppInit(){
        mRcAppList.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        mRcAppList.setItemAnimator(new DefaultItemAnimator());
        AppListAdapter adapter = new AppListAdapter(getApplicationContext(), null);
        mRcAppList.setAdapter(adapter);
        adapter.addData(addAppFilterRule(APP_RULE_DISPLAY_USERS),true);
    }

    private List<AppInfo> addAppFilterRule(int rule){
        switch (rule){
            case APP_RULE_DISPLAY_ALL:
                return mAppList;
            case APP_RULE_DISPLAY_USERS:
                List newList = new ArrayList();
                for (AppInfo appInfo : mAppList) {
                    if (appInfo.isUserApp())
                        newList.add(appInfo);
                }

                return newList;
            default:
                return mAppList;
        }
    }
}
