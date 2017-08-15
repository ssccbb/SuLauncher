package com.sung.sulauncher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import com.sung.sulauncher.adapter.AppListAdapter;
import com.sung.sulauncher.model.AppInfo;
import com.sung.sulauncher.utils.Contants;
import com.sung.sulauncher.utils.LauncherController;
import com.sung.sulauncher.utils.AppInfoProvider;
import java.util.ArrayList;
import java.util.List;

public class SuHome extends Activity implements AppListAdapter.onAppActionListenner{
    public final static String TAG = "Suhome Launcher";
    private List<AppInfo> mAppList;
    private RecyclerView mRcAppList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

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
        mRcAppList.setLayoutManager(new GridLayoutManager(getApplicationContext(),Contants.APP_COLUMN_NUM));
        mRcAppList.setItemAnimator(new DefaultItemAnimator());
        AppListAdapter adapter = new AppListAdapter(getApplicationContext(), null);
        adapter.addOnAppActionListenner(this);
        mRcAppList.setAdapter(adapter);
        adapter.addData(addAppFilterRule(Contants.APP_RULE_DISPLAY_ALL),true);
    }

    /**
    * 过滤规则
    * */
    private List<AppInfo> addAppFilterRule(int rule){
        switch (rule){
            case Contants.APP_RULE_DISPLAY_ALL:
                return mAppList;
            case Contants.APP_RULE_DISPLAY_USERS:
                List filterList = new ArrayList();
                for (AppInfo appInfo : mAppList) {
                    if (appInfo.isUserApp())
                        filterList.add(appInfo);
                }

                return filterList;
            default:
                return mAppList;
        }
    }

    @Override
    public void onSingleClick(String appPackageName) {
        LauncherController.startApp(getApplicationContext(),appPackageName);
    }

    @Override
    public void onLongClick(String appPackageName, int position) {
        Toast.makeText(this, appPackageName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //屏蔽返回 防止一直切屏
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
