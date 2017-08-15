package com.sung.sulauncher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sung.sulauncher.R;
import com.sung.sulauncher.model.AppInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sung on 2017/8/14.
 */

public class AppListAdapter extends RecyclerView.Adapter implements View.OnClickListener,View.OnLongClickListener{
    private onAppActionListenner onAppActionListenner;
    private List<AppInfo> data = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public AppListAdapter(Context context, List data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        if (data == null)
            data = new ArrayList();

        this.data.addAll(data);
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.applist_item,null);
        return new AppHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppInfo appInfo = data.get(position);
        AppHolder appHolder = (AppHolder) holder;
        appHolder.mRootLayout.setTag(position);
        appHolder.mIvIcon.setImageDrawable(appInfo.getIcon());
        appHolder.mTvName.setText(appInfo.getName().trim());

        appHolder.mRootLayout.setOnClickListener(this);
        appHolder.mRootLayout.setOnLongClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List appList, boolean clear){
        if (clear)
            data.clear();

        if (appList == null)
            return;

        this.data.addAll(appList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (onAppActionListenner == null || view.getTag() == null)
            return;

        int position = (int) view.getTag();
        onAppActionListenner.onSingleClick(data.get(position).getPackname());
    }

    @Override
    public boolean onLongClick(View view) {
        if (onAppActionListenner == null || view.getTag() == null)
            return false;

        int position = (int) view.getTag();
        onAppActionListenner.onLongClick(data.get(position).getPackname(),position);
        return true;
    }

    public void addOnAppActionListenner(onAppActionListenner onAppActionListenner) {
        this.onAppActionListenner = onAppActionListenner;
    }

    public interface onAppActionListenner{
        void onSingleClick(String appPackageName);
        void onLongClick(String appPackageName, int position);
    }

    class AppHolder extends RecyclerView.ViewHolder{
        public View mRootLayout;
        public ImageView mIvIcon;
        public TextView mTvName;

        public AppHolder(View itemView) {
            super(itemView);
            mRootLayout = itemView;
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
