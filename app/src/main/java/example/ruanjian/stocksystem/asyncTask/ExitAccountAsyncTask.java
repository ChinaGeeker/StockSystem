package example.ruanjian.stocksystem.asyncTask;


import android.os.AsyncTask;
import android.widget.Toast;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;

public class ExitAccountAsyncTask extends AsyncTask<String, Void, Void>
{
    private String _accountName = "";

    private StockMainActivity _stockMainActivity;

    public ExitAccountAsyncTask(StockMainActivity stockMainActivity)
    {
        this._stockMainActivity = stockMainActivity;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        _accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        AccountUtils.exitAccount();
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        String accountExitStr = StockSystemUtils.context.getText(R.string.accountExit).toString();
        accountExitStr = accountExitStr.replace("$", _accountName);
        StockSystemUtils.printLog(StockSystemConstant.LOG_INFO, accountExitStr, null);
        Toast.makeText(_stockMainActivity, R.string.exitSuccess, Toast.LENGTH_LONG).show();
        _stockMainActivity.finish();
    }


}