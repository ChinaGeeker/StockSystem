package example.ruanjian.stocksystem.utils;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import example.ruanjian.stocksystem.LoginActivity;
import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.databases.sqlUtils.AccountSQLUtils;
import example.ruanjian.stocksystem.databases.sqlUtils.AccountStockSQLUtils;
import example.ruanjian.stocksystem.databases.sqlUtils.HistoryBrowsingSQLUtils;
import example.ruanjian.stocksystem.databases.sqlUtils.StockSQLUtils;
import example.ruanjian.stocksystem.databases.StockSystemSQLHelper;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.log.Logger;
import example.ruanjian.stocksystem.manager.ActivityAppManager;

public class StockSystemUtils
{
    public static Context context;
    private static int _notificationId = 0;
    public static int stateRealTimeUpdate = -1;
    private static List<Integer> _notificationIdArr;
    public static String stockInfoActivitySimpleName = "";

    private static StockSystemSQLHelper _stockSystemSQLHelper;

    public static void printLog(int logType, Object message, Throwable t)
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

    public static void sendNotifition(StockInfo stockInfo)
    {
        if (_notificationIdArr == null)
        {
            _notificationIdArr = new ArrayList<Integer>();
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StockSystemConstant.STOCK_INFO, stockInfo);
        intent.putExtras(bundle);

        PendingIntent pendingIntent =  PendingIntent.getActivity(context, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);

        builder.setTicker(context.getString(R.string.stockChange));
        builder.setSmallIcon(R.drawable.notifitionicon);
        builder.setContentTitle(stockInfo.getName());
        builder.setContentText(stockInfo.getName() + context.getString(R.string.stockUp));
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        _notificationIdArr.add(_notificationId);
        notificationManager.notify(_notificationId, notification);
        _notificationId++;
    }

    public static void cancelNotifition(int notificationId)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public static void checkSendNotifitionCondition(float gap, StockInfo stockInfo)
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

    public static void cancelAllNotifition()
    {
        if (_notificationIdArr == null)
        {
            _notificationIdArr = new ArrayList<Integer>();
        }
        for (Integer notificationId:_notificationIdArr)
        {
            cancelNotifition(notificationId);
        }
        _notificationIdArr.clear();
        _notificationId = 0;
    }

    public static void cleanLog()
    {
        String content = "";
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(StockSystemConstant.LOG_FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(context, R.string.successCleanLog, Toast.LENGTH_SHORT).show();
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

    public static boolean isConnectedNetWork(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public static String getCurTimeStringOne()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }

    public static String getCurTimeString()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }

    public static void initUtils(StockSystemSQLHelper stockSystemSQLHelper)
    {
        Logger.getLoggerInstance();
        _stockSystemSQLHelper = stockSystemSQLHelper;
        StockUtils.stockSQLUtils = StockSQLUtils.getInstance();
        AccountUtils.accountSQLUtils = AccountSQLUtils.getInstance();
        AccountStockUtils.accountStockSQLUtils = AccountStockSQLUtils.getInstance();
        HistoryBrowsingUtils.historyBrowsingSQLUtils = HistoryBrowsingSQLUtils.getInstance();
    }

    public static StockSystemSQLHelper getStockSystemSQLHelper()
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

    public static int getActiveNetworkInfoType()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || networkInfo.isAvailable() == false)
        {
            return -1;
        }
//        ConnectivityManager.TYPE_WIMAX;
        return networkInfo.getType();
    }

    public static String getLogPath()
    {
        return context.getFilesDir().getAbsolutePath() + File.separator + StockSystemConstant.LOG_FILE_NAME;
//        return Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + "stockSystem" + File.separator + "stockSystem.log";
    }


    /**
     * 把bitmap转成圆形
     * */
    public static Bitmap toRoundBitmap(Bitmap bitmap)
    {
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        int r=0;
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


}
