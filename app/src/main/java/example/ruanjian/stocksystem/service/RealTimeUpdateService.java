package example.ruanjian.stocksystem.service;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.net.MalformedURLException;

import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockInfoActivity;
import example.ruanjian.stocksystem.manager.ActivityAppManager;

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
        StockSystemUtils.stateRealTimeUpdate = -1;
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
        private Message _msg;
        private int splitStockData(String stockData)
        {
            int resultIndex = StockSystemConstant.STATE_SUCCESS;
            try {
                Pattern pattern = Pattern.compile(StockSystemConstant.STOCK_DATA_REX);
                Matcher matcher = pattern.matcher(stockData);
                if (matcher.find() == true)
                {
                    String result = matcher.group(2);
                    String stockNumber = matcher.group(1);
                    if (result.equals(StockSystemConstant.FAILED) || result.equals(""))
                    {
                        return StockSystemConstant.STATE_CANCELED;
                    }
                    StockInfo stockInfo = StockUtils.analysisStockData(result, stockNumber);
                    float gap = stockInfo.getCurrentPrice() - _curStockInfo.getCurrentPrice();
                    StockSystemUtils.checkSendNotifitionCondition(gap, stockInfo);
                    _msg.what = StockSystemConstant.STATE_SUCCESS;
                    Bundle bundle =  _msg.getData();
                    if (bundle == null)
                    {
                        bundle = new Bundle();
                    }
                    bundle.putSerializable(StockSystemConstant.STOCK_INFO, stockInfo);
                    _msg.setData(bundle);
                    _curStockInfo = stockInfo;
                }
            } catch (Exception e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
                resultIndex = StockSystemConstant.STATE_CANCELED;
            }
            return resultIndex;
        }


        protected void sendContent(String... params)
        {
            String dataUrl = StockSystemConstant.QUERY_URL + _curStockInfo.get_number();
            try {
                URL url = new URL(dataUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(2000);
                httpURLConnection.setReadTimeout(2000);
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StockSystemConstant.CHRSET_NAME_GBK);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String temp = "";
                while ((temp = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(temp);
                }
                splitStockData(stringBuffer.toString());
            } catch (UnknownHostException e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            } catch (IOException e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            }catch (Exception e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            }
        }

        public void runImage() {
            try {
                String imageUrl = StockSystemConstant.QUERY_IMG.replace("$", _curStockInfo.get_number());
                URL url = new URL(imageUrl);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setReadTimeout(3000);
                urlConnection.connect();
                _msg.what = StockSystemConstant.STATE_SUCCESS;
                Bundle bundle =  _msg.getData();
                if (bundle == null)
                {
                    bundle = new Bundle();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
                bundle.putParcelable(StockSystemConstant.STOCK_IMAGE, bitmap);
                _msg.setData(bundle);
            }catch (MalformedURLException e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            }catch (UnknownHostException e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            }catch (IOException e) {
                e.printStackTrace();
                StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            }
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
                    _msg = Message.obtain();
                    runImage();
                    sendContent();
                    ActivityAppManager activityAppManager = ActivityAppManager.getInstance();
                    StockInfoActivity stockInfoActivity = (StockInfoActivity) activityAppManager.getActivityBySimpeName(StockSystemUtils.stockInfoActivitySimpleName);
                    if (stockInfoActivity != null)
                    {
                        stockInfoActivity.stockInfoHandler.sendMessage(_msg);
                    }
                    sleep(StockSystemConstant.REALTIME_UPDATE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
                }
            }
        }
    }





}
