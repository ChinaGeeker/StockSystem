package example.ruanjian.stocksystem.activity;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.widget.Button;
import android.content.Intent;
import android.app.AlertDialog;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.content.DialogInterface;

import java.net.URL;
import java.io.IOException;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.net.MalformedURLException;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.handler.StockInfoHandler;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.manager.ActivityAppManager;
import example.ruanjian.stocksystem.service.RealTimeUpdateService;

public class StockInfoActivity extends Activity {

    private TextView _sotckNameTxt;
    private TextView _stockNumberTxt;
    private TextView stockMaxPriceTxt;
    private TextView _stockCurPriceTxt;
    private TextView _stockMinPriceTxt;
    private TextView _stockOpenPriceTxt;
    private TextView _stockClosingPriceTxt;

    private Button _deleteBtn;
    private ImageView _stockImage;

    private ImageHandler infoHandler;

    private StockInfo _curStockInfo = null;

    private AlertDialog _alertDialog = null;

    public StockInfoHandler stockInfoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActivityAppManager.getInstance().addActivity(this);

        stockInfoHandler = new StockInfoHandler();
        StockSystemUtils.stockInfoActivitySimpleName = getClass().getSimpleName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_info_activity);
        initView();
        updateData();
    }

    public void refreshData(Bundle bundle)
    {
        Bitmap bitmap = (Bitmap) bundle.getParcelable(StockSystemConstant.STOCK_IMAGE);
        if (bundle == null || bitmap == null)
        {
            return;
        }
        _stockImage.setImageBitmap(bitmap);
        _curStockInfo = (StockInfo) bundle.getSerializable(StockSystemConstant.STOCK_INFO);
        setData();
    }
    private void initView()
    {
        infoHandler = new ImageHandler();
        _stockImage = (ImageView) findViewById(R.id.stockImage);
        _stockNumberTxt = (TextView) findViewById(R.id.stockNumberTxt);
        stockMaxPriceTxt = (TextView) findViewById(R.id.stockMaxPriceTxt);
        _stockMinPriceTxt = (TextView) findViewById(R.id.stockMinPriceTxt);
        _stockCurPriceTxt = (TextView) findViewById(R.id.stockCurPriceTxt);
        _stockOpenPriceTxt = (TextView) findViewById(R.id.stockOpenPriceTxt);
        _stockClosingPriceTxt = (TextView) findViewById(R.id.stockClosingPriceTxt);
        _sotckNameTxt = (TextView) findViewById(R.id.stockInfo_fragment_stockNameTxt);


        _deleteBtn = (Button) findViewById(R.id.deleteBtn);
        _deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_curStockInfo != null) {
                    AccountStockUtils.removeStockInfo(_curStockInfo);
                }
                finish();
            }
        });
    }

    public void updateData()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            _curStockInfo = (StockInfo) bundle.getSerializable(StockSystemConstant.STOCK_INFO);
        }
        if (_curStockInfo != null)
        {
            setData();
            String imageUrl = StockSystemConstant.QUERY_IMG.replace("$", _curStockInfo.get_number());
            if (StockSystemUtils.isConnectedNetWork(this) == true)
            {
                new ImageThread(imageUrl).start();
            }
        }
    }

    private void setData()
    {
        if (_curStockInfo != null)
        {
            _sotckNameTxt.setText(_curStockInfo.getName());
            _stockNumberTxt.setText(_curStockInfo.get_number());
            stockMaxPriceTxt.setText(_curStockInfo.get_maxPrice());
            _stockMinPriceTxt.setText(_curStockInfo.get_minPrice());
            _stockCurPriceTxt.setText(_curStockInfo.get_currentPrice());
            _stockOpenPriceTxt.setText(_curStockInfo.get_openingPrice());
            _stockClosingPriceTxt.setText(_curStockInfo.getClosing_price());
        }
    }

    @Override
    public void onStart()
    {
        Log.v("StockInfoActivity 测试 ", "onStart");
        super.onStart();
    }

    @Override
    public void onStop()
    {
        Log.v("StockInfoActivity 测试 ", "onStop");
        super.onStop();
    }

    @Override
    public void onResume()
    {
        Log.v("StockInfoActivity 测试 ", "onResume");
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        StockSystemUtils.stockInfoActivitySimpleName = "";
        ActivityAppManager.getInstance().removeActivity(this);
        Log.v("StockInfoActivity 测试 ", "onDestroy");
        super.onDestroy();
    }
    class ImageHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == StockSystemConstant.STATE_SUCCESS)
            {
                Bundle bundle =  msg.getData();
                Bitmap bitmap = (Bitmap) bundle.getParcelable(StockSystemConstant.STOCK_IMAGE);
                _stockImage.setImageBitmap(bitmap);
                showUpdateAlert();
            }
        }

    }
    private void showUpdateAlert()
    {
        if (StockSystemUtils.stateRealTimeUpdate >= StockSystemConstant.STATE_CANCELED)
        {
            return;
        }
        if (StockSystemUtils.isConnectedNetWork(this) == true)
        {
            if (_alertDialog == null)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(StockInfoActivity.this);
                builder.setMessage(R.string.realTimeUpdateWarn);
                builder.setTitle(R.string.warn);
                builder.setPositiveButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _alertDialog.dismiss();
                        StockSystemUtils.stateRealTimeUpdate = StockSystemConstant.STATE_CANCELED;
                    }
                });
                builder.setNegativeButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 实时更新
                        StockSystemUtils.stateRealTimeUpdate = StockSystemConstant.STATE_SUCCESS;
                        Intent intent = new Intent();
                        intent.putExtra(StockSystemConstant.STOCK_INFO, _curStockInfo);
                        intent.setClass(StockInfoActivity.this, RealTimeUpdateService.class);
                        startService(intent);
                        _alertDialog.dismiss();
                    }
                });
                _alertDialog = builder.create();
            }
            _alertDialog.show();
        }

    }


    class ImageThread extends Thread
    {
        private String _imageUrl;
        public ImageThread(String imageUrl) {
            this._imageUrl = imageUrl;
        }
        @Override
        public void run() {
            try {
                URL url = new URL(_imageUrl);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setReadTimeout(3000);
                urlConnection.connect();
                Message message = new Message();
                message.what = StockSystemConstant.STATE_SUCCESS;
                Bundle bundle = new Bundle();
                Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
                bundle.putParcelable(StockSystemConstant.STOCK_IMAGE,bitmap);
                message.setData(bundle);
                infoHandler.sendMessage(message);
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
    }



}