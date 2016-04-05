package example.ruanjian.stocksystem.broadcast;

import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;

import example.ruanjian.stocksystem.activity.StockInfoActivity;

public class StockGifImageBroadcast extends BroadcastReceiver
{

    private StockInfoActivity _stockInfoActivity;


    public StockGifImageBroadcast(StockInfoActivity stockInfoActivity) {
        this._stockInfoActivity = stockInfoActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        if (bundle == null)
        {
            Log.v("StockInfoBroadcast ", " 没有传递参数  ");
            return;
        }
        _stockInfoActivity.realTimeRefreshData(bundle);
    }



}
