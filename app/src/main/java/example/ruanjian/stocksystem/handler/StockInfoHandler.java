package example.ruanjian.stocksystem.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockInfoActivity;
import example.ruanjian.stocksystem.manager.ActivityAppManager;

public class StockInfoHandler extends Handler
{
    //将广播改为 handler
    public StockInfoHandler()
    {
    }

    @Override
    public void handleMessage(Message msg)
    {
        super.handleMessage(msg);
        Bundle bundle = msg.getData();
        if (msg.what == StockSystemConstant.STATE_SUCCESS) {
            ActivityAppManager activityAppManager = ActivityAppManager.getInstance();
            StockInfoActivity stockInfoActivity = (StockInfoActivity) activityAppManager.getActivityBySimpeName(StockSystemUtils.stockInfoActivitySimpleName);
            if (stockInfoActivity != null)
            {
                stockInfoActivity.refreshData(bundle);
            }
        }

    }


}
