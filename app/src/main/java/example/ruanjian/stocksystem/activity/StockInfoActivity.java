package example.ruanjian.stocksystem.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.app.AlertDialog;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.content.DialogInterface;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.asyncTask.DeleteAccountStockAsyncTask;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.manager.BroadcastManager;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.service.RealTimeUpdateService;
import example.ruanjian.stocksystem.asyncTask.StockGifImageAsyncTask;
import example.ruanjian.stocksystem.broadcast.StockGifImageBroadcast;

public class StockInfoActivity extends BaseActivity {

    private TextView _stockNameTxt;
    private TextView _stockNumberTxt;
    private TextView _stockMaxPriceTxt;
    private TextView _stockCurPriceTxt;
    private TextView _stockMinPriceTxt;
    private TextView _stockOpenPriceTxt;
    private TextView _stockClosingPriceTxt;

    private Button _deleteBtn;
    private ImageView _stockImage;

    private StockInfo _curStockInfo = null;

    private AlertDialog _alertDialog = null;


    private StockGifImageBroadcast _stockGifImageBroadcast;


    private Context _context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        _context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_info_activity);
        initView();
        checkIntentData();
    }

    private void refreshGifImage(Bundle bundle)
    {
        Bitmap bitmap = bundle.getParcelable(StockSystemConstant.STOCK_GIF_IMAGE);
        if (bundle == null || bitmap == null)
        {
            return;
        }
        _stockImage.setImageBitmap(bitmap);
        showUpdateAlert();
    }

    public void realTimeRefreshData(Bundle bundle)
    {
        refreshGifImage(bundle);
        StockInfo tempStockInfo = (StockInfo) bundle.getSerializable(StockSystemConstant.STOCK_INFO);
        if (tempStockInfo == null)
        {
            return;
        }
        _curStockInfo = tempStockInfo;
        Log.v("  刷新  ","   realTimeRefreshData");
        setData();
    }

    private void initView()
    {
        _stockImage = (ImageView) findViewById(R.id.stockImage);
        _stockNumberTxt = (TextView) findViewById(R.id.stockNumberTxt);
        _stockMaxPriceTxt = (TextView) findViewById(R.id.stockMaxPriceTxt);
        _stockMinPriceTxt = (TextView) findViewById(R.id.stockMinPriceTxt);
        _stockCurPriceTxt = (TextView) findViewById(R.id.stockCurPriceTxt);
        _stockOpenPriceTxt = (TextView) findViewById(R.id.stockOpenPriceTxt);
        _stockClosingPriceTxt = (TextView) findViewById(R.id.stockClosingPriceTxt);
        _stockNameTxt = (TextView) findViewById(R.id.stockInfo_fragment_stockNameTxt);

        _deleteBtn = (Button) findViewById(R.id.deleteBtn);
        _deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_curStockInfo != null) {
                    AccountStockUtils.removeStockInfo(_curStockInfo);
                    new DeleteAccountStockAsyncTask(_context).execute(_curStockInfo.get_number());
                }
            }
        });
        registerBroadcast();
    }

    private void checkIntentData()
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
            new StockGifImageAsyncTask().execute(_curStockInfo.get_number());
        }
    }

    private void setData()
    {
        if (_curStockInfo != null)
        {
            _stockNameTxt.setText(_curStockInfo.getName());
            _stockNumberTxt.setText(_curStockInfo.get_number());
            _stockMaxPriceTxt.setText(_curStockInfo.get_maxPrice());
            _stockMinPriceTxt.setText(_curStockInfo.get_minPrice());
            _stockCurPriceTxt.setText(_curStockInfo.get_currentPrice());
            _stockOpenPriceTxt.setText(_curStockInfo.get_openingPrice());
            _stockClosingPriceTxt.setText(_curStockInfo.getClosing_price());
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        if (_alertDialog != null)
        {
            _alertDialog.dismiss();
        }
        unRegisterBroadcast();
        super.onDestroy();
    }



    private void registerBroadcast()
    {
        if (_stockGifImageBroadcast == null)
        {
            _stockGifImageBroadcast = new StockGifImageBroadcast(this);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(StockSystemConstant.STOCK_GIF_IMAGE_ACTION);
        registerReceiver(_stockGifImageBroadcast, intentFilter);
    }

    private void unRegisterBroadcast()
    {
        unregisterReceiver(_stockGifImageBroadcast);
        _stockGifImageBroadcast = null;
    }









    private void showUpdateAlert()
    {
        if (stockSystemApplication.stateRealTimeUpdate >= StockSystemConstant.STATE_CANCELED)
        {
            return;
        }
        if (stockSystemApplication.isConnectedNetWork() == true)
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
                        stockSystemApplication.stateRealTimeUpdate = StockSystemConstant.STATE_CANCELED;
                    }
                });
                builder.setNegativeButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 实时更新
                        stockSystemApplication.stateRealTimeUpdate = StockSystemConstant.STATE_SUCCESS;
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


}