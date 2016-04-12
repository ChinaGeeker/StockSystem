package example.ruanjian.stocksystem.asyncTask;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import android.app.AlertDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.activity.LoginActivity;
import example.ruanjian.stocksystem.manager.AlertDialogManager;
import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.application.StockSystemApplication;

public class LoginAccountAsyncTask extends AsyncTask<String, Void, Void>
{
    private String _accountNameStr = "";

    private AlertDialog _alertDialog;

    private LoginActivity _loginActivity;

    private int _loginResult = 0;
    private String _registerMessage = "";

    public LoginAccountAsyncTask(LoginActivity loginActivity, AlertDialog alertDialog)
    {
        this._loginActivity = loginActivity;
        _alertDialog = alertDialog;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        _accountNameStr = params[0];
        String accountPasswordStr = params[1];

        String parameterData = StockSystemConstant.ACCOUNT_NAME + "=" + _accountNameStr+"&"
                + StockSystemConstant.ACCOUNT_COLUMN_PASSWORD_NAME + "=" + accountPasswordStr;
        String resultObj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.LOGIN_URL, parameterData).toString();
        try {
            JSONObject jsonObject = new JSONObject(resultObj);
            _loginResult = jsonObject.getInt(StockSystemConstant.RETURN_TYPE);
            _registerMessage = jsonObject.getString(StockSystemConstant.RETURN_MESSAGE);
            boolean isExistContent = jsonObject.has(StockSystemConstant.RETURN_CONTENT);
            if (isExistContent == true)
            {
                JSONObject contentJson = jsonObject.getJSONObject(StockSystemConstant.RETURN_CONTENT);
                String headIconPath =  contentJson.getString(StockSystemConstant.ACCOUNT_COLUMN_ICON_PATH_NAME);
                int loginTimes = contentJson.getInt(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME);

                AccountInfo accountInfo = new AccountInfo();
                accountInfo.set_accountName(_accountNameStr);
                accountInfo.set_password(accountPasswordStr);
                accountInfo.set_userIconPath(headIconPath);
                accountInfo.set_loginTime(loginTimes);
                accountInfo.set_loginState(StockSystemConstant.STATE_SUCCESS);
                AccountUtils.addAccountInfo(accountInfo);
                AccountUtils.setCurLoginAccountInfo(accountInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        AlertDialogManager.closeAlertDialog(_alertDialog);
        if (_loginResult == StockSystemConstant.STATE_CANCELED)
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