package example.ruanjian.stocksystem.service;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;

import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class RealTimeUpdateService extends Service
{
    private StockInfo _curStockInfo;
    private boolean _isEnd = false;
    private boolean _isStart = false;

    private RealTimeThread _realTimeThread;

    public RealTimeUpdateService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
       return null;
    }

    @Override
    public void onCreate()
    {
        _isEnd = false;
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent == null)
        {
            return 0;
        }
        _curStockInfo = (StockInfo) intent.getSerializableExtra(StockSystemConstant.STOCK_INFO);
        startRealTimeUpdate();
        return super.onStartCommand(intent, flags, startId);
    }


    public void stopRealTimeUpdate()
    {
        StockSystemApplication.getInstance().stateRealTimeUpdate = -1;
        _isStart = false;
        _curStockInfo = null;
        _isEnd = true;
        _realTimeThread = null;
    }

    private void startRealTimeUpdate()
    {
        if (_curStockInfo == null)
        {
            _isEnd = true;
            return;
        }
        if (_isStart == false)
        {
            _isStart = true;
        }
        if (_realTimeThread == null)
        {
            _realTimeThread = new RealTimeThread();
            _realTimeThread.start();
        }
    }

    @Override
    public void onDestroy()
    {
        stopRealTimeUpdate();
        super.onDestroy();
    }

    class RealTimeThread extends Thread
    {
        private void updateData()
        {
            Bundle bundle =  new Bundle();
            String stockNumber = _curStockInfo.get_number();
            Bundle returnBundle = NetworkUtils.getStockGifImage(stockNumber);
            if (returnBundle != null)
            {
                Bitmap bitmap = returnBundle.getParcelable(StockSystemConstant.STOCK_GIF_IMAGE);
                bundle.putParcelable(StockSystemConstant.STOCK_GIF_IMAGE, bitmap);
            }
            int result = NetworkUtils.getStockInfo(stockNumber);
            StockInfo newStockInfo = StockUtils.getStockInfoByStockNumber(stockNumber);
            if (result == StockSystemConstant.STATE_SUCCESS)
            {
                bundle.putSerializable(StockSystemConstant.STOCK_INFO, newStockInfo);
            }
            if (newStockInfo != null)
            {
                float gap = newStockInfo.getCurrentPrice() - _curStockInfo.getCurrentPrice();
                StockSystemApplication.getInstance().checkSendNotifitionCondition(gap, newStockInfo);
            }
            Intent intent = new Intent();
            intent.setAction(StockSystemConstant.STOCK_GIF_IMAGE_ACTION);
            intent.putExtras(bundle);
            StockSystemApplication.getInstance().sendBroadcast(intent);
        }

        @Override
        public void run()
        {
            while (_isEnd == false && (_curStockInfo != null))
            {
                try {
                     boolean isExist = AccountStockUtils.isExistByStockNumber(_curStockInfo.get_number());
                    if (isExist == false)// 被删除了 就不更新
                    {
                        stopRealTimeUpdate();
                        return;
                    }
                    updateData();
                    sleep(StockSystemConstant.REALTIME_UPDATE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
                }
            }
        }
    }





}
