package com.sung.sulauncher.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sung.sulauncher.R;
import com.sung.sulauncher.adapter.AppListAdapter;
import com.sung.sulauncher.common.UISwitch;
import com.sung.sulauncher.model.AppInfo;
import com.sung.sulauncher.utils.AppInfoProvider;
import com.sung.sulauncher.utils.Contants;
import com.sung.sulauncher.utils.LauncherController;
import java.util.ArrayList;
import java.util.List;

public class AppListFragment extends BaseFragment implements AppListAdapter.onAppActionListenner{
    public final static String TAG = "AppList";
    private UISwitch mUISwitch;
    private List<AppInfo> mAppList;
    private RecyclerView mRcAppList;

    public AppListFragment() {
        // Required empty public constructor
    }

    public static AppListFragment getInstance(){
        return new AppListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAppList = AppInfoProvider.getAppInfos(getActivityContext());
        return inflater.inflate(R.layout.fragment_app_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    /**
     * 初始化ui，获得applist
     * */
    private void initUI(){
        mRcAppList = findView(R.id.rc_app_list);
        ListAppInit();
    }

    /**
     * 初始化列表
     * */
    private void ListAppInit(){
        mRcAppList.setLayoutManager(new GridLayoutManager(getActivityContext(), Contants.APP_COLUMN_NUM));
        mRcAppList.setItemAnimator(new DefaultItemAnimator());
        AppListAdapter adapter = new AppListAdapter(getActivityContext(), null);
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
        LauncherController.startApp(getActivityContext(),appPackageName);
    }

    @Override
    public void onLongClick(String appPackageName, int position) {
        Toast.makeText(getActivityContext(), appPackageName, Toast.LENGTH_SHORT).show();
    }

    public void addUISwitchListener(UISwitch uiSwitch){
        this.mUISwitch = uiSwitch;
    }

}
