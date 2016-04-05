package example.ruanjian.stocksystem.broadcast;

import android.util.Log;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;

public class StockMainBroadcast extends BroadcastReceiver
{

    private StockMainActivity _stockMainActivity;

    public StockMainBroadcast(StockMainActivity stockMainActivity)
    {
        this._stockMainActivity = stockMainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        if (bundle == null)
        {
            Log.v("StockMainBroadcast ", " 没有传递参数  ");
            return;
        }
        int type = bundle.getInt(StockSystemConstant.STOCK_MAIN_TYPE, -1);
        _stockMainActivity.handler(type);

    }

}
