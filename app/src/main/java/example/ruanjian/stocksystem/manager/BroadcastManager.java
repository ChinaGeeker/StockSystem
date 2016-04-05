package example.ruanjian.stocksystem.manager;


import android.content.Intent;
import android.os.Bundle;

import example.ruanjian.stocksystem.application.StockSystemApplication;

public class BroadcastManager
{
    private static BroadcastManager _instance;

    public static BroadcastManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new BroadcastManager();
        }
        return _instance;
    }

    public void sendBroadcast(String action, Bundle extras)
    {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtras(extras);
        StockSystemApplication.getInstance().sendBroadcast(intent);
    }

}
