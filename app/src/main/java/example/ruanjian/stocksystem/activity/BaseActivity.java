package example.ruanjian.stocksystem.activity;

import android.app.Activity;
import android.os.Bundle;

import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.manager.ActivityAppManager;

public class BaseActivity extends Activity
{
    protected StockSystemApplication stockSystemApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        stockSystemApplication = (StockSystemApplication) getApplication();
        ActivityAppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy()
    {
        ActivityAppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

}
