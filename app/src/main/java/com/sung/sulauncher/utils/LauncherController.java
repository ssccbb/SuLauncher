package com.sung.sulauncher.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.sung.sulauncher.R;

/**
 * Created by sung on 2017/8/15.
 */

public class LauncherController {
    private final static String TAG = "AppController";

    public static void startApp(Context context, String packageName){
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(context, "启动失败！", Toast.LENGTH_SHORT).show();
            printError(packageName,e);
        }
    }

    private static void printError(String packageName, Exception e){
        Log.e(TAG, "startApp\t" + packageName + "error.\nthrow exception:"+e.toString().trim());
    }

    public static void changeWallpaper(Context context){
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        Intent choose_wallpaper = Intent.createChooser(intent, context.getResources().getString(R.string.choose_wallpaper));
        context.startActivity(choose_wallpaper);
    }
}
