package example.ruanjian.stocksystem.asyncTask;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.manager.AlertDialogManager;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;

public class ExitAccountAsyncTask extends AsyncTask<String, Void, Void>
{
    private String _accountName = "";

    private  AlertDialog _alertDialog;

    private StockMainActivity _stockMainActivity;

    public ExitAccountAsyncTask(StockMainActivity stockMainActivity,  AlertDialog alertDialog)
    {
        this._alertDialog = alertDialog;
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
        AlertDialogManager.closeAlertDialog(_alertDialog);
        String accountExitStr = StockSystemApplication.getInstance().getText(R.string.accountExit).toString();
        accountExitStr = accountExitStr.replace("$", _accountName);
        StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_INFO, accountExitStr, null);
        Toast.makeText(_stockMainActivity, R.string.exitSuccess, Toast.LENGTH_LONG).show();
        _stockMainActivity.finish();
    }


}