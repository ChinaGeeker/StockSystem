package example.ruanjian.stocksystem.application;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.graphics.Paint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.content.Context;
import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.PorterDuff;
import android.app.NotificationManager;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.log.Logger;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.activity.LoginActivity;
import example.ruanjian.stocksystem.manager.ActivityAppManager;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.databases.StockSystemSQLHelper;

public class StockSystemApplication extends Application
{
    private  int _notificationId = 0;
    public int stateRealTimeUpdate = -1;
    private List<Integer> _notificationIdArr;

    private static StockSystemApplication _instance;

    private StockSystemSQLHelper _stockSystemSQLHelper;

    public static StockSystemApplication getInstance()
    {
        return _instance;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        _instance = this;
        initUtils();
    }

    public void printLog(int logType, Object message, Throwable t)
    {
        Logger logger = Logger.getLoggerInstance();
        switch (logType)
        {
            case StockSystemConstant.LOG_WARN:
                logger.warn(message, t);
                break;
            case StockSystemConstant.LOG_INFO:
                logger.info(message, t);
                break;
            case StockSystemConstant.LOG_ERROR:
                logger.error(message, t);
                break;
            case StockSystemConstant.LOG_DEBUG:
                logger.debug(message, t);
                break;
        }
    }

    public void sendNotifition(StockInfo stockInfo)
    {
        if (_notificationIdArr == null)
        {
            _notificationIdArr = new ArrayList<Integer>();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StockSystemConstant.STOCK_INFO, stockInfo);
        intent.putExtras(bundle);

        PendingIntent pendingIntent =  PendingIntent.getActivity(getApplicationContext(), (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setTicker(getString(R.string.stockChange));
        builder.setSmallIcon(R.drawable.notifitionicon);
        builder.setContentTitle(stockInfo.getName());
        builder.setContentText(stockInfo.getName() + getString(R.string.stockUp));
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);
        Notification notification;
        if (getSDKVersion() >= 16)
        {
            notification = builder.build();
        }
        else{
            notification = builder.getNotification();
        }
        _notificationIdArr.add(_notificationId);
        notificationManager.notify(_notificationId, notification);
        _notificationId++;
    }

    public void cancelNotification(int notificationId)
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public void checkSendNotifitionCondition(float gap, StockInfo stockInfo)
    {
        // 股票差距 0.5 , app退出 就弹通知
        if (gap >= StockSystemConstant.STOCK_GAP && (ActivityAppManager.getInstance().isEmpty() == true))
        {
            sendNotifition(stockInfo);
        }
        else {
            StockUtils.updateStockData(stockInfo);
        }
    }

    public void cancelAllNotifition()
    {
        if (_notificationIdArr == null)
        {
            _notificationIdArr = new ArrayList<Integer>();
        }
        for (Integer notificationId:_notificationIdArr)
        {
            cancelNotification(notificationId);
        }
        _notificationIdArr.clear();
        _notificationId = 0;
    }

    public void cleanLog()
    {
        String content = "";
        try {
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(StockSystemConstant.LOG_FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
//            Toast.makeText(_context, R.string.successCleanLog, Toast.LENGTH_SHORT).show();
//            context.deleteFile(LOG_FILE_NAME); //删除日志文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
        catch (IOException e) {
            e.printStackTrace();
            printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
    }

    public boolean isConnectedNetWork(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            Toast.makeText(context, R.string.netWrongFailed, Toast.LENGTH_LONG).show();
            return false;
        }
        if (networkInfo.isAvailable() == false)
        {
            Toast.makeText(context, networkInfo.getTypeName() + R.string.netWrongFailed, Toast.LENGTH_LONG).show();
            return false;
        }
//        if (networkInfo == null || networkInfo.isAvailable() == false)
//        {
//            Toast.makeText(context, R.string.netWrongFailed, Toast.LENGTH_LONG).show();
//            return false;
//        }
        Toast.makeText(context,  context.getString(R.string.curNetType) + networkInfo.getTypeName(), Toast.LENGTH_LONG).show();
        return true;
    }

    public String getCurTimeStringOne()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public String getCurTimeString()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    private void initUtils()
    {
        Logger.getLoggerInstance();
        _stockSystemSQLHelper =  StockSystemSQLHelper.getInstance(getApplicationContext());
    }

    public StockSystemSQLHelper getStockSystemSQLHelper()
    {
        return _stockSystemSQLHelper;
    }


/*  public static boolean activityIsTop()
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String processName = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.v( " activityIsTop 测试 ", "  processName" + processName);
        return processName.equals(STOCK_INFO_ACTIVITY_PROCESS_NAME);
    }*/

    public int getActiveNetworkInfoType()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || networkInfo.isAvailable() == false)
        {
            return -1;
        }
//        ConnectivityManager.TYPE_WIMAX;
        return networkInfo.getType();
    }

    public String getLogPath()
    {
        return getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + StockSystemConstant.LOG_FILE_NAME;
//        return Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + "stockSystem" + File.separator + "stockSystem.log";
    }


    /**
     * 把bitmap转成圆形
     * */
    public Bitmap toRoundBitmap(Bitmap bitmap)
    {
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        int r;
        //取最短边做边长
        if(width<height){
            r=width;
        }else{
            r=height;
        }
        //构建一个bitmap
        Bitmap backgroundBm=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas=new Canvas(backgroundBm);
        Paint p=new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect=new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r/2, r/2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    public int getSDKVersion()
    {
        return Build.VERSION.SDK_INT;
    }

    public int getColorById(int id)
    {
        if (getSDKVersion() >= 23)
        {
            return getResources().getColor(id, null);
        }
        return getResources().getColor(id);
    }

    public void destroy()
    {
        _stockSystemSQLHelper.close();
    }


    @Override
    public void onTrimMemory(int level) {

        Log.v("onTrimMemory 00  ", _notificationId + " getNotificationIdArr  getNotificationIdArr");
        super.onTrimMemory(level);
        Log.v("onTrimMemory 11 ", _notificationId + " getNotificationIdArr  getNotificationIdArr");

    }
}