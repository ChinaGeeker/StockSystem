package example.ruanjian.stocksystem.asyncTask;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.info.VersionInfo;
import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class CheckVersionAsyncTask extends AsyncTask<Void, Void, Boolean>
{
    public CheckVersionAsyncTask()
    {
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        VersionInfo versionInfo = null;
        boolean isUpgrade = false;//默认不升级
        Object versionObj = NetworkUtils.sendHttpUrlConnecttionByGet(StockSystemConstant.SYSTEM_VERSION_URL);
        try {
            JSONObject versionJson = new JSONObject(versionObj.toString());
            Log.v("  versionJson  "," " + versionJson);
            versionInfo = new VersionInfo();
            versionInfo.set_versionCode(versionJson.getInt(StockSystemConstant.SYSTEM_VERSION_CODE));
            versionInfo.set_versionDescribe(versionJson.getString(StockSystemConstant.SYSTEM_VERSION_DESC));
            versionInfo.set_versionName(versionJson.getString(StockSystemConstant.SYSTEM_VERSION_NAME));
            versionInfo.set_versionUrl(versionJson.getString(StockSystemConstant.SYSTEM_VERSION_APK_URL));
            StockSystemApplication.getInstance().set_versionInfo(versionInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (versionInfo == null)
        {
            return isUpgrade;
        }
        PackageManager packageManager = StockSystemApplication.getInstance().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(StockSystemApplication.getInstance().getPackageName(), 0);
            int oldVersion = packageInfo.versionCode;
            if (oldVersion < versionInfo.get_versionCode())
            {
                isUpgrade = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return isUpgrade;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        // 根据布局值 判断是否 提示用户更新
        Intent intent = new Intent();
        intent.setAction(StockSystemConstant.VERSION_UPGRADE_ACTION);
        StockSystemApplication.getInstance().sendBroadcast(intent);
    }


}