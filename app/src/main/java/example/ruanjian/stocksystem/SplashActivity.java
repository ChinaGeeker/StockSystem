package example.ruanjian.stocksystem;

import android.os.Bundle;
import android.content.Intent;

import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.activity.BaseActivity;
import example.ruanjian.stocksystem.activity.LoginActivity;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.asyncTask.GetAllAccountAsyncTask;

public class SplashActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.splash_activity);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        if (AccountUtils.getCurLoginAccountInfo() != null)
        {
            Intent intent = new Intent();
            intent.setClass(this, StockMainActivity.class);
            Intent deintent = getIntent();
            Bundle bundle = deintent.getExtras();
            if (bundle != null)
            {
                intent.putExtras(bundle);
            }
            startActivity(intent);
            finish();
        }
        else
        {
            if (AccountUtils.getAllAccountInfo().size() <= 0)
            {
                new GetAllAccountAsyncTask(this).execute();
            }
            else
            {
               startLoaingActivity();
            }
        }
        super.onStart();
    }

    public void startLoaingActivity()
    {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume()
    {
        stockSystemApplication.cancelAllNotifition();
        super.onResume();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }




    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}
