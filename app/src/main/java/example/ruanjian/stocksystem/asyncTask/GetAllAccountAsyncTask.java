package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;

import example.ruanjian.stocksystem.SplashActivity;
import example.ruanjian.stocksystem.utils.AccountUtils;

public class GetAllAccountAsyncTask extends AsyncTask<String, Void, Void>{

    private SplashActivity _splashActivity;

    public GetAllAccountAsyncTask(SplashActivity splashActivity)
    {
        this._splashActivity = splashActivity;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        AccountUtils.queryAllAccountInfo();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        _splashActivity.startLoaingActivity();
    }


}
