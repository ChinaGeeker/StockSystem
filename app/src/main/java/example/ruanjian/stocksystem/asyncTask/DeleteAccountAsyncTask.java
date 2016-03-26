package example.ruanjian.stocksystem.asyncTask;

import android.util.Log;
import android.os.AsyncTask;

import example.ruanjian.stocksystem.LoginActivity;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;

public class DeleteAccountAsyncTask extends AsyncTask<String, Void, Integer>
{

    private String _accountName = "";
    private LoginActivity _loginActivity;

    public DeleteAccountAsyncTask(LoginActivity loginActivity)
    {
        this._loginActivity = loginActivity;
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        _accountName = params[0];
        int resultIndex = AccountUtils.delete(_accountName);
        return resultIndex;
    }

    @Override
    protected void onPostExecute(Integer resultIndex)
    {
       /* if (resultIndex <= -1)
        {
            Log.v("测试", "删除账号失败");
        }
        else
        {
            Log.v("测试", "删除账号成功g");
        }*/
        AccountInfo curAccountInfo = AccountUtils.curSelectAccountInfo;
        if (curAccountInfo != null && curAccountInfo.get_accountName().equals(_accountName))
        {
            AccountUtils.curSelectAccountInfo = null;
        }
        _loginActivity.refreshLogin();
    }


}
