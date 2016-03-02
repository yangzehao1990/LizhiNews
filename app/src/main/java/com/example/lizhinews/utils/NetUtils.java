package com.example.lizhinews.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.example.lizhinews.ui.R;


public class NetUtils
{
    private Context mContext;
    /**
     * 判断网络状态
     */
    public static boolean NetWorkUtils(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            //得到所有网络管理器
            NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo networkInfo : infos) {
                    //判断网络状态是否是连接状态
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 无网络状态下，弹出对话框设置网络
     * @param context
     */
    public static void ShowDialog(final Activity context) {
        if(!NetWorkUtils(context))
        {
            new AlertDialog.Builder(context)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(R.string.net_info)
                    .setMessage(R.string.setting_network)
                    .setPositiveButton(R.string.determined, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转到网络设置
                            context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            context.finish();
                        }
                    })
                    .setNeutralButton(R.string.cancel,null).create().show();
        }
    }
}
