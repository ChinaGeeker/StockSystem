package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.widget.Toast;
import android.content.Intent;
import android.app.AlertDialog;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.activity.LoginActivity;
import example.ruanjian.stocksystem.manager.AlertDialogManager;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;

public class LoginAccountAsyncTask extends AsyncTask<String, Void, Boolean>
{
    private String _accountNameStr = "";

    private AlertDialog _alertDialog;

    private LoginActivity _loginActivity;

    public LoginAccountAsyncTask(LoginActivity loginActivity, AlertDialog alertDialog)
    {
        this._loginActivity = loginActivity;
        _alertDialog = alertDialog;
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        _accountNameStr = params[0];
        String passwordStr =  params[1];
        return  AccountUtils.updateLoginAccount(_accountNameStr, passwordStr);
    }

    @Override
    protected void onPostExecute(Boolean isLoginSuccess)
    {
        AlertDialogManager.closeAlertDialog(_alertDialog);
        if (isLoginSuccess == false)
        {
            Toast.makeText(_loginActivity, R.string.accountPasswordWrong, Toast.LENGTH_LONG).show();
        }
        else
        {
            String accountLoginStr = StockSystemApplication.getInstance().getText(R.string.accountLogin).toString();
            accountLoginStr = accountLoginStr.replace("$", _accountNameStr);
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_INFO, accountLoginStr, null);
            Intent intent = new Intent();
            intent.setClass(_loginActivity, StockMainActivity.class);
            _loginActivity.startActivity(intent);
        }
    }


}