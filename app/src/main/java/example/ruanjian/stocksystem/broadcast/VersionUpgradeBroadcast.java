package example.ruanjian.stocksystem.broadcast;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.asyncTask.DownLoadVersionAsyncTask;
import example.ruanjian.stocksystem.info.VersionInfo;
import example.ruanjian.stocksystem.manager.AlertDialogManager;

public class VersionUpgradeBroadcast extends BroadcastReceiver implements DialogInterface.OnClickListener
{
    private StockMainActivity _stockMainActivity;

    public VersionUpgradeBroadcast(StockMainActivity stockMainActivity)
    {
        this._stockMainActivity = stockMainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        VersionInfo versionInfo = StockSystemApplication.getInstance().get_versionInfo();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("检测到新版本" + versionInfo.get_versionName() + ",是否进行更新？");
        builder.setTitle("版本更新");
        builder.setIcon(R.drawable.icon);
        builder.setPositiveButton("取 消", this);
        builder.setNegativeButton("确 定", this);
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if (which == -2)//下载
        {
            new DownLoadVersionAsyncTask(_stockMainActivity).execute();
//            new Notification.Builder()

        }
        //   setPositiveButton  -1;  setNegativeButton  -2
    }



}
