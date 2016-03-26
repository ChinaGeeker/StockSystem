package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.widget.Toast;
import android.content.ContentValues;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.RegisterActivity;

public class RegisterAccountAsyncTask extends AsyncTask<String, Void, Boolean> {


    private RegisterActivity _registerActivity;

    public RegisterAccountAsyncTask(RegisterActivity registerActivity)
    {
        this._registerActivity = registerActivity;
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        boolean isRegisterSuccess = false;
        String accountNameStr = params[0];
        String accountPasswordStr = params[1];
        String selection = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?";

        boolean isExist = AccountUtils.isExist(selection, new String[]{accountNameStr});
        if (isExist == false)
        {
            int loginTimes = (int) (System.currentTimeMillis() / 1000);
            ContentValues contentValues = new ContentValues();
            contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_ICON_PATH_NAME, StockSystemConstant.DEFAULT);
            contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME, StockSystemConstant.STATE_CANCELED);
            contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME, accountNameStr);
            contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_PASSWORD_NAME, accountPasswordStr);
            contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME, loginTimes);

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.set_accountName(accountNameStr);
            accountInfo.set_password(accountPasswordStr);
            accountInfo.set_userIconPath(StockSystemConstant.DEFAULT);
            accountInfo.set_loginTime(loginTimes);
            accountInfo.set_loginState(StockSystemConstant.STATE_CANCELED);
            int insertResult = AccountUtils.insertAccount(null, contentValues);
            if (insertResult <= -1)
            {
                isRegisterSuccess = false;
            }
            else
            {
                AccountUtils.addAccountInfo(accountInfo);
                isRegisterSuccess = true;
            }
        }
        return isRegisterSuccess;
    }

    @Override
    protected void onPostExecute(Boolean isRegisterSuccess)
    {
        if (isRegisterSuccess == false)
        {
            Toast.makeText(_registerActivity, R.string.havedregistered, Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(_registerActivity, R.string.registeredSuccess, Toast.LENGTH_LONG).show();
            _registerActivity.finish();
        }
    }


}