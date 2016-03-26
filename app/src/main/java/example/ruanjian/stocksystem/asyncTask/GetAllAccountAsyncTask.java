package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;

import example.ruanjian.stocksystem.LoginActivity;
import example.ruanjian.stocksystem.utils.AccountUtils;

public class GetAllAccountAsyncTask extends AsyncTask<String, Void, Void>{

    private LoginActivity _loginActivity;

    public GetAllAccountAsyncTask(LoginActivity loginActivity)
    {
        this._loginActivity = loginActivity;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        AccountUtils.queryAllAccountInfo();
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        _loginActivity.refreshLogin();
    }

}
