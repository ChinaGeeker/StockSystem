package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.widget.Toast;
import android.content.Intent;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.LoginActivity;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;

public class LoginAccountAsyncTask extends AsyncTask<String, Void, Boolean> {

    private String _accountNameStr = "";
    private LoginActivity _loginActivity;

    public LoginAccountAsyncTask(LoginActivity loginActivity)
    {
        this._loginActivity = loginActivity;
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        _accountNameStr = params[0];
        String passwordStr =  params[1];
        boolean isLoginSuccess = AccountUtils.updateLoginAccount(_accountNameStr, passwordStr);
        return isLoginSuccess;
    }

    @Override
    protected void onPostExecute(Boolean isLoginSuccess)
    {
        if (isLoginSuccess == false)
        {
            Toast.makeText(_loginActivity, R.string.accountPasswordWrong, Toast.LENGTH_LONG).show();
        }
        else
        {
            String accountLoginStr = StockSystemUtils.context.getText(R.string.accountLogin).toString();
            accountLoginStr = accountLoginStr.replace("$", _accountNameStr);
            StockSystemUtils.printLog(StockSystemConstant.LOG_INFO, accountLoginStr, null);
            Intent intent = new Intent();
            intent.setClass(_loginActivity, StockMainActivity.class);
            _loginActivity.startActivity(intent);
        }
    }


}